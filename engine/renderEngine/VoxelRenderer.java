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
import toolbox.OGLUtils;
import toolbox.ObjectUtils;
import voxels.Block;
import voxels.Chunk;
import voxels.ChunkManager;
import voxels.VoxelShader;

public class VoxelRenderer {
	
	private static final float size = ES.VOXEL_BLOCK_SIZE / 2;
		
	private static final float[] VERTICES = {
			//front
			-size, size, size,
			size, size, size,
			size, -size, size,
			-size, -size, size,
			//back
			size, size, -size,
			-size, size, -size,
			-size, -size, -size,
			size, -size, -size,
			//top
			-size, size, -size,
			size, size, -size,
			size, size, size,
			-size, size, size,
			//bottom
			size, -size, -size,
			-size, -size, -size,
			-size, -size, size,
			size, -size, size,
			//left
			-size, size, -size,
			-size, size, size,
			-size, -size, size,
			-size, -size, -size,
			//right
			size, size, size,
			size, size, -size,
			size, -size, -size,
			size, -size, size
	};
	
	private static final float[] TEXTURES = {
			//front
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f,
			//back
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f,
			//top
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f,
			//bottom
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f, 
			//left
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f,
			//right
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f
	};
	
	private static final float[] NORMALS = {
			 0, 0, 1, 0, 0, 1, // front
			 0, 0, 1, 0, 0, 1, // front
			 0, 0, -1, 0, 0, -1, // back
			 0, 0, -1, 0, 0, -1, // back
			 0, 1, 0,  0, 1, 0, // top
			 0, 1, 0,  0, 1, 0, // top
			 0, -1, 0, 0, -1, 0, // bottom
			 0, -1, 0, 0, -1, 0, // bottom
			 -1, 0, 0, -1, 0, 0, // left
			 -1, 0, 0, -1, 0, 0, // left
			 1, 0, 0, 1, 0, 0,  // right
			 1, 0, 0, 1, 0, 0  // right
	};
	
	private static final int[] INDICES = {
			 0, 3, 1,  1, 3, 2, // front
			 4, 7, 5,  5, 7, 6, // back
			 8,11, 9,  9,11,10, // top
			 12,15,13, 13,15,14, // bottom
			 16,19,17, 17,19,18, // left
			 20,23,21, 21,23,22  // right
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
		RawModel rawModel = loader.loadToVAO(VERTICES, TEXTURES, NORMALS, INDICES);
		this.cube = new TexturedModel("cube", rawModel,
	    		new ModelTexture("bark", loader.loadTexture(ES.MODEL_TEXTURE_PATH, "crate")));
		this.texture = cube.getTexture();
		texture.setNumberOfRows(1);
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
//		OGLUtils.doWiredFrame(true);
		bindTexture(texture);
		int t = 0;
		for(int i = 0; i < chunker.getSize(); i++) {
			for(int x = 0; x <= ES.VOXEL_CHUNK_SIZE; x++) {
				for(int y = 0; y <= ES.VOXEL_CHUNK_SIZE; y++) {
					for(int z = 0; z <= ES.VOXEL_CHUNK_SIZE; z++) {
						if(!isAllSideCovered(chunker.getChunk(i), x, y, z)) {
							if(chunker.getChunk(i).getBlock(x, y, z).getIsActive()) {
								prepareInstance(chunker.getBlockPosition(i, x, y, z));
								GL11.glDrawElements(GL11.GL_TRIANGLES, 24, 
										GL11.GL_UNSIGNED_INT, 48);
//								GL11.glDrawElements(GL11.GL_TRIANGLES, cube.getRawModel().getVertexCount(), 
//									GL11.GL_UNSIGNED_INT, 0);
								t += 1;
							}
						}	
					}
				}
			}
		}
		System.out.println(t);
		OGLUtils.doWiredFrame(false);
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
		shader.loadOffset(0.0f, 0.0f);
		if(texture.isHasTransparency()) {
			OGLUtils.cullBackFaces(false);
		}
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTexturedModel() {
		OGLUtils.cullBackFaces(true);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private boolean isAllSideCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		int coveredSide = 0;
		if(isXPositiveCovered(chunk, x, y, z)) {
			coveredSide += 1;
		}
		
		if(isXNegativeCovered(chunk, x, y, z)) {
			coveredSide += 1;
		}
		
		if(isYPositiveCovered(chunk, x, y, z)) {
			coveredSide += 1;
		}
		
		if(isYNegativeCovered(chunk, x, y, z)) {
			coveredSide += 1;
		}
		
		if(isZPositiveCovered(chunk, x, y, z)) {
			coveredSide += 1;
		}
		
		if(isZNegativeCovered(chunk, x, y, z)) {
			coveredSide += 1;
		}
		
		if(coveredSide == 6) {
			isCovered = true;
		}
		return isCovered;
	}
	
	private boolean isXPositiveCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.blockExist(x + 1, y, z)) {
			if(chunk.getBlock(x + 1, y, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isXNegativeCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.blockExist(x - 1, y, z)) {
			if(chunk.getBlock(x - 1, y, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isYPositiveCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.blockExist(x, y + 1, z)) {
			if(chunk.getBlock(x, y + 1, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isYNegativeCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.blockExist(x, y - 1, z)) {
			if(chunk.getBlock(x, y - 1, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isZPositiveCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.blockExist(x, y, z + 1)) {
			if(chunk.getBlock(x, y, z + 1).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isZNegativeCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.blockExist(x, y, z - 1)) {
			if(chunk.getBlock(x, y, z - 1).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}

}
