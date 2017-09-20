package renderer.viewCulling.frustum;


import object.camera.ICamera;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vec3f;

public class Frustum {

	private float[][] plane = new float[6][4];
	
	private ICamera camera;

	public Frustum extractFrustum(ICamera camera, Matrix4f projectionMatrix) {
		this.camera = camera;
		Matrix4f clip;
		float t;

		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		/* multiply matrix */
		clip = Matrix4f.mul(projectionMatrix, viewMatrix);
		/* RIGHT */
		/* find A,B,C,D on the RIGHT plane */
		this.plane[0][0] = clip.m[0][3] - clip.m[0][0];
		this.plane[0][1] = clip.m[1][3] - clip.m[1][0];
		this.plane[0][2] = clip.m[2][3] - clip.m[2][0];
		this.plane[0][3] = clip.m[3][3] - clip.m[3][0];

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[0][0]) + Maths.sqr(plane[0][1]) + Maths.sqr(plane[0][2]));
		plane[0][0] /= t;
		plane[0][1] /= t;
		plane[0][2] /= t;
		plane[0][3] /= t;

		/* LEFT */
		/* find A,B,C,D for the LEFT plane */
		this.plane[1][0] = clip.m[0][3] + clip.m[0][0];
		this.plane[1][1] = clip.m[1][3] + clip.m[1][0];
		this.plane[1][2] = clip.m[2][3] + clip.m[2][0];
		this.plane[1][3] = clip.m[3][3] + clip.m[3][0];

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[1][0]) + Maths.sqr(plane[1][1]) + Maths.sqr(plane[1][2]));
		plane[1][0] /= t;
		plane[1][1] /= t;
		plane[1][2] /= t;
		plane[1][3] /= t;
		
		/* BOTTOM */
		/* find A,B,C,D for the BOTTOM plane */
		this.plane[2][0] = clip.m[0][3] + clip.m[0][1];
		this.plane[2][1] = clip.m[1][3] + clip.m[1][1];
		this.plane[2][2] = clip.m[2][3] + clip.m[2][1];
		this.plane[2][3] = clip.m[3][3] + clip.m[3][1];

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[2][0]) + Maths.sqr(plane[2][1]) + Maths.sqr(plane[2][2]));
		plane[2][0] /= t;
		plane[2][1] /= t;
		plane[2][2] /= t;
		plane[2][3] /= t;
		
		/* TOP */
		/* find A,B,C,D for the TOP plane */
		this.plane[3][0] = clip.m[0][3] - clip.m[0][1];
		this.plane[3][1] = clip.m[1][3] - clip.m[1][1];
		this.plane[3][2] = clip.m[2][3] - clip.m[2][1];
		this.plane[3][3] = clip.m[3][3] - clip.m[3][1];

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[3][0]) + Maths.sqr(plane[3][1]) + Maths.sqr(plane[3][2]));
		plane[3][0] /= t;
		plane[3][1] /= t;
		plane[3][2] /= t;
		plane[3][3] /= t;
		
		/* BACK */
		/* find A,B,C,D for the BACK plane */
		this.plane[4][0] = clip.m[0][3] - clip.m[0][2];
		this.plane[4][1] = clip.m[1][3] - clip.m[1][2];
		this.plane[4][2] = clip.m[2][3] - clip.m[2][2];
		this.plane[4][3] = clip.m[3][3] - clip.m[3][2];

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[4][0]) + Maths.sqr(plane[4][1]) + Maths.sqr(plane[4][2]));
		plane[4][0] /= t;
		plane[4][1] /= t;
		plane[4][2] /= t;
		plane[4][3] /= t;
		
		/* FRONT */
		/* find A,B,C,D for the FRONT plane */
		this.plane[5][0] = clip.m[0][3] + clip.m[0][2];
		this.plane[5][1] = clip.m[1][3] + clip.m[1][2];
		this.plane[5][2] = clip.m[2][3] + clip.m[2][2];
		this.plane[5][3] = clip.m[3][3] + clip.m[3][2];

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[5][0]) + Maths.sqr(plane[5][1]) + Maths.sqr(plane[5][2]));
		plane[5][0] /= t;
		plane[5][1] /= t;
		plane[5][2] /= t;
		plane[5][3] /= t;
		
		return this;
	}

	public boolean pointInFrustum(Vec3f position) {
		boolean isInFrustum = true;
		for (int p = 0; p < 6; p++) {
			if (plane[p][0] * position.x + plane[p][1] * position.y + plane[p][2] * position.z + plane[p][3] <= 0) {
				isInFrustum = false;
				break;
			}
		}
		return isInFrustum;
	}

	public boolean sphereInFrustum(Vec3f position, float radius) {
		boolean isInFrustum = true;
		for (int p = 0; p < 6; p++) {
			if (plane[p][0] * position.x + plane[p][1] * position.y + plane[p][2] * position.z
					+ plane[p][3] <= -radius) {
				isInFrustum = false;
				break;
			}
		}
		return isInFrustum;
	}
	
	public boolean sphereInFrustumAndDsitance(Vec3f position, float radius, 
			float startClipDistance, float endClipDistance) {
		float distance = Maths.distance2Points(position, this.camera.getPosition());
		if(distance < startClipDistance || distance > endClipDistance) {
			return false;
		}
		for (int p = 0; p < 6; p++) {
			if (plane[p][0] * position.x + plane[p][1] * position.y + plane[p][2] * position.z
					+ plane[p][3] <= -radius) {
				return false;
			}
		}
		return true;
	}

	public float distanceSphereInFrustum(Vec3f position, float radius) {
		float distance = 0;
		for (int p = 0; p < 6; p++) {
			distance = plane[p][0] * position.x + plane[p][1] * position.y + plane[p][2] * position.z + plane[p][3];
			if (distance <= -radius) {
				return 0;
			}		
		}
		return distance + radius;
	}

}
