package renderer.viewCulling.frustum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import tool.math.Maths;

public class Frustum {

	private float[][] plane = new float[6][4];

	public void extractFrustum(ICamera camera, Matrix4f projectionMatrix) {
		Matrix4f clip;
		float t;

		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		/* multiply matrix */
		clip = Matrix4f.mul(projectionMatrix, viewMatrix, null);
		/* RIGHT */
		/* find A,B,C,D on the RIGHT plane */
		this.plane[0][0] = clip.m03 - clip.m00;
		this.plane[0][1] = clip.m13 - clip.m10;
		this.plane[0][2] = clip.m23 - clip.m20;
		this.plane[0][3] = clip.m33 - clip.m30;

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[0][0]) + Maths.sqr(plane[0][1]) + Maths.sqr(plane[0][2]));
		plane[0][0] /= t;
		plane[0][1] /= t;
		plane[0][2] /= t;
		plane[0][3] /= t;

		/* LEFT */
		/* find A,B,C,D for the LEFT plane */
		this.plane[1][0] = clip.m03 + clip.m00;
		this.plane[1][1] = clip.m13 + clip.m10;
		this.plane[1][2] = clip.m23 + clip.m20;
		this.plane[1][3] = clip.m33 + clip.m30;

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[1][0]) + Maths.sqr(plane[1][1]) + Maths.sqr(plane[1][2]));
		plane[1][0] /= t;
		plane[1][1] /= t;
		plane[1][2] /= t;
		plane[1][3] /= t;
		
		/* BOTTOM */
		/* find A,B,C,D for the BOTTOM plane */
		this.plane[2][0] = clip.m03 + clip.m01;
		this.plane[2][1] = clip.m13 + clip.m11;
		this.plane[2][2] = clip.m23 + clip.m21;
		this.plane[2][3] = clip.m33 + clip.m31;

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[2][0]) + Maths.sqr(plane[2][1]) + Maths.sqr(plane[2][2]));
		plane[2][0] /= t;
		plane[2][1] /= t;
		plane[2][2] /= t;
		plane[2][3] /= t;
		
		/* TOP */
		/* find A,B,C,D for the TOP plane */
		this.plane[3][0] = clip.m03 - clip.m01;
		this.plane[3][1] = clip.m13 - clip.m11;
		this.plane[3][2] = clip.m23 - clip.m21;
		this.plane[3][3] = clip.m33 - clip.m31;

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[3][0]) + Maths.sqr(plane[3][1]) + Maths.sqr(plane[3][2]));
		plane[3][0] /= t;
		plane[3][1] /= t;
		plane[3][2] /= t;
		plane[3][3] /= t;
		
		/* BACK */
		/* find A,B,C,D for the BACK plane */
		this.plane[4][0] = clip.m03 - clip.m02;
		this.plane[4][1] = clip.m13 - clip.m12;
		this.plane[4][2] = clip.m23 - clip.m22;
		this.plane[4][3] = clip.m33 - clip.m32;

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[4][0]) + Maths.sqr(plane[4][1]) + Maths.sqr(plane[4][2]));
		plane[4][0] /= t;
		plane[4][1] /= t;
		plane[4][2] /= t;
		plane[4][3] /= t;
		
		/* FRONT */
		/* find A,B,C,D for the FRONT plane */
		this.plane[5][0] = clip.m03 + clip.m02;
		this.plane[5][1] = clip.m13 + clip.m12;
		this.plane[5][2] = clip.m23 + clip.m22;
		this.plane[5][3] = clip.m33 + clip.m32;

		/* normalize equation of plane */
		t = (float) Math.sqrt(Maths.sqr(plane[5][0]) + Maths.sqr(plane[5][1]) + Maths.sqr(plane[5][2]));
		plane[5][0] /= t;
		plane[5][1] /= t;
		plane[5][2] /= t;
		plane[5][3] /= t;
	}

	public boolean pointInFrustum(Vector3f position) {
		boolean isInFrustum = true;
		for (int p = 0; p < 6; p++) {
			if (plane[p][0] * position.x + plane[p][1] * position.y + plane[p][2] * position.z + plane[p][3] <= 0) {
				isInFrustum = false;
				break;
			}
		}
		return isInFrustum;
	}

	public boolean sphereInFrustum(Vector3f position, float radius) {
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

	public float distanceSphereInFrustum(Vector3f position, float radius) {
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
