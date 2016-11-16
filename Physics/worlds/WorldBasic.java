package worlds;

import org.lwjgl.util.vector.Vector3f;

public abstract class WorldBasic {
	
	protected int worldID;
	protected Vector3f position1;
	protected Vector3f position2;
	
	protected WorldBasic(int id, Vector3f position1, Vector3f position2) {
		worldID = id;
		this.position1 = position1;
		this.position2 = position2;
	}
	
	
	protected int getID() {
		return worldID;
	}
	
	protected void delete() {}

}
