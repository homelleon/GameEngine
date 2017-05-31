package viewCulling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import objects.cameras.CameraInterface;
import objects.entities.EntityInterface;
import objects.terrains.TerrainInterface;

public class CullingTerrainHeight implements Culling {
	
	private List<TerrainInterface> terrains = new ArrayList<TerrainInterface>();
	private CameraInterface camera;
	
	public CullingTerrainHeight(CameraInterface camera, Collection<TerrainInterface> terrains) {
			this.terrains.addAll(terrains);
			this.camera = camera;
	}

	@Override
	public Boolean getVisibility(EntityInterface entity) {
		Boolean isVisible = false;
		
		return isVisible;
	}

	@Override
	public void update(CameraInterface camera) {
		
	}

}
