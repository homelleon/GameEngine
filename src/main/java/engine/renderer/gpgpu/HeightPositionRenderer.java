package renderer.gpgpu;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import core.settings.EngineSettings;
import primitive.buffer.Loader;
import primitive.buffer.VBO;
import primitive.texture.Texture2D;
import shader.gpgpu.HeightPositionShader;

public class HeightPositionRenderer {
	
	HeightPositionShader shader;
	Texture2D heightMap;
	float[] heights;
	
	public HeightPositionRenderer(Texture2D heightMap) {
		this.heightMap = heightMap;
		this.shader = new HeightPositionShader();
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render() {
		// initialize
		int size =  heightMap.getHeight();
		this.heights = new float[size * size];
		VBO vbo = Loader.getInstance().getVertexLoader().loadToSSBO(heights);
		
		// compute
		shader.start();
		vbo.bindBase(0);
		heightMap.bind(1);
		GL43.glDispatchCompute(size, size, 1);
		GL42.glMemoryBarrier(GL42.GL_ALL_BARRIER_BITS);
		GL11.glFinish();
		shader.stop();
		vbo.unbindBase(0);
		
		// map values
		vbo.bind();
		ByteBuffer byteBuffer = GL15.glMapBuffer(GL43.GL_SHADER_STORAGE_BUFFER, GL15.GL_READ_ONLY, null);
		for (int i = 0; i < size * size; i++) {
			heights[i] = byteBuffer.asFloatBuffer().get(i) * EngineSettings.TERRAIN_SCALE_Y;
		}
		GL15.glUnmapBuffer(GL43.GL_SHADER_STORAGE_BUFFER);
		vbo.unbind();
	}
	
	public float[] getHeights() {
		return heights;
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
	}

}
