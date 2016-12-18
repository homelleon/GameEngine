package environmentMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import cameras.Camera;
import cameras.CameraCubeMap;
import entities.Entity;
import models.TexturedModel;
import renderEngine.EntityRenderer;
import renderEngine.TerrainRenderer;
import terrains.Terrain;
import textures.Texture;

public class EnvironmentMapRenderer {
	
	Vector3f center = new Vector3f(0,5,0);
	TerrainRenderer terrainRenderer;
	EntityRenderer entityRenderer;
	
	Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	public EnvironmentMapRenderer(Matrix4f projectionMatrix) {
		this.terrainRenderer = new TerrainRenderer(projectionMatrix);
		this.entityRenderer = new EntityRenderer(projectionMatrix);	
	}
	
	public void render(Texture cubeMap, Collection<Entity> entities, Collection<Terrain> terrains, Camera camera) {
		for(Entity entity : entities) {
			processEntity(entity);
		}

		CameraCubeMap cubeCamera = new CameraCubeMap(center);
		int fbo = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		
		int depthBuffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, cubeMap.size, cubeMap.size);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);
		
		GL11.glViewport(0, 0, cubeMap.size, cubeMap.size);
		
		for(int i=0;i<6;i++) {
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, cubeMap.textureId, 0);
			cubeCamera.switchToFace(i);
			entityRenderer.renderLow(this.entities, camera);
		}
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());	
		
		GL30.glDeleteRenderbuffers(depthBuffer);
		GL30.glDeleteFramebuffers(fbo);
		this.entities.clear();
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
	
	

}
