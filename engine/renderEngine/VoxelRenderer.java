package renderEngine;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import cameras.Camera;
import models.RawModel;
import scene.ES;
import terrains.TerrainShader;
import textures.ModelTexture;
import toolbox.Maths;
import voxels.Chunk;
import voxels.Voxel;
import voxels.VoxelShader;

public class VoxelRenderer {
	
	private static final float[] VERTICES = {        
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,

		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,

		     ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,

		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,

		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE,  ES.VOXEL_SIZE, -ES.VOXEL_SIZE,

		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		     ES.VOXEL_SIZE, -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,
		    -ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE,
		    ES.VOXEL_SIZE, -ES.VOXEL_SIZE,  ES.VOXEL_SIZE
		};
	
	private static String[] TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};
	
	private RawModel cube;
	private int texture;
	private VoxelShader shader;
	Matrix4f projectionMatrix;
	Loader loader;
	
	public VoxelRenderer(Loader loader, Matrix4f projectionMatrix) {
		this.shader = new VoxelShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
		this.loader = loader;
	}
	
	public void render(Collection<Chunk> chunks, Camera camera) {
		shader.start();
		cube = loader.loadToVAO(VERTICES, 3);
		texture = loader.loadCubeMap(ES.SKYBOX_TEXTURE_PATH, TEXTURE_FILES);
		shader.loadViewMatrix(camera);
		for(Chunk chunk : chunks) {
			for(int i = 0; i < ES.CHUNK_SIZE/16; i++) {
				prepareInstance(chunk.getVoxel(i));
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
			}
		}
		shader.stop();
	}
	
	private void prepareInstance(Voxel voxel) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(voxel.getPosition(),
				1, 1, 1, 1);
		shader.loadTranformationMatrix(transformationMatrix);
	}
	
	private void prepareModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
	}
	
	private void bindTexture(ModelTexture texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTexture(ModelTexture texture) {
		
	}

}
