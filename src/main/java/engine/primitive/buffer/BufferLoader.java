package primitive.buffer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

import object.bounding.BoundingBox;
import object.bounding.BoundingSphere;
import primitive.model.Mesh;

/**
 * Vertex buffer and vertex array loader.
 * 
 * @author homelleon
 * @version 1.0
 */
public class BufferLoader {

	/**
	 * List of vertex arrays objects.
	 */
	private List<VAO> vaos = new ArrayList<VAO>();

	/**
	 * List of vertex buffer objects.
	 */
	private List<VBO> vbos = new ArrayList<VBO>();
	
	private List<PatchVAO> patchVaos = new ArrayList<PatchVAO>();
	
	/**
	 * Loads vertex positions, normals and indices attributes into the new
	 * vertex array object and creates new RawModel.
	 * 
	 * @param positions
	 *            - float array of vertices positions
	 * @param normals
	 *            - float array of vertices normals
	 * @param indices
	 *            - integer array of vertices order indices
	 * 
	 * @return {@link Mesh} value
	 */
	public Mesh loadToVao(float[] positions, float[] normals, int[] indices) {
		VAO vao = VAO.create();
		this.vaos.add(vao);
		vao.bind();
		vao.createIndexBuffer(indices);
		vao.createAttribute(0, 3, positions);
		vao.createAttribute(2, 3, normals);
		VAO.unbind();
		return new Mesh(vao, indices.length);
	}

	/**
	 * Loads vertex positions and texture coordinates into new vertex array
	 * object and returns index of vertex array object.
	 * 
	 * @param positions
	 *            - float array of vertices positions
	 * @param textureCoords
	 *            - float array of 2d texture coordinates
	 * 
	 * @return integer value of vao identification number
	 */
	public VAO loadToVAO(float[] positions, float[] textureCoords) {
		VAO vao = VAO.create();
		this.vaos.add(vao);
		vao.bind();
		vao.createAttribute(0, 2, positions);
		vao.createAttribute(1, 2, textureCoords);
		VAO.unbind();
		return vao;
	}
	
	public VAO loadPatchToVAO(float[] positions, float[] textureCoords, float[] normals, int patchSize) {
		VAO vao = VAO.create();
		this.vaos.add(vao);
		vao.bind();
		vao.createPatchAttribute(0, 3, positions, patchSize);
		vao.createPatchAttribute(1, 2, textureCoords, patchSize);
		vao.createPatchAttribute(2, 3, normals, patchSize);
		VAO.unbind();
		return vao;
	}

	/**
	 * Loads vertex positions, texture coordinates, normals and indices
	 * attributes into new vertex array object and creates new RawModel.
	 * 
	 * @param positions
	 *            - float array of vertices positions
	 * @param textureCoords
	 *            - float array of 2d texture coordinates
	 * @param normals
	 *            - flaot array normal Vecs coordinates
	 * @param indices
	 *            - integer array of vertices order indices
	 *            
	 * @return {@link Mesh} value
	 */
	public Mesh loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		VAO vao = VAO.create();
		this.vaos.add(vao);
		vao.bind();
		vao.createIndexBuffer(indices);
		vao.createAttribute(0, 3, positions);
		vao.createAttribute(1, 2, textureCoords);
		vao.createAttribute(2, 3, normals);
		VAO.unbind();
		BoundingSphere sphere = new BoundingSphere(positions);
		BoundingBox box = new BoundingBox(positions);
		return new Mesh(vao, indices.length, sphere, box);
	}

	/**
	 * Loads verticies buffer object to use with verticies array object.
	 * 
	 * @param positions
	 *            {@link Float} array of model position verticies
	 * @param textureCoords
	 *            {@link Float} array of model texture 2d coordinates
	 * @param normals
	 *            {@link Float} array of model points normal Vecs
	 * @param tangents
	 * @param indices
	 *            {@link Integer} array of model points order
	 * @return
	 */
	public Mesh loadToVAO(float[] positions, float[] textureCoords, float[] normals, float[] tangents,
			int[] indices) {
		VAO vao = VAO.create();
		this.vaos.add(vao);
		vao.bind();
		vao.createIndexBuffer(indices);
		vao.createAttribute(0, 3, positions);
		vao.createAttribute(1, 2, textureCoords);
		vao.createAttribute(2, 3, normals);
		vao.createAttribute(3, 3, tangents);
		VAO.unbind();
		BoundingSphere sphere = new BoundingSphere(positions);
		BoundingBox box = new BoundingBox(positions);
		return new Mesh(vao, indices.length, sphere, box);

	}


	/**
	 * Updates verticies buffer object to use with verticies array object.
	 * 
	 * @param vbo
	 *            {@link Integer} value of verticies buffer object number in
	 *            video buffer
	 * @param data
	 *            {@link Float} array value of data to update in video buffer
	 * @param buffer
	 */
	public void updateVbo(VBO vbo, float[] data, FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		vbo.bind();
		vbo.storeSubData(buffer, data);
		vbo.unbind();
	}
	
	/**
	 * Creates empty vertex buffer object.
	 * 
	 * @param floatCount
	 *            {@link Float} size of empty verticies buffer
	 * 
	 * @return {@link Integer} ordinal number of vbo in video buffer
	 */
	public VBO createEmptyVbo(int floatCount) {
		VBO vbo = VBO.create(GL15.GL_ARRAY_BUFFER);
		vbos.add(vbo);
		vbo.bind();
		vbo.storeData(floatCount * 4);
		vbo.unbind();
		return vbo;
	}

	/**
	 * Adds instaced attributes in vertex buffer object.
	 * 
	 * @param vao vertex array object
	 * @param vbo 
	 * @param attribute
	 * @param dataSize
	 * @param instancedDataLength
	 * @param offset
	 */
	public void addInstacedAttribute(VAO vao, VBO vbo, int attribute, int dataSize, int instancedDataLength,
			int offset) {
		vao.bind();
		vbo.bind();
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, instancedDataLength * 4, offset * 4);
		GL33.glVertexAttribDivisor(attribute, 1);
		vbo.unbind();
		VAO.unbind();
	}

	/**
	 * Loads vertex array object into video buffer using verticies position
	 * array and object dimension.
	 * 
	 * @param positions
	 *            {@link Float} array of model position verticies
	 * @param dimensions
	 *            {@link Integer} value of object dimensions
	 * @return
	 */
	public Mesh loadToVAO(float[] positions, int dimensions) {
		VAO vao = VAO.create();
		this.vaos.add(vao);
		vao.bind();
		vao.createAttribute(0, dimensions, positions);
		VAO.unbind();
		
		BoundingSphere sphere = new BoundingSphere(positions);
		BoundingBox box = new BoundingBox(positions);
		return new Mesh(vao, positions.length / dimensions, sphere, box);

	}
	

	/**
	 * Cleans all verticies array and buffer objects video buffer.
	 */
	public void clean() {
		if(!vaos.isEmpty()) {
			vaos.forEach(VAO::delete);
		}
		
		if(!vbos.isEmpty()) {
			vbos.forEach(VBO::delete);
		}
		if(!patchVaos.isEmpty()) {
			patchVaos.forEach(PatchVAO::delete);
		}
	}

}
