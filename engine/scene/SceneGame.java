package scene;

import java.util.Collection;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioManager;
import audio.AudioManagerStructured;
import audio.AudioMaster;
import audio.AudioMasterBuffered;
import audio.AudioSource;
import audio.AudioSourceSimple;
import cameras.Camera;
import entities.Entity;
import entities.EntityManager;
import entities.EntityManagerStructured;
import entities.Player;
import guis.GuiManager;
import guis.GuiManagerStructured;
import lights.Light;
import lights.LightManager;
import lights.LightManagerStructured;
import maps.GameMap;
import particles.ParticleManager;
import particles.ParticleManagerStructured;
import particles.ParticleSystem;
import renderEngine.Loader;
import terrains.Terrain;
import terrains.TerrainManager;
import terrains.TerrainManagerStructured;
import texts.TextManager;
import texts.TextManagerStructured;
import textures.Texture;
import toolbox.Frustum;
import toolbox.MousePicker;
import voxels.ChunkManager;
import voxels.ChunkManagerStructured;
import water.WaterManager;
import water.WaterManagerStructured;

public class SceneGame implements Scene {
	
	private final static int CHUNK_WORLD_SIZE = 2;
	private Player player;
	private Camera camera;
	private Light sun;
	
	private Texture environmentMap = Texture.newEmptyCubeMap(128);
	
	private Frustum frustum = new Frustum();
	private MousePicker picker;
	private AudioMaster audioMaster = new AudioMasterBuffered();
	
	private EntityManager entityManager = new EntityManagerStructured();
	private TerrainManager terrainManager = new TerrainManagerStructured();
	private WaterManager waterManager = new WaterManagerStructured();
	private ChunkManager chunkManager = 
			new ChunkManagerStructured(CHUNK_WORLD_SIZE, new Vector3f(0,0,0));
	private ParticleManager particleManager = new ParticleManagerStructured();
	private LightManager lightManager = new LightManagerStructured();
	private AudioManager audioManager = new AudioManagerStructured(audioMaster);
	private GuiManager guiManager = new GuiManagerStructured();
	private TextManager textManager = new TextManagerStructured();
	
	public SceneGame() {}
	
	public SceneGame(GameMap map) {
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
	public Player getPlayer() {
		return this.player;
	}
	
	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public Camera getCamera() {
		return this.camera;
	}
	
	@Override
	public void setCamera(Camera camera) {
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
	public EntityManager getEntities() {
		return this.entityManager;
	}

	/* 
	 * @Terrains
	 */

	@Override
	public TerrainManager getTerrains() {
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
	public ChunkManager getChunks() {
		return this.chunkManager;
	}

	
	/* 
	 * @Particles
	 */

	@Override
	public ParticleManager getParticles() {
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
	public AudioManager getAudioSources() {
		return this.audioManager;
	}
	
	/* 
	 * @GuiTexture
	 */

	@Override
	public GuiManager getGuis() {
		return this.guiManager;
	}
	
	/* 
	 * @GuiText
	 */

	@Override
	public TextManager getTexts() {
		return this.textManager;
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
				
				for(Terrain terrain : this.terrainManager.getAll()) {
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
				
				for(Terrain terrain : this.terrainManager.getAll()) {
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
		this.textManager.clearAll();		
	}

}
