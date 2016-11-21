package optimisations;

import java.util.Collection;
import java.util.List;

import entities.Camera;
import entities.Entity;
import terrains.Terrain;

public interface Optimisation {
	
	public void optimize(Camera camera, Collection<Entity> entities, Collection<Terrain> terrains);
	
}
