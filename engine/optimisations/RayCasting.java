package optimisations;

import java.util.Collection;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Entity;
import scene.ES;
import toolbox.Maths;

public class RayCasting {
	
	private static final float OFFSET = 50f;
	
	private Vector3f currentRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;
	
	public RayCasting(Camera cam, Matrix4f projection) {
		this.camera = cam;
		this.projectionMatrix = projection;
		this.viewMatrix = Maths.createViewMatrix(camera);
	}
	
	public void checkEntities(Collection<Entity> entities) {
		for(Entity entity : entities) {
			if(cast(entity.getPosition())) {
				entity.setRendered(false);
			} else {
				entity.setRendered(true);
			}
		}
	}
	
	public boolean cast(Vector3f position) {
		boolean isIntersects = false;
		viewMatrix = Maths.createViewMatrix(camera);
		currentRay = calculateRay();
		float error = 5; 
		for(int i = 0; i < 5; )
		if(Maths.pointIsOnRay(position, currentRay)) {
			isIntersects = true;
		}
		return isIntersects;
	}
	
	private Vector3f calculateRay() {
		int width = ES.DISPLAY_WIDTH;
		int height = ES.DISPLAY_HEIGHT;
		Vector3f worldRay = new Vector3f(0,0,0);
		for(int i = 0; i < 2; i++) {
			float x = Mouse.getX();
			float y = Mouse.getY();
//			int x = width / 2;
//			int y = height / 2;
			Vector2f normalizedCoords = getNormalizedDeviceCoords(x, y);
			Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
			Vector4f eyeCoords = toEyeCoords(clipCoords);
			worldRay = toWorldCoords(eyeCoords);
		}
		return worldRay;		
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

}
