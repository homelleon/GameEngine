package tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.settings.EngineSettings;
import object.entity.entity.DecorEntity;
import object.entity.entity.IEntity;
import object.entity.entity.TexturedEntity;
import object.terrain.terrain.ITerrain;
import object.terrain.terrain.MappedTerrain;
import object.terrain.terrain.ProceduredTerrain;
import object.texture.Texture2D;
import object.texture.material.Material;
import object.texture.terrain.TerrainTexturePack;
import object.water.WaterTile;
import primitive.buffer.Loader;
import primitive.model.Mesh;
import primitive.model.Model;
import tool.math.vector.Vector3f;
import tool.meshLoader.normalMapObject.NormalMappedObjLoader;
import tool.meshLoader.object.ModelData;
import tool.meshLoader.object.OBJFileLoader;
import tool.meshLoader.objloader.OBJLoader;

public class EngineUtils {

	public static Model loadStaticModel(String objFile, String textureName) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		Loader loader = Loader.getInstance();
		Mesh rawModel = loader.getVertexLoader().loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		Model staticModel = new Model(objFile, rawModel,
				new Material(textureName, loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, textureName)));
		return staticModel;
	}
	
	public static List<Model> loadStaticModels(String objFile, String textureName) {
		List<Model> models = new ArrayList<Model>();
		OBJLoader objLoader = new OBJLoader();
		Mesh[] meshes = objLoader.load(EngineSettings.MODEL_PATH, objFile, null);
		for(int i=0; i< meshes.length; i++) {
			Model staticModel = new Model(objFile, meshes[i],
					new Material(textureName, Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, textureName)));
			models.add(staticModel);
		}
		return models;
	}
	
	public static Model loadStaticModel(String objFile, Material texture) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		Loader loader = Loader.getInstance();
		Mesh rawModel = loader.getVertexLoader().loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		Model staticModel = new Model(objFile, rawModel, texture);
		return staticModel;
	}
	
	public static Model loadStaticModel(String name, Mesh rawModel, String textureName) {
		Loader loader = Loader.getInstance();
		Model staticModel = new Model(name, rawModel,
				new Material(textureName, loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, textureName)));
		return staticModel;
	}
	
	public static Model loadStaticModel(String name, Mesh rawModel, Material texture) {
		Model staticModel = new Model(name, rawModel, texture);
		return staticModel;
	}

	public static Model loadStaticModel(String objFile, String textureName, String normalTexture,
			String specularTexture) {
		Loader loader = Loader.getInstance();
		ModelData data = NormalMappedObjLoader.loadOBJ(objFile);
		Mesh rawModel = new Mesh(objFile, Loader.getInstance().getVertexLoader()
				.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices()));
		Material texture = new Material(objFile + "Material", loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, "barrel"));
		Model model = new Model(objFile, rawModel, texture);
		Texture2D normaMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_NORMAL_MAP_PATH, normalTexture);
		model.getMaterial().setNormalMap(normaMap);
		// TODO: настроить проверку на нуль
		if (specularTexture != null) {
			Texture2D specularMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_SPECULAR_MAP_PATH, specularTexture);
			model.getMaterial().setSpecularMap(specularMap);
		}
		return model;
	}
	
	public static Model loadStaticModel(String name, Mesh mesh, Texture2D texture, Texture2D normalTexture,
			Texture2D specularTexture) {
		Material material = new Material(name + "Material");
		material.setNormalMap(normalTexture);
		if(specularTexture!= null) {
			material.setSpecularMap(specularTexture);
		}
		Model model = new Model(name, mesh, material);
		
		return model;
	}

	public static List<IEntity> createGrassField(float x, float z, float r, float sizeNoise, float density) {
		// TODO: Noise - better using
		Model grass = loadStaticModel("tree", "bark");
		grass.getMaterial().getDiffuseMap().setNumberOfRows(1);
		grass.getMaterial().setShininess(1);
		grass.getMaterial().setReflectivity(0);
		grass.getMaterial().getDiffuseMap().setHasTransparency(true);
		grass.getMaterial().setUseFakeLighting(true);
		if (density == 0) {
			density = (float) 0.01;
		}
		int radius = (int) (r * density);
		float invDensity = 1 / density;
		Random random = new Random();
		
		return IntStream.range(0, radius).parallel()
				.mapToObj(j -> IntStream.range(0, radius).parallel()
						.mapToObj(i -> {
							int xSeed = random.nextInt(20);
							int ySeed = random.nextInt(180);
							int zSeed = random.nextInt(20);
							int textIndexSeed = random.nextInt(1);
							float noise = random.nextInt(10)/5;
							IEntity grassEntity = new DecorEntity("Grass" + String.valueOf(i) + "-" + String.valueOf(j), Stream.of(grass).collect(Collectors.toList()), textIndexSeed,
									new Vector3f(x + invDensity * i, 0, z + invDensity * j), new Vector3f(-90, 0, -45), noise);
							grassEntity.setBaseName("grassEntity");
							return grassEntity;
						}))
				.flatMap(entity -> entity)
				.collect(Collectors.toList());
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

	public static MappedTerrain createMultiTexTerrain(String name, int x, int y, String basicTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, String heightTexture, Loader loader) {
		Texture2D backgroundTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, basicTexture);
		Texture2D rTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, redTexture);
		Texture2D gTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, greenTexture);
		Texture2D bTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, blueTexture);

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture + "Pack", backgroundTexture, rTexture,
				gTexture, bTexture);
		Texture2D blendMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH, blendTexture);
		MappedTerrain terrain = new MappedTerrain(name, x, y, texturePack, blendMap, heightTexture);
		return terrain;
	}

	public static ITerrain createMultiTexTerrain(String name, int x, int y, String basicTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, float amplitude, int octaves, float roughness) {
		Loader loader = Loader.getInstance();
		Texture2D backgroundTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, basicTexture);
		Texture2D rTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, redTexture);
		Texture2D gTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, greenTexture);
		Texture2D bTexture = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, blueTexture);

		TerrainTexturePack texturePack = new TerrainTexturePack(basicTexture + "Pack", backgroundTexture, rTexture,
				gTexture, bTexture);
		Texture2D blendMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH, blendTexture);
		ITerrain terrain = new ProceduredTerrain(name, x, y, texturePack, blendMap, amplitude, octaves, roughness);
		return terrain;
	}

	public static void createForest(List<TexturedEntity> forest, Vector3f position, float noise) {

	}

}
