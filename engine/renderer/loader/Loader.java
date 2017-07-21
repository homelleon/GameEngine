package renderer.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import core.settings.EngineSettings;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import object.bounding.BoundingBox;
import object.bounding.BoundingSphere;
import object.model.RawModel;
import object.texture.TextureData;
import tool.math.Maths;

/**
 * Class to load vertices information and textures into video buffer.
 * 
 * @author homelleon
 *
 */
public class Loader {
	
	private static Loader instance;
	
	private Loader() {};
	
	/**
	 * Gets instance of loader class object.
	 * 
	 * @return {@link Loader} class object
	 */
	public static Loader getInstance() {
		if (instance == null) {
			instance = new Loader();
	    }
		return instance;
	}
	
	/**
	 * List of vertex arrays objects indices.
	 */
	private List<Integer> vaos = new ArrayList<Integer>();
	
	/**
	 * List of vertex buffer objects indices.
	 */
	private List<Integer> vbos = new ArrayList<Integer>();
	
	/**
	 * Map of named textures indicies.
	 */
	private Map<String, Integer> textures = new HashMap<String, Integer>(); 
	
	/**
	 * Loads vertex positions, normals and indices attributes into the new
	 * vertex array object and creates new RawModel. 
	 * 
	 * @param positions 
	 * 					- float array of vertices positions
	 * @param normals 
	 * 					- float array of vertices normals
	 * @param indices 
	 * 					- integer array of vertices order indices
	 * 
	 * @return {@link RawModel} value
	 */
	public RawModel loadToVao(float[] positions, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	/**
	 * Loads vertex positions and texture coordinates into new vertex array
	 * object and returns index of vertex array object.
	 *   
	 * @param positions 
	 * 					- float array of vertices positions
	 * @param textureCoords 
	 * 					- float array of 2d texture coordinates
	 * 
	 * @return integer value of vao identification number
	 */
	public int loadToVAO(float[] positions, float[] textureCoords) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 2, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		return vaoID;
	}
	
	/**
	 * Loads vertex positions, texture coordinates, normals and indices
	 * attributes into new vertex array object and creates new RawModel. 
	 *  
	 * @param positions 
	 * 					- float array of vertices positions
	 * @param textureCoords
	 * 					- float array of 2d texture coordinates
	 * @param normals 
	 * 					- flaot array normal vectors coordinates
	 * @param indices 
	 * 					- integer array of vertices order indices
	 * 
	 * @return {@link RawModel} value
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		BoundingSphere sphere = new BoundingSphere(positions);
		BoundingBox box = new BoundingBox(positions, this);
		return new RawModel(vaoID, indices.length, sphere, box);
	}
	
	/**
	 * Updates verticies buffer object to use with verticies array object.
	 * 
	 * @param vbo {@link Integer} value of verticies buffer object number in
	 * 		  video buffer 
	 * @param data {@link Float} array value of data to update in video buffer
	 * @param buffer
	 */
	public void updateVbo(int vbo, float[] data, FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_STREAM_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Loads verticies buffer object to use with verticies array object.
	 *  
	 * @param positions {@link Float} array of model position verticies
	 * @param textureCoords {@link Float} array of model texture 2d coordinates
	 * @param normals {@link Float} array of model points normal vectors
	 * @param tangents
	 * @param indices {@link Integer} array of model points order
	 * @return
	 */
	public RawModel loadToVAO(float[] positions,float[] textureCoords,float[] normals, float[] tangents, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		storeDataInAttributeList(3, 3, tangents);
		unbindVAO();
		float radius = getDistFarVertToCenter(positions);
		BoundingSphere sphere = new BoundingSphere(positions);
		BoundingBox box = new BoundingBox(positions, this);
		return new RawModel(vaoID,indices.length, sphere, box);
		
	}
	
	/**
	 * Creates empty vertex buffer object.
	 * 
	 * @param floatCount {@link Float} size of empty verticies buffer
	 * 
	 * @return {@link Integer} ordinal number of vbo in video buffer
	 */
	public int createEmptyVbo(int floatCount) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vbo;
	}
	
	public void addInstacedAttribute(int vao, int vbo, int attribute, int dataSize, 
			int instancedDataLength, int offset) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, 
				instancedDataLength * 4, offset * 4);
		GL33.glVertexAttribDivisor(attribute, 1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);			
	}
	
	/**
	 * Loads vertex array object into video buffer using verticies position
	 * array and object dimension.
	 * 
	 * @param positions {@link Float} array of model position verticies
	 * @param dimensions {@link Integer} value of object dimensions
	 * @return
	 */
	public RawModel loadToVAO(float[] positions, int dimensions) {
		int vaoID = createVAO();
		this.storeDataInAttributeList(0,dimensions,positions);
		unbindVAO();
		float radius = getDistFarVertToCenter(positions);
		BoundingSphere sphere = new BoundingSphere(positions);
		BoundingBox box = new BoundingBox(positions, this);
		return new RawModel(vaoID, positions.length/dimensions, sphere, box);
		
	}
	
	/**
	 * Loads texture into video buffer using texture path and texture name
	 * parameters.
	 *  
	 * @param folder {@link String} value of texture location name
	 * @param fileName {@link String} value of texture file name
	 * 
	 * @return {@link Integer} value of texture ordered number in video buffer
	 */
	public int loadTexture(String folder, String fileName) {
		return loadTexture(folder, fileName, "PNG");
	}
	
	public int loadTexture(String path, String fileName, String format) {
		Texture texture = null;
		try {
			float bias;
			if (path == EngineSettings.FONT_FILE_PATH) {
				bias = 0; 
			}else{
				bias = -2.4f;
			}
			
			String extension = EngineSettings.EXTENSION_PNG;
			
			switch(format) {
				case "PNG":
					extension = EngineSettings.EXTENSION_PNG;
					break;
				case "TGA":
					extension = EngineSettings.EXTENSION_TGA;
					break;
				case "JPG":
					extension = EngineSettings.EXTENSION_JPG;
					throw new TypeNotPresentException("JPG file is not supported!", null);
				default:
					throw new TypeNotPresentException("Uknown file extention!", null); 
			}
			
			texture = TextureLoader.getTexture(format, new FileInputStream(path+fileName + extension));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias);
			if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic){
				float amount = Math.min(4f, 
						GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 
						EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			}else{
				System.out.println("Anisotropic filtering is not supported");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.put(fileName, textureID);
		return textureID;		
	}
	
	/**
	 * Gets texture name by its identification number (ordered number) in
	 * video buffer.
	 * 
	 * @param id {@link Integer} value of texture ordered number in video buffer
	 * @return {@link String} value of texture name
	 */
	public String getTextureByID(int id) {
		String name = null;
		for(String key : textures.keySet()) {
			if(textures.get(key).equals(id)) {
				name = key;
				break;
			}
		}
		return name;
	}
	
	/**
	 * Cleans all verticies array and buffer objects video buffer.
	 */
	public void cleanUp() {
		
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		
		for (int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for (int texture:textures.values()) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	/**
	 * Loads cubic texture of cube map into video buffer.
	 * 
	 * @param path {@link String} value of texture location name
	 * @param textureFiles {@link String} array of 6 flat textures name
	 *  
	 * @return {@link Integer} ordered number of texture in video buffer
	 */
	public int loadCubeMap(String path, String[] textureFiles) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);
		
		for(int i=0;i<textureFiles.length;i++){
			TextureData data = decodeTextureFile(path + textureFiles[i] + ".png");
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, 
					data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		textures.put(path, texID);
		return texID;
	}
	
	/**
	 * Loads voxel cubic texture of cube map into video buffer.
	 * 
	 * @param path {@link String} value of texture location name
	 * @param textureFiles {@link String} array of 6 flat textures name
	 * @return
	 */
	public int loadCubeVoxelMap(String path, String[] textureFiles) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);
		
		for(int i=0;i<textureFiles.length;i++){
			TextureData data = decodeTextureFile(path + textureFiles[i] + ".png");
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, 
					data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		textures.put(path, texID);
		return texID;
	}
	
	private TextureData decodeTextureFile(String fileName) {
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try {
			FileInputStream in = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ", didn't work");
			System.exit(-1);
		}
		return new TextureData(buffer, width, height);
	}
	
	/**
	 * Creates vertex array object for video buffer.
	 * <p>After created need to add verticies buffer objects and
	 * unbind verticies array object.
	 * 
	 * @return {@link Integer} ordered number of verticies array object
	 * from video buffer
	 */
	private int createVAO() {		
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	/**
	 * Stores data into attribute list to prepare rendering.
	 *  
	 * @param attributeNumber
	 * 						  	- integer 
	 * @param coordinateSize
	 * 							- integer size of coordinates
	 * @param data
	 * 							- float array of data to store into video
	 * 							  buffer
	 */
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {		
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);		
	}
	
	/**
	 * Unbinds vertex array object to stop storing vertex buffer objects
	 * in it.
	 */
	private void unbindVAO() {		
		GL30.glBindVertexArray(0);		
	}
	
	/**
	 * Binds indicies buffer object to prepare indicies for video buffer.
	 * 
	 * @param indices {@link Integer} array of ordered number of model points
	 */
	private void bindIndicesBuffer(int[] indices) {		
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Stores integer data into buffer.
	 * 
	 * @param data
	 * 				- integer array of data
	 * @return	{@link IntBuffer} object
	 */
	private IntBuffer storeDataInIntBuffer(int[] data) {		
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
		
	}
	
	/**
	 * Stores float data into buffer.
	 * 
	 * @param data
	 * 				- float array of data
	 * @return	{@link FloatBuffer} object
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Gets distance from coordinate axis center to farthest point.
	 *  
	 * @param positions
	 * @return
	 */
	private float getDistFarVertToCenter(float[] positions) {
		float distance = 0;
		Vector3f center = new Vector3f(0,0,0);
		Vector3f point;
		for(int i = 1; i < positions.length / 3; i++) {
			point = new Vector3f(positions[3 * i - 3],
					positions[3 * i - 2], positions[3 * i - 1]);
			float length = Maths.distance2Points(point, center);
			if(length > distance) {
				distance = length;
			}
		}
		return distance;
	}
	

}
