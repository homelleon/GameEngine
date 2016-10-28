package engine.maps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.entities.Entity;
import engine.scene.Settings;
import engine.terrains.Terrain;

public class MapFileWriter implements MapWriteable {
	
	@Override
	public void write(GameMap map) {
		try {
			File mapFile = new File(Settings.MAP_PATH + map.getName() + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile));
			
			List<String> lines = new ArrayList<String>();
			
			lines.add("#This map is createrd by MapFileWriter");
			
			if (!map.getEntities().isEmpty()){
				for(Entity entity : map.getEntities()){
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
			
			if (!map.getTerrains().isEmpty()){
				for(Terrain terrain: map.getTerrains()){
					String line = "<t> ";
					line += String.valueOf(terrain.getName());
				}
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
