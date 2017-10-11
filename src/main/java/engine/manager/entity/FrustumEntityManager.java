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
import object.scene.IScene;
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
	public void rebuildNodes(Collection<IEntity> entities, int size) {
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Entity nodes");
			EngineDebug.println("Generating entity nodes...", 1);
		}
		
		EntityNode node = new EntityNode(new Vector3f(0,-50,0));
		entityNodes.add(node);	
		entities.forEach(this::addEntityInNodes);
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Generated " + entityNodes.size() + " nodes.", 2);
			EngineDebug.println("Succed!", 1);
			EngineDebug.printClose("Entity nodes");
		}
	}
	
	@Override
	public void addEntityInNodes(IEntity entity) {
		if(entity.hasParent()) {
			EntityNode parent = entity.getParentNode();
			parent.removeEntity(entity.getName());
			if(parent.getNamedEntities().isEmpty()) {
				this.entityNodes.remove(parent);
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
		this.entityNodes.add(node);
		return node;
	}
	
	@Override
	public List<IEntity> updateWithFrustum(Frustum frustum, ICamera camera, boolean isLowDetail) {		
		return entityNodes.parallelStream()
				.filter(node -> !node.getNamedEntities().isEmpty())
				.filter(node -> this.checkVisibity(node.getPosition(), frustum, camera, NODE_CHECK_RADIUS))
				.flatMap(node -> node.getNamedEntities().values().stream())
				.filter(entity -> this.checkVisibility(entity, frustum, camera, isLowDetail))
				.collect(Collectors.toList());
	}
	
	private boolean checkVisibity(Vector3f position, Frustum frustum, ICamera camera, float radius) {
		float distance1 = 0;
		float distance2 = radius;
		float distance = Maths.distance2Points(position, camera.getPosition());
		if(distance > distance2 || distance < distance1) {
			return false;
		} else {
			return frustum.sphereInFrustum(position, EntityNode.BIG_RADIUS);
		}
	}
	
	private boolean checkVisibility(IEntity entity, Frustum frustum, ICamera camera, boolean isLowDetail) {
		float distance1 = 0;
		float distance2 = EngineSettings.RENDERING_VIEW_DISTANCE;
		if(isLowDetail) {
			distance2 /= 2;
		}
		if(entity.getType() == EngineSettings.ENTITY_TYPE_DECORATE) {
			distance2 /= 4;
		}
		float distance = Maths.distance2Points(entity.getPosition(), camera.getPosition());
		if(distance > distance2 || distance < distance1) {
			return false;
		}
		return frustum.sphereInFrustum(entity.getPosition(), entity.getSphereRadius());	
	}

	@Override
	public List<IEntity> prepareFrustumHighEntities(IScene scene, Matrix4f projectionMatrix) {
		if(scene.getCamera().isMoved() || scene.getCamera().isRotated()) {
			Matrix4f projectionViewMatrix = Matrix4f.mul(projectionMatrix, scene.getCamera().getViewMatrix());	
			this.frustum.extractFrustum(projectionViewMatrix);
			this.frustumHighEntities.clear();
			this.frustumHighEntities = this.updateWithFrustum(this.frustum, scene.getCamera(), false);
		}
		return this.frustumHighEntities;
	}
	
	@Override
	public List<IEntity> prepareFrustumLowEntities(IScene scene, Matrix4f projectionMatrix) {
		if(scene.getCamera().isMoved() || scene.getCamera().isRotated()) {
			Matrix4f projectionViewMatrix = Matrix4f.mul(projectionMatrix, scene.getCamera().getViewMatrix());	
			this.frustum.extractFrustum(projectionViewMatrix);
			this.frustumLowEntities.clear();
			this.frustumLowEntities = this.updateWithFrustum(this.frustum, scene.getCamera(), true);
		}
		return this.frustumLowEntities;
	}

	@Override
	public List<IEntity> prepareShadowFrustumEntities(IScene scene, Matrix4f shadowMapSpaceMatrix) {
		if(scene.getCamera().isMoved() || scene.getCamera().isRotated()) {
			this.frustum.extractFrustum(shadowMapSpaceMatrix);
			this.frustumShadowEntities.clear();
			this.frustumShadowEntities = this.updateWithFrustum(this.frustum, scene.getCamera(), false);
		}
		return this.frustumShadowEntities;
	}

	@Override
	public List<IEntity> getFrustumHighEntities() {
		return this.frustumHighEntities;
	}
	
	@Override
	public List<IEntity> getFrustumLowEntities() {
		return this.frustumLowEntities;
	}

	@Override
	public List<IEntity> getFrustumShadowEntities() {
		return this.frustumShadowEntities;
	}

	@Override
	public void clean() {
		this.frustumHighEntities.clear();
		this.frustumLowEntities.clear();
		this.frustumShadowEntities.clear();
	}

}
