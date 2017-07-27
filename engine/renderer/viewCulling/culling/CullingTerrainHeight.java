package renderer.viewCulling.culling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import object.camera.CameraInterface;
import object.entity.entity.Entity;
import object.terrain.terrain.TerrainInterface;

public class CullingTerrainHeight implements Culling {

	private List<TerrainInterface> terrains = new ArrayList<TerrainInterface>();
	private CameraInterface camera;

	public CullingTerrainHeight(CameraInterface camera, Collection<TerrainInterface> terrains) {
		this.terrains.addAll(terrains);
		this.camera = camera;
	}

	@Override
	public Boolean getVisibility(Entity entity) {
		Boolean isVisible = false;

		return isVisible;
	}

	@Override
	public void update(CameraInterface camera) {

	}

}
