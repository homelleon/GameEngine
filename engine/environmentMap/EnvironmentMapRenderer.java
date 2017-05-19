package environmentMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import cameras.CameraInterface;
import cameras.CameraCubeMap;
import entities.EntityInterface;
import models.TexturedModel;
import renderEngine.MasterRenderer;
import scene.SceneInterface;

public class EnvironmentMapRenderer {
	
	Map<TexturedModel, List<EntityInterface>> entities = new HashMap<TexturedModel, List<EntityInterface>>();

	public void render(SceneInterface scene, MasterRenderer renderer, EntityInterface shinyEntity) {
		CameraInterface cubeCamera = new CameraCubeMap(shinyEntity.getPosition());
		int fbo = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		
		int depthBuffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, scene.getEnvironmentMap().size, scene.getEnvironmentMap().size);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);
		
		GL11.glViewport(0, 0, scene.getEnvironmentMap().size, scene.getEnvironmentMap().size);
		for(EntityInterface entity : scene.getEntities().getAll()) {
			if(entity != shinyEntity) {
				processEntity(entity);
			}
		}
		
		for(int i=0;i<6;i++) {
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, scene.getEnvironmentMap().textureId, 0);
			cubeCamera.switchToFace(i);
		
			renderer.renderLowQualityScene(entities, scene.getTerrains().getAll(), scene.getLights().getAll(), cubeCamera);
		}
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());	
		
		GL30.glDeleteRenderbuffers(depthBuffer);
		GL30.glDeleteFramebuffers(fbo);
		entities.clear();
	}
	
	public void processEntity(EntityInterface entity) {
		TexturedModel entityModel = entity.getModel();
		List<EntityInterface> batch = entities.get(entityModel);
		if(batch!=null) {
			batch.add(entity);	
		}else{
			List<EntityInterface> newBatch = new ArrayList<EntityInterface>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);		
		}
	}
		

}
