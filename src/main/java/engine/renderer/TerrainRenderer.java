package renderer;

import java.util.Collection;

import org.lwjgl.util.vector.Vector4f;

import core.EngineMain;
import core.settings.EngineSettings;
import manager.octree.Node;
import object.camera.Camera;
import object.light.Light;
import object.terrain.Terrain;
import object.terrain.TerrainNode;
import object.terrain.TerrainQuadTree;
import primitive.buffer.VAO;
import primitive.texture.Texture2D;
import primitive.texture.material.TerrainMaterial;
import primitive.texture.terrain.TerrainTexturePack;
import shader.terrain.TerrainShader;
import tool.GraphicUtils;
import tool.math.Matrix4f;
import tool.math.vector.Color;

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer(Matrix4f projectionMatrix) {
		this.shader = new TerrainShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadScale(EngineSettings.TERRAIN_SCALE_Y);
		shader.loadTessellationVariables(EngineSettings.TESSELLATION_FACTOR, EngineSettings.TESSELLATION_SLOPE, EngineSettings.TESSELLATION_SHIFT);
		shader.loadLodMorphAreaArray(EngineSettings.lod_morph_areas);
		shader.stop();
	}
	
	public void render(Collection<Terrain> terrains, Vector4f clipPlane, Collection<Light> lights,
			Camera camera, Matrix4f toShadowMapSpace) {
		
		if (EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_TERRAIN || 
				EngineMain.getWiredFrameMode() == EngineSettings.WIRED_FRAME_ENTITY_TERRAIN) {
			GraphicUtils.doWiredFrame(true);
		}
		
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera.getViewMatrix());
		shader.loadCameraPosition(camera.getPosition());
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		for (Terrain terrain : terrains) {
			if(camera.isMoved()) {
				terrain.updateQuadTree(camera);
			}
			prepareTerrain(terrain);
			for(Node node : terrain.getQuadTree().getChildren()) {
				((TerrainNode) node).render(shader, terrain.getQuadTree().getVao());
			}
			unbindTexture();
		}

		shader.stop();
		
		GraphicUtils.doWiredFrame(false);
	}

	public void render(Collection<Terrain> terrains, Collection<Light> lights, Camera camera) {
		shader.start();
		shader.loadClipPlane(EngineSettings.NO_CLIP);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera.getViewMatrix());
		shader.loadCameraPosition(camera.getPosition());
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		for (Terrain terrain : terrains) {
			prepareTerrain(terrain);
			for(Node node : terrain.getQuadTree().getChildren()) {
				((TerrainNode) node).render(shader, terrain.getQuadTree().getVao());
			}
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
