package object.entity.player;

import java.util.Collection;

import object.entity.entity.EntityInterface;
import object.terrain.terrain.TerrainInterface;

public interface PlayerInterface extends EntityInterface{
	
	public void move(Collection<TerrainInterface> terrains);
}
