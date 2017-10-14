package object.terrain.terrain;

import java.util.stream.Stream;

import manager.octree.Node;
import object.camera.ICamera;
import primitive.buffer.Loader;
import primitive.buffer.PatchVAO;
import renderer.terrain.PatchedTerrainRenderer;
import tool.EngineUtils;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class TerrainQuadTree extends Node {
	
	private static int rootNodes = 8;
	private Matrix4f worldTransformationMatrix;
	private PatchVAO vao;
	
	public TerrainQuadTree(ICamera camera) {
		float[] positions = this.generateVertexData();
		this.vao = Loader.getInstance().getVertexLoader().loadPatchToVAO(positions, 16);
		for(int i = 0; i < rootNodes; i++) {
			for(int j = 0; j < rootNodes; j++) {
				this.addChild(new TerrainNode(new Vector2f(i/ (float) rootNodes, j / (float) rootNodes), 0, new Vector2f(i, j), camera));
			}
		}
		this.worldTransformationMatrix = new Matrix4f();
		this.worldTransformationMatrix.scale(new Vector3f(PatchedTerrainRenderer.SCALE_XZ, PatchedTerrainRenderer.SCALE_Y, PatchedTerrainRenderer.SCALE_XZ));
		this.worldTransformationMatrix.translate(new Vector3f(-PatchedTerrainRenderer.SCALE_XZ / 2f, 0, -PatchedTerrainRenderer.SCALE_XZ));
	}
	
	public void updateQuadTree(ICamera camera) {
		for(Node node: getChildren()) {
			((TerrainNode) node).updateQuadTree(camera);
		}
	}

	public float[] generateVertexData() {
		Vector2f[] vertices = new Vector2f[16];
		
		int index = 0;
		
		vertices[index++] = new Vector2f(0, 0);
		vertices[index++] = new Vector2f(0.333f, 0);
		vertices[index++] = new Vector2f(0.666f, 0);
		vertices[index++] = new Vector2f(1, 0);
		
		vertices[index++] = new Vector2f(0, 0.333f);
		vertices[index++] = new Vector2f(0.333f, 0.333f);
		vertices[index++] = new Vector2f(0.666f, 0.333f);
		vertices[index++] = new Vector2f(1, 0.333f);
		
		vertices[index++] = new Vector2f(0, 0.666f);
		vertices[index++] = new Vector2f(0.333f, 0.666f);
		vertices[index++] = new Vector2f(0.666f, 0.666f);
		vertices[index++] = new Vector2f(1, 0.666f);
		
		vertices[index++] = new Vector2f(0, 1);
		vertices[index++] = new Vector2f(0.333f, 1);
		vertices[index++] = new Vector2f(0.666f, 1);
		vertices[index++] = new Vector2f(1, 1);
		
		Object[] positionArray = Stream.of(vertices)
				.flatMap(vertex -> Stream.of(vertex.x, vertex.y))
				.toArray();
		
		float[] positions = EngineUtils.toFloatArray(positionArray);
		
		return positions;
	}
	public static int getRootNodes() {
		return rootNodes;
	}

	public static void setRootNodes(int rootNodes) {
		TerrainQuadTree.rootNodes = rootNodes;
	}

	public PatchVAO getVao() {
		return vao;
	}

}
