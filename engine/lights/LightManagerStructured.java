package lights;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Light manager for controling and storing structured map and arrays of 
 * lights.
 * 
 * @author homelleon
 * @version 1.0
 */

public class LightManagerStructured implements LightManager {
	
	Map<String, Light> lights = new HashMap<String, Light>();

	@Override
	public void addAll(Collection<Light> lightList) {
		if((lightList != null) && (!lightList.isEmpty())) {
			for(Light light : lightList) {
				this.lights.put(light.getName(), light);
			}
		}	
	}

	@Override
	public void add(Light light) {
		if(light != null) {
			this.lights.put(light.getName(), light); 		
		}
	}

	@Override
	public Light getByName(String name) {
		Light light = null;
		if(this.lights.containsKey(name)) {
			light = this.lights.get(name);
		}
		return light;
	}

	@Override
	public Collection<Light> getAll() {
		return this.lights.values();
	}

	@Override
	public void clearAll() {
		this.lights.clear();
	}

}
