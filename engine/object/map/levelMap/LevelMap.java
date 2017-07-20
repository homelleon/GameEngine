package object.map.levelMap;

import java.util.ArrayList;
import java.util.List;

import object.entity.entity.Entity;
import object.entity.entity.EntityBuilder;
import object.entity.entity.TexturedEntityBuilder;

/**
 * Map that controls entity objects to load in the editor menu. 
 * 
 * @author homelleon
 *
 */
public class LevelMap implements LevelMapInterface {
	
	private List<Entity> entities = new ArrayList<Entity>();

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
		EntityBuilder builder = new TexturedEntityBuilder();
		builder.setModel(model).setTexture(texName);
		Entity entity = builder.createEntity(name);
		this.entities.add(entity);
	}

}
