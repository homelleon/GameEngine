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
		if ((waterList != null) && (!waterList.isEmpty())) {
			waterList.forEach(water -> 
				this.waters.put(water.getName(), water));
		}
	}

	public void add(Water water) {
		if (water != null) {
			this.waters.put(water.getName(), water);
		}
	}

	public Water getByName(String name) {
		return this.waters.containsKey(name) ? this.waters.get(name) : null;
	}

	public Collection<Water> getAll() {
		return this.waters.values();
	}

	public void clean() {
		this.waters.clear();
	}

}
