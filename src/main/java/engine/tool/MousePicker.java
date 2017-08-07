package tool;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.scene.scene.IScene;
import tool.math.Maths;

/**
 * Ray casting from coursor.
 * 
 * @author homelleon
 *
 */
public class MousePicker {

	private Vector3f currentRay = new Vector3f(0, 0, 0);

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private ICamera camera;

	/**
	 * Mouse ray constructor.
	 * 
	 * @param camera
	 *            {@link ICamera} with current view position
	 * @param projectionMatrix
	 *            {@link Matrix4f} value of object projection
	 */
	public MousePicker(ICamera camera, Matrix4f projectionMatrix) {
		this.camera = camera;
		this.projectionMatrix = projectionMatrix;
		this.viewMatrix = Maths.createViewMatrix(camera);
	}

	/**
	 * Returns current ray direction.
	 * 
	 * @return {@link Vector3f} value of current ray
	 */
	public Vector3f getCurrentRay() {
		return currentRay;
	}

	/**
	 * Update viewMatrix and the current ray for MousePicker.
	 */
	public void update() {
		viewMatrix = Maths.createViewMatrix(camera);
		currentRay = calculateMouseRay();
	}

	/**
	 * Method that calculate ray casting from the screen of the camera
	 * transforming to the world space.
	 * 
	 * @return {@link Vector3f} value of a ray in a world space.
	 */
	private Vector3f calculateMouseRay() {
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	/**
	 * Calculate if the ray interects the sphere.
	 * <p>
	 * Uses to check if a bounding sphere was intersected by a ray.
	 * 
	 * @param position
	 *            Vector3f value of a current sphere center position
	 * @param radius
	 *            float value of a sphere radius
	 * @return true if the sphere was intersected</br>
	 *         false if the sphere wasn't intersected
	 */
	/* intersection with sphere */
	public boolean intersects(Vector3f position, float radius) {
		boolean isIntersects = false;
		Vector3f l = new Vector3f(position.x - camera.getPosition().x, position.y - camera.getPosition().y,
				position.z - camera.getPosition().z);
		Vector3f currRay = getCurrentRay();
		float d = Vector3f.dot(l, currRay);
		float lSq = Vector3f.dot(l, l);
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
	 *            Vector3f value of a minimum point of a box
	 * @param max
	 *            Vector3f value of a maximum point of a box
	 * @return true if the box was intersected</br>
	 *         false if the box wasn't intersected
	 */
	/* intersection with box */
	public boolean intersectsW(Vector3f min, Vector3f max) {
		boolean isIntersects = false;
		Vector3f invertRay = getCurrentRay();
		invertRay = new Vector3f(1 / invertRay.x, 1 / invertRay.y, 1 / invertRay.z);
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

	public boolean intersects(Vector3f min, Vector3f max) {
		Vector3f dir = getCurrentRay();
		Vector3f center = camera.getPosition();

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
	 * @return Vector3f value of coordinates in world space
	 */
	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
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
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	/**
	 * Method that normailze coordinates of a current coursor position.
	 * 
	 * @param mouseX
	 *            float value of X coordinat of a coursor on the screen
	 * @param mouseY
	 *            float value of Y coordinat of a coursor on the screen
	 * @return Vector2f value of normialize and transponded mouse coordinates
	 */
	private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
		float x = (2f * mouseX) / Display.getWidth() - 1;
		float y = (2f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}

	public IEntity chooseObjectByRay(IScene scene) {
		IEntity pickedEntity = null;
		List<IEntity> pointedEntities = new ArrayList<IEntity>();
		for (List<IEntity> frustumList : scene.getEntities().getFromFrustum().values()) {
			for (IEntity entity : frustumList) {
				if (intersects(entity.getPosition(), entity.getSphereRadius())) {
					Vector3f min = entity.getModel().getRawModel().getBBox().getMin();
					Vector3f max = entity.getModel().getRawModel().getBBox().getMax();
					Vector3f position = entity.getPosition();
					float scale = entity.getScale();
					// only sphere intersection
					pointedEntities.add(entity);
					if (intersects(min, max)) {
						// box intersection
					}
				}

			}
		}
		float distance = EngineSettings.RENDERING_VIEW_DISTANCE + 1;
		float midDist = 0;
		int index = -1;
		for (int i = 0; i < pointedEntities.size(); i++) {
			midDist = Maths.distanceFromCamera(pointedEntities.get(i), camera);
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
