package optimisations;

import java.util.Collection;

import cameras.Camera;
import entities.Entity;
import terrains.Terrain;
import voxels.Chunk;

public interface Optimisation {
	
	public void optimize(Camera camera, Collection<Entity> entities, Collection<Terrain> terrains, Collection<Chunk> chunks);
	
}
