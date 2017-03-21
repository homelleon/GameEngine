package scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioSource;
import audio.AudioSourceSimple;
import cameras.CameraPlayer;
import entities.EntityManagerStructured;
import entities.Player;
import entities.PlayerTextured;
import fontMeshCreator.GuiText;
import guis.GuiManagerStructured;
import lights.Light;
import models.TexturedModel;
import particles.ParticleManagerStructured;
import renderEngine.Loader;
import toolbox.ObjectUtils;
import water.WaterTile;

/**
 * Manager of default test objects of the scene.
 *  
 * @author homelleon
 *
 */
public class SceneManagerDefault implements SceneManager {
	
	private String playerName = "player1";
	private String cameraName = "cameraMain";

	@Override
	public void init(Scene scene, Loader loader) {	
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = ObjectUtils.loadStaticModel("cube", "cube1", loader);
		Player player1 = new PlayerTextured(playerName,cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		player1.getModel().getTexture().setReflectiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveIndex(1.33f);
		player1.getModel().getTexture().setShineDamper(5.0f);
		
		/*--------------TEXT----------------*/

		GuiText text = new GuiText("Version","This is an Alfa-version of the game engine", 
				3, scene.getTexts().getMaster().getFont(), new Vector2f(0.25f, 0), 0.5f, true, scene.getTexts().getMaster());
		
		text.setColour(1, 0, 0);
		
		/*--------------AUDIO----------------*/
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		AudioSource ambientSource = new AudioSourceSimple("birds", 
				"forest.wav", 1000, scene.getAudioSources().getMaster());
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f);
		ambientSource.play();
		ambientSource.setPosition(new Vector3f(400, 50, 400));
		
		/*--------------WATER----------------*/
		List<WaterTile> waterList = new ArrayList<WaterTile>();
		WaterTile water = new  WaterTile("Water", 0, 0, -4, 1000);
		waterList.add(water);
		waterList.stream().forEach((i) -> i.setTilingSize(0.05f));
		waterList.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waterList.stream().forEach((i) -> i.setWaveStrength(0.1f));
		/*---------------SCENE-------------*/
		
		/*TODO: replace it by map loading system*/
		scene.setPlayer(player1);
		scene.getAudioSources().getMaster().setListenerData(scene.getPlayer().getPosition());
		scene.getEntities().add(player1);
		scene.getEntities().addAll(ObjectUtils.createGrassField(500, 500, 50, 1, 0.1f, loader));
		scene.getEntities().addAll(EntityManagerStructured.createNormalMappedEntities(loader));
		scene.setCamera(new CameraPlayer(player1, cameraName));
		scene.setSun(new Light("Sun", new Vector3f(-100000,150000,-100000), new Vector3f(1.3f,1.3f,1.3f)));
		scene.getLights().add(scene.getSun());
		scene.getLights().add(new Light("Light1", new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1.1f, 0.01f, 0.002f)));
		scene.getLights().add(new Light("Light2", new Vector3f(20,2,20),new Vector3f(0,5,0), new Vector3f(1, 0.01f, 0.002f)));

		scene.getAudioSources().add(ambientSource);
		scene.getGuis().addAll(GuiManagerStructured.createGui(loader));
		scene.getWaters().addAll(waterList);
		scene.getParticles().addAll(ParticleManagerStructured.createParticleSystem(loader));
		
		scene.spreadEntitiesOnHeights(scene.getEntities().getAll());
		scene.getEntities().getByName("Cuby4").getModel().getTexture().setReflectiveFactor(1.2f);
				
	}

}
