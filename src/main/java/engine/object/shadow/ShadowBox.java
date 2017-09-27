package object.shadow;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import tool.math.VMatrix4f;
import tool.math.vector.Vector3fF;

/**
 * Represents the 3D cuboidal area of the world in which objects will cast
 * shadows (basically represents the orthographic projection area for the shadow
 * render pass). It is updated each frame to optimise the area, making it as
 * small as possible (to allow for optimal shadow map resolution) while not
 * being too small to avoid objects not having shadows when they should.
 * Everything inside the cuboidal area represented by this object will be
 * rendered to the shadow map in the shadow render pass. Everything outside the
 * area won't be.
 * 
 * @author Karl
 *
 */
public class ShadowBox {

	private static final float OFFSET = EngineSettings.SHADOW_DISTANCE;
	private static final Vector4f UP = new Vector4f(0, 1, 0, 0);
	private static final Vector4f FORWARD = new Vector4f(0, 0, -1, 0);

	private float minX, maxX;
	private float minY, maxY;
	private float minZ, maxZ;
	private VMatrix4f lightViewMatrix;
	private ICamera cam;

	private float farHeight, farWidth, nearHeight, nearWidth;

	/**
	 * Creates a new shadow box and calculates some initial values relating to
	 * the camera's view frustum, namely the width and height of the near plane
	 * and (possibly adjusted) far plane.
	 * 
	 * @param lightViewMatrix
	 *            - basically the "view matrix" of the light. Can be used to
	 *            transform a point from world space into "light" space (i.e.
	 *            changes a point's coordinates from being in relation to the
	 *            world's axis to being in terms of the light's local axis).
	 * @param camera
	 *            - the in-game camera.
	 */
	public ShadowBox(VMatrix4f lightViewMatrix, ICamera camera) {
		this.lightViewMatrix = lightViewMatrix;
		this.cam = camera;
		calculateWidthsAndHeights();
	}

	/**
	 * Updates the bounds of the shadow box based on the light direction and the
	 * camera's view frustum, to make sure that the box covers the smallest area
	 * possible while still ensuring that everything inside the camera's view
	 * (within a certain range) will cast shadows.
	 */
	public void update() {
		VMatrix4f rotation = calculateCameraRotationMatrix();
		Vector3fF forwardVector = new Vector3fF(VMatrix4f.transform(rotation, FORWARD));

		Vector3fF toFar = new Vector3fF(forwardVector);
		toFar.scale(EngineSettings.SHADOW_DISTANCE);
		Vector3fF toNear = new Vector3fF(forwardVector);
		toNear.scale(EngineSettings.NEAR_PLANE);
		Vector3fF centerNear = Vector3fF.add(toNear, cam.getPosition());
		Vector3fF centerFar = Vector3fF.add(toFar, cam.getPosition());

		Vector4f[] points = calculateFrustumVertices(rotation, forwardVector, centerNear, centerFar);

		boolean first = true;
		for (Vector4f point : points) {
			if (first) {
				minX = point.x;
				maxX = point.x;
				minY = point.y;
				maxY = point.y;
				minZ = point.z;
				maxZ = point.z;
				first = false;
				continue;
			}
			if (point.x > maxX) {
				maxX = point.x;
			} else if (point.x < minX) {
				minX = point.x;
			}
			if (point.y > maxY) {
				maxY = point.y;
			} else if (point.y < minY) {
				minY = point.y;
			}
			if (point.z > maxZ) {
				maxZ = point.z;
			} else if (point.z < minZ) {
				minZ = point.z;
			}
		}
		maxZ += OFFSET;

	}

	/**
	 * Calculates the center of the "view cuboid" in light space first, and then
	 * converts this to world space using the inverse light's view matrix.
	 * 
	 * @return The center of the "view cuboid" in world space.
	 */
	public Vector3fF getCenter() {
		float x = (minX + maxX) / 2f;
		float y = (minY + maxY) / 2f;
		float z = (minZ + maxZ) / 2f;
		Vector4f cen = new Vector4f(x, y, z, 1);
		VMatrix4f invertedLight = new VMatrix4f();
		VMatrix4f.invert(lightViewMatrix, invertedLight);
		return new Vector3fF(VMatrix4f.transform(invertedLight, cen));
	}

	/**
	 * @return The width of the "view cuboid" (orthographic projection area).
	 */
	public float getWidth() {
		return maxX - minX;
	}

	/**
	 * @return The height of the "view cuboid" (orthographic projection area).
	 */
	public float getHeight() {
		return maxY - minY;
	}

	/**
	 * @return The length of the "view cuboid" (orthographic projection area).
	 */
	public float getLength() {
		return maxZ - minZ;
	}

	/**
	 * Calculates the position of the vertex at each corner of the view frustum
	 * in light space (8 vertices in total, so this returns 8 positions).
	 * 
	 * @param rotation
	 *            - camera's rotation.
	 * @param forwardVector
	 *            - the direction that the camera is aiming, and thus the
	 *            direction of the frustum.
	 * @param centerNear
	 *            - the center point of the frustum's near plane.
	 * @param centerFar
	 *            - the center point of the frustum's (possibly adjusted) far
	 *            plane.¸
	 * @return The positions of the vertices of the frustum in light space.
	 */
	private Vector4f[] calculateFrustumVertices(VMatrix4f rotation, Vector3fF forwardVector, Vector3fF centerNear,
			Vector3fF centerFar) {
		Vector3fF upVector = new Vector3fF(VMatrix4f.transform(rotation, UP));
		Vector3fF rightVector = Vector3fF.cross(forwardVector, upVector);
		Vector3fF downVector = new Vector3fF(-upVector.x, -upVector.y, -upVector.z);
		Vector3fF leftVector = new Vector3fF(-rightVector.x, -rightVector.y, -rightVector.z);
		Vector3fF farTop = Vector3fF.add(centerFar,
				new Vector3fF(upVector.x * farHeight, upVector.y * farHeight, upVector.z * farHeight));
		Vector3fF farBottom = Vector3fF.add(centerFar,
				new Vector3fF(downVector.x * farHeight, downVector.y * farHeight, downVector.z * farHeight));
		Vector3fF nearTop = Vector3fF.add(centerNear,
				new Vector3fF(upVector.x * nearHeight, upVector.y * nearHeight, upVector.z * nearHeight));
		Vector3fF nearBottom = Vector3fF.add(centerNear,
				new Vector3fF(downVector.x * nearHeight, downVector.y * nearHeight, downVector.z * nearHeight));
		Vector4f[] points = new Vector4f[8];
		points[0] = calculateLightSpaceFrustumCorner(farTop, rightVector, farWidth);
		points[1] = calculateLightSpaceFrustumCorner(farTop, leftVector, farWidth);
		points[2] = calculateLightSpaceFrustumCorner(farBottom, rightVector, farWidth);
		points[3] = calculateLightSpaceFrustumCorner(farBottom, leftVector, farWidth);
		points[4] = calculateLightSpaceFrustumCorner(nearTop, rightVector, nearWidth);
		points[5] = calculateLightSpaceFrustumCorner(nearTop, leftVector, nearWidth);
		points[6] = calculateLightSpaceFrustumCorner(nearBottom, rightVector, nearWidth);
		points[7] = calculateLightSpaceFrustumCorner(nearBottom, leftVector, nearWidth);
		return points;
	}

	/**
	 * Calculates one of the corner vertices of the view frustum in world space
	 * and converts it to light space.
	 * 
	 * @param startPoint
	 *            - the starting center point on the view frustum.
	 * @param direction
	 *            - the direction of the corner from the start point.
	 * @param width
	 *            - the distance of the corner from the start point.
	 * @return - The relevant corner vertex of the view frustum in light space.
	 */
	private Vector4f calculateLightSpaceFrustumCorner(Vector3fF startPoint, Vector3fF direction, float width) {
		Vector3fF point = Vector3fF.add(startPoint,
				new Vector3fF(direction.x * width, direction.y * width, direction.z * width));
		Vector4f point4f = new Vector4f(point.x, point.y, point.z, 1f);
		point4f = VMatrix4f.transform(lightViewMatrix, point4f);
		return point4f;
	}

	/**
	 * @return The rotation of the camera represented as a matrix.
	 */
	private VMatrix4f calculateCameraRotationMatrix() {
		VMatrix4f rotation = new VMatrix4f();
		rotation.rotate((float) Math.toRadians(-cam.getYaw()), new Vector3fF(0, 1, 0));
		rotation.rotate((float) Math.toRadians(-cam.getPitch()), new Vector3fF(1, 0, 0));
		return rotation;
	}

	/**
	 * Calculates the width and height of the near and far planes of the
	 * camera's view frustum. However, this doesn't have to use the "actual" far
	 * plane of the view frustum. It can use a shortened view frustum if desired
	 * by bringing the far-plane closer, which would increase shadow resolution
	 * but means that distant objects wouldn't cast shadows.
	 */
	private void calculateWidthsAndHeights() {
		farWidth = (float) (EngineSettings.SHADOW_DISTANCE * Math.tan(Math.toRadians(EngineSettings.FOV)));
		nearWidth = (float) (EngineSettings.NEAR_PLANE * Math.tan(Math.toRadians(EngineSettings.FOV)));
		farHeight = farWidth / getAspectRatio();
		nearHeight = nearWidth / getAspectRatio();
	}

	/**
	 * @return The aspect ratio of the display (width:height ratio).
	 */
	private float getAspectRatio() {
		return (float) Display.getWidth() / (float) Display.getHeight();
	}

}
