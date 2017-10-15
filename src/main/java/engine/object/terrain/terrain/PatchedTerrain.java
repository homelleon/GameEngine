package object.terrain.terrain;

import core.settings.EngineSettings;
import manager.octree.Node;
import object.camera.ICamera;

public class PatchedTerrain extends Node {
	
	public PatchedTerrain(String name, ICamera camera) {
		super();
		EngineSettings.lod_morph_areas = new int[EngineSettings.LOD_RANGES.length];
		for(int i = 0; i < EngineSettings.LOD_RANGES.length; i++) {
			if(EngineSettings.LOD_RANGES[i] == 0) {
				EngineSettings.lod_morph_areas[i] = 0;
			} else { 
				EngineSettings.lod_morph_areas[i] = EngineSettings.LOD_RANGES[i] - (int) ((EngineSettings.SCALE_XZ / TerrainQuadTree.getRootNodes()) / Math.pow(2, i + 1));
			}
		}
		addChild(new TerrainQuadTree(camera));
	}
	
	public void updateQuadTree(ICamera camera) {
		((TerrainQuadTree) getChildren().get(0)).updateQuadTree(camera);
	}

}
