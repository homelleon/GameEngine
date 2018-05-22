package renderer.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.Camera;
import object.entity.Entity;
import object.light.Light;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
import primitive.texture.Texture;
import primitive.texture.material.Material;
import scene.Scene;
import shader.entity.EntityShader;
import shader.entity.NormalMappedEntityShader;
import tool.GraphicUtils;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;

/**
 * Render engine for entities.
 * <p>
 * Uses map of entities and input variables to draw entities in the scene.
 * <p>
 * Also can draw low quality entities clipping heavy shader program uniforms.
 * 
 * @author homelleon
 * @version 1.0
 * 
 * @see IEntity
 * @see IMainRenderer
 */
public class TexturedEntityRenderer implements EntityRenderer {

	private EntityShader simpleShader;
	private NormalMappedEntityShader normalShader;
	private Texture environmentMap;

	/**
	 * Entity render engine constructor.
	 * <p>
	 * Initializes EntityShader and loads texture units and projection matrix in
	 * it.
	 * 
	 * @param projectionMatrix
	 *            - {@link Matrix4f} value to draw entities in the scene using
	 *            its projection in the game world space
	 */

	public TexturedEntityRenderer(Matrix4f projectionMatrix) {
		this.simpleShader = new EntityShader();
		simpleShader.start();
		simpleShader.loadProjectionMatrix(projectionMatrix);
		simpleShader.connectTextureUnits();
		simpleShader.stop();
	}

	/**
	 * Rendering map of entities using entityShader and OpenGL per indicies
	 * rendering engine.
	 * 
	 * @param entities
	 *            - map of {@link IEntity} list with {@link Model} key
	 *            that have to be rendered
	 * @param clipPlane
	 *            - {@link Vector4f} plane that clipps scene
	 * @param lights
	 *            - collection of {@link Light} that effects on scene
	 * @param camera
	 *            - {@link ICamera} that represents point of view
	 * @param toShadowMapSpace
	 *            - {@link Matrix4f} value of space where shadow map is rendered
	 * @param environmentMap
	 *            - {@link Texture} value of reflection map to render at
	 *            reflecting objects
	 * 
	 * @see IEntity
	 * @see Light
	 * @see Camera
	 * @see Texture
	 */
	public void render(Map<Model, List<Entity>> entities, Scene scene, Vector4f clipPlane, Matrix4f toShadowMapSpace, Texture environmentMap) {
		if (entities.isEmpty()) return;
		
		if (environmentMap != null)
			this.environmentMap = environmentMap;
		
		if (clipPlane == null)
			clipPlane = EngineSettings.NO_CLIP;
		
		simpleShader.start();
		simpleShader.loadClipPlane(clipPlane);
		simpleShader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		simpleShader.loadFogDensity(EngineSettings.FOG_DENSITY);
		simpleShader.loadLights(scene.getLights().getAll());
		simpleShader.loadCamera(scene.getCamera());
		if (toShadowMapSpace != null)
			simpleShader.loadToShadowSpaceMatrix(toShadowMapSpace);
		simpleShader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);			
		entities.forEach((model, entityList) -> {
			prepareTexturedModel(model);
			entityList.forEach(entity -> {
					prepareInstance(entity);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			});
			unbindTexturedModel();
		});
		simpleShader.stop();
	}

	public void clean() {
		simpleShader.clean();
	}
 

	/**
	 * Prepare TexturedModel for using in shader program
	 * 
	 * @param model
	 *            - {@link Model} used to bind VBO attributes
	 */
	private void prepareTexturedModel(Model model) {
		Mesh rawModel = model.getMesh();
		VAO vao = rawModel.getVAO();
		vao.bind(0, 1, 2);
		Material material = model.getMaterial();
		simpleShader.loadNumberOfRows(material.getDiffuseMap().getNumberOfRows());
		
		if (material.getDiffuseMap().isTransparent())
			GraphicUtils.cullBackFaces(false);
		
		simpleShader.loadFakeLightingVariable(material.isUseFakeLighting());
		simpleShader.loadShineVariables(material.getShininess(), material.getReflectivity());
		simpleShader.loadReflectiveFactor(material.getReflectiveFactor());
		simpleShader.loadRefractVariables(material.getRefractiveIndex(), material.getRefractiveFactor());
		
		model.getMaterial().getDiffuseMap().bind(0);
		
		simpleShader.loadUsesSpecularMap(material.getSpecularMap() != null);
		
		if (material.getSpecularMap() != null) 
			material.getSpecularMap().bind(4);
		
		simpleShader.loadUsesAlphaMap(material.getAlphaMap() != null);
		
		if (material.getAlphaMap() != null)
			material.getAlphaMap().bind(5);
		
		if ((material.getReflectiveFactor() > 0) || (material.getRefractiveFactor() > 0)) {
			GL13.glActiveTexture(GL13.GL_TEXTURE7);
			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, environmentMap.textureId);
		}
	}

	/**
	 * Unbinds VBO for used model
	 */
	private void unbindTexturedModel() {
		GraphicUtils.cullBackFaces(true);
		VAO.unbind(0, 1, 2);
	}

	/**
	 * Prepare entity shader for each entity used as value.
	 * 
	 * @param entity
	 *            -{@link Entity} used to prepare entity shader
	 */

	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(),
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale().getX());
		simpleShader.loadTranformationMatrix(transformationMatrix);
		Vector2f textureOffset = entity.getTextureOffset();
		simpleShader.loadOffset(textureOffset.x, textureOffset.y);
		simpleShader.loadManipulateVariables(entity.isChosen());
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
