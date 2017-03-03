package scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.util.vector.Vector3f;

import audio.AudioMaster;
import audio.AudioSource;
import cameras.Camera;
import entities.Entity;
import entities.EntityManager;
import entities.EntityManagerStructured;
import entities.Light;
import entities.Player;
import fontMeshCreator.GuiText;
import guis.GuiTexture;
import maps.GameMap;
import particles.ParticleSystem;
import terrains.Terrain;
import textures.Texture;
import toolbox.Frustum;
import toolbox.MousePicker;
import voxels.Chunk;
import water.WaterTile;

public class SceneGame implements Scene {
	
	private Player player;
	private Camera camera;
	private Light sun;
	
	private Texture environmentMap = Texture.newEmptyCubeMap(128);
	
	private Frustum frustum = new Frustum();
	private MousePicker picker;
	private AudioMaster audioMaster;
	
	private EntityManager entityManager = new EntityManagerStructured();
	private Map<String, Terrain> terrains = new WeakHashMap<String, Terrain>();
	private Map<String, WaterTile> waters = new WeakHashMap<String, WaterTile>();
	private List<Chunk> chunks = new ArrayList<Chunk>();
	private Map<String, ParticleSystem> particles = new WeakHashMap<String, ParticleSystem>();
	private Map<String, Light> lights = new WeakHashMap<String, Light>();
	private Map<String, AudioSource> audioSources = new WeakHashMap<String, AudioSource>();
	private Map<String, GuiTexture> guis = new WeakHashMap<String, GuiTexture>();
	private Map<String, GuiText> texts = new WeakHashMap<String, GuiText>();
	
	public SceneGame() {}
	
	public SceneGame(GameMap map) {
		this.getEntities().addAll(map.getEntities().values());
		this.addAllTerrains(map.getTerrains().values());
		this.addAllWaters(map.getWaters().values());
		this.addAllParticles(map.getParticles().values());
		this.addAllLights(map.getLights().values());
		this.addAllAudioSources(map.getAudioSources().values());
		this.addAllGuis(map.getGuis().values());
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
	
	public void setAudioMaster(AudioMaster master) {
		this.audioMaster = master;
	}
	
	public AudioMaster getAudioMaster() {
		return this.audioMaster;
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
	public Map<String, Terrain> getTerrains() {
		return this.terrains;
	}

	@Override
	public void addTerrain(Terrain terrain) {
		this.terrains.put(terrain.getName(), terrain);
	}

	@Override
	public void addAllTerrains(Collection<Terrain> terrainList) {
		for(Terrain terrain : terrainList) {
			this.terrains.put(terrain.getName(), terrain);
		}
	}
	
	/* 
	 * @Waters
	 */
	
	@Override
	public Map<String, WaterTile> getWaters() {
		return this.waters;
	}

	@Override
	public void addWater(WaterTile water) {
		this.waters.put(water.getName(), water);
	}

	@Override
	public void addAllWaters(Collection<WaterTile> waterList) {
		for(WaterTile water : waterList) {
			this.waters.put(water.getName(), water);
		}
	}
	
	/* 
	 * @VoxelGrids
	 */
	
	@Override
	public List<Chunk> getChunks() {
		return this.chunks;
	}

	@Override
	public void addChunk(Chunk chunk) {
		this.chunks.add(chunk);
	}

	@Override
	public void addAllChunks(Collection<Chunk> chunkList) {
		for(Chunk chunk : chunkList) {
			this.chunks.add(chunk);
		}
	}
	
	/* 
	 * @Particles
	 */

	@Override
	public Map<String, ParticleSystem> getParticles() {
		return this.particles;
	}

	@Override
	public void addParticle(ParticleSystem particle) {
		this.particles.put(particle.getName(), particle);
	}

	@Override
	public void addAllParticles(Collection<ParticleSystem> particleList) {
		for(ParticleSystem particle : particleList) {
			this.particles.put(particle.getName(), particle);
		}
	}


	/* 
	 * @Lights
	 */
	

	@Override
	public Map<String, Light> getLights() {
		return this.lights;
	}

	@Override
	public void addLight(Light light) {
		this.lights.put(light.getName(), light);
	}

	@Override
	public void addAllLights(Collection<Light> lightList) {
		for(Light light : lightList) {
			this.lights.put(light.getName(), light);
		}
	}
	

	/* 
	 * @AudioSources
	 */
	
	@Override
	public Map<String, AudioSource> getAudioSources() {
		return this.audioSources;
	}

	@Override
	public void addAudioSource(AudioSource source) {
		this.audioSources.put(source.getName(), source);
	}

	@Override
	public void addAllAudioSources(Collection<AudioSource> sourceList) {
		for(AudioSource source : sourceList) {
			this.audioSources.put(source.getName(), source);
		}
	}
	
	/* 
	 * @GuiTexture
	 */

	@Override
	public Map<String, GuiTexture> getGuis() {
		return this.guis;
	}

	@Override
	public void addGui(GuiTexture gui) {
		this.guis.put(gui.getName(), gui);
	}

	@Override
	public void addAllGuis(Collection<GuiTexture> guiList) {
		for(GuiTexture gui : guiList) {
			this.guis.put(gui.getName(), gui);
		}
	}
	
	/* 
	 * @GuiText
	 */

	@Override
	public Map<String, GuiText> getTexts() {
		return this.texts;
	}

	@Override
	public void addText(GuiText text) {
		this.texts.put(text.getName(), text);
	}

	@Override
	public void addAllTexts(Collection<GuiText> textList) {
		for(GuiText text : textList) {
			this.texts.put(text.getName(), text);
		}
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
	public void spreadEntitiesOnHeights() {
		if (!entityManager.getAll().isEmpty()) {
			for(Entity entity : this.entityManager.getAll()) {
				float terrainHeight = 0;
				
				for(Terrain terrain : this.terrains.values()) {
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
				
				for(Terrain terrain : this.terrains.values()) {
					terrainHeight += terrain.getHeightOfTerrain(system.getPosition().x, system.getPosition().z);
				}
				system.setPosition(new Vector3f(system.getPosition().x, terrainHeight, system.getPosition().z));
			}
		}
	}
	
	
	public void cleanUp() {
		this.environmentMap.delete();
		this.entityManager.clearAll();
		this.terrains.clear();
		this.waters.clear();
		this.chunks.clear();
		this.particles.clear();
		this.lights.clear();
		this.audioSources.clear();
		this.guis.clear();
		this.texts.clear();		
	}

}
