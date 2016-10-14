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
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import water.WaterTile;

public class ObjectGenerator {
	//TODO: ALL individual object creator
	
	public List<Light> lights;

	private Loader loader = new Loader();
	
	public TexturedModel loadStaticModel(String objFile, String textureName){
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		RawModel rawModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());	
	    TexturedModel staticModel = new TexturedModel(rawModel,new ModelTexture(loader.loadTexture(Settings.MODEL_TEXTURE_PATH,textureName)));
	    return staticModel;
	}
	
	public List<Entity> createGrassField(float x, float z, float r, float sizeNoise, float density){
		//TODO: Noise - better using
		
		TexturedModel grass = loadStaticModel("grassObject","grassObjAtlas");
		int texIndex = 4;
		grass.getTexture().setNumberOfRows(2);
		grass.getTexture().setShineDamper(1);
		grass.getTexture().setReflectivity(0);
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);	
		if (density == 0){
		 density = (float) 0.01;	
		}
		r = r*density;
		density = 1/density;
		Random random = new Random();
		List<Entity>grasses = new ArrayList<Entity>();
		for(Integer j = 0; j < r; j++){
			for(Integer i = 0; i < r; i++){
				sizeNoise = 1 + 2*(float) random.nextDouble();
				Entity grassEntity = new Entity(grass, texIndex, new Vector3f(x + density*i, 0, z + density*j), 0, 0, 0, sizeNoise);
				grasses.add(grassEntity);
				Entity grassEntity1 = new Entity(grass, texIndex, new Vector3f((float) (x + density*i), 0, (float) (z + density*j)), 0, 100, 0, sizeNoise);
				grasses.add(grassEntity1);
	
			}
		}
		
		return grasses;
		
	}
	
	public List<WaterTile> createWaterSurfce(float x, float y, float z, int size){
		List<WaterTile> waters = new ArrayList<WaterTile>();
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				WaterTile water = new WaterTile(x + WaterTile.TILE_SIZE * i, y + WaterTile.TILE_SIZE * j, z, size);
				waters.add(water);
			}
		}
		return waters;
	}
	
	public Terrain createMultiTexTerrain(String basicTexture, String redTexture, String greenTexture, String blueTexture, String blendTexture, String heightTexture){
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, basicTexture));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, redTexture));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, greenTexture));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, blueTexture));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture(Settings.BLEND_MAP_PATH,blendTexture));
		Terrain terrain = new Terrain(0,0,loader,texturePack, blendMap, heightTexture);	
		return terrain;
	}
	
	public Terrain createMultiTexTerrain(String basicTexture, String redTexture, String greenTexture, String blueTexture, String blendTexture, float amplitude, int octaves, float roughness){
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, basicTexture));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, redTexture));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, greenTexture));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, blueTexture));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture(Settings.BLEND_MAP_PATH,blendTexture));
		Terrain terrain = new Terrain(0,0,loader,texturePack, blendMap, amplitude, octaves, roughness);
		return terrain;
	}
	
	public void createForest(List<Entity> forest, float x, float y, float r, float noise){
		
	}
	
	public void createSun(Light light){
		
	}
	

}
