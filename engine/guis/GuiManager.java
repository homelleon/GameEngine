package guis;

import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.util.vector.Vector2f;

import renderEngine.Loader;
import scene.ES;

public class GuiManager {
	
	public static Map<String, GuiTexture> createGui(Loader loader) {
		Map<String, GuiTexture> guis = new WeakHashMap<String, GuiTexture>();
		
		GuiTexture gui = new GuiTexture("Helth", loader.loadTexture(ES.INTERFACE_TEXTURE_PATH,"helthBar"), new Vector2f(-0.7f, -0.7f), new Vector2f(0.25f, 0.25f));
		guis.put(gui.getName(), gui);
		
		return guis;
	}

}
