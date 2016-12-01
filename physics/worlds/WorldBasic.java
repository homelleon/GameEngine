package worlds;

import java.util.Collection;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import bodies.Body;
import entities.Entity;
import intersects.IntersectData;

public abstract class WorldBasic {
	
	protected int worldID;
	protected Vector3f position1;
	protected Vector3f position2;
	protected boolean hasGravity;
	
	protected Map<Integer, Body> bodies;
	protected int bodyIDCount = 0; 
	
	protected WorldBasic(int id, Vector3f position1, Vector3f position2) {
		worldID = id;
		this.position1 = position1;
		this.position2 = position2;
	}
		
	protected int getID() {
		return worldID;
	}
	
	protected boolean hasGravity() {
		return hasGravity;
	}
	
	private boolean isContainsBody(int bodyID) {
		boolean isSucced = false;
		if (bodies.containsKey(bodyID)) {
			isSucced = true;
		}
		return isSucced;
	}
	
	protected int attachToEntity(Entity entity, int bodyType) {
		return 0;
	}
	
	protected boolean removeBody(int bodyID) {
		boolean isSucced = false;
		if(isContainsBody(bodyID)) {
			bodies.remove(bodyID);
			isSucced = true;
		}
		return isSucced;
	}
	
	protected void update() {
		for(Body body1 : bodies.values()) {			
				Collection<Body> bodyBatch = bodies.values();
				bodyBatch.remove(body1);
				for(Body body2 : bodyBatch) {
					IntersectData data = body1.checkIntersection(body2);
					if(data.getDistance() < 900) {
						System.out.println(body1.getEntity().getName() + " is close to " + 
					body2.getEntity().getName() + " at " + data.getDistance());
				}
				bodyBatch.clear();
			}
		}
	}
	
	protected void delete() {}

}
