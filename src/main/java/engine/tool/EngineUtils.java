package tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.entity.entity.Entity;
import object.entity.entity.TexturedEntity;
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

	public static TexturedModel loadStaticModel(String objFile, String texName) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		Loader loader = Loader.getInstance();
		RawModel rawModel = loader.getVertexLoader().loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		TexturedModel staticModel = new TexturedModel(objFile, rawModel,
				new ModelTexture(texName, loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, texName)));
		return staticModel;
	}

	public static TexturedModel loadNormalModel(String objFile, String texName, String normalTexture,
			String specularTexture) {
		Loader loader = Loader.getInstance();
		RawModel rawModel = NormalMappedObjLoader.loadOBJ(objFile);
		ModelTexture texture = new ModelTexture(loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, "barrel"));
		TexturedModel model = new TexturedModel(objFile, rawModel, texture);
		int normaMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_NORMAL_MAP_PATH, normalTexture);
		model.getTexture().setNormalMap(normaMap);
		model.getTexture().setShineDamper(10);
		model.getTexture().setReflectivity(0.5f);
		// TODO: настроить проверку на нуль
		if (specularTexture != null) {
			int specularMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_SPECULAR_MAP_PATH, specularTexture);
			model.getTexture().setSpecularMap(specularMap);
		}
		return model;
	}

	public static List<Entity> createGrassField(float x, float z, float r, float sizeNoise, float density) {
		// TODO: Noise - better using

		Loader loader = Loader.getInstance();
		TexturedModel grass = loadStaticModel("grassObject", "grassObjAtlas");
		int texIndex = 4;
		grass.getTexture().setNumberOfRows(2);
		grass.getTexture().setShineDamper(1);
		grass.getTexture().setReflectivity(0);
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		if (density == 0) {
			density = (float) 0.01;
		}
		r = r * density;
		density = 1 / density;
		Random random = new Random();
		List<Entity> grasses = new ArrayList<Entity>();
		for (Integer j = 0; j < r; j++) {
			for (Integer i = 0; i < r; i++) {
				sizeNoise = 1 + 2 * (float) random.nextDouble();
				Entity grassEntity = new TexturedEntity("Grass" + String.valueOf(i) + "/" + String.valueOf(j),
						EngineSettings.ENTITY_TYPE_DETAIL, grass, texIndex,
						new Vector3f(x + density * i, 0, z + density * j), new Vector3f(0, 0, 0), sizeNoise);
				grasses.add(grassEntity);
				Entity grassEntity1 = new TexturedEntity("Grass" + String.valueOf(i) + "/" + String.valueOf(j),
						EngineSettings.ENTITY_TYPE_DETAIL, grass, texIndex,
						new Vector3f(x + density * i, 0, z + density * j), new Vector3f(0, 100, 0), sizeNoise);
				grasses.add(grassEntity1);

			}
		}
		return grasses;
	}

	public static List<WaterTile> createWaterSurfce(Vector3f position, int size) {
		List<WaterTile> waters = new ArrayList<WaterTile>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				WaterTile water = new WaterTile(position.x + WaterTile.TILE_SIZE * i,
						position.y + WaterTile.TILE_SIZE * j, position.z, size);
				waters.add(water);
			}
		}
		return waters;
	}

	public static Terrain createMultiTexTerrain(String name, int x, int y, String basicTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, String heightTexture, Loader loader) {
		TerrainTexture backgroundTexture = new TerrainTexture(basicTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, basicTexture));
		TerrainTexture rTexture = new TerrainTexture(redTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, redTexture));
		TerrainTexture gTexture = new TerrainTexture(greenTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, greenTexture));
		TerrainTexture bTexture = new TerrainTexture(blueTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, blueTexture));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture + "Pack", backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(blendTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH, blendTexture));
		Terrain terrain = new Terrain(name, x, y, texturePack, blendMap, heightTexture);
		return terrain;
	}

	public static Terrain createMultiTexTerrain(String name, int x, int y, String basicTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, float amplitude, int octaves, float roughness) {
		Loader loader = Loader.getInstance();
		TerrainTexture backgroundTexture = new TerrainTexture(basicTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, basicTexture));
		TerrainTexture rTexture = new TerrainTexture(redTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, redTexture));
		TerrainTexture gTexture = new TerrainTexture(greenTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, greenTexture));
		TerrainTexture bTexture = new TerrainTexture(blueTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, blueTexture));

		TerrainTexturePack texturePack = new TerrainTexturePack(basicTexture + "Pack", backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(blendTexture,
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH, blendTexture));
		Terrain terrain = new Terrain(name, x, y, loader, texturePack, blendMap, amplitude, octaves, roughness);
		return terrain;
	}

	public static void createForest(List<TexturedEntity> forest, Vector3f position, float noise) {

	}

}
