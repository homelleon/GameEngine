package tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.settings.EngineSettings;
import object.entity.Entity;
import object.water.Water;
import primitive.buffer.Loader;
import primitive.model.Mesh;
import primitive.model.Model;
import primitive.model.meshLoader.normalMapObject.NormalMappedObjLoader;
import primitive.model.meshLoader.object.ModelData;
import primitive.model.meshLoader.object.OBJFileLoader;
import primitive.model.meshLoader.objloader.OBJLoader;
import primitive.texture.Texture2D;
import primitive.texture.material.Material;
import shader.Shader;
import shader.ShaderPool;
import tool.math.vector.Vector3f;

public class EngineUtils {

	public static Model loadModel(String objFile, String textureName) {
		return loadModels(objFile, textureName).get(0);
	}
	
	public static List<Model> loadModels(String objFile, String textureName) {
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
	
	public static List<Model> loadModels(String objFile, String textureName, boolean normal) {
		List<Model> models = new ArrayList<Model>();
		OBJLoader objLoader = normal ? new OBJLoader(true) : new OBJLoader();
		Mesh[] meshes = objLoader.load(EngineSettings.MODEL_PATH, objFile, null);
		for(int i = 0; i < meshes.length; i++) {
			Model staticModel = new Model(objFile, meshes[i],
					new Material(textureName, Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, textureName)));
			models.add(staticModel);
		}
		return models;
	}
	
	public static Model loadOldModel(String objFile, String materialName) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		Loader loader = Loader.getInstance();
		Mesh mesh = loader.getVertexLoader().loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		Model model = new Model(objFile, mesh, new Material(materialName, Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, materialName)));
		return model;
	}
	
	public static Model loadModel(String objFile, Material material) {
		ModelData data = OBJFileLoader.loadOBJ(objFile);
		Loader loader = Loader.getInstance();
		Mesh mesh = loader.getVertexLoader().loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		Model model = new Model(objFile, mesh, material);
		return model;
	}
	
	public static Model loadModel(String name, Mesh mesh, String textureName) {
		Loader loader = Loader.getInstance();
		Model model = new Model(name, mesh,
				new Material(textureName, loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, textureName)));
		return model;
	}
	
	public static Model loadModel(String name, Mesh mesh, Material texture) {
		Model model = new Model(name, mesh, texture);
		return model;
	}

	public static Model loadModel(String objFile, String textureName, String normalTexture,
			String specularTexture) {
		Loader loader = Loader.getInstance();
		ModelData data = NormalMappedObjLoader.loadOBJ(objFile);
		Mesh mesh = new Mesh(objFile, Loader.getInstance().getVertexLoader()
				.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices()));
		Material texture = new Material(objFile + "Material", loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, "barrel"));
		Model model = new Model(objFile, mesh, texture);
		Texture2D normaMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_NORMAL_MAP_PATH, normalTexture);
		model.getMaterial().setNormalMap(normaMap);
		// TODO: настроить проверку на нуль
		if (specularTexture != null) {
			Texture2D specularMap = loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_SPECULAR_MAP_PATH, specularTexture);
			model.getMaterial().setSpecularMap(specularMap);
		}
		return model;
	}
	
	public static Model loadModel(String name, Mesh mesh, Texture2D texture, Texture2D normalTexture,
			Texture2D specularTexture) {
		Material material = new Material(name + "Material");
		material.setNormalMap(normalTexture);
		if(specularTexture!= null) {
			material.setSpecularMap(specularTexture);
		}
		Model model = new Model(name, mesh, material);
		
		return model;
	}

	public static List<Entity> createObjectField(float x, float z, float r, float sizeNoise, float density) {
		Model objectModel = loadModel("spartan", "spartan");
		objectModel.getMaterial().getDiffuseMap().setNumberOfRows(1);
		objectModel.getMaterial().setShininess(1);
		objectModel.getMaterial().setReflectivity(0);
		objectModel.getMaterial().getDiffuseMap().setTransparent(true);
		objectModel.getMaterial().setUseFakeLighting(true);
		if (density == 0) {
			density = (float) 0.01;
		}
		int radius = (int) (r * density);
		float invDensity = 1 / density;
		Random random = new Random();
		Shader unitShader = ShaderPool.getInstance().get(Shader.ENTITY); 
		
		return IntStream.range(0, radius).parallel()
				.mapToObj(j -> IntStream.range(0, radius).parallel()
						.mapToObj(i -> {
							int xSeed = random.nextInt(20);
							int ySeed = random.nextInt(180);
							int zSeed = random.nextInt(20);
							int textIndexSeed = random.nextInt(1);
							float noise = 0.06f + (float) random.nextInt(10) / 500;
							Entity fieldEntity = new Entity(
									"field" + String.valueOf(i) + "-" + String.valueOf(j),
									unitShader,
									Stream.of(objectModel).collect(Collectors.toList()), 
									textIndexSeed,
									new Vector3f(
											x + invDensity * i, 
											0, 
											z + invDensity * j), 
									new Vector3f(0, 0, 0), new Vector3f(noise, noise, noise));
							fieldEntity.setBaseName("fieldEntity");
							return fieldEntity;
						}))
				.flatMap(entity -> entity)
				.collect(Collectors.toList());
	}

	public static List<Water> createWaterSurfce(Vector3f position, int size) {
		List<Water> waters = new ArrayList<Water>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Water water = new Water(position.x + Water.TILE_SIZE * i,
						position.y + Water.TILE_SIZE * j, position.z, size);
				waters.add(water);
			}
		}
		return waters;
	}
	
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];
		
		for(int i = 0; i < data.length; i++) {
			result[i] = data[i].intValue();
		}		
		
		return result;
	}
	
	public static float[] toFloatArray(Object[] data) {
		float[] result = new float[data.length];
		
		for(int i = 0; i < data.length; i++) {
			result[i] = (Float)data[i];
		}
		
		return result;
	}

}
