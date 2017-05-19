package entities;

import java.util.Collection;

import terrains.TerrainInterface;

public interface PlayerInterface extends EntityInterface{
	
	public void move(Collection<TerrainInterface> terrains);
}
