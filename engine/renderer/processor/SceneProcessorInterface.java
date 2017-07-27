package renderer.processor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.entity.Entity;
import object.model.TexturedModel;
import object.terrain.terrain.TerrainInterface;
import renderer.viewCulling.frustum.Frustum;

/**
 * 
 * @author homelleon
 * @see SceneProcessor
 */
public interface SceneProcessorInterface {

	public void processTerrain(TerrainInterface terrain, Collection<TerrainInterface> terrains);

	public void processEntity(Entity entity, Map<TexturedModel, List<Entity>> entities, Frustum frustum);

	public void processNormalMapEntity(Entity entity, Map<TexturedModel, List<Entity>> normalMapEntities,
			Frustum frustum);

	public void processShadowEntity(Entity entity, Map<TexturedModel, List<Entity>> entities, Frustum frustum);

	public void processShadowNormalMapEntity(Entity entity, Map<TexturedModel, List<Entity>> normalMapEntities,
			Frustum frustum);

}
