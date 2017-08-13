package object.scene;

import java.util.Collection;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import manager.audio.IAudioManager;
import manager.entity.IEntityManager;
import manager.gui.GUIManager;
import manager.gui.IGUIManager;
import manager.light.ILightManager;
import manager.particle.IParticleManager;
import manager.scene.IObjectManager;
import manager.scene.ObjectManager;
import manager.terrain.ITerrainManager;
import manager.voxel.ChunkManager;
import manager.voxel.IChunkManager;
import manager.water.IWaterManager;
import object.audio.master.IAudioMaster;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.entity.player.IPlayer;
import object.light.Light;
import object.particle.ParticleSystem;
import object.terrain.terrain.ITerrain;
import object.texture.Texture;
import renderer.viewCulling.frustum.Frustum;
import tool.MousePicker;

public class Scene extends ObjectManager implements IScene {

	private final static int CHUNK_WORLD_SIZE = 2;
	private IPlayer player;
	private ICamera camera;
	private Light sun;

	private Texture environmentMap = Texture.newEmptyCubeMap(128);

	private Frustum frustum = new Frustum();
	private MousePicker picker;

	private IChunkManager chunkManager = new ChunkManager(CHUNK_WORLD_SIZE, new Vector3f(0, 0, 0));
	private IGUIManager uiManager = new GUIManager();

	public Scene(IAudioMaster audioMaster) {
		super(audioMaster);
	}

	public Scene(IObjectManager levelMap, IAudioMaster audioMaster) {
		super(audioMaster);
		initialize(levelMap);
	}
	
	private void initialize(IObjectManager levelMap) {
		this.getEntities().addAll(levelMap.getEntities().getAll());
		this.getTerrains().addAll(levelMap.getTerrains().getAll());
		//this.getWaters().addAll(objectMap.getWaters().values());
		this.getParticles().addAll(levelMap.getParticles().getAll());
		this.getLights().addAll(levelMap.getLights().getAll());
		this.getAudioSources().getMaster().init();
		this.getAudioSources().addAll(levelMap.getAudioSources().getAll());
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
		super.clean();
		this.environmentMap.delete();
		this.chunkManager.clearAll();
		this.uiManager.cleanAll();
	}

}
