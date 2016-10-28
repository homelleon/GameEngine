package engine.maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engine.audio.AudioMaster;
import engine.audio.Source;
import engine.entities.Entity;
import engine.models.TexturedModel;
import engine.renderEngine.Loader;
import engine.scene.SceneObjectTools;
import engine.scene.Settings;
import engine.terrains.Terrain;

public class MapFileLoader implements MapLoadable {
	
	public GameMap loadMap(String fileName, Loader loader) {
		FileReader isr = null;
        File mapFile = new File(Settings.MAP_PATH + fileName + ".txt");
	        
        try {
            isr = new FileReader(mapFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found in res; don't use any extention");
        }
        BufferedReader reader = new BufferedReader(isr);
        MapReadable mapParser = new MapParser();
        
		return  mapParser.readMap(fileName, reader, loader);
	}

}
