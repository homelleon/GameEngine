package renderer.object.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import object.camera.CubeMapCamera;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.model.textured.TexturedModel;
import object.scene.IScene;
import renderer.object.main.MainRenderer;

public class EnvironmentMapRenderer {

	Map<TexturedModel, List<IEntity>> entities = new HashMap<TexturedModel, List<IEntity>>();

	public void render(IScene scene, MainRenderer renderer, IEntity shinyEntity) {
		ICamera cubeCamera = new CubeMapCamera("shinyCubeMap", shinyEntity.getPosition());
		int fbo = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

		int depthBuffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, scene.getEnvironmentMap().size,
				scene.getEnvironmentMap().size);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER,
				depthBuffer);

		GL11.glViewport(0, 0, scene.getEnvironmentMap().size, scene.getEnvironmentMap().size);
		scene.getEntities().getAll().stream()
			 .filter(entity -> entity != shinyEntity)
			 .forEach(entity -> processEntity(entity));

		for (int i = 0; i < 6; i++) {
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,
					GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, scene.getEnvironmentMap().textureId, 0);
			cubeCamera.switchToFace(i);

			renderer.renderLowQualityScene(entities, scene.getTerrains().getAll(), scene.getLights().getAll(),
					cubeCamera);
		}
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());

		GL30.glDeleteRenderbuffers(depthBuffer);
		GL30.glDeleteFramebuffers(fbo);
		entities.clear();
	}

	public void processEntity(IEntity entity) {
		TexturedModel entityModel = entity.getModel();
		List<IEntity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<IEntity> newBatch = new ArrayList<IEntity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

}
