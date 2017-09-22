package renderer.object.shadow;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.openglObject.VAO;
import shader.shadow.ShadowShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.openGL.OGLUtils;

public class ShadowMapEntityRenderer {

	private Matrix4f projectionViewMatrix;
	private ShadowShader shader;

	/**
	 * @param shader
	 *            - the simple shader program being used for the shadow render
	 *            pass.
	 * @param projectionViewMatrix
	 *            - the orthographic projection matrix multiplied by the light's
	 *            "view" matrix.
	 */
	protected ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) {
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}

	/**
	 * Renders entities to the shadow map. Each model is first bound and then
	 * all of the entities using that model are rendered to the shadow map.
	 * 
	 * @param entities
	 *            - the entities to be rendered to the shadow map.
	 */
	protected void render(Map<TexturedModel, List<IEntity>> entities, ICamera camera) {
		for (TexturedModel model : entities.keySet()) {
			RawModel rawModel = model.getRawModel();
			bindModel(rawModel);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
			if (model.getTexture().isHasTransparency()) {
				OGLUtils.cullBackFaces(false);
			}
			for (IEntity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			if (model.getTexture().isHasTransparency()) {
				OGLUtils.cullBackFaces(true);
			}
		}
		unbindModel();
	}

	/**
	 * Binds a raw model before rendering. Only the attribute 0 is enabled here
	 * because that is where the positions are stored in the VAO, and only the
	 * positions are required in the vertex shader.
	 * 
	 * @param rawModel
	 *            - the model to be bound.
	 */
	private void bindModel(RawModel rawModel) {
		VAO modelVao = rawModel.getVAO();
		modelVao.bind(0,1);
	}
	
	private void unbindModel() {
		VAO.unbind(0,1);
	}

	/**
	 * Prepares an entity to be rendered. The model matrix is created in the
	 * usual way and then multiplied with the projection and view matrix (often
	 * in the past we've done this in the vertex shader) to create the
	 * mvp-matrix. This is then loaded to the vertex shader as a uniform.
	 * 
	 * @param entity
	 *            - the entity to be prepared for rendering.
	 */
	private void prepareInstance(IEntity entity) {
		Matrix4f modelMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(),
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale());
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, modelMatrix);
		shader.loadMvpMatrix(mvpMatrix);
		Vector2f textureOffset = entity.getTextureOffset();
		shader.loadOffset(textureOffset.x, textureOffset.y);
		shader.loadNumberOfRows(entity.getModel().getTexture().getNumberOfRows());
	}

}
