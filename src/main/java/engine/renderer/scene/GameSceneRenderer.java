package renderer.scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import core.EngineMain;
import core.display.DisplayManager;
import manager.scene.IObjectManager;
import map.objectMap.ObjectMapManager;
import map.writer.ILevelMapWriter;
import map.writer.LevelMapXMLWriter;
import object.gui.element.object.GUIObject;
import object.gui.gui.GUI;
import object.gui.gui.IGUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import object.input.Controls;
import object.input.IControls;
import object.input.KeyboardGame;
import object.particle.master.ParticleMaster;
import object.scene.IScene;
import object.water.WaterFrameBuffers;
import renderer.main.MainRenderer;
import renderer.water.WaterRenderer;
import shader.postProcessing.Fbo;
import shader.postProcessing.PostProcessing;
import shader.water.WaterShader;
import tool.MousePicker;
import tool.math.vector.Vector2f;
import tool.openGL.OGLUtils;

/**
 * Class that render scene objects and check controls. TODO: need to refactor
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public class GameSceneRenderer implements ISceneRenderer {

	private MainRenderer mainRenderer;
	private WaterRenderer waterRenderer;
	private WaterFrameBuffers waterFBOs;
	private Fbo multisampleFbo;
	private Fbo outputFbo;
	private Fbo outputFbo2;
	private MousePicker mousePicker;
	private IScene scene;
	private IControls controls;
	GUIText fpsText;
	GUIText coordsText;

	@Override
	public void initialize(IScene scene) {
		this.scene = scene;
		this.mainRenderer = new MainRenderer(scene);
		ParticleMaster.init(mainRenderer.getProjectionMatrix());
		this.multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight());
		this.outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		this.outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init();

		this.waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(waterShader, mainRenderer.getProjectionMatrix(), waterFBOs);

		this.mousePicker = new MousePicker(scene.getCamera(), mainRenderer.getProjectionMatrix());
		this.scene.setMousePicker(mousePicker);
		this.controls = new Controls();
		
		//GUI text info
		String fontName = "candara";
		fpsText = createFPSText(Math.round(1 / DisplayManager.getFrameTimeSeconds()), fontName);
		fpsText.setColor(1, 0, 0);
		coordsText = createPickerCoordsText(mousePicker, fontName);
		coordsText.setColor(1, 0, 0);
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		List<GUIText> textList = new ArrayList<GUIText>();
		textList.add(fpsText);
		textList.add(coordsText);
		String statusGroupName = "statusGroup";
		String statusGUIName = "status";
		IGUI statusInterface = new GUI(statusGUIName, textureList, textList);
		scene.getUserInterface().getGroups().createEmpty(statusGroupName);
		scene.getUserInterface().getGroups().get(statusGroupName).add(statusInterface);
		((GUIObject) scene.getUserInterface().getGroups().get(statusGroupName)).show();
	}

	@Override
	public void render(boolean isPaused) {
		checkInputs();
		if (!isPaused) {
			move();
		}
		mainRenderer.renderShadowMap(scene);
		renderParticles();
		renderWaterSurface();
		renderToScreen();
	}

	private void checkInputs() {		
		if (KeyboardGame.isKeyPressed(Keyboard.KEY_T)) {
			EngineMain.pauseEngine(true);
			ILevelMapWriter mapWriter = new LevelMapXMLWriter();
			IObjectManager map = new ObjectMapManager();
			map.getEntities().addAll(scene.getEntities().getAll());
			map.getTerrains().addAll(scene.getTerrains().getAll());
			mapWriter.write(map);
			EngineMain.pauseEngine(false);
		}

	}

	private void renderToScreen() {
		waterFBOs.unbindCurrentFrameBuffer();
		multisampleFbo.bindFrameBuffer();
		mainRenderer.renderScene(scene, new Vector4f(0, -1, 0, 15));
		waterRenderer.render(scene.getWaters().getAll(), scene.getCamera(), scene.getSun());
		ParticleMaster.renderParticles(scene.getCamera());
		multisampleFbo.unbindFrameBuffer();
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
		PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
		renderGUI();
		mousePicker.update();
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
		mainRenderer.renderScene(scene, new Vector4f(0, 1, 0, -scene.getWaters().getByName("Water").getHeight()),
				true);
		scene.getCamera().getPosition().y += distance;
		scene.getCamera().invertPitch();
	}

	private void renderWaterRefraction() {
		waterFBOs.bindRefractionFrameBuffer();
		mainRenderer.renderScene(scene, new Vector4f(0, -1, 0, scene.getWaters().getByName("Water").getHeight() + 1f),
				false);
	}

	private void renderParticles() {
		scene.getParticles().get("Cosmic").generateParticles();
		scene.getParticles().get("Star").generateParticles();
		ParticleMaster.update(scene.getCamera());
	}

	private void renderGUI() {
		float fps = Math.round(1 / DisplayManager.getFrameTimeSeconds());
		scene.getUserInterface().getComponent().getTexts()
			.changeContent(fpsText.getName(), "FPS: " + String.valueOf((int) fps));
		String coords = String.valueOf(mousePicker.getCurrentRay());
		scene.getUserInterface().getComponent().getTexts()
			.changeContent(coordsText.getName(), coords);
		scene.getUserInterface().render();
	}

	private GUIText createFPSText(float FPS, String fontName) {
		GUIText guiText = new GUIText("FPS", "FPS: " + String.valueOf((int) FPS), 2f, fontName, new Vector2f(0.65f, 0.9f),
				0.5f, true);
		scene.getUserInterface().getComponent().getTexts().add(guiText);
		return guiText;
	}

	private GUIText createPickerCoordsText(MousePicker picker, String fontName) {
		String text = String.valueOf(picker.getCurrentRay());
		GUIText guiText = new GUIText("Coords", text, 1, fontName, new Vector2f(0.3f, 0.8f), 1f, true);
		scene.getUserInterface().getComponent().getTexts().add(guiText);
		return guiText;
	}

	private void move() {
		controls.update(scene);
		scene.getPlayer().move(scene.getTerrains().getAll());
		scene.getCamera().move();
		if(scene.getPlayer().isMoved()) {
			scene.getEntities().addEntityInNodes(scene.getPlayer());
		}
		scene.getAudioSources().getMaster().setListenerData(scene.getCamera().getPosition());
	}

	@Override
	public MainRenderer getMainRenderer() {
		return this.mainRenderer;
	}

	@Override
	public void clean() {
		scene.clean();
		PostProcessing.clean();
		outputFbo.clean();
		outputFbo2.clean();
		multisampleFbo.clean();
		waterFBOs.clean();
		mainRenderer.clean();
	}

}
