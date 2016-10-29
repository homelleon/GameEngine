package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import entities.EntitiesManager;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiManager;
import guis.GuiRenderer;
import guis.GuiTexture;
import maps.GameMap;
import maps.MapFileLoader;
import maps.MapFileWriter;
import maps.MapLoadable;
import maps.MapWriteable;
import models.TexturedModel;
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

public class SceneRenderer implements WorldGethable {

	public static boolean gamePaused = false;	
	private Loader loader;
	private MasterRenderer renderer;
	private Source ambientSource;
	
	private boolean isMidday = true;
	
	//TODO: Delete unnecessary objects
	private List<GameMap> maps;
	private List<GuiTexture> guis;
	private GuiRenderer guiRenderer;
	private List<ParticleSystem> pSystem;
	private List<Terrain> terrains;
	private List<Entity> entities;
	private List<Entity> normalMapEntities;
	private List<WaterTile> waters;
	FontType font;
	WaterFrameBuffers waterFBOs;
	private Fbo multisampleFbo;	
	private Fbo outputFbo;
	private Fbo outputFbo2;
	private Player player;
	private List<Light> lights;
	private Light sun;
	private WaterRenderer waterRenderer;
	private Camera camera;
	private GameTime time;
	private MousePicker picker;
	
	public SceneRenderer() {
		
		//***************PRE LOAD TOOLS*************//
		this.loader = new Loader();
		MapLoadable mapLoader = new MapFileLoader();
		GameMap map = mapLoader.loadMap("map1", loader);
		this.maps = new ArrayList<GameMap>();
		maps.add(map);
		
		//***************TERRAIN********************//
		
		this.terrains = map.getTerrains();

        //***********GAME OBJECTS****************//
		
		this.entities = EntitiesManager.createEntities(loader);
		this.normalMapEntities = EntitiesManager.createNormalMappedEntities(loader);
		
		entities.addAll(map.getEntities());

		
		spreadOnHeights(entities);
		spreadOnHeights(normalMapEntities);
		for(Entity entity : normalMapEntities) {
			if(entity.getName() == "boulder") {
				entity.increasePosition(0, 20, 0);
			}
		}
		
		//***********LIGHTS****************//
		this.lights = new ArrayList<Light>();
		this.sun = new Light(new Vector3f(100000,1500000,-100000),new Vector3f(1.3f,1.3f,1.3f));
		lights.add(sun);
		//lights.add(new Light(new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(0, 0.01f, 0.002f)));
		
		//***********PLAYER****************//
		TexturedModel cubeModel = SceneObjectTools.loadStaticModel("cube", "cube1", loader);	
		this.player = new Player(cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		this.camera = new Camera(player);	
		this.time = new GameTime(10);
		
		this.renderer = new MasterRenderer(loader, camera);		
		
		//**************PARTICLES***************//
		this.pSystem = ParticlesManager.createParticleSystem(loader);		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		//******************FBO*******************//
		
		this.multisampleFbo = new Fbo(Display.getWidth(),Display.getHeight());
		this.outputFbo = new Fbo(Display.getWidth(),Display.getHeight(), Fbo.DEPTH_TEXTURE);
		this.outputFbo2 = new Fbo(Display.getWidth(),Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init(loader);

		//*******************FONTS*************//
		TextMaster.init(loader);
		this.font = 
				new FontType(loader.loadTexture(Settings.FONT_PATH, "candara"),
						new File(Settings.FONT_PATH + "candara.fnt"));
		GUIText text = new GUIText("This is an Alfa-version of the game engine", 
				3, font, new Vector2f(0.25f, 0), 0.5f, true);
		
		text.setColour(1, 0, 0);
		//*******************AUDIO*************//
		
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		this.ambientSource = new Source("birds", "forest.wav", 200);
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f);
		ambientSource.play();
		ambientSource.setPosition(10, 20, 10);			
		
		//***************GUI***********//
		this.guis = GuiManager.createGui(loader);	
		this.guiRenderer = new GuiRenderer(loader);		
		//**************WATER***********************//
		this.waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterFBOs);
		this.waters = new ArrayList<WaterTile>(); 
		waters.add(new WaterTile(0, 0, -4, 1000));
		waters.stream().forEach((i) -> i.setTilingSize(0.1f));
		waters.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waters.stream().forEach((i) -> i.setWaveStrength(0.1f));		
		
	//**************IN GAME TOOLS**************************//	
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		
	}
			
	public void render() {
		if (gamePaused == false) {
			time.start();
			moves();	
		}else{
			picker.update();
			System.out.println(picker.getCurrentRay());
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapWriteable mapWriter = new MapFileWriter();
			GameMap map = new GameMap("coolmap");
			map.setEntities(entities);
			map.setTerrains(terrains);
			mapWriter.write(map);
			System.out.println("save");
		}
		
		renderParticlesToScreen();
		renderer.renderShadowMap(entities, normalMapEntities, player, sun, camera);				
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		renderReflectionTexture();		
		renderRefractionTexture();
	    renderToScreen();
	}
	
	private void spreadOnHeights(List<Entity> entities) {
		for(Entity entity : entities) {
			entity.setPosition(new Vector3f(entity.getPosition().x, 
					terrains.get(0).getHeightOfTerrain(entity.getPosition().x, 
							entity.getPosition().z), entity.getPosition().z));
		}
	}
	
	
    private void moves() {
		camera.move();
		player.move(terrains);
		AudioMaster.setListenerData(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);
    }
    
    private void renderReflectionTexture() {
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderer.processEntity(player);
		renderer.renderScene(entities, normalMapEntities, terrains, lights, 
				camera, new Vector4f(0, 1, 0, -waters.get(0).getHeight()));
	    camera.getPosition().y += distance;
	    camera.invertPitch();
    }
    
    private void renderRefractionTexture() {
    	waterFBOs.bindRefractionFrameBuffer();
		renderer.processEntity(player);
		renderer.renderScene(entities, normalMapEntities, terrains, lights, 
				camera, new Vector4f(0, -1, 0, waters.get(0).getHeight()));

    }
    
    private void renderToScreen() {
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		waterFBOs.unbindCurrentFrameBuffer();
	    
		multisampleFbo.bindFrameBuffer();
	    renderer.processEntity(player);
	    renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 15));
	    waterRenderer.render(waters, camera, sun);
	    ParticleMaster.renderParticles(camera);
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
	    guiRenderer.render(guis);
	    GUIText text = createFPSText(1 / DisplayManager.getFrameTimeSeconds());
	    text.setColour(1, 0, 0);
	    TextMaster.render();
	    text.remove();
    }
    
    public void renderParticlesToScreen() {
		pSystem.get(0).generateParticles(player.getPosition());
		pSystem.get(1).generateParticles(new Vector3f(50,terrains.get(0).getHeightOfTerrain(50, 50),50));
		ParticleMaster.update(camera);
    }
    	
	public void cleanUp() {
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
	
	public GUIText createFPSText(float FPS) {
		return new GUIText("FPS: " + String.valueOf((int)FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
	}

	@Override
	public GameMap getMap(String name) {
		for(GameMap map: maps){
			if(map.getName() == name){
				return map;
			}
		}
		return null;
	}

	@Override
	public Camera getCamera(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getEntity(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Terrain getTerrain(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WaterTile getWater(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuiTexture getGui(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticleSystem getParticles(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
