package object.scene;

import java.util.Collection;

import manager.audio.IAudioManager;
import manager.entity.IEntityManager;
import manager.gui.GUIManager;
import manager.gui.IGUIManager;
import manager.light.ILightManager;
import manager.particle.IParticleManager;
import manager.scene.AObjectManager;
import manager.scene.IObjectManager;
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
import tool.math.vector.Vec3f;

public class Scene extends AObjectManager implements IScene {

	private final static int CHUNK_WORLD_SIZE = 2;
	private IPlayer player;
	private ICamera camera;
	private Light sun;
	private Frustum frustum;

	private Texture environmentMap = Texture.newEmptyCubeMap(128);
	private MousePicker mousePicker;

	private IChunkManager chunkManager;
	private IGUIManager uiManager = new GUIManager();
	
	public Scene() {
		super();
	}

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
		this.getWaters().addAll(levelMap.getWaters().getAll());
		this.getParticles().addAll(levelMap.getParticles().getAll());
		this.getLights().addAll(levelMap.getLights().getAll());
		this.getAudioSources().getMaster().init();
		this.getAudioSources().addAll(levelMap.getAudioSources().getAll());
		Vec3f voxelGridPosition = this.spreadPointOnHeights(new Vec3f(0,0,0));
		this.chunkManager = new ChunkManager(CHUNK_WORLD_SIZE, voxelGridPosition);		
		chunkManager.getChunk(2).getBlock(0,5,5).setIsActive(false);
		chunkManager.getChunk(2).getBlock(0,4,5).setIsActive(false);
		chunkManager.getChunk(2).getBlock(0,5,6).setIsActive(false);
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
	public MousePicker getMousePicker() {
		return this.mousePicker;
	}

	@Override
	public void setMousePicker(MousePicker mousePicker) {
		this.mousePicker = mousePicker;
	}
	
	@Override
	public void setFrustum(Frustum frustum) {
		this.frustum = frustum;
	}

	@Override
	public Frustum getFrustum() {
		return this.frustum;
	}

	private Vec3f spreadPointOnHeights(Vec3f position) {
		float terrainHeight = 0;
		for (ITerrain terrain : this.terrainManager.getAll()) {
			terrainHeight += terrain.getHeightOfTerrain(position.x, position.z);
		}
		position.setY(terrainHeight);
		return position;
	}
	@Override
	public void spreadEntitiesOnHeights(Collection<IEntity> entityList) {
		if (!entityList.isEmpty()) {
			for (IEntity entity : entityList) {
				float terrainHeight = 0;

				for (ITerrain terrain : this.terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vec3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
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
				system.setPosition(new Vec3f(system.getPosition().x, terrainHeight, system.getPosition().z));
			}
		}
	}

	@Override
	public void clean() {
		super.clean();
		this.environmentMap.delete();
		if(this.chunkManager != null) {
			this.chunkManager.clearAll();
		}
		if(this.uiManager != null) {
			this.uiManager.cleanAll();
		}
	}

}
