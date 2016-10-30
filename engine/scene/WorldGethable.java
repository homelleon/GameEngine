package scene;

import entities.Camera;
import entities.Entity;
import entities.Player;
import guis.GuiTexture;
import maps.GameMap;
import particles.ParticleSystem;
import terrains.Terrain;
import water.WaterTile;

public interface WorldGethable {

	public GameMap getMapByName(String name);
	public Camera getCameraByName(String name);
	public Entity getEntityByName(String name);
	public Player getPlayerByName(String name);
	public Terrain getTerrainByName(String name);
	public WaterTile getWaterByName(String name);
	public GuiTexture getGuiByName(String name);
	public ParticleSystem getParticlesByName(String name);
	public void render();
	public void cleanUp();
	

}
