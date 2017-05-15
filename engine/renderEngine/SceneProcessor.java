package renderEngine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import entities.Entity;
import models.TexturedModel;
import terrains.Terrain;
import viewCulling.Frustum;

/**
 * 
 * @author homelleon
 * @see SceneProcessorSimple
 */
public interface SceneProcessor {
	
	public void processTerrain(Terrain terrain, Collection<Terrain> terrains);
	
	public void processEntity(Entity entity,
			Map<TexturedModel, List<Entity>> entities, Frustum frustum);

	public void processNormalMapEntity(Entity entity,
			Map<TexturedModel, List<Entity>> normalMapEntities, Frustum frustum);
		
	public void processShadowEntity(Entity entity,
			Map<TexturedModel, List<Entity>> entities, Frustum frustum);

	public void processShadowNormalMapEntity(Entity entity,
			Map<TexturedModel, List<Entity>> normalMapEntities, Frustum frustum);

}
