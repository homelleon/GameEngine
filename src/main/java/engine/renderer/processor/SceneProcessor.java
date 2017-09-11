package renderer.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.entity.IEntity;
import object.model.textured.TexturedModel;
import object.terrain.terrain.ITerrain;

public class SceneProcessor implements ISceneProcessor {

	@Override
	public void processTerrain(ITerrain terrain, Collection<ITerrain> terrains) {
		terrains.add(terrain);
	}

	@Override
	public void processEntity(IEntity entity, Map<TexturedModel, List<IEntity>> entities) {
		TexturedModel entityModel = entity.getModel();
		List<IEntity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<IEntity> newBatch = new ArrayList<IEntity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	@Override
	public void processShadowEntity(IEntity entity, Map<TexturedModel, List<IEntity>> entities) {
		TexturedModel entityModel = entity.getModel();
		List<IEntity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<IEntity> newBatch = new ArrayList<IEntity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	@Override
	public void processShadowNormalMapEntity(IEntity entity, Map<TexturedModel, List<IEntity>> normalMapEntities) {
		TexturedModel entityModel = entity.getModel();
		List<IEntity> batch = normalMapEntities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<IEntity> newBatch = new ArrayList<IEntity>();
			newBatch.add(entity);
			normalMapEntities.put(entityModel, newBatch);
		}
	}

}
