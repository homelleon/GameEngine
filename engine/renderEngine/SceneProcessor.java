package renderEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import objects.entities.EntityInterface;
import objects.models.TexturedModel;
import objects.terrains.TerrainInterface;
import scene.ES;
import viewCulling.Frustum;

public class SceneProcessor implements SceneProcessorInterface {
	
	@Override
	public void processTerrain(TerrainInterface terrain, Collection<TerrainInterface> terrains) {
		terrains.add(terrain);
	}
	
	@Override
	public void processEntity(EntityInterface entity, 
			Map<TexturedModel, List<EntityInterface>> entities, Frustum frustum) {
		if(checkVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<EntityInterface> batch = entities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<EntityInterface> newBatch = new ArrayList<EntityInterface>();
				newBatch.add(entity);
				entities.put(entityModel, newBatch);		
			}
		}
	}
	
	@Override
	public void processNormalMapEntity(EntityInterface entity, 
			Map<TexturedModel, List<EntityInterface>> normalMapEntities, 
			Frustum frustum) {
		if(checkVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<EntityInterface> batch = normalMapEntities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<EntityInterface> newBatch = new ArrayList<EntityInterface>();
				newBatch.add(entity);
				normalMapEntities.put(entityModel, newBatch);	
			}
		}
	}
	
	@Override	
	public void processShadowEntity(EntityInterface entity,
			Map<TexturedModel, List<EntityInterface>> entities, Frustum frustum) {
		if(checkShadowVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<EntityInterface> batch = entities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<EntityInterface> newBatch = new ArrayList<EntityInterface>();
				newBatch.add(entity);
				entities.put(entityModel, newBatch);		
			}
		}
	}

	@Override
	public void processShadowNormalMapEntity(EntityInterface entity, 
			Map<TexturedModel, List<EntityInterface>> normalMapEntities, Frustum frustum) {
		if(checkShadowVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<EntityInterface> batch = normalMapEntities.get(entityModel);
			if(batch!=null) {
				batch.add(entity);	
			}else{
				List<EntityInterface> newBatch = new ArrayList<EntityInterface>();
				newBatch.add(entity);
				normalMapEntities.put(entityModel, newBatch);	
			}
		}
	}
	
	private boolean checkVisibility(EntityInterface entity, Frustum frustum) {
		boolean isVisible = false;
		float distance = frustum.distanceSphereInFrustum(entity.getPosition(), entity.getSphereRadius());
		if (distance > 0 && distance <= ES.RENDERING_VIEW_DISTANCE) {
			isVisible = true;
		}
		return isVisible;
	}

	private boolean checkShadowVisibility(EntityInterface entity, Frustum frustum) {
		boolean isVisible = false;
		Vector3f position = new Vector3f(entity.getPosition().x, 0, entity.getPosition().z);
		float distance = frustum.distanceSphereInFrustum(position, entity.getSphereRadius());
		if (distance >= -ES.SHADOW_DISTANCE && distance <= ES.SHADOW_DISTANCE) {
			isVisible = true;
		}
		return isVisible;
	}

}
