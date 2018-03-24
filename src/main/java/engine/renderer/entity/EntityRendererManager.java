package renderer.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector4f;

import object.camera.Camera;
import object.entity.Entity;
import object.light.Light;
import primitive.model.Model;
import primitive.texture.Texture;
import tool.math.Matrix4f;

public class EntityRendererManager {
	
	private Map<EntityRenderer, Map<Model, List<Entity>>> entityRenderers = 
			new HashMap<EntityRenderer, Map<Model, List<Entity>>>();
	
	public EntityRendererManager addPair(EntityRenderer renderer, Map<Model, List<Entity>> enities) {
		this.entityRenderers.put(renderer, enities);
		return this;
	}
	
	public void render(Vector4f clipPlane, Collection<Light> lights, Camera camera, Matrix4f toShadowMapSpace,
			Texture environmentMap) {
		entityRenderers.forEach((renderer, entities) -> 
			renderer.render(entities, clipPlane, lights, camera, toShadowMapSpace, environmentMap));
	}

	public void clean() {
		this.entityRenderers.keySet().forEach(EntityRenderer::clean);
		this.entityRenderers.values().forEach(Map::clear);
		this.entityRenderers.clear();
	}

}
