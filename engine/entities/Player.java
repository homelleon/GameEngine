package entities;

import java.util.List;

import terrains.Terrain;

public interface Player extends Entity{
	
	public void move(List<Terrain> terrains);
}
