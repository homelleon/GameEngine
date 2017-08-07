package object.scene.scene;

import java.util.Collection;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.audio.manager.AudioManager;
import object.audio.manager.IAudioManager;
import object.audio.master.AudioMaster;
import object.audio.master.IAudioMaster;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.entity.manager.EntityManager;
import object.entity.manager.IEntityManager;
import object.entity.player.IPlayer;
import object.gui.manager.GUIManager;
import object.gui.manager.IGUIManager;
import object.light.ILightManager;
import object.light.Light;
import object.light.LightManager;
import object.map.objectMap.IObjectManager;
import object.particle.ParticleSystem;
import object.particle.manager.IParticleManager;
import object.particle.manager.ParticleManager;
import object.terrain.manager.ITerrainManager;
import object.terrain.manager.TerrainManager;
import object.terrain.terrain.ITerrain;
import object.texture.Texture;
import object.voxel.manager.ChunkManager;
import object.voxel.manager.IChunkManager;
import object.water.manager.IWaterManager;
import object.water.manager.WaterManager;
import renderer.viewCulling.frustum.Frustum;
import tool.MousePicker;

public class Scene implements IScene {

	private final static int CHUNK_WORLD_SIZE = 2;
	private IPlayer player;
	private ICamera camera;
	private Light sun;

	private Texture environmentMap = Texture.newEmptyCubeMap(128);

	private Frustum frustum = new Frustum();
	private MousePicker picker;
	private IAudioMaster audioMaster = new AudioMaster();

	private IEntityManager entityManager = new EntityManager();
	private ITerrainManager terrainManager = new TerrainManager();
	private IWaterManager waterManager = new WaterManager();
	private IChunkManager chunkManager = new ChunkManager(CHUNK_WORLD_SIZE, new Vector3f(0, 0, 0));
	private IParticleManager particleManager = new ParticleManager();
	private ILightManager lightManager = new LightManager();
	private IAudioManager audioManager = new AudioManager(audioMaster);
	private IGUIManager uiManager = new GUIManager();

	public Scene() {
	}

	public Scene(IObjectManager objectMap, IObjectManager levelMap) {
		initialize(objectMap, levelMap);
	}
	
	private void initialize(IObjectManager objectMap, IObjectManager levelMap) {
		this.getEntities().addAll(levelMap.getEntities().getAll());
		this.getTerrains().addAll(levelMap.getTerrains().getAll());
		//this.getWaters().addAll(objectMap.getWaters().values());
		this.getParticles().addAll(objectMap.getParticles().getAll());
		this.getLights().addAll(objectMap.getLights().getAll());
		this.getAudioSources().getMaster().init();
		this.getAudioSources().addAll(objectMap.getAudioSources().getAll());
		for (int i = 0; i < CHUNK_WORLD_SIZE * CHUNK_WORLD_SIZE * CHUNK_WORLD_SIZE; i++) {
			for (int x = 0; x <= EngineSettings.VOXEL_CHUNK_SIZE; x++) {
				for (int y = 0; y <= EngineSettings.VOXEL_CHUNK_SIZE; y++) {
					for (int z = 0; z <= EngineSettings.VOXEL_CHUNK_SIZE; z++) {
						chunkManager.getChunk(i).getBlock(x, y, z).setIsActive(true);
					}
				}
			}
		}
		// chunkManager.getChunk(2).setIsActive(false);
	}

	@Override
	public Texture getEnvironmentMap() {
		return this.environmentMap;
	}

	@Override
	public IPlayer getPlayer() {
		return this.player;
	}

	@Override
	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	@Override
	public ICamera getCamera() {
		return this.camera;
	}

	@Override
	public void setCamera(ICamera camera) {
		this.camera = camera;
	}

	@Override
	public Light getSun() {
		return this.sun;
	}

	@Override
	public void setSun(Light sun) {
		this.sun = sun;
	}

	/*
	 * @Enitites
	 */
	@Override
	public IEntityManager getEntities() {
		return this.entityManager;
	}

	/*
	 * @Terrains
	 */

	@Override
	public ITerrainManager getTerrains() {
		return this.terrainManager;
	}

	/*
	 * @Waters
	 */

	@Override
	public IWaterManager getWaters() {
		return this.waterManager;
	}

	/*
	 * @VoxelGrids
	 */

	@Override
	public IChunkManager getChunks() {
		return this.chunkManager;
	}

	/*
	 * @Particles
	 */

	@Override
	public IParticleManager getParticles() {
		return this.particleManager;
	}

	/*
	 * @Lights
	 */

	@Override
	public ILightManager getLights() {
		return this.lightManager;
	}

	/*
	 * @AudioSources
	 */

	@Override
	public IAudioManager getAudioSources() {
		return this.audioManager;
	}

	@Override
	public IGUIManager getUserInterface() {
		return this.uiManager;
	}

	@Override
	public Frustum getFrustum() {
		return this.frustum;
	}

	@Override
	public MousePicker getPicker() {
		return this.picker;
	}

	@Override
	public void setPicker(MousePicker picker) {
		this.picker = picker;
	}

	@Override
	public void spreadEntitiesOnHeights(Collection<IEntity> entityList) {
		if (!entityList.isEmpty()) {
			for (IEntity entity : entityList) {
				float terrainHeight = 0;

				for (ITerrain terrain : this.terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
	}

	@Override
	public void spreadParitclesOnHeights(Collection<ParticleSystem> systems) {
		if (!systems.isEmpty()) {
			for (ParticleSystem system : systems) {
				float terrainHeight = 0;

				for (ITerrain terrain : this.terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(system.getPosition().x, system.getPosition().z);
				}
				system.setPosition(new Vector3f(system.getPosition().x, terrainHeight, system.getPosition().z));
			}
		}
	}

	@Override
	public void clean() {
		this.environmentMap.delete();
		this.entityManager.clean();
		this.terrainManager.clean();
		this.waterManager.clearAll();
		this.chunkManager.clearAll();
		this.particleManager.clean();
		this.lightManager.clean();
		this.audioManager.clean();
		this.uiManager.cleanAll();
	}

}
