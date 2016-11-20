package maps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import scene.ES;
import terrains.Terrain;

public class MapsTXTWriter implements MapsWriter {
	
	@Override
	public void write(GameMap map) {
		try {
			File mapFile = new File(ES.MAP_PATH + map.getName() + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile));
			
			List<String> lines = new ArrayList<String>();
			
			lines.add("#This map is createrd by MapFileWriter");
			
			if (!map.getEntities().isEmpty()) {
				for(Entity entity : map.getEntities().values()) {
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
				for(Terrain terrain: map.getTerrains().values()) {
					String line = "<t> ";
					line += String.valueOf(terrain.getName());
					line += " ";
					line += String.valueOf((int) terrain.getX());
					line += " ";
					line += String.valueOf((int) terrain.getZ());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getBackgroundTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getrTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getgTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getbTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getBlendMap().getName());
					line += " ";
					if(terrain.isProcedureGenerated()) {
						line += "true";
						line += " ";
						line += String.valueOf(terrain.getAmplitude());
						line += " ";
						line += String.valueOf(terrain.getOctaves());
						line += " ";
						line += String.valueOf(terrain.getRoughness());
					} else {
						line += "false";
						line += " ";
						line += String.valueOf(terrain.getHeightMapName());
					}
					lines.add(line);
				}
			}
			
			
			for(String line : lines) {
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
