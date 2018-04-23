package manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import object.water.Water;

/**
 * Manages water tiles.
 * 
 * @author homelleon
 *
 */
public class WaterManager {

	Map<String, Water> waters = new HashMap<String, Water>();

	public void addAll(Collection<Water> waterList) {
		if (waterList == null || waterList.isEmpty()) return;
		waterList.forEach(water -> waters.put(water.getName(), water));
	}

	public void add(Water water) {
		if (water == null) return;
		waters.put(water.getName(), water);
	}

	public Water getByName(String name) {
		return waters.containsKey(name) ? waters.get(name) : null;
	}

	public Collection<Water> getAll() {
		return waters.values();
	}

	public void clean() {
		waters.clear();
	}

}
