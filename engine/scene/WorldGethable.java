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

	public GameMap getMap(String name);
	public Camera getCamera(String name);
	public Entity getEntity(String name);
	public Player getPlayer(String name);
	public Terrain getTerrain(String name);
	public WaterTile getWater(String name);
	public GuiTexture getGui(String name);
	public ParticleSystem getParticles(String name);
	public void render();
	public void cleanUp();
	

}
