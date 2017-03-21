package renderEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;
import scene.ES;
import terrains.Terrain;
import toolbox.Frustum;

public class SceneProcessorSimple implements SceneProcessor {
	
	@Override
	public void processTerrain(Terrain terrain, Collection<Terrain> terrains) {
		terrains.add(terrain);
	}
	
	@Override
	public void processEntity(Entity entity, 
			Map<TexturedModel, List<Entity>> entities, Frustum frustum) {
		if(checkVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<Entity> batch = entities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<Entity> newBatch = new ArrayList<Entity>();
				newBatch.add(entity);
				entities.put(entityModel, newBatch);		
			}
		}
	}
	
	@Override
	public void processNormalMapEntity(Entity entity, 
			Map<TexturedModel, List<Entity>> normalMapEntities, 
			Frustum frustum) {
		if(checkVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<Entity> batch = normalMapEntities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<Entity> newBatch = new ArrayList<Entity>();
				newBatch.add(entity);
				normalMapEntities.put(entityModel, newBatch);	
			}
		}
	}
	
	@Override	
	public void processShadowEntity(Entity entity,
			Map<TexturedModel, List<Entity>> entities, Frustum frustum) {
		if(checkShadowVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<Entity> batch = entities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<Entity> newBatch = new ArrayList<Entity>();
				newBatch.add(entity);
				entities.put(entityModel, newBatch);		
			}
		}
	}

	@Override
	public void processShadowNormalMapEntity(Entity entity, 
			Map<TexturedModel, List<Entity>> normalMapEntities, Frustum frustum) {
		if(checkShadowVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<Entity> batch = normalMapEntities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<Entity> newBatch = new ArrayList<Entity>();
				newBatch.add(entity);
				normalMapEntities.put(entityModel, newBatch);	
			}
		}
	}
	
	private boolean checkVisibility(Entity entity, Frustum frustum) {
		boolean isVisible = false;
		float distance = frustum.distanceSphereInFrustum(entity.getPosition(), entity.getSphereRadius());
		if (distance > 0 && distance <= ES.RENDERING_VIEW_DISTANCE) {
			isVisible = true;
		}
		return isVisible;
	}

	private boolean checkShadowVisibility(Entity entity, Frustum frustum) {
		boolean isVisible = false;
		Vector3f position = new Vector3f(entity.getPosition().x, 0, entity.getPosition().z);
		float distance = frustum.distanceSphereInFrustum(position, entity.getSphereRadius());
		if (distance >= -ES.SHADOW_DISTANCE && distance <= ES.SHADOW_DISTANCE) {
			isVisible = true;
		}
		return isVisible;
	}

}