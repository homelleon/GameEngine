package renderer.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector4f;

import object.entity.Entity;
import primitive.model.Model;
import primitive.texture.Texture;
import scene.Scene;
import tool.math.Matrix4f;

/**
 * 
 * @author homelleon
 * @see TexturedEntityRenderer
 * @see NormalEntityRenderer
 */
public interface EntityRenderer {
	
	void render(Map<Model, List<Entity>> entities, Scene scene,  
			Vector4f clipPlane, Matrix4f toShadowMapSpace, Texture environmentMap);
	
	void clean();

}
