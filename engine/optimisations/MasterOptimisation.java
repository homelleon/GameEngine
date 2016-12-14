package optimisations;

import java.util.Collection;

import org.lwjgl.util.vector.Matrix4f;

import cameras.Camera;
import entities.Entity;
import terrains.Terrain;
import voxels.Area;
import voxels.Chunk;

public class MasterOptimisation implements Optimisation {
	private DistanceCutConst cutDist;
	private RayCasting rCast;
	
		
	

	public MasterOptimisation(Camera camera, Matrix4f projection) {
		this.cutDist = new DistanceCutConst();
		this.rCast = new RayCasting(camera, projection);
	}

	public void optimize(Camera camera, Collection<Entity> entities, Collection<Terrain> terrains, Collection<Area> areas) {
		cutDist.cutRender(camera, entities, terrains, areas);	
		//rCast.checkEntities(entities);
	}

}