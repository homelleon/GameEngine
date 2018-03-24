package renderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import core.DisplayManager;
import primitive.buffer.Loader;
import primitive.buffer.VAO;
import primitive.buffer.VBO;
import shader.debug.DebugShader;

public class DebugRenderer {
	
	private DebugShader shader;
	private VAO positionVAO;
	private List<VBO> attributes = new ArrayList<VBO>();
	
	public DebugRenderer() {
		float[] positions = createPositions();
		this.positionVAO = Loader.getInstance().getVertexLoader().loadToVAO(positions, 2).getVAO();
		this.shader = new DebugShader();
	}
	
	public void addAttribute(VBO vbo) {
		this.attributes.add(vbo);
	}
	
	public void render() {
		DisplayManager.updateDisplay();
		GL11.glClearColor(0, 0.2f, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.start();
		
		positionVAO.bind(0);
		for(int i = 0; i < attributes.size(); i++) {
			attributes.get(i).bindBase(i);
		}
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 3);
		VAO.unbind();
		shader.stop();
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
	}
	
	private float[] createPositions() {
		float[] positions = new float[6];
		// create triangle
		// A
		positions[0] = 0.5f;
		positions[1] = -0.5f;
		// B
		positions[2] = -0.5f;
		positions[3] = -0.5f;
		// C
		positions[4] = 0.0f;
		positions[5] = 0.5f;
		return  positions;
	}
}