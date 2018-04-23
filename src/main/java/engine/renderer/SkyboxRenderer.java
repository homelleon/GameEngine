package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import core.DisplayManager;
import core.EngineMain;
import core.settings.EngineSettings;
import primitive.buffer.Loader;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import scene.Scene;
import shader.skybox.SkyboxShader;
import tool.math.Matrix4f;
import tool.math.vector.Color;

public class SkyboxRenderer {

	private static final float SIZE = 500f;

	private static final float[] VERTICES = { 
			-SIZE, SIZE, -SIZE, 
			-SIZE, -SIZE, -SIZE, 
			SIZE, -SIZE, -SIZE, 
			
			SIZE, -SIZE,-SIZE, 
			SIZE, SIZE, -SIZE, 
			-SIZE, SIZE, -SIZE,
			
			-SIZE, -SIZE, SIZE, 
			-SIZE, -SIZE, -SIZE, 
			-SIZE, SIZE, -SIZE, 
			
			-SIZE, SIZE, -SIZE, 
			-SIZE, SIZE, SIZE, 
			-SIZE,-SIZE, SIZE,

			SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, SIZE,
			SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE,
			
			SIZE, SIZE, -SIZE,
			SIZE, -SIZE, -SIZE,
			-SIZE, -SIZE, SIZE,
			-SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE, 
			
			SIZE, SIZE, SIZE, 
			SIZE, -SIZE, SIZE, 
			-SIZE, -SIZE, SIZE,
			-SIZE, SIZE, -SIZE, 
			
			SIZE, SIZE, -SIZE, 
			SIZE, SIZE, SIZE, 
			SIZE, SIZE, SIZE, 
			-SIZE, SIZE, SIZE, 
			
			-SIZE, SIZE, -SIZE,
			-SIZE, -SIZE, -SIZE, 
			-SIZE, -SIZE, SIZE, 
			SIZE, -SIZE, -SIZE,
			
			SIZE, -SIZE, -SIZE, 
			-SIZE, -SIZE, SIZE, 
			SIZE, -SIZE, SIZE };

	private static String[] TEXTURE_FILES = { 
			"right", "left", "top", "bottom", "back", "front" };
	private static String[] NIGHT_TEXTURE_FILES = { 
			"nightRight", "nightLeft", "nightTop", 
			"nightBottom", "nightBack", "nightFront" };

	private Mesh cube;
	private int dayTexture;
	private int nightTexture;
	private SkyboxShader shader;
	private float time = 0;

	public SkyboxRenderer(Matrix4f projectionMatrix) {
		Loader loader = Loader.getInstance();
		cube = loader.getVertexLoader().loadToVAO(VERTICES, 3);
		dayTexture = loader.getTextureLoader().loadCubeMap(EngineSettings.TEXTURE_SKYBOX_PATH, TEXTURE_FILES);
		nightTexture = loader.getTextureLoader().loadCubeMap(EngineSettings.TEXTURE_SKYBOX_PATH, NIGHT_TEXTURE_FILES);
		shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Scene scene) {
		shader.start();
		shader.loadViewMatrix(scene.getCamera());
		shader.loadFogColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		VAO vao = cube.getVAO();
		vao.bind(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL11.glFlush();
		VAO.unbind(0);
		shader.stop();

	}

	private void bindTextures() {
		if (!EngineMain.getIsEnginePaused()) {
			time += DisplayManager.getFrameTimeSeconds() * 200;
		}
		time %= 24000;
		int texture1;
		int texture2;
		float blendFactor;

		if (time >= 0 && time < 50000) {
			texture1 = dayTexture;
			texture2 = dayTexture;
			blendFactor = (time - 0) / (50000 - 0);
		} else if (time >= 50000 && time < 80000) {
			texture1 = dayTexture;
			texture2 = nightTexture;
			blendFactor = (time - 50000) / (80000 - 50000);
		} else if (time >= 80000 && time < 210000) {
			texture1 = nightTexture;
			texture2 = nightTexture;
			blendFactor = (time - 80000) / (210000 - 80000);
		} else {
			texture1 = nightTexture;
			texture2 = dayTexture;
			blendFactor = (time - 210000) / (240000 - 210000);
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		shader.loadBlendFactor(blendFactor);
	}

	public int getTexture() {
		return dayTexture;
	}

}
