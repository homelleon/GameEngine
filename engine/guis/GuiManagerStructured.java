package guis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import renderEngine.Loader;
import scene.ES;

/**
 * Graphic interface manager for controling and storing structured map and
 * arrays of graphic interfaces.
 * 
 * @author homelleon
 * @version 1.0
 */

public class GuiManagerStructured implements GuiManager {
	
	Map<String, GuiTexture> guis = new HashMap<String, GuiTexture>();
	
	public static List<GuiTexture> createGui(Loader loader) {
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		GuiTexture gui = new GuiTexture("Helth", loader.loadTexture(ES.INTERFACE_TEXTURE_PATH,"helthBar"), new Vector2f(-0.7f, -0.7f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		
		return guis;
	}

	@Override
	public void addAll(Collection<GuiTexture> guiList) {
		if((guiList != null) && (!guiList.isEmpty())) {
			for(GuiTexture gui : guiList) {
				this.guis.put(gui.getName(), gui);
			}
		}	
	}

	@Override
	public void add(GuiTexture gui) {
		if(gui != null) {
			this.guis.put(gui.getName(), gui); 		
		}
	}

	@Override
	public GuiTexture getByName(String name) {
		GuiTexture gui = null;
		if(this.guis.containsKey(name)) {
			gui = this.guis.get(name);
		}
		return gui;
	}

	@Override
	public Collection<GuiTexture> getAll() {
		return this.guis.values();
	}

	@Override
	public void clearAll() {
		this.guis.clear();
	}

}
