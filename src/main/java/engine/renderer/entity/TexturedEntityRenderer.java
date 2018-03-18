package renderer.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.Entity;
import object.light.ILight;
import object.light.Light;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
import primitive.texture.Texture;
import primitive.texture.material.Material;
import renderer.main.IMainRenderer;
import shader.entity.textured.EntityShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;
import tool.openGL.OGLUtils;

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
public class TexturedEntityRenderer implements IEntityRenderer {

	private EntityShader shader;
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
		this.shader = new EntityShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
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
	 * @see ICamera
	 * @see Texture
	 */
	public void renderHigh(Map<Model, List<Entity>> entities, Vector4f clipPlane, Collection<ILight> lights,
			ICamera camera, Matrix4f toShadowMapSpace, Texture environmentMap) {
		if (!entities.isEmpty()) {
			this.environmentMap = environmentMap;
			shader.start();
			shader.loadClipPlane(clipPlane);
			shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
			shader.loadFogDensity(EngineSettings.FOG_DENSITY);
			shader.loadLights(lights);
			shader.loadCamera(camera);
			shader.loadToShadowSpaceMatrix(toShadowMapSpace);
			shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
					EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);			
			entities.forEach((model, entityList) -> {
				prepareTexturedModel(model);
				entityList.forEach(entity -> {
						prepareInstance(entity);
						GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
						GL11.glFlush();
				});
				unbindTexturedModel();
			});
			shader.stop();
		}
	}

	/**
	 * Rendering enities for low quality scene using more complex shader
	 * uniforms and OpenGL per indicies rendering engine
	 * 
	 * @param entities
	 *            - map of {@link IEntity} list with {@link Model} key
	 *            that have to be rendered
	 * @param lights
	 *            - collection of {@link Light} that effects on scene
	 * 
	 * @param camera
	 *            - {@link ICamera} that represents point of view
	 * 
	 * @see IEntity
	 * @see Light
	 * @see ICamera
	 */
	public void renderLow(Map<Model, List<Entity>> entities, Collection<ILight> lights, ICamera camera, Matrix4f toShadowMapSpace) {
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.start();
		shader.loadClipPlane(EngineSettings.NO_CLIP);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadCamera(camera);
		entities.keySet()
			.forEach(model -> {
				prepareLowTexturedModel(model);
				entities.get(model)
					.forEach(entity -> {
						prepareInstance(entity);
						GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					});
					unbindTexturedModel();
			});
		shader.stop();
	}

	public void clean() {
		shader.clean();
	}
 
	/**
	 * Prepare low quality TexturedModel for using in shader program
	 * 
	 * @param model
	 *            - {@link Model} used to bind VBO attributes
	 */
	private void prepareLowTexturedModel(Model model) {
		Mesh rawModel = model.getMesh();
		VAO vao = rawModel.getVAO();
		vao.bind(0, 1, 2);
		Material texture = model.getMaterial();
		shader.loadNumberOfRows(texture.getDiffuseMap().getNumberOfRows());
		if (texture.getDiffuseMap().isHasTransparency()) {
			OGLUtils.cullBackFaces(false);
		}
		model.getMaterial().getDiffuseMap().bind(0);
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
		shader.loadNumberOfRows(material.getDiffuseMap().getNumberOfRows());
		if (material.getDiffuseMap().isHasTransparency()) {
			OGLUtils.cullBackFaces(false);
		}
		shader.loadFakeLightingVariable(material.isUseFakeLighting());
		shader.loadShineVariables(material.getShininess(), material.getReflectivity());
		shader.loadReflectiveFactor(material.getReflectiveFactor());
		shader.loadRefractVariables(material.getRefractiveIndex(), material.getRefractiveFactor());
		model.getMaterial().getDiffuseMap().bind(0);
		shader.loadUsesSpecularMap(material.getSpecularMap() !=null);
		if (material.getSpecularMap() !=null) {
			material.getSpecularMap().bind(4);
		}
		shader.loadUsesAlphaMap(material.getAlphaMap() != null);
		if(material.getAlphaMap() != null) {
			material.getAlphaMap().bind(5);
		}
		if ((material.getReflectiveFactor() > 0) || (material.getRefractiveFactor() > 0)) {
			GL13.glActiveTexture(GL13.GL_TEXTURE7);
			GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, environmentMap.textureId);
		}
	}

	/**
	 * Unbinds VBO for used model
	 */
	private void unbindTexturedModel() {
		OGLUtils.cullBackFaces(true);
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
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale());
		shader.loadTranformationMatrix(transformationMatrix);
		Vector2f textureOffset = entity.getTextureOffset();
		shader.loadOffset(textureOffset.x, textureOffset.y);
		shader.loadManipulateVariables(entity.isChosen());
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
