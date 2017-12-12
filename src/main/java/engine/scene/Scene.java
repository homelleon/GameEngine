package scene;

import java.util.Collection;

import control.MousePicker;
import manager.audio.IAudioManager;
import manager.entity.FrustumEntityManager;
import manager.entity.IEntityManager;
import manager.entity.IFrustumEntityManager;
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
import primitive.texture.Texture;
import tool.math.Frustum;
import tool.math.vector.Vector3f;

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
	private IFrustumEntityManager frustumManager;
	
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
		Vector3f voxelGridPosition = this.spreadPointOnHeights(new Vector3f(0,0,0));
		this.chunkManager = new ChunkManager(CHUNK_WORLD_SIZE, voxelGridPosition);		
		chunkManager.getChunk(1).getBlock(0,5,5).setIsActive(false);
		chunkManager.getChunk(1).getBlock(0,4,5).setIsActive(false);
		chunkManager.getChunk(1).getBlock(0,5,6).setIsActive(false);
		this.frustum = new Frustum();
		this.frustumManager = new FrustumEntityManager(this.frustum);
		this.frustumManager.rebuildNodes(this.getEntities().getAll(), ITerrain.TERRAIN_SIZE);
	}
	
	@Override
	public IFrustumEntityManager getFrustumEntities() {
		return frustumManager;
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

	private Vector3f spreadPointOnHeights(Vector3f position) {
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
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
	}

	@Override
	public void spreadParitclesOnHeights(Collection<ParticleSystem> particleSystems) {
		if (!particleSystems.isEmpty()) {
			for (ParticleSystem partilceSystem : particleSystems) {
				float terrainHeight = 0;

				for (ITerrain terrain : this.terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(partilceSystem.getPosition().x, partilceSystem.getPosition().z);
				}
				partilceSystem.setPosition(new Vector3f(partilceSystem.getPosition().x, terrainHeight, partilceSystem.getPosition().z));
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
		if(this.frustumManager != null) {
			this.frustumManager.clean();
		}
		if(this.audioManager != null) {
			this.audioManager.clean();
		}
	}

}
