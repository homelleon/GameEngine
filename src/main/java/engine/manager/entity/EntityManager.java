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
import tool.manager.AbstractManager;
import tool.math.Frustum;
import tool.math.Maths;
import tool.math.vector.Vector3f;

/**
 * Manages entities in the game engine.
 * <p>
 * Can differ entities that are situated in the frustum. Also can store entities
 * chosen by player and entities for editor menu.
 * 
 * @author homelleon
 * @version 1.0.1
 * @see IEntityManager
 * @see AbstactManager
 * 
 */
public class EntityManager extends AbstractManager<IEntity> implements IEntityManager {

	private static float NODE_CHECK_RADIUS = EngineSettings.RENDERING_VIEW_DISTANCE + EntityNode.RADIUS;
	private boolean hasNodes = false;
	private List<IEntity> pointedEntities = new ArrayList<IEntity>();
	private List<IEntity> editorEntities = new ArrayList<IEntity>();
	private List<EntityNode> entityNodes = new ArrayList<EntityNode>();
	
	@Override
	public void rebuildNodes(int size) {
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Entity nodes");
			EngineDebug.println("Generating entity nodes...", 1);
		}
		this.hasNodes = true;
		
		EntityNode node = new EntityNode(new Vector3f(0,-50,0));
		entityNodes.add(node);	
		this.getAll().forEach(this::addEntityInNodes);
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
		entityNodes.stream()
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
	public void setHasNodes(boolean value) {
		this.hasNodes = value;
	}
	
	@Override
	public void add(IEntity entity) {
		super.add(entity);
		if(hasNodes) {
			EntityNode entityNode = entityNodes.stream()
				.filter(node -> node.isBounding(entity))
				.findFirst()
				.get();
			entityNode.addEntity(entity);
		}
	}
	
	@Override
	public void addPointedList(Collection<IEntity> pointedList) {
		pointedList.forEach(entity -> {
			entity.setChosen(true);
			this.pointedEntities.add(entity);
		});
	}

	@Override
	public void setEditorList(List<IEntity> editorList) {
		if (editorList != null) {
			this.editorEntities = editorList;
		}
	}

	@Override
	public void addPointed(IEntity entity) {
		if (entity != null) {
			entity.setChosen(true);
			this.pointedEntities.add(entity);
		}
	}

	@Override
	public void addForEditor(IEntity entity) {
		this.editorEntities.add(entity);
	}

	@Override
	public List<IEntity> updateWithFrustum(Frustum frustum, ICamera camera, boolean isLowDetail) {		
		return entityNodes.parallelStream()
				.filter(node -> !node.getNamedEntities().isEmpty())
				.filter(node -> this.checkVisibity(node.getPosition(), camera, NODE_CHECK_RADIUS))
				.flatMap(node -> node.getNamedEntities().values().stream())
				.filter(entity -> this.checkVisibility(entity, frustum, camera, isLowDetail))
				.collect(Collectors.toList());
	}

	@Override
	public List<IEntity> getPointed() {
		return this.pointedEntities;
	}

	@Override
	public List<IEntity> getForEditor() {
		return this.editorEntities;
	}

	@Override
	public IEntity getForEditorByIndex(int index) {
		return this.editorEntities.get(index);
	}

	@Override
	public void clearPointed() {
		this.pointedEntities
			.forEach(entity -> entity.setChosen(false));
		this.pointedEntities.clear();
	}

	@Override
	public void clean() {
		super.clean();
		this.pointedEntities.clear();
		this.editorEntities.clear();
	}
	
	private boolean checkVisibity(Vector3f position, ICamera camera, float radius) {
		float distance = Maths.distance2Points(position, camera.getPosition());
		return distance <= radius;
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


}
