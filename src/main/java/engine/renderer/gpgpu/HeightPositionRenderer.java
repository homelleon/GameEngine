package renderer.gpgpu;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import object.texture.Texture2D;
import primitive.buffer.Loader;
import primitive.buffer.VBO;
import shader.gpgpu.HeightPositionShader;

public class HeightPositionRenderer {
	
	HeightPositionShader shader;
	Texture2D heightMap;
	HeightStructure buffer;
	
	public HeightPositionRenderer(Texture2D heightMap) {
		this.heightMap = heightMap;
		this.shader = new HeightPositionShader();
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
	}
	
	// inner structure class
	private class HeightStructure {
		public float[] heights;
		
		public HeightStructure(int size) {
			this.heights = new float[size];
		}
	}
	
	public void render() {
		int size =  heightMap.getHeight();
		buffer = this.new HeightStructure(size);
		VBO vbo = Loader.getInstance().getVertexLoader().loadToVBOasSSBO(buffer.heights);
		vbo.bind();		
		// compute
		vbo.bindBase(0);
		heightMap.bind(1);	
		GL43.glDispatchCompute(size / 2, size / 2, 1);
		GL42.glMemoryBarrier(GL43.GL_SHADER_STORAGE_BARRIER_BIT);
		ByteBuffer byteBuffer = GL15.glMapBuffer(GL43.GL_SHADER_STORAGE_BUFFER, GL15.GL_WRITE_ONLY, null);
		GL11.glFinish();
		buffer.heights = byteBuffer.asFloatBuffer().array();		
		GL15.glUnmapBuffer(GL43.GL_SHADER_STORAGE_BUFFER);
		shader.stop();
	}
	
	public float[] getHeights() {
		return this.buffer.heights.clone();
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
	}

}
