package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import renderEngine.Loader;
import scene.EngineSettings;

public class GuiManager {
	
	public static List<GuiTexture> createGui(Loader loader) {
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		GuiTexture gui = new GuiTexture("Helth", loader.loadTexture(EngineSettings.INTERFACE_TEXTURE_PATH,"helthBar"), new Vector2f(-0.7f, -0.7f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		
		return guis;
	}

}
