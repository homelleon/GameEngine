package renderer.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.entity.entity.IEntity;
import object.model.textured.TexturedModel;
import object.terrain.terrain.ITerrain;
import renderer.viewCulling.frustum.Frustum;

public class SceneProcessor implements ISceneProcessor {

	@Override
	public void processTerrain(ITerrain terrain, Collection<ITerrain> terrains) {
		terrains.add(terrain);
	}

	@Override
	public void processEntity(IEntity entity, Map<TexturedModel, List<IEntity>> entities, Frustum frustum) {
		if (checkVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<IEntity> batch = entities.get(entityModel);
			if (batch != null) {
				batch.add(entity);
			} else {
				List<IEntity> newBatch = new ArrayList<IEntity>();
				newBatch.add(entity);
				entities.put(entityModel, newBatch);
			}
		}
	}

	@Override
	public void processNormalMapEntity(IEntity entity, Map<TexturedModel, List<IEntity>> normalMapEntities,
			Frustum frustum) {
		if (checkVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<IEntity> batch = normalMapEntities.get(entityModel);
			if (batch != null) {
				batch.add(entity);
			} else {
				List<IEntity> newBatch = new ArrayList<IEntity>();
				newBatch.add(entity);
				normalMapEntities.put(entityModel, newBatch);
			}
		}
	}

	@Override
	public void processShadowEntity(IEntity entity, Map<TexturedModel, List<IEntity>> entities, Frustum frustum) {
		if (checkShadowVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<IEntity> batch = entities.get(entityModel);
			if (batch != null) {
				batch.add(entity);
			} else {
				List<IEntity> newBatch = new ArrayList<IEntity>();
				newBatch.add(entity);
				entities.put(entityModel, newBatch);
			}
		}
	}

	@Override
	public void processShadowNormalMapEntity(IEntity entity, Map<TexturedModel, List<IEntity>> normalMapEntities,
			Frustum frustum) {
		if (checkShadowVisibility(entity, frustum)) {
			TexturedModel entityModel = entity.getModel();
			List<IEntity> batch = normalMapEntities.get(entityModel);
			if (batch != null) {
				batch.add(entity);
			} else {
				List<IEntity> newBatch = new ArrayList<IEntity>();
				newBatch.add(entity);
				normalMapEntities.put(entityModel, newBatch);
			}
		}
	}

	private boolean checkVisibility(IEntity entity, Frustum frustum) {
		boolean isVisible = false;
		float distance = frustum.distanceSphereInFrustum(entity.getPosition(), entity.getSphereRadius());
		if (distance > 0 && distance <= EngineSettings.RENDERING_VIEW_DISTANCE) {
			isVisible = true;
		}
		return isVisible;
	}

	private boolean checkShadowVisibility(IEntity entity, Frustum frustum) {
		boolean isVisible = false;
		Vector3f position = new Vector3f(entity.getPosition().x, 0, entity.getPosition().z);
		float distance = frustum.distanceSphereInFrustum(position, entity.getSphereRadius());
		if (distance >= -EngineSettings.SHADOW_DISTANCE && distance <= EngineSettings.SHADOW_DISTANCE) {
			isVisible = true;
		}
		return isVisible;
	}

}
