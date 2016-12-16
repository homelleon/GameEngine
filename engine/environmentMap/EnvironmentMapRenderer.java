package environmentMap;

import java.util.Collection;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import cameras.CameraCubeMap;
import entities.Entity;
import renderEngine.EntityRenderer;
import renderEngine.TerrainRenderer;
import terrains.Terrain;
import textures.Texture;

public class EnvironmentMapRenderer {
	
	Vector3f center = new Vector3f(0,0,0);
	
	public EnvironmentMapRenderer(Matrix4f projectionMatrix) {
		TerrainRenderer terrainRenderer = new TerrainRenderer(projectionMatrix);
		EntityRenderer entityRenderer = new EntityRenderer(projectionMatrix);	
	}
	
	public void render(Texture cubeMap, Collection<Entity> entities, Collection<Terrain> terrains) {
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
			//render
		}
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());		
	}

}
