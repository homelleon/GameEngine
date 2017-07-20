package worlds;

import java.util.WeakHashMap;

import org.lwjgl.util.vector.Vector3f;

import bodies.BodyInterface;
import object.entity.entity.Entity;
import bodies.Body2DCircle;
import bodies.Body2DPlane;
import bodies.Body2DQuad;
import bodies.Body2DTriangle;
import bodies.Body3DCube;
import bodies.Body3DMesh;
import bodies.Body3DPyramid;
import bodies.Body3DSphere;
import physicMain.PE10;

/*
 *  world with gravity
 */

public class WorldG extends WorldB implements WorldInterface {
	
	private float gravity = 9.8f;
	

	public WorldG(int id, Vector3f position1, Vector3f position2) {
		super(id, position1, position2);
		super.hasGravity = true;
		super.bodies = new WeakHashMap<Integer, BodyInterface>();
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
	public boolean removeBody(int bodyID) {
		return super.removeBody(bodyID);
	}
	
	@Override
	public int attachToEntity(Entity entity, int bodyType) {
		BodyInterface body = null;
		switch(bodyType) {
			case PE10.BODY_2D_CIRCLE: 
				body = new Body2DCircle();
			case PE10.BODY_2D_PLANE:
				body = new Body2DPlane();
			case PE10.BODY_2D_QUAD:
				body = new Body2DQuad();
			case PE10.BODY_2D_TRIANGLE:
				body = new Body2DTriangle();
			case PE10.BODY_3D_CUBE:
				body = new Body3DCube();
			case PE10.BODY_3D_MESH:
				body = new Body3DMesh();
			case PE10.BODY_3D_PYRAMID:
				body = new Body3DPyramid();
			case PE10.BODY_3D_SPHERE:
				body = new Body3DSphere();
		}
		body.attachEntity(entity);		
		super.bodyIDCount += 1;
		super.bodies.put(super.bodyIDCount, body);
		return bodyIDCount;
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
