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
		List<Model> entityModels = entity.getModels();
		entityModels.forEach(model -> {
			List<IEntity> batch = entities.get(model);
			if (batch != null) {
				batch.add(entity);
			} else {
				List<IEntity> newBatch = new ArrayList<IEntity>();
				newBatch.add(entity);
				entities.put(model, newBatch);
			}
		});
	}

}
