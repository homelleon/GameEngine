package optimisations;

import java.util.Collection;
import java.util.List;

import entities.Camera;
import entities.Entity;
import terrains.Terrain;

public class CutOptimisation implements Optimisation {
	private DistanceCutConst cutDist;
	
		
	

	public CutOptimisation() {
		this.cutDist = new DistanceCutConst();
	}

	@Override
	public void optimize(Camera camera, Collection<Entity> entities, Collection<Terrain> terrains) {
		cutDist.cutRender(camera, entities, terrains);						
	}

}
