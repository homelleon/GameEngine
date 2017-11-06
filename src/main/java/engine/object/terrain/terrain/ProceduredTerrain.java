package object.terrain.terrain;

import core.settings.EngineSettings;
import object.GameObject;
import object.camera.FreeCamera;
import object.camera.ICamera;
import object.terrain.generator.HeightsGenerator;
import object.texture.material.TerrainMaterial;
import primitive.buffer.Loader;
import primitive.buffer.VBO;
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
public class ProceduredTerrain extends GameObject implements ITerrain {

	private float x;
	private float z;
	private VBO vbo;
	private TerrainMaterial material;
	private boolean isVisible = true;

	private float[][] heights;

	public ProceduredTerrain(String name, int gridX, int gridZ, TerrainMaterial material, VBO vbo, float[][] heights) {
		super(name);
		super(name, new FreeCamera("FreeCamera", new Vector3f(0,0,0)));
		this.vbo = vbo;
		this.x = gridX * ITerrain.TERRAIN_SIZE;
		this.z = gridZ * ITerrain.TERRAIN_SIZE;
		this.material = material;
		this.heights = heights;
		EngineSettings.lod_morph_areas = new int[EngineSettings.LOD_RANGES.length];
		for(int i = 0; i < EngineSettings.LOD_RANGES.length; i++) {
			if(EngineSettings.LOD_RANGES[i] == 0) {
				EngineSettings.lod_morph_areas[i] = 0;
			} else { 
				EngineSettings.lod_morph_areas[i] = EngineSettings.LOD_RANGES[i] - (int) ((EngineSettings.SCALE_XZ / TerrainQuadTree.getRootNodes()) / Math.pow(2, i + 1));
			}
		}
		addChild(new TerrainQuadTree(new FreeCamera("FreeCamera", new Vector3f(0,0,0))));
//		this.model = generateWithProcedure(amplitude, octaves, roughness);
	}
	
	@Override
	public void updateQuadTree(ICamera camera) {
		((TerrainQuadTree) getChildren().get(0)).updateQuadTree(camera);
	}
	
	@Override
	public TerrainQuadTree getQuadTree() {
		return (TerrainQuadTree) this.getChildren().get(0);
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
	public VBO getVbo() {
		return vbo;
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
	public TerrainMaterial getMaterial() {
		return material;
	}

	@Override
	public ITerrain clone(String name) {
		int gridX = (int) (this.x / ITerrain.TERRAIN_SIZE);
		int gridZ = (int) (this.z / ITerrain.TERRAIN_SIZE);
		return new ProceduredTerrain(name, gridX, gridZ, this.material, this.vbo, this.heights);
	}

	private Mesh generateWithProcedure(float amp, int oct, float rough) {
		this.generator = new HeightsGenerator(amp, oct, rough);
		int VERTEX_COUNT = ITerrain.TERRAIN_VERTEX_COUNT;
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
//		float[] normals = new float[count * 3];
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
//				Vector3f normal = calculateNormal(j, i, generator);
//				normals[vertexPointer * 3] = normal.x;
//				normals[vertexPointer * 3 + 1] = normal.y;
//				normals[vertexPointer * 3 + 2] = normal.z;
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
		return Loader.getInstance().getVertexLoader().loadToVAOwithTBO(vertices, textureCoords, indices);
	}
	
	private Mesh generateWithProcedure(float amp, int oct, float rough, int seed) {
		this.generator = new HeightsGenerator(amp, oct, rough, seed);
		int VERTEX_COUNT = 128;
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
//		float[] normals = new float[count * 3];
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
//				Vector3f normal = calculateNormal(j, i, generator);
//				normals[vertexPointer * 3] = normal.x;
//				normals[vertexPointer * 3 + 1] = normal.y;
//				normals[vertexPointer * 3 + 2] = normal.z;
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
		return Loader.getInstance().getVertexLoader().loadToVAOwithTBO(vertices, textureCoords, indices);
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
