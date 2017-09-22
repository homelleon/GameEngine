package renderer.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.entity.IEntity;
import object.terrain.terrain.ITerrain;
import primitive.model.Model;

public class SceneProcessor implements ISceneProcessor {


	@Override
	public void processTerrain(ITerrain terrain, Collection<ITerrain> terrains) {
		terrains.add(terrain);
	}

	@Override
	public void processEntity(IEntity entity, Map<Model, List<IEntity>> entities) {
		Model entityModel = entity.getModel();
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
	public void processShadowEntity(IEntity entity, Map<Model, List<IEntity>> entities) {
		Model entityModel = entity.getModel();
		List<IEntity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<IEntity> newBatch = new ArrayList<IEntity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

}
