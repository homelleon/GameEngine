package renderer.object.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.CameraInterface;
import object.entity.entity.Entity;
import object.light.Light;
import object.model.RawModel;
import object.model.TexturedModel;
import object.texture.Texture;
import object.texture.model.ModelTexture;
import renderer.object.main.MainRendererInterface;
import shader.entity.EntityShader;
import tool.math.Maths;
import tool.openGL.OGLUtils;

/**
 * Render engine for entities.
 * <p>Uses map of entities and input variables to draw entities in the scene.
 * <p>Also can draw low quality entities clipping heavy shader program 
 * uniforms. 
 * 
 * @author homelleon
 * @version 1.0
 * 
 * @see Entity
 * @see MainRendererInterface
 */
public class EntityRenderer {

	private EntityShader shader;
	private Texture environmentMap;
	
	/**
	 * Entity render engine constructor.
	 * <p>Initializes EntityShader and loads texture units and projection
	 * matrix in it.   
	 *  
	 * @param projectionMatrix
	 * 							- {@link Matrix4f} value to draw entities 
	 * 							in the scene using its projection in the game
	 * 							world space
	 */
	
	public EntityRenderer(Matrix4f projectionMatrix) {
		this.shader = new EntityShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	/**
	 * Rendering map of entities using entityShader and OpenGL 
	 * per indicies rendering engine.
	 * 
	 * @param entities
	 * 							- map of {@link Entity} list with {@link TexturedModel}
	 * 							key that have to be rendered
	 * @param clipPlane
	 * 							- {@link Vector4f} plane that clipps scene
	 * @param lights
	 * 							- collection of {@link Light} that effects on 
	 * 							scene
	 * @param camera			
	 * 							- {@link CameraInterface} that represents point of view
	 * @param toShadowMapSpace
	 * 							- {@link Matrix4f} value of space where shadow map is 
	 * 							rendered
	 * @param environmentMap
	 * 							- {@link Texture} value of reflection map to render
	 * 							at reflecting objects
	 * 
	 * @see Entity
	 * @see Light
	 * @see CameraInterface
	 * @see Texture
	 */
	public void render(Map<TexturedModel, List<Entity>> entities, 
			Vector4f clipPlane, Collection<Light> lights, CameraInterface camera, 
			Matrix4f toShadowMapSpace, Texture environmentMap) {
		this.environmentMap = environmentMap;
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE);
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadCamera(camera);
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE, EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		for(TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch) {
					prepareInstance(entity);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), 
							GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
		shader.stop();
	}
	

	/**
	 * Rendering enities for low quality scene using more complex 
	 * shader uniforms and OpenGL per indicies rendering engine 
	 * 	 
	 * @param entities
	 * 					- map of {@link Entity} list with {@link TexturedModel}
	 * 					key that have to be rendered
	 * @param lights	- collection of {@link Light} that effects on 
	 * 					scene
	 * 					
	 * @param camera
	 * 					- {@link CameraInterface} that represents point of view
	 * 
	 * @see Entity
	 * @see Light
	 * @see CameraInterface
	 */
	public void renderLow(Map<TexturedModel, List<Entity>> entities, 
			Collection<Light> lights, CameraInterface camera) {
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.start();
		shader.loadClipPlane(EngineSettings.NO_CLIP);
		shader.loadSkyColour(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE);
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadCamera(camera);
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE, EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		for(TexturedModel model : entities.keySet()) {
			prepareLowTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), 
						GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
		shader.stop();		
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	/**
	 * Prepare low quality TexturedModel for using in shader program 
	 * 
	 * @param model
	 * 				- {@link TexturedModel} used to bind VBO attributes
	 */
	private void prepareLowTexturedModel(TexturedModel model) {
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
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	/**
	 * Prepare TexturedModel for using in shader program 
	 * 
	 * @param model
	 * 				- {@link TexturedModel} used to bind VBO attributes
	 */
	private void prepareTexturedModel(TexturedModel model) {
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
			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, environmentMap.textureId);
		}
	}
	
	/**
	 * Unbinds VBO for used model	
	 */
	private void unbindTexturedModel() {
		OGLUtils.cullBackFaces(true);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Prepare entity shader for each entity used as value.
	 * 
	 * @param entity
	 * 					-{@link Entity} used to prepare entity shader
	 */
	
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTranformationMatrix(transformationMatrix);
		shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
		shader.loadManipulateVariables(entity.getIsChosen());
	}
	
	/**
	 * Returns environment map used to attach at reflecting surfaces.
	 * 
	 * @return Texture value of environment map
	 */
	public Texture getEnvironmentMap() {
		return environmentMap;
	}

}
