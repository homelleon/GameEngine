package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.PlayerCamera;
import terrains.Terrain;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static float sqr(float a) {
		return a*a;
	}
	
	public static float distance2Points(Vector3f point1, Vector3f point2) {
		float distance = 0;
		distance = Maths.sqr(point1.x - point2.x);
		distance += Maths.sqr(point1.y - point2.y);
		distance += Maths.sqr(point1.z - point2.z);
		distance = (float) Math.sqrt(distance);
	    return distance;
	}	
	
	public static float distance2Points(Vector2f point1, Vector2f point2) {
		float distance = 0;
		distance = Maths.sqr(point1.x - point2.x);
		distance += Maths.sqr(point1.y - point2.y);
		distance = (float) Math.sqrt(distance);
	    return distance;
	}	
	
	public static float distanceFromCamera(Entity entity, Camera camera) {
		float distance = 0;
		distance = distance2Points(entity.getPosition(), camera.getPosition());
	    return distance;
	}	
	
	public static float distanceFromCamera(Terrain terrain, Camera camera) {
		float distance = 0;
		
		float cX = camera.getPosition().x;
		float cY = camera.getPosition().y;
		float cZ = camera.getPosition().z;
		float tX1 = terrain.getX() * terrain.getSize();
		float tZ1 = terrain.getZ() * terrain.getSize();
		float tX = terrain.getX();
		float tZ = terrain.getZ();
		System.out.println(tX);
		//System.out.println(tZ);
		//System.out.println(tX1);
		//System.out.println(tZ1);
		
		Vector2f terrainPoint;
		Vector2f terrainSideP1;
		Vector2f terrainSideP2;
		Vector2f camVec = new Vector2f(cX, cZ);		
		
		if (cX < tX1) {			
			if(cZ < tZ) {
				terrainPoint = new Vector2f(tX, tZ);
				distance = sqr(distance2Points(camVec, terrainPoint));
			} else if(cZ > tZ1) {
				terrainPoint = new Vector2f(tX, tZ1);
				distance = sqr(distance2Points(camVec, terrainPoint));				
			} else {
				System.out.println("Left");
				terrainSideP1 = new Vector2f(tX, tZ1);
				terrainSideP2 = new Vector2f(tX, tZ);
				distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));
			}
		} else if (cX > tX1) {	
			if(cZ < tZ) {
				terrainPoint = new Vector2f(tX1, tZ);
				distance = sqr(distance2Points(camVec, terrainPoint));
			} else if(cZ > tZ1) {
				terrainPoint = new Vector2f(tX1, tZ1);
				distance = sqr(distance2Points(camVec, terrainPoint));				
			} else {
				System.out.println("Right");
				terrainSideP1 = new Vector2f(tX1, tZ1);
				terrainSideP2 = new Vector2f(tX1, tZ);
				distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));
			}
		} else if(cZ < tZ) {
			System.out.println("Bottom");
			terrainSideP1 = new Vector2f(tX, tX1);
			terrainSideP2 = new Vector2f(tZ, tZ1);
			distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));
		} else if(cZ > tZ1) {
			System.out.println("Top");
			terrainSideP1 = new Vector2f(tX, tZ1);
			terrainSideP2 = new Vector2f(tX1, tZ1);
			distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));				
		}
						
		distance += Maths.sqr(0 - camera.getPosition().y);
		distance = (float) Math.sqrt(distance);
		// TODO Count distances for camera at every side of terrain
		//System.out.println(distance);
	    return distance;
	}	
	
	public static float distanceLineAndPoint(Vector2f point, Vector2f linePoint1, Vector2f linePoint2) {
		float x = point.x;
		float y = point.y;
		
		float x1 = linePoint1.x;
		float y1 = linePoint1.y;
		
		float x2 = linePoint2.x;
		float y2 = linePoint2.y;
		
		float m = (y2 - y1) / (x2 - x1);
		float k = y1 - m * x1;
		
		float sqrx = (x + m * y - m * k) / (sqr(m) + 1);
		sqrx = sqr(sqrx);
				
		float sqry = m * (x + m * y - m * k) / (sqr(m) + 1) + k - y;
		sqry = sqr(sqry);
		
		float distance = (float) Math.sqrt(sqrx + sqry); 

		return distance;
	}
	
}
