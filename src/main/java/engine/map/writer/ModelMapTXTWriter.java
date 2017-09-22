package map.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.settings.EngineSettings;
import manager.scene.IObjectManager;
import object.entity.entity.IEntity;
import object.terrain.terrain.ITerrain;
import primitive.buffer.Loader;

public class ModelMapTXTWriter implements ILevelMapWriter {

	@Override
	public void write(IObjectManager map) {
		try {
			File mapFile = new File(EngineSettings.MAP_PATH + "newModelMap" + ".xml");
			BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile));

			System.out.println("Start saving map...");
			List<String> lines = new ArrayList<String>();

			lines.add("#This map is createrd by MapFileWriter");

			System.out.println("Saving terrains...");
			if (!map.getTerrains().getAll().isEmpty()) {
				for (ITerrain terrain : map.getTerrains().getAll()) {
					String line = "<t> ";
					line += String.valueOf(terrain.getName());
					line += " ";
					line += String.valueOf((int) (terrain.getX() / terrain.getSize()));
					line += " ";
					line += String.valueOf((int) (terrain.getZ() / terrain.getSize()));
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getBackgroundTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getRTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getGTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getBTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getBlendMap().getName());
					line += " ";
					if (terrain.getIsProcedureGenerated()) {
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
			if (!map.getEntities().getAll().isEmpty()) {
				for (IEntity entity : map.getEntities().getAll()) {
					String line = "<e> ";
					line += String.valueOf(entity.getName());
					line += " ";
					line += String.valueOf(entity.getModel().getName());
					line += " ";
					// TODO: find out why it returns null texture
					String texture = entity.getModel().getMaterial().getDiffuseMap().getName();
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
					line += " ";
					if (entity.getType() == EngineSettings.ENTITY_TYPE_SIMPLE) {
						line += String.valueOf(false);
					} else {
						Loader loader = Loader.getInstance();
						line += String.valueOf(true);
						line += " ";						
						String normal = entity.getModel().getMaterial().getNormalMap().getName();
						line += normal;
						line += " ";
						String specular = entity.getModel().getMaterial().getSpecularMap().getName();
						line += specular;
						line += " ";
						line += entity.getModel().getMaterial().getShininess();
						line += " ";
						line += entity.getModel().getMaterial().getReflectivity();
					}
					lines.add(line);
				}
			}
			System.out.println("Succed!");

			for (String line : lines) {
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
