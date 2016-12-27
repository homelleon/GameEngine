package renderEngine;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import scene.ES;
import textures.ModelTexture;
import toolbox.Maths;
import toolbox.ObjectUtils;
import voxels.Block;
import voxels.Chunk;
import voxels.ChunkManager;
import voxels.VoxelShader;

public class VoxelRenderer {
	
	private static final float size = ES.VOXEL_BLOCK_SIZE;
	
	private static final float[] VERTICES = {        
		    // -z
			-size,  size, -size,
		    -size, -size, -size,
		    size, -size, -size,
		    size, -size, -size,
		    size,  size, -size,
		    -size,  size, -size,
		    // -x
		    -size, -size,  size,
		    -size, -size, -size,
		    -size,  size, -size,
		    -size,  size, -size,
		    -size,  size,  size,
		    -size, -size,  size,
		    // +x
		    size, -size, -size,
		    size, -size,  size,
		    size,  size,  size,
		    size,  size,  size,
		    size,  size, -size,
		    size, -size, -size,
		    // +z
		    -size, -size,  size,
		    -size,  size,  size,
		    size,  size,  size,
		    size,  size,  size,
		    size, -size,  size,
		    -size, -size,  size,
		    // +y
		    -size,  size, -size,
		    size,  size, -size,
		    size,  size,  size,
		    size,  size,  size,
		    -size,  size,  size,
		    -size,  size, -size,
		    // -y
		    -size, -size, -size,
		    -size, -size,  size,
		    size, -size, -size,
		    size, -size, -size,
		    -size, -size,  size,
		    size, -size,  size
		};
	
	private static final float[] VERTICES_X = {
			size, -size, -size,
		    size, -size,  size,
		    size,  size,  size,
		    size,  size,  size,
		    size,  size, -size,
		    size, -size, -size
	};
	
	private static final float[] VERTICES_MIN_X = {
			-size, -size,  size,
		    -size, -size, -size,
		    -size,  size, -size,
		    -size,  size, -size,
		    -size,  size,  size,
		    -size, -size,  size
	};
	
	private static final float[] VERTICES_Y = {
			-size,  size, -size,
		    size,  size, -size,
		    size,  size,  size,
		    size,  size,  size,
		    -size,  size,  size,
		    -size,  size, -size
	};
	
	private static final float[] VERTICES_MIN_Y = {
			-size, -size, -size,
		    -size, -size,  size,
		    size, -size, -size,
		    size, -size, -size,
		    -size, -size,  size,
		    size, -size,  size
	};
	
	private static final float[] VERTICES_Z = {
			-size, -size,  size,
		    -size,  size,  size,
		    size,  size,  size,
		    size,  size,  size,
		    size, -size,  size,
		    -size, -size,  size
	};
	
	private static final float[] VERTICES_MIN_Z = {
			-size,  size, -size,
		    -size, -size, -size,
		    size, -size, -size,
		    size, -size, -size,
		    size,  size, -size,
		    -size,  size, -size
	};
	
	private static final float[] TEXTURES = {
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0,
			0, 1, 0
	};
	
	private static final float[] NORMALS = {
			1, 0, 0, 
			1, 0, 0,
			1, 0, 0,
			1, 0, 0,
			1, 0, 0,
			1, 0, 0,
			1, 0, 0, 
			1, 0, 0,
			1, 0, 0,
			1, 0, 0,
			1, 0, 0,
			1, 0, 0
	};
	
	private static final int[] INDICES = {
			0, -1, 0, 
			0, -1, 0,
			0, -1, 0,
			0, -1, 0,
			0, -1, 0,
			0, -1, 0,
			0, -1, 0, 
			0, -1, 0,
			0, -1, 0,
			0, -1, 0,
			0, -1, 0,
			0, -1, 0	
	};
	
	private static String[] TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};
	
	private TexturedModel cube;
	private ModelTexture texture;
	private VoxelShader shader;
	Matrix4f projectionMatrix;
	Loader loader;
	
	public VoxelRenderer(Loader loader, Matrix4f projectionMatrix) {
		this.shader = new VoxelShader();
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.connectTextureUnits();
		this.shader.stop();
		this.loader = loader;
		this.cube = ObjectUtils.loadStaticModel("cube", "cube1", loader);
		this.texture = cube.getTexture();
	}
	
	public void render(ChunkManager chunker, Vector4f clipPlane, Collection<Light> lights, Camera camera, Matrix4f toShadowMapSpace) {
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(ES.DISPLAY_RED, ES.DISPLAY_GREEN, ES.DISPLAY_BLUE);
		shader.loadFogDensity(ES.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(ES.SHADOW_DISTANCE, ES.SHADOW_MAP_SIZE, ES.SHADOW_TRANSITION_DISTANCE, ES.SHADOW_PCF);
		prepareModel(cube.getRawModel());
		bindTexture(texture);
		for(int i = 0; i < chunker.getSize(); i++) {
			for(int x = 0; x < ES.VOXEL_CHUNK_SIZE; x++) {
				for(int y = 0; y < ES.VOXEL_CHUNK_SIZE; y++) {
					for(int z = 0; z < ES.VOXEL_CHUNK_SIZE; z++) {
						if(chunker.getChunk(i).getBlock(x, y, z).getIsActive()) {
							prepareInstance(chunker.getBlockPosition(i, x, y, z));
							GL11.glDrawElements(GL11.GL_TRIANGLES, cube.getRawModel().getVertexCount(), 
									GL11.GL_UNSIGNED_INT, 0);
						}	
					}
				}
			}
		}
		unbindTexturedModel();
		shader.stop();
	}
	
	private void prepareInstance(Vector3f position) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(position, 0, 0, 0, 1);
		shader.loadTranformationMatrix(transformationMatrix);
	}
	
	private void prepareModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
	}
	
	private void bindTexture(ModelTexture texture) {
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadNumberOfRows(texture.getNumberOfRows());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
		shader.loadUsesSpecularMap(texture.hasSpecularMap());
		if(texture.hasSpecularMap()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getSpecularMap());
		}
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

}
