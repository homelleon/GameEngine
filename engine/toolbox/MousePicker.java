package toolbox;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Entity;
import renderEngine.MasterRenderer;
import scene.ES;
import scene.Scene;

public class MousePicker {
	
	private Vector3f currentRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;
	
	public MousePicker(Camera cam, Matrix4f projection) {
		this.camera = cam;
		this.projectionMatrix = projection;
		this.viewMatrix = Maths.createViewMatrix(camera);
	}
	
	public Vector3f getCurrentRay() {
		return currentRay;
	}
	
	public void update() {
		viewMatrix = Maths.createViewMatrix(camera);
		currentRay = calculateMouseRay();
	}
	
	private Vector3f calculateMouseRay() {
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();	
		Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;		
	}
	
	/* intersection with sphere */
	public boolean intersects(Vector3f position, float radius) {
		boolean isIntersects = false;
		Vector3f l = new Vector3f(position.x - camera.getPosition().x, 
				position.y - camera.getPosition().y, 
				position.z - camera.getPosition().z);
		Vector3f currRay = getCurrentRay(); 
		float d = Vector3f.dot(l, currRay);
		float lSq = Vector3f.dot(l, l);
	    if ( d < 0 && lSq > radius ) {
	    	return false;
	    } else {
		    float mSq = lSq - d*d;
	    	isIntersects = (mSq <= Maths.sqr(radius));
	    }		
		return isIntersects;
	}
	
	/* intersection with box */
	public boolean intersects(Vector3f min, Vector3f max) {
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
		if((tmin < tmax) && (tmax > 0)) {
			isIntersects = true; 
		}
		
		return isIntersects;
	}
	
	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}
	
	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
		float x = (2f*mouseX) / Display.getWidth() - 1;
		float y = (2f*mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}
	
	public Entity chooseObjectByRay(Scene scene) {
		Entity pickedEntity = null;
		List<Entity> pointedEntities = new ArrayList<Entity>();
		for(List<Entity> frustumList : scene.getEntities().getFromFrustum().values()) {
			for (Entity entity : frustumList) {
				if (intersects(entity.getPosition(), entity.getSphereRadius())) {
					Vector3f min = entity.getModel().getRawModel().getBBox().getMin();
					Vector3f max = entity.getModel().getRawModel().getBBox().getMax();
					Vector3f position = entity.getPosition();
					float scale = entity.getScale();
					if (intersects(min, max)) {
						pointedEntities.add(entity);
					}
				}
			
			}			
		}
		float distance = ES.RENDERING_VIEW_DISTANCE + 1;
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
