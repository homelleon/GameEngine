package renderEngine;

import org.lwjgl.util.vector.Vector4f;

import scene.Scene;

public interface MasterRenderer {
	
	/** Rendering  all objects of scene.
	 * @param Scene scene - scene name, 
	 * @param Vector4f clipPlane - clipping plane,
	 * @param boolean isLowDistance - if true uses low quality scene*/
	public void renderScene(Scene scene, Vector4f clipPlane, boolean isLowDistance);
	public void renderScene(Scene scene, Vector4f clipPlane);
	public void renderShadowMap(Scene scene);
	public void cleanUp();
	public EntityRenderer getEntityRenderer();
	public void setEntityWiredFrame(boolean entitiyWiredFrame);
	public void setTerrainWiredFrame(boolean terrainWiredFrame);
}
