package scene;

import entities.PlayerCamera;
import entities.Entity;
import entities.Player;
import guis.GuiTexture;
import maps.GameMap;
import particles.ParticleSystem;
import terrains.Terrain;
import water.WaterTile;

public interface WorldGethable {

	public void loadMap(String name);
	public GameMap getMap();
	public void render();
	public void init();
	public void cleanUp();
	public void setScenePaused(boolean isScenePaused);
	
}
