package tool.math;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.terrain.terrain.ITerrain;
import tool.math.vector.Vec2f;
import tool.math.vector.Vec3f;

public class Maths {

	public static Matrix4f createTransformationMatrix(Vec2f translation, Vec2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.translate(translation);
		matrix.scale(new Vec3f(scale.x, scale.y, 1f));
		return matrix;
	}

	public static float barryCentric(Vec3f p1, Vec3f p2, Vec3f p3, Vec2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4f createTransformationMatrix(Vec3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.translate(translation);
		matrix.rotate(new Vec3f(rx, 0, 0));
		matrix.rotate(new Vec3f(0, ry, 0));
		matrix.rotate(new Vec3f(0, 0, rz));
		matrix.scale(new Vec3f(scale, scale, scale));
		return matrix;
	}

	public static Matrix4f createViewMatrix(ICamera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		viewMatrix.rotate(new Vec3f(camera.getPitch(), 0, 0));
		viewMatrix.rotate(new Vec3f(0, camera.getYaw(), 0));
		viewMatrix.rotate(new Vec3f(0, 0, camera.getRoll()));
		Vec3f cameraPos = camera.getPosition();
		Vec3f negativeCameraPos = new Vec3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		viewMatrix.translate(negativeCameraPos);
		return viewMatrix;
	}

	public static float sqr(float a) {
		return a * a;
	}
	
	public static int sqr(int a) {
		return a * a;
	}
	
	public static float cube(float a) {
		return a * a * a;
	}
	
	public static int cube(int a) {
		return a * a * a;
	}

	public static void swapFloat(float a, float b) {
		float c = a;
		a = b;
		b = c;
	}

	/* distance between 2 points in 3D */
	public static float distance2Points(Vec3f point1, Vec3f point2) {
		float distance = 0;
		distance = Maths.sqr(point1.x - point2.x);
		distance += Maths.sqr(point1.y - point2.y);
		distance += Maths.sqr(point1.z - point2.z);
		distance = (float) Math.sqrt(distance);
		return distance;
	}

	/* distance between 2 points in 2D */
	public static float distance2Points(Vec2f point1, Vec2f point2) {
		float distance = 0;
		distance = Maths.sqr(point1.x - point2.x);
		distance += Maths.sqr(point1.y - point2.y);
		distance = (float) Math.sqrt(distance);
		return distance;
	}

	/* distance between line and point in 2D */
	public static float distanceLineAndPoint(Vec2f point, Vec2f linePoint1, Vec2f linePoint2) {
		float x = point.x;
		float y = point.y;

		float x1 = linePoint1.x;
		float y1 = linePoint1.y;

		float x2 = linePoint2.x;
		float y2 = linePoint2.y;

		/* exception if x2 - x1 = 0 */
		float difX = x2 - x1;
		if (difX == 0) {
			difX = 0.01f;
		}

		float k = (y2 - y1) / (difX);
		float b = y1 - k * x1;

		float xH = (y * k + x - b * k) / (sqr(k) + 1);
		float yH = k * xH + b;

		Vec2f vecH = new Vec2f(xH, yH);

		return distance2Points(point, vecH);
	}

	/* distance from camera to entity */
	public static float distanceFromCamera(IEntity entity, ICamera camera) {
		return distance2Points(entity.getPosition(), camera.getPosition());
	}
	
	public static float distanceFromCameraWithRadius(IEntity entity, ICamera camera) {
		return (distance2Points(entity.getPosition(), camera.getPosition()) - entity.getSphereRadius());
	}

	/* distance from camera to terrain */
	public static float distanceFromCamera(ITerrain terrain, ICamera camera) {
		float distance = 0;

		float cX = camera.getPosition().x;
		float cY = camera.getPosition().y;
		float cZ = camera.getPosition().z;
		float tX1 = terrain.getX() + terrain.getSize();
		float tZ1 = terrain.getZ() + terrain.getSize();
		float tX = terrain.getX();
		float tZ = terrain.getZ();

		Vec2f terrainPoint;
		Vec2f terrainSideP1;
		Vec2f terrainSideP2;
		Vec2f camVec = new Vec2f(cX, cZ);

		if (cX > tX1) {
			if (cZ < tZ) {
				terrainPoint = new Vec2f(tX1, tZ);
				distance = sqr(distance2Points(camVec, terrainPoint));
			} else if (cZ > tZ1) {
				terrainPoint = new Vec2f(tX1, tZ1);
				distance = sqr(distance2Points(camVec, terrainPoint));
			} else {
				terrainSideP1 = new Vec2f(tX1, tZ);
				terrainSideP2 = new Vec2f(tX1, tZ1);
				distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));
			}

		} else if (cX < tX) {
			if (cZ < tZ) {
				terrainPoint = new Vec2f(tX, tZ);
				distance = sqr(distance2Points(camVec, terrainPoint));
			} else if (cZ > tZ1) {
				terrainPoint = new Vec2f(tX, tZ1);
				distance = sqr(distance2Points(camVec, terrainPoint));
			} else {
				terrainSideP1 = new Vec2f(tX, tZ);
				terrainSideP2 = new Vec2f(tX, tZ1);
				distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));
			}

		} else if (cZ < tZ) {
			terrainSideP1 = new Vec2f(tX, tZ);
			terrainSideP2 = new Vec2f(tX1, tZ);
			distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));
			;
		} else if (cZ > tZ1) {
			terrainSideP1 = new Vec2f(tX, tZ1);
			terrainSideP2 = new Vec2f(tX1, tZ1);
			distance = sqr(distanceLineAndPoint(camVec, terrainSideP1, terrainSideP2));
		}

		distance += Maths.sqr(terrain.getHeightOfTerrain(cX, cZ) - cY);
		distance = (float) Math.sqrt(distance);

		return distance;
	}

	/*
	 * @homelleon "tailOfDivisionNoReminder" function to find tail number that
	 * is got after dividing one number on an other without reminder (can't
	 * explain it because of poor English)
	 */

	public static int tailOfDivisionNoReminder(int value1, int value2) {
		return value1 - (int) Math.floor(value1 / value2) * value2;
	}

	public static boolean pointIsOnRay(Vec3f point, Vec3f ray) {
		boolean isOnRay = false;

		float x = point.x;
		float y = point.y;
		float z = point.z;

		float p1 = ray.x;
		float p2 = ray.y;
		float p3 = ray.z;

		float oX = x / p1;
		float oY = y / p2;
		float oZ = z / p3;

		if ((oX) == (oY)) {
			if ((oY) == (oZ)) {
				isOnRay = true;
			}
		}
		return isOnRay;
	}

	public static boolean pointIsInQuad(Vec2f point, Vec2f quadPoint1, Vec2f quadPoint2) {
		return ((point.x >= quadPoint1.x) &&
				(point.x <= quadPoint2.x) &&
				(point.y >= quadPoint1.y) &&
				(point.y <= quadPoint2.y)) ? true : false;
		
	}
	
	public static int compareTo(Vec2f a, Vec2f b) {
		if(a == b) {
			return 0;
		} else if(a.x == b.x) {
			if(a.y < b.y) {return -1;}
		} else if(a.x < b.x) {
			return -1;
		}
		return 1;
}

}
