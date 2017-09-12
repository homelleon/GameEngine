package renderer.object.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import manager.voxel.IChunkManager;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.ILight;
import object.model.textured.TexturedModel;
import object.scene.IScene;
import object.shadow.renderer.ShadowMapMasterRenderer;
import object.terrain.terrain.ITerrain;
import object.texture.Texture;
import renderer.object.bounding.BoundingRenderer;
import renderer.object.entity.DecorEntityRenderer;
import renderer.object.entity.NormalEntityRenderer;
import renderer.object.entity.TexturedEntityRenderer;
import renderer.object.environment.EnvironmentMapRenderer;
import renderer.object.skybox.SkyboxRenderer;
import renderer.object.terrain.TerrainRenderer;
import renderer.object.voxel.VoxelRenderer;
import renderer.processor.ISceneProcessor;
import renderer.processor.SceneProcessor;
import renderer.viewCulling.frustum.Frustum;
import tool.openGL.OGLUtils;

public class MainRenderer implements IMainRenderer {

	private Matrix4f projectionMatrix;
	private Matrix4f normalDistProjectionMatrix;
	private Matrix4f lowDistProjectionMatrix;
	private TexturedEntityRenderer texturedEntityRenderer;
	private NormalEntityRenderer normalEntityRenderer;
	private DecorEntityRenderer decorEntityRenderer;
	private TerrainRenderer terrainRenderer;	
	private SkyboxRenderer skyboxRenderer;
	private VoxelRenderer voxelRenderer;
	private BoundingRenderer boundingRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;
	private EnvironmentMapRenderer enviroRenderer;

	private ISceneProcessor processor;

	private Texture environmentMap;
	private Frustum frustum = new Frustum();

	private boolean terrainWiredFrame = false;
	private boolean entitiyWiredFrame = false;
	private boolean environmentDynamic = false;
	private boolean environmentRendered = false;

	private Map<TexturedModel, List<IEntity>> texturedEntities = new HashMap<TexturedModel, List<IEntity>>();
	private Map<TexturedModel, List<IEntity>> normalEntities = new HashMap<TexturedModel, List<IEntity>>();
	private Map<TexturedModel, List<IEntity>> decorEntities = new HashMap<TexturedModel, List<IEntity>>();
	private Collection<ITerrain> terrains = new ArrayList<ITerrain>();

	public MainRenderer(IScene scene) {
		OGLUtils.cullBackFaces(true);
		createProjectionMatrix();
		createLowDistProjectionMatrix();
		projectionMatrix = normalDistProjectionMatrix;
		this.texturedEntityRenderer = new TexturedEntityRenderer(projectionMatrix);
		this.normalEntityRenderer = new NormalEntityRenderer(projectionMatrix);
		this.decorEntityRenderer = new DecorEntityRenderer(projectionMatrix);
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
		render(scene.getChunks(), scene.getLights().getAll(), scene.getCamera(), clipPlane, isLowDistance);
	}

	private void render(IChunkManager chunkManager, Collection<ILight> lights, ICamera camera,
			Vector4f clipPlane, boolean isLowDistance) {
		prepare();
		checkWiredFrameOn(entitiyWiredFrame);
		if (EngineDebug.boundingMode != EngineDebug.BOUNDING_NONE) {
			boundingRenderer.render(texturedEntities, normalEntities, camera);
		}
		texturedEntityRenderer.renderHigh(texturedEntities, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix(),
				environmentMap);
		normalEntityRenderer.render(normalEntities, clipPlane, lights, camera,
				shadowMapRenderer.getToShadowMapSpaceMatrix());
		decorEntityRenderer.renderHigh(decorEntities, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix());
		checkWiredFrameOff(entitiyWiredFrame);

		checkWiredFrameOn(terrainWiredFrame);
		voxelRenderer.render(chunkManager, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix(),
				frustum);
		terrainRenderer.render(terrains, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix());
		checkWiredFrameOff(terrainWiredFrame);

		skyboxRenderer.render(camera);

		terrains.clear();
		texturedEntities.clear();
		normalEntities.clear();
		decorEntities.clear();
	}

	@Override
	public void renderLowQualityScene(Map<TexturedModel, List<IEntity>> entities, Collection<ITerrain> terrains,
			Collection<ILight> lights, ICamera camera) {
		texturedEntityRenderer.renderLow(entities, lights, camera);
		terrainRenderer.renderLow(terrains, lights, camera);
		skyboxRenderer.render(camera);
	}

	@Override
	public void renderShadowMap(IScene scene) {
		this.frustum.extractFrustum(scene.getCamera(), this.getProjectionMatrix());
		scene.getEntities().updateWithFrustum(frustum, scene.getCamera())
			.forEach((type, list) -> 
				list.parallelStream().forEach(entity -> processShadowEntityByType(type, entity)));
		shadowMapRenderer.render(texturedEntities, terrains, normalEntities, scene.getSun(), scene.getCamera());
		texturedEntities.clear();
		normalEntities.clear();
		terrains.clear();
	}

	private int getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}

	@Override
	public void clean() {
		texturedEntityRenderer.clean();
		normalEntityRenderer.clean();
		terrainRenderer.clean();
		shadowMapRenderer.clean();
	}

	private void prepare() {
		OGLUtils.depthTest(true);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
	}

	private void createProjectionMatrix() {
		normalDistProjectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(EngineSettings.FOV / 2f)) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = EngineSettings.FAR_PLANE - EngineSettings.NEAR_PLANE;

		normalDistProjectionMatrix.m00 = x_scale;
		normalDistProjectionMatrix.m11 = y_scale;
		normalDistProjectionMatrix.m22 = -((EngineSettings.FAR_PLANE + EngineSettings.NEAR_PLANE) / frustrum_length);
		normalDistProjectionMatrix.m23 = -1;
		normalDistProjectionMatrix.m32 = -((2 * EngineSettings.NEAR_PLANE * EngineSettings.FAR_PLANE)
				/ frustrum_length);
		normalDistProjectionMatrix.m33 = 0;
	}

	private void createLowDistProjectionMatrix() {
		lowDistProjectionMatrix = new Matrix4f();
		float farPlane = 100;
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(EngineSettings.FOV / 2f)) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = farPlane - EngineSettings.NEAR_PLANE;

		lowDistProjectionMatrix.m00 = x_scale;
		lowDistProjectionMatrix.m11 = y_scale;
		lowDistProjectionMatrix.m22 = -((farPlane + EngineSettings.NEAR_PLANE) / frustrum_length);
		lowDistProjectionMatrix.m23 = -1;
		lowDistProjectionMatrix.m32 = -((2 * EngineSettings.NEAR_PLANE * farPlane) / frustrum_length);
		lowDistProjectionMatrix.m33 = 0;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	@Override
	public TexturedEntityRenderer getEntityRenderer() {
		return this.texturedEntityRenderer;
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

	private synchronized void processShadowEntityByType(int type, IEntity entity) {
		switch(type) {
			case EngineSettings.ENTITY_TYPE_SIMPLE:
		 		processor.processShadowEntity(entity, texturedEntities);
		 		break;
			case EngineSettings.ENTITY_TYPE_DECORATE:
		 		processor.processShadowEntity(entity, texturedEntities);
		 		break;
		 	case EngineSettings.ENTITY_TYPE_NORMAL:
		 		processor.processShadowNormalMapEntity(entity, normalEntities);
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
