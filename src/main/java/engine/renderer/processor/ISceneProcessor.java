package renderer.processor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.entity.IEntity;
import object.model.textured.TexturedModel;
import object.terrain.terrain.ITerrain;
import renderer.viewCulling.frustum.Frustum;

/**
 * 
 * @author homelleon
 * @see SceneProcessor
 */
public interface ISceneProcessor {

	public void processTerrain(ITerrain terrain, Collection<ITerrain> terrains);

	public void processEntity(IEntity entity, Map<TexturedModel, List<IEntity>> entities);

	public void processShadowEntity(IEntity entity, Map<TexturedModel, List<IEntity>> entities);

	public void processShadowNormalMapEntity(IEntity entity, Map<TexturedModel, List<IEntity>> normalMapEntities);

}
