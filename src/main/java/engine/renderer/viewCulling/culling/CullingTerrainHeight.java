package renderer.viewCulling.culling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.terrain.terrain.ITerrain;

public class CullingTerrainHeight implements Culling {

	private List<ITerrain> terrains = new ArrayList<ITerrain>();
	private ICamera camera;

	public CullingTerrainHeight(ICamera camera, Collection<ITerrain> terrains) {
		this.terrains.addAll(terrains);
		this.camera = camera;
	}

	@Override
	public Boolean getVisibility(IEntity entity) {
		Boolean isVisible = false;

		return isVisible;
	}

	@Override
	public void update(ICamera camera) {

	}

}
