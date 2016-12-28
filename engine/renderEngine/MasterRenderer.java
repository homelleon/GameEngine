package renderEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import normalMappingRenderer.NormalMappingRenderer;
import scene.ES;
import scene.Scene;
import shadows.ShadowMapMasterRenderer;
import terrains.Terrain;
import textures.Texture;
import toolbox.OGLUtils;
import voxels.ChunkManager;

public class MasterRenderer {
		
	private Matrix4f projectionMatrix;
	
	private ChunkManager chunker;
	
	private EntityRenderer entityRenderer;	
	private TerrainRenderer terrainRenderer;	
	private NormalMappingRenderer normalMapRenderer;
	private SkyboxRenderer skyboxRenderer;
	private VoxelRenderer voxelRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;
	private Texture environmentMap;
	
	private boolean terrainWiredFrame = false;
	private boolean entitiyWiredFrame = false;
	
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Entity>> normalMapEntities = new HashMap<TexturedModel, List<Entity>>();
	private Collection<Terrain> terrains = new ArrayList<Terrain>(); 
		
	public MasterRenderer(Loader loader, Camera camera) {
		OGLUtils.cullBackFaces(true);
		createProjectionMatrix();
		this.entityRenderer = new EntityRenderer(projectionMatrix);
		this.terrainRenderer = new TerrainRenderer(projectionMatrix);
		this.skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		this.normalMapRenderer = new NormalMappingRenderer(projectionMatrix);
		this.voxelRenderer = new VoxelRenderer(loader, projectionMatrix);
		this.shadowMapRenderer = new ShadowMapMasterRenderer(camera);
		this.chunker = new ChunkManager(3, new Vector3f(0,0,0));
		chunker.getChunk(0).getBlock(0, 0, 0).setIsActive(true);
		chunker.getChunk(0).getBlock(0, 1, 0).setIsActive(true);
		chunker.getChunk(0).getBlock(1, 0, 0).setIsActive(true);
		chunker.getChunk(0).getBlock(0, 0, 1).setIsActive(true);
		chunker.getChunk(0).getBlock(0, 0, 2).setIsActive(true);
		chunker.getChunk(1).getBlock(0, 0, 0).setIsActive(true);
		chunker.getChunk(1).getBlock(0, 1, 0).setIsActive(true);
		chunker.getChunk(1).getBlock(0, 0, 1).setIsActive(true);
		chunker.getChunk(2).getBlock(0, 0, 1).setIsActive(true);
		chunker.getChunk(2).getBlock(0, 2, 1).setIsActive(true);
		chunker.getChunk(3).getBlock(0, 0, 0).setIsActive(true);
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public void render(Collection<Light> lights, Camera camera, Vector4f clipPlane) {	
		prepare();	
		checkWiredFrameOn(entitiyWiredFrame);
		entityRenderer.render(entities, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix(), environmentMap);
		normalMapRenderer.render(normalMapEntities, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix());
		checkWiredFrameOff(entitiyWiredFrame);
		
		checkWiredFrameOn(terrainWiredFrame);
		voxelRenderer.render(chunker, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix());
		terrainRenderer.render(terrains, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix());
		checkWiredFrameOff(terrainWiredFrame);
		
		skyboxRenderer.render(camera);
			
		terrains.clear();
		entities.clear();
		normalMapEntities.clear();
	}
	
	public void rendereLowQualityScene(Map<TexturedModel, List<Entity>> entities, Collection<Terrain> terrains, Collection<Light> lights, Camera camera) {
		entityRenderer.renderLow(entities, lights, camera);
		terrainRenderer.renderLow(terrains, lights, camera);
		skyboxRenderer.render(camera);
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch!=null) {
			batch.add(entity);	
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);		
		}
	}
	
	public void processNormalMapEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = normalMapEntities.get(entityModel);
		if(batch!=null) {
			batch.add(entity);	
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			normalMapEntities.put(entityModel, newBatch);	
		}
	}
	
	
	public void renderScene(Scene scene, Vector4f clipPlane) {
			this.environmentMap = scene.getEnvironmentMap();
			for (Terrain terrain : scene.getTerrains().values()) {
				processTerrain(terrain);
			}
			
			for (Entity entity : scene.getEntities().values()) {
				if(entity.getType() == ES.ENTITY_TYPE_SIMPLE) {
					processEntity(entity);
				} else if(entity.getType() == ES.ENTITY_TYPE_NORMAL) {
					processNormalMapEntity(entity);
				}					
			}
		
			render(scene.getLights().values(), scene.getCamera(), clipPlane);
		}
	
	public void renderShadowMap(Scene scene) {
		for(Entity entity : scene.getEntities().values()) {
			if(entity.getType() == ES.ENTITY_TYPE_SIMPLE) {
				processEntity(entity);
			} else if(entity.getType() == ES.ENTITY_TYPE_NORMAL) {
				processNormalMapEntity(entity);
			}
		}
		
		shadowMapRenderer.render(entities, terrains, normalMapEntities, scene.getSun(), scene.getCamera());
		entities.clear();
		normalMapEntities.clear();
		terrains.clear();
	}
	
	public int getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}
	
	public void cleanUp() {		
		entityRenderer.cleanUp();
		normalMapRenderer.cleanUp();
		terrainRenderer.cleanUp();
		shadowMapRenderer.cleanUp();
	}
	
	public void prepare() {
		OGLUtils.depthTest(true);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(ES.DISPLAY_RED, ES.DISPLAY_GREEN, ES.DISPLAY_BLUE, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());		
	}
	
	private void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth()/(float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(ES.FOV / 2f)) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = ES.FAR_PLANE - ES.NEAR_PLANE;
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((ES.FAR_PLANE + ES.NEAR_PLANE) / frustrum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * ES.NEAR_PLANE * ES.FAR_PLANE) / frustrum_length);
		projectionMatrix.m33 = 0;
	}
	
	public EntityRenderer getEntityRenderer() {
		return this.entityRenderer;
	}
	
	
	
	public void setEntityWiredFrame(boolean entitiyWiredFrame) {
		this.entitiyWiredFrame = entitiyWiredFrame;
	}
	
	

	public void setTerrainWiredFrame(boolean terrainWiredFrame) {
		this.terrainWiredFrame = terrainWiredFrame;
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
