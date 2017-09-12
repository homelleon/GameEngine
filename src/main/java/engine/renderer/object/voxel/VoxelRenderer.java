package renderer.object.voxel;

import java.util.Collection;
import java.util.stream.IntStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import manager.voxel.ChunkIndex;
import manager.voxel.IChunkManager;
import object.camera.ICamera;
import object.light.ILight;
import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.texture.model.ModelTexture;
import object.voxel.Chunk;
import object.voxel.data.FaceCullingData;
import renderer.loader.Loader;
import renderer.viewCulling.frustum.Frustum;
import shader.voxel.VoxelShader;
import tool.math.Maths;
import tool.math.vector.Vector3i;
import tool.openGL.OGLUtils;

public class VoxelRenderer {

	private static final float SIZE = EngineSettings.VOXEL_BLOCK_SIZE / 2;
	private static final float BLOCK_RADIUS = 2 * SIZE;
	private static final float CHUNK_RADIUS = 2 * BLOCK_RADIUS * EngineSettings.VOXEL_CHUNK_SIZE;

	private static final float[] VERTICES = {
			// front
			-SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE,
			// back
			SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE,
			// top
			-SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE,
			// bottom
			SIZE, -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE,
			// left
			-SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE,
			// right
			SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE };

	private static final float[] TEXTURES = {
			// front
			0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
			// back
			0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
			// top
			0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
			// bottom
			0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
			// left
			0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
			// right
			0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f };

	private static final float[] NORMALS = { 0, 0, 1, 0, 0, 1, // front
			0, 0, 1, 0, 0, 1, // front
			0, 0, -1, 0, 0, -1, // back
			0, 0, -1, 0, 0, -1, // back
			0, 1, 0, 0, 1, 0, // top
			0, 1, 0, 0, 1, 0, // top
			0, -1, 0, 0, -1, 0, // bottom
			0, -1, 0, 0, -1, 0, // bottom
			-1, 0, 0, -1, 0, 0, // left
			-1, 0, 0, -1, 0, 0, // left
			1, 0, 0, 1, 0, 0, // right
			1, 0, 0, 1, 0, 0 // right
	};

	private static final int[] INDICES = { 0, 3, 1, 1, 3, 2, // front
			4, 7, 5, 5, 7, 6, // back
			8, 11, 9, 9, 11, 10, // top
			12, 15, 13, 13, 15, 14, // bottom
			16, 19, 17, 17, 19, 18, // left
			20, 23, 21, 21, 23, 22 // right
	};

	/* local cube side constants */
	private static final int FRONT = 0;
	private static final int BACK = 1;
	private static final int TOP = 2;
	private static final int BOTTOM = 3;
	private static final int LEFT = 4;
	private static final int RIGHT = 5;

	private static String[] TEXTURE_FILES = { "right", "left", "top", "bottom", "back", "front" };

	private TexturedModel cube;
	private ModelTexture texture;
	private VoxelShader shader;

	Matrix4f projectionMatrix;

	public VoxelRenderer(Matrix4f projectionMatrix) {
		Loader loader = Loader.getInstance();
		this.shader = new VoxelShader();
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.connectTextureUnits();
		this.shader.stop();
		RawModel rawModel = loader.getVertexLoader().loadToVAO(VERTICES, TEXTURES, NORMALS, INDICES);
		this.cube = new TexturedModel("cube", rawModel,
				new ModelTexture("bark", loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, "crate")));
		this.texture = cube.getTexture();
		texture.setNumberOfRows(1);
	}

	public void render(IChunkManager chunkManager, Vector4f clipPlane, Collection<ILight> lights,
			ICamera camera, Matrix4f toShadowMapSpace, Frustum frustum) {
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(EngineSettings.DISPLAY_RED, EngineSettings.DISPLAY_GREEN, EngineSettings.DISPLAY_BLUE);
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		prepareModel(cube.getRawModel());
		bindTexture(texture);
		
		IntStream.range(0, chunkManager.getSize())
			.filter(i -> checkVisibility(frustum, chunkManager.getChunkPosition(i), CHUNK_RADIUS))
			.filter(i -> chunkManager.getChunk(i).getIsAcitve())
			.mapToObj(ChunkIndex::new)
			.flatMap(index -> IntStream.range(0, EngineSettings.VOXEL_CHUNK_SIZE + 1)
					.mapToObj(x -> new ChunkIndex(index.getI())
							.setX(x)))
			.flatMap(index -> IntStream.range(0, EngineSettings.VOXEL_CHUNK_SIZE + 1)
					.mapToObj(y -> new ChunkIndex(index.getI())
							.setX(index.getX()).setY(y)))
			.flatMap(index -> IntStream.range(0, EngineSettings.VOXEL_CHUNK_SIZE + 1)
					.mapToObj(z -> new ChunkIndex(index.getI())
							.setX(index.getX()).setY(index.getY()).setZ(z)))
			.map(index -> index.setFCD(isNeedBlockCulling(
					chunkManager.getChunk(
							index.getI()),index.getX(), index.getY(), index.getZ())))
			.filter(index -> !isAllFaceCulled(index.getFCD()))
			.filter(index -> chunkManager.getChunk(
					index.getI()).getBlock(index.getX(), index.getY(), index.getZ())
					.getIsActive())
			.map(index -> {
					prepareInstance(chunkManager.getBlockPosition(
							index.getI(), new Vector3i(
									index.getX(), index.getY(), index.getZ())));
					return index;
				})
			.forEach(index -> 
				IntStream.range(0, 6)
					.filter(face -> !index.getFCD().getFace(face))
					.forEachOrdered(this::drawElements)			
			);
		unbindTexturedModel();
		shader.stop();
	}
	
	private synchronized void drawElements(int face) {
		GL12.glDrawRangeElements(GL11.GL_TRIANGLES,0, 6, 6,
				GL11.GL_UNSIGNED_INT, 24 * face);
	}

	private boolean checkVisibility(Frustum frustum, Vector3f position, float radius) {
		boolean isVisible = false;
		float distance = frustum.distanceSphereInFrustum(position, radius);
		if (distance > 0 && distance <= EngineSettings.RENDERING_VIEW_DISTANCE) {
			isVisible = true;
		}
		return isVisible;
	}

	private synchronized void prepareInstance(Vector3f position) {
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
		if (texture.isHasTransparency()) {
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
		for (int face = 0; face < 6; face++) {
			if (isFaceCovered(chunk, x, y, z, face)) {
				fcData.setFaceRendering(face, true);
			}
		}

		return fcData;
	}

	private FaceCullingData isNeedChunkCulling(IChunkManager chunker, int index) {
		FaceCullingData fcData = new FaceCullingData();
		for (int face = 0; face < 6; face++) {
			if (isFaceCovered(chunker, index, face)) {
				fcData.setFaceRendering(face, true);
			}
		}

		return fcData;
	}

	private boolean isAllFaceCulled(FaceCullingData fcData) {
		boolean isAllFaceCulled = true;
		for (int i = 0; i < 6; i++) {
			if (!fcData.getFace(i)) {
				isAllFaceCulled = false;
			}
		}

		return isAllFaceCulled;
	}

	private boolean isFaceCovered(Chunk chunk, int x, int y, int z, int face) {
		boolean isCovered = false;
		Vector3i position = new Vector3i(x, y, z);
		switch (face) {
		case FRONT:
			position.z += 1;
			break;
		case BACK:
			position.z -= 1;
			break;
		case TOP:
			position.y += 1;
			break;
		case BOTTOM:
			position.y -= 1;
			break;
		case LEFT:
			position.x -= 1;
			break;
		case RIGHT:
			position.x += 1;
			break;
		}

		if (chunk.isBlockExist(position)) {
			if (chunk.getBlock(position).getIsActive()) {
				isCovered = true;
			}
		}

		return isCovered;
	}

	private boolean isFaceCovered(IChunkManager chunker, int index, int face) {
		boolean isCovered = false;
		Vector3i position = chunker.getChunkIndexVector(index);
		switch (face) {
		case FRONT:
			position.z += 1;
			break;
		case BACK:
			position.z -= 1;
			break;
		case TOP:
			position.y += 1;
			break;
		case BOTTOM:
			position.y -= 1;
			break;
		case LEFT:
			position.x -= 1;
			break;
		case RIGHT:
			position.x += 1;
			break;
		}
		if (chunker.isChunkExist(position)) {
			if (chunker.getChunk(position).getIsAcitve()) {
				isCovered = true;
			}
		}

		return isCovered;
	}

}
