package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import renderEngine.Loader;
import scene.ES;
import textures.ModelTexture;
import toolbox.ObjectUtils;

public class EntitiesManager {
	
	public static List<Entity> createEntities(Loader loader) {
		List<Entity> entities = new ArrayList<Entity>();
		//******StaticModels***************//
		//Grass/
		TexturedModel grassModel = ObjectUtils.loadStaticModel("grass", "grass", loader);		
		//Stall//
	    TexturedModel stallModel = ObjectUtils.loadStaticModel("stall", "stallTexture", loader);
	    stallModel.getTexture().setShineDamper(5);
	    stallModel.getTexture().setReflectivity(1);
		//Cube//
		TexturedModel cubeModel = ObjectUtils.loadStaticModel("cube", "cube1", loader);		
		cubeModel.getTexture().setShineDamper(5);
		cubeModel.getTexture().setReflectivity(1);
		cubeModel.getTexture().setHasTransparency(true);
		cubeModel.getTexture().setUseFakeLighting(true);
		//Cherry//
		TexturedModel cherryModel = ObjectUtils.loadStaticModel("cherry", "cherry", loader);
		cherryModel.getTexture().setShineDamper(10);
		cherryModel.getTexture().setReflectivity(0.5f);
		cherryModel.getTexture().setSpecularMap(loader.loadTexture(ES.SPECULAR_MAP_PATH, "cherryS"));
		//Tree//
		TexturedModel treeModel = ObjectUtils.loadStaticModel("tree", "bark", loader);
		treeModel.getTexture().setShineDamper(10);
		treeModel.getTexture().setReflectivity(0.5f);
			
		//Entities attach
		List<Entity> grasses = ObjectUtils.createGrassField(500, 500, 50, 1, 
			0.1f, loader);
		Entity stall = new EntityTextured("stall", stallModel, new Vector3f(50, 0, 50), 0, 0, 0, 4);
		Entity cube = new EntityTextured("cube", cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		Entity cherry = new EntityTextured("cherry", cherryModel, new Vector3f(150, 0, 150), 0, 0, 0, 4);
		Entity tree = new EntityTextured("tree", treeModel, new Vector3f(10, 0, 10), 0, 30, 0, 4);
		
		//entities.add(cube);
		//entities.add(stall);
		//entities.add(cherry);
		//entities.add(tree);
		entities.addAll(grasses);
		
		return entities;
	}
	
	public static List<Entity> createNormalMappedEntities(Loader loader) {
		List<Entity> entities = new ArrayList<Entity>();
		TexturedModel barrelModel = new TexturedModel("barrel", NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture(ES.MODEL_TEXTURE_PATH,"barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture(ES.NORMAL_MAP_PATH, "barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		barrelModel.getTexture().setSpecularMap(loader.loadTexture(ES.SPECULAR_MAP_PATH, "barrelS"));

		TexturedModel boulderModel = new TexturedModel("boulder", NormalMappedObjLoader.loadOBJ("boulder", loader),
				new ModelTexture(loader.loadTexture(ES.MODEL_TEXTURE_PATH,"boulder")));
		boulderModel.getTexture().setNormalMap(loader.loadTexture(ES.NORMAL_MAP_PATH, "boulderNormal"));
		boulderModel.getTexture().setShineDamper(10);
		boulderModel.getTexture().setReflectivity(0.5f);
		
		/*Creating entities*/
		Entity barrel = new EntityTextured("barrel", ES.ENTITY_TYPE_NORMAL, barrelModel, new Vector3f(200, 0, 200), 0,0,0,1);
		Entity boulder = new EntityTextured("boulder", ES.ENTITY_TYPE_NORMAL, boulderModel, new Vector3f(250, 0, 250), 0,0,0,1);
		
		entities.add(barrel);
		entities.add(boulder);
		
		return entities; 
	}

}
