package renderer.object.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector4f;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.ILight;
import object.texture.Texture;
import primitive.model.Model;
import tool.math.VMatrix4f;

/**
 * 
 * @author homelleon
 * @see TexturedEntityRenderer
 * @see NormalEntityRenderer
 * @see DecorEntityRenderer
 */
public interface IEntityRenderer {
	
	void renderHigh(Map<Model, List<IEntity>> entities, 
			Vector4f clipPlane, Collection<ILight> lights,
			ICamera camera, VMatrix4f toShadowMapSpace, Texture environmentMap);
	
	void renderLow(Map<Model, List<IEntity>> entities, 
			Collection<ILight> lights, ICamera camera, VMatrix4f toShadowMapSpace);
	
	void clean();

}
