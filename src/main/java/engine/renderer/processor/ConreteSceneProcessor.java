package renderer.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.Entity;
import object.terrain.Terrain;
import primitive.model.Model;

public class ConreteSceneProcessor implements SceneProcessor {


	@Override
	public void processTerrain(Terrain terrain, Collection<Terrain> terrains) {
		terrains.add(terrain);
	}

	@Override
	public void processEntity(Entity entity, Map<Model, List<Entity>> entities) {
		List<Model> entityModels = entity.getModels();
		entityModels.forEach(model -> {
			List<Entity> batch = entities.get(model);
			if (batch != null) {
				batch.add(entity);
			} else {
				List<Entity> newBatch = new ArrayList<Entity>();
				newBatch.add(entity);
				entities.put(model, newBatch);
			}
		});
	}

}
