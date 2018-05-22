package renderer.scene;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

import control.Controls;
import control.ControlsImpl;
import control.MousePicker;
import core.EngineDebug;
import object.particle.ParticleMaster;
import object.water.WaterFrameBuffers;
import renderer.MainRenderer;
import renderer.WaterRenderer;
import scene.Scene;
import shader.postprocess.Fbo;
import shader.postprocess.PostProcessing;
import shader.water.WaterShader;
import tool.GraphicUtils;

/**
 * Class that render scene objects and check controls. TODO: need to refactor
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public class GameSceneRenderer implements SceneRenderer {

	private MainRenderer mainRenderer;
	private WaterRenderer waterRenderer;
	private WaterFrameBuffers waterFBOs;
	private Fbo sceneFbo;
	private MousePicker mousePicker;
	private Scene scene;
	private Controls controls;
	private EngineDebug debug;

	@Override
	public void initialize(Scene scene) {
		this.scene = scene;
		mainRenderer = new MainRenderer(scene);
		int width = Display.getWidth();
		int height = Display.getHeight();
		ParticleMaster.init(scene.getCamera().getProjectionMatrix());
		sceneFbo = new Fbo().setSize(width, height).initialize();

		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init();

		waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		waterRenderer = new WaterRenderer(waterShader, scene.getCamera().getProjectionMatrix(), waterFBOs);

		mousePicker = new MousePicker(scene.getCamera());
		scene.setMousePicker(mousePicker);
		controls = new ControlsImpl();
		debug = new EngineDebug(scene);
		debug.initialize();
		render(true);
	}

	@Override
	public void render(boolean isPaused) {
		if (!isPaused) {
			checkInputs();
			move();
		}
		renderShadows();
		renderParticles();
		renderWaterSurface();
		renderScene();
		debug.update();
		renderUI();
	}
	
	private void checkInputs() {		
		controls.update(scene);
	}
	
	private void move() {
		scene.getPlayer().move(scene.getTerrains().getAll());
		scene.getCamera().move();
		if (scene.getPlayer().isMoved()) {
			recalcuteEntityNodesChildren();
			moveSoundListener();
		}
	}
	
	private void recalcuteEntityNodesChildren() {
		scene.getFrustumEntities().addEntityInNodes(scene.getPlayer());
	}
	
	private void moveSoundListener() {
		scene.getAudioSources().getMaster().setListenerData(scene.getCamera().getPosition());
	}
	
	private void renderShadows() {
		mainRenderer.renderShadows(scene);
	}

	private void renderScene() {
		waterFBOs.unbindCurrentFrameBuffer();
		sceneFbo.bind();
		GraphicUtils.clipDistance(true);
		mainRenderer.render(scene, new Vector4f(0, 1, 0, 15));
		ParticleMaster.renderParticles(scene.getCamera());
		GraphicUtils.clipDistance(false);	
		waterRenderer.render(scene.getWaters().getAll(), scene.getCamera(), scene.getSun());
		sceneFbo.unbind();
		PostProcessing.doPostProcessing(sceneFbo);
		mousePicker.update();
	}

	private void renderWaterSurface() {
		GraphicUtils.clipDistance(true);
		renderWaterReflection();
		renderWaterRefraction();
		GraphicUtils.clipDistance(false);
	}

	private void renderWaterReflection() {
		waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (scene.getCamera().getPosition().y - scene.getWaters().getByName("Water").getHeight());
		scene.getCamera().getPosition().y -= distance;
		scene.getCamera().invertPitch();
		mainRenderer.render(scene,
				new Vector4f(0, 1, 0, -scene.getWaters().getByName("Water").getHeight()));
		scene.getCamera().getPosition().y += distance;
		scene.getCamera().invertPitch();
	}

	private void renderWaterRefraction() {
		waterFBOs.bindRefractionFrameBuffer();
		mainRenderer.render(scene, new Vector4f(0, -1, 0, scene.getWaters().getByName("Water").getHeight() + 1f));
	}

	private void renderParticles() {
		scene.getParticles().get("Cosmic").generateParticles();
		scene.getParticles().get("Star").generateParticles();
		ParticleMaster.update(scene.getCamera());
	}
	
	private void renderUI() {
		scene.getUI().render();
	}

	@Override
	public MainRenderer getMainRenderer() {
		return mainRenderer;
	}

	@Override
	public void clean() {
		scene.clean();
		PostProcessing.clean();
		sceneFbo.clean();
		waterFBOs.clean();
		mainRenderer.clean();
	}

}
