package scene;

import java.util.Collection;

import control.MousePicker;
import manager.AudioManager;
import manager.LightManager;
import manager.ParticleManager;
import manager.TerrainManager;
import manager.WaterManager;
import manager.entity.EntityManager;
import manager.entity.FrustumEntityManager;
import manager.gui.GUIManager;
import manager.scene.ObjectManager;
import manager.voxel.ChunkManager;
import object.audio.AudioMaster;
import object.camera.Camera;
import object.entity.Entity;
import object.entity.Player;
import object.light.Light;
import object.particle.ParticleSystem;
import object.terrain.Terrain;
import primitive.texture.Texture;
import tool.math.vector.Vector3f;

public class Scene extends ObjectManager {

	private final static int CHUNK_WORLD_SIZE = 2;
	private Player player;
	private Camera camera;
	private Light sun;


	private Texture environmentMap = Texture.newEmptyCubeMap(128);
	private MousePicker mousePicker;

	private ChunkManager chunkManager;
	private GUIManager uiManager = new GUIManager();
	private FrustumEntityManager frustumManager;
	
	public Scene() {
		super();
	}

	public Scene(AudioMaster audioMaster) {
		super(audioMaster);
	}

	public Scene(ObjectManager levelMap, AudioMaster audioMaster) {
		super(audioMaster);
		initialize(levelMap);
	}
	
	private void initialize(ObjectManager levelMap) {
		this.getEntities().addAll(levelMap.getEntities().getAll());
		this.getTerrains().addAll(levelMap.getTerrains().getAll());
		this.getWaters().addAll(levelMap.getWaters().getAll());
		this.getParticles().addAll(levelMap.getParticles().getAll());
		this.getLights().addAll(levelMap.getLights().getAll());
		this.getAudioSources().getMaster().init();
		this.getAudioSources().addAll(levelMap.getAudioSources().getAll());
		Vector3f voxelGridPosition = this.spreadPointOnHeights(new Vector3f(0, 0, 0));
		this.chunkManager = new ChunkManager(CHUNK_WORLD_SIZE, voxelGridPosition);		
		chunkManager.getChunk(1).getBlock(0, 5, 5).setIsActive(false);
		chunkManager.getChunk(1).getBlock(0, 4, 5).setIsActive(false);
		chunkManager.getChunk(1).getBlock(0, 5, 6).setIsActive(false);
		this.frustumManager = new FrustumEntityManager();
		this.frustumManager.rebuildNodes(this.getEntities().getAll());
	}
	
	public FrustumEntityManager getFrustumEntities() {
		return frustumManager;
	}

	public Texture getEnvironmentMap() {
		return environmentMap;
	}

	public Player getPlayer() {
		return player;
	}

	public Scene setPlayer(Player player) {
		this.player = player;
		return this;
	}

	public Camera getCamera() {
		return camera;
	}

	public Scene setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}

	public Light getSun() {
		return sun;
	}

	public Scene setSun(Light sun) {
		this.sun = sun;
		return this;
	}

	/*
	 * @Enitites
	 */
	@Override
	public EntityManager getEntities() {
		return entityManager;
	}

	/*
	 * @Terrains
	 */

	@Override
	public TerrainManager getTerrains() {
		return terrainManager;
	}

	/*
	 * @Waters
	 */

	@Override
	public WaterManager getWaters() {
		return waterManager;
	}

	/*
	 * @VoxelGrids
	 */

	public ChunkManager getChunks() {
		return chunkManager;
	}

	/*
	 * @Particles
	 */

	@Override
	public ParticleManager getParticles() {
		return particleManager;
	}

	/*
	 * @Lights
	 */

	@Override
	public LightManager getLights() {
		return lightManager;
	}

	/*
	 * @AudioSources
	 */

	@Override
	public AudioManager getAudioSources() {
		return audioManager;
	}

	public GUIManager getUserInterface() {
		return uiManager;
	}

	public MousePicker getMousePicker() {
		return mousePicker;
	}

	public Scene setMousePicker(MousePicker mousePicker) {
		this.mousePicker = mousePicker;
		return this;
	}

	private Vector3f spreadPointOnHeights(Vector3f position) {
		float terrainHeight = 0;
		for (Terrain terrain : terrainManager.getAll()) {
			terrainHeight += terrain.getHeightOfTerrain(position.x, position.z);
		}
		position.setY(terrainHeight);
		return position;
	}
	
	public Scene spreadEntitiesOnHeights(Collection<Entity> entityList) {
		if (!entityList.isEmpty()) {
			for (Entity entity : entityList) {
				float terrainHeight = 0;

				for (Terrain terrain : terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
		return this;
	}

	public Scene spreadParitclesOnHeights(Collection<ParticleSystem> particleSystems) {
		if (!particleSystems.isEmpty()) {
			for (ParticleSystem partilceSystem : particleSystems) {
				float terrainHeight = 0;

				for (Terrain terrain : terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(partilceSystem.getPosition().x, partilceSystem.getPosition().z);
				}
				partilceSystem.setPosition(new Vector3f(partilceSystem.getPosition().x, terrainHeight, partilceSystem.getPosition().z));
			}
		}
		return this;
	}

	@Override
	public void clean() {
		super.clean();
		environmentMap.delete();
		
		if (chunkManager != null)
			chunkManager.clearAll();
		
		if (uiManager != null)
			uiManager.cleanAll();
		
		if (frustumManager != null)
			frustumManager.clean();
		
		if (audioManager != null)
			audioManager.clean();
		
	}

}
