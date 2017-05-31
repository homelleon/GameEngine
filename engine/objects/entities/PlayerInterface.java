package objects.entities;

import java.util.Collection;

import objects.terrains.TerrainInterface;

public interface PlayerInterface extends EntityInterface{
	
	public void move(Collection<TerrainInterface> terrains);
}
