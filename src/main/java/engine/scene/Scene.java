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
import object.entity.Entity;
import object.entity.Player;
import object.light.Light;
import object.particle.ParticleSystem;
import object.terrain.terrain.ITerrain;
import primitive.texture.Texture;
import tool.math.Frustum;
import tool.math.vector.Vector3f;

public class Scene extends AObjectManager {

	private final static int CHUNK_WORLD_SIZE = 2;
	private Player player;
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
		Vector3f voxelGridPosition = this.spreadPointOnHeights(new Vector3f(0, 0, 0));
		this.chunkManager = new ChunkManager(CHUNK_WORLD_SIZE, voxelGridPosition);		
		chunkManager.getChunk(1).getBlock(0, 5, 5).setIsActive(false);
		chunkManager.getChunk(1).getBlock(0, 4, 5).setIsActive(false);
		chunkManager.getChunk(1).getBlock(0, 5, 6).setIsActive(false);
		this.frustum = new Frustum();
		this.frustumManager = new FrustumEntityManager(this.frustum);
		this.frustumManager.rebuildNodes(this.getEntities().getAll());
	}
	
	public IFrustumEntityManager getFrustumEntities() {
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

	public ICamera getCamera() {
		return camera;
	}

	public Scene setCamera(ICamera camera) {
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
	public IEntityManager getEntities() {
		return entityManager;
	}

	/*
	 * @Terrains
	 */

	@Override
	public ITerrainManager getTerrains() {
		return terrainManager;
	}

	/*
	 * @Waters
	 */

	@Override
	public IWaterManager getWaters() {
		return waterManager;
	}

	/*
	 * @VoxelGrids
	 */

	public IChunkManager getChunks() {
		return chunkManager;
	}

	/*
	 * @Particles
	 */

	@Override
	public IParticleManager getParticles() {
		return particleManager;
	}

	/*
	 * @Lights
	 */

	@Override
	public ILightManager getLights() {
		return lightManager;
	}

	/*
	 * @AudioSources
	 */

	@Override
	public IAudioManager getAudioSources() {
		return audioManager;
	}

	public IGUIManager getUserInterface() {
		return uiManager;
	}

	public MousePicker getMousePicker() {
		return mousePicker;
	}

	public Scene setMousePicker(MousePicker mousePicker) {
		this.mousePicker = mousePicker;
		return this;
	}
	
	public Scene setFrustum(Frustum frustum) {
		this.frustum = frustum;
		return this;
	}

	public Frustum getFrustum() {
		return frustum;
	}

	private Vector3f spreadPointOnHeights(Vector3f position) {
		float terrainHeight = 0;
		for (ITerrain terrain : terrainManager.getAll()) {
			terrainHeight += terrain.getHeightOfTerrain(position.x, position.z);
		}
		position.setY(terrainHeight);
		return position;
	}
	
	public Scene spreadEntitiesOnHeights(Collection<Entity> entityList) {
		if (!entityList.isEmpty()) {
			for (Entity entity : entityList) {
				float terrainHeight = 0;

				for (ITerrain terrain : terrainManager.getAll()) {
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

				for (ITerrain terrain : terrainManager.getAll()) {
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
