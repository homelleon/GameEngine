package renderEngine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
import cameras.Camera;
import engineMain.DisplayManager;
import entities.Light;
import entities.Player;
import environmentMap.EnvironmentMapRenderer;
import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import maps.GameMap;
import maps.MapsTXTWriter;
import maps.MapsWriter;
import optimisations.MasterOptimisation;
import optimisations.Optimisation;
import particles.ParticleMaster;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import scene.Scene;
import textures.Texture;
import toolbox.MousePicker;
import toolbox.OGLUtils;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;

public class SceneRenderer {
	
	private MasterRenderer renderer;
	private WaterRenderer waterRenderer;
    protected EnvironmentMapRenderer enviroRenderer;
    protected GuiRenderer guiRenderer;
	private Texture environmentMap;
	private WaterFrameBuffers waterFBOs;
	protected Fbo multisampleFbo;	
    protected Fbo outputFbo;
    protected Fbo outputFbo2;
    protected MousePicker picker;
    protected Optimisation optimisation;
	
	public void render(Scene scene, Light sun, Camera camera, Player player, FontType font, Loader loader) {
		
		this.renderer = new MasterRenderer(loader, camera);
		this.enviroRenderer = new EnvironmentMapRenderer(renderer.getProjectionMatrix());
		this.guiRenderer = new GuiRenderer(loader);	
		this.optimisation = new MasterOptimisation(camera, renderer.getProjectionMatrix());
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		this.multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight());
		this.outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		this.outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init(loader);
		
		this.waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterFBOs);
		
		this.environmentMap = Texture.newEmptyCubeMap(128);
		
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		

		if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapsWriter mapWriter = new MapsTXTWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(map.getEntities().values());
			map.setTerrains(map.getTerrains().values());
			mapWriter.write(map);
			System.out.println("save");
		}
		
    	enviroRenderer.render(environmentMap, scene, camera);
    	move(scene, camera, player);	
		renderParticles(scene, camera, player);
		renderWaterSurface(scene, sun, camera);		
	    renderToScreen(scene, font, sun, camera);	
	}
	
	private void renderToScreen(Scene scene, FontType font, Light sun, Camera camera) {
		waterFBOs.unbindCurrentFrameBuffer();
		multisampleFbo.bindFrameBuffer();
		optimisation.optimize(camera, scene.getEntities().values(), scene.getTerrains().values(), scene.getVoxelGrids().values());
	    renderer.renderScene(scene, camera, new Vector4f(0, -1, 0, 15), environmentMap);
	    waterRenderer.render(scene.getWaters().values(), camera, sun);
	    ParticleMaster.renderParticles(camera);
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
	    guiRenderer.render(scene.getGuis().values());
	    renderText(font);
	}
	
	private void renderWaterSurface(Scene scene, Light sun, Camera camera) {
		OGLUtils.clipDistance(true);
		renderWaterReflection(scene, sun, camera);
		renderWaterRefraction(scene, sun, camera);
		OGLUtils.clipDistance(false);
	}
	
	private void renderWaterReflection(Scene scene, Light sun, Camera camera) {
		renderer.renderShadowMap(scene, sun, camera);
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - scene.getWaters().get("Water").getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderer.renderScene(scene, camera, new Vector4f(0, 1, 0, -scene.getWaters().get("Water").getHeight()), environmentMap);
		camera.getPosition().y += distance;
		camera.invertPitch();
	}
	
	private void renderWaterRefraction(Scene scene, Light sun, Camera camera) {
		waterFBOs.bindRefractionFrameBuffer();
		renderer.renderScene(scene, camera, new Vector4f(0, -1, 0, scene.getWaters().get("Water").getHeight()+1f), environmentMap);
	}
	
	protected void renderParticles(Scene scene, Camera camera, Player player) {
    	scene.getParticles().get("Cosmic").setPosition(player.getPosition());
    	scene.getParticles().get("Cosmic").generateParticles();
    	scene.getParticles().get("Star").generateParticles();
		ParticleMaster.update(camera);
    }
	
	protected void renderText(FontType font) {
    	GuiText fpsText = createFPSText(1 / DisplayManager.getFrameTimeSeconds(), font);
	    fpsText.setColour(1, 0, 0);
	    GuiText coordsText = createPickerCoordsText(picker, font);
	    coordsText.setColour(1, 0, 0);
	    TextMaster.render();
	    fpsText.remove();
	    coordsText.remove();	
    } 
	
	protected GuiText createFPSText(float FPS, FontType font) {
    	return new GuiText("FPS", "FPS: " + String.valueOf((int)FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
	}
    
    protected GuiText createPickerCoordsText(MousePicker picker, FontType font) {
    	picker.update();
		String text = (String) String.valueOf(picker.getCurrentRay());
		return new GuiText("Coords", text, 1, font, new Vector2f(0.3f, 0.2f), 1f, true);    	
    }
    
    private void move(Scene scene, Camera camera, Player player) {
		camera.move();	
		player.move(scene.getTerrains().values());
		AudioMaster.setListenerData(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);
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
		renderer.cleanUp();
		//enviroRenderer.cleanUp;
	}
	
	

}
