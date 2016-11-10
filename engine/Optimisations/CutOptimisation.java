package optimisations;

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
	public void optimize(Camera camera, List<Entity> entities, List<Terrain> terrains) {
		cutDist.cutRender(camera, entities, terrains);						
	}

}
