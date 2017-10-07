package object.entity.player;

import java.util.Collection;

import object.entity.entity.IEntity;
import object.terrain.terrain.ITerrain;

/**
 * 
 * @author homelleon
 * @see Player
 */
public interface IPlayer extends IEntity {

	public void move(Collection<ITerrain> terrains);
}
