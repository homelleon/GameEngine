package object.scene.scene;

import java.util.Collection;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.audio.manager.AudioManager;
import object.audio.manager.AudioManagerInterface;
import object.audio.master.AudioMaster;
import object.audio.master.AudioMasterInterface;
import object.camera.CameraInterface;
import object.entity.entity.Entity;
import object.entity.manager.EntityManager;
import object.entity.manager.EntityManagerInterface;
import object.entity.player.PlayerInterface;
import object.gui.manager.GUIManager;
import object.gui.manager.GUIManagerInterface;
import object.light.Light;
import object.light.LightManager;
import object.light.LightManagerStructured;
import object.map.modelMap.ModelMap;
import object.particle.ParticleSystem;
import object.particle.manager.ParticleManager;
import object.particle.manager.ParticleManagerInterface;
import object.terrain.manager.TerrainManager;
import object.terrain.manager.TerrainManagerInterface;
import object.terrain.terrain.TerrainInterface;
import object.texture.Texture;
import object.voxel.manager.ChunkManager;
import object.voxel.manager.ChunkManagerInterface;
import object.water.manager.WaterManager;
import object.water.manager.WaterManagerInterface;
import renderer.viewCulling.frustum.Frustum;
import tool.MousePicker;

public class Scene implements SceneInterface {
	
	private final static int CHUNK_WORLD_SIZE = 2;
	private PlayerInterface player;
	private CameraInterface camera;
	private Light sun;
	
	private Texture environmentMap = Texture.newEmptyCubeMap(128);
	
	private Frustum frustum = new Frustum();
	private MousePicker picker;
	private AudioMasterInterface audioMaster = new AudioMaster();
	
	private EntityManagerInterface entityManager = new EntityManager();
	private TerrainManagerInterface terrainManager = new TerrainManager();
	private WaterManagerInterface waterManager = new WaterManager();
	private ChunkManagerInterface chunkManager = 
			new ChunkManager(CHUNK_WORLD_SIZE, new Vector3f(0,0,0));
	private ParticleManagerInterface particleManager = new ParticleManager();
	private LightManager lightManager = new LightManagerStructured();
	private AudioManagerInterface audioManager = new AudioManager(audioMaster);
	private GUIManagerInterface uiManager = new GUIManager();
	
	public Scene() {}
	
	public Scene(ModelMap map) {
		this.getEntities().addAll(map.getEntities().values());
		this.getTerrains().addAll(map.getTerrains().values());
		this.getWaters().addAll(map.getWaters().values());
		this.getParticles().addAll(map.getParticles().values());
		this.getLights().addAll(map.getLights().values());		
		this.getAudioSources().getMaster().init();
		this.getAudioSources().addAll(map.getAudioSources().values());		
		for(int i = 0; i < CHUNK_WORLD_SIZE * CHUNK_WORLD_SIZE *
				CHUNK_WORLD_SIZE; i++) {
			for(int x = 0; x <= EngineSettings.VOXEL_CHUNK_SIZE; x++) {
				for(int y = 0; y <= EngineSettings.VOXEL_CHUNK_SIZE; y++) {
					for(int z = 0; z <= EngineSettings.VOXEL_CHUNK_SIZE; z++) {
						chunkManager.getChunk(i).getBlock(x, y, z)
						.setIsActive(true);
					}
				}
			}			
		}
		//chunkManager.getChunk(2).setIsActive(false);
	}
	
	@Override
	public Texture getEnvironmentMap() {
		return this.environmentMap;
	}
	
	@Override
	public PlayerInterface getPlayer() {
		return this.player;
	}
	
	@Override
	public void setPlayer(PlayerInterface player) {
		this.player = player;
	}
	
	@Override
	public CameraInterface getCamera() {
		return this.camera;
	}
	
	@Override
	public void setCamera(CameraInterface camera) {
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
	public EntityManagerInterface getEntities() {
		return this.entityManager;
	}

	/* 
	 * @Terrains
	 */

	@Override
	public TerrainManagerInterface getTerrains() {
		return this.terrainManager;
	}
	
	/* 
	 * @Waters
	 */
	
	@Override
	public WaterManagerInterface getWaters() {
		return this.waterManager;
	}

	
	/* 
	 * @VoxelGrids
	 */
	
	@Override
	public ChunkManagerInterface getChunks() {
		return this.chunkManager;
	}

	
	/* 
	 * @Particles
	 */

	@Override
	public ParticleManagerInterface getParticles() {
		return this.particleManager;
	}

	/* 
	 * @Lights
	 */
	

	@Override
	public LightManager getLights() {
		return this.lightManager;
	}

	/* 
	 * @AudioSources
	 */
	
	@Override
	public AudioManagerInterface getAudioSources() {
		return this.audioManager;
	}	
	
	@Override
	public GUIManagerInterface getUserInterface() {		
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
	public void spreadEntitiesOnHeights(Collection<Entity> entityList) {
		if (!entityList.isEmpty()) {
			for(Entity entity : entityList) {
				float terrainHeight = 0;
				
				for(TerrainInterface terrain : this.terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
	}
    
	@Override
	public void spreadParitclesOnHeights(Collection<ParticleSystem> systems) {
		if (!systems.isEmpty()) {
			for(ParticleSystem system : systems) {
				float terrainHeight = 0;
				
				for(TerrainInterface terrain : this.terrainManager.getAll()) {
					terrainHeight += terrain.getHeightOfTerrain(system.getPosition().x, system.getPosition().z);
				}
				system.setPosition(new Vector3f(system.getPosition().x, terrainHeight, system.getPosition().z));
			}
		}
	}
	
	@Override
	public void cleanUp() {
		this.environmentMap.delete();
		this.entityManager.clearAll();
		this.terrainManager.clearAll();
		this.waterManager.clearAll();
		this.chunkManager.clearAll();
		this.particleManager.clearAll();
		this.lightManager.clearAll();
		this.audioManager.clearAll();
		this.uiManager.cleanAll();		
	}

}
