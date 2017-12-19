package intersects;

import java.util.List;

import tool.math.vector.Vector3f;

public class IntersectData {
	
	private float distance;
	private List<Vector3f> positions;
	private int type;
	
	public IntersectData(int type, float distance, List<Vector3f> positions) {
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
	
	public void setPosition(List<Vector3f> positions) {
		this.positions = positions;
	}
	
	public List<Vector3f> getPositions() {
		return this.positions;
	}
	
	public void setType(int value) {
		this.type = value;
	}
	
	public int getType() {
		return type;
	}
	
}
