package renderer.object.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector4f;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.ILight;
import object.model.textured.TexturedModel;
import object.texture.Texture;
import tool.math.Matrix4f;

/**
 * 
 * @author homelleon
 * @see EntityRendererManager
 */
public interface IEntityRendererManager {
	
	void addPair(IEntityRenderer renderer, Map<TexturedModel, List<IEntity>> enitties);
	
	void render(Vector4f clipPlane, Collection<ILight> lights,
			ICamera camera, Matrix4f toShadowMapSpace, Texture environmentMap, boolean isLowDetailed);
	
	void clean();

}
