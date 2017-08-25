package manager.scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.util.vector.Vector3f;

import manager.particle.ParticleManager;
import object.audio.source.AudioSource;
import object.audio.source.IAudioSource;
import object.camera.TargetCamera;
import object.entity.player.IPlayer;
import object.entity.player.Player;
import object.light.Light;
import object.model.textured.TexturedModel;
import object.scene.IScene;
import object.water.WaterTile;
import renderer.loader.Loader;
import tool.EngineUtils;

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
	public void init(IScene scene, Loader loader) {
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = EngineUtils.loadStaticModel("cube", "cube1");
		IPlayer player1 = new Player(
				playerName, 
				cubeModel, 
				new Vector3f(100, 0, 10), 
				new Vector3f(0, 0, 0), 
				1
		);
		player1.getModel().getTexture().setReflectiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveIndex(1.33f);
		player1.getModel().getTexture().setShineDamper(5.0f);

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
		WaterTile water = new WaterTile("Water", 0, 0, -4, 1000);
		waterList.add(water);
		waterList.stream()
			.forEach(waterTile -> {
				waterTile.setTilingSize(0.05f);
				waterTile.setWaterSpeed(0.7f);
				waterTile.setWaveStrength(0.1f);
			});
		/*---------------SCENE-------------*/

		/* TODO: replace it by map loading system */
		scene.setPlayer(player1);
		scene.getAudioSources().getMaster().setListenerData(scene.getPlayer().getPosition());
		scene.getEntities().add(player1);
		scene.getEntities().addAll(EngineUtils.createGrassField(500, 500, 50, 1, 0.1f));
		scene.setCamera(new TargetCamera(player1, cameraName));
		scene.setSun(new Light("Sun", new Vector3f(-100000, 150000, -100000), new Vector3f(1.3f, 1.3f, 1.3f)));
		scene.getLights().add(scene.getSun());
		scene.getLights().add(new Light("Light1", new Vector3f(200, 2, 200), new Vector3f(10, 0, 0),
				new Vector3f(1.1f, 0.01f, 0.002f)));
		scene.getLights().add(
				new Light("Light2", new Vector3f(20, 2, 20), new Vector3f(0, 5, 0), new Vector3f(1, 0.01f, 0.002f)));

		scene.getAudioSources().add(ambientSource);
		scene.getWaters().addAll(waterList);
		scene.getParticles().addAll(ParticleManager.createParticleSystem(loader));

		scene.spreadEntitiesOnHeights(scene.getEntities().getAll());
		scene.getEntities().get("Cuby4").getModel().getTexture().setReflectiveFactor(1.2f);

	}

}
