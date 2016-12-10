package scene;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
import audio.Source;
import engineMain.DisplayManager;
import entities.Entity;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import gameMain.Game;
import gameMain.MyGame;
import guis.GuiRenderer;
import maps.GameMap;
import maps.MapsLoader;
import maps.MapsTXTLoader;
import optimisations.Optimisation;
import particles.ParticleMaster;
import particles.ParticleSystem;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import toolbox.MousePicker;
import voxels.Chunk;
import water.WaterFrameBuffers;
import water.WaterRenderer;

public abstract class SceneManager {
	
    protected Game game = new MyGame();
	
    protected Loader loader;
    protected MasterRenderer renderer;
    protected Source ambientSource;
	
    protected String cameraName;
    protected String playerName;
	
    protected Collection<Chunk> chunks;
    protected GameMap map;
    protected GuiRenderer guiRenderer;
    protected FontType font;
    protected WaterFrameBuffers waterFBOs;
    protected Fbo multisampleFbo;	
    protected Fbo outputFbo;
    protected Fbo outputFbo2;
    protected WaterRenderer waterRenderer;	
    protected GameTime time;
    protected MousePicker picker;
    protected Optimisation optimisation;
    
    protected boolean mapIsLoaded = false;
    protected boolean isPaused = false;
    
    public void loadMap(String name) {
		/*--------------PRE LOAD TOOLS-------------*/
		this.loader = Loader.getInstance();
		/*---------------MAP-----------------------*/
		MapsLoader mapLoader = new MapsTXTLoader();
		this.map = mapLoader.loadMap(name, loader);	
		this.mapIsLoaded = true;
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
		map.getCameras().get(cameraName).move();	
		AudioMaster.setListenerData(map.getCameras().get(cameraName).getPosition().x, map.getCameras().get(cameraName).getPosition().y, map.getCameras().get(cameraName).getPosition().z);
    }
    
    protected void renderReflectionTexture() {
    	renderer.renderShadowMap(map.getEntities().values(), map.getLights().get("Sun"), map.getCameras().get(cameraName));
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (map.getCameras().get(cameraName).getPosition().y - map.getWaters().get("Water").getHeight());
		map.getCameras().get(cameraName).getPosition().y -= distance;
		map.getCameras().get(cameraName).invertPitch();
		renderer.processEntity(map.getPlayers().get(playerName));
		renderer.renderScene(map.getEntities().values(), map.getTerrains().values(), chunks, map.getLights().values(), 
				map.getCameras().get(cameraName), new Vector4f(0, 1, 0, -map.getWaters().get("Water").getHeight()));
		map.getCameras().get(cameraName).getPosition().y += distance;
		map.getCameras().get(cameraName).invertPitch();
    }
    
    protected void renderRefractionTexture() {
    	waterFBOs.bindRefractionFrameBuffer();
		renderer.processEntity(map.getPlayers().get(playerName));
		renderer.renderScene(map.getEntities().values(), map.getTerrains().values(), chunks, map.getLights().values(), 
				map.getCameras().get(cameraName), new Vector4f(0, -1, 0, map.getWaters().get("Water").getHeight()+1f));
    }
    
    protected void renderToScreen() {
    	
    	GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		waterFBOs.unbindCurrentFrameBuffer();
	    
		multisampleFbo.bindFrameBuffer();
		optimisation.optimize(map.getCameras().get(cameraName), map.getEntities().values(), map.getTerrains().values());
	    renderer.renderScene(map.getEntities().values(), map.getTerrains().values(), chunks, map.getLights().values(), 
	    		map.getCameras().get(cameraName), new Vector4f(0, -1, 0, 15));
	    waterRenderer.render(map.getWaters().values(), map.getCameras().get(cameraName), map.getLights().get("Sun"));
	    ParticleMaster.renderParticles(map.getCameras().get(cameraName));
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
	    guiRenderer.render(map.getGuis().values());
	    renderText();
    }
    
    protected void renderParticles() {
    	map.getParticles().get("Cosmic").setPosition(map.getPlayers().get(playerName).getPosition());
    	map.getParticles().get("Cosmic").generateParticles();
		map.getParticles().get("Star").generateParticles();
		ParticleMaster.update(map.getCameras().get(cameraName));
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
    	
    protected void spreadEntitiesOnHeights(Collection<Entity> entities) {
		if (!map.getEntities().isEmpty()) {
			for(Entity entity : entities){
				float terrainHeight = 0;
				
				for(Terrain terrain : map.getTerrains().values()){
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
	}
    
    protected void spreadParitclesOnHeights(Collection<ParticleSystem> systems) {
		if (!systems.isEmpty()) {
			for(ParticleSystem system : systems){
				float terrainHeight = 0;
				
				for(Terrain terrain : map.getTerrains().values()){
					terrainHeight += terrain.getHeightOfTerrain(system.getPosition().x, system.getPosition().z);
				}
				system.setPosition(new Vector3f(system.getPosition().x, terrainHeight, system.getPosition().z));
			}
		}
	}
    
    
    public void setTerrainWiredFrame(boolean value) {
		renderer.setTerrainWiredFrame(value);
	}
	
	public void setEntityWiredFrame(boolean value) {
		renderer.setEntityWiredFrame(value);
	}
	
	public void setScenePaused(boolean value) {
		isPaused = value;
	}		
	
    public GameMap getMap() {
		return map;
	}

}
