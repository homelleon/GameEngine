package ru.homelleon.engineTester;

import org.lwjgl.opengl.Display;

import ru.homelleon.renderEngine.DisplayManager;
import ru.homelleon.renderEngine.Loader;
import ru.homelleon.renderEngine.RawModel;
import ru.homelleon.renderEngine.Renderer;
import ru.homelleon.shaders.StaticShader;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.creatDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		//Vertecies
		float[] vertecies = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0

		};
		
		int[] indices = {
				
				0,1,3,
				3,1,2
		};
		
		RawModel model = loader.loadToVAO(vertecies,indices);
		
		while(!Display.isCloseRequested()){
			//game logic
			renderer.prepare();
			shader.start();
			renderer.render(model);
			shader.stop();			
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
