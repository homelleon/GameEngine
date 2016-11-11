package maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioMaster;
import audio.Source;
import entities.Entity;
import models.TexturedModel;
import renderEngine.Loader;
import scene.SceneObjectTools;
import scene.EngineSettings;
import terrains.Terrain;

public class MapFileLoader implements MapLoadable {
	
	public GameMap loadMap(String fileName, Loader loader) {
		FileReader isr = null;
        File mapFile = new File(EngineSettings.MAP_PATH + fileName + ".txt");
	        
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
