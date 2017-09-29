package renderer.shadow;

import java.util.Collection;

import org.lwjgl.opengl.GL11;

import object.camera.ICamera;
import object.terrain.terrain.ITerrain;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import shader.shadow.ShadowShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class ShadowMapTerrainRenderer {

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
	public ShadowMapTerrainRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) {
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}

	/**
	 * Renders terrains to the shadow map. Each model is first bound and then
	 * all of the terrains using that model are rendered to the shadow map.
	 * 
	 * @param terrains
	 *            - the terrains to be rendered to the shadow map.
	 */
	public void render(Collection<ITerrain> terrains) {
		for (ITerrain terrain : terrains) {
			Mesh mesh = terrain.getModel();
			bindMesh(mesh);
			prepareInstance(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		unbindMesh();
	}

	/**
	 * Binds a raw model before rendering. Only the attribute 0 is enabled here
	 * because that is where the positions are stored in the VAO, and only the
	 * positions are required in the vertex shader.
	 * 
	 * @param rawModel
	 *            - the model to be bound.
	 */
	private void bindMesh(Mesh rawModel) {
		VAO modelVao = rawModel.getVAO();
		modelVao.bind(0, 1);
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

	private void unbindMesh() {
		VAO.unbind(0, 1);
	}

	private void prepareInstance(ITerrain terrain) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0,
				0, 1);
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, transformationMatrix);
		shader.loadMvpMatrix(mvpMatrix);
	}

}
