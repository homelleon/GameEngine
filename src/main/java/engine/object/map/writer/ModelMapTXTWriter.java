package object.map.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.settings.EngineSettings;
import object.entity.entity.IEntity;
import object.scene.manager.IObjectManager;
import object.terrain.terrain.ITerrain;
import renderer.loader.Loader;

public class ModelMapTXTWriter implements IModelMapWriter {

	@Override
	public void write(IObjectManager map, Loader loader) {
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
					line += String.valueOf(terrain.getTexturePack().getrTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getgTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getTexturePack().getbTexture().getName());
					line += " ";
					line += String.valueOf(terrain.getBlendMap().getName());
					line += " ";
					if (terrain.isProcedureGenerated()) {
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
					String texture = loader.getTextureLoader().getTextureByID(entity.getModel().getTexture().getID());
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
						line += String.valueOf(true);
						line += " ";
						String normal = loader.getTextureLoader().getTextureByID(entity.getModel().getTexture().getNormalMap());
						line += normal;
						line += " ";
						String specular = loader.getTextureLoader().getTextureByID(entity.getModel().getTexture().getSpecularMap());
						line += specular;
						line += " ";
						line += entity.getModel().getTexture().getShineDamper();
						line += " ";
						line += entity.getModel().getTexture().getReflectivity();
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
