package object.terrain.terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;

import manager.octree.Node;
import object.camera.ICamera;
import primitive.buffer.PatchVAO;
import renderer.terrain.PatchedTerrainRenderer;
import shader.terrain.PatchedTerrainShader;
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
		
		this.worldTransformationMatrix.scale(new Vector3f(PatchedTerrainRenderer.SCALE_XZ, PatchedTerrainRenderer.SCALE_Y, PatchedTerrainRenderer.SCALE_XZ));
		this.worldTransformationMatrix.translate(new Vector3f(-PatchedTerrainRenderer.SCALE_XZ / 2f, 0, -PatchedTerrainRenderer.SCALE_XZ / 2f));
		
		this.computeWorldPosition();
		this.updateQuadTree(camera);		
	}
	
	public void computeWorldPosition() {
		Vector2f loc = new Vector2f(location.add(gap / 2f)).mul(PatchedTerrainRenderer.SCALE_XZ).sub(PatchedTerrainRenderer.SCALE_XZ / 2f);
		
		this.worldPosition = new Vector3f(loc.getX(), 0, loc.getY());
	}
	
	public void updateQuadTree(ICamera camera) {
		if(camera.getPosition().getY() > PatchedTerrainRenderer.SCALE_Y) {
			this.worldPosition.setY(PatchedTerrainRenderer.SCALE_Y);
		} else {
			this.worldPosition.setY(camera.getPosition().getY());
		}
		updateChildren(camera);
		for(Node node : this.getChildren()) {
			((TerrainNode) node).updateQuadTree(camera);
		}
	}
	
	public void updateChildren(ICamera camera) {
		float distance = (new Vector3f(camera.getPosition()).sub(this.worldPosition)).length();
		
		if(distance < PatchedTerrainRenderer.LOD_MORPH_AREAS[this.lod]) {
			this.addChildren(this.lod + 1, camera);
		} else if(distance >= PatchedTerrainRenderer.LOD_MORPH_AREAS[this.lod]) {
			this.removeChildren();
		}
	}
	
	public void removeChildren() {
		if(!this.isLeaf()) {
			this.setLeaf(true);
		}
		if(!this.getChildren().isEmpty()) {
			this.getChildren().clear();
		}
	}
	
	public void render(PatchedTerrainShader shader, PatchVAO vao) {
		if(this.isLeaf()) {
			shader.loadLoDVariables(lod, index, gap, location);
			shader.loadPositionMatrix(worldTransformationMatrix, worldTransformationMatrix);
			
			GL11.glDrawArrays(GL40.GL_PATCHES, 0, vao.getSize());
		}
		for(Node child : this.getChildren()) {
			((TerrainNode) child).render(shader, vao);
		}
	}
	
	private void addChildren(int lod, ICamera camera) {
		if(this.isLeaf()) {
			this.setLeaf(false);
		}
		
		if(this.getChildren().isEmpty()) {
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

	public int getLod() {
		return lod;
	}

	public void setLod(int lod) {
		this.lod = lod;
	}

	public Vector2f getLocation() {
		return location;
	}

	public void setLocation(Vector2f location) {
		this.location = location;
	}

	public Vector3f getWorldPosition() {
		return worldPosition;
	}

	public void setWorldPosition(Vector3f worldPosition) {
		this.worldPosition = worldPosition;
	}

	public Vector2f getIndex() {
		return index;
	}

	public void setIndex(Vector2f index) {
		this.index = index;
	}

	public float getGap() {
		return gap;
	}

	public void setGap(float gap) {
		this.gap = gap;
	}
}
