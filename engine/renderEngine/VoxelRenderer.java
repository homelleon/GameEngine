package renderEngine;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
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
import toolbox.Vector3i;
import voxels.Chunk;
import voxels.ChunkManager;
import voxels.FaceCullingData;
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
			if(chunker.getChunk(i).getIsAcitve()) {
				FaceCullingData chunkFCData = isNeedChunkCulling(chunker, i);
				if(!isAllFaceCulled(chunkFCData)) {
					for(int x = 0; x <= ES.VOXEL_CHUNK_SIZE; x++) {					
						for(int y = 0; y <= ES.VOXEL_CHUNK_SIZE; y++) {
							for(int z = 0; z <= ES.VOXEL_CHUNK_SIZE; z++) {
								FaceCullingData blockFCData = isNeedBlockCulling(chunker.getChunk(i), x, y, z);
								if(!isAllFaceCulled(blockFCData)) {
									if(chunker.getChunk(i).getBlock(x, y, z).getIsActive()) {
										prepareInstance(chunker.getBlockPosition(i, x, y, z));
										for(int j = 0; j < 6; j ++) {
											int startCount = 0;
											if(blockFCData.getFace(j) == false) {										
												GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, 6, 6, GL11.GL_UNSIGNED_INT, 24 * j);
												t += 1;
											}
										}
										blockFCData = null;
										chunkFCData = null;
									}
								}	
							}
						}
					}
				}
			}
		}
		//System.out.println(t);
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
	
	private FaceCullingData isNeedBlockCulling(Chunk chunk, int x, int y, int z) {
		FaceCullingData fcData = new FaceCullingData();
		if(isFrontCovered(chunk, x, y, z)) {
			fcData.setFaceRendering(0, true);
		}
		
		if(isBackCovered(chunk, x, y, z)) {
			fcData.setFaceRendering(1, true);
		}
		
		if(isTopCovered(chunk, x, y, z)) {
			fcData.setFaceRendering(2, true);
		}
		
		if(isBottomCovered(chunk, x, y, z)) {
			fcData.setFaceRendering(3, true);
		}
		
		if(isLeftCovered(chunk, x, y, z)) {
			fcData.setFaceRendering(4, true);
		}
		
		if(isRightCovered(chunk, x, y, z)) {
			fcData.setFaceRendering(5, true);
		}
		
		return fcData;
	}
	
	private FaceCullingData isNeedChunkCulling(ChunkManager chunker, int index) {
		FaceCullingData fcData = new FaceCullingData();
		if(isFrontCovered(chunker, index)) {
			fcData.setFaceRendering(0, true);
		}
		
		if(isBackCovered(chunker, index)) {
			fcData.setFaceRendering(1, true);
		}
		
		if(isTopCovered(chunker, index)) {
			fcData.setFaceRendering(2, true);
		}
		
		if(isBottomCovered(chunker, index)) {
			fcData.setFaceRendering(3, true);
		}
		
		if(isLeftCovered(chunker, index)) {
			fcData.setFaceRendering(4, true);
		}
		
		if(isRightCovered(chunker, index)) {
			fcData.setFaceRendering(5, true);
		}
		
		return fcData;
	}
	
	private boolean isAllFaceCulled(FaceCullingData fcData) {
		boolean isAllFaceCulled = true;
		for(int i = 0; i < 6; i ++) {
			if (fcData.getFace(i) == false) {
				isAllFaceCulled = false;
			}
		}
		return isAllFaceCulled;
	}
	
	private boolean isRightCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.isBlockExist(x + 1, y, z)) {
			if(chunk.getBlock(x + 1, y, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isLeftCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.isBlockExist(x - 1, y, z)) {
			if(chunk.getBlock(x - 1, y, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isTopCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.isBlockExist(x, y + 1, z)) {
			if(chunk.getBlock(x, y + 1, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isBottomCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.isBlockExist(x, y - 1, z)) {
			if(chunk.getBlock(x, y - 1, z).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isFrontCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.isBlockExist(x, y, z + 1)) {
			if(chunk.getBlock(x, y, z + 1).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isBackCovered(Chunk chunk, int x, int y, int z) {
		boolean isCovered = false;
		if(chunk.isBlockExist(x, y, z - 1)) {
			if(chunk.getBlock(x, y, z - 1).getIsActive()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isFrontCovered(ChunkManager chunker, int index) {
		boolean isCovered = false;
		Vector3i position = chunker.getChunkXYZPosition(index);
		position.z += 1;
		if(chunker.isChunkExist(position)) {
			if(chunker.getChunk(position).getIsAcitve()) {
				isCovered = true;
			}
		}
		return isCovered;
	}
	
	private boolean isBackCovered(ChunkManager chunker, int index) {
		boolean isCovered = false;
		Vector3i position = chunker.getChunkXYZPosition(index);
		position.z -= 1;
		if(chunker.isChunkExist(position)) {
			if(chunker.getChunk(position).getIsAcitve()) {
				isCovered = true;
			}
		}
		return isCovered;
	}

	
	private boolean isTopCovered(ChunkManager chunker, int index) {
		boolean isCovered = false;
		Vector3i position = chunker.getChunkXYZPosition(index);
		position.y += 1;
		if(chunker.isChunkExist(position)) {
			if(chunker.getChunk(position).getIsAcitve()) {
				isCovered = true;
			}
		}
		return isCovered;
	}

	
	private boolean isBottomCovered(ChunkManager chunker, int index) {
		boolean isCovered = false;
		Vector3i position = chunker.getChunkXYZPosition(index);
		position.y -= 1;
		if(chunker.isChunkExist(position)) {
			if(chunker.getChunk(position).getIsAcitve()) {
				isCovered = true;
			}
		}
		return isCovered;
	}

	
	private boolean isLeftCovered(ChunkManager chunker, int index) {
		boolean isCovered = false;
		Vector3i position = chunker.getChunkXYZPosition(index);
		position.x -= 1;
		if(chunker.isChunkExist(position)) {
			if(chunker.getChunk(position).getIsAcitve()) {
				isCovered = true;
			}
		}
		return isCovered;
	}

	
	private boolean isRightCovered(ChunkManager chunker, int index) {
		boolean isCovered = false;
		Vector3i position = chunker.getChunkXYZPosition(index);
		position.x += 1;
		if(chunker.isChunkExist(position)) {
			if(chunker.getChunk(position).getIsAcitve()) {
				isCovered = true;
			}
		}
		return isCovered;
	}

	
	

}
