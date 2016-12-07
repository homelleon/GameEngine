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
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Entity;
import entities.EntityShader;
import entities.Light;
import models.TexturedModel;
import normalMappingRenderer.NormalMappingRenderer;
import scene.ES;
import shadows.ShadowMapMasterRenderer;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import terrains.TerrainShader;
import toolbox.OGLUtils;

public class MasterRenderer {
		
	private Matrix4f projectionMatrix;
	
	private EntityShader shader = new EntityShader();
	private EntityRenderer entityRenderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private NormalMappingRenderer normalMapRenderer;
	
	private boolean terrainWiredFrame = false;
	private boolean entitiyWiredFrame = false;
	
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Entity>> normalMapEntities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	private SkyboxRenderer skyboxRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;
	
	public MasterRenderer(Loader loader, Camera camera) {
		OGLUtils.cullBackFaces(true);
		createProjectionMatrix();
		this.entityRenderer = new EntityRenderer(shader,projectionMatrix);
		this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		this.skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		this.normalMapRenderer = new NormalMappingRenderer(projectionMatrix);
		this.shadowMapRenderer = new ShadowMapMasterRenderer(camera);
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public void render(Collection<Light> lights, Camera camera, Vector4f clipPlane) {
		prepare();
		checkWiredFrameOn(entitiyWiredFrame);
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(ES.DISPLAY_RED, ES.DISPLAY_GREEN, ES.DISPLAY_BLUE);
		shader.loadFogDensity(ES.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		entityRenderer.render(entities, shadowMapRenderer.getToShadowMapSpaceMatrix());
		shader.stop();
		normalMapRenderer.render(normalMapEntities, clipPlane, lights, camera, shadowMapRenderer.getToShadowMapSpaceMatrix());
		checkWiredFrameOff(entitiyWiredFrame);
		
		checkWiredFrameOn(terrainWiredFrame);
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadSkyColour(ES.DISPLAY_RED, ES.DISPLAY_GREEN, ES.DISPLAY_BLUE);
		terrainShader.loadFogDensity(ES.FOG_DENSITY);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains, shadowMapRenderer.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		checkWiredFrameOff(terrainWiredFrame);
		skyboxRenderer.render(camera, ES.DISPLAY_RED, ES.DISPLAY_GREEN, ES.DISPLAY_BLUE);
		terrains.clear();
		entities.clear();
		normalMapEntities.clear();
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
	
	//public void renderScene(Collection<Entity> entities, Collection<Entity> normalEntities, Collection<Terrain> terrains, List<Light> lights,
	//	Camera camera, Vector4f clipPlane) {
	//	for (Terrain terrain : terrains) {
	//		processTerrain(terrain);
	//	}
	//	for (Entity entity : entities) {
	//		processEntity(entity);
	//	}
//		for(Entity entity : normalEntities) {
	//		processNormalMapEntity(entity);
	//	}
	//	render(lights, camera, clipPlane);
//	}
	
	public void renderScene(Collection<Entity> entities, Collection<Terrain> terrains, Collection<Light> lights,
			Camera camera, Vector4f clipPlane) {
			for (Terrain terrain : terrains) {
				processTerrain(terrain);
			}
			for (Entity entity : entities) {
				if(entity.getType() == ES.ENTITY_TYPE_SIMPLE) {
					processEntity(entity);
				} else if(entity.getType() == ES.ENTITY_TYPE_NORMAL) {
					processNormalMapEntity(entity);
				}					
			}
			render(lights, camera, clipPlane);
		}
	
	public void renderShadowMap(Collection<Entity> entityList, Light sun, 
			Camera camera) {
		for(Entity entity : entityList) {
			if(entity.getType() == ES.ENTITY_TYPE_SIMPLE) {
				processEntity(entity);
			} else if(entity.getType() == ES.ENTITY_TYPE_NORMAL) {
				processNormalMapEntity(entity);
			}
		}
		
		shadowMapRenderer.render(entities, normalMapEntities, sun, camera);
		entities.clear();
		normalMapEntities.clear();
	}
	
	public int getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}
	
	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
		normalMapRenderer.cleanUp();
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
