package object.shadow;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

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
	private Matrix4f lightViewMatrix;
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
	public ShadowBox(Matrix4f lightViewMatrix, ICamera camera) {
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
		Matrix4f rotation = calculateCameraRotationMatrix();
		Vector3f forwardVec = new Vector3f(Matrix4f.transform(rotation, FORWARD));

		Vector3f toFar = new Vector3f(forwardVec);
		toFar.scale(EngineSettings.SHADOW_DISTANCE);
		Vector3f toNear = new Vector3f(forwardVec);
		toNear.scale(EngineSettings.NEAR_PLANE);
		Vector3f centerNear = Vector3f.add(toNear, cam.getPosition());
		Vector3f centerFar = Vector3f.add(toFar, cam.getPosition());

		Vector4f[] points = calculateFrustumVertices(rotation, forwardVec, centerNear, centerFar);

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
	public Vector3f getCenter() {
		float x = (minX + maxX) / 2f;
		float y = (minY + maxY) / 2f;
		float z = (minZ + maxZ) / 2f;
		Vector4f cen = new Vector4f(x, y, z, 1);
		Matrix4f invertedLight = new Matrix4f();
		Matrix4f.invert(lightViewMatrix, invertedLight);
		return new Vector3f(Matrix4f.transform(invertedLight, cen));
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
	 * @param forwardVec
	 *            - the direction that the camera is aiming, and thus the
	 *            direction of the frustum.
	 * @param centerNear
	 *            - the center point of the frustum's near plane.
	 * @param centerFar
	 *            - the center point of the frustum's (possibly adjusted) far
	 *            plane.¸
	 * @return The positions of the vertices of the frustum in light space.
	 */
	private Vector4f[] calculateFrustumVertices(Matrix4f rotation, Vector3f forwardVec, Vector3f centerNear,
			Vector3f centerFar) {
		Vector3f upVec = new Vector3f(Matrix4f.transform(rotation, UP));
		Vector3f rightVec = Vector3f.cross(forwardVec, upVec);
		Vector3f downVec = new Vector3f(-upVec.x, -upVec.y, -upVec.z);
		Vector3f leftVec = new Vector3f(-rightVec.x, -rightVec.y, -rightVec.z);
		Vector3f farTop = Vector3f.add(centerFar,
				new Vector3f(upVec.x * farHeight, upVec.y * farHeight, upVec.z * farHeight));
		Vector3f farBottom = Vector3f.add(centerFar,
				new Vector3f(downVec.x * farHeight, downVec.y * farHeight, downVec.z * farHeight));
		Vector3f nearTop = Vector3f.add(centerNear,
				new Vector3f(upVec.x * nearHeight, upVec.y * nearHeight, upVec.z * nearHeight));
		Vector3f nearBottom = Vector3f.add(centerNear,
				new Vector3f(downVec.x * nearHeight, downVec.y * nearHeight, downVec.z * nearHeight));
		Vector4f[] points = new Vector4f[8];
		points[0] = calculateLightSpaceFrustumCorner(farTop, rightVec, farWidth);
		points[1] = calculateLightSpaceFrustumCorner(farTop, leftVec, farWidth);
		points[2] = calculateLightSpaceFrustumCorner(farBottom, rightVec, farWidth);
		points[3] = calculateLightSpaceFrustumCorner(farBottom, leftVec, farWidth);
		points[4] = calculateLightSpaceFrustumCorner(nearTop, rightVec, nearWidth);
		points[5] = calculateLightSpaceFrustumCorner(nearTop, leftVec, nearWidth);
		points[6] = calculateLightSpaceFrustumCorner(nearBottom, rightVec, nearWidth);
		points[7] = calculateLightSpaceFrustumCorner(nearBottom, leftVec, nearWidth);
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
	private Vector4f calculateLightSpaceFrustumCorner(Vector3f startPoint, Vector3f direction, float width) {
		Vector3f point = Vector3f.add(startPoint,
				new Vector3f(direction.x * width, direction.y * width, direction.z * width));
		Vector4f point4f = new Vector4f(point.x, point.y, point.z, 1f);
		point4f = Matrix4f.transform(lightViewMatrix, point4f);
		return point4f;
	}

	/**
	 * @return The rotation of the camera represented as a matrix.
	 */
	private Matrix4f calculateCameraRotationMatrix() {
		Matrix4f rotation = new Matrix4f();
		rotation.rotate((float) Math.toRadians(-cam.getYaw()), new Vector3f(0, 1, 0));
		rotation.rotate((float) Math.toRadians(-cam.getPitch()), new Vector3f(1, 0, 0));
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
