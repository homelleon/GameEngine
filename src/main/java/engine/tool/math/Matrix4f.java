package tool.math;

import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Vector4f;

import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;


public class Matrix4f {

	public float[][] m;
	
	public Matrix4f()
	{
		load(new float[4][4]);
		setIdentity();
	}
	
	public Matrix4f Zero()
	{
		m[0][0] = 0; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = 0; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 0; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 0;
	
		return this;
	}
	
	public Matrix4f setIdentity()
	{
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
	
		return this;
	}
	
	public Matrix4f Orthographic2D(int width, int height)
	{
		m[0][0] = 2f/(float)width; 	m[0][1] = 0; 			    m[0][2] = 0; m[0][3] = -1;
		m[1][0] = 0;		 		m[1][1] = 2f/(float)height; m[1][2] = 0; m[1][3] = -1;
		m[2][0] = 0; 				m[2][1] = 0; 				m[2][2] = 1; m[2][3] =  0;
		m[3][0] = 0; 				m[3][1] = 0; 				m[3][2] = 0; m[3][3] =  1;
		
		return this;
	}
	
	public Matrix4f translate(Vector3f translation)
	{
		m[3][0] += m[0][0] * translation.x + m[1][0] * translation.y + m[2][0] * translation.z;
		m[3][1] += m[0][1] * translation.x + m[1][1] * translation.y + m[2][1] * translation.z;
		m[3][2] += m[0][2] * translation.x + m[1][2] * translation.y + m[2][2] * translation.z;
		m[3][3] += m[0][3] * translation.x + m[1][3] * translation.y + m[2][3] * translation.z;
	
		return this;
	}
	
	public Matrix4f translate(Vector2f translation) {		
		m[3][0] += m[0][0] * translation.x + m[1][0] * translation.y;
		m[3][1] += m[0][1] * translation.x + m[1][1] * translation.y;
		m[3][2] += m[0][2] * translation.x + m[1][2] * translation.y;
		m[3][3] += m[0][3] * translation.x + m[1][3] * translation.y;
		
		return this;
	}
	
	public Vector4f transform(Vector4f plane) {
		float x = this.m[0][0] * plane.x + this.m[1][0] * plane.y + this.m[2][0] * plane.z + this.m[3][0] * plane.w;
		float y = this.m[0][1] * plane.x + this.m[1][1] * plane.y + this.m[2][1] * plane.z + this.m[3][1] * plane.w;
		float z = this.m[0][2] * plane.x + this.m[1][2] * plane.y + this.m[2][2] * plane.z + this.m[3][2] * plane.w;
		float w = this.m[0][3] * plane.x + this.m[1][3] * plane.y + this.m[2][3] * plane.z + this.m[3][3] * plane.w;
		
		return new Vector4f(x,y,z,w);
	}
	
	public static Vector4f transform(Matrix4f matrix, Vector4f plane) {
		float x = matrix.m[0][0] * plane.x + matrix.m[1][0] * plane.y + matrix.m[2][0] * plane.z + matrix.m[3][0] * plane.w;
		float y = matrix.m[0][1] * plane.x + matrix.m[1][1] * plane.y + matrix.m[2][1] * plane.z + matrix.m[3][1] * plane.w;
		float z = matrix.m[0][2] * plane.x + matrix.m[1][2] * plane.y + matrix.m[2][2] * plane.z + matrix.m[3][2] * plane.w;
		float w = matrix.m[0][3] * plane.x + matrix.m[1][3] * plane.y + matrix.m[2][3] * plane.z + matrix.m[3][3] * plane.w;
		
		return new Vector4f(x,y,z,w);
	}
	
	public Matrix4f rotate(Vector3f rotation)
	{
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		float x = (float)Math.toRadians(rotation.getX());
		float y = (float)Math.toRadians(rotation.getY());
		float z = (float)Math.toRadians(rotation.getZ());
		
		rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); 	 rz.m[0][2] = 0; 				   rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  	 rz.m[1][2] = 0; 				   rz.m[1][3] = 0;
		rz.m[2][0] = 0; 				 rz.m[2][1] = 0; 				   	 rz.m[2][2] = 1; 				   rz.m[2][3] = 0;
		rz.m[3][0] = 0; 				 rz.m[3][1] = 0; 				   	 rz.m[3][2] = 0; 				   rz.m[3][3] = 1;
		
		rx.m[0][0] = 1; 				 rx.m[0][1] = 0;					 rx.m[0][2] = 0; 				   rx.m[0][3] = 0;
		rx.m[1][0] = 0; 				 rx.m[1][1] = (float)Math.cos(x); 	 rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
		rx.m[2][0] = 0; 				 rx.m[2][1] = (float)Math.sin(x); 	 rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
		rx.m[3][0] = 0; 				 rx.m[3][1] = 0; 				 	 rx.m[3][2] = 0;				   rx.m[3][3] = 1;
		
		ry.m[0][0] = (float)Math.cos(y); ry.m[0][1] = 0; 					 ry.m[0][2] = (float)Math.sin(y);  ry.m[0][3] = 0;
		ry.m[1][0] = 0; 				 ry.m[1][1] = 1; 				 	 ry.m[1][2] = 0; 				   ry.m[1][3] = 0;
		ry.m[2][0] = -(float)Math.sin(y);ry.m[2][1] = 0;					 ry.m[2][2] = (float)Math.cos(y);  ry.m[2][3] = 0;
		ry.m[3][0] = 0; 				 ry.m[3][1] = 0; 					 ry.m[3][2] = 0; 				   ry.m[3][3] = 1;
	
		m =  rz.mul(ry.mul(rx)).getM();
		
		return this;
	}
	
	public Matrix4f rotate(float angle, Vector3f axis) {
		
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		float oneminusc = 1.0f - c;
		float xy = axis.x*axis.y;
		float yz = axis.y*axis.z;
		float xz = axis.x*axis.z;
		float xs = axis.x*s;
		float ys = axis.y*s;
		float zs = axis.z*s;

		float f00 = axis.x*axis.x*oneminusc+c;
		float f01 = xy*oneminusc+zs;
		float f02 = xz*oneminusc-ys;
		// n[3] not used
		float f10 = xy*oneminusc-zs;
		float f11 = axis.y*axis.y*oneminusc+c;
		float f12 = yz*oneminusc+xs;
		// n[7] not used
		float f20 = xz*oneminusc+ys;
		float f21 = yz*oneminusc-xs;
		float f22 = axis.z*axis.z*oneminusc+c;

		float t00 = this.m[0][0] * f00 + this.m[1][0] * f01 + this.m[2][0] * f02;
		float t01 = this.m[0][1] * f00 + this.m[1][1] * f01 + this.m[2][1] * f02;
		float t02 = this.m[0][2] * f00 + this.m[1][2] * f01 + this.m[2][2] * f02;
		float t03 = this.m[0][3] * f00 + this.m[1][3] * f01 + this.m[2][3] * f02;
		float t10 = this.m[0][0] * f10 + this.m[1][0] * f11 + this.m[2][0] * f12;
		float t11 = this.m[0][1] * f10 + this.m[1][1] * f11 + this.m[2][1] * f12;
		float t12 = this.m[0][2] * f10 + this.m[1][2] * f11 + this.m[2][2] * f12;
		float t13 = this.m[0][3] * f10 + this.m[1][3] * f11 + this.m[2][3] * f12;
		
		
		m[2][0] = this.m[0][0] * f20 + this.m[1][0] * f21 + this.m[2][0] * f22;
		m[2][1] = this.m[0][1] * f20 + this.m[1][1] * f21 + this.m[2][1] * f22;
		m[2][2] = this.m[0][2] * f20 + this.m[1][2] * f21 + this.m[2][2] * f22;
		m[2][3] = this.m[0][3] * f20 + this.m[1][3] * f21 + this.m[2][3] * f22;
		m[0][0] = t00;
		m[0][1] = t01;
		m[0][2] = t02;
		m[0][3] = t03;
		m[1][0] = t10;
		m[1][1] = t11;
		m[1][2] = t12;
		m[1][3] = t13;
		
		return this;
	}
	
	public Matrix4f scale(Vector3f scaling)
	{
		m[0][0] = m[0][0] * scaling.x;
		m[0][1] = m[0][1] * scaling.x;
		m[0][2] = m[0][2] * scaling.x;
		m[0][3] = m[0][3] * scaling.x;
		m[1][0] = m[1][0] * scaling.y;
		m[1][1] = m[1][1] * scaling.y;
		m[1][2] = m[1][2] * scaling.y;
		m[1][3] = m[1][3] * scaling.y;
		m[2][0] = m[2][0] * scaling.z;
		m[2][1] = m[2][1] * scaling.z;
		m[2][2] = m[2][2] * scaling.z;
		m[2][3] = m[2][3] * scaling.z;
		
		return this;
	}
	
	public Matrix4f OrthographicProjection(float l, float r, float b, float t, float n, float f){
		
		m[0][0] = 2.0f/(r-l); 	m[0][1] = 0; 			m[0][2] = 0; 			m[0][3] = -(r+l)/(r-l);
		m[1][0] = 0;			m[1][1] = 2.0f/(t-b); 	m[1][2] = 0; 			m[1][3] = -(t+b)/(t-b);
		m[2][0] = 0; 			m[2][1] = 0; 			m[2][2] = 2.0f/(f-n); 	m[2][3] = -(f+n)/(f-n);
		m[3][0] = 0; 			m[3][1] = 0; 			m[3][2] = 0; 			m[3][3] = 1;
	
		return this;
	}
	
	public Matrix4f PerspectiveProjection(float fovY, float width, float height, float zNear, float zFar)
	{
		float tanFOV = (float) Math.tan(Math.toRadians(fovY/2));
		float aspectRatio = width/height;
		
		m[0][0] = 1/(tanFOV*aspectRatio); m[0][1] = 0; 		 	   m[0][2] = 0; 				m[0][3] = 0;
		m[1][0] = 0; 					  m[1][1] = 1/tanFOV; 	   m[1][2] = 0; 			 	m[1][3] = 0;
		m[2][0] = 0; 				 	  m[2][1] = 0; 		 	   m[2][2] = zFar/(zFar-zNear);	m[2][3] = zFar*zNear /(zFar-zNear);
		m[3][0] = 0; 				 	  m[3][1] = 0; 		 	   m[3][2] = 1; 				m[3][3] = 1;
	
		return this;
	}
	
	public Matrix4f View(Vector3f forward, Vector3f up)
	{
		Vector3f f = forward;
		Vector3f u = up;
		Vector3f r = u.cross(f);
		
		m[0][0] = r.getX(); m[0][1] = r.getY(); m[0][2] = r.getZ(); m[0][3] = 0;
		m[1][0] = u.getX(); m[1][1] = u.getY(); m[1][2] = u.getZ(); m[1][3] = 0;
		m[2][0] = f.getX();	m[2][1] = f.getY(); m[2][2] = f.getZ(); m[2][3] = 0;
		m[3][0] = 0; 		m[3][1] = 0; 		m[3][2] = 0; 		m[3][3] = 1;
	
		return this;
	}
	
	
	public Matrix4f mul(Matrix4f r){
		
		return clone(mul(this, r));
	}
	
	public static Matrix4f mul(Matrix4f l, Matrix4f r){
		Matrix4f m = new Matrix4f();
		m.m[0][0] = l.m[0][0] * r.m[0][0] + l.m[1][0] * r.m[0][1] + l.m[2][0] * r.m[0][2] + l.m[3][0] * r.m[0][3];
		m.m[0][1] = l.m[0][1] * r.m[0][0] + l.m[1][1] * r.m[0][1] + l.m[2][1] * r.m[0][2] + l.m[3][1] * r.m[0][3];
		m.m[0][2] = l.m[0][2] * r.m[0][0] + l.m[1][2] * r.m[0][1] + l.m[2][2] * r.m[0][2] + l.m[3][2] * r.m[0][3];
		m.m[0][3] = l.m[0][3] * r.m[0][0] + l.m[1][3] * r.m[0][1] + l.m[2][3] * r.m[0][2] + l.m[3][3] * r.m[0][3];
		m.m[1][0] = l.m[0][0] * r.m[1][0] + l.m[1][0] * r.m[1][1] + l.m[2][0] * r.m[1][2] + l.m[3][0] * r.m[1][3];
		m.m[1][1] = l.m[0][1] * r.m[1][0] + l.m[1][1] * r.m[1][1] + l.m[2][1] * r.m[1][2] + l.m[3][1] * r.m[1][3];
		m.m[1][2] = l.m[0][2] * r.m[1][0] + l.m[1][2] * r.m[1][1] + l.m[2][2] * r.m[1][2] + l.m[3][2] * r.m[1][3];
		m.m[1][3] = l.m[0][3] * r.m[1][0] + l.m[1][3] * r.m[1][1] + l.m[2][3] * r.m[1][2] + l.m[3][3] * r.m[1][3];
		m.m[2][0] = l.m[0][0] * r.m[2][0] + l.m[1][0] * r.m[2][1] + l.m[2][0] * r.m[2][2] + l.m[3][0] * r.m[2][3];
		m.m[2][1] = l.m[0][1] * r.m[2][0] + l.m[1][1] * r.m[2][1] + l.m[2][1] * r.m[2][2] + l.m[3][1] * r.m[2][3];
		m.m[2][2] = l.m[0][2] * r.m[2][0] + l.m[1][2] * r.m[2][1] + l.m[2][2] * r.m[2][2] + l.m[3][2] * r.m[2][3];
		m.m[2][3] = l.m[0][3] * r.m[2][0] + l.m[1][3] * r.m[2][1] + l.m[2][3] * r.m[2][2] + l.m[3][3] * r.m[2][3];
		m.m[3][0] = l.m[0][0] * r.m[3][0] + l.m[1][0] * r.m[3][1] + l.m[2][0] * r.m[3][2] + l.m[3][0] * r.m[3][3];
		m.m[3][1] = l.m[0][1] * r.m[3][0] + l.m[1][1] * r.m[3][1] + l.m[2][1] * r.m[3][2] + l.m[3][1] * r.m[3][3];
		m.m[3][2] = l.m[0][2] * r.m[3][0] + l.m[1][2] * r.m[3][1] + l.m[2][2] * r.m[3][2] + l.m[3][2] * r.m[3][3];
		m.m[3][3] = l.m[0][3] * r.m[3][0] + l.m[1][3] * r.m[3][1] + l.m[2][3] * r.m[3][2] + l.m[3][3] * r.m[3][3];

		return m;
	}
	
	public Quaternion mul(Quaternion v)
	{
		Quaternion res = new Quaternion(0,0,0,0);
		
		res.setX(m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3] * v.getW());
		res.setY(m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3] * v.getW());
		res.setZ(m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3] * v.getW());
		res.setW(m[3][0] * v.getX() + m[3][1] * v.getY() + m[3][2] * v.getZ() + m[3][3] * v.getW());
		
		return res;
	}
	
	public Matrix4f transpose()
	{
		Matrix4f result = new Matrix4f();
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				result.set(i, j, get(j,i));
			}
		}
		return result;
	}
	public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
		float determinant = src.determinant();

		if (determinant != 0) {
			/*
			 * m00 m01 m02 m03
			 * m10 m11 m12 m13
			 * m20 m21 m22 m23
			 * m30 m31 m32 m33
			 */
			if (dest == null)
				dest = new Matrix4f();
			float determinant_inv = 1f/determinant;

			// first row
			float t00 =  determinant3x3(src.m[1][1], src.m[1][2], src.m[1][3], src.m[2][1], src.m[2][2], src.m[2][3], src.m[3][1], src.m[3][2], src.m[3][3]);
			float t01 = -determinant3x3(src.m[1][0], src.m[1][2], src.m[1][3], src.m[2][0], src.m[2][2], src.m[2][3], src.m[3][0], src.m[3][2], src.m[3][3]);
			float t02 =  determinant3x3(src.m[1][0], src.m[1][1], src.m[1][3], src.m[2][0], src.m[2][1], src.m[2][3], src.m[3][0], src.m[3][1], src.m[3][3]);
			float t03 = -determinant3x3(src.m[1][0], src.m[1][1], src.m[1][2], src.m[2][0], src.m[2][1], src.m[2][2], src.m[3][0], src.m[3][1], src.m[3][2]);
			// second row
			float t10 = -determinant3x3(src.m[0][1], src.m[0][2], src.m[0][3], src.m[2][1], src.m[2][2], src.m[2][3], src.m[3][1], src.m[3][2], src.m[3][3]);
			float t11 =  determinant3x3(src.m[0][0], src.m[0][2], src.m[0][3], src.m[2][0], src.m[2][2], src.m[2][3], src.m[3][0], src.m[3][2], src.m[3][3]);
			float t12 = -determinant3x3(src.m[0][0], src.m[0][1], src.m[0][3], src.m[2][0], src.m[2][1], src.m[2][3], src.m[3][0], src.m[3][1], src.m[3][3]);
			float t13 =  determinant3x3(src.m[0][0], src.m[0][1], src.m[0][2], src.m[2][0], src.m[2][1], src.m[2][2], src.m[3][0], src.m[3][1], src.m[3][2]);
			// third row
			float t20 =  determinant3x3(src.m[0][1], src.m[0][2], src.m[0][3], src.m[1][1], src.m[1][2], src.m[1][3], src.m[3][1], src.m[3][2], src.m[3][3]);
			float t21 = -determinant3x3(src.m[0][0], src.m[0][2], src.m[0][3], src.m[1][0], src.m[1][2], src.m[1][3], src.m[3][0], src.m[3][2], src.m[3][3]);
			float t22 =  determinant3x3(src.m[0][0], src.m[0][1], src.m[0][3], src.m[1][0], src.m[1][1], src.m[1][3], src.m[3][0], src.m[3][1], src.m[3][3]);
			float t23 = -determinant3x3(src.m[0][0], src.m[0][1], src.m[0][2], src.m[1][0], src.m[1][1], src.m[1][2], src.m[3][0], src.m[3][1], src.m[3][2]);
			// fourth row
			float t30 = -determinant3x3(src.m[0][1], src.m[0][2], src.m[0][3], src.m[1][1], src.m[1][2], src.m[1][3], src.m[2][1], src.m[2][2], src.m[2][3]);
			float t31 =  determinant3x3(src.m[0][0], src.m[0][2], src.m[0][3], src.m[1][0], src.m[1][2], src.m[1][3], src.m[2][0], src.m[2][2], src.m[2][3]);
			float t32 = -determinant3x3(src.m[0][0], src.m[0][1], src.m[0][3], src.m[1][0], src.m[1][1], src.m[1][3], src.m[2][0], src.m[2][1], src.m[2][3]);
			float t33 =  determinant3x3(src.m[0][0], src.m[0][1], src.m[0][2], src.m[1][0], src.m[1][1], src.m[1][2], src.m[2][0], src.m[2][1], src.m[2][2]);

			// transpose and divide by the determinant
			dest.m[0][0] = t00*determinant_inv;
			dest.m[1][1] = t11*determinant_inv;
			dest.m[2][2] = t22*determinant_inv;
			dest.m[3][3] = t33*determinant_inv;
			dest.m[0][1] = t10*determinant_inv;
			dest.m[1][0] = t01*determinant_inv;
			dest.m[2][0] = t02*determinant_inv;
			dest.m[0][2] = t20*determinant_inv;
			dest.m[1][2] = t21*determinant_inv;
			dest.m[2][1] = t12*determinant_inv;
			dest.m[0][3] = t30*determinant_inv;
			dest.m[3][0] = t03*determinant_inv;
			dest.m[1][3] = t31*determinant_inv;
			dest.m[3][1] = t13*determinant_inv;
			dest.m[3][2] = t23*determinant_inv;
			dest.m[2][3] = t32*determinant_inv;
			return dest;
		} else
			return null;
	}
	
	private static float determinant3x3(float t00, float t01, float t02,
		     float t10, float t11, float t12,
		     float t20, float t21, float t22)
	{
		return   t00 * (t11 * t22 - t12 * t21)
		      + t01 * (t12 * t20 - t10 * t22)
		      + t02 * (t10 * t21 - t11 * t20);
	}
	
	public float determinant() {
		float f =
			m[0][0]
				* ((m[1][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][1] + m[1][3] * m[2][1] * m[3][2])
					- m[1][3] * m[2][2] * m[3][1]
					- m[1][1] * m[2][3] * m[3][2]
					- m[1][2] * m[2][1] * m[3][3]);
		f -= m[0][1]
			* ((m[1][0] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][0] + m[1][3] * m[2][0] * m[3][2])
				- m[1][3] * m[2][2] * m[3][0]
				- m[1][0] * m[2][3] * m[3][2]
				- m[1][2] * m[2][0] * m[3][3]);
		f += m[0][2]
			* ((m[1][0] * m[2][1] * m[3][3] + m[1][1] * m[2][3] * m[3][0] + m[1][3] * m[2][0] * m[3][1])
				- m[1][3] * m[2][1] * m[3][0]
				- m[1][0] * m[2][3] * m[3][1]
				- m[1][1] * m[2][0] * m[3][3]);
		f -= m[0][3]
			* ((m[1][0] * m[2][1] * m[3][2] + m[1][1] * m[2][2] * m[3][0] + m[1][2] * m[2][0] * m[3][1])
				- m[1][2] * m[2][1] * m[3][0]
				- m[1][0] * m[2][2] * m[3][1]
				- m[1][1] * m[2][0] * m[3][2]);
		return f;
	}
	
	public static Matrix4f invert(Matrix4f r)
	{
		float s0 = r.get(0, 0) * r.get(1, 1) - r.get(1, 0) * r.get(0, 1);
		float s1 = r.get(0, 0) * r.get(1, 2) - r.get(1, 0) * r.get(0, 2);
		float s2 = r.get(0, 0) * r.get(1, 3) - r.get(1, 0) * r.get(0, 3);
		float s3 = r.get(0, 1) * r.get(1, 2) - r.get(1, 1) * r.get(0, 2);
		float s4 = r.get(0, 1) * r.get(1, 3) - r.get(1, 1) * r.get(0, 3);
		float s5 = r.get(0, 2) * r.get(1, 3) - r.get(1, 2) * r.get(0, 3);

		float c5 = r.get(2, 2) * r.get(3, 3) - r.get(3, 2) * r.get(2, 3);
		float c4 = r.get(2, 1) * r.get(3, 3) - r.get(3, 1) * r.get(2, 3);
		float c3 = r.get(2, 1) * r.get(3, 2) - r.get(3, 1) * r.get(2, 2);
		float c2 = r.get(2, 0) * r.get(3, 3) - r.get(3, 0) * r.get(2, 3);
		float c1 = r.get(2, 0) * r.get(3, 2) - r.get(3, 0) * r.get(2, 2);
		float c0 = r.get(2, 0) * r.get(3, 1) - r.get(3, 0) * r.get(2, 1);
		
		
		float div = (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
		if (div == 0) return null;
		
	    float invdet = 1.0f / div;
	    
	    Matrix4f invM = new Matrix4f();
	    
	    invM.set(0, 0, (r.get(1, 1) * c5 - r.get(1, 2) * c4 + r.get(1, 3) * c3) * invdet);
	    invM.set(0, 1, (-r.get(0, 1) * c5 + r.get(0, 2) * c4 - r.get(0, 3) * c3) * invdet);
	    invM.set(0, 2, (r.get(3, 1) * s5 - r.get(3, 2) * s4 + r.get(3, 3) * s3) * invdet);
	    invM.set(0, 3, (-r.get(2, 1) * s5 + r.get(2, 2) * s4 - r.get(2, 3) * s3) * invdet);

	    invM.set(1, 0, (-r.get(1, 0) * c5 + r.get(1, 2) * c2 - r.get(1, 3) * c1) * invdet);
	    invM.set(1, 1, (r.get(0, 0) * c5 - r.get(0, 2) * c2 + r.get(0, 3) * c1) * invdet);
	    invM.set(1, 2, (-r.get(3, 0) * s5 + r.get(3, 2) * s2 - r.get(3, 3) * s1) * invdet);
	    invM.set(1, 3, (r.get(2, 0) * s5 - r.get(2, 2) * s2 + r.get(2, 3) * s1) * invdet);

	    invM.set(2, 0, (r.get(1, 0) * c4 - r.get(1, 1) * c2 + r.get(1, 3) * c0) * invdet);
	    invM.set(2, 1, (-r.get(0, 0) * c4 + r.get(0, 1) * c2 - r.get(0, 3) * c0) * invdet);
	    invM.set(2, 2, (r.get(3, 0) * s4 - r.get(3, 1) * s2 + r.get(3, 3) * s0) * invdet);
	    invM.set(2, 3, (-r.get(2, 0) * s4 + r.get(2, 1) * s2 - r.get(2, 3) * s0) * invdet);

	    invM.set(3, 0, (-r.get(1, 0) * c3 + r.get(1, 1) * c1 - r.get(1, 2) * c0) * invdet);
	    invM.set(3, 1, (r.get(0, 0) * c3 - r.get(0, 1) * c1 + r.get(0, 2) * c0) * invdet);
	    invM.set(3, 2, (-r.get(3, 0) * s3 + r.get(3, 1) * s1 - r.get(3, 2) * s0) * invdet);
	    invM.set(3, 3, (r.get(2, 0) * s3 - r.get(2, 1) * s1 + r.get(2, 2) * s0) * invdet);
		
		return invM;
	}
	
	public Matrix4f invert()
	{
		float s0 = get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
		float s1 = get(0, 0) * get(1, 2) - get(1, 0) * get(0, 2);
		float s2 = get(0, 0) * get(1, 3) - get(1, 0) * get(0, 3);
		float s3 = get(0, 1) * get(1, 2) - get(1, 1) * get(0, 2);
		float s4 = get(0, 1) * get(1, 3) - get(1, 1) * get(0, 3);
		float s5 = get(0, 2) * get(1, 3) - get(1, 2) * get(0, 3);

		float c5 = get(2, 2) * get(3, 3) - get(3, 2) * get(2, 3);
		float c4 = get(2, 1) * get(3, 3) - get(3, 1) * get(2, 3);
		float c3 = get(2, 1) * get(3, 2) - get(3, 1) * get(2, 2);
		float c2 = get(2, 0) * get(3, 3) - get(3, 0) * get(2, 3);
		float c1 = get(2, 0) * get(3, 2) - get(3, 0) * get(2, 2);
		float c0 = get(2, 0) * get(3, 1) - get(3, 0) * get(2, 1);
		
		
		float div = (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
		if (div == 0) return null;
		
	    float invdet = 1.0f / div;
	    
	    Matrix4f invM = new Matrix4f();
	    
	    invM.set(0, 0, (get(1, 1) * c5 - get(1, 2) * c4 + get(1, 3) * c3) * invdet);
	    invM.set(0, 1, (-get(0, 1) * c5 + get(0, 2) * c4 - get(0, 3) * c3) * invdet);
	    invM.set(0, 2, (get(3, 1) * s5 - get(3, 2) * s4 + get(3, 3) * s3) * invdet);
	    invM.set(0, 3, (-get(2, 1) * s5 + get(2, 2) * s4 - get(2, 3) * s3) * invdet);

	    invM.set(1, 0, (-get(1, 0) * c5 + get(1, 2) * c2 - get(1, 3) * c1) * invdet);
	    invM.set(1, 1, (get(0, 0) * c5 - get(0, 2) * c2 + get(0, 3) * c1) * invdet);
	    invM.set(1, 2, (-get(3, 0) * s5 + get(3, 2) * s2 - get(3, 3) * s1) * invdet);
	    invM.set(1, 3, (get(2, 0) * s5 - get(2, 2) * s2 + get(2, 3) * s1) * invdet);

	    invM.set(2, 0, (get(1, 0) * c4 - get(1, 1) * c2 + get(1, 3) * c0) * invdet);
	    invM.set(2, 1, (-get(0, 0) * c4 + get(0, 1) * c2 - get(0, 3) * c0) * invdet);
	    invM.set(2, 2, (get(3, 0) * s4 - get(3, 1) * s2 + get(3, 3) * s0) * invdet);
	    invM.set(2, 3, (-get(2, 0) * s4 + get(2, 1) * s2 - get(2, 3) * s0) * invdet);

	    invM.set(3, 0, (-get(1, 0) * c3 + get(1, 1) * c1 - get(1, 2) * c0) * invdet);
	    invM.set(3, 1, (get(0, 0) * c3 - get(0, 1) * c1 + get(0, 2) * c0) * invdet);
	    invM.set(3, 2, (-get(3, 0) * s3 + get(3, 1) * s1 - get(3, 2) * s0) * invdet);
	    invM.set(3, 3, (get(2, 0) * s3 - get(2, 1) * s1 + get(2, 2) * s0) * invdet);
		
		return invM;
	}
	
	public boolean equals(Matrix4f m){
		if (this.m[0][0] == m.getM()[0][0] && this.m[0][1] == m.getM()[0][1] &&
			this.m[0][2] == m.getM()[0][2] && this.m[0][3] == m.getM()[0][3] &&
			this.m[1][0] == m.getM()[1][0] && this.m[1][1] == m.getM()[1][1] &&
			this.m[1][2] == m.getM()[1][2] && this.m[1][3] == m.getM()[1][3] &&
			this.m[2][0] == m.getM()[2][0] && this.m[2][1] == m.getM()[2][1] &&
			this.m[2][2] == m.getM()[2][2] && this.m[2][3] == m.getM()[2][3] &&
			this.m[3][0] == m.getM()[3][0] && this.m[3][1] == m.getM()[3][1] &&
			this.m[3][2] == m.getM()[3][2] && this.m[3][3] == m.getM()[3][3])
				return true;
		else
			return false;	
	}
	
	public void set(int x, int y, float value)
	{
		this.m[x][y] = value;
	}
	
	public float get(int x, int y)
	{
		return  this.m[x][y];
	}

	public float [][] getM() {
		return m;
	}
	
	/**
	 * Load from a float buffer. The buffer stores the matrix in column major
	 * (OpenGL) order.
	 *
	 * @param buf A float buffer to read from
	 * @return this
	 */
	public Matrix4f load(FloatBuffer buf) {

		m[0][0] = buf.get();
		m[0][1] = buf.get();
		m[0][2] = buf.get();
		m[0][3] = buf.get();
		m[1][0] = buf.get();
		m[1][1] = buf.get();
		m[1][2] = buf.get();
		m[1][3] = buf.get();
		m[2][0] = buf.get();
		m[2][1] = buf.get();
		m[2][2] = buf.get();
		m[2][3] = buf.get();
		m[3][0] = buf.get();
		m[3][1] = buf.get();
		m[3][2] = buf.get();
		m[3][3] = buf.get();

		return this;
	}
	
	public void load(float [][] m) {
		this.m = m;
	}
	
	/**
	 * Store this matrix in a float buffer. The matrix is stored in column
	 * major (openGL) order.
	 * @param buf The buffer to store this matrix in
	 */
	public Matrix4f store(FloatBuffer buf) {
		buf.put(m[0][0]);
		buf.put(m[0][1]);
		buf.put(m[0][2]);
		buf.put(m[0][3]);
		buf.put(m[1][0]);
		buf.put(m[1][1]);
		buf.put(m[1][2]);
		buf.put(m[1][3]);
		buf.put(m[2][0]);
		buf.put(m[2][1]);
		buf.put(m[2][2]);
		buf.put(m[2][3]);
		buf.put(m[3][0]);
		buf.put(m[3][1]);
		buf.put(m[3][2]);
		buf.put(m[3][3]);
		
		return this;
	}
	
	public String toString() {
		
		return 	"|" + m[0][0] + " " + m[0][1] + " " + m[0][2] + " " + m[0][3] + "|\n" +
				"|" + m[1][0] + " " + m[1][1] + " " + m[1][2] + " " + m[1][3] + "|\n" +
				"|" + m[2][0] + " " + m[2][1] + " " + m[2][2] + " " + m[2][3] + "|\n" +
				"|" + m[3][0] + " " + m[3][1] + " " + m[3][2] + " " + m[3][3] + "|";
	}
	
	public Matrix4f clone(Matrix4f m) {
		this.m[0][0] = m.m[0][0];
		this.m[0][1] = m.m[0][1];
		this.m[0][2] = m.m[0][2];
		this.m[0][3] = m.m[0][3];
		this.m[1][0] = m.m[1][0];
		this.m[1][1] = m.m[1][1];
		this.m[1][2] = m.m[1][2];
		this.m[1][3] = m.m[1][3];
		this.m[2][0] = m.m[2][0];
		this.m[2][1] = m.m[2][1];
		this.m[2][2] = m.m[2][2];
		this.m[2][3] = m.m[2][3];
		this.m[3][0] = m.m[3][0];
		this.m[3][1] = m.m[3][1];
		this.m[3][2] = m.m[3][2];
		this.m[3][3] = m.m[3][3];
		
		return this;
	}
}
