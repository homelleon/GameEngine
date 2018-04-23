package renderer;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;

import object.camera.Camera;
import object.particle.Particle;
import primitive.buffer.BufferLoader;
import primitive.buffer.Loader;
import primitive.buffer.VAO;
import primitive.buffer.VBO;
import primitive.model.Mesh;
import primitive.texture.particle.ParticleMaterial;
import shader.particle.ParticleShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class ParticleRenderer {

	private static final float[] VERTICES = { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f };
	private static final int MAX_INSTANCES = 10000;
	private static final int INSTANCE_DATA_LENGTH = 21;

	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);

	private Mesh quad;
	private ParticleShader shader;

	private VBO vbo;
	private int pointer = 0;

	public ParticleRenderer(Matrix4f projectionMatrix) {
		BufferLoader vertexLoader = Loader.getInstance().getVertexLoader();
		this.vbo = vertexLoader.createEmptyVbo(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
		quad = vertexLoader.loadToVAO(VERTICES, 2);		
		vertexLoader.addInstacedAttribute(quad.getVAO(), vbo, 1, 4, INSTANCE_DATA_LENGTH, 0);
		vertexLoader.addInstacedAttribute(quad.getVAO(), vbo, 2, 4, INSTANCE_DATA_LENGTH, 4);
		vertexLoader.addInstacedAttribute(quad.getVAO(), vbo, 3, 4, INSTANCE_DATA_LENGTH, 8);
		vertexLoader.addInstacedAttribute(quad.getVAO(), vbo, 4, 4, INSTANCE_DATA_LENGTH, 12);
		vertexLoader.addInstacedAttribute(quad.getVAO(), vbo, 5, 4, INSTANCE_DATA_LENGTH, 16);
		vertexLoader.addInstacedAttribute(quad.getVAO(), vbo, 6, 1, INSTANCE_DATA_LENGTH, 20);
		shader = new ParticleShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}

	public void render(Map<ParticleMaterial, List<Particle>> particles, Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		prepare();
		for (ParticleMaterial texture : particles.keySet()) {
			bindTexture(texture);
			List<Particle> particleList = particles.get(texture);
			pointer = 0;
			float[] vboData = new float[particleList.size() * INSTANCE_DATA_LENGTH];
			particleList.forEach(particle -> {
				updateModelViewMatrix(particle.getPosition(), particle.getRotation(), particle.getScale(), viewMatrix,
						vboData);
				updateTexCoordInfo(particle, vboData);
			});
			Loader.getInstance().getVertexLoader().updateVbo(vbo, vboData, buffer);
			GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount(), particleList.size());
			GL11.glFlush();
		}
		finishRendering();
	}

	public void cleanUp() {
		shader.clean();
	}

	private void updateTexCoordInfo(Particle particle, float[] data) {
		data[pointer++] = particle.getTexOffset1().x;
		data[pointer++] = particle.getTexOffset1().y;
		data[pointer++] = particle.getTexOffset2().x;
		data[pointer++] = particle.getTexOffset2().y;
		data[pointer++] = particle.getBlend();
	}

	private void bindTexture(ParticleMaterial material) {
		if (material.isAdditive())
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		else
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		material.getTexture().bind(0);
		shader.loadNumberOfRows(material.getTexture().getNumberOfRows());
	}

	private void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix,
			float[] vboData) {
		Matrix4f modelMatrix = new Matrix4f();
		modelMatrix.translate(position);
		modelMatrix.m[0][0] = viewMatrix.m[0][0];
		modelMatrix.m[0][1] = viewMatrix.m[1][0];
		modelMatrix.m[0][2] = viewMatrix.m[2][0];
		modelMatrix.m[1][0] = viewMatrix.m[0][1];
		modelMatrix.m[1][1] = viewMatrix.m[1][1];
		modelMatrix.m[1][2] = viewMatrix.m[2][1];
		modelMatrix.m[2][0] = viewMatrix.m[0][2];
		modelMatrix.m[2][1] = viewMatrix.m[1][2];
		modelMatrix.m[2][2] = viewMatrix.m[2][2];

		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix, modelMatrix);
		modelViewMatrix.rotate((float) Math.toRadians(rotation), new Vector3f(0, 0, 1));
		modelViewMatrix.scale(new Vector3f(scale, scale, scale));
		storeMatrixData(modelViewMatrix, vboData);
	}

	private void storeMatrixData(Matrix4f matrix, float[] vboData) {
		vboData[pointer++] = matrix.m[0][0];
		vboData[pointer++] = matrix.m[0][1];
		vboData[pointer++] = matrix.m[0][2];
		vboData[pointer++] = matrix.m[0][3];
		vboData[pointer++] = matrix.m[1][0];
		vboData[pointer++] = matrix.m[1][1];
		vboData[pointer++] = matrix.m[1][2];
		vboData[pointer++] = matrix.m[1][3];
		vboData[pointer++] = matrix.m[2][0];
		vboData[pointer++] = matrix.m[2][1];
		vboData[pointer++] = matrix.m[2][2];
		vboData[pointer++] = matrix.m[2][3];
		vboData[pointer++] = matrix.m[3][0];
		vboData[pointer++] = matrix.m[3][1];
		vboData[pointer++] = matrix.m[3][2];
		vboData[pointer++] = matrix.m[3][3];
	}

	private void prepare() {
		shader.start();
		VAO vao = quad.getVAO();
		vao.bind(0, 1, 2, 3, 4, 5, 6);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
	}

	private void finishRendering() {
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		VAO.unbind(0, 1, 2, 3, 4, 5, 6);
		shader.stop();
	}

}