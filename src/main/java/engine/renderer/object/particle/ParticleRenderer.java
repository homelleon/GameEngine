package renderer.object.particle;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Vector3f;

import object.camera.ICamera;
import object.model.raw.RawModel;
import object.particle.particle.Particle;
import object.texture.particle.ParticleTexture;
import renderer.loader.Loader;
import renderer.loader.VertexBufferLoader;
import shader.particle.ParticleShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vec3f;

public class ParticleRenderer {

	private static final float[] VERTICES = { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f };
	private static final int MAX_INSTANCES = 10000;
	private static final int INSTANCE_DATA_LENGTH = 21;

	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);

	private RawModel quad;
	private ParticleShader shader;

	private int vbo;
	private int pointer = 0;

	public ParticleRenderer(Matrix4f projectionMatrix) {
		VertexBufferLoader vertexLoader = Loader.getInstance().getVertexLoader();
		this.vbo = vertexLoader.createEmptyVbo(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
		quad = vertexLoader.loadToVAO(VERTICES, 2);
		vertexLoader.addInstacedAttribute(quad.getVaoID(), vbo, 1, 4, INSTANCE_DATA_LENGTH, 0);
		vertexLoader.addInstacedAttribute(quad.getVaoID(), vbo, 2, 4, INSTANCE_DATA_LENGTH, 4);
		vertexLoader.addInstacedAttribute(quad.getVaoID(), vbo, 3, 4, INSTANCE_DATA_LENGTH, 8);
		vertexLoader.addInstacedAttribute(quad.getVaoID(), vbo, 4, 4, INSTANCE_DATA_LENGTH, 12);
		vertexLoader.addInstacedAttribute(quad.getVaoID(), vbo, 5, 4, INSTANCE_DATA_LENGTH, 16);
		vertexLoader.addInstacedAttribute(quad.getVaoID(), vbo, 6, 1, INSTANCE_DATA_LENGTH, 20);
		shader = new ParticleShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<ParticleTexture, List<Particle>> particles, ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		prepare();
		for (ParticleTexture texture : particles.keySet()) {
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

	private void bindTexture(ParticleTexture texture) {
		if (texture.isAdditive()) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		} else {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		shader.loadNumberOfRows(texture.getNumberOfRows());
	}

	private void updateModelViewMatrix(Vec3f position, float rotation, float scale, Matrix4f viewMatrix,
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
		modelViewMatrix.rotate((float) Math.toRadians(rotation), new Vec3f(0, 0, 1));
		modelViewMatrix.scale(new Vec3f(scale, scale, scale));
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
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(4);
		GL20.glEnableVertexAttribArray(5);
		GL20.glEnableVertexAttribArray(6);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
	}

	private void finishRendering() {
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL20.glDisableVertexAttribArray(5);
		GL20.glDisableVertexAttribArray(6);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

}