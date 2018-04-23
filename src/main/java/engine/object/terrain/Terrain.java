package object.terrain;

import core.settings.EngineSettings;
import manager.octree.Node;
import object.camera.Camera;
import object.camera.FreeCamera;
import primitive.texture.material.TerrainMaterial;
import tool.math.Maths;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

/**
 * Terrain class representing landscape plane.
 * 
 * @author homelleon
 * @see ITerrain
 */
public class Terrain extends Node<Vector3f> {
	
	public static final int TERRAIN_VERTEX_COUNT = 512;
	public static final int TERRAIN_SIZE = 6000;
	
	private float x;
	private float z;
	private TerrainMaterial material;
	private boolean isVisible = true;

	private float[][] heights;

	public Terrain(String name, int gridX, int gridZ, TerrainMaterial material, float[][] heights) {
		super(name);
		x = gridX * TERRAIN_SIZE;
		z = gridZ * TERRAIN_SIZE;
		this.material = material;
		this.heights = heights;
		EngineSettings.lod_morph_areas = new int[EngineSettings.LOD_RANGES.length];
		for(int i = 0; i < EngineSettings.LOD_RANGES.length; i++) {
			if(EngineSettings.LOD_RANGES[i] == 0) {
				EngineSettings.lod_morph_areas[i] = 0;
			} else { 
				EngineSettings.lod_morph_areas[i] = EngineSettings.LOD_RANGES[i] - (int) ((EngineSettings.TERRAIN_SCALE_XZ / TerrainQuadTree.getRootNodes()) / Math.pow(2, i + 1));
			}
		}
		addChild(new TerrainQuadTree(new FreeCamera("FreeCamera", new Vector3f(0,0,0))));
	}
	
	public void updateQuadTree(Camera camera) {
		((TerrainQuadTree) getChildren().get(0)).updateQuadTree(camera);
	}
	
	public TerrainQuadTree getQuadTree() {
		return (TerrainQuadTree) this.getChildren().get(0);
	}
	
	public float getSize() {
		return TERRAIN_SIZE;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public void setXPosition(int xPosition) {
		x = xPosition * TERRAIN_SIZE;		
	}

	public void setZPosition(int zPosition) {
		z = zPosition * TERRAIN_SIZE;		
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public float getHeightOfTerrain(float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = EngineSettings.TERRAIN_SCALE_XZ / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0)
			return EngineSettings.TERRAIN_SCALE_Y / 2;
		
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
	
	public TerrainMaterial getMaterial() {
		return material;
	}

	public Terrain clone(String name) {
		int gridX = (int) (x / TERRAIN_SIZE);
		int gridZ = (int) (z / TERRAIN_SIZE);
		return new Terrain(name, gridX, gridZ, material.clone(name + "Material"), heights.clone());
	}

}
