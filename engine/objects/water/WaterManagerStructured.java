package objects.water;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WaterManagerStructured implements WaterManager {
	
	Map<String, WaterTile> waters = new HashMap<String, WaterTile>();

	@Override
	public void addAll(Collection<WaterTile> waterList) {
		if((waterList != null) && (!waterList.isEmpty())) {
			for(WaterTile water : waterList) {
				this.waters.put(water.getName(), water);
			}
		}
	}

	@Override
	public void add(WaterTile water) {
		if(water != null) {
			this.waters.put(water.getName(), water); 		
		}
	}

	@Override
	public WaterTile getByName(String name) {
		WaterTile water = null;
		if(this.waters.containsKey(name)) {
			water = this.waters.get(name);
		}
		return water;
	}

	@Override
	public Collection<WaterTile> getAll() {
		return this.waters.values();
	}

	@Override
	public void clearAll() {
		this.waters.clear();
	}

}
