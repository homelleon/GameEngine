package renderer.shadow;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import object.entity.entity.IEntity;
import object.texture.material.Material;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
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
	public ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) {
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
	public void render(Map<Model, List<IEntity>> entities) {
		shader.start();
		entities.forEach((model, entityList) -> {
			Mesh mesh = model.getMesh();
			bindModel(mesh);
			Material material = model.getMaterial();
			material.getDiffuseMap().bind(0);
			shader.loadNumberOfRows(material.getDiffuseMap().getNumberOfRows());
			if (model.getMaterial().getDiffuseMap().isHasTransparency()) {
				OGLUtils.cullBackFaces(false);
			}
			entityList.forEach(entity -> {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			});
			if (model.getMaterial().getDiffuseMap().isHasTransparency()) {
				OGLUtils.cullBackFaces(true);
			}
			unbindModel();
		});
		shader.stop();
	}

	/**
	 * Binds a raw model before rendering. Only the attribute 0 is enabled here
	 * because that is where the positions are stored in the VAO, and only the
	 * positions are required in the vertex shader.
	 * 
	 * @param mesh
	 *            - the model to be bound.
	 */
	private void bindModel(Mesh mesh) {
		VAO modelVao = mesh.getVAO();
		modelVao.bind(0, 1);
	}
	
	private void unbindModel() {
		VAO.unbind(0, 1);
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
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation().getX(),
				entity.getRotation().getY(), entity.getRotation().getZ(), entity.getScale());
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, transformationMatrix);
		shader.loadMvpMatrix(mvpMatrix);
		Vector2f textureOffset = entity.getTextureOffset();
		shader.loadOffset(textureOffset.x, textureOffset.y);
	}

}
