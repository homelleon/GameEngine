package maps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.EntityInterface;
import renderEngine.Loader;
import scene.ES;
import terrains.TerrainInterface;

public class MapsTXTWriter implements MapsWriter {
	
	@Override
	public void write(GameMap map, Loader loader) {
		try {
			File mapFile = new File(ES.MAP_PATH + map.getName() + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile));
			
			System.out.println("Start saving map '" + map.getName() + "'...");
			List<String> lines = new ArrayList<String>();
			
			lines.add("#This map is createrd by MapFileWriter");
			
			System.out.println("Saving terrains...");
			if (!map.getTerrains().isEmpty()){
				for(TerrainInterface terrain: map.getTerrains().values()) {
					String line = "<t> ";
					line += String.valueOf(terrain.getName());
					line += " ";
					line += String.valueOf((int) (terrain.getX()/terrain.getSize()));
					line += " ";
					line += String.valueOf((int) (terrain.getZ()/terrain.getSize()));
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
			
			System.out.println("Succed!");
			
			System.out.println("Saving entities...");
			if (!map.getEntities().isEmpty()) {
				for(EntityInterface entity : map.getEntities().values()) {
					String line = "<e> ";
					line += String.valueOf(entity.getName());
					line += " ";
					line += String.valueOf(entity.getModel().getName());
					line += " ";
					//TODO: find out why it returns null texture
					String texture = loader.getTextureByID(entity.getModel().getTexture().getID());
					System.out.println(texture);
					line += String.valueOf(texture);
					line += " ";
					line += String.valueOf(entity.getPosition().x);
					line += " ";
					line += String.valueOf(entity.getPosition().y);
					line += " ";
					line += String.valueOf(entity.getPosition().z);
					line += " ";
					line += String.valueOf(entity.getScale());
					line +=" ";
					if(entity.getType() == ES.ENTITY_TYPE_SIMPLE) {
						line += String.valueOf(false);
					} else {						
						line += String.valueOf(true);
						line +=" ";
						String normal = loader.getTextureByID(entity.getModel().getTexture().getNormalMap());
						line += normal;
						line +=" ";
						String specular = loader.getTextureByID(entity.getModel().getTexture().getSpecularMap());
						line += specular;
						line +=" ";
						line += entity.getModel().getTexture().getShineDamper();
						line +=" ";
						line += entity.getModel().getTexture().getReflectivity();
					}
					lines.add(line);
				}
			}
			System.out.println("Succed!");			
			
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
		System.out.println("Save complete!");
	}

}
