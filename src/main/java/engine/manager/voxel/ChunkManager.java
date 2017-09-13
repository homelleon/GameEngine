package manager.voxel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.voxel.Block;
import object.voxel.Chunk;
import tool.math.Maths;
import tool.math.vector.IVectorBuilder3;
import tool.math.vector.Vector3i;
import tool.math.vector.VectorBuilder3f;
import tool.math.vector.VectorBuilder3i;

/**
 * Structured manage system is expected to implement chunks, blocks and voxels
 * control.
 * <p>
 * No matter that chunks are structured as one dimentional array for low memory
 * storage, user can get access to it using 3-dimentional {@link Vector3i}
 * variable.
 * 
 * @author homelleon
 * @version 1.0.1
 *
 */
public class ChunkManager implements IChunkManager {

	private List<Chunk> chunks = new ArrayList<Chunk>();
	private int size;
	private Vector3f position;

	public ChunkManager(int size, Vector3f position) {
		this.size = size;
		this.position = position;
		chunks = IntStream.range(0, size + 1)
			.flatMap(x -> IntStream.range(0, size + 1))
			.flatMap(y -> IntStream.range(0, size + 1))
			.mapToObj(z -> new Chunk())
			.collect(Collectors.toList());
		this.createVoxels();
	}
	
	private void createVoxels() {
		IntStream.range(0, Maths.cube(size) * Maths.cube(Maths.cube(EngineSettings.VOXEL_CHUNK_SIZE + 1)))
		.forEach(index -> getBlock(index).setIsActive(true));
//		IntStream.range(0, Maths.cube(size)).parallel()
//		.mapToObj(ChunkIndex::new)
//		.flatMap(index -> IntStream.range(0, EngineSettings.VOXEL_CHUNK_SIZE + 1).parallel()
//				.mapToObj(x -> new ChunkIndex(index.getI())
//						.setX(x)))
//		.flatMap(index -> IntStream.range(0, EngineSettings.VOXEL_CHUNK_SIZE + 1).parallel()
//				.mapToObj(y -> new ChunkIndex(index.getI())
//						.setX(index.getX()).setY(y)))
//		.flatMap(index -> IntStream.range(0, EngineSettings.VOXEL_CHUNK_SIZE + 1).parallel()
//				.mapToObj(z -> new ChunkIndex(index.getI())
//						.setX(index.getX()).setY(index.getY()).setZ(z)))
//		.forEach(index -> getChunk(index.getI())
//				.getBlock(index.getX(), index.getY(), index.getZ())
//				.setIsActive(true));
	}

	@Override
	public void addChunk() {
		chunks.add(new Chunk());
	}

	@Override
	public void deleteChunk() {
		chunks.remove(chunks.size() - 1);
	}

	@Override
	public Vector3f getChunkPosition(Vector3i indexPosition) {
		float step = EngineSettings.VOXEL_CHUNK_SIZE * EngineSettings.VOXEL_BLOCK_SIZE;
		IVectorBuilder3<Float, Vector3f> vecBuilder = new VectorBuilder3f();
		return vecBuilder
					.setX(position.x + indexPosition.x * step)
					.setY(position.y + indexPosition.y * step)
					.setZ(position.z + indexPosition.z * step)
					.build();
	}

	@Override
	public Vector3f getChunkPosition(int index) {
		float step = EngineSettings.VOXEL_CHUNK_SIZE * EngineSettings.VOXEL_BLOCK_SIZE;
		int x = (int) Math.floor(index / Maths.sqr(size));
		int y = (int) Math.floor(index / size);
		int z = index;
		IVectorBuilder3<Float, Vector3f> vecBuilder = new VectorBuilder3f();
		return vecBuilder
					.setX(position.x + Maths.tailOfDivisionNoReminder(x, size) * step)
					.setY(position.y + Maths.tailOfDivisionNoReminder(y, size) * step)
					.setZ(position.z + Maths.tailOfDivisionNoReminder(z, size) * step)
					.build();
	}

	@Override
	public Vector3f getBlockPosition(int index, Vector3i indexPosition) {
		float step = EngineSettings.VOXEL_BLOCK_SIZE;
		Vector3f chunkPosition = getChunkPosition(index);
		IVectorBuilder3<Float, Vector3f> vecBuilder = new VectorBuilder3f();
		return vecBuilder
					.setX(chunkPosition.x + indexPosition.x * step)
					.setY(chunkPosition.y + indexPosition.y * step)
					.setZ(chunkPosition.z + indexPosition.z * step)
					.build();
	}	

	@Override
	public Vector3f getBlockPosition(int chunkIndex, int blockIndex) {
		return getBlockPosition(chunkIndex, getBlockIndexPosition(blockIndex));
	}
	
	private Vector3i getBlockIndexPosition(int index) {
		int x = (int) Math.floor(index / Maths.sqr(EngineSettings.VOXEL_CHUNK_SIZE));
		int y = (int) Math.floor(index / EngineSettings.VOXEL_CHUNK_SIZE);
		int z = index;
		IVectorBuilder3<Integer, Vector3i> vecBuilder = new VectorBuilder3i();
		return vecBuilder
				.setX(x)
				.setY(y)
				.setZ(z)
				.build();
	}

	@Override
	public Vector3i getChunkIndexVector(int index) {
		int x = (int) Math.floor(index / Maths.sqr(size));
		int y = (int) Math.floor(index / size);
		int z = index;
		IVectorBuilder3<Integer, Vector3i> vecBuilder = new VectorBuilder3i();
		return vecBuilder
					.setX(Maths.tailOfDivisionNoReminder(x, size))
					.setY(Maths.tailOfDivisionNoReminder(y, size))
					.setZ(Maths.tailOfDivisionNoReminder(z, size))
					.build();
	}

	@Override
	public boolean isChunkExist(int index) {
		return index >= 0 && index < this.chunks.size() ? true : false;
	}

	@Override
	public boolean isChunkExist(Vector3i indexPosition) {
		int index = indexPosition.x * size * size + indexPosition.y * size + indexPosition.z;
		return index >= 0 && index < this.chunks.size() ? true : false;
	}

	@Override
	public Chunk getChunk(Vector3i indexPosition) {
		return chunks.get(indexPosition.x * size * size + indexPosition.y * size + indexPosition.z);
	}

	@Override
	public Chunk getChunk(int index) {
		return chunks.get(index);
	}
	
	public Block getBlock(int index) {
		int chunkIndex = (int) Math.floor(index / Maths.sqr(EngineSettings.VOXEL_CHUNK_SIZE) * Maths.sqr(size));
		int x = (int) Math.floor(index / Maths.sqr(EngineSettings.VOXEL_CHUNK_SIZE) * Maths.sqr(size));
		int y = (int) Math.floor(index / EngineSettings.VOXEL_CHUNK_SIZE * Maths.sqr(size));
		int z = (int) Math.floor(index / Maths.sqr(size));
		chunkIndex = Maths.tailOfDivisionNoReminder(chunkIndex, EngineSettings.VOXEL_CHUNK_SIZE * Maths.sqr(size));
		x = Maths.tailOfDivisionNoReminder(x, EngineSettings.VOXEL_CHUNK_SIZE * Maths.sqr(size));
		y = Maths.tailOfDivisionNoReminder(y, EngineSettings.VOXEL_CHUNK_SIZE * Maths.sqr(size));
		z = Maths.tailOfDivisionNoReminder(z, EngineSettings.VOXEL_CHUNK_SIZE * Maths.sqr(size));
		return getChunk(chunkIndex).getBlock(x,y,z);
	}

	@Override
	public int getSize() {
		return chunks.size();
	}

	@Override
	public void clearAll() {
		chunks.clear();
	}

}
