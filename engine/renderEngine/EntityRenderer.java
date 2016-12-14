package renderEngine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Entity;
import entities.EntityShader;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import scene.ES;
import textures.ModelTexture;
import toolbox.Maths;
import toolbox.OGLUtils;

public class EntityRenderer {

	private EntityShader shader;
	
	public EntityRenderer(Matrix4f projectionMatrix) {
		this.shader = new EntityShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities, Vector4f clipPlane, Collection<Light> lights, Camera camera, Matrix4f toShadowMapSpace, int environmentMap) {
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(ES.DISPLAY_RED, ES.DISPLAY_GREEN, ES.DISPLAY_BLUE);
		shader.loadFogDensity(ES.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadCamera(camera);
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(ES.SHADOW_DISTANCE, ES.SHADOW_MAP_SIZE, ES.SHADOW_TRANSITION_DISTANCE, ES.SHADOW_PCF);
		for(TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model, environmentMap);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch) {
				if(entity.isRendered()) {
					prepareInstance(entity);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), 
							GL11.GL_UNSIGNED_INT, 0);
				}
			}
			unbindTexturedModel();
		}
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	private void prepareTexturedModel(TexturedModel model, int environmentMap) {
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if(texture.isHasTransparency()) {
			OGLUtils.cullBackFaces(false);
		}
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		shader.loadReflectiveFactor(texture.getReflectiveFactor());
		shader.loadRefractVariables(texture.getRefractiveIndex(), texture.getRefractiveFactor());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		shader.loadUsesSpecularMap(texture.hasSpecularMap());
		if(texture.hasSpecularMap()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getSpecularMap());
		}
		if((texture.getReflectiveFactor() > 0)
				|| (texture.getRefractiveFactor() > 0)) {
			GL13.glActiveTexture(GL13.GL_TEXTURE3);
			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, environmentMap);
		}
	}
	
	private void unbindTexturedModel() {
		OGLUtils.cullBackFaces(true);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTranformationMatrix(transformationMatrix);
		shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}

}
