package entities;

import java.util.Collection;

import terrains.Terrain;

public interface Player extends Entity{
	
	public void move(Collection<Terrain> terrains);
}
