package scene;

import primitive.buffer.VAO;
import renderer.Renderer;
import shader.ShaderProgram;

public abstract class Drawing extends Member {
	
	protected Renderer renderer;
	protected ShaderProgram shader;
	protected VAO geometry;

	protected Drawing(String name) {
		super(name);
	}
	
	protected Renderer getRenderer() {
		return renderer;
	}
	
	protected Drawing setRenderer(Renderer renderer) {
		this.renderer = renderer;
		return this;
	}
	
	protected ShaderProgram getShader() {
		return shader;
	}
	
	protected Drawing setShader(ShaderProgram shader) {
		this.shader = shader;
		return this;
	}
	
	protected VAO getGeometry() {
		return geometry;
	}
	
	protected Drawing setGeometry(VAO geometry) {
		this.geometry = geometry;
		return this;
	}
}
