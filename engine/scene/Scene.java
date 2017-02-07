package scene;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import audio.AudioMaster;
import audio.AudioSource;
import cameras.Camera;
import entities.Entity;
import entities.EntityManager;
import entities.Light;
import entities.Player;
import fontMeshCreator.GuiText;
import guis.GuiTexture;
import particles.ParticleSystem;
import terrains.Terrain;
import textures.Texture;
import toolbox.Frustum;
import voxels.Chunk;
import water.WaterTile;

public interface Scene {
	
	public Texture getEnvironmentMap();
	
	Player getPlayer();
	void setPlayer(Player player);
	
	Camera getCamera();
	void setCamera(Camera camera);
	
	Light getSun();
	void setSun(Light sun);
	
	public void setAudioMaster(AudioMaster master);
	public AudioMaster getAudioMaster();
	
	EntityManager getEntities();
	
	Map<String, Terrain> getTerrains();
	void addTerrain(Terrain terrain);
	void addAllTerrains(Collection<Terrain> terrainList);
	
	Map<String, WaterTile> getWaters();
	void addWater(WaterTile water);
	void addAllWaters(Collection<WaterTile> waterList);	
	
	List<Chunk> getChunks();
	void addChunk(Chunk chunk);
	void addAllChunks(Collection<Chunk> chunkList);
	
	Map<String, ParticleSystem> getParticles();
	void addParticle(ParticleSystem particle);
	void addAllParticles(Collection<ParticleSystem> particleList);
	
	Map<String, Light> getLights();
	void addLight(Light light);
	void addAllLights(Collection<Light> lightList);
	
	Map<String, AudioSource> getAudioSources();
	void addAudioSource(AudioSource source);
	void addAllAudioSources(Collection<AudioSource> sourceList);	
	
	Map<String, GuiTexture> getGuis();
	void addGui(GuiTexture gui);
	void addAllGuis(Collection<GuiTexture> guiList);

	Map<String, GuiText> getTexts();
	void addText(GuiText text);
	void addAllTexts(Collection<GuiText> textList);
	
	Frustum getFrustum();
	
	void spreadEntitiesOnHeights();
	void spreadParitclesOnHeights(Collection<ParticleSystem> systems);
	void cleanUp();

}
