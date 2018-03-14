package renderer.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector4f;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.ILight;
import primitive.model.Model;
import primitive.texture.Texture;
import tool.math.Matrix4f;

public class EntityRendererManager implements IEntityRendererManager {
	
	private Map<IEntityRenderer, Map<Model, List<IEntity>>> entityRenderers = 
			new HashMap<IEntityRenderer, Map<Model, List<IEntity>>>();
	
	@Override
	public EntityRendererManager addPair(IEntityRenderer renderer, Map<Model, List<IEntity>> enitties) {
		this.entityRenderers.put(renderer, enitties);
		return this;
	}
	
	@Override
	public void render(Vector4f clipPlane, Collection<ILight> lights, ICamera camera, Matrix4f toShadowMapSpace,
			Texture environmentMap, boolean isLowDetailed) {
		if (isLowDetailed) {
			entityRenderers.forEach((renderer, entities) -> 
				renderer.renderLow(entities, lights, camera, toShadowMapSpace));
		} else {
			entityRenderers.forEach((renderer, entities) -> 
				renderer.renderHigh(entities, clipPlane, lights, camera, 
						toShadowMapSpace, environmentMap));
		}
	}

	@Override
	public void clean() {
		this.entityRenderers.keySet().forEach(IEntityRenderer::clean);
		this.entityRenderers.values().forEach(Map::clear);
		this.entityRenderers.clear();
	}

}
