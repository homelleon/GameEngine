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

	public void loadMap();
	public GameMap getMap();
	public void render();
	public void init();
	public void cleanUp();
}
