package object.terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;

import core.settings.EngineSettings;
import manager.octree.Node;
import object.camera.Camera;
import primitive.buffer.VAO;
import shader.terrain.TerrainShader;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class TerrainNode extends Node<Vector3f> {
	
	private int lod;
	private Vector2f location;
	private Vector3f worldPosition;
	private Matrix4f localTransformationMatrix;
	private Matrix4f worldTransformationMatrix;
	private Vector2f index;
	private float gap;
	
	public TerrainNode(Vector2f location, int lod, Vector2f index, Camera camera) {
		super("terrainNode");
		this.setLeaf(true);
		this.localTransformationMatrix = new Matrix4f();
		this.worldTransformationMatrix = new Matrix4f();
		this.lod = lod;
		this.location = location;
		this.index = index;
		this.gap = 1f / (TerrainQuadTree.getRootNodes() * (float) (Math.pow(2, lod)));
		
		Vector3f localScaling = new Vector3f(gap, 0, gap);
		Vector3f localTranslation = new Vector3f(location.getX(), 0, location.getY());
		
		this.localTransformationMatrix.translate(localTranslation);
		this.localTransformationMatrix.scale(localScaling);
		
		
		this.worldTransformationMatrix.translate(new Vector3f(-EngineSettings.TERRAIN_SCALE_XZ / 2f, 0, -EngineSettings.TERRAIN_SCALE_XZ / 2f));
		this.worldTransformationMatrix.scale(new Vector3f(EngineSettings.TERRAIN_SCALE_XZ, EngineSettings.TERRAIN_SCALE_Y, EngineSettings.TERRAIN_SCALE_XZ));
		
		this.computeWorldPosition();
		this.updateQuadTree(camera);		
	}
	
	public void computeWorldPosition() {
		Vector2f loc = new Vector2f(location).add(gap / 2f).mul(EngineSettings.TERRAIN_SCALE_XZ).sub(EngineSettings.TERRAIN_SCALE_XZ / 2f);
		this.worldPosition = new Vector3f(loc.getX(), 0, loc.getY());
	}
	
	public void updateQuadTree(Camera camera) {
		float y = (camera.getPosition().getY() > EngineSettings.TERRAIN_SCALE_Y) ?
				EngineSettings.TERRAIN_SCALE_Y : camera.getPosition().getY();
		worldPosition.setY(y);
		updateChildNodes(camera);
		this.getChildren().stream()
			.map(child -> (TerrainNode) child)
			.forEach(child -> child.updateQuadTree(camera));
	}
	
	public void updateChildNodes(Camera camera) {
		float distance = (new Vector3f(camera.getPosition()).sub(worldPosition)).length();
		if (distance < EngineSettings.LOD_RANGES[lod]) {
			addChildNodes(lod + 1, camera);
		} else if (distance >= EngineSettings.LOD_RANGES[lod]) {
			removeChildNodes();
		}
	}
	
	public void removeChildren() {
		if (!isLeaf())
			setLeaf(true);
		
		if (!getChildren().isEmpty())
			getChildren().clear();
	}
	
	public void render(TerrainShader shader, VAO vao) {
		if (isLeaf()) {
			shader.loadLoDVariables(lod, index, gap, location);
			shader.loadLocalMatrix(localTransformationMatrix);
			GL11.glDrawArrays(GL40.GL_PATCHES, 0, 16);
		}
		getChildren().stream()
			.map(child -> (TerrainNode) child)
			.forEach(child -> child.render(shader, vao));
	}
	
	private void addChildNodes(int lod, Camera camera) {
		if (isLeaf())
			setLeaf(false);
		
		if (getChildren().isEmpty()) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					addChild(
							new TerrainNode(
								new Vector2f(location).add(new Vector2f(i * gap / 2f, j * gap / 2f)),
								lod,
								new Vector2f(i, j),
								camera
							)
					);
				}
			}
		}
		
	}
	
	private void removeChildNodes() {
		if (!isLeaf())
			setLeaf(true);
		
		if (!getChildren().isEmpty())
			getChildren().clear();
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
