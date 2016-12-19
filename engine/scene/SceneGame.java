package scene;

import java.util.Collection;
import java.util.Map;

import audio.AudioSource;
import cameras.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.GuiText;
import guis.GuiTexture;
import maps.GameMap;
import particles.ParticleSystem;
import terrains.Terrain;
import voxels.VoxelGrid;
import water.WaterTile;

public class SceneGame implements Scene {
	
	private Map<String, Entity> entities;
	private Map<String, Player> players;
	private Map<String, Terrain> terrains;
	private Map<String, WaterTile> waters;
	private Map<String, VoxelGrid> grids;
	private Map<String, ParticleSystem> particles;
	private Map<String, Camera> cameras;
	private Map<String, Light> lights;
	private Map<String, AudioSource> audioSources;
	private Map<String, GuiTexture> guis;
	private Map<String, GuiText> texts;
	
	public SceneGame() {}
	
	public SceneGame(GameMap map) {
		this.addAllEntities(map.getEntities().values());
		this.addAllPlayers(map.getPlayers().values());
		this.addAllTerrains(map.getTerrains().values());
		this.addAllWaters(map.getWaters().values());
		this.addAllParticles(map.getParticles().values());
		this.addAllCameras(map.getCameras().values());
		this.addAllLights(map.getLights().values());
		this.addAllAudioSources(map.getAudioSources().values());
		this.addAllGuis(map.getGuis().values());
	}
	
	/* 
	 * @Enitites
	 */
	@Override
	public Map<String, Entity> getEntities() {
		return this.entities;
	}

	@Override
	public void addEntity(Entity entity) {
		this.entities.put(entity.getName(), entity);
	}

	@Override
	public void addAllEntities(Collection<Entity> entityList) {
		for(Entity entity : entityList) {
			this.entities.put(entity.getName(), entity);
		}
	}
	
	/* 
	 * @Players
	 */	
	@Override
	public Map<String, Player> getPlayers() {
		return this.players;
	}

	@Override
	public void addPlayer(Player player) {
		this.players.put(player.getName(), player);
	}

	@Override
	public void addAllPlayers(Collection<Player> playerList) {
		for(Player player : playerList) {
			this.players.put(player.getName(), player);
		}
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
	public Map<String, VoxelGrid> getVoxelGrids() {
		return this.grids;
	}

	@Override
	public void addVoxelGrid(VoxelGrid grid) {
		this.grids.put(grid.getName(), grid);
	}

	@Override
	public void addAllVoxelGrids(Collection<VoxelGrid> gridList) {
		for(VoxelGrid grid : gridList) {
			this.grids.put(grid.getName(), grid);
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
	 * @Cameras
	 */

	@Override
	public Map<String, Camera> getCameras() {
		return this.cameras;
	}

	@Override
	public void addCamera(Camera camera) {
		this.cameras.put(camera.getName(), camera);
	}

	@Override
	public void addAllCameras(Collection<Camera> cameraList) {
		for(Camera camera : cameraList) {
			this.cameras.put(camera.getName(), camera);
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

}
