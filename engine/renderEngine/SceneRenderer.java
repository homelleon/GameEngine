package renderEngine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import engineMain.DisplayManager;
import engineMain.EngineMain;
import font.fontMeshCreator.FontType;
import font.fontMeshCreator.GUIText;
import inputs.Controls;
import inputs.ControlsInterface;
import inputs.KeyboardGame;
import maps.GameMap;
import maps.MapsWriterInterface;
import maps.MapsXMLWriter;
import objects.gui.guiTextures.GUITextureRenderer;
import objects.particles.ParticleMaster;
import objects.water.WaterFrameBuffers;
import objects.water.WaterRenderer;
import objects.water.WaterShader;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import scene.ES;
import scene.SceneInterface;
import toolbox.MousePicker;
import toolbox.OGLUtils;

/**
 * Class that render scene objects and check controls. 
 * TODO: need to refactor
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public class SceneRenderer {

	private MasterRenderer masterRenderer;
	private WaterRenderer waterRenderer;
	private GUITextureRenderer guiRenderer;
	private WaterFrameBuffers waterFBOs;
	private Fbo multisampleFbo;
	private Fbo outputFbo;
	private Fbo outputFbo2;
	private MousePicker picker;
	private SceneInterface scene;
	private ControlsInterface controls;

	public void init(SceneInterface scene, Loader loader) {
		this.scene = scene;
		this.masterRenderer = new MasterRenderer(loader, scene.getCamera());
		this.guiRenderer = new GUITextureRenderer(loader);
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
		this.controls = new Controls();
	}

	public void render(Loader loader, boolean isPaused) {
		checkInputs();
		saveMap(loader);
		if(!isPaused) {
			move();
		}
		scene.getEntities().updateWithFrustum(scene.getFrustum());
		masterRenderer.renderShadowMap(scene);
		renderParticles();
		renderWaterSurface();
		renderToScreen();
	}
	
	private void saveMap(Loader loader) {
		if (KeyboardGame.isKeyPressed(Keyboard.KEY_T)) {
			MapsWriterInterface mapWriter = new MapsXMLWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(scene.getEntities().getAll());
			map.setTerrains(scene.getTerrains().getAll());
			mapWriter.write(map, loader);
			System.out.println("save");
		}
	}
	
	private void checkInputs() {
		if(KeyboardGame.isKeyPressed(ES.KEY_PAUSE)) {
			EngineMain.pauseEngine(!EngineMain.getIsEnginePaused());
		}
		
		if (KeyboardGame.isKeyReleased(Keyboard.KEY_H)) {

			System.out.println("Key H released");
		}
		if (KeyboardGame.isKeyPressed(Keyboard.KEY_H)) {
			System.out.println("H");
			System.out.println("Key H pressed");
		} 		
		
	}

	private void renderToScreen() {
		waterFBOs.unbindCurrentFrameBuffer();
		multisampleFbo.bindFrameBuffer();
		masterRenderer.renderScene(scene, new Vector4f(0, -1, 0, 15));
		waterRenderer.render(scene.getWaters().getAll(), scene.getCamera(), scene.getSun());
		ParticleMaster.renderParticles(scene.getCamera());
		multisampleFbo.unbindFrameBuffer();
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
		PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
		guiRenderer.render(scene.getGuis().getAll());
		renderText();		
	}

	private void renderWaterSurface() {
		OGLUtils.clipDistance(true);
		renderWaterReflection();
		renderWaterRefraction();
		OGLUtils.clipDistance(false);
	}

	private void renderWaterReflection() {
		waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (scene.getCamera().getPosition().y - scene.getWaters().getByName("Water").getHeight());
		scene.getCamera().getPosition().y -= distance;
		scene.getCamera().invertPitch();
		masterRenderer.renderScene(scene, new Vector4f(0, 1, 0, -scene.getWaters().getByName("Water").getHeight()), true);
		scene.getCamera().getPosition().y += distance;
		scene.getCamera().invertPitch();
	}

	private void renderWaterRefraction() {
		waterFBOs.bindRefractionFrameBuffer();
		masterRenderer.renderScene(scene, new Vector4f(0, -1, 0, scene.getWaters().getByName("Water").getHeight() + 1f),
				true);
	}

	protected void renderParticles() {
		scene.getParticles().getByName("Cosmic").generateParticles();
		scene.getParticles().getByName("Star").generateParticles();
		ParticleMaster.update(scene.getCamera());
	}

	protected void renderText() {
		GUIText fpsText = createFPSText(Math.round(1 / DisplayManager.getFrameTimeSeconds()), scene.getUserInterface().getComponent().getTexts().getMaster().getFont());
		fpsText.setColour(1, 0, 0);
		GUIText coordsText = createPickerCoordsText(picker, scene.getUserInterface().getComponent().getTexts().getMaster().getFont());
		coordsText.setColour(1, 0, 0);
		scene.getUserInterface().getComponent().getTexts().getMaster().render();
		fpsText.remove();
		coordsText.remove();
	}

	protected GUIText createFPSText(float FPS, FontType font) {
		GUIText guiText = new GUIText("FPS", "FPS: " + String.valueOf((int) FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
		scene.getUserInterface().getComponent().getTexts().add(guiText);
		return guiText;
	}

	protected GUIText createPickerCoordsText(MousePicker picker, FontType font) {
		picker.update();
		String text = (String) String.valueOf(picker.getCurrentRay());
		GUIText guiText = new GUIText("Coords", text, 1, font, new Vector2f(0.3f, 0.2f), 1f, true);
		scene.getUserInterface().getComponent().getTexts().add(guiText);
		return guiText; 
	}

	private void move() {
		controls.update(scene);
		scene.getCamera().move();
		scene.getPlayer().move(scene.getTerrains().getAll());
		scene.getAudioSources().getMaster().setListenerData(scene.getCamera().getPosition());
	}
	
	public MasterRenderer getMasterRenderer() {
		return this.masterRenderer;
	}

	public void cleanUp() {
		scene.cleanUp();
		PostProcessing.cleanUp();
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		multisampleFbo.cleanUp();
		waterFBOs.cleanUp();
		guiRenderer.cleanUp();
		masterRenderer.cleanUp();
	}

}
