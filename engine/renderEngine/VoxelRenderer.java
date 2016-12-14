package renderEngine;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import cameras.Camera;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import scene.ES;
import textures.ModelTexture;
import toolbox.Maths;
import toolbox.ObjectUtils;
import voxels.Area;
import voxels.Block;
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
	}
	
	public void render(Collection<Area> areas, Vector4f clipPlane, Collection<Light> lights, Camera camera, Matrix4f toShadowMapSpace) {
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
		for(Area area : areas) {
			if(area.isRendered()) {
				for(Chunk chunk : area.getChunks()) {
					if(chunk.isRendered()) {
						for(Block block : chunk.getBlocks()) {
							if(block.isRendered()) {
								for(Voxel voxel : block.getVoxels()) {
									prepareInstance(voxel);
									GL11.glDrawElements(GL11.GL_TRIANGLES, cube.getRawModel().getVertexCount(), 
											GL11.GL_UNSIGNED_INT, 0);
								}
							}
						}
						
					}
				}
			}
		}
		unbindTexturedModel();
		shader.stop();
	}
	
	private void prepareInstance(Voxel voxel) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(voxel.getPosition(), 0, 0, 0, 1);
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
