package object.terrain.terrain;

import manager.octree.Node;
import object.camera.ICamera;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class TerrainNode extends Node {
	
	private int lod;
	private Vector2f location;
	private Vector3f worldPosition;
	private Matrix4f localTransformationMatrix;
	private Matrix4f worldTransformationMatrix;
	private Vector2f index;
	private float gap;
	
	public TerrainNode(Vector2f location, int lod, Vector2f index, ICamera camera) {
		this.setLeaf(true);
		this.localTransformationMatrix = new Matrix4f();
		this.worldTransformationMatrix = new Matrix4f();
		this.lod = lod;
		this.location = location;
		this.index = index;
		this.gap = 1f / (TerrainQuadTree.getRootNodes() * (float) (Math.pow(2, lod)));
		
		Vector3f localScaling = new Vector3f(gap, 0, gap);
		Vector3f localTranslation = new Vector3f(location.getX(), 0, location.getY());
		
		this.localTransformationMatrix.scale(localScaling);
		this.localTransformationMatrix.translate(localTranslation);
		
		this.worldTransformationMatrix.scale(new Vector3f(ITerrain.TERRAIN_SCALE_XZ, ITerrain.TERRAIN_SCALE_Y, ITerrain.TERRAIN_SCALE_XZ));
		this.worldTransformationMatrix.translate(new Vector3f(-ITerrain.TERRAIN_SCALE_XZ / 2f, 0, -ITerrain.TERRAIN_SCALE_XZ / 2f));
		
		this.computeWorldPosition();
		this.updateQuadTree(camera);		
	}
	
	public void computeWorldPosition() {
		Vector2f loc = new Vector2f(location.add(gap / 2f)).mul(ITerrain.TERRAIN_SCALE_XZ).sub(ITerrain.TERRAIN_SCALE_XZ / 2f);
		
		this.worldPosition = new Vector3f(loc.getX(), 0, loc.getY());
	}
	
	public void updateQuadTree(ICamera camera) {
		if(camera.getPosition().getY() > ITerrain.TERRAIN_SCALE_Y) {
			this.worldPosition.setY(ITerrain.TERRAIN_SCALE_Y);
		} else {
			this.worldPosition.setY(camera.getPosition().getY());
		}
	}
	
	public void updateChildNodes(ICamera camera) {
		float distance = (new Vector3f(camera.getPosition()).sub(this.worldPosition)).length();
		
		if(distance < ITerrain.LOD_RANGE[this.lod]) {
			this.addChildren(this.lod + 1, camera);
		} else if(distance >= ITerrain.LOD_RANGE[this.lod]) {
			this.removeChildren();
		}
	}
	
	private void addChildren(int lod, ICamera camera) {
		if(this.isLeaf()) {
			this.setLeaf(false);
		}
		
		if(this.getChildren().size() == 0) {
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 2; j++) {
					this.addChild(
							new TerrainNode(
								new Vector2f(location.add(new Vector2f(i * gap / 2f,j * gap / 2f))),
								lod,
								new Vector2f(i, j),
								camera
							)
					);
				}
			}
		}
		
	}
}
