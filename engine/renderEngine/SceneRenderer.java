package renderEngine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
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

	private int mouseTimer = 0;
	private boolean isMouseClicked = false;

	public void init(Scene scene, Loader loader) {
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

		enviroRenderer.render(scene, masterRenderer, scene.getEntities().get("Cuby4"));
	}

	public void render(Scene scene, FontType font, Loader loader) {
		if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapsWriter mapWriter = new MapsTXTWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(map.getEntities().values());
			map.setTerrains(map.getTerrains().values());
			mapWriter.write(map);
			System.out.println("save");
		}

		move(scene);
		//enviroRenderer.render(scene, masterRenderer, scene.getEntities().get("Cuby4"));
		masterRenderer.renderShadowMap(scene);
		renderParticles(scene);
		renderWaterSurface(scene);
		renderToScreen(scene, font);
	}

	private void renderToScreen(Scene scene, FontType font) {
		boolean isMousePointed = false;
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
		/* intersection of entities with mouse ray */

		if (MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {	
			scene.addPointedEntity(picker.chooseObjectByRay(scene, masterRenderer));
			isMousePointed = true;
			
		}
		if (MouseGame.isOncePressed(MouseGame.RIGHT_CLICK)) {
			scene.clearPointedEntities();
		}
		
		if(isMousePointed) {
			for(Entity entity : scene.getPointedEntities().values()) {
				System.out.println(entity.getName());
				int power = 4;
				Vector3f rayDirection = picker.getCurrentRay();
				entity.increasePosition(power * rayDirection.x,
						power * rayDirection.y, power * rayDirection.z);
			}		
		}
	}


	private void renderWaterSurface(Scene scene) {
		OGLUtils.clipDistance(true);
		renderWaterReflection(scene);
		renderWaterRefraction(scene);
		OGLUtils.clipDistance(false);
	}

	private void renderWaterReflection(Scene scene) {
		waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (scene.getCamera().getPosition().y - scene.getWaters().get("Water").getHeight());
		scene.getCamera().getPosition().y -= distance;
		scene.getCamera().invertPitch();
		masterRenderer.renderScene(scene, new Vector4f(0, 1, 0, -scene.getWaters().get("Water").getHeight()), true);
		scene.getCamera().getPosition().y += distance;
		scene.getCamera().invertPitch();
	}

	private void renderWaterRefraction(Scene scene) {
		waterFBOs.bindRefractionFrameBuffer();
		masterRenderer.renderScene(scene, new Vector4f(0, -1, 0, scene.getWaters().get("Water").getHeight() + 1f),
				true);
	}

	protected void renderParticles(Scene scene) {
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

	private void move(Scene scene) {
		scene.getCamera().move();
		scene.getPlayer().move(scene.getTerrains().values());
		AudioMaster.setListenerData(scene.getCamera().getPosition().x, scene.getCamera().getPosition().y,
				scene.getCamera().getPosition().z);
	}

	public EntityRenderer getEntityRenderer() {
		return masterRenderer.getEntityRenderer();
	}

	public void cleanUp() {
		PostProcessing.cleanUp();
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		multisampleFbo.cleanUp();
		TextMaster.cleanUp();
		waterFBOs.cleanUp();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		masterRenderer.cleanUp();
	}

}
