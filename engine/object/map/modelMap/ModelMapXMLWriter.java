package object.map.modelMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.settings.EngineSettings;
import object.entity.entity.EntityInterface;
import object.terrain.terrain.TerrainInterface;
import renderer.loader.Loader;
import tool.xml.XMLUtils;

public class ModelMapXMLWriter implements ModelMapWriterInterface {
	
	@Override
	public void write(ModelMap map, Loader loader) {
		try {
			File mapFile = new File(EngineSettings.MAP_PATH + map.getName() + ".xml");
			BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile));
			
			System.out.println("Start saving map '" + map.getName() + "'...");
			List<String> lines = new ArrayList<String>();
			
			lines.add("<!-- This map is createrd by MapXMLWriter -->");
			
			lines.add(XMLUtils.getBeginTag(XMLUtils.MAP));
			System.out.println("Saving terrains...");
			if (!map.getTerrains().isEmpty()){
				lines.add(XMLUtils.getBeginTag(XMLUtils.TERRAINS, 1));
				for(TerrainInterface terrain: map.getTerrains().values()) {
					lines.add(XMLUtils.getBeginTag(XMLUtils.TERRAIN, 2));
					String name = String.valueOf(terrain.getName());
					
					String x = String.valueOf((int) (terrain.getX()/terrain.getSize()));
					String y = String.valueOf((int) (terrain.getZ()/terrain.getSize()));
					String baseTexture = String.valueOf(terrain.getTexturePack().getBackgroundTexture().getName());
					String redTexture = String.valueOf(terrain.getTexturePack().getrTexture().getName());
					String greenTexture = String.valueOf(terrain.getTexturePack().getgTexture().getName());
					String blueTexture = String.valueOf(terrain.getTexturePack().getbTexture().getName());
					String blendTexture = String.valueOf(terrain.getBlendMap().getName());
					String procGenerated = "false";
					
					lines.add(XMLUtils.addTagValue(XMLUtils.NAME, name, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.X, x, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.Y, y, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.BASE_TEXTURE, baseTexture, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.RED_TEXTURE, redTexture, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.GREEN_TEXTURE, greenTexture, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.BLUE_TEXTURE, blueTexture, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.BLEND_TEXTURE, blendTexture, 3));
					if(terrain.isProcedureGenerated()) {
						procGenerated = "true";
						String amplitude = String.valueOf(terrain.getAmplitude());
						String octave = String.valueOf(terrain.getOctaves());
						String rpughness = String.valueOf(terrain.getRoughness());
						
						lines.add(XMLUtils.addTagValue(XMLUtils.PROCEDURE_GENERATED, procGenerated, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.AMPLITUDE, amplitude, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.OCTAVE, octave, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.ROUGHTNESS, rpughness, 3));
					} else {
						
						String heightTexture = String.valueOf(terrain.getHeightMapName());
						
						lines.add(XMLUtils.addTagValue(XMLUtils.PROCEDURE_GENERATED, procGenerated, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.HEIGHT_TEXTURE, heightTexture, 3));
					}
					lines.add(XMLUtils.getEndTag(XMLUtils.TERRAIN, 2));
				}
				lines.add(XMLUtils.getEndTag(XMLUtils.TERRAINS, 1));
			}
			
			System.out.println("Succed!");
			
			System.out.println("Saving entities...");
			if (!map.getEntities().isEmpty()) {
				lines.add(XMLUtils.getBeginTag(XMLUtils.ENTITIES, 1));
				for(EntityInterface entity : map.getEntities().values()) {
					lines.add(XMLUtils.getBeginTag(XMLUtils.ENTITY, 2));
					String name = String.valueOf(entity.getName());
					String model = String.valueOf(entity.getModel().getName());					
					//TODO: find out why it returns null texture
					String texture = loader.getTextureByID(entity.getModel().getTexture().getID());
					String x = String.valueOf(entity.getPosition().x);
					String y = String.valueOf(entity.getPosition().y);
					String z = String.valueOf(entity.getPosition().z);
					String scale = String.valueOf(entity.getScale());
					String normal = String.valueOf(false);
					
					lines.add(XMLUtils.addTagValue(XMLUtils.NAME, name, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.MODEL, model, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.TEXTURE, texture, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.X, x, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.Y, y, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.Z, z, 3));
					lines.add(XMLUtils.addTagValue(XMLUtils.SCALE, scale, 3));
					
					if(entity.getType() == EngineSettings.ENTITY_TYPE_SIMPLE) {						
						lines.add(XMLUtils.addTagValue(XMLUtils.NORMAL, normal, 3));
					} else {						
						normal = String.valueOf(true);						
						String normalTexture = loader.getTextureByID(entity.getModel().getTexture().getNormalMap());
						String specularTexture = loader.getTextureByID(entity.getModel().getTexture().getSpecularMap());
						String shineDumper = String.valueOf(entity.getModel().getTexture().getShineDamper());
						String reflectivity = String.valueOf(entity.getModel().getTexture().getReflectivity());
						
						lines.add(XMLUtils.addTagValue(XMLUtils.NORMAL, normal, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.NORMAL_TEXTURE, normalTexture, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.SPECULAR_TEXTURE, specularTexture, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.SHINE_DUMPER, shineDumper, 3));
						lines.add(XMLUtils.addTagValue(XMLUtils.REFLECTIVITY, reflectivity, 3));
					}
					lines.add(XMLUtils.getEndTag(XMLUtils.ENTITY, 2));
				}
				lines.add(XMLUtils.getEndTag(XMLUtils.ENTITIES, 1));
			}			
			System.out.println("Succed!");
			
			lines.add(XMLUtils.getEndTag(XMLUtils.MAP));
			
			for(String line : lines) {
				writer.write(line);
				writer.flush();
				writer.newLine();
			}
			
			writer.flush();
			writer.close();			
				
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't create map file!");
		} 
		System.out.println("Save complete!");
	}

}
