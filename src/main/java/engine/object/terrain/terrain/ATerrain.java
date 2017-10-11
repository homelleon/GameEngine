package object.terrain.terrain;

import object.GameObject;
import object.camera.ICamera;

public abstract class ATerrain extends GameObject {

	protected ATerrain(String name, ICamera camera) {
		super(name);
		addChild(new TerrainQuadTree(camera));
	}
	
	public void updateQuadTree(ICamera camera) {
		if(camera.isMoved()) {
			((TerrainQuadTree) getChildren().get(0)).updateQuadTree(camera);
		}
	}
	
	
}