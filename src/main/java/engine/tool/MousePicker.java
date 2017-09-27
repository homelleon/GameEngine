package tool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.scene.IScene;
import tool.math.Maths;
import tool.math.VMatrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3fF;

/**
 * Ray casting from coursor.
 * 
 * @author homelleon
 *
 */
public class MousePicker {

	private Vector3fF currentRay = new Vector3fF(0, 0, 0);
	private Vector2f current2DPoint = new Vector2f(0, 0);

	private VMatrix4f projectionMatrix;
	private VMatrix4f viewMatrix;
	private ICamera camera;

	/**
	 * Mouse ray constructor.
	 * 
	 * @param camera
	 *            {@link ICamera} with current view position
	 * @param projectionMatrix
	 *            {@link VMatrix4f} value of object projection
	 */
	public MousePicker(ICamera camera, VMatrix4f projectionMatrix) {
		this.camera = camera;
		this.projectionMatrix = projectionMatrix;
		this.viewMatrix = Maths.createViewMatrix(camera);
	}

	/**
	 * Returns current ray direction.
	 * 
	 * @return {@link Vector3fF} value of current ray
	 */
	public Vector3fF getCurrentRay() {
		return this.currentRay;
	}
	
	public Vector2f getCurrentScreenPoint() {
		return this.current2DPoint;
	}

	/**
	 * Update viewMatrix and the current ray for MousePicker.
	 */
	public void update() {
		this.viewMatrix = Maths.createViewMatrix(this.camera);
		this.currentRay = calculateMouseRay();
	}

	/**
	 * Method that calculate ray casting from the screen of the camera
	 * transforming to the world space.
	 * 
	 * @return {@link Vector3fF} value of a ray in a world space.
	 */
	private Vector3fF calculateMouseRay() {
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		this.current2DPoint = getNormalizedDeviceCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(this.current2DPoint.x, this.current2DPoint.y, -1f, 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3fF worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	/**
	 * Calculate if the ray interects the sphere.
	 * <p>
	 * Uses to check if a bounding sphere was intersected by a ray.
	 * 
	 * @param position
	 *            Vec3f value of a current sphere center position
	 * @param radius
	 *            float value of a sphere radius
	 * @return true if the sphere was intersected</br>
	 *         false if the sphere wasn't intersected
	 */
	/* intersection with sphere */
	public boolean intersects(Vector3fF position, float radius) {
		boolean isIntersects = false;
		Vector3fF l = new Vector3fF(position.x - this.camera.getPosition().x, 
				position.y - this.camera.getPosition().y,
				position.z - this.camera.getPosition().z);
		Vector3fF currRay = getCurrentRay();
		float d = Vector3fF.dot(l, currRay);
		float lSq = Vector3fF.dot(l, l);
		if (d < 0 && lSq > radius) {
			return false;
		} else {
			float mSq = lSq - d * d;
			isIntersects = (mSq <= Maths.sqr(radius));
		}
		return isIntersects;
	}

	/**
	 * Calculate if the ray interects the box.
	 * <p>
	 * Uses to check if a bounding box was intersected by a ray.
	 * 
	 * @param min
	 *            Vec3f value of a minimum point of a box
	 * @param max
	 *            Vec3f value of a maximum point of a box
	 * @return true if the box was intersected</br>
	 *         false if the box wasn't intersected
	 */
	/* intersection with box */
	public boolean intersectsV(Vector3fF min, Vector3fF max) {
		boolean isIntersects = false;
		Vector3fF invertRay = getCurrentRay();
		invertRay = new Vector3fF(1 / invertRay.x, 1 / invertRay.y, 1 / invertRay.z);
		float lo = invertRay.x * min.x;
		float hi = invertRay.x * max.x;
		float tmin = Math.min(lo, hi);
		float tmax = Math.max(lo, hi);

		float lo1 = invertRay.y * min.y;
		float hi1 = invertRay.y * max.y;
		tmin = Math.max(tmin, Math.min(lo1, hi1));
		tmax = Math.min(tmax, Math.max(lo1, hi1));

		float lo2 = invertRay.z * min.z;
		float hi2 = invertRay.z * max.z;
		tmin = Math.max(tmin, Math.min(lo2, hi2));
		tmax = Math.min(tmax, Math.max(lo2, hi2));
		if ((tmin < tmax) && (tmax > 0)) {
			isIntersects = true;
		}

		return isIntersects;
	}
	
	public boolean intersects(Vector3fF min, Vector3fF max) {
		Vector3fF dir = getCurrentRay();
		Vector3fF center = this.camera.getPosition();
		
		float tmin = -10000;
		float tmax = 10000;
		if(dir.x != 0) {
			
			float tx1 = (min.x - center.x) / dir.x;
			float tx2 = (max.x - center.x) / dir.x;
			
			tmin = Math.max(tmin, Math.min(tx1, tx2));
			tmax = Math.min(tmax, Math.max(tx1, tx2));
			
		}
		
		if(dir.y != 0) {
			float ty1 = (min.y - center.y) / dir.y;
			float ty2 = (max.y - center.y) / dir.y;
			
			tmin = Math.max(tmin, Math.min(ty1, ty2));
			tmax = Math.min(tmax, Math.max(ty1, ty2));			
		}
		
		if(dir.z != 0) {
			float tz1 = (min.z - center.z) / dir.z;
			float tz2 = (max.z - center.z) / dir.z;
			
			tmin = Math.max(tmin, Math.min(tz1, tz2));
			tmax = Math.min(tmax, Math.max(tz1, tz2));			
		}
		
		return tmax > tmin;
	}

	public boolean intersectsF(Vector3fF min, Vector3fF max) {
		Vector3fF dir = getCurrentRay();
		Vector3fF center = this.camera.getPosition();

		float tmin = (min.x - center.x) / dir.x;
		float tmax = (max.x - center.x) / dir.x;

		if (tmin > tmax) {
			Maths.swapFloat(tmin, tmax);
		}

		float tymin = (min.y - center.y) / dir.y;
		float tymax = (max.y - center.y) / dir.y;

		if (tymin > tymax) {
			Maths.swapFloat(tymin, tymax);
		}

		if ((tmin > tymax) || (tymin > tmax)) {
			return false;
		}

		if (tymin > tmin) {
			tmin = tymin;
		}

		if (tymax < tmax) {
			tmax = tymax;
		}

		float tzmin = (min.z - center.z) / dir.z;
		float tzmax = (max.z - center.z) / dir.z;

		if (tzmin > tzmax) {
			Maths.swapFloat(tzmin, tzmax);
		}

		if ((tmin > tzmax) || (tzmin > tmax)) {
			return false;
		}

		if (tzmin > tmin) {
			tmin = tzmin;
		}

		if (tzmax < tmax) {
			tmax = tzmax;
		}

		return true;
	}

	/**
	 * Method that traslate coursor from eye space to the world space.
	 * 
	 * @param eyeCoords
	 *            Vector4f value of coordinates in eye space
	 * @return Vec3f value of coordinates in world space
	 */
	private Vector3fF toWorldCoords(Vector4f eyeCoords) {
		VMatrix4f invertedView = VMatrix4f.invert(this.viewMatrix);
		Vector4f rayWorld = VMatrix4f.transform(invertedView, eyeCoords);
		Vector3fF mouseRay = new Vector3fF(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalize();
		return mouseRay;
	}

	/**
	 * Method that translate clipping plane coordinates to eye-space
	 * coordinates.
	 * 
	 * @param clipCoords
	 *            Vector4f value of clipping plane coordinates.
	 * @return Vector4f value of eye-space plane coordinates.
	 */
	private Vector4f toEyeCoords(Vector4f clipCoords) {
		VMatrix4f invertedProjection = VMatrix4f.invert(this.projectionMatrix);
		Vector4f eyeCoords = VMatrix4f.transform(invertedProjection, clipCoords);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	/**
	 * Method that normailze coordinates of a current coursor position.
	 * 
	 * @param mouseX
	 *            float value of X coordinat of a coursor on the screen
	 * @param mouseY
	 *            float value of Y coordinat of a coursor on the screen
	 * @return Vec2f value of normialize and transponded mouse coordinates
	 */
	private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
		float x = (2f * mouseX) / Display.getWidth() - 1f;
		float y = (2f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}

	public IEntity chooseObjectByRay(IScene scene) {
		IEntity pickedEntity = null;
		List<IEntity> pointedEntities = new ArrayList<IEntity>();
		pointedEntities.addAll(
			scene.getEntities().updateWithFrustum(scene.getFrustum(), camera).values().stream()
			.flatMap(list -> list.stream())
			.filter(entity -> intersects(entity.getPosition(), entity.getSphereRadius()))
//			.filter(entity -> {
//				Vec3f min = entity.getModel().getRawModel().getBBox().getMin();
//				Vec3f max = entity.getModel().getRawModel().getBBox().getMax();
//				Vec3f position = entity.getPosition();
//				min = Vec3f.add(min, position);
//				max = Vec3f.add(max, position);
//				float scale = entity.getScale();
//				min.x *= scale;
//				min.y *= scale;
//				min.z *= scale;
//				max.x *= scale;
//				max.y *= scale;
//				max.z *= scale;
//				return intersects(min, max); 
//			})
			.collect(Collectors.toList())
		);
		float distance = EngineSettings.RENDERING_VIEW_DISTANCE + 1;
		float midDist = 0;
		int index = -1;
		for (int i = 0; i < pointedEntities.size(); i++) {
			midDist = Maths.distanceFromCamera(pointedEntities.get(i), this.camera);
			if (midDist <= distance) {
				distance = midDist;
				index = i;
			}
		}
		if (index >= 0) {
			pickedEntity = pointedEntities.get(index);
		}
		pointedEntities.clear();
		pointedEntities = null;

		return pickedEntity;
	}

}
