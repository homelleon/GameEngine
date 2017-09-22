package renderer.processor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.entity.IEntity;
import object.terrain.terrain.ITerrain;
import primitive.model.Model;

/**
 * 
 * @author homelleon
 * @see SceneProcessor
 */
public interface ISceneProcessor {

	public void processTerrain(ITerrain terrain, Collection<ITerrain> terrains);

	public void processEntity(IEntity entity, Map<Model, List<IEntity>> entities);

	public void processShadowEntity(IEntity entity, Map<Model, List<IEntity>> entities);

}
