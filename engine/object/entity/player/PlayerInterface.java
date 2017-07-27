package object.entity.player;

import java.util.Collection;

import object.entity.entity.Entity;
import object.terrain.terrain.TerrainInterface;

public interface PlayerInterface extends Entity {

	public void move(Collection<TerrainInterface> terrains);
}
