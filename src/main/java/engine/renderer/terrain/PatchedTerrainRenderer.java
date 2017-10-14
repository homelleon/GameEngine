package renderer.terrain;

import manager.octree.Node;
import object.scene.IScene;
import object.terrain.terrain.PatchedTerrain;
import object.terrain.terrain.TerrainNode;
import object.terrain.terrain.TerrainQuadTree;
import primitive.buffer.PatchVAO;
import shader.terrain.PatchedTerrainShader;
import tool.math.Matrix4f;

public class PatchedTerrainRenderer {
	
	private PatchedTerrainShader shader;
	
	public final static int TESSELLATION_FACTOR = 600;
	public final static float TESSELLATION_SLOPE = 1.8f;
	public final static float TESSELLATION_SHIFT = 0.1f;
	
	public final static float SCALE_XZ = 6000f;
	public final static float SCALE_Y = -5f;
	
	public final static int[] LOD_MORPH_AREAS = {1750, 874, 386, 192, 100, 50, 0, 0};
	
	public PatchedTerrainRenderer() {
		this.shader = new PatchedTerrainShader();
		shader.start();
		shader.loadScale(SCALE_Y);
		shader.loadTessellationVariables(TESSELLATION_FACTOR, TESSELLATION_SLOPE, TESSELLATION_SHIFT);
		shader.loadLodMorphAreaArray(LOD_MORPH_AREAS);
	}
	
	public void render(PatchedTerrain terrain, IScene scene, Matrix4f projectionViewMatrix) {
		shader.start();
		shader.loadProjectionViewMatrix(projectionViewMatrix);
		shader.loadCameraPosition(scene.getCamera().getPosition());
		TerrainQuadTree terrainTree = (TerrainQuadTree) terrain.getChildren().get(0);
		PatchVAO vao = terrainTree.getVao();
		vao.bind();
		for(Node node : terrain.getChildren().get(0).getChildren()) {
			((TerrainNode) node).render(shader, vao);
		}
		vao.unbind();
		shader.stop();
	}
	
	public void clean() {
		shader.clean();
	}

}

