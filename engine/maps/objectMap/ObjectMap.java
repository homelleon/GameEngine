package maps.objectMap;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import objects.entities.Entity;
import objects.entities.EntityInterface;
import objects.models.TexturedModel;
import renderers.Loader;
import toolbox.EngineUtils;

/**
 * Map that controls entity objects to load in the editor menu. 
 * 
 * @author homelleon
 *
 */
public class ObjectMap implements ObjectMapInterface {
	
	private List<EntityInterface> entities = new ArrayList<EntityInterface>();
	
	private Loader loader;
	
	public ObjectMap(Loader loader) {
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
