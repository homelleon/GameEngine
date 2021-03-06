package object.water;

public class WaterTile {

	public static final float TILE_SIZE = 10000;

	private float height;
	private float x, z;
	private float size;
	private float tilingSize;
	private float waterSpeed;
	private float waveStrength;

	private String name;

	public WaterTile(float centerX, float centerZ, float height, float size) {
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
		this.size = size;
		this.tilingSize = 0.4f * size;
		this.waterSpeed = 0;
		this.waveStrength = 0;
		this.name = "NoName";
	}

	public WaterTile(String name, float centerX, float centerZ, float height, float size) {
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
		this.size = size;
		this.tilingSize = 0.4f * size;
		this.waterSpeed = 0;
		this.waveStrength = 0;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public void setTilingSize(float tilingSize) {
		this.tilingSize = tilingSize * size;
	}

	public float getTilingSize() {
		return tilingSize;
	}

	public float getWaterSpeed() {
		return waterSpeed;
	}

	public void setWaterSpeed(float waterSpeed) {
		this.waterSpeed = waterSpeed;
	}

	public float getWaveStrength() {
		return waveStrength;
	}

	public void setWaveStrength(float waveStrength) {
		this.waveStrength = waveStrength;
	}

	public float getSize() {
		return size;
	}

}