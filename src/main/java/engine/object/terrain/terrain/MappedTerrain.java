package object.terrain.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.settings.EngineSettings;
import object.camera.FreeCamera;
import object.camera.ICamera;
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
public class MappedTerrain extends ATerrain implements ITerrain {

	private static final float MAX_HEIGHT = 100;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;

	private float x;
	private float z;
	private Mesh model;
	private TerrainTexturePack texturePack;
	private Texture2D blendMap;
	private String heightMapName;
	private boolean isProcedureGenerated = false;
	private boolean isVisible = true;
	private float amplitude;
	private int octaves;
	private float roughness;

	private float[][] heights;

	/**
	 * Constructs terrain plane from height map.
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
	 * @param heightMap
	 *            {@link String} value of height map name
	 * 
	 * @see #Terrain(String, int, int, Loader, TerrainTexturePack,
	 *      TerrainTexture, float, int, float)
	 */
	public MappedTerrain(String name, int gridX, int gridZ, TerrainTexturePack texturePack,
			Texture2D blendMap, String heightMap) {
		super(name, new FreeCamera("FreeCamera", new Vector3f(0,0,0)));
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * ITerrain.TERRAIN_SIZE;
		this.z = gridZ * ITerrain.TERRAIN_SIZE;
		this.model = generateTerrain(heightMap);
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
		return null;
	}

	@Override
	public ITerrain clone(String name) {
		int gridX = (int) (this.x / ITerrain.TERRAIN_SIZE);
		int gridZ = (int) (this.z / ITerrain.TERRAIN_SIZE);
		return new MappedTerrain(name, gridX, gridZ, this.texturePack,this.blendMap,this.heightMapName);
	}

	private Mesh generateTerrain(String heightMap) {

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(EngineSettings.TEXTURE_HEIGHT_MAP_PATH + heightMap + ".png"));
			this.heightMapName = heightMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
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
				float height = getHeight(j, i, image);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = i / ((float) VERTEX_COUNT - 1) * ITerrain.TERRAIN_SIZE;
				Vector3f normal = calculateNormal(j, i, image);
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


	private Vector3f calculateNormal(int x, int z, BufferedImage image) {
		float heightL = getHeight(x - 1, z, image);
		float heightR = getHeight(x + 1, z, image);
		float heightD = getHeight(x, z - 1, image);
		float heightU = getHeight(x, z + 1, image);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalize();
		return normal;
	}

	private float getHeight(int x, int z, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;
		return height;
	}


}
