package worlds;

import org.lwjgl.util.vector.Vector3f;

/*
 *  world with gravity
 */

public class WorldG extends WorldBasic implements World {
	
	private float gravity = 9.8f;

	public WorldG(int id, Vector3f position1, Vector3f position2) {
		super(id, position1, position2);
		hasGravity = true;
	}

	@Override
	public int getID() {
		return super.getID();
	}
	
	@Override
	public boolean hasGravity() {
		return super.hasGravity();
	}
	
	@Override
	public void setGravity(float value) {
		this.gravity = value;
	}
	
	@Override
	public float getGravity() {
		return this.gravity;
	}
	
	@Override
	public void update() {
		super.update();		
	}

	@Override
	public void delete() {
		super.delete();		
	}

}
