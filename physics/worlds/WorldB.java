package worlds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import bodies.BodyInterface;
import intersects.IntersectData;
import objects.entities.EntityInterface;

public abstract class WorldB {
	
	protected int worldID;
	protected Vector3f position1;
	protected Vector3f position2;
	protected boolean hasGravity;
	
	protected Map<Integer, BodyInterface> bodies;
	protected int bodyIDCount = 0; 
	
	protected WorldB(int id, Vector3f position1, Vector3f position2) {
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
	
	protected int attachToEntity(EntityInterface entity, int bodyType) {
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
		for(BodyInterface body1 : bodies.values()) {			
				List<BodyInterface> bodyList = new ArrayList<BodyInterface>();
				bodyList.addAll(bodies.values());
				
				for(BodyInterface body2 : bodyList) {
					if (!(body2 == body1)) {
						IntersectData data = body1.checkIntersection(body2);
						if(data.getDistance() < 200) {
							System.out.println(body1.getEntity().getName() + " is close to " + 
						body2.getEntity().getName() + " at " + data.getDistance());
					}
				}
				body1.update();
				//bodyBatch.clear();
			}
		}
		System.out.println();
	}
	
	protected void delete() {}

}
