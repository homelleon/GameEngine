package renderer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import manager.voxel.ChunkManager;
import object.camera.Camera;
import object.voxel.Chunk;
import object.voxel.FaceCullingData;
import primitive.buffer.Loader;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.model.Model;
import primitive.texture.material.Material;
import scene.Scene;
import shader.voxel.VoxelShader;
import tool.GraphicUtils;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector3f;
import tool.math.vector.Vector3i;

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

	private Model cube;
	private Material texture;
	private VoxelShader shader;

	Matrix4f projectionMatrix;

	public VoxelRenderer(Matrix4f projectionMatrix) {
		Loader loader = Loader.getInstance();
		shader = new VoxelShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
		Mesh rawModel = loader.getVertexLoader().loadToVAO(VERTICES, TEXTURES, NORMALS, INDICES);
		cube = new Model("cube", rawModel,
				new Material("bark", loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, "crate")));
		texture = cube.getMaterial();
		texture.getDiffuseMap().setNumberOfRows(1);
	}

	public void render(Scene scene, Vector4f clipPlane, Matrix4f toShadowMapSpace) {
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(new Color(EngineSettings.RED, EngineSettings.GREEN, EngineSettings.BLUE));
		shader.loadFogDensity(EngineSettings.FOG_DENSITY);
		shader.loadLights(scene.getLights().getAll());
		shader.loadViewMatrix(scene.getCamera());
		shader.loadToShadowSpaceMatrix(toShadowMapSpace);
		shader.loadShadowVariables(EngineSettings.SHADOW_DISTANCE, EngineSettings.SHADOW_MAP_SIZE,
				EngineSettings.SHADOW_TRANSITION_DISTANCE, EngineSettings.SHADOW_PCF);
		prepareModel(cube.getMesh());
		bindMaterial(texture);
		
		getAllBlocks(scene.getChunks(), scene.getCamera()).stream()
			.filter(generalIndex -> scene.getChunks().getBlockByGeneralIndex(generalIndex).getIsActive())
			.map(generalIndex -> {
				prepareInstance(scene.getChunks().getBlockPositionByGeneralIndex(generalIndex));
				return generalIndex;
			})
			.map(generalIndex -> isNeedBlockCulling(scene.getChunks(), generalIndex))
			.filter(blockCullingData -> !isAllFaceCulled(blockCullingData))
			.flatMap(blockCullingData -> IntStream.range(0, 6)
//					.filter(face -> !blockCullingData.getFace(face))
					.boxed()
			)
			.forEach(this::drawElements);
		
		unbindTexturedModel();
		shader.stop();
	}
	
	private List<Integer> getAllBlocks(ChunkManager chunkManager, Camera camera) {
		return IntStream.range(0, (int) Math.pow(chunkManager.getSize(), 3)).parallel()
					.sorted()
					.filter(chunkIndex -> checkVisibility(chunkManager.getChunkPositionByChunkIndex(chunkIndex), CHUNK_RADIUS, camera))
					.filter(chunkIndex -> chunkManager.getChunk(chunkIndex).getIsAcitve())
					.map(chunkIndex -> chunkIndex * (int) Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3))
					.flatMap(chunkIndex -> 
								IntStream.range(0, (int) Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3)).parallel()
									.map(block -> block + chunkIndex))
					.mapToObj(generalIndex -> generalIndex)
					.collect(Collectors.toList());
	}

	
	private void drawElements(int face) {
		GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, 6, 6,
				GL11.GL_UNSIGNED_INT, 24 * face);
	}

	private boolean checkVisibility(Vector3f position, float radius, Camera camera) {
		return camera.getFrustum().sphereInFrustumAndDsitance(position, radius, 0, EngineSettings.RENDERING_VIEW_DISTANCE, camera);
	}

	private synchronized void prepareInstance(Vector3f position) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(position, 0, 0, 0, 1);
		shader.loadTranformationMatrix(transformationMatrix);
	}

	private void prepareModel(Mesh rawModel) {
		VAO vao = rawModel.getVAO();
		vao.bind(0, 1, 2);

	}

	private void bindMaterial(Material material) {
		shader.loadFakeLightingVariable(material.isUseFakeLighting());
		shader.loadNumberOfRows(material.getDiffuseMap().getNumberOfRows());
		shader.loadOffset(0.0f, 0.0f);
		
		if (material.getDiffuseMap().isTransparent())
			GraphicUtils.cullBackFaces(false);
		
		shader.loadShineVariables(material.getShininess(), material.getReflectivity());
		material.getDiffuseMap().bind(0);
	}

	private void unbindTexturedModel() {
		GraphicUtils.cullBackFaces(true);
		VAO.unbind(0, 1, 2);
	}
	
	private FaceCullingData isNeedBlockCulling(ChunkManager chunkManager, int index) {
		FaceCullingData fcData = new FaceCullingData(index);
		Chunk chunk = chunkManager.getChunkByGeneralIndex(index);
		Vector3i blockIndexPosition = chunkManager.getBlockIndexVectorByGenerealIndex(index);
		for (int face = 0; face < 6; face++) {
			if (isFaceCovered(chunk, blockIndexPosition.x, blockIndexPosition.y, blockIndexPosition.z, face))
				fcData.setFaceRendering(face, true);
		}
		return fcData;
	}

	private boolean isAllFaceCulled(FaceCullingData fcData) {
		for (int i = 0; i < 6; i++) {
			if (!fcData.getFace(i)) return false;
		}
		return true;
	}

	private boolean isFaceCovered(Chunk chunk, int x, int y, int z, int face) {
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

		if (!chunk.isBlockExist(position)) return false;
		return (chunk.getBlock(position).getIsActive());
	}

	private boolean isFaceCovered(ChunkManager chunker, int index, int face) {
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
		
		if (!chunker.isChunkExist(position)) return false;
		return (chunker.getChunk(position).getIsAcitve());
	}

}