package renderer.water;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import core.EngineMain;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.Light;
import object.texture.Texture2D;
import object.water.WaterFrameBuffers;
import object.water.WaterTile;
import primitive.buffer.Loader;
import primitive.buffer.TextureBufferLoader;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import shader.water.WaterShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;
import tool.openGL.OGLUtils;

public class WaterRenderer {

	private static final String DUDV_MAP = "waterDUDV";
	private static final String NORMAL_MAP = "waterNormalMap";
	private static final float WAVE_SPEED = 0.01f;

	private Mesh quad;
	private WaterShader shader;
	private WaterFrameBuffers fbos;

	private Texture2D dudvTexture;
	private Texture2D normalMap;

	private float moveFactor = 0;

	public WaterRenderer(WaterShader shader, Matrix4f projectionMatrix, WaterFrameBuffers fbos) {
		this.shader = shader;
		this.fbos = fbos;
		TextureBufferLoader textureLoader = Loader.getInstance().getTextureLoader();
		dudvTexture = textureLoader.loadTexture(EngineSettings.TEXTURE_DUDV_MAP_PATH, DUDV_MAP);
		normalMap = textureLoader.loadTexture(EngineSettings.TEXTURE_NORMAL_MAP_PATH, NORMAL_MAP);
		shader.start();
		shader.connectTextureUnits();
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadSkyColour(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE);
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		setUpVAO();
	}

	public void render(Collection<WaterTile> water, ICamera camera, Light sun) {
		prepareRender(camera, sun);
		for (WaterTile tile : water) {
			Matrix4f modelMatrix = Maths.createTransformationMatrix(
					new Vector3f(tile.getX(), tile.getHeight(), tile.getZ()), 0, 0, 0, tile.getSize());
			shader.loadModelMatrix(modelMatrix);
			shader.loadTilingSize(tile.getTilingSize());
			shader.loadWaveStrength(tile.getWaveStrength());
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
		}
		unbind();
	}

	private void prepareRender(ICamera camera, Light sun) {
		shader.start();
		shader.loadViewMatrix(camera);
		moveFactor += WAVE_SPEED * DisplayManager.getFrameTimeSeconds();
		moveFactor %= 1;
		shader.loadMoveFactor(moveFactor);
		shader.loadLight(sun);
		VAO vao = quad.getVAO();
		vao.bind(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
		dudvTexture.bind(2);
		normalMap.bind(3);
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionDepthTexture());

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	}

	private void unbind() {
		GL11.glDisable(GL11.GL_BLEND);
		VAO.unbind(0);
		shader.stop();
	}

	private void setUpVAO() {
		// Just x and z vertex positions here, y is set to 0 in v.shader
		float[] vertices = { 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1 };

		quad = Loader.getInstance().getVertexLoader().loadToVAO(vertices, 2);
	}

}