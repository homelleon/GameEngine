package renderer.object.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.ILight;
import object.model.textured.TexturedModel;
import object.texture.Texture;

public interface IEntityRenderer {
	
	void renderHigh(Map<TexturedModel, List<IEntity>> entities, 
			Vector4f clipPlane, Collection<ILight> lights,
			ICamera camera, Matrix4f toShadowMapSpace, Texture environmentMap);
	
	void renderLow(Map<TexturedModel, List<IEntity>> entities, 
			Collection<ILight> lights, ICamera camera);
	
	void clean();

}