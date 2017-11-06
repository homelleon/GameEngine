package object.terrain.terrain;

import object.texture.Texture2D;
import tool.math.vector.Vector2f;

public class TerrainGenerator {
	
	private Texture2D heightMap;
	private ITerrain terrain;
	private Vector2f position;
	private boolean isProcedured = false;
	private int size;
	private float amplitude;
	private int octaves;
	private float roughness;
	
	public TerrainGenerator(int size, Vector2f position, Texture2D heightMap) {
		this.size = size;
		this.heightMap = heightMap;
		this.position = position;
	}
	
	public TerrainGenerator(int size, Vector2f position, float amplitude, int octaves, float roughness) {
		this.size = size;
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.position = position;
		this.isProcedured = true;
	}
	
	public ITerrain generate() {
		return isProcedured ? generateProcedured() : generateHeightMapped();
	}
	
	private ITerrain generateProcedured() {
		return null;
	}
	
	private ITerrain generateHeightMapped() {
		return null;
	}
	

}
