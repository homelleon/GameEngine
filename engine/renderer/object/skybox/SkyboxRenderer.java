package renderer.object.skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import core.EngineMain;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.CameraInterface;
import object.model.RawModel;
import renderer.loader.Loader;
import shader.skybox.SkyboxShader;

public class SkyboxRenderer {

	private static final float SIZE = 500f;

	private static final float[] VERTICES = { -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
			-SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,

			-SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE,
			-SIZE, SIZE,

			SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE,
			-SIZE,

			-SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE,
			SIZE,

			-SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE,
			-SIZE,

			-SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE,
			-SIZE, SIZE };

	private static String[] TEXTURE_FILES = { "right", "left", "top", "bottom", "back", "front" };
	private static String[] NIGHT_TEXTURE_FILES = { "nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack",
			"nightFront" };

	private RawModel cube;
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

	public void render(CameraInterface camera) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColour(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE);
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
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

		if (time >= 0 && time < 5000) {
			texture1 = nightTexture;
			texture2 = nightTexture;
			blendFactor = (time - 0) / (5000 - 0);
		} else if (time >= 5000 && time < 8000) {
			texture1 = nightTexture;
			texture2 = dayTexture;
			blendFactor = (time - 5000) / (8000 - 5000);
		} else if (time >= 8000 && time < 21000) {
			texture1 = dayTexture;
			texture2 = dayTexture;
			blendFactor = (time - 8000) / (21000 - 8000);
		} else {
			texture1 = dayTexture;
			texture2 = nightTexture;
			blendFactor = (time - 21000) / (24000 - 21000);
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		shader.loadBlendFactor(blendFactor);
	}

	public int getTexture() {
		return this.dayTexture;
	}

}
