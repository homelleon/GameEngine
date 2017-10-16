package renderer.terrain;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import manager.octree.Node;
import object.camera.ICamera;
import object.light.ILight;
import object.terrain.terrain.ITerrain;
import object.terrain.terrain.TerrainNode;
import object.terrain.terrain.TerrainQuadTree;
import object.texture.terrain.TerrainTexturePack;
import primitive.buffer.PatchVAO;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import shader.terrain.TerrainShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer(Matrix4f projectionMatrix) {
		this.shader = new TerrainShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadScale(EngineSettings.SCALE_Y);
		shader.loadTessellationVariables(EngineSettings.TESSELLATION_FACTOR, EngineSettings.TESSELLATION_SLOPE, EngineSettings.TESSELLATION_SHIFT);
		shader.loadLodMorphAreaArray(EngineSettings.lod_morph_areas);
		shader.connectTextureUnits();
		shader.stop();
	}

	public void render(Collection<ITerrain> terrains, Vector4f clipPlane, Collection<ILight> lights,
			ICamera camera, Matrix4f toShadowMapSpace) {
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE);
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera.getViewMatrix());
		shader.loadCameraPosition(camera.getPosition());
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);

		
		for (ITerrain terrain : terrains) {
			if(camera.isMoved() || camera.isRotated()) {
				terrain.updateQuadTree(camera);
			}
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
			for(Node node : terrain.getQuadTree().getChildren()) {
				((TerrainNode) node).render(shader, terrain.getQuadTree().getVao());
			}
			unbindTexture();
		}

		shader.stop();
	}

	public void renderLow(Collection<ITerrain> terrains, Collection<ILight> lights, ICamera camera) {
		shader.start();
		shader.loadClipPlane(EngineSettings.NO_CLIP);
		shader.loadSkyColour(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE);
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera.getViewMatrix());
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);

		for (ITerrain terrain : terrains) {
			prepareTerrain(terrain);
			loadModelMatrix(terrain);
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

	private void prepareTerrain(ITerrain terrain) {
		TerrainQuadTree terrainTree = (TerrainQuadTree) terrain.getQuadTree();
		PatchVAO patchVao = terrainTree.getVao();
		patchVao.bind();
//		Mesh mesh = terrain.getModel();
//		VAO vao = mesh.getVAO();
//		vao.bind(0, 1, 2);
		bindTexture(terrain);
		shader.loadShineVariables(1, 0);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	}

	private void bindTexture(ITerrain terrain) {
		TerrainTexturePack texturePack = terrain.getTexturePack();
		texturePack.getBackgroundTexture().bind(0);
		texturePack.getRTexture().bind(1);
		texturePack.getGTexture().bind(2);
		texturePack.getBTexture().bind(3);
		terrain.getBlendMap().bind(4);
	}

	private void unbindTexture() {
		//VAO.unbind(0, 1, 2);
		PatchVAO.unbind();
	}

	private void loadModelMatrix(ITerrain terrain) {
		Matrix4f transformationMatrix = Maths
				.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
		shader.loadTranformationMatrix(transformationMatrix);
	}

}
