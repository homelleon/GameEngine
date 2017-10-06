package manager.water;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import object.water.WaterTile;

/**
 * Manages water tiles.
 * 
 * @author homelleon
 *
 */
public class WaterManager implements IWaterManager {

	Map<String, WaterTile> waters = new HashMap<String, WaterTile>();

	@Override
	public void addAll(Collection<WaterTile> waterList) {
		if ((waterList != null) && (!waterList.isEmpty())) {
			waterList.forEach(water -> 
				this.waters.put(water.getName(), water));
		}
	}

	@Override
	public void add(WaterTile water) {
		if (water != null) {
			this.waters.put(water.getName(), water);
		}
	}

	@Override
	public WaterTile getByName(String name) {
		return this.waters.containsKey(name) ? this.waters.get(name) : null;
	}

	@Override
	public Collection<WaterTile> getAll() {
		return this.waters.values();
	}

	@Override
	public void clean() {
		this.waters.clear();
	}

}
