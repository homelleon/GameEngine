package engine.terrains;

import java.util.ArrayList;
import java.util.List;

import engine.renderEngine.Loader;
import engine.scene.SceneObjectTools;

public class TerrainManager {
	
	public static List<Terrain> createTerrain(Loader loader) {
		List<Terrain> terrains = new ArrayList<Terrain>();
		
		Terrain terrain = SceneObjectTools.createMultiTexTerrain(0,0,"grass", 
				"ground", "floweredGrass", "road", "blendMap", 100f, 5, 0.5f, loader);
		//Terrain terrain2 = SceneObjectTools.createMultiTexTerrain(0,1,"grass", 
		//"ground", "floweredGrass", "road", "blendMap", "heightMap", loader);
		terrains.add(terrain);
		//terrains.add(terrain2);
		
		return terrains;
	}

}
