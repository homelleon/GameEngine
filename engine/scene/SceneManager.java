package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.CameraPlayer;
import entities.EntitiesManager;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.PlayerTextured;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import gameMain.Game;
import gameMain.MyGame;
import guis.GuiManager;
import guis.GuiRenderer;
import guis.GuiTexture;
import maps.GameMap;
import maps.MapsLoader;
import maps.MapsTXTLoader;
import maps.MapsTXTWriter;
import maps.MapsWriter;
import models.TexturedModel;
import optimisations.CutOptimisation;
import optimisations.Optimisation;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticlesManager;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public abstract class SceneManager {
	
    protected Game game = new MyGame();
	
    protected Loader loader;
    protected MasterRenderer renderer;
    protected Source ambientSource;
	
    protected String cameraName;
    protected String playerName;
	
    protected Map<String, Camera> cameras;
    protected Map<String, Player> players;
    protected GameMap map;
    protected List<GuiTexture> guis;
    protected GuiRenderer guiRenderer;
    protected List<ParticleSystem> pSystem;
    protected List<Terrain> terrains;
    protected List<Entity> entities;
    protected List<Entity> normalMapEntities;
    protected List<WaterTile> waters;
    protected List<Light> lights;
    protected FontType font;
    protected WaterFrameBuffers waterFBOs;
    protected Fbo multisampleFbo;	
    protected Fbo outputFbo;
    protected Fbo outputFbo2;
    protected Light sun;
    protected WaterRenderer waterRenderer;	
    protected GameTime time;
    protected MousePicker picker;
    protected Optimisation optimisation;
    
    protected boolean mapIsLoaded = false;
    
    protected void loadMap(String name) {
		/*--------------PRE LOAD TOOLS-------------*/
		this.loader = new Loader();
		/*---------------MAP-----------------------*/
		MapsLoader mapLoader = new MapsTXTLoader();
		this.map = mapLoader.loadMap(name, loader);	
		this.mapIsLoaded = true;
	}
    
    protected GameMap getMap() {
		return map;
	}
    
    protected void init() {
    	if (!this.mapIsLoaded) {
			loadMap("map");
		}		
    }
    
    protected void render() {
    	game.onUpdate();  
    	/*
    	 * need to be deleted:
    	 */
		cameras.get(cameraName).move();	
		AudioMaster.setListenerData(cameras.get(cameraName).getPosition().x, cameras.get(cameraName).getPosition().y, cameras.get(cameraName).getPosition().z);
    }
    
    protected void renderReflectionTexture() {
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (cameras.get(cameraName).getPosition().y - waters.get(0).getHeight());
		cameras.get(cameraName).getPosition().y -= distance;
		cameras.get(cameraName).invertPitch();
		renderer.processEntity(players.get(playerName));
		renderer.renderScene(entities, normalMapEntities, terrains, lights, 
				cameras.get(cameraName), new Vector4f(0, 1, 0, -waters.get(0).getHeight()));
		cameras.get(cameraName).getPosition().y += distance;
		cameras.get(cameraName).invertPitch();
    }
    
    protected void renderRefractionTexture() {
    	waterFBOs.bindRefractionFrameBuffer();
		renderer.processEntity(players.get(playerName));
		renderer.renderScene(entities, normalMapEntities, terrains, lights, 
				cameras.get(cameraName), new Vector4f(0, -1, 0, waters.get(0).getHeight()+1f));
    }
    
    protected void renderToScreen() {
    	GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		waterFBOs.unbindCurrentFrameBuffer();
	    
		multisampleFbo.bindFrameBuffer();
		optimisation.optimize(cameras.get(cameraName), entities, terrains);
	    renderer.processEntity(players.get(playerName));
	    renderer.renderScene(entities, normalMapEntities, terrains,	lights, 
	    		cameras.get(cameraName), new Vector4f(0, -1, 0, 15));
	    waterRenderer.render(waters, cameras.get(cameraName), sun);
	    ParticleMaster.renderParticles(cameras.get(cameraName));
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
	    guiRenderer.render(guis);
	    renderText();
    }
    
    protected void renderParticles() {
    	pSystem.get(0).generateParticles(players.get(playerName).getPosition());
		pSystem.get(1).generateParticles(new Vector3f(50,terrains.get(0).getHeightOfTerrain(50, 50),50));
		ParticleMaster.update(cameras.get(cameraName));
    }
    
    protected void cleanUp() {
    	PostProcessing.cleanUp();
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		multisampleFbo.cleanUp();
		TextMaster.cleanUp();
		ambientSource.delete();
		waterFBOs.cleanUp();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
    }
 
    
    protected GUIText createFPSText(float FPS) {
    	return new GUIText("FPS: " + String.valueOf((int)FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
	}
    
    protected GUIText createPickerCoordsText(MousePicker picker) {
    	picker.update();
		String text = (String) String.valueOf(picker.getCurrentRay());
		return new GUIText(text, 1, font, new Vector2f(0.3f, 0.2f), 1f, true);    	
    }
    
    protected void renderText() {
    	GUIText fpsText = createFPSText(1 / DisplayManager.getFrameTimeSeconds());
	    fpsText.setColour(1, 0, 0);
	    GUIText coordsText = createPickerCoordsText(picker);
	    coordsText.setColour(1, 0, 0);
	    TextMaster.render();
	    fpsText.remove();
	    coordsText.remove();	
    }
    
    
	
    protected void spreadOnHeights(List<Entity> entities) {
		if (!entities.isEmpty()) {
			for(Entity entity : entities){
				float terrainHeight = 0;
				
				for(Terrain terrain : terrains){
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
	}

}
