package intersects;

import java.util.List;

import tool.math.vector.Vec3f;

public class IntersectData {
	
	private float distance;
	private List<Vec3f> positions;
	private int type;
	
	public IntersectData(int type, float distance, List<Vec3f> positions) {
		this.type = type;
		this.distance = distance;
		this.positions = positions;
	}
		
	public void setDistance(int value) {
		this.distance = value;
	}	
	
	public float getDistance() {
		return this.distance;
	}	
	
	public void setPosition(List<Vec3f> positions) {
		this.positions = positions;
	}
	
	public List<Vec3f> getPositions() {
		return this.positions;
	}
	
	public void setType(int value) {
		this.type = value;
	}
	
	public int getType() {
		return type;
	}
	
}
