package renderer.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import core.EngineMain;
import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.camera.Camera;
import object.entity.Entity;
import object.terrain.Terrain;
import primitive.model.Model;
import primitive.texture.Texture;
import primitive.texture.Texture2D;
import renderer.bounding.BoundingRenderer;
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
import scene.Scene;
import shader.Shader;
import shader.ShaderPool;
import tool.math.Matrix4f;
import tool.openGL.OGLUtils;

public class MainRenderer implements IMainRenderer {

	private Matrix4f projectionMatrix;
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

	private Map<Model, List<Entity>> texturedEntities = new HashMap<Model, List<Entity>>();
	private Map<Model, List<Entity>> normalEntities = new HashMap<Model, List<Entity>>();
	private Collection<Terrain> terrains = new ArrayList<Terrain>();

	public MainRenderer(Scene scene) {
		OGLUtils.cullBackFaces(true);
		projectionMatrix = new Matrix4f().makeProjectionMatrix(EngineSettings.NEAR_PLANE, EngineSettings.FAR_PLANE, EngineSettings.FOV);
		
		entityRendererManager = new EntityRendererManager()
			.addPair(new TexturedEntityRenderer(projectionMatrix), texturedEntities)
			.addPair(new NormalEntityRenderer(projectionMatrix), normalEntities);
		
		terrainRenderer = new TerrainRenderer(projectionMatrix);
		
		skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		voxelRenderer = new VoxelRenderer(projectionMatrix);
		boundingRenderer = new BoundingRenderer(projectionMatrix);
		shadowMapRenderer = new ShadowMapMasterRenderer(scene.getCamera());
		enviroRenderer = new EnvironmentMapRenderer();
		processor = new SceneProcessor();
	}
	
	@Override
	public void render(Scene scene) {
		scene.getEntities().getAll().forEach(this::processEntity);
		renderEditorSceneObjects(scene);
	}

	@Override
	public void render(Scene scene, Vector4f clipPlane) {
		scene.getTerrains().getAll().forEach(this::processTerrain);
		environmentMap = scene.getEnvironmentMap();
		processEntities(scene, scene.getCamera().isMoved());
		
		if (environmentDynamic) {
			environmentRendered = false;
		}
		
		if (!environmentRendered) {
			enviroRenderer.render(scene, this, scene.getEntities().get("Cuby4"));
			environmentRendered = true;
		}
		
		renderSceneObjects(scene, clipPlane);
	}

	@Override
	public void renderCubemap(Scene scene, Entity shinyEntity, Camera camera) {
		scene.getEntities().delete(shinyEntity.getName());
		processEntities(scene, true);
		
		entityRendererManager.render(null, scene.getLights().getAll(), camera, null, null);
		terrainRenderer.render(terrains, scene.getLights().getAll(), camera);
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
	private void renderSceneObjects(Scene scene, Vector4f clipPlane) {
		prepare();
		
		if (EngineDebug.getBoundingVisibility() != EngineDebug.BOUNDING_NONE)
			boundingRenderer.render(texturedEntities, normalEntities, scene.getCamera());
		
		if (EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY || 
				EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY_TERRAIN)
			OGLUtils.doWiredFrame(true);
		
		Matrix4f shadowMapMatrix = shadowMapRenderer.getToShadowMapMatrix();
		entityRendererManager.render(clipPlane, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix, environmentMap);
		
		OGLUtils.doWiredFrame(false);
		
		voxelRenderer.render(scene.getChunks(), clipPlane, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix, scene.getFrustum());
		terrainRenderer.render(terrains, clipPlane, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix);
		skyboxRenderer.render(scene.getCamera());	
		
		cleanSceneObjects();
	}
	
	private void renderEditorSceneObjects(Scene scene) {
		prepare();
		Matrix4f shadowMapMatrix = shadowMapRenderer.getToShadowMapMatrix();
		entityRendererManager.render(EngineSettings.NO_CLIP, scene.getLights().getAll(), scene.getCamera(), 
				shadowMapMatrix, null);
		cleanSceneObjects();
	}

	@Override
	public void renderShadows(Scene scene) {
		processShadowEntities(scene, scene.getCamera().isMoved());
		shadowMapRenderer.render(texturedEntities, normalEntities, terrains, scene.getSun(), scene.getCamera());
		cleanSceneObjects();
	}

	private void prepare() {
		OGLUtils.depthTest(true);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE, 1);
		getShadowMapTexture().bind(6);
	}
	
	private void processEntities(Scene scene, boolean doRebuild) {
		List<Entity> frustumEntities = scene.getFrustumEntities().processEntities(scene, projectionMatrix, doRebuild);
		frustumEntities.stream().forEach(this::processEntity);
	}
	
	private void processShadowEntities(Scene scene, boolean doRebuild) {
		List<Entity> frustumEntities = 
				scene.getFrustumEntities().prepareShadowEntities(
						scene, shadowMapRenderer.getToShadowMapMatrix(), doRebuild);
		frustumEntities.stream().forEach(this::processEntity);
	}
	
	private void cleanSceneObjects() {
		texturedEntities.clear();
		normalEntities.clear();
		terrains.clear();
	}

	@Override
	public void clean() {
		entityRendererManager.clean();
		terrainRenderer.clean();
		shadowMapRenderer.clean();
		ShaderPool.getInstance().clean();
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	private Texture2D getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}
	
	private void processTerrain(Terrain terrain) {
		processor.processTerrain(terrain, terrains);
	}
	
	private void processEntity(Entity entity) {
		processEntity(entity.getShader().getType(), entity);
	}
	
	private synchronized void processEntity(int type, Entity entity) {
		switch(type) {
		 	case Shader.ENTITY:
		 		processor.processEntity(entity, texturedEntities);
		 		break;
		 	case Shader.NORMAL_ENTITY:
		 		processor.processEntity(entity, normalEntities);
		 		break;
		}
	}

}
