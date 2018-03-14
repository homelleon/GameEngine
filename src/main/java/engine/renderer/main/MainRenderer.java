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
import object.terrain.terrain.ITerrain;
import primitive.model.Model;
import primitive.texture.Texture;
import primitive.texture.Texture2D;
import renderer.bounding.BoundingRenderer;
import renderer.entity.DecorEntityRenderer;
import renderer.entity.EntityRendererManager;
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
import scene.IScene;
import tool.math.Matrix4f;
import tool.openGL.OGLUtils;

public class MainRenderer implements IMainRenderer {

	private Matrix4f projectionMatrix;
	private Matrix4f projectionNormalMatrix;
	private Matrix4f projectionLowMatrix;
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
		projectionNormalMatrix = createProjectionMatrix(EngineSettings.FAR_PLANE);
		projectionLowMatrix = createProjectionMatrix(100);
		projectionMatrix = projectionNormalMatrix;
		
		entityRendererManager = new EntityRendererManager()
			.addPair(new TexturedEntityRenderer(projectionMatrix), texturedEntities)
			.addPair(new NormalEntityRenderer(projectionMatrix), normalEntities)
			.addPair(new DecorEntityRenderer(projectionMatrix), decorEntities);
		
		terrainRenderer = new TerrainRenderer(projectionMatrix);
		
		skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		voxelRenderer = new VoxelRenderer(projectionMatrix);
		boundingRenderer = new BoundingRenderer(projectionMatrix);
		shadowMapRenderer = new ShadowMapMasterRenderer(scene.getCamera());
		enviroRenderer = new EnvironmentMapRenderer();
		processor = new SceneProcessor();
	}
	
	@Override
	public void render(IScene scene) {
		scene.getEntities().getAll().forEach(this::processEntity);
		renderEditorSceneObjects(scene);
	}

	@Override
	public void render(IScene scene, Vector4f clipPlane) {
		render(scene, clipPlane, false);
	}

	@Override
	public void render(IScene scene, Vector4f clipPlane, boolean isLowDistance) {
		scene.getTerrains().getAll().forEach(this::processTerrain);
		environmentMap = scene.getEnvironmentMap();
		processEntities(scene, isLowDistance, scene.getCamera().isMoved());
		
		if (environmentDynamic) {
			environmentRendered = false;
		}
		
		if (!environmentRendered) {
			enviroRenderer.render(scene, this, scene.getEntities().get("Cuby4"));
			environmentRendered = true;
		}
		
		renderSceneObjects(scene, clipPlane, isLowDistance);
	}

	@Override
	public void renderCubemap(IScene scene, IEntity shinyEntity, ICamera camera) {
		scene.getEntities().delete(shinyEntity.getName());
		processEntities(scene, true, true);
		
		entityRendererManager.render(null, scene.getLights().getAll(), camera, null, null, true);
		terrainRenderer.renderLow(terrains, scene.getLights().getAll(), camera);
		skyboxRenderer.render(camera);
		
		cleanSceneObjects();
	}

	/**
	 * Renders full scene with preloaded objects.
	 * 
	 * @param scene {@link IScene}
	 * @param clipPlane {@link Vector4f} clipping plane
	 * @param isLowDistance 
	 */
	private void renderSceneObjects(IScene scene, Vector4f clipPlane, boolean isLowDistance) {
		prepare();
		
		if (EngineDebug.getBoundingVisibility() != EngineDebug.BOUNDING_NONE)
			boundingRenderer.render(texturedEntities, normalEntities, decorEntities, scene.getCamera());
		
		if (EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY || 
				EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY_TERRAIN)
			OGLUtils.doWiredFrame(true);
		
		Matrix4f shadowMapMatrix = shadowMapRenderer.getToShadowMapMatrix();
		entityRendererManager.render(clipPlane, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix, environmentMap, false);
		
		OGLUtils.doWiredFrame(false);
		
		voxelRenderer.render(scene.getChunks(), clipPlane, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix, scene.getFrustum());
		terrainRenderer.render(terrains, clipPlane, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix);
		skyboxRenderer.render(scene.getCamera());	
		
		cleanSceneObjects();
	}
	
	private void renderEditorSceneObjects(IScene scene) {
		prepare();
		Matrix4f shadowMapMatrix = shadowMapRenderer.getToShadowMapMatrix();
		entityRendererManager.render(EngineSettings.NO_CLIP, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix, null, false);
		cleanSceneObjects();
	}

	@Override
	public void renderShadows(IScene scene) {
		processShadowEntities(scene, scene.getCamera().isMoved());
		shadowMapRenderer.render(decorEntities, terrains, scene.getSun(), scene.getCamera());
		cleanSceneObjects();
	}

	private void prepare() {
		OGLUtils.depthTest(true);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE, 1);
		getShadowMapTexture().bind(6);
	}
	
	private void processEntities(IScene scene, boolean isLowDistance, boolean doRebuild) {
		List<IEntity> frustumEntities = isLowDistance ?
			scene.getFrustumEntities().processLowEntities(scene, projectionLowMatrix, doRebuild) :
			scene.getFrustumEntities().processHighEntities(scene, projectionNormalMatrix, doRebuild);
		frustumEntities.stream().forEach(this::processEntity);
	}
	
	private void processShadowEntities(IScene scene, boolean doRebuild) {
		List<IEntity> frustumEntities = 
				scene.getFrustumEntities().prepareShadowEntities(
						scene, shadowMapRenderer.getToShadowMapMatrix(), doRebuild);
		frustumEntities.stream().forEach(entity -> processEntity(EngineSettings.ENTITY_TYPE_DECORATE, entity));
	}
	
	private void cleanSceneObjects() {
		texturedEntities.clear();
		normalEntities.clear();
		decorEntities.clear();
		terrains.clear();
	}

	@Override
	public void clean() {
		entityRendererManager.clean();
		terrainRenderer.clean();
		shadowMapRenderer.clean();
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
	
	private void processTerrain(ITerrain terrain) {
		processor.processTerrain(terrain, terrains);
	}
	
	private void processEntity(IEntity entity) {
		processEntity(entity.getType(), entity);
	}
	
	private synchronized void processEntity(int type, IEntity entity) {
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
