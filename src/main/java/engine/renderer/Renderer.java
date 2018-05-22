package renderer;

import primitive.buffer.VAO;
import shader.Shader;
import shader.postprocess.Fbo;

public class Renderer {
	
	private Shader shader;
	private VAO vao;
	
	public void render() {
		shader.start();
		shader.stop();
	}
	
	public void render(Fbo fbo) {
		shader.start();
		shader.stop();
	}
	
	public void setShader(Shader shader) {
		this.shader = shader;
	}
	
	public void setShaderVariables() {
		
	}
	
	public void setGeometryBuffer(VAO vao) {
		this.vao = vao;
	}

	
	public void clean() {
		shader.clean();
	}
	
}
