package scene;

import java.util.Collection;

import org.lwjgl.util.vector.Vector3f;

import maps.GameMap;
import objects.audio.AudioManager;
import objects.audio.AudioManagerInterface;
import objects.audio.AudioMaster;
import objects.audio.AudioMasterInterface;
import objects.cameras.CameraInterface;
import objects.entities.EntityInterface;
import objects.entities.EntityManager;
import objects.entities.EntityManagerInterface;
import objects.entities.PlayerInterface;
import objects.gui.GUIManager;
import objects.gui.GUIManagerInterface;
import objects.gui.guiTextures.GUITextureManager;
import objects.gui.guiTextures.GUITextureManagerInterface;
import objects.lights.Light;
import objects.lights.LightManager;
import objects.lights.LightManagerStructured;
import objects.particles.ParticleManager;
import objects.particles.ParticleManagerInterface;
import objects.particles.ParticleSystem;
import objects.terrains.TerrainInterface;
import objects.terrains.TerrainManager;
import objects.terrains.TerrainManagerInterface;
import objects.textures.Texture;
import objects.voxels.ChunkManager;
import objects.voxels.ChunkManagerInterface;
import objects.water.WaterManager;
import objects.water.WaterManagerStructured;
import toolbox.MousePicker;
import viewCulling.Frustum;

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
	private WaterManager waterManager = new WaterManagerStructured();
	private ChunkManagerInterface chunkManager = 
			new ChunkManager(CHUNK_WORLD_SIZE, new Vector3f(0,0,0));
	private ParticleManagerInterface particleManager = new ParticleManager();
	private LightManager lightManager = new LightManagerStructured();
	private AudioManagerInterface audioManager = new AudioManager(audioMaster);
	private GUITextureManagerInterface guiManager = new GUITextureManager();
	private GUIManagerInterface uiManager = new GUIManager();

	
	public Scene() {}
	
	public Scene(GameMap map) {
		this.getEntities().addAll(map.getEntities().values());
		this.getTerrains().addAll(map.getTerrains().values());
		this.getWaters().addAll(map.getWaters().values());
		this.getParticles().addAll(map.getParticles().values());
		this.getLights().addAll(map.getLights().values());		
		this.getAudioSources().getMaster().init();
		this.getAudioSources().addAll(map.getAudioSources().values());		
		this.getGuis().addAll(map.getGuis().values());
		for(int i = 0; i < CHUNK_WORLD_SIZE * CHUNK_WORLD_SIZE *
				CHUNK_WORLD_SIZE; i++) {
			for(int x = 0; x <= ES.VOXEL_CHUNK_SIZE; x++) {
				for(int y = 0; y <= ES.VOXEL_CHUNK_SIZE; y++) {
					for(int z = 0; z <= ES.VOXEL_CHUNK_SIZE; z++) {
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
	public WaterManager getWaters() {
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
	
	/* 
	 * @GuiTexture
	 */

	@Override
	public GUITextureManagerInterface getGuis() {
		return this.guiManager;
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
	public void spreadEntitiesOnHeights(Collection<EntityInterface> entityList) {
		if (!entityList.isEmpty()) {
			for(EntityInterface entity : entityList) {
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
		this.guiManager.clearAll();
		this.uiManager.cleanAll();		
	}



}
