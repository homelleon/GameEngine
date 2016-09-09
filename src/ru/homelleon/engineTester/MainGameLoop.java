package ru.homelleon.engineTester;

import org.lwjgl.opengl.Display;

import ru.homelleon.models.RawModel;
import ru.homelleon.renderEngine.DisplayManager;
import ru.homelleon.renderEngine.Loader;
import ru.homelleon.renderEngine.Renderer;
import ru.homelleon.renderEngine.TexturedModel;
import ru.homelleon.shaders.StaticShader;
import ru.homelleon.textures.ModelTexture;

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
		
		float[] textureCoords = {
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		RawModel model = loader.loadToVAO(vertecies,textureCoords,indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		TexturedModel texturedModel = new TexturedModel(model,texture);
		
		while(!Display.isCloseRequested()){
			//game logic
			renderer.prepare();
			shader.start();
			renderer.render(texturedModel);
			shader.stop();			
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
