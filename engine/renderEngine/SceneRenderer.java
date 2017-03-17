package renderEngine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import engineMain.DisplayManager;
import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import inputs.Controls;
import inputs.ControlsInGame;
import inputs.KeyboardGame;
import maps.GameMap;
import maps.MapsTXTWriter;
import maps.MapsWriter;
import particles.ParticleMaster;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import scene.Scene;
import toolbox.MousePicker;
import toolbox.OGLUtils;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;

/**
 * Class that render scene objects and check controls. 
 * TODO: need to refactor
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public class SceneRenderer {

	private MasterRendererSimple masterRenderer;
	private WaterRenderer waterRenderer;
	private GuiRenderer guiRenderer;
	private WaterFrameBuffers waterFBOs;
	private Fbo multisampleFbo;
	private Fbo outputFbo;
	private Fbo outputFbo2;
	private MousePicker picker;
	private Scene scene;
	private Controls controls;

	public void init(Scene scene, Loader loader) {
		this.scene = scene;
		this.masterRenderer = new MasterRendererSimple(loader, scene.getCamera());
		this.guiRenderer = new GuiRenderer(loader);
		ParticleMaster.init(loader, masterRenderer.getProjectionMatrix());
		this.multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight());
		this.outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		this.outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init(loader);

		this.waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, masterRenderer.getProjectionMatrix(), waterFBOs);

		this.picker = new MousePicker(scene.getCamera(), masterRenderer.getProjectionMatrix());
		scene.setPicker(picker);
		this.controls = new ControlsInGame();
	}

	public void render(FontType font, Loader loader) {
		checkInputs();
		saveMap(loader);
		move();
		scene.getEntities().updateWithFrustum(scene.getFrustum());
		masterRenderer.renderShadowMap(scene);
		renderParticles();
		renderWaterSurface();
		renderToScreen(font);
	}
	
	private void saveMap(Loader loader) {
		if (KeyboardGame.isKeyPressed(Keyboard.KEY_T)) {
			MapsWriter mapWriter = new MapsTXTWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(map.getEntities().values());
			map.setTerrains(map.getTerrains().values());
			mapWriter.write(map);
			System.out.println("save");
		}
	}
	
	private void checkInputs() {
		if (KeyboardGame.isKeyReleased(Keyboard.KEY_H)) {

			System.out.println("Key H released");
		}
		if (KeyboardGame.isKeyPressed(Keyboard.KEY_H)) {
			System.out.println("H");
			System.out.println("Key H pressed");
		} 
		if(KeyboardGame.isKeyPressed(Keyboard.KEY_Y)) {
			scene.spreadEntitiesOnHeights();
		}		
		
	}

	private void renderToScreen(FontType font) {
		waterFBOs.unbindCurrentFrameBuffer();
		multisampleFbo.bindFrameBuffer();
		masterRenderer.renderScene(scene, new Vector4f(0, -1, 0, 15));
		waterRenderer.render(scene.getWaters().values(), scene.getCamera(), scene.getSun());
		ParticleMaster.renderParticles(scene.getCamera());
		multisampleFbo.unbindFrameBuffer();
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
		PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
		guiRenderer.render(scene.getGuis().values());
		renderText(font);		
	}

	private void renderWaterSurface() {
		OGLUtils.clipDistance(true);
		renderWaterReflection();
		renderWaterRefraction();
		OGLUtils.clipDistance(false);
	}

	private void renderWaterReflection() {
		waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (scene.getCamera().getPosition().y - scene.getWaters().get("Water").getHeight());
		scene.getCamera().getPosition().y -= distance;
		scene.getCamera().invertPitch();
		masterRenderer.renderScene(scene, new Vector4f(0, 1, 0, -scene.getWaters().get("Water").getHeight()), true);
		scene.getCamera().getPosition().y += distance;
		scene.getCamera().invertPitch();
	}

	private void renderWaterRefraction() {
		waterFBOs.bindRefractionFrameBuffer();
		masterRenderer.renderScene(scene, new Vector4f(0, -1, 0, scene.getWaters().get("Water").getHeight() + 1f),
				true);
	}

	protected void renderParticles() {
		scene.getParticles().get("Cosmic").generateParticles();
		scene.getParticles().get("Star").generateParticles();
		ParticleMaster.update(scene.getCamera());
	}

	protected void renderText(FontType font) {
		GuiText fpsText = createFPSText(Math.round(1 / DisplayManager.getFrameTimeSeconds()), font);
		fpsText.setColour(1, 0, 0);
		GuiText coordsText = createPickerCoordsText(picker, font);
		coordsText.setColour(1, 0, 0);
		TextMaster.render();
		fpsText.remove();
		coordsText.remove();
	}

	protected GuiText createFPSText(float FPS, FontType font) {
		return new GuiText("FPS", "FPS: " + String.valueOf((int) FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
	}

	protected GuiText createPickerCoordsText(MousePicker picker, FontType font) {
		picker.update();
		String text = (String) String.valueOf(picker.getCurrentRay());
		return new GuiText("Coords", text, 1, font, new Vector2f(0.3f, 0.2f), 1f, true);
	}

	private void move() {
		controls.update(scene);
		scene.getCamera().move();
		scene.getPlayer().move(scene.getTerrains().values());
		scene.getAudioMaster().setListenerData(scene.getCamera().getPosition());
	}
	
	public MasterRendererSimple getMasterRenderer() {
		return this.masterRenderer;
	}

	public void cleanUp() {
		scene.getAudioMaster().cleanUp();
		scene.getEntities().clearAll();
		PostProcessing.cleanUp();
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		multisampleFbo.cleanUp();
		TextMaster.cleanUp();
		waterFBOs.cleanUp();
		guiRenderer.cleanUp();
		masterRenderer.cleanUp();
	}

}
