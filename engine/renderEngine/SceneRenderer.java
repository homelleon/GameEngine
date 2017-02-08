package renderEngine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMasterBuffered;
import engineMain.DisplayManager;
import entities.Entity;
import environmentMap.EnvironmentMapRenderer;
import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import inputs.MouseGame;
import maps.GameMap;
import maps.MapsTXTWriter;
import maps.MapsWriter;
import particles.ParticleMaster;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import scene.ES;
import scene.Scene;
import toolbox.MousePicker;
import toolbox.OGLUtils;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;

public class SceneRenderer {

	private MasterRenderer masterRenderer;
	private WaterRenderer waterRenderer;
	protected EnvironmentMapRenderer enviroRenderer;
	protected GuiRenderer guiRenderer;
	private WaterFrameBuffers waterFBOs;
	protected Fbo multisampleFbo;
	protected Fbo outputFbo;
	protected Fbo outputFbo2;
	protected MousePicker picker;
	protected Scene scene;

	private int mouseTimer = 0;
	private boolean isMouseClicked = false;

	public void init(Scene scene, Loader loader) {
		this.scene = scene;
		this.masterRenderer = new MasterRenderer(loader, scene.getCamera());
		this.enviroRenderer = new EnvironmentMapRenderer();
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

		enviroRenderer.render(scene, masterRenderer, scene.getEntities().getByName("Cuby4"));
	}

	public void render(FontType font, Loader loader) {
		if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapsWriter mapWriter = new MapsTXTWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(map.getEntities().values());
			map.setTerrains(map.getTerrains().values());
			mapWriter.write(map);
			System.out.println("save");
		}

		move();
		//enviroRenderer.render(scene, masterRenderer, scene.getEntities().get("Cuby4"));
		masterRenderer.renderShadowMap(scene);
		renderParticles();
		renderWaterSurface();
		renderToScreen(font);
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
		checkControls();		
	}
	
	private void checkControls() {
		boolean isMousePointed = false;
		/* intersection of entities with mouse ray */
		//TODO: make class for control
		if (MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {	
			Entity pointedEntity = picker.chooseObjectByRay(scene, masterRenderer);
			if(pointedEntity != null) {
				scene.getEntities().addPointed(pointedEntity);
			}
			isMousePointed = true;			
		}
		
		if (MouseGame.isOncePressed(MouseGame.RIGHT_CLICK)) {
			scene.getEntities().clearPointed();
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_FORWARD)) {
			scene.getEntities().getPointed().forEach(i -> i.move(1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_BACKWARD)) {
			scene.getEntities().getPointed().forEach(i -> i.move(-1, 0));
		}		
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_LEFT)) {
			scene.getEntities().getPointed().forEach(i -> i.move(0, 1));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_RIGHT)) {
			scene.getEntities().getPointed().forEach(i -> i.move(0, -1));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_UP)) {
			scene.getEntities().getPointed().forEach(i -> i.increasePosition(0, 1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_DOWN)) {
			scene.getEntities().getPointed().forEach(i -> i.increasePosition(0, -1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_ROTATE_LEFT)) {
			scene.getEntities().getPointed().forEach(i -> i.increaseRotation(0, 1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_ROTATE_RIGHT)) {
			scene.getEntities().getPointed().forEach(i -> i.increaseRotation(0, -1, 0));
		}
		
		/* move in ray direction */
		if(isMousePointed) {
			for(Entity entity : scene.getEntities().getPointed()) {
				System.out.println(entity.getName());
				int power = 4;
				Vector3f rayDirection = picker.getCurrentRay();
				entity.increasePosition(power * rayDirection.x,
						power * rayDirection.y, power * rayDirection.z);
			}		
		}
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
		scene.getCamera().move();
		scene.getPlayer().move(scene.getTerrains().values());
		scene.getAudioMaster().setListenerData(scene.getCamera().getPosition());
	}

	public EntityRenderer getEntityRenderer() {
		return masterRenderer.getEntityRenderer();
	}

	public void cleanUp() {
		scene.getAudioMaster().cleanUp();
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
