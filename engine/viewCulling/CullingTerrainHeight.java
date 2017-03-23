package viewCulling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cameras.Camera;
import entities.Entity;
import terrains.Terrain;

public class CullingTerrainHeight implements Culling {
	
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private Camera camera;
	
	public CullingTerrainHeight(Camera camera, Collection<Terrain> terrains) {
			this.terrains.addAll(terrains);
			this.camera = camera;
	}

	@Override
	public Boolean getVisibility(Entity entity) {
		Boolean isVisible = false;
		
		return isVisible;
	}

	@Override
	public void update(Camera camera) {
		
	}

}
