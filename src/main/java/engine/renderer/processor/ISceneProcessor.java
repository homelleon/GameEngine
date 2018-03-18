package renderer.processor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.Entity;
import object.terrain.terrain.ITerrain;
import primitive.model.Model;

/**
 * 
 * @author homelleon
 * @see SceneProcessor
 */
public interface ISceneProcessor {

	public void processTerrain(ITerrain terrain, Collection<ITerrain> terrains);

	public void processEntity(Entity entity, Map<Model, List<Entity>> entities);

}
