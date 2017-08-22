package manager.water;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import object.water.WaterTile;

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
		WaterTile water = null;
		if (this.waters.containsKey(name)) {
			water = this.waters.get(name);
		}
		return water;
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
