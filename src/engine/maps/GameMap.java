package engine.maps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.audio.Source;
import engine.entities.Entity;
import engine.scene.Settings;
import engine.terrains.Terrain;

public class GameMap {
	
	public String name;
	public List<Entity> entities;
	public List<Entity> normalEntities;
	public List<Terrain> terrains;
	public List<Source> audios;
	
	public void saveMapFile(){
		try {
			File mapFile = new File(Settings.MAP_PATH + "map" + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile));
			
			List<String> lines = new ArrayList<String>();
			
			if (!entities.isEmpty()){
				for(Entity entity : entities){
					String line = "<e> ";
					line += String.valueOf(entity.getName());
					line += " ";
					line += String.valueOf(entity.getModel().getName());
					line += " ";
					line += String.valueOf(entity.getModel().getTexture().getName());
					line += " ";
					line += String.valueOf(entity.getPosition().x);
					line += " ";
					line += String.valueOf(entity.getPosition().y);
					line += " ";
					line += String.valueOf(entity.getPosition().z);
					line += " ";
					line += String.valueOf(entity.getScale());
					line += " ";
					line += String.valueOf(entity.isDetail());
					lines.add(line);
				}
			}
			
			if (!terrains.isEmpty()){
				for(Terrain terrain: terrains){
					String line = "<t> ";
					line += String.valueOf(terrain.ge)
				}
			}
			
			if (!audios.isEmpty()){
				//line += String.valueOf();
			}
			
			for(String line : lines){
				writer.write(line);
				writer.flush();
				writer.newLine();
			}
			
			
			writer.write("<end>");
			writer.flush();
			writer.close();			
				
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't create map file!");
		} 
		
		
		
	}

}
