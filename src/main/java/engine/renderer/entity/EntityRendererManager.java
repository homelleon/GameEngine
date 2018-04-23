package renderer.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector4f;

import object.entity.Entity;
import primitive.model.Model;
import primitive.texture.Texture;
import scene.Scene;
import tool.math.Matrix4f;

public class EntityRendererManager {
	
	private Map<EntityRenderer, Map<Model, List<Entity>>> entityRenderers = 
			new HashMap<EntityRenderer, Map<Model, List<Entity>>>();
	
	public EntityRendererManager addPair(EntityRenderer renderer, Map<Model, List<Entity>> enities) {
		entityRenderers.put(renderer, enities);
		return this;
	}
	
	public void render(Scene scene, Vector4f clipPlane, Matrix4f toShadowMapSpace,
			Texture environmentMap) {
		entityRenderers.forEach((renderer, entities) -> 
			renderer.render(entities, scene, clipPlane, toShadowMapSpace, environmentMap));
	}

	public void clean() {
		entityRenderers.keySet().forEach(EntityRenderer::clean);
		entityRenderers.values().forEach(Map::clear);
		entityRenderers.clear();
	}

}
