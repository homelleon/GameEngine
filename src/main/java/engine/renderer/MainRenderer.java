package renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import core.EngineDebug;
import core.EngineMain;
import core.settings.EngineSettings;
import object.entity.Entity;
import object.terrain.Terrain;
import primitive.model.Model;
import primitive.texture.Texture;
import primitive.texture.Texture2D;
import renderer.entity.EntityRendererManager;
import renderer.entity.NormalEntityRenderer;
import renderer.entity.TexturedEntityRenderer;
import renderer.processor.SceneProcessor;
import renderer.processor.SceneProcessorImpl;
import renderer.shadow.ShadowMapRenderer;
import scene.Scene;
import shader.Shader;
import shader.ShaderPool;
import tool.GraphicUtils;
import tool.math.Matrix4f;

public class MainRenderer {

	private TerrainRenderer terrainRenderer;
	private SkyboxRenderer skyboxRenderer;
	private VoxelRenderer voxelRenderer;
	private BoundingRenderer boundingRenderer;
	private ShadowMapRenderer shadowMapRenderer;
	private EnvironmentMapRenderer enviroRenderer;
	
	EntityRendererManager entityRendererManager;
	
	private SceneProcessor processor;

	private Texture environmentMap;

	private boolean environmentDynamic = false;
	private boolean environmentRendered = false;

	private Map<Model, List<Entity>> texturedEntities = new HashMap<Model, List<Entity>>();
	private Map<Model, List<Entity>> normalEntities = new HashMap<Model, List<Entity>>();
	private Collection<Terrain> terrains = new ArrayList<Terrain>();

	public MainRenderer(Scene scene) {
		GraphicUtils.cullBackFaces(true);
		Matrix4f projectionMatrix = scene.getCamera().getProjectionMatrix();
		
		entityRendererManager = new EntityRendererManager()
			.addPair(new TexturedEntityRenderer(projectionMatrix), texturedEntities)
			.addPair(new NormalEntityRenderer(projectionMatrix), normalEntities);
		
		terrainRenderer = new TerrainRenderer(projectionMatrix);
		
		skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		voxelRenderer = new VoxelRenderer(projectionMatrix);
		boundingRenderer = new BoundingRenderer(projectionMatrix);
		shadowMapRenderer = new ShadowMapRenderer(scene.getCamera());
		enviroRenderer = new EnvironmentMapRenderer();
		processor = new SceneProcessorImpl();
	}
	
	public void render(Scene scene) {
		scene.getEntities().getAll().forEach(this::processEntity);
		renderEditorSceneObjects(scene);
	}

	public void render(Scene scene, Vector4f clipPlane) {
		scene.getTerrains().getAll().forEach(this::processTerrain);
		environmentMap = scene.getEnvironmentMap();
		processEntities(scene, scene.getCamera().isMoved());
		
		if (environmentDynamic)
			environmentRendered = false;
		
		if (!environmentRendered) {
			enviroRenderer.render(scene, this, scene.getEntities().get("Cuby4"));
			environmentRendered = true;
		}
		
		renderSceneObjects(scene, clipPlane);
	}

	public void renderCubemap(Scene scene, Entity shinyEntity) {
		scene.getEntities().delete(shinyEntity.getName());
		processEntities(scene, true);
		entityRendererManager.render(scene, null, null, null);
		terrainRenderer.render(terrains, scene);
		skyboxRenderer.render(scene);
		forceDrawingAndCleanUp();
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
			GraphicUtils.doWiredFrame(true);
		
		Matrix4f shadowMapMatrix = shadowMapRenderer.getToShadowMapMatrix();
		entityRendererManager.render(scene, clipPlane, shadowMapMatrix, environmentMap);
		
		GraphicUtils.doWiredFrame(false);
		
		voxelRenderer.render(scene, clipPlane, shadowMapMatrix);
		terrainRenderer.render(terrains, scene, clipPlane, shadowMapMatrix);
		skyboxRenderer.render(scene);
		forceDrawingAndCleanUp();
	}
	
	private void renderEditorSceneObjects(Scene scene) {
		prepare();
		Matrix4f shadowMapMatrix = shadowMapRenderer.getToShadowMapMatrix();
		entityRendererManager.render(scene,	EngineSettings.NO_CLIP, shadowMapMatrix, null);
		forceDrawingAndCleanUp();
	}

	public void renderShadows(Scene scene) {
		processShadowEntities(scene);
		shadowMapRenderer.render(texturedEntities, normalEntities, terrains, scene);
		forceDrawingAndCleanUp();
	}

	private void prepare() {
		GraphicUtils.depthTest(true);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE, 1);
		getShadowMapTexture().bind(6);
	}
	
	private void processEntities(Scene scene, boolean doRebuild) {
		List<Entity> frustumEntities = scene.getFrustumEntities().getUpdatedEntities(scene, doRebuild);
		frustumEntities.stream().forEach(this::processEntity);
	}
	
	private void processShadowEntities(Scene scene) {
		List<Entity> frustumEntities = 
				scene.getFrustumEntities().getUpdatedShadowEntities(
						scene, shadowMapRenderer.getToShadowMapMatrix(), scene.getCamera().isMoved());
		frustumEntities.stream().forEach(this::processEntity);
	}
	
	private void forceDrawingAndCleanUp() {
		GraphicUtils.forceDrawingAndWait();
		texturedEntities.clear();
		normalEntities.clear();
		terrains.clear();
	}

	public void clean() {
		entityRendererManager.clean();
		terrainRenderer.clean();
		shadowMapRenderer.clean();
		ShaderPool.INSTANCE.clean();
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
	
	private void processEntity(int type, Entity entity) {
		switch (type) {
		 	case Shader.ENTITY:
		 		processor.processEntity(entity, texturedEntities);
		 		break;
		 	case Shader.NORMAL_ENTITY:
		 		processor.processEntity(entity, normalEntities);
		 		break;
		}
	}

}
