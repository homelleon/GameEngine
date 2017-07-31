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
import object.camera.CameraInterface;
import object.entity.entity.Entity;
import object.light.Light;
import object.model.TexturedModel;
import object.scene.scene.SceneInterface;
import object.shadow.renderer.ShadowMapMasterRenderer;
import object.terrain.terrain.TerrainInterface;
import object.texture.Texture;
import object.voxel.manager.ChunkManagerInterface;
import renderer.loader.Loader;
import renderer.object.bounding.BoundingRenderer;
import renderer.object.entity.EntityRenderer;
import renderer.object.entity.NormalMappingRenderer;
import renderer.object.environment.EnvironmentMapRenderer;
import renderer.object.skybox.SkyboxRenderer;
import renderer.object.terrain.TerrainRenderer;
import renderer.object.voxel.VoxelRenderer;
import renderer.processor.SceneProcessor;
import renderer.processor.SceneProcessorInterface;
import renderer.viewCulling.frustum.Frustum;
import tool.openGL.OGLUtils;

public class MainRenderer implements MainRendererInterface {

	private Matrix4f projectionMatrix;
	private Matrix4f normalDistProjectionMatrix;
	private Matrix4f lowDistProjectionMatrix;
	private EntityRenderer entityRenderer;
	private TerrainRenderer terrainRenderer;
	private NormalMappingRenderer normalMapRenderer;
	private SkyboxRenderer skyboxRenderer;
	private VoxelRenderer voxelRenderer;
	private BoundingRenderer boundingRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;
	private EnvironmentMapRenderer enviroRenderer;

	private SceneProcessorInterface processor;

	private Texture environmentMap;
	private Frustum frustum = new Frustum();

	private boolean terrainWiredFrame = false;
	private boolean entitiyWiredFrame = false;
	private boolean environmentDinamic = false;
	private boolean environmentRendered = false;

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Entity>> normalMapEntities = new HashMap<TexturedModel, List<Entity>>();
	private Collection<TerrainInterface> terrains = new ArrayList<TerrainInterface>();

	public MainRenderer(CameraInterface camera) {
		OGLUtils.cullBackFaces(true);
		createProjectionMatrix();
		createLowDistProjectionMatrix();
		projectionMatrix = normalDistProjectionMatrix;
		this.entityRenderer = new EntityRenderer(projectionMatrix);
		this.terrainRenderer = new TerrainRenderer(projectionMatrix);
		this.skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		this.normalMapRenderer = new NormalMappingRenderer(projectionMatrix);
		this.voxelRenderer = new VoxelRenderer(projectionMatrix);
		this.boundingRenderer = new BoundingRenderer(projectionMatrix);
		this.shadowMapRenderer = new ShadowMapMasterRenderer(camera);
		this.enviroRenderer = new EnvironmentMapRenderer();
		this.processor = new SceneProcessor();
	}

	@Override
	public void renderScene(SceneInterface scene, Vector4f clipPlane) {
		renderScene(scene, clipPlane, false);
	}

	@Override
	public void renderScene(SceneInterface scene, Vector4f clipPlane, boolean isLowDistance) {
		this.frustum.extractFrustum(scene.getCamera(), projectionMatrix);
		this.environmentMap = scene.getEnvironmentMap();
		for (TerrainInterface terrain : scene.getTerrains().getAll()) {
			processor.processTerrain(terrain, terrains);
		}

		for (Entity entity : scene.getEntities().getAll()) {
			if (entity.getType() == EngineSettings.ENTITY_TYPE_SIMPLE) {
				processor.processEntity(entity, entities, frustum);
			} else if (entity.getType() == EngineSettings.ENTITY_TYPE_NORMAL) {
				processor.processNormalMapEntity(entity, normalMapEntities, frustum);
			}
		}
		if (this.environmentDinamic) {
			environmentRendered = false;
		}
		if (!environmentRendered) {
			enviroRenderer.render(scene, this, scene.getEntities().getByName("Cuby4"));
			this.environmentRendered = true;
		}
		render(scene.getChunks(), scene.getLights().getAll(), scene.getCamera(), clipPlane, isLowDistance);

	}

	private void render(ChunkManagerInterface chunkManager, Collection<Light> lights, CameraInterface camera,
			Vector4f clipPlane, boolean isLowDistance) {
		prepare();
		checkWiredFrameOn(entitiyWiredFrame);
		entityRenderer.render(entities, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix(),
				environmentMap);
		if (EngineDebug.boundingMode != EngineDebug.BOUNDING_NONE) {
			boundingRenderer.render(entities, normalMapEntities, camera);
		}
		normalMapRenderer.render(normalMapEntities, clipPlane, lights, camera,
				shadowMapRenderer.getToShadowMapSpaceMatrix());
		checkWiredFrameOff(entitiyWiredFrame);

		checkWiredFrameOn(terrainWiredFrame);
		if (!isLowDistance) {
			voxelRenderer.render(chunkManager, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix(),
					frustum);
		}
		terrainRenderer.render(terrains, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix());
		checkWiredFrameOff(terrainWiredFrame);

		skyboxRenderer.render(camera);

		terrains.clear();
		entities.clear();
		normalMapEntities.clear();
	}

	@Override
	public void renderLowQualityScene(Map<TexturedModel, List<Entity>> entities, Collection<TerrainInterface> terrains,
			Collection<Light> lights, CameraInterface camera) {
		entityRenderer.renderLow(entities, lights, camera);
		terrainRenderer.renderLow(terrains, lights, camera);
		skyboxRenderer.render(camera);
	}

	@Override
	public void renderShadowMap(SceneInterface scene) {
		for (Entity entity : scene.getEntities().getAll()) {
			if (entity.getType() == EngineSettings.ENTITY_TYPE_SIMPLE) {
				processor.processShadowEntity(entity, entities, frustum);
			} else if (entity.getType() == EngineSettings.ENTITY_TYPE_NORMAL) {
				processor.processShadowNormalMapEntity(entity, normalMapEntities, frustum);
			}
		}

		shadowMapRenderer.render(entities, terrains, normalMapEntities, scene.getSun(), scene.getCamera());
		entities.clear();
		normalMapEntities.clear();
		terrains.clear();
	}

	private int getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}

	@Override
	public void cleanUp() {
		entityRenderer.cleanUp();
		normalMapRenderer.cleanUp();
		terrainRenderer.cleanUp();
		shadowMapRenderer.cleanUp();
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
	public Collection<Entity> createFrustumEntities(SceneInterface scene) {
		frustum.extractFrustum(scene.getCamera(), projectionMatrix);
		List<Entity> frustumEntities = new ArrayList<Entity>();
		for (Entity entity : scene.getEntities().getAll()) {
			if (frustum.sphereInFrustum(entity.getPosition(), entity.getSphereRadius())) {
				frustumEntities.add(entity);
			}
		}
		return frustumEntities;
	}

	@Override
	public EntityRenderer getEntityRenderer() {
		return this.entityRenderer;
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

	private void checkWiredFrameOn(boolean value) {
		if (value) {
			OGLUtils.doWiredFrame(true);
		}
	}

	private void checkWiredFrameOff(boolean value) {
		if (value) {
			OGLUtils.doWiredFrame(false);
		}
	}

}
