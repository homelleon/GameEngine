package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import guis.GuiRenderer;
import guis.GuiTexture;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import particles.ParticlesManager;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class SceneRenderer {

	public static boolean gamePaused = false;	
	private Loader loader;
	private MasterRenderer renderer;
	private Source ambientSource;
	
	private boolean isMidday = true;
	
	//TODO: Delete unnecessary objects
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
	private Player player;
	private List<Light> lights;
	private Light sun;
	private WaterRenderer waterRenderer;
	private Camera camera;
	private GameTime time;
	private MousePicker picker;
	
	public SceneRenderer(){
		
		//***************PRE LOAD TOOLS*************//
		this.loader = new Loader();
		
		//***************TERRAIN********************//

		this.terrains = new ArrayList<Terrain>();
		Terrain terrain = SceneObjectTools.createMultiTexTerrain("grass", "ground", "floweredGrass", "road", "blendMap", 100f, 5, 0.5f, loader);
		//Terrain terrain = generator.createMultiTexTerrain("grass", "ground", "floweredGrass", "road", "blendMap", "heightMap");
		terrains.add(terrain);
				

        //***********GAME OBJECTS****************//
		
		this.entities = EntitiesManager.createEntities(loader);
		this.normalMapEntities = EntitiesManager.createNormalMappedEntities(loader);
		
		spreadOnHeights(entities);
		spreadOnHeights(normalMapEntities);
		
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
		PostProcessing.init(loader);

		//*******************FONTS*************//
		TextMaster.init(loader);
		this.font = new FontType(loader.loadTexture(Settings.FONT_PATH, "candara"), new File(Settings.FONT_PATH + "candara.fnt"));
		GUIText text = new GUIText("This is an Alfa-version of the game engine", 3, font, new Vector2f(0.25f, 0), 0.5f, true);
		
		text.setColour(1, 0, 0);
		//*******************AUDIO*************//
		
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
		this.ambientSource = new Source();
		int buffer = AudioMaster.loadSound("forest.wav");
		Source ambientSource = new Source();
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.2f);
		ambientSource.play(buffer);
		float xPos = 8;				
		
		//***************GUI***********//
		this.guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture(Settings.INTERFACE_TEXTURE_PATH,"helthBar"), new Vector2f(-0.7f, -0.7f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		
		//GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), 
		//		new Vector2f(-0.5f, 0.5f), new Vector2f(0.5f,0.5f));
		//guis.add(shadowMap);

		
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
			
	public void render(){
		if (gamePaused == false){
			time.start();
			moves();	
		}else{
			picker.update();
			System.out.println(picker.getCurrentRay());
		}
		renderParticlesToScreen();
		renderer.renderShadowMap(entities, normalMapEntities, player, sun, camera);				
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		renderReflectionTexture();		
		renderRefractionTexture();
	    renderToScreen();
	}
	
	private void spreadOnHeights(List<Entity> entities){
		for(Entity entity : entities){
			entity.setPosition(new Vector3f(entity.getPosition().x, 
					terrains.get(0).getHeightOfTerrain(entity.getPosition().x, 
							entity.getPosition().z), entity.getPosition().z));
		}
	}
	
	
    private void moves(){
		camera.move();	
		player.move(terrains.get(0));
    }
    
    private void renderReflectionTexture(){
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderer.processEntity(player);
		renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -waters.get(0).getHeight()));
	    camera.getPosition().y += distance;
	    camera.invertPitch();
    }
    
    private void renderRefractionTexture(){
    	waterFBOs.bindRefractionFrameBuffer();
		renderer.processEntity(player);
		renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, waters.get(0).getHeight()));

    }
    
    private void renderToScreen(){
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		waterFBOs.unbindCurrentFrameBuffer();
	    
		multisampleFbo.bindFrameBuffer();
	    renderer.processEntity(player);
	    renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 15));
	    waterRenderer.render(waters, camera, sun);
	    ParticleMaster.renderParticles(camera);
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(outputFbo);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture());
	    guiRenderer.render(guis);
	    GUIText text = createFPSText(1 / DisplayManager.getFrameTimeSeconds());
	    text.setColour(1, 0, 0);
	    TextMaster.render();
	    text.remove();
    }
    
    public void renderParticlesToScreen(){
		pSystem.get(0).generateParticles(player.getPosition());
		pSystem.get(1).generateParticles(new Vector3f(50,terrains.get(0).getHeightOfTerrain(50, 50),50));
		ParticleMaster.update(camera);
    }
    	
	public void cleanUp(){
		PostProcessing.cleanUp();
		outputFbo.cleanUp();
		multisampleFbo.cleanUp();
		TextMaster.cleanUp();
		ambientSource.delete();
		waterFBOs.cleanUp();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
	}
	
	public GUIText createFPSText(float FPS){
		return new GUIText("FPS: " + String.valueOf((int)FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
	}

}
