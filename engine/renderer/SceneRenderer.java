package renderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import core.EngineMain;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.gui.font.FontType;
import object.gui.group.GUIGroup;
import object.gui.group.GUIGroupInterface;
import object.gui.gui.GUI;
import object.gui.gui.GUIInterface;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import object.input.Controls;
import object.input.ControlsInterface;
import object.input.KeyboardGame;
import object.map.modelMap.ModelMap;
import object.map.modelMap.ModelMapWriterInterface;
import object.map.modelMap.ModelMapXMLWriter;
import object.particle.master.ParticleMaster;
import object.scene.scene.SceneInterface;
import object.water.WaterFrameBuffers;
import renderer.objectRenderer.GUITextureRenderer;
import renderer.objectRenderer.WaterRenderer;
import shader.postProcessing.Fbo;
import shader.postProcessing.PostProcessing;
import shader.water.WaterShader;
import tool.MousePicker;
import tool.openGL.OGLUtils;

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
	private FontType defaultFont;

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
			ModelMapWriterInterface mapWriter = new ModelMapXMLWriter();
			ModelMap map = new ModelMap("newMap", loader);
			map.setEntities(scene.getEntities().getAll());
			map.setTerrains(scene.getTerrains().getAll());
			mapWriter.write(map, loader);
			System.out.println("save");
		}
	}
	
	private void checkInputs() {
		if(KeyboardGame.isKeyPressed(EngineSettings.KEY_PAUSE)) {
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
		FontType font = scene.getUserInterface().getComponent().getTexts().getFonts().get("candara");
		GUIText fpsText = createFPSText(Math.round(1 / DisplayManager.getFrameTimeSeconds()), font);
		fpsText.setColour(1, 0, 0);		
		GUIText coordsText = createPickerCoordsText(picker, font);
		coordsText.setColour(1, 0, 0);
		List<GUIInterface> statusGUIList = new ArrayList<GUIInterface>();
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		List<GUIText> textList = new ArrayList<GUIText>();
		textList.add(fpsText);
		textList.add(coordsText);
		GUIInterface statusInterface = new GUI("status", textureList, textList);
		statusGUIList.add(statusInterface);
		GUIGroupInterface statusGUIGroup = new GUIGroup("statusGroup", statusGUIList);
		statusGUIList.add(statusInterface);
		scene.getUserInterface().addGUIGroup(statusGUIGroup);
		scene.getUserInterface().getGUIGroup("statusGroup").showAll();
		scene.getUserInterface().render();
	}

	protected GUIText createFPSText(float FPS, FontType font) {
		
		GUIText guiText = new GUIText("FPS", "FPS: " + String.valueOf((int) FPS), 2f, font , new Vector2f(0.65f, 0), 0.5f, true);
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
