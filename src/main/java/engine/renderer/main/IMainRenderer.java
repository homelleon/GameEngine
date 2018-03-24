package renderer.main;

import org.lwjgl.util.vector.Vector4f;

import object.camera.Camera;
import object.entity.Entity;
import scene.Scene;
import tool.math.Matrix4f;

/**
 * Main interface of render Engine that contatins scene, environment and shadow
 * rendering system to control them and send scene objects in it.
 * 
 * @author homelleon
 * @version 1.0
 * @see MainRenderer
 *
 */
public interface IMainRenderer {
	
	public void render(Scene scene);

	/**
	 * Method renders all object of the scene.
	 * 
	 * @param scene
	 *            {@link IScene} value
	 * @param clipPlane
	 *            {@link Vector4f} value of clipping plane matrix
	 */
	public void render(Scene scene, Vector4f clipPlane);

	/**
	 * 
	 * @param scene
	 *            {@link IScene} value
	 */
	public void renderShadows(Scene scene);

	/**
	 * Method cleans all shaders and rendering objects.
	 */
	public void clean();

	/**
	 * 
	 * @param entities
	 * @param terrains
	 * @param lights
	 * @param camera
	 */
	void renderCubemap(Scene scene, Entity shinyEntity, Camera camera);

	/**
	 * 
	 * @return
	 */
	Matrix4f getProjectionMatrix();

}
