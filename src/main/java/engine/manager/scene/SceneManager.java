package manager.scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import manager.particle.ParticleManager;
import object.audio.source.AudioSource;
import object.audio.source.IAudioSource;
import object.camera.TargetCamera;
import object.entity.Player;
import object.light.Light;
import object.water.WaterTile;
import primitive.buffer.Loader;
import primitive.model.Model;
import primitive.texture.material.Material;
import scene.Scene;
import shader.Shader;
import shader.ShaderPool;
import tool.EngineUtils;
import tool.math.vector.Color;
import tool.math.vector.Vector3f;

/**
 * Manages default test objects for the scene.
 * 
 * @author homelleon
 *
 */
public class SceneManager implements ISceneManager {

	private String playerName = "player1";
	private String cameraName = "cameraMain";

	@Override
	public void init(Scene scene, int mode) {
		switch(mode) {
			case EngineSettings.ENGINE_MODE_GAME:
				initializeGame(scene);
				break;
			case EngineSettings.ENGINE_MODE_EDITOR:
				initializeEditor(scene);
				break;
		}
	}
	
	private void initializeEditor(Scene scene) {
		List<Model> cubeModels = EngineUtils.loadModels("xuchilbara", "xuchilbara_dif", true);
		Shader playerShader = ShaderPool.getInstance().get(Shader.ENTITY);
		Player player1 = new Player(
				playerName,
				playerShader,
				cubeModels, 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 0, 0), 
				0.2f
		);
		
		player1.setBaseName("cubeEntity1");
		player1.getModels().forEach(model -> model.getMaterial().setShininess(5.0f));
		player1.getModels().get(0).getMaterial().setNormalMap(
				Loader.getInstance().getTextureLoader().loadTexture(
						EngineSettings.TEXTURE_NORMAL_MAP_PATH, "xuchilbara_n"));
		player1.getModels().get(0).getMaterial().setSpecularMap(
				Loader.getInstance().getTextureLoader().loadTexture(
						EngineSettings.TEXTURE_SPECULAR_MAP_PATH, "xuchilbara_spec"));
		scene.setPlayer(player1);
		scene.getEntities().add(player1);
		scene.setCamera(new TargetCamera(cameraName, player1));
		scene.setSun(new Light("Sun",
				new Vector3f(1000, 5000, 1000), 
				new Color(255, 255, 255),
				new Vector3f(1.0f, 0, 0)));
		scene.getLights().add(scene.getSun());
	}
	
	private void initializeGame(Scene scene) {
		/*------------------PLAYER-----------------*/
//		List<Model> cubeModels = EngineUtils.loadModels("xuchilbara", "xuchilbara_dif", true);
		List<Model> cubeModels = EngineUtils.loadModels("cube", "Cube1");
		
		if (EngineDebug.hasDebugPermission())
			EngineDebug.println(cubeModels.get(0).getName(), 2);
		
		Shader playerShader = ShaderPool.getInstance().get(Shader.ENTITY);
		Player player1 = new Player(
				playerName,
				playerShader,
				cubeModels, 
				new Vector3f(50, 0, 50), 
				new Vector3f(0, 0, 0), 
				1.05f
		);
		
		player1.setBaseName("xuchilbaraEntity");
		player1.getModels().forEach(model -> {
			Material material = model.getMaterial();
//			material.getDiffuseMap().setHasTransparency(true);
			material.setShininess(0.5f);
//			material.setNormalMap(
//				Loader.getInstance().getTextureLoader().loadTexture(
//						EngineSettings.TEXTURE_NORMAL_MAP_PATH, "xuchilbara_n"));
//			material.setSpecularMap(
//					Loader.getInstance().getTextureLoader().loadTexture(
//							EngineSettings.TEXTURE_SPECULAR_MAP_PATH, "xuchilbara_spec"));
//			material.setAlphaMap(
//					Loader.getInstance().getTextureLoader().loadTexture(
//							EngineSettings.TEXTURE_ALPHA_MAP_PATH, "xuchilbara_o"));
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
		// ambientSource.play();
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
		scene.getFrustumEntities().addEntityInNodes(player1);
		scene.getEntities().addAll(EngineUtils.createObjectField(500, 500, 1000, 4, 0.1f));
		scene.getEntities().getAll()
			.forEach(entity -> scene.getFrustumEntities().addEntityInNodes(entity));
		scene.setCamera(new TargetCamera(cameraName, player1));
		
		// light
		scene.setSun(new Light("Sun", 
				new Vector3f(10000, 8000, 10000),
				new Color(255, 255, 255),
				new Vector3f(1.0f, 0, 0)));
		scene.getLights().add(scene.getSun());
		
		scene.getLights().add(new Light("Light1", 
				new Vector3f(25, 0, 50), 
				new Color(255, 0, 0),
				new Vector3f(0, 0, 0.0002f)));
		
		scene.getLights().add(new Light("Light2", 
				new Vector3f(20, 0, 20),
				new Color(0, 255, 0), 
				new Vector3f(0, 0, 0.0002f)));
		
		scene.getAudioSources().add(ambientSource);
		scene.getWaters().addAll(waterList);
		scene.getParticles().addAll(ParticleManager.createParticleSystem());
		
		if (EngineDebug.hasDebugPermission())
			EngineDebug.println("Total loaded entities: " + scene.getEntities().getAll().stream().count(), 2);

		scene.spreadEntitiesOnHeights(scene.getEntities().getAll());
		//scene.getEntities().get("Cuby4").getModel().getTexture().setReflectiveFactor(1.2f);
	}

}
