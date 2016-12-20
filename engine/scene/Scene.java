package scene;

import java.util.Collection;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import audio.AudioSource;
import cameras.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.GuiText;
import guis.GuiTexture;
import particles.ParticleSystem;
import terrains.Terrain;
import voxels.VoxelGrid;
import water.WaterTile;

public interface Scene {
	
	Player getPlayer();
	void setPlayer(Player player);
	
	Camera getCamera();
	void setCamera(Camera camera);
	
	Light getSun();
	void setSun(Light sun);
	
	Map<String, Entity> getEntities();
	void addEntity(Entity entity);
	void addAllEntities(Collection<Entity> entityList);
	
	Map<String, Terrain> getTerrains();
	void addTerrain(Terrain terrain);
	void addAllTerrains(Collection<Terrain> terrainList);
	
	Map<String, WaterTile> getWaters();
	void addWater(WaterTile water);
	void addAllWaters(Collection<WaterTile> waterList);	
	
	Map<String, VoxelGrid> getVoxelGrids();
	void addVoxelGrid(VoxelGrid grid);
	void addAllVoxelGrids(Collection<VoxelGrid> gridList);
	
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
	
	void spreadEntitiesOnHeights();
	void spreadParitclesOnHeights(Collection<ParticleSystem> systems);
	void createVoxelTerrain(int size, Vector3f position);


}
