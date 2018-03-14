package manager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import manager.octree.EntityNode;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import scene.Scene;
import tool.math.Frustum;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class FrustumEntityManager implements IFrustumEntityManager {
	
	private static float NODE_CHECK_RADIUS = EngineSettings.RENDERING_VIEW_DISTANCE + EntityNode.RADIUS;
	private List<EntityNode> entityNodes = new ArrayList<EntityNode>();
	private Frustum frustum;
	private List<IEntity> frustumHighEntities = new ArrayList<IEntity>();
	private List<IEntity> frustumLowEntities = new ArrayList<IEntity>();
	private List<IEntity> frustumShadowEntities = new ArrayList<IEntity>();

	public FrustumEntityManager(Frustum frustum) {
		this.frustum = frustum;
	}
	
	@Override
	public void rebuildNodes(Collection<IEntity> entities) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Entity nodes");
			EngineDebug.println("Generating entity nodes...", 1);
		}
		
		entities.forEach(this::addEntityInNodes);
		
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Generated " + entityNodes.size() + " nodes.", 2);
			EngineDebug.println("Succed!", 1);
			EngineDebug.printClose("Entity nodes");
		}
	}
	
	@Override
	public void addEntityInNodes(IEntity entity) {
		if (entity.hasParent()) {
			EntityNode parent = entity.getParentNode();
			parent.removeEntity(entity.getName());
			if (parent.getNamedEntities().isEmpty()) {
				entityNodes.remove(parent);
			}
		}
		entityNodes.parallelStream()
				.filter(node -> node.isBounding(entity))
				.findFirst()
				.orElseGet(() -> createEntityNode(entity.getPosition()))
				.addEntity(entity);
	}
	
	private EntityNode createEntityNode(Vector3f position) {
		float step = EntityNode.RADIUS;
		int x = (int) Math.floor(position.x / step);
		int y = (int) Math.floor(position.y / step);
		int z = (int) Math.floor(position.z / step);
		Vector3f nodePosition = new Vector3f(x * step, y * step, z * step);
		EntityNode node = new EntityNode(nodePosition);
		entityNodes.add(node);
		return node;
	}
	
	@Override
	public List<IEntity> updateWithFrustum(Frustum frustum, ICamera camera, boolean isLowDetail) {		
		return entityNodes.stream()
				.filter(node -> !node.getNamedEntities().isEmpty())
				.filter(node -> checkVisibility(node.getPosition(), frustum, camera, NODE_CHECK_RADIUS))
				.flatMap(node -> node.getNamedEntities().values().stream())
				.filter(entity -> checkVisibility(entity, frustum, camera, isLowDetail))
				.collect(Collectors.toList());
	}
	
	private boolean checkVisibility(Vector3f position, Frustum frustum, ICamera camera, float radius) {
		float distance1 = 0;
		float distance2 = radius;
		float distance = Maths.distance2Points(position, camera.getPosition());
		return (distance > distance2 || distance < distance1) ?
				false :
				frustum.sphereInFrustum(position, EntityNode.BIG_RADIUS);
	}
	
	private boolean checkVisibility(IEntity entity, Frustum frustum, ICamera camera, boolean isLowDetail) {
		float distance1 = 0;
		float distance2 = EngineSettings.RENDERING_VIEW_DISTANCE;
		
		if (isLowDetail) {
			distance2 /= 2;
		}
		
		if (entity.getType() == EngineSettings.ENTITY_TYPE_DECORATE) {
			distance2 /= 4;
		}
		
		float distance = Maths.distance2Points(entity.getPosition(), camera.getPosition());
		
		return (distance > distance2 || distance < distance1) ?
				false :
				frustum.sphereInFrustum(entity.getPosition(), entity.getSphereRadius());
	}

	@Override
	public List<IEntity> processHighEntities(Scene scene, Matrix4f projectionMatrix, boolean toRebuild) {
		if (toRebuild) {
			Matrix4f projectionViewMatrix = Matrix4f.mul(projectionMatrix, scene.getCamera().getViewMatrix()); 
			frustumHighEntities = prepareEntities(scene, projectionViewMatrix, false);
		}
		return frustumHighEntities;
	}
	
	@Override
	public List<IEntity> processLowEntities(Scene scene, Matrix4f projectionMatrix, boolean toRebuild) {
		if (toRebuild) {
			Matrix4f projectionViewMatrix = Matrix4f.mul(projectionMatrix, scene.getCamera().getViewMatrix());		
			frustumLowEntities = prepareEntities(scene, projectionViewMatrix, true);
		}		
		return frustumLowEntities;
	}

	@Override
	public List<IEntity> prepareShadowEntities(Scene scene, Matrix4f shadowMapMatrix, boolean toRebuild) {
		if (toRebuild) {
			frustumShadowEntities = prepareEntities(scene, shadowMapMatrix, false);
		}
		return frustumShadowEntities;
	}
	
	private List<IEntity> prepareEntities(Scene scene, Matrix4f projectionMatrix, boolean isLowDetail) {
		frustum.extractFrustum(projectionMatrix);
		return updateWithFrustum(frustum, scene.getCamera(), isLowDetail);
	}

	@Override
	public List<IEntity> getHighEntities() {
		return frustumHighEntities;
	}
	
	@Override
	public List<IEntity> getLowEntities() {
		return frustumLowEntities;
	}

	@Override
	public List<IEntity> getShadowEntities() {
		return frustumShadowEntities;
	}

	@Override
	public void clean() {
		frustumHighEntities.clear();
		frustumLowEntities.clear();
		frustumShadowEntities.clear();
	}

}
