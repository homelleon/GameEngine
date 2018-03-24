package renderer.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector4f;

import object.camera.Camera;
import object.entity.Entity;
import object.light.Light;
import primitive.model.Model;
import primitive.texture.Texture;
import tool.math.Matrix4f;

/**
 * 
 * @author homelleon
 * @see TexturedEntityRenderer
 * @see NormalEntityRenderer
 */
public interface IEntityRenderer {
	
	void render(Map<Model, List<Entity>> entities, 
			Vector4f clipPlane, Collection<Light> lights,
			Camera camera, Matrix4f toShadowMapSpace, Texture environmentMap);
	
	void render(Map<Model, List<Entity>> entities, 
			Collection<Light> lights, Camera camera, Matrix4f toShadowMapSpace);
	
	void clean();

}
