package renderer.gpgpu;

import java.nio.ByteBuffer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import primitive.buffer.Loader;
import primitive.buffer.VBO;
import primitive.texture.Texture2D;
import renderer.debug.DebugRenderer;
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
		int size =  heightMap.getHeight();
		this.heights = new float[size*size];
		for(int i = 0; i < size * size; i++) {
			heights[i] = 0.1f;
		}
		VBO vbo = Loader.getInstance().getVertexLoader().loadToVBOasSSBO(heights);	
		// compute
		vbo.bindBase(0);
		heightMap.bind(1);
		GL43.glDispatchCompute(size, size, 1);
		GL42.glMemoryBarrier(GL43.GL_SHADER_STORAGE_BARRIER_BIT);
		GL11.glFinish();
		ByteBuffer byteBuffer = GL15.glMapBuffer(GL43.GL_SHADER_STORAGE_BUFFER, GL15.GL_READ_ONLY, null);
		for(int i = 0; i < size * size; i++) {
			heights[i] = byteBuffer.asFloatBuffer().get(i);
			System.out.println(byteBuffer.asFloatBuffer().get(i));
		}
		GL15.glUnmapBuffer(GL43.GL_SHADER_STORAGE_BUFFER);
		vbo.unbind();
		shader.stop();
		
//		// debugging
//		DebugRenderer debugRenderer = new DebugRenderer();
//		debugRenderer.addAttribute(vbo);
//		while(!Display.isCloseRequested()) {
//			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
//				break;
//			}
//			debugRenderer.render();
//		}
//		debugRenderer.clean();
//		System.exit(0);
	}
	
	public float[] getHeights() {
		return heights;
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
	}

}
