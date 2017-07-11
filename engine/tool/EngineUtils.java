package tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Node;

import core.settings.EngineSettings;
import object.entity.entity.Entity;
import object.entity.entity.EntityInterface;
import object.model.RawModel;
import object.model.TexturedModel;
import object.terrain.terrain.Terrain;
import object.texture.model.ModelTexture;
import object.texture.terrain.TerrainTexture;
import object.texture.terrain.TerrainTexturePack;
import object.water.WaterTile;
import renderer.loader.Loader;
import tool.converter.normalMapObject.NormalMappedObjLoader;
import tool.converter.object.ModelData;
import tool.converter.object.OBJFileLoader;

public class EngineUtils {
	
	public static TexturedModel loadStaticModel(String objFile, 
			String texName, Loader loader) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		RawModel rawModel = loader.loadToVAO(data.getVertices(), 
				data.getTextureCoords(), data.getNormals(), 
				data.getIndices());
	    TexturedModel staticModel = new TexturedModel(objFile, rawModel,
	    		new ModelTexture(texName, loader.loadTexture(EngineSettings.TEXTURE_MODEL_PATH, texName)));
	    return staticModel;
	}
	
	public static TexturedModel loadNormalModel(String objFile, String texName, String normalTexture, String specularTexture, Loader loader) {
		RawModel rawModel = NormalMappedObjLoader.loadOBJ(objFile, loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture(EngineSettings.TEXTURE_MODEL_PATH,"barrel"));
		TexturedModel model = new TexturedModel(objFile, rawModel, texture);
		int normaMap = loader.loadTexture(EngineSettings.TEXTURE_NORMAL_MAP_PATH, normalTexture);
		model.getTexture().setNormalMap(normaMap);
		model.getTexture().setShineDamper(10);
		model.getTexture().setReflectivity(0.5f);
		System.out.println(specularTexture);
		//TODO: настроить проверку на нуль
		if(specularTexture != null) {
			int specularMap = loader.loadTexture(EngineSettings.TEXTURE_SPECULAR_MAP_PATH, specularTexture);
			model.getTexture().setSpecularMap(specularMap);
		}
		return model;		
	}
	
	
	public static List<EntityInterface> createGrassField(float x, float z, float r, float sizeNoise, 
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
		List<EntityInterface>grasses = new ArrayList<EntityInterface>();
		for(Integer j = 0; j < r; j++) {
			for(Integer i = 0; i < r; i++) {
				sizeNoise = 1 + 2*(float) random.nextDouble();
				EntityInterface grassEntity = new Entity("Grass" + 
				String.valueOf(i) + "/" + String.valueOf(j), 
				EngineSettings.ENTITY_TYPE_DETAIL, grass, texIndex, 
						new Vector3f(x + density*i, 0, z + density*j), 0, 0, 0, sizeNoise);
				grasses.add(grassEntity);
				EntityInterface grassEntity1 = new Entity("Grass" + 
				String.valueOf(i) + "/" + String.valueOf(j), 
				EngineSettings.ENTITY_TYPE_DETAIL, grass, texIndex, 
						new Vector3f((float) (x + density*i), 0, 
								(float) (z + density*j)), 0, 100, 0, sizeNoise);
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
	
	public static Terrain createMultiTexTerrain(String name, int x, int y, String basicTexture, 
			String redTexture, String greenTexture, String blueTexture, 
			String blendTexture, String heightTexture, Loader loader) {
		TerrainTexture backgroundTexture =
				new TerrainTexture(basicTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, basicTexture));
		TerrainTexture rTexture = 
				new TerrainTexture(redTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, redTexture));
		TerrainTexture gTexture = 
				new TerrainTexture(greenTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, greenTexture));
		TerrainTexture bTexture = 
				new TerrainTexture(blueTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, blueTexture));
		
		TerrainTexturePack texturePack = 
				new TerrainTexturePack(backgroundTexture + "Pack", backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = 
				new TerrainTexture(blendTexture, loader.loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH, blendTexture));
		Terrain terrain = new Terrain(name, x,y,loader,texturePack, blendMap, heightTexture);	
		return terrain;
	}
	
	public static Terrain createMultiTexTerrain(String name, int x, int y, String basicTexture, 
			String redTexture, String greenTexture,	String blueTexture,
			String blendTexture, float amplitude, int octaves, float roughness,
			Loader loader) {
		TerrainTexture backgroundTexture = 
				new TerrainTexture(basicTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, basicTexture));
		TerrainTexture rTexture = 
				new TerrainTexture(redTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, redTexture));
		TerrainTexture gTexture = 
				new TerrainTexture(greenTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, greenTexture));
		TerrainTexture bTexture = 
				new TerrainTexture(blueTexture, loader.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, blueTexture));
		
		TerrainTexturePack texturePack = 
				new TerrainTexturePack(basicTexture + "Pack", backgroundTexture, rTexture,	gTexture, bTexture);
		TerrainTexture blendMap = 
				new TerrainTexture(blendTexture, loader.loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH,blendTexture));
		Terrain terrain = 
				new Terrain(name, x,y,loader,texturePack, blendMap, amplitude, octaves, roughness);
		return terrain;
	}
	
	public static void createForest(List<Entity> forest, float x, float y, float r, float noise){
		
	}	

}
