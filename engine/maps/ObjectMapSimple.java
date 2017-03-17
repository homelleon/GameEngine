package maps;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.EntityTextured;
import models.TexturedModel;
import renderEngine.Loader;
import toolbox.ObjectUtils;

/**
 * Map that controls entity objects to load in the editor menu. 
 * 
 * @author homelleon
 *
 */
public class ObjectMapSimple implements ObjectMap {
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	private Loader loader;
	
	public ObjectMapSimple(Loader loader) {
		this.loader = loader;		
	}

	@Override
	public List<Entity> getALLEntities() {
		return entities;
	}

	@Override
	public Entity getEntity(int index) {
		return entities.get(index);
	}
	
	@Override
	public void loadEntity(String name, String model, String texName) {
		TexturedModel staticModel = ObjectUtils.loadStaticModel(model, texName, loader);
		EntityTextured entity = new EntityTextured(name, staticModel, new Vector3f(0,0,0), 0, 0, 0, 1);
		this.entities.add(entity);
	}

}
