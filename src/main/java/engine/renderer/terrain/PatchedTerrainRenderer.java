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
	public final static float SCALE_Y = 5f;
	
	public final static int[] LOD_RANGES = {1750, 874, 386, 192, 100, 50, 0, 0};
	public static int[] lod_morph_areas;
	
	public static int[] getLodMorphAreas() {
		return lod_morph_areas;
	}
	
	public PatchedTerrainRenderer() {
		lod_morph_areas = new int[LOD_RANGES.length];
		for(int i = 0; i < LOD_RANGES.length; i++) {
			if(LOD_RANGES[i] == 0) {
				lod_morph_areas[i] = 0;
			} else { 
				lod_morph_areas[i] = LOD_RANGES[i] - (int) ((SCALE_XZ / TerrainQuadTree.getRootNodes()) / Math.pow(2, i + 1));
			}
		}
		this.shader = new PatchedTerrainShader();
		shader.start();
		shader.loadScale(SCALE_Y);
		shader.loadTessellationVariables(TESSELLATION_FACTOR, TESSELLATION_SLOPE, TESSELLATION_SHIFT);
		shader.loadLodMorphAreaArray(lod_morph_areas);
		shader.stop();
	}
	
	public void render(PatchedTerrain terrain, IScene scene, Matrix4f projectionViewMatrix) {
		shader.start();
		shader.loadProjectionViewMatrix(projectionViewMatrix);
		shader.loadCameraPosition(scene.getCamera().getPosition());
		TerrainQuadTree terrainTree = (TerrainQuadTree) terrain.getChildren().get(0);
		PatchVAO patchVao = terrainTree.getVao();
		patchVao.bind();
		for(Node node : terrainTree.getChildren()) {
			((TerrainNode) node).render(shader, patchVao);
		}
		patchVao.unbind();
		shader.stop();
	}
	
	public void clean() {
		shader.clean();
	}

}

