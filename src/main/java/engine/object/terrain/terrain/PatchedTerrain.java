package object.terrain.terrain;

import manager.octree.Node;
import object.camera.ICamera;
import primitive.buffer.Loader;
import primitive.buffer.PatchVAO;

public class PatchedTerrain extends Node {
	
	public PatchedTerrain(String name, ICamera camera) {
		super();
		addChild(new TerrainQuadTree(camera));
	}
	
	public void updateQuadTree(ICamera camera) {
		if(camera.isMoved()) {
			((TerrainQuadTree) getChildren().get(0)).updateQuadTree(camera);
		}
	}

}
