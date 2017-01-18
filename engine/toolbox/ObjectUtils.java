package toolbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.EntityManager;
import entities.EntityTextured;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import scene.ES;
import terrains.TerrainTextured;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import water.WaterTile;

public class ObjectUtils {
	
	public static TexturedModel loadStaticModel(String objFile, 
			String texName, Loader loader) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		RawModel rawModel = loader.loadToVAO(data.getVertices(), 
				data.getTextureCoords(), data.getNormals(), 
				data.getIndices());
	    TexturedModel staticModel = new TexturedModel(objFile, rawModel,
	    		new ModelTexture(texName, loader.loadTexture(ES.MODEL_TEXTURE_PATH, texName)));
	    return staticModel;
	}
	
	public static TexturedModel loadNormalModel(String objFile, String texName, String normalTexture, String specularTexture, Loader loader) {
		RawModel rawModel = NormalMappedObjLoader.loadOBJ(objFile, loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture(ES.MODEL_TEXTURE_PATH,"barrel"));
		TexturedModel model = new TexturedModel(objFile, rawModel, texture);
		int normaMap = loader.loadTexture(ES.NORMAL_MAP_PATH, normalTexture);
		int specularMap = loader.loadTexture(ES.SPECULAR_MAP_PATH, specularTexture);
		model.getTexture().setNormalMap(normaMap);
		model.getTexture().setShineDamper(10);
		model.getTexture().setReflectivity(0.5f);
		model.getTexture().setSpecularMap(specularMap);
		return model;		
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
				Entity grassEntity = new EntityTextured("Grass" + 
				String.valueOf(i) + "/" + String.valueOf(j), 
				EntityManager.ENTITY_TYPE_DETAIL, grass, texIndex, 
						new Vector3f(x + density*i, 0, z + density*j), 0, 0, 0, sizeNoise);
				grasses.add(grassEntity);
				Entity grassEntity1 = new EntityTextured("Grass" + 
				String.valueOf(i) + "/" + String.valueOf(j), 
				EntityManager.ENTITY_TYPE_DETAIL, grass, texIndex, 
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
	
	public static TerrainTextured createMultiTexTerrain(String name, int x, int y, String basicTexture, 
			String redTexture, String greenTexture, String blueTexture, 
			String blendTexture, String heightTexture, Loader loader) {
		TerrainTexture backgroundTexture =
				new TerrainTexture(basicTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, basicTexture));
		TerrainTexture rTexture = 
				new TerrainTexture(redTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, redTexture));
		TerrainTexture gTexture = 
				new TerrainTexture(greenTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, greenTexture));
		TerrainTexture bTexture = 
				new TerrainTexture(blueTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, blueTexture));
		
		TerrainTexturePack texturePack = 
				new TerrainTexturePack(backgroundTexture + "Pack", backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = 
				new TerrainTexture(blendTexture, loader.loadTexture(ES.BLEND_MAP_PATH, blendTexture));
		TerrainTextured terrain = new TerrainTextured(name, x,y,loader,texturePack, blendMap, heightTexture);	
		return terrain;
	}
	
	public static TerrainTextured createMultiTexTerrain(String name, int x, int y, String basicTexture, 
			String redTexture, String greenTexture,	String blueTexture,
			String blendTexture, float amplitude, int octaves, float roughness,
			Loader loader) {
		TerrainTexture backgroundTexture = 
				new TerrainTexture(basicTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, basicTexture));
		TerrainTexture rTexture = 
				new TerrainTexture(redTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, redTexture));
		TerrainTexture gTexture = 
				new TerrainTexture(greenTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, greenTexture));
		TerrainTexture bTexture = 
				new TerrainTexture(blueTexture, loader.loadTexture(ES.TERRAIN_TEXTURE_PATH, blueTexture));
		
		TerrainTexturePack texturePack = 
				new TerrainTexturePack(basicTexture + "Pack", backgroundTexture, rTexture,	gTexture, bTexture);
		TerrainTexture blendMap = 
				new TerrainTexture(blendTexture, loader.loadTexture(ES.BLEND_MAP_PATH,blendTexture));
		TerrainTextured terrain = 
				new TerrainTextured(name, x,y,loader,texturePack, blendMap, amplitude, octaves, roughness);
		return terrain;
	}
	
	public static void createForest(List<EntityTextured> forest, float x, float y, float r, float noise){
		
	}
	

}
