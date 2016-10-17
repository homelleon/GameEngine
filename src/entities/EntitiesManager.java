package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import renderEngine.Loader;
import scene.SceneObjectTools;
import scene.Settings;
import textures.ModelTexture;

public class EntitiesManager {
	
	public static List<Entity> createEntities(Loader loader){
		List<Entity> entities = new ArrayList<Entity>();
		//******StaticModels***************//
		//Grass/
		TexturedModel grassModel = SceneObjectTools.loadStaticModel("grass", "grass", loader);		
		//Stall//
	    TexturedModel stallModel = SceneObjectTools.loadStaticModel("stall", "stallTexture", loader);
	    stallModel.getTexture().setShineDamper(5);
	    stallModel.getTexture().setReflectivity(1);
		//Cube//
		TexturedModel cubeModel = SceneObjectTools.loadStaticModel("cube", "cube1", loader);		
		cubeModel.getTexture().setShineDamper(5);
		cubeModel.getTexture().setReflectivity(1);
		cubeModel.getTexture().setHasTransparency(true);
		cubeModel.getTexture().setUseFakeLighting(true);
		
		//Entities attach
		List<Entity> grasses = SceneObjectTools.createGrassField(0, 0, 800, 1, 0.3f, loader);
		Entity stall = new Entity(stallModel, new Vector3f(50,0,50),0,0,0,1);
		Entity cube = new Entity(cubeModel, new Vector3f(100,0,10),0,0,0,1);
		
		entities.add(cube);
		entities.add(stall);
		entities.addAll(grasses);
		
		return entities;
	}
	
	public static List<Entity> createNormalMappedEntities(Loader loader){
		List<Entity> entities = new ArrayList<Entity>();
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture(Settings.MODEL_TEXTURE_PATH,"barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture(Settings.NORMAL_MAP_PATH, "barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		TexturedModel boulderModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("boulder", loader),
				new ModelTexture(loader.loadTexture(Settings.MODEL_TEXTURE_PATH,"boulder")));
		boulderModel.getTexture().setNormalMap(loader.loadTexture(Settings.NORMAL_MAP_PATH, "boulderNormal"));
		boulderModel.getTexture().setShineDamper(10);
		boulderModel.getTexture().setReflectivity(0.5f);
		Entity barrel = new Entity(barrelModel, new Vector3f(200, 0, 200), 0,0,0,1);
		Entity boulder = new Entity(boulderModel, new Vector3f(250,0,250), 0,0,0,1);
		
		entities.add(barrel);
		entities.add(boulder);
		
		return entities;
	}

}
