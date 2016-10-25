package engine.scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import engine.entities.Entity;
import engine.models.RawModel;
import engine.models.TexturedModel;
import engine.objConverter.ModelData;
import engine.objConverter.OBJFileLoader;
import engine.renderEngine.Loader;
import engine.terrains.Terrain;
import engine.textures.ModelTexture;
import engine.textures.TerrainTexture;
import engine.textures.TerrainTexturePack;
import engine.water.WaterTile;

public class SceneObjectTools {
	
	public static TexturedModel loadStaticModel(String objFile, 
			String textureName, Loader loader) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		RawModel rawModel = loader.loadToVAO(data.getVertices(), 
				data.getTextureCoords(), data.getNormals(), 
				data.getIndices());	
	    TexturedModel staticModel = new TexturedModel(rawModel,
	    		new ModelTexture(loader.loadTexture(Settings.MODEL_TEXTURE_PATH, textureName)));
	    return staticModel;
	}
	
	
	public static List<Entity> createGrassField(float x, float z, float r, float sizeNoise, 
			float density, Loader loader) {
		//TODO: Noise - better using
		
		TexturedModel grass = loadStaticModel("grassObject","grassObjAtlas", loader);
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
		for(Integer j = 0; j < r; j++) {
			for(Integer i = 0; i < r; i++) {
				sizeNoise = 1 + 2*(float) random.nextDouble();
				Entity grassEntity = new Entity(grass, texIndex, 
						new Vector3f(x + density*i, 0, z + density*j), 0, 0, 0, sizeNoise);
				grassEntity.setIsDetail(true);
				grasses.add(grassEntity);
				Entity grassEntity1 = new Entity(grass, texIndex, 
						new Vector3f((float) (x + density*i), 0, 
								(float) (z + density*j)), 0, 100, 0, sizeNoise);
				grassEntity1.setIsDetail(true);
				grasses.add(grassEntity1);
	
			}
		}	
		return grasses;	
	}
	
	public static List<WaterTile> createWaterSurfce(float x, float y, float z, int size) {
		List<WaterTile> waters = new ArrayList<WaterTile>();
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				WaterTile water = new WaterTile(x + WaterTile.TILE_SIZE * i, 
						y + WaterTile.TILE_SIZE * j, z, size);
				waters.add(water);
			}
		}
		return waters;
	}
	
	public static Terrain createMultiTexTerrain(int x, int y, String basicTexture, 
			String redTexture, String greenTexture, String blueTexture, 
			String blendTexture, String heightTexture, Loader loader) {
		TerrainTexture backgroundTexture =
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, basicTexture));
		TerrainTexture rTexture = 
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, redTexture));
		TerrainTexture gTexture = 
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, greenTexture));
		TerrainTexture bTexture = 
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, blueTexture));
		
		TerrainTexturePack texturePack = 
				new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = 
				new TerrainTexture(loader.loadTexture(Settings.BLEND_MAP_PATH,blendTexture));
		Terrain terrain = new Terrain(x,y,loader,texturePack, blendMap, heightTexture);	
		return terrain;
	}
	
	public static Terrain createMultiTexTerrain(int x, int y, String basicTexture, 
			String redTexture, String greenTexture,	String blueTexture,
			String blendTexture, float amplitude, int octaves, float roughness,
			Loader loader) {
		TerrainTexture backgroundTexture = 
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, basicTexture));
		TerrainTexture rTexture = 
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, redTexture));
		TerrainTexture gTexture = 
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, greenTexture));
		TerrainTexture bTexture = 
				new TerrainTexture(loader.loadTexture(Settings.TERRAIN_TEXTURE_PATH, blueTexture));
		
		TerrainTexturePack texturePack = 
				new TerrainTexturePack(backgroundTexture, rTexture,	gTexture, bTexture);
		TerrainTexture blendMap = 
				new TerrainTexture(loader.loadTexture(Settings.BLEND_MAP_PATH,blendTexture));
		Terrain terrain = 
				new Terrain(x,y,loader,texturePack, blendMap, amplitude, octaves, roughness);
		return terrain;
	}
	
	public static void createForest(List<Entity> forest, float x, float y, float r, float noise){
		
	}
	

}
