package renderEngine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Entity;
import lights.Light;
import models.TexturedModel;
import scene.Scene;
import terrains.Terrain;
import viewCulling.Frustum;

/**
 * Main interface of render Engine that contatins scene, environment and shadow
 * rendering system to control them and send scene objects in it. 
 * 
 * @author homelleon
 * @version 1.0
 * @see MasterRendererSimple
 *
 */
public interface MasterRenderer {
	
	/** Rendering  all objects of scene.
	 * <p>Can define if scene is rendering using low or hight quality objects
	 * by 'isLowDistance' parameter.
	 * 
	 * @param scene 
	 * 						{@link Scene} value 
	 * @param clipPlane 
	 * 						{@link Vector4f} value of clipping plane matrix
	 * @param isLowDistance 
	 * 						{@link Boolean} value to define if low quality scene need
	 * 						to be rendered
	 */	
	public void renderScene(Scene scene, Vector4f clipPlane, boolean isLowDistance);
	
	/**
	 * Method renders all object of the scene.
	 * 
	 * @param scene 
	 * 					{@link Scene} value 
	 * @param clipPlane	
	 * 					{@link Vector4f} value of clipping plane matrix
	 */
	public void renderScene(Scene scene, Vector4f clipPlane);
	
	/**
	 * 
	 * @param scene
	 * 				{@link Scene} value
	 */
	public void renderShadowMap(Scene scene);
	
	/**
	 * Method cleans all shaders and rendering objects.
	 */
	public void cleanUp();
	
	/**
	 * 
	 * @return
	 */
	public EntityRenderer getEntityRenderer();
	
	/**
	 * 
	 * @param entitiyWiredFrame
	 */
	public void setEntityWiredFrame(boolean entitiyWiredFrame);
	
	/**
	 * 
	 * @param terrainWiredFrame
	 */
	public void setTerrainWiredFrame(boolean terrainWiredFrame);
	

	
	/**
	 * 
	 * @param entities
	 * @param terrains
	 * @param lights
	 * @param camera
	 */
	void renderLowQualityScene(Map<TexturedModel, List<Entity>> entities, Collection<Terrain> terrains,
			Collection<Light> lights, Camera camera);
	
	/**
	 * 
	 * @return
	 */
	Matrix4f getProjectionMatrix();
	
	/**
	 * 
	 * @param scene
	 * @return
	 */
	Collection<Entity> createFrustumEntities(Scene scene);
	
	/**
	 * 
	 * @return
	 */
	Frustum getFrustum();
	
	
}
