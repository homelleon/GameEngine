package renderer;

import java.util.Collection;

import org.lwjgl.util.vector.Vector4f;

import core.EngineMain;
import core.settings.EngineSettings;
import object.terrain.Terrain;
import object.terrain.TerrainNode;
import object.terrain.TerrainQuadTree;
import primitive.buffer.VAO;
import primitive.texture.Texture2D;
import primitive.texture.material.TerrainMaterial;
import primitive.texture.terrain.TerrainTexturePack;
import scene.Scene;
import shader.terrain.TerrainShader;
import tool.GraphicUtils;
import tool.math.Matrix4f;
import tool.math.vector.Color;

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer(Matrix4f projectionMatrix) {
		shader = new TerrainShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadScale(EngineSettings.TERRAIN_SCALE_Y);
		shader.loadTessellationVariables(EngineSettings.TESSELLATION_FACTOR, EngineSettings.TESSELLATION_SLOPE, EngineSettings.TESSELLATION_SHIFT);
		shader.loadLodMorphAreaArray(EngineSettings.lod_morph_areas);
		shader.stop();
	}
	
	public void render(Collection<Terrain> terrains, Scene scene, Vector4f clipPlane, Matrix4f toShadowMapSpace) {
		
		if (EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_TERRAIN || 
				EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY_TERRAIN) {
			GraphicUtils.doWiredFrame(true);
		}
		
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(scene.getLights().getAll());
		shader.loadViewMatrix(scene.getCamera().getViewMatrix());
		shader.loadCameraPosition(scene.getCamera().getPosition());
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		for (Terrain terrain : terrains) {
			if (scene.getCamera().isMoved())
				terrain.updateQuadTree(scene.getCamera());
			prepareTerrain(terrain);
			terrain.getQuadTree().getChildren()
				.forEach(node -> ((TerrainNode) node).render(shader, terrain.getQuadTree().getVao()));
			unbindTexture();
		}

		shader.stop();
		
		GraphicUtils.doWiredFrame(false);
	}

	public void render(Collection<Terrain> terrains, Scene scene) {
		shader.start();
		shader.loadClipPlane(EngineSettings.NO_CLIP);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(scene.getLights().getAll());
		shader.loadViewMatrix(scene.getCamera().getViewMatrix());
		shader.loadCameraPosition(scene.getCamera().getPosition());
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		for (Terrain terrain : terrains) {
			prepareTerrain(terrain);
			terrain.getQuadTree().getChildren()
				.forEach(node -> ((TerrainNode) node).render(shader, terrain.getQuadTree().getVao()));
			unbindTexture();

		}

		shader.stop();
	}

	public void clean() {
		shader.clean();
	}

	private void prepareTerrain(Terrain terrain) {
		TerrainQuadTree terrainTree = (TerrainQuadTree) terrain.getQuadTree();
		VAO vao = terrainTree.getVao();
		vao.bind(0);
		bindTexture(terrain);
		shader.loadShineVariables(1, 0);
		shader.loadWorldMatrix(terrainTree.getWorldMatrix());
	}

	private void bindTexture(Terrain terrain) {
		TerrainMaterial material = terrain.getMaterial();
		TerrainTexturePack texturePack = material.getTexturePack();
		texturePack.getBackgroundTexture().bilinearFilter();
		texturePack.getBackgroundTexture().bind(0);
		texturePack.getRTexture().bind(1);
		texturePack.getGTexture().bind(2);
		texturePack.getBTexture().bind(3);
		material.getBlendMap().bind(4);
		material.getHeightMap().bind(7);
		material.getNormalMap().bind(8);
	}

	private void unbindTexture() {
		VAO.unbind(0);
		Texture2D.unbind();
	}

}
