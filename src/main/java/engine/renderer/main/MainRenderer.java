package renderer.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

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
import renderer.processor.ISceneProcessor;
import renderer.processor.SceneProcessor;
import renderer.shadow.ShadowMapMasterRenderer;
import renderer.skybox.SkyboxRenderer;
import renderer.terrain.TerrainRenderer;
import renderer.voxel.VoxelRenderer;
import tool.math.Frustum;
import tool.math.Matrix4f;
import tool.openGL.OGLUtils;

public class MainRenderer implements IMainRenderer {

	private Matrix4f projectionMatrix;
	private Matrix4f normalDistProjectionMatrix;
	private Matrix4f lowDistProjectionMatrix;
	private TerrainRenderer terrainRenderer;	
	private SkyboxRenderer skyboxRenderer;
	private VoxelRenderer voxelRenderer;
	private BoundingRenderer boundingRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;
	private EnvironmentMapRenderer enviroRenderer;
	
	IEntityRendererManager entityRendererManager; 
	
	private ISceneProcessor processor;

	private Texture environmentMap;
	private Frustum frustum = new Frustum();

	private boolean terrainWiredFrame = false;
	private boolean entitiyWiredFrame = false;
	private boolean environmentDynamic = false;
	private boolean environmentRendered = false;

	private Map<Model, List<IEntity>> texturedEntities = new HashMap<Model, List<IEntity>>();
	private Map<Model, List<IEntity>> normalEntities = new HashMap<Model, List<IEntity>>();
	private Map<Model, List<IEntity>> decorEntities = new HashMap<Model, List<IEntity>>();
	private Collection<ITerrain> terrains = new ArrayList<ITerrain>();

	public MainRenderer(IScene scene) {
		OGLUtils.cullBackFaces(true);
		createProjectionMatrix();
		createLowDistProjectionMatrix();
		projectionMatrix = normalDistProjectionMatrix;
		
		IEntityRenderer texturedEntityRenderer = new TexturedEntityRenderer(projectionMatrix);
		IEntityRenderer normalEntityRenderer = new NormalEntityRenderer(projectionMatrix);
		IEntityRenderer decorEntityRenderer = new DecorEntityRenderer(projectionMatrix);
		this.entityRendererManager = new EntityRendererManager();
		this.entityRendererManager.addPair(texturedEntityRenderer, texturedEntities);
		this.entityRendererManager.addPair(normalEntityRenderer, normalEntities);
		this.entityRendererManager.addPair(decorEntityRenderer, decorEntities);
		
		this.terrainRenderer = new TerrainRenderer(projectionMatrix);
		this.skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		this.voxelRenderer = new VoxelRenderer(projectionMatrix);
		this.boundingRenderer = new BoundingRenderer(projectionMatrix);
		this.shadowMapRenderer = new ShadowMapMasterRenderer(scene.getCamera());
		scene.setFrustum(this.frustum);
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
		this.frustum.extractFrustum(scene.getCamera(), this.getProjectionMatrix());
		scene.getTerrains().getAll().forEach(terrain -> processor.processTerrain(terrain, terrains));
		this.environmentMap = scene.getEnvironmentMap();
		scene.getEntities().updateWithFrustum(this.frustum, scene.getCamera())
			.forEach((type, list) -> 
				list.parallelStream().forEach(entity -> 
					processEntityByType(type, entity)));
		if (this.environmentDynamic) {
			environmentRendered = false;
		}
		if (!environmentRendered) {
			enviroRenderer.render(scene, this, scene.getEntities().get("Cuby4"));
			this.environmentRendered = true;
		}
		render(scene, clipPlane, isLowDistance);
	}

	private void render(IScene scene, Vector4f clipPlane, boolean isLowDistance) {
		prepare();
		checkWiredFrameOn(entitiyWiredFrame);
		if (EngineDebug.boundingMode != EngineDebug.BOUNDING_NONE) {
			boundingRenderer.render(texturedEntities, normalEntities, scene.getCamera());
		}
		
		Matrix4f shadowMapSpaceMatrix = shadowMapRenderer.getToShadowMapSpaceMatrix();
		this.entityRendererManager.render(clipPlane, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix, environmentMap, false);
		checkWiredFrameOn(terrainWiredFrame);
		voxelRenderer.render(scene.getChunks(), clipPlane, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix,
				frustum);
		terrainRenderer.render(terrains, clipPlane, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix);
		checkWiredFrameOff(terrainWiredFrame);

		skyboxRenderer.render(scene.getCamera());		
		terrains.clear();
		texturedEntities.clear();
		normalEntities.clear();
		decorEntities.clear();
	}
	
	private void renderEditorScene(IScene scene) {
		prepare();
		Matrix4f shadowMapSpaceMatrix = shadowMapRenderer.getToShadowMapSpaceMatrix();
		this.entityRendererManager.render(EngineSettings.NO_CLIP, scene.getLights().getAll(), scene.getCamera(), shadowMapSpaceMatrix, null, false);
		terrains.clear();
		texturedEntities.clear();
		normalEntities.clear();
		decorEntities.clear();
	}

	@Override
	public void renderLowQualityScene(Map<Model, List<IEntity>> entities, Collection<ITerrain> terrains,
			Collection<ILight> lights, ICamera camera) {
		this.entityRendererManager.render(null, lights, camera, null, null, true);
		terrainRenderer.renderLow(terrains, lights, camera);
		skyboxRenderer.render(camera);
	}

	@Override
	public void renderShadowMap(IScene scene) {
		this.frustum.extractFrustum(scene.getCamera(), this.getProjectionMatrix());
		scene.getEntities().updateWithFrustum(frustum, scene.getCamera())
			.forEach((type, list) -> list.parallelStream()
					.forEach(entity -> 
						processEntityByType(type, entity)));
		scene.getTerrains().getAll()
		.forEach(terrain -> processor.processTerrain(terrain, terrains));
		shadowMapRenderer.render(texturedEntities, terrains, normalEntities, scene.getSun(), scene.getCamera());
		texturedEntities.clear();
		normalEntities.clear();
		decorEntities.clear();
		terrains.clear();
	}

	private Texture2D getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}

	@Override
	public void clean() {
		this.entityRendererManager.clean();
		terrainRenderer.clean();
		shadowMapRenderer.clean();
	}

	private void prepare() {
		OGLUtils.depthTest(true);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE, 1);
		getShadowMapTexture().bind(5);
	}

	private void createProjectionMatrix() {
		normalDistProjectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(EngineSettings.FOV / 2f)));
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = EngineSettings.FAR_PLANE - EngineSettings.NEAR_PLANE;

		normalDistProjectionMatrix.m[0][0] = x_scale;
		normalDistProjectionMatrix.m[1][1] = y_scale;
		normalDistProjectionMatrix.m[2][2] = -((EngineSettings.FAR_PLANE + EngineSettings.NEAR_PLANE) / frustrum_length);
		normalDistProjectionMatrix.m[2][3] = -1;
		normalDistProjectionMatrix.m[3][2] = -((2 * EngineSettings.NEAR_PLANE * EngineSettings.FAR_PLANE)
				/ frustrum_length);
		normalDistProjectionMatrix.m[3][3] = 0;
	}

	private void createLowDistProjectionMatrix() {
		lowDistProjectionMatrix = new Matrix4f();
		float farPlane = 100;
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(EngineSettings.FOV / 2f)));
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = farPlane - EngineSettings.NEAR_PLANE;

		lowDistProjectionMatrix.m[0][0] = x_scale;
		lowDistProjectionMatrix.m[1][1] = y_scale;
		lowDistProjectionMatrix.m[2][2] = -((farPlane + EngineSettings.NEAR_PLANE) / frustrum_length);
		lowDistProjectionMatrix.m[2][3] = -1;
		lowDistProjectionMatrix.m[3][2] = -((2 * EngineSettings.NEAR_PLANE * farPlane) / frustrum_length);
		lowDistProjectionMatrix.m[3][3] = 0;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	@Override
	public Frustum getFrustum() {
		return this.frustum;
	}

	@Override
	public void setEntityWiredFrame(boolean entitiyWiredFrame) {
		this.entitiyWiredFrame = entitiyWiredFrame;
	}

	@Override
	public void setTerrainWiredFrame(boolean terrainWiredFrame) {
		this.terrainWiredFrame = terrainWiredFrame;
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

	private void checkWiredFrameOn(boolean value) {
		if(value) {
			OGLUtils.doWiredFrame(true);
		}
	}

	private void checkWiredFrameOff(boolean value) {
		if(value) {
			OGLUtils.doWiredFrame(false);
		}
	}

}
