package object.map.levelMap;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import object.entity.entity.Entity;
import object.entity.entity.EntityInterface;
import object.model.TexturedModel;
import renderer.loader.Loader;
import tool.EngineUtils;

/**
 * Map that controls entity objects to load in the editor menu. 
 * 
 * @author homelleon
 *
 */
public class LevelMap implements LevelMapInterface {
	
	private List<EntityInterface> entities = new ArrayList<EntityInterface>();
	
	private Loader loader;
	
	public LevelMap(Loader loader) {
		this.loader = loader;		
	}

	@Override
	public List<EntityInterface> getALLEntities() {
		return entities;
	}

	@Override
	public EntityInterface getEntity(int index) {
		return entities.get(index);
	}
	
	@Override
	public void loadEntity(String name, String model, String texName) {
		TexturedModel staticModel = EngineUtils.loadStaticModel(model, texName, loader);
		Entity entity = new Entity(name, staticModel, new Vector3f(0,0,0), 0, 0, 0, 1);
		this.entities.add(entity);
	}

}
