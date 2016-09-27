package scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class SceneObjects {
	//TODO: ALL individual object creator
	
	public List<Light> lights;
	Random random = new Random();
	private Loader loader = new Loader();
	
	public List<Entity> CreateGrassField(float x, float z, float r, float noise){
		//TODO: Noise - better using
		 ModelData dataGrass = OBJFileLoader.loadOBJ("grassObject");
			RawModel grass = loader.loadToVAO(dataGrass.getVertices(), dataGrass.getTextureCoords(), dataGrass.getNormals(), dataGrass.getIndices());
			ModelTexture grassTextureAtlas = new ModelTexture(loader.loadTexture("model","grassObjAtlas"));
			grassTextureAtlas.setNumberOfRows(2);
			TexturedModel staticGrass = new TexturedModel(grass, grassTextureAtlas);
			staticGrass.getTexture().setShineDamper(1);
			staticGrass.getTexture().setReflectivity(0);
			staticGrass.getTexture().setHasTransparency(true);
			staticGrass.getTexture().setUseFakeLighting(true);
			
		List<Entity>grasses = new ArrayList<Entity>();
		for(Integer j = 0; j < r; j++){
			for(Integer i = 0; i < r; i++){
				noise = random.nextFloat();
				Entity grassEntity = new Entity(staticGrass, 1, new Vector3f(x + 2*i, 0, z + 2*j), 0, 0, 0, noise);
				grasses.add(grassEntity);
				Entity grassEntity1 = new Entity(staticGrass, 1, new Vector3f((float) (x + 2*i), 0, (float) (z + 2*j)), 0, 100, 0, noise);
				grasses.add(grassEntity1);
	
			}
			}
		
		return grasses;
		
	}
	
	public void CreateForest(List<Entity> forest, float x, float y, float r, float noise){
		
	}
	
	public void CreateSun(Light light){
		
	}
	

}
