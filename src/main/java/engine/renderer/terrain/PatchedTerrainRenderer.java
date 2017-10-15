package renderer.terrain;

import core.settings.EngineSettings;
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
	
	public PatchedTerrainRenderer() {
		this.shader = new PatchedTerrainShader();
		shader.start();
		shader.loadScale(EngineSettings.SCALE_Y);
		shader.loadTessellationVariables(EngineSettings.TESSELLATION_FACTOR, EngineSettings.TESSELLATION_SLOPE, EngineSettings.TESSELLATION_SHIFT);
		shader.loadLodMorphAreaArray(EngineSettings.lod_morph_areas);
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
			//((TerrainNode) node).render(shader, patchVao);
		}
		patchVao.unbind();
		shader.stop();
	}
	
	public void clean() {
		shader.clean();
	}

}

