package object.terrain.terrain;

import object.terrain.generator.HeightsGenerator;
import object.texture.Texture2D;
import object.texture.terrain.TerrainTexturePack;
import primitive.buffer.Loader;
import primitive.model.Mesh;
import tool.math.Maths;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

/**
 * Terrain class representing landscape plane.
 * 
 * @author homelleon
 * @see ITerrain
 */
public class ProceduredTerrain extends ATerrain implements ITerrain {

	private float x;
	private float z;
	private Mesh model;
	private TerrainTexturePack texturePack;
	private Texture2D blendMap;
	private String heightMapName;
	private boolean isProcedureGenerated = true;
	private boolean isVisible = true;
	private float amplitude;
	private int octaves;
	private float roughness;
	private HeightsGenerator generator;

	private float[][] heights;

	/**
	 * Constructs random terrain plane from input parameters by terrain
	 * procedure generator.
	 * 
	 * @param name
	 *            {@link String} value of terrain name
	 * @param gridX
	 *            {@link integer} value of x-roordinate with step = SIZE
	 * @param gridZ
	 *            {@link integer} value of z-roordinate with step = SIZE
	 * @param loader
	 *            {@link Loader} object used to load textures and verticies
	 * @param texturePack
	 *            {@link TerrainTexturePack} pack of 4 textures
	 * @param blendMap
	 *            {@link TerrainTexture} texture to define intensity of blending
	 *            affect on surface
	 * @param amplitude
	 *            {@link Float} value of maximum and minimum height for terrain
	 *            generation
	 * @param octaves
	 *            {@link Integer} value of point to point changes intensity for
	 *            terrain generator
	 * @param roughness
	 *            {@link Float} value of terrain edges roughness for terrain
	 *            generator
	 * @see #Terrain(String, int, int, Loader, TerrainTexturePack,
	 *      TerrainTexture, String)
	 */
	public ProceduredTerrain(String name, int gridX, int gridZ, TerrainTexturePack texturePack,
			Texture2D blendMap, float amplitude, int octaves, float roughness) {
		super(name);
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * ITerrain.TERRAIN_SIZE;
		this.z = gridZ * ITerrain.TERRAIN_SIZE;
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.model = generateWithProcedure(amplitude, octaves, roughness);
	}
	
	public ProceduredTerrain(String name, int seed, int gridX, int gridZ, TerrainTexturePack texturePack,
			Texture2D blendMap, float amplitude, int octaves, float roughness) {
		super(name);
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * ITerrain.TERRAIN_SIZE;
		this.z = gridZ * ITerrain.TERRAIN_SIZE;
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.model = generateWithProcedure(amplitude, octaves, roughness, seed);
	}
	
	public ProceduredTerrain(String name, int gridX, int gridZ, TerrainTexturePack texturePack,
			Texture2D blendMap, float amplitude, int octaves, float roughness, float[][] heights, Mesh model) {
		super(name);
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * ITerrain.TERRAIN_SIZE;
		this.z = gridZ * ITerrain.TERRAIN_SIZE;
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.heights = heights;
		this.model = model;
	}

	@Override
	public float getSize() {
		return ITerrain.TERRAIN_SIZE;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getZ() {
		return z;
	}

	@Override
	public void setXPosition(int xPosition) {
		this.x = xPosition * ITerrain.TERRAIN_SIZE;		
	}

	@Override
	public void setZPosition(int zPosition) {
		this.z = zPosition * ITerrain.TERRAIN_SIZE;		
	}

	@Override
	public Mesh getModel() {
		return model;
	}

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	@Override
	public Texture2D getBlendMap() {
		return blendMap;
	}

	@Override
	public String getHeightMapName() {
		return heightMapName;
	}

	@Override
	public boolean getIsProcedureGenerated() {
		return isProcedureGenerated;
	}

	@Override
	public float getAmplitude() {
		return amplitude;
	}

	@Override
	public int getOctaves() {
		return octaves;
	}

	@Override
	public float getRoughness() {
		return roughness;
	}

	@Override
	public float getHeightOfTerrain(float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = ITerrain.TERRAIN_SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord)) {
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0),
					new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1),
					new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0),
					new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1),
					new Vector2f(xCoord, zCoord));
		}
		return answer;
	}
	
	@Override
	public HeightsGenerator getGenerator() {
		return this.generator;
	}
	

	@Override
	public ITerrain clone(String name) {
		int gridX = (int) (this.x / ITerrain.TERRAIN_SIZE);
		int gridZ = (int) (this.z / ITerrain.TERRAIN_SIZE);
		return new ProceduredTerrain(name, gridX, gridZ, 
				this.texturePack,this.blendMap, this.amplitude, 
				this.octaves, this.roughness, this.heights, this.model);
	}

	private Mesh generateWithProcedure(float amp, int oct, float rough) {
		this.generator = new HeightsGenerator(amp, oct, rough);
		int VERTEX_COUNT = ITerrain.TERRAIN_VERTEX_COUNT;
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = j / ((float) VERTEX_COUNT - 1) * ITerrain.TERRAIN_SIZE;
				float height = getHeight(j, i, generator);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = i / ((float) VERTEX_COUNT - 1) * ITerrain.TERRAIN_SIZE;
				Vector3f normal = calculateNormal(j, i, generator);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return Loader.getInstance().getVertexLoader().loadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private Mesh generateWithProcedure(float amp, int oct, float rough, int seed) {
		this.generator = new HeightsGenerator(amp, oct, rough, seed);
		int VERTEX_COUNT = 128;
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = j / ((float) VERTEX_COUNT - 1) * ITerrain.TERRAIN_SIZE;
				float height = getHeight(j, i, generator);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = i / ((float) VERTEX_COUNT - 1) * ITerrain.TERRAIN_SIZE;
				Vector3f normal = calculateNormal(j, i, generator);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return Loader.getInstance().getVertexLoader().loadToVAO(vertices, textureCoords, normals, indices);
	}

	private float getHeight(int x, int z, HeightsGenerator generator) {
		return generator.generateHeight(x, z);
	}

	private Vector3f calculateNormal(int x, int z, HeightsGenerator generator) {
		float heightL = getHeight(x - 1, z, generator);
		float heightR = getHeight(x + 1, z, generator);
		float heightD = getHeight(x, z - 1, generator);
		float heightU = getHeight(x, z + 1, generator);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalize();
		return normal;
	}

}
