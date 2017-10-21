package renderer.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import core.EngineMain;
import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.ILight;
import object.scene.IScene;
import object.terrain.terrain.ITerrain;
import object.texture.Texture;
import object.texture.Texture2D;
import primitive.model.Model;
import renderer.bounding.BoundingRenderer;
import renderer.entity.DecorEntityRenderer;
import renderer.entity.EntityRendererManager;
import renderer.entity.IEntityRenderer;
import renderer.entity.IEntityRendererManager;
import renderer.entity.NormalEntityRenderer;
import renderer.entity.TexturedEntityRenderer;
import renderer.environment.EnvironmentMapRenderer;
import renderer.gpgpu.NormalMapRenderer;
import renderer.processor.ISceneProcessor;
import renderer.processor.SceneProcessor;
import renderer.shadow.ShadowMapMasterRenderer;
import renderer.skybox.SkyboxRenderer;
import renderer.terrain.TerrainRenderer;
import renderer.voxel.VoxelRenderer;
import tool.math.Matrix4f;
import tool.openGL.OGLUtils;

public class MainRenderer implements IMainRenderer {

	private Matrix4f projectionMatrix;
	private Matrix4f normalDistProjectionMatrix;
	private Matrix4f lowDistProjectionMatrix;
	private NormalMapRenderer normalMapRenderer;
	private TerrainRenderer terrainRenderer;
	private SkyboxRenderer skyboxRenderer;
	private VoxelRenderer voxelRenderer;
	private BoundingRenderer boundingRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;
	private EnvironmentMapRenderer enviroRenderer;
	
	IEntityRendererManager entityRendererManager;
	
	private ISceneProcessor processor;

	private Texture environmentMap;

	private boolean environmentDynamic = false;
	private boolean environmentRendered = false;

	private Map<Model, List<IEntity>> texturedEntities = new HashMap<Model, List<IEntity>>();
	private Map<Model, List<IEntity>> normalEntities = new HashMap<Model, List<IEntity>>();
	private Map<Model, List<IEntity>> decorEntities = new HashMap<Model, List<IEntity>>();
	private Collection<ITerrain> terrains = new ArrayList<ITerrain>();

	public MainRenderer(IScene scene) {
		OGLUtils.cullBackFaces(true);
		this.normalDistProjectionMatrix = createProjectionMatrix(EngineSettings.FAR_PLANE);
		this.lowDistProjectionMatrix = createProjectionMatrix(100);
		projectionMatrix = normalDistProjectionMatrix;
		
		IEntityRenderer texturedEntityRenderer = new TexturedEntityRenderer(projectionMatrix);
		IEntityRenderer normalEntityRenderer = new NormalEntityRenderer(projectionMatrix);
		IEntityRenderer decorEntityRenderer = new DecorEntityRenderer(projectionMatrix);
		this.entityRendererManager = new EntityRendererManager();
		this.entityRendererManager.addPair(texturedEntityRenderer, texturedEntities);
		this.entityRendererManager.addPair(normalEntityRenderer, normalEntities);
		this.entityRendererManager.addPair(decorEntityRenderer, decorEntities);
		this.terrainRenderer = new TerrainRenderer(projectionMatrix);
		//this.normalMapRenderer = new NormalMapRenderer(terrainRenderer.getHeightMap().getWidth());
		//this.normalMapRenderer.setStrength(4);
		//this.normalMapRenderer.render(terrainRenderer.getHeightMap());
		//this.terrainRenderer.setNormalMap(this.normalMapRenderer.getNormalMap());
		this.skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		this.voxelRenderer = new VoxelRenderer(projectionMatrix);
		this.boundingRenderer = new BoundingRenderer(projectionMatrix);
		this.shadowMapRenderer = new ShadowMapMasterRenderer(scene.getCamera());
		this.enviroRenderer = new EnvironmentMapRenderer();
		this.processor = new SceneProcessor();
	}

	@Override
	public void renderScene(IScene scene, Vector4f clipPlane) {
		renderScene(scene, clipPlane, false);
	}
	
	@Override
	public void renderScene(IScene scene) {
		scene.getEntities().getAll()
			.forEach(entity -> processEntityByType(entity.getType(), entity));
		renderEditorScene(scene);
	}

	@Override
	public void renderScene(IScene scene, Vector4f clipPlane, boolean isLowDistance) {
		scene.getTerrains().getAll().forEach(terrain -> processor.processTerrain(terrain, terrains));
		this.environmentMap = scene.getEnvironmentMap();
		this.prepareFrustumEntities(scene, isLowDistance);
		if (this.environmentDynamic) {
			environmentRendered = false;
		}
		if (!environmentRendered) {
			enviroRenderer.render(scene, this, scene.getEntities().get("Cuby4"));
			this.environmentRendered = true;
		}
		render(scene, clipPlane, isLowDistance);
	}

	@Override
	public void renderLowQualityScene(Map<Model, List<IEntity>> entities, Collection<ITerrain> terrains,
			Collection<ILight> lights, ICamera camera) {
		this.entityRendererManager.render(null, lights, camera, null, null, true);
		terrainRenderer.renderLow(terrains, lights, camera);
		skyboxRenderer.render(camera);
		this.cleanScene();
	}

	/**
	 * Renders full scene with preloaded objects.
	 * 
	 * @param scene {@link IScene}
	 * @param clipPlane {@link Vector4f} clipping plane
	 * @param isLowDistance 
	 */
	private void render(IScene scene, Vector4f clipPlane, boolean isLowDistance) {
		prepare();

		if (EngineDebug.getBoundingMode() != EngineDebug.BOUNDING_NONE) {
			boundingRenderer.render(texturedEntities, normalEntities, decorEntities, scene.getCamera());
		}
		
		if(EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY || 
				EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY_TERRAIN) {
			OGLUtils.doWiredFrame(true);
		}
		Matrix4f shadowMapSpaceMatrix = shadowMapRenderer.getToShadowMapSpaceMatrix();
		this.entityRendererManager.render(clipPlane, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix, environmentMap, false);
		OGLUtils.doWiredFrame(false);
		voxelRenderer.render(scene.getChunks(), clipPlane, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix,
				scene.getFrustum());
		if(EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_TERRAIN || 
				EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY_TERRAIN) {
			OGLUtils.doWiredFrame(true);
		}
		terrainRenderer.render(terrains, clipPlane, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix);
		OGLUtils.doWiredFrame(false);

		skyboxRenderer.render(scene.getCamera());	
		this.cleanScene();
	}
	
	private void renderEditorScene(IScene scene) {
		prepare();
		Matrix4f shadowMapSpaceMatrix = shadowMapRenderer.getToShadowMapSpaceMatrix();
		this.entityRendererManager.render(EngineSettings.NO_CLIP, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix, null, false);
		this.cleanScene();
	}

	@Override
	public void renderShadowMap(IScene scene) {
		scene.getTerrains().getAll()
			.forEach(terrain -> processor.processTerrain(terrain, terrains));
		prepareShadowFrustumEntities(scene);
		shadowMapRenderer.render(decorEntities, terrains, scene.getSun(), scene.getCamera());
		this.cleanScene();
	}

	private void prepare() {
		OGLUtils.depthTest(true);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE, 1);
		getShadowMapTexture().bind(6);
	}
	
	private void prepareFrustumEntities(IScene scene, boolean isLowDistance) {
		List<IEntity> frustumEntities = new ArrayList<IEntity>();
		if(isLowDistance) {
			frustumEntities = scene.getFrustumEntities().prepareFrustumLowEntities(scene, this.lowDistProjectionMatrix);
		} else {
			frustumEntities = scene.getFrustumEntities().prepareFrustumHighEntities(scene, this.normalDistProjectionMatrix);
		}
		frustumEntities.parallelStream()
			.forEach(entity -> 
				processEntityByType(entity.getType(), entity));
	}
	
	private void prepareShadowFrustumEntities(IScene scene) {
		List<IEntity> frustumEntities = new ArrayList<IEntity>();
		frustumEntities = scene.getFrustumEntities().prepareShadowFrustumEntities(scene, shadowMapRenderer.getToShadowMapSpaceMatrix());
		frustumEntities.parallelStream()
			.forEach(entity -> 
				processEntityByType(EngineSettings.ENTITY_TYPE_DECORATE, entity));
	}
	
	private void cleanScene() {
		this.texturedEntities.clear();
		this.normalEntities.clear();
		this.decorEntities.clear();
		this.terrains.clear();
	}

	@Override
	public void clean() {
		this.entityRendererManager.clean();
		this.terrainRenderer.clean();
		this.shadowMapRenderer.clean();
	}
	
	private Matrix4f createProjectionMatrix(float farPlane) {
		Matrix4f currProjectionMatrix = new Matrix4f();		
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(EngineSettings.FOV / 2f)));
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = farPlane - EngineSettings.NEAR_PLANE;

		currProjectionMatrix.m[0][0] = x_scale;
		currProjectionMatrix.m[1][1] = y_scale;
		currProjectionMatrix.m[2][2] = -((farPlane + EngineSettings.NEAR_PLANE) / frustrum_length);
		currProjectionMatrix.m[2][3] = -1;
		currProjectionMatrix.m[3][2] = -((2 * EngineSettings.NEAR_PLANE * farPlane) / frustrum_length);
		currProjectionMatrix.m[3][3] = 0;
		return currProjectionMatrix;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	private Texture2D getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}
	
	private synchronized void processEntityByType(int type, IEntity entity) {
		switch(type) {
		 	case EngineSettings.ENTITY_TYPE_SIMPLE:
		 		processor.processEntity(entity, texturedEntities);
		 		break;
		 	case EngineSettings.ENTITY_TYPE_DECORATE:
		 		processor.processEntity(entity, decorEntities);
		 		break;
		 	case EngineSettings.ENTITY_TYPE_NORMAL:
		 		processor.processEntity(entity, normalEntities);
		 		break;
		}
	}

}
