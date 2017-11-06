package object.terrain.terrain;

import java.util.stream.Stream;

import core.settings.EngineSettings;
import manager.octree.Node;
import object.camera.ICamera;
import primitive.buffer.Loader;
import primitive.buffer.VAO;
import tool.EngineUtils;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class TerrainQuadTree extends Node {
	
	private static int rootNodes = 8;
	private Matrix4f worldTransformationMatrix;
	private VAO vao;
	
	public TerrainQuadTree(ICamera camera) {
		float[] positions = this.generateVertexData();
		this.vao = Loader.getInstance().getVertexLoader().loadPatchToVAO(positions, 16);
		
		for(int i = 0; i < rootNodes; i++) {
			for(int j = 0; j < rootNodes; j++) {
				this.addChild(new TerrainNode(new Vector2f(i/ (float) rootNodes, j / (float) rootNodes), 0, new Vector2f(i, j), camera));
			}
		}
		this.worldTransformationMatrix = new Matrix4f();
		this.worldTransformationMatrix.translate(new Vector3f(-EngineSettings.SCALE_XZ / 2f, 0, -EngineSettings.SCALE_XZ / 2f));
		this.worldTransformationMatrix.scale(new Vector3f(EngineSettings.SCALE_XZ, EngineSettings.SCALE_Y, EngineSettings.SCALE_XZ));
	}
	
	public void updateQuadTree(ICamera camera) {
		for(Node node: getChildren()) {
			((TerrainNode) node).updateQuadTree(camera);
		}
	}

	private float[] generateVertexData() {
		Vector3f[] vertices = new Vector3f[16];
		
		int index = 0;
		
		vertices[index++] = new Vector3f(0, 0, 0);
		vertices[index++] = new Vector3f(0.333f, 0,  0);
		vertices[index++] = new Vector3f(0.666f, 0, 0);
		vertices[index++] = new Vector3f(1, 0, 0);
		
		vertices[index++] = new Vector3f(0, 0, 0.333f);
		vertices[index++] = new Vector3f(0.333f, 0, 0.333f);
		vertices[index++] = new Vector3f(0.666f, 0, 0.333f);
		vertices[index++] = new Vector3f(1, 0, 0.333f);
		
		vertices[index++] = new Vector3f(0, 0, 0.666f);
		vertices[index++] = new Vector3f(0.333f, 0, 0.666f);
		vertices[index++] = new Vector3f(0.666f, 0, 0.666f);
		vertices[index++] = new Vector3f(1, 0, 0.666f);
		
		vertices[index++] = new Vector3f(0, 0, 1);
		vertices[index++] = new Vector3f(0.333f, 0, 1);
		vertices[index++] = new Vector3f(0.666f, 0, 1);
		vertices[index++] = new Vector3f(1, 0, 1);
		
		Object[] positionArray = Stream.of(vertices)
				.flatMap(vertex -> Stream.of(vertex.x, vertex.y, vertex.z))
				.toArray();
		
		float[] positions = EngineUtils.toFloatArray(positionArray);
		return positions;
	}
	
	public Matrix4f getWorldMatrix() {
		return this.worldTransformationMatrix;
	}
	
	public static int getRootNodes() {
		return rootNodes;
	}

	public static void setRootNodes(int rootNodes) {
		TerrainQuadTree.rootNodes = rootNodes;
	}

	public VAO getVao() {
		return vao;
	}

}
