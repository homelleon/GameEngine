package toolbox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class OGLUtils {
	
	public static void depthTest(boolean value) {
		if(value) {
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		} else {
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		}
	}
	
	public static void cullBackFaces(boolean value) {
		if(value) {
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		} else {
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
	}
	
	public static void doWiredFrame(boolean value) {
		if(value) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		} else {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}
	
	public static void antialiasing(boolean value) {
		if(value) {
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} else {
			GL11.glDisable(GL13.GL_MULTISAMPLE);
		}
	}

}
