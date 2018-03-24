package renderer.processor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.Entity;
import object.terrain.Terrain;
import primitive.model.Model;

/**
 * 
 * @author homelleon
 * @see ConreteSceneProcessor
 */
public interface SceneProcessor {

	public void processTerrain(Terrain terrain, Collection<Terrain> terrains);

	public void processEntity(Entity entity, Map<Model, List<Entity>> entities);

}
