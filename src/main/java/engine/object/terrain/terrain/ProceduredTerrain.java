package object.terrain.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.model.RawModel;
import object.terrain.generator.HeightsGenerator;
import object.texture.terrain.TerrainTexture;
import object.texture.terrain.TerrainTexturePack;
import renderer.loader.Loader;
import tool.math.Maths;

/**
 * Terrain class representing landscape plane.
 * 
 * @author homelleon
 * @see ITerrain
 */
public class ProceduredTerrain implements ITerrain {

	private static final float SIZE = 500;

	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private String heightMapName;
	private boolean isProcedureGenerated = false;
	private String name;
	private boolean isVisible = true;
	private float amplitude;
	private int octaves;
	private float roughness;

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
			TerrainTexture blendMap, float amplitude, int octaves, float roughness) {
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.model = generateWithProcedure(amplitude, octaves, roughness);
		this.name = name;
	}

	@Override
	public float getSize() {
		return SIZE;
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
		this.x = xPosition * SIZE;		
	}

	@Override
	public void setZPosition(int zPosition) {
		this.z = zPosition * SIZE;		
	}

	@Override
	public RawModel getModel() {
		return model;
	}

	@Override
	public String getName() {
		return name;
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
	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	@Override
	public String getHeightMapName() {
		return heightMapName;
	}

	@Override
	public boolean isProcedureGenerated() {
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
		float gridSquareSize = SIZE / ((float) heights.length - 1);
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
	public ITerrain clone(String name) {
		int gridX = (int) (this.x / SIZE);
		int gridZ = (int) (this.z / SIZE);
		return new ProceduredTerrain(name, gridX, gridZ, this.texturePack,this.blendMap, this.amplitude, this.octaves, this.roughness);
	}

	private RawModel generateWithProcedure(float amp, int oct, float rough) {
		this.isProcedureGenerated = true;
		HeightsGenerator generator = new HeightsGenerator(amp, oct, rough);

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
				vertices[vertexPointer * 3] = j / ((float) VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(j, i, generator);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = i / ((float) VERTEX_COUNT - 1) * SIZE;
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

	// private RawModel generateWithProcedure(Loader loader, float amp, int oct,
	// float rough) {
	// this.isProcedureGenerated = true;
	// HeightsGenerator generator = new HeightsGenerator(amp, oct, rough);
	//
	// int VERTEX_COUNT = 128;
	// heights = new float[VERTEX_COUNT][VERTEX_COUNT];
	// int count = VERTEX_COUNT * VERTEX_COUNT;
	// float[] vertices = new float[count * 3];
	// float[] normals = new float[count * 3];
	// float[] textureCoords = new float[count * 2];
	// int[] indices = new int[2*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
	// int vertexPointer = 0;
	// for(int i=0;i<VERTEX_COUNT;i++){
	// for(int j=0;j<VERTEX_COUNT;j++){
	// vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
	// float height = getHeight(j, i, generator);
	// heights[j][i] = height;
	// vertices[vertexPointer*3+1] = height;
	// vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
	// Vector3f normal = calculateNormal(j, i, generator);
	// normals[vertexPointer*3] = normal.x;
	// normals[vertexPointer*3+1] = normal.y;
	// normals[vertexPointer*3+2] = normal.z;
	// textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
	// textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
	// vertexPointer++;
	// }
	// }
	// int pointer = 0;
	// int INDEX_COUNT = VERTEX_COUNT / 3;
	// for(int gz=0;gz<INDEX_COUNT-1;gz++) {
	// for(int gx=0;gx<INDEX_COUNT-1;gx++) {
	// int center = ((3 * gz + 1) * VERTEX_COUNT) + 3 * gx + 1;
	// int topLeft = ((3 * gz) * VERTEX_COUNT) + 3 * gx;
	// int top = topLeft + 1;
	// int topRight = ((3 * gz) * VERTEX_COUNT) + 3 * gx + 2;
	// int bottomRight = ((3 * gz + 2) * VERTEX_COUNT) + 3 * gx + 2;
	// int bottomLeft = bottomRight - 2;
	// int left = center - 1;
	//
	// indices[pointer++] = center;
	// indices[pointer++] = topLeft;
	// indices[pointer++] = top;
	// indices[pointer++] = topRight;
	// indices[pointer++] = bottomRight;
	// indices[pointer++] = bottomLeft;
	// indices[pointer++] = left;
	// indices[pointer++] = topLeft;
	// }
	// }
	// return loader.loadToVAO(vertices, textureCoords, normals, indices);
	// }

	private float getHeight(int x, int z, HeightsGenerator generator) {
		return generator.generateHeight(x, z);
	}

	private Vector3f calculateNormal(int x, int z, HeightsGenerator generator) {
		float heightL = getHeight(x - 1, z, generator);
		float heightR = getHeight(x + 1, z, generator);
		float heightD = getHeight(x, z - 1, generator);
		float heightU = getHeight(x, z + 1, generator);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}

}
