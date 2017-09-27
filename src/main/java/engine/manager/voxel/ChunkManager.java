package manager.voxel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import core.settings.EngineSettings;
import object.voxel.Block;
import object.voxel.Chunk;
import tool.math.Maths;
import tool.math.vector.IVectorBuilder3;
import tool.math.vector.Vector3fF;
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
	private Vector3fF position;

	public ChunkManager(int size, Vector3fF position) {
		this.size = size;
		this.position = position;
		chunks = IntStream.range(0, size)
			.flatMap(x -> IntStream.range(0, size))
			.flatMap(y -> IntStream.range(0, size))
			.mapToObj(z -> new Chunk())
			.collect(Collectors.toList());
		this.createVoxels();
	}
	
	private void createVoxels() {
		IntStream.rangeClosed(0, (int) (Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3) * (Math.pow(size, 3))))
		.forEach(blockIndex -> getBlockByBlockIndex(blockIndex).setIsActive(true));
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
	public Vector3fF getChunkPosition(Vector3i indexPosition) {
		float step = EngineSettings.VOXEL_CHUNK_SIZE * EngineSettings.VOXEL_BLOCK_SIZE;
		IVectorBuilder3<Float, Vector3fF> vecBuilder = new VectorBuilder3f();
		return vecBuilder
					.setX(position.x + indexPosition.x * step)
					.setY(position.y + indexPosition.y * step)
					.setZ(position.z + indexPosition.z * step)
					.build();
	}

	@Override
	public Vector3fF getChunkPositionByChunkIndex(int chunkIndex) {
		float step = EngineSettings.VOXEL_CHUNK_SIZE * EngineSettings.VOXEL_BLOCK_SIZE;
		int x = (int) Math.floor(chunkIndex / Maths.sqr(size));
		int y = (int) Math.floor(chunkIndex / size);
		int z = chunkIndex;
		IVectorBuilder3<Float, Vector3fF> vecBuilder = new VectorBuilder3f();
		return vecBuilder
					.setX(position.x + Maths.tailOfDivisionNoReminder(x, size) * step)
					.setY(position.y + Maths.tailOfDivisionNoReminder(y, size) * step)
					.setZ(position.z + Maths.tailOfDivisionNoReminder(z, size) * step)
					.build();
	}
	
	@Override
	public Vector3fF getChunkPositionByBlockIndex(int blockIndex) {
		float step = EngineSettings.VOXEL_CHUNK_SIZE * EngineSettings.VOXEL_BLOCK_SIZE;
		int x = (int) Math.floor(blockIndex / (Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3) *  Math.pow(size,2)));
		int y = (int) Math.floor(blockIndex / (Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3) * size));
		int z = (int) Math.floor(blockIndex / Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3));
		x = Maths.tailOfDivisionNoReminder(x, size);
		y = Maths.tailOfDivisionNoReminder(y, size);
		z = Maths.tailOfDivisionNoReminder(z, size);
		IVectorBuilder3<Float, Vector3fF> positionVecBuilder = new VectorBuilder3f();
		return positionVecBuilder
				.setX(position.x + x * step)
				.setY(position.y + y * step)
				.setZ(position.y + z * step)
				.build();
	}

	@Override
	public Vector3fF getBlockPosition(int chunkIndex, Vector3i indexPosition) {
		float step = EngineSettings.VOXEL_BLOCK_SIZE;
		Vector3fF chunkPosition = getChunkPositionByChunkIndex(chunkIndex);
		IVectorBuilder3<Float, Vector3fF> vecBuilder = new VectorBuilder3f();
		return vecBuilder
					.setX(chunkPosition.x + indexPosition.x * step)
					.setY(chunkPosition.y + indexPosition.y * step)
					.setZ(chunkPosition.z + indexPosition.z * step)
					.build();
	}
	
	@Override
	public Vector3fF getBlockPositionByBlockIndex(int blockIndex) {
		int x = (int) Math.floor(blockIndex / Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 2));
		int y = (int) Math.floor(blockIndex / EngineSettings.VOXEL_CHUNK_SIZE);
		int z = blockIndex;
		x = Maths.tailOfDivisionNoReminder(x, EngineSettings.VOXEL_CHUNK_SIZE);
		y = Maths.tailOfDivisionNoReminder(y, EngineSettings.VOXEL_CHUNK_SIZE);
		z = Maths.tailOfDivisionNoReminder(z, EngineSettings.VOXEL_CHUNK_SIZE);
		IVectorBuilder3<Integer, Vector3i> vecBuilder = new VectorBuilder3i();
		Vector3fF chunkPosition = getChunkPositionByBlockIndex(blockIndex);
		Vector3i blockVector = vecBuilder
				.setX(x)
				.setY(y)
				.setZ(z)
				.build();
		float step = EngineSettings.VOXEL_BLOCK_SIZE;
		return new VectorBuilder3f()
				.setX(chunkPosition.x + blockVector.x * step)
				.setY(chunkPosition.y + blockVector.y * step)
				.setZ(chunkPosition.z + blockVector.z * step)
				.build();
	}
	
	@Override
	public Vector3fF getBlockPosition(int chunkIndex, int blockIndex) {
		return getBlockPosition(chunkIndex, getBlockIndexPosition(blockIndex));
	}
	
	private Vector3i getBlockIndexPosition(int blockIndex) {
		int x = (int) Math.floor(blockIndex / Maths.sqr(EngineSettings.VOXEL_CHUNK_SIZE));
		int y = (int) Math.floor(blockIndex / EngineSettings.VOXEL_CHUNK_SIZE);
		int z = blockIndex;
		x = Maths.tailOfDivisionNoReminder(x, EngineSettings.VOXEL_CHUNK_SIZE);
		y = Maths.tailOfDivisionNoReminder(y, EngineSettings.VOXEL_CHUNK_SIZE);
		z = Maths.tailOfDivisionNoReminder(z, EngineSettings.VOXEL_CHUNK_SIZE);
		IVectorBuilder3<Integer, Vector3i> vecBuilder = new VectorBuilder3i();
		return vecBuilder
				.setX(x)
				.setY(y)
				.setZ(z)
				.build();
	}

	@Override
	public Vector3i getChunkIndexVector(int chunkIndex) {
		int x = (int) Math.floor(chunkIndex / Maths.sqr(size));
		int y = (int) Math.floor(chunkIndex / size);
		int z = chunkIndex;
		IVectorBuilder3<Integer, Vector3i> vecBuilder = new VectorBuilder3i();
		return vecBuilder
					.setX(Maths.tailOfDivisionNoReminder(x, size))
					.setY(Maths.tailOfDivisionNoReminder(y, size))
					.setZ(Maths.tailOfDivisionNoReminder(z, size))
					.build();
	}
	
	@Override
	public Vector3i getBlockIndexVector(int index) {
		int x = (int) Math.floor(index / Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 2));
		int y = (int) Math.floor(index / EngineSettings.VOXEL_CHUNK_SIZE);
		int z = index;
		IVectorBuilder3<Integer, Vector3i> vecBuilder = new VectorBuilder3i();
		return vecBuilder
					.setX(Maths.tailOfDivisionNoReminder(x, EngineSettings.VOXEL_CHUNK_SIZE))
					.setY(Maths.tailOfDivisionNoReminder(y, EngineSettings.VOXEL_CHUNK_SIZE))
					.setZ(Maths.tailOfDivisionNoReminder(z, EngineSettings.VOXEL_CHUNK_SIZE))
					.build();
	}

	@Override
	public boolean isChunkExist(int chunkIndex) {
		return chunkIndex >= 0 && chunkIndex < this.chunks.size() ? true : false;
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
	public Chunk getChunk(int chunkIndex) {
		return chunks.get(chunkIndex);
	}
	
	@Override
	public Chunk getChunkByBlockIndex(int blockIndex) {
		int chX = (int) Math.floor(blockIndex / (Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3) *  Math.pow(size,2)));
		int chY = (int) Math.floor(blockIndex / (Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3) * size));
		int chZ = (int) Math.floor(blockIndex / Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3));
		chX = Maths.tailOfDivisionNoReminder(chX, size);
		chY = Maths.tailOfDivisionNoReminder(chY, size);
		chZ = Maths.tailOfDivisionNoReminder(chZ, size);
		IVectorBuilder3<Integer, Vector3i> chunkVecBuilder = new VectorBuilder3i();
		Vector3i chunkVector = chunkVecBuilder
				.setX(chX)
				.setY(chY)
				.setZ(chZ)
				.build();
		return getChunk(chunkVector);
	}
	
	@Override
	public Block getBlockByBlockIndex(int blockIndex) {
		int chX = (int) Math.floor(blockIndex / (Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3) *  Math.pow(size,2)));
		int chY = (int) Math.floor(blockIndex / (Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3) * size));
		int chZ = (int) Math.floor(blockIndex / Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 3));
		int x = (int) Math.floor(blockIndex / Math.pow(EngineSettings.VOXEL_CHUNK_SIZE, 2));
		int y = (int) Math.floor(blockIndex / EngineSettings.VOXEL_CHUNK_SIZE);
		int z = blockIndex;
		x = Maths.tailOfDivisionNoReminder(x, EngineSettings.VOXEL_CHUNK_SIZE);
		y = Maths.tailOfDivisionNoReminder(y, EngineSettings.VOXEL_CHUNK_SIZE);
		z = Maths.tailOfDivisionNoReminder(z, EngineSettings.VOXEL_CHUNK_SIZE);
		chX = Maths.tailOfDivisionNoReminder(chX, size);
		chY = Maths.tailOfDivisionNoReminder(chY, size);
		chZ = Maths.tailOfDivisionNoReminder(chZ, size);
		IVectorBuilder3<Integer, Vector3i> chunkVecBuilder = new VectorBuilder3i();
		Vector3i chunkVector = chunkVecBuilder
				.setX(chX)
				.setY(chY)
				.setZ(chZ)
				.build();
		return getChunk(chunkVector).getBlock(x,y,z);
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void clearAll() {
		chunks.clear();
	}

}
