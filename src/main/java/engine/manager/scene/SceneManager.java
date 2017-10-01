package manager.scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

import core.settings.EngineSettings;
import manager.particle.ParticleManager;
import object.audio.source.AudioSource;
import object.audio.source.IAudioSource;
import object.camera.TargetCamera;
import object.entity.player.IPlayer;
import object.entity.player.Player;
import object.light.Light;
import object.scene.IScene;
import object.texture.material.Material;
import object.water.WaterTile;
import primitive.model.Model;
import tool.EngineUtils;
import tool.math.vector.Vector3f;

/**
 * Manager of default test objects of the scene.
 * 
 * @author homelleon
 *
 */
public class SceneManager implements ISceneManager {

	private String playerName = "player1";
	private String cameraName = "cameraMain";

	@Override
	public void init(IScene scene, int mode) {
		switch(mode) {
			case EngineSettings.ENGINE_MODE_GAME:
				initializeGame(scene);
				break;
			case EngineSettings.ENGINE_MODE_EDITOR:
				initializeEditor(scene);
				break;
		}
	}
	
	private void initializeEditor(IScene scene) {
		List<Model> cubeModels = EngineUtils.loadStaticModels("cube", "cube1");
		IPlayer player1 = new Player(
				playerName, 
				cubeModels, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				1
		);
		player1.setBaseName("cubeEntity1");
		player1.getModels().forEach(model -> model.getMaterial().setShininess(5.0f));
		scene.setPlayer(player1);
		scene.getEntities().add(player1);
		scene.setCamera(new TargetCamera(cameraName, player1));
		scene.setSun(new Light("Sun", 
				new Vector3f(-1000, 5000, -1000), 
				new Vector3f(1.3f, 1.3f, 1.3f)));
		scene.getLights().add(scene.getSun());
	}
	
	private void initializeGame(IScene scene) {
		/*------------------PLAYER-----------------*/
		Model cubeModel = EngineUtils.loadStaticModel("cube", "cube1");
		Collection<Model> playerModels = new ArrayList<Model>();
		playerModels.add(cubeModel);
		IPlayer player1 = new Player(
				playerName, 
				playerModels, 
				new Vector3f(100, 0, 10), 
				new Vector3f(0, 0, 0), 
				1
		);
		player1.setBaseName("cubeEntity1");
		player1.getModels().forEach(model -> {
			Material material = model.getMaterial();
			material.setReflectiveFactor(1.0f);
			material.setRefractiveFactor(1.0f);
			material.setShininess(5.0f);			
		});

		/*--------------UI-------------------*/
		scene.getUserInterface().initialize();
		/*--------------TEXT----------------*/

		/*--------------AUDIO----------------*/
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		IAudioSource ambientSource = new AudioSource("birds", "forest.wav", 1000,
				scene.getAudioSources().getMaster());
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f);
		ambientSource.play();
		ambientSource.setPosition(new Vector3f(400, 50, 400));

		/*--------------WATER----------------*/
		List<WaterTile> waterList = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile("Water", 0, 0, -4, 10000);
		waterList.add(water);
		waterList.forEach(waterTile -> {
				waterTile.setTilingSize(0.05f);
				waterTile.setWaterSpeed(0.7f);
				waterTile.setWaveStrength(0.1f);
			});
		/*---------------SCENE-------------*/

		/* TODO: replace it by map loading system */
		scene.setPlayer(player1);
		scene.getAudioSources().getMaster().setListenerData(scene.getPlayer().getPosition());
		scene.getEntities().add(player1);
		scene.getEntities().addAll(EngineUtils.createGrassField(500, 500, 8000, 4, 0.02f));
		scene.setCamera(new TargetCamera(cameraName, player1));
		scene.setSun(new Light("Sun", 
				new Vector3f(-1000000, 200000, -1000000), 
				new Vector3f(1.3f, 1.3f, 1.3f)));
		scene.getLights().add(scene.getSun());
		scene.getLights().add(new Light("Light1", new Vector3f(200, 2, 200), new Vector3f(10, 0, 0),
				new Vector3f(1.1f, 0.01f, 0.002f)));
		scene.getLights().add(
				new Light("Light2", 
						new Vector3f(20, 2, 20), 
						new Vector3f(0, 5, 0), 
						new Vector3f(1, 0.01f, 0.002f)));
		scene.getAudioSources().add(ambientSource);
		scene.getWaters().addAll(waterList);
		scene.getParticles().addAll(ParticleManager.createParticleSystem());

		scene.spreadEntitiesOnHeights(scene.getEntities().getAll());
		//scene.getEntities().get("Cuby4").getModel().getTexture().setReflectiveFactor(1.2f);
	}

}
