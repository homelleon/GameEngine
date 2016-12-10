package renderEngine;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import cameras.Camera;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import scene.ES;
import textures.ModelTexture;
import toolbox.Maths;
import toolbox.ObjectUtils;
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
		texture.setNumberOfRows(1);
		//this.texture = loader.loadCubeMap(ES.SKYBOX_TEXTURE_PATH, TEXTURE_FILES);
	}
	
	public void render(Collection<Chunk> chunks, Collection<Light> lights, Camera camera) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadLights(lights);
		for(Chunk chunk : chunks) {
			prepareModel(cube.getRawModel());
			bindTexture(texture);
			for(Voxel voxel : chunk.getVoxels()) {
				prepareInstance(voxel);
				//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
				GL11.glDrawElements(GL11.GL_TRIANGLES, cube.getRawModel().getVertexCount(), 
						GL11.GL_UNSIGNED_INT, 0);
			}
		}
		unbindTexture();
		shader.stop();
	}
	
	private void prepareInstance(Voxel voxel) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(voxel.getPosition(), 0, 0, 0, 1);
		shader.loadTranformationMatrix(transformationMatrix);
	}
	
	private void prepareModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
	}
	
	private void bindTexture(ModelTexture texture) {
		shader.loadNumberOfRows(texture.getNumberOfRows());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTexture() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

}
