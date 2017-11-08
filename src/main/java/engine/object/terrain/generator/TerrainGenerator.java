package object.terrain.generator;

import object.terrain.terrain.ITerrain;
import object.terrain.terrain.Terrain;
import object.texture.Texture2D;
import object.texture.material.TerrainMaterial;
import primitive.buffer.Loader;
import primitive.buffer.VBO;
import renderer.gpgpu.HeightMapRenderer;
import renderer.gpgpu.NormalMapRenderer;
import tool.math.vector.Vector2f;

/**
 * Generates terrain from height map or input value for generator.
 * <br>Please, create new class for different terrain type.
 * 
 * @author homelleon
 * @version 1.0
 */
public class TerrainGenerator {
	
	private static final float MAX_HEIGHT = 100;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	
	private Texture2D heightMap;
	private Texture2D normalMap;
	private Vector2f position;
	private boolean isProcedured = false;
	private int size = 128;
	private float amplitude;
	private int octaves;
	private float roughness;
	
	/**
	 * Construct terrain generator from height map.
	 * 
	 * @param size
	 * @param position
	 * @param heightMap
	 */
	public TerrainGenerator(int size, Vector2f position, Texture2D heightMap) {
		this.size = size;
		this.heightMap = heightMap;
		this.position = position;
	}
	
	/**
	 * Construct terrain generator from input values.
	 * 
	 * @param size 
	 * @param position
	 * @param amplitude
	 * @param octaves
	 * @param roughness
	 */
	public TerrainGenerator(int size, Vector2f position, float amplitude, int octaves, float roughness) {
		this.size = size;
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.position = position;
		this.isProcedured = true;
	}
	
	public ITerrain generate(String name) {
		return isProcedured ? generateProcedured(name) : generateHeightMapped(name);
	}
	
	private ITerrain generateProcedured(String name) {
		HeightsGenerator generator = new HeightsGenerator(amplitude, octaves, roughness);
		int VERTEX_COUNT = size;
		
		float[][] heights2D = new float[VERTEX_COUNT][VERTEX_COUNT]; // 2 dimentional heights
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] heights1D = new float[count]; // 1 dimentional heights for buffer object
		
		// create and store heights
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				float height = generator.generateHeight(j, i);
				heights2D[j][i] = height;
				heights1D[vertexPointer] = height;
				vertexPointer++;
			}
		}
		
		// bind vbo as texture buffer from 1 dimentional heights
		VBO vbo = Loader.getInstance().getVertexLoader().loadToVBOasTBO(heights1D);
		
		// generate height and normal map
		heightMap = generateHeightMap(size / 2, vbo);
		normalMap = generateNormalMap(size / 2, heightMap);

		TerrainMaterial material = getMaterial(name + "Material", heightMap, normalMap);
		
		return new Terrain(name, (int) position.x, (int) position.y, material, heights2D);
	}
	
	private ITerrain generateHeightMapped(String name) {
		int VERTEX_COUNT = heightMap.getHeight();
		
		float[][] heights2D = new float[VERTEX_COUNT][VERTEX_COUNT];
		
		// create and store heights
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				heights2D[j][i] = getHeight(j, i, heightMap);
			}
		}

		normalMap = generateNormalMap(size / 2, heightMap);

		TerrainMaterial material = getMaterial(name + "Material", heightMap, normalMap);
		
		return new Terrain(name, (int) position.x, (int) position.y, material, heights2D);
	}
	
	private TerrainMaterial getMaterial(String name, Texture2D heightMap, Texture2D normalMap) {
		return new TerrainMaterial(name)
				.setHeightMap(heightMap)
				.setNormalMap(normalMap);
	}
	
	private float getHeight(int x, int z, Texture2D heightMap) {
		if (x < 0 || x >= heightMap.getHeight() || z < 0 || z >= heightMap.getHeight()) {
			return 0;
		}
		// TODO: implement getRGB function
		float height = 0; // heightMap.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;
		return height;
	}
	
	private Texture2D generateHeightMap(int size, VBO vbo) {
		HeightMapRenderer heightRenderer = new HeightMapRenderer(size, vbo);
		heightRenderer.render();
		Texture2D heightMap = heightRenderer.getHeightMap();
		heightRenderer.clean();
		return heightMap;
	}
	
	private Texture2D generateNormalMap(int size, Texture2D heightMap) {
		NormalMapRenderer normalRenderer = new NormalMapRenderer(size, 15f, heightMap);
		normalRenderer.render();
		Texture2D normalMap = normalRenderer.getNormalMap();
		normalRenderer.clean();
		return normalMap;
	}
	

}
