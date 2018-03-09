package renderer.scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import control.Controls;
import control.IControls;
import control.KeyboardGame;
import control.MousePicker;
import core.EngineMain;
import core.debug.EngineDebug;
import core.display.DisplayManager;
import manager.scene.IObjectManager;
import map.objectMap.ObjectMapManager;
import map.writer.ILevelMapWriter;
import map.writer.LevelMapXMLWriter;
import object.gui.group.IGUIGroup;
import object.gui.gui.GUI;
import object.gui.gui.IGUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import object.particle.master.ParticleMaster;
import object.water.WaterFrameBuffers;
import primitive.texture.Texture2D;
import renderer.main.MainRenderer;
import renderer.water.WaterRenderer;
import scene.IScene;
import shader.postProcessing.Fbo;
import shader.postProcessing.PostProcessing;
import shader.water.WaterShader;
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
	
	private String statusGroupName = "statusGroup";

	@Override
	public void initialize(IScene scene) {
		this.scene = scene;
		mainRenderer = new MainRenderer(scene);
		ParticleMaster.init(mainRenderer.getProjectionMatrix());
		multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight());
		outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init();

		waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		waterRenderer = new WaterRenderer(waterShader, mainRenderer.getProjectionMatrix(), waterFBOs);

		mousePicker = new MousePicker(scene.getCamera(), mainRenderer.getProjectionMatrix());
		scene.setMousePicker(mousePicker);
		controls = new Controls();
		
		// GUI text info
		String fontName = "candara";
		fpsText = createFPSText(Math.round(1 / DisplayManager.getFrameTimeSeconds()), fontName);
		fpsText.setColor(1, 0, 0);
		coordsText = createPickerCoordsText(mousePicker, fontName);
		coordsText.setColor(1, 0, 0);
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		// debug texture
		Texture2D heightMap = scene.getTerrains().get("Terrain1").getMaterial().getHeightMap();
		Texture2D normalMap = scene.getTerrains().get("Terrain1").getMaterial().getNormalMap();
		GUITexture debugTexture1 = new GUITexture("debugTexture1", heightMap, new Vector2f(-0.5f, 0), new Vector2f(0.3f, 0.3f));
		GUITexture debugTexture2 = new GUITexture("debugTexture2", normalMap, new Vector2f(0.5f, 0), new Vector2f(0.3f, 0.3f));
		textureList.add(debugTexture1);
		textureList.add(debugTexture2);
		List<GUIText> textList = new ArrayList<GUIText>();
		textList.add(fpsText);
		textList.add(coordsText);
		
		String statusGUIName = "status";
		IGUI statusInterface = new GUI(statusGUIName, textureList, textList);
		scene.getUserInterface().getGroups().createEmpty(statusGroupName);
		scene.getUserInterface().getGroups().get(statusGroupName).add(statusInterface);
	}

	@Override
	public void render(boolean isPaused) {
		checkInputs();
		if (!isPaused) 
			move();
		mainRenderer.renderShadows(scene);
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
		
		IGUIGroup statusGUIGroup = scene.getUserInterface().getGroups().get(statusGroupName);
		
		if (EngineDebug.hasHardDebugPermission()) {
			if (!statusGUIGroup.getIsVisible())
				statusGUIGroup.show();
		} else {
			if (statusGUIGroup.getIsVisible())
				statusGUIGroup.hide();
		}
	}

	private void renderToScreen() {
		waterFBOs.unbindCurrentFrameBuffer();
		multisampleFbo.bindFrameBuffer();
		OGLUtils.clipDistance(true);
		mainRenderer.render(scene, new Vector4f(0, 1, 0, 15));
		ParticleMaster.renderParticles(scene.getCamera());
		OGLUtils.clipDistance(false);		
		waterRenderer.render(scene.getWaters().getAll(), scene.getCamera(), scene.getSun());
		multisampleFbo.unbindFrameBuffer();
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
		multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
		PostProcessing.doPostProcessing(outputFbo.getColorTexture(), outputFbo2.getColorTexture());
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
		mainRenderer.render(scene,
				new Vector4f(0, 1, 0, -scene.getWaters().getByName("Water").getHeight()), true);
		scene.getCamera().getPosition().y += distance;
		scene.getCamera().invertPitch();
	}

	private void renderWaterRefraction() {
		waterFBOs.bindRefractionFrameBuffer();
		mainRenderer.render(scene, new Vector4f(0, -1, 0, scene.getWaters().getByName("Water").getHeight() + 1f),
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
		if (scene.getPlayer().isMoved())
			scene.getFrustumEntities().addEntityInNodes(scene.getPlayer());
		scene.getAudioSources().getMaster().setListenerData(scene.getCamera().getPosition());
	}

	@Override
	public MainRenderer getMainRenderer() {
		return mainRenderer;
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
