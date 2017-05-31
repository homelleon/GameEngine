package renderers.scene;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import objects.entities.EntityInterface;
import objects.models.TexturedModel;
import objects.terrains.TerrainInterface;
import renderers.viewCulling.Frustum;

/**
 * 
 * @author homelleon
 * @see SceneProcessor
 */
public interface SceneProcessorInterface {
	
	public void processTerrain(TerrainInterface terrain, Collection<TerrainInterface> terrains);
	
	public void processEntity(EntityInterface entity,
			Map<TexturedModel, List<EntityInterface>> entities, Frustum frustum);

	public void processNormalMapEntity(EntityInterface entity,
			Map<TexturedModel, List<EntityInterface>> normalMapEntities, Frustum frustum);
		
	public void processShadowEntity(EntityInterface entity,
			Map<TexturedModel, List<EntityInterface>> entities, Frustum frustum);

	public void processShadowNormalMapEntity(EntityInterface entity,
			Map<TexturedModel, List<EntityInterface>> normalMapEntities, Frustum frustum);

}
