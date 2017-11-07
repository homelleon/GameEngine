package object.terrain.terrain;

import core.settings.EngineSettings;
import object.GameObject;
import object.camera.FreeCamera;
import object.camera.ICamera;
import object.texture.material.TerrainMaterial;
import tool.math.Maths;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

/**
 * Terrain class representing landscape plane.
 * 
 * @author homelleon
 * @see ITerrain
 */
public class Terrain extends GameObject implements ITerrain {
	
	private float x;
	private float z;
	private TerrainMaterial material;
	private boolean isVisible = true;

	private float[][] heights;

	public Terrain(String name, int gridX, int gridZ, TerrainMaterial material, float[][] heights) {
		super(name);
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
		return new Terrain(name, gridX, gridZ, this.material.clone(name + "Material"), this.heights.clone());
	}

}
