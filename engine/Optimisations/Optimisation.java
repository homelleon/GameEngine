package Optimisations;

import java.util.List;

import entities.Camera;
import entities.Entity;
import terrains.Terrain;

public interface Optimisation {
	
	public void optimize(Camera camera, List<Entity> entities, List<Terrain> terrains);
	
}
