package shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_OFFSET;
import static org.lwjgl.opengl.GL31.glUniformBlockBinding;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Vector4f;

import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	private int geometryShaderID;
	private int tessControlShaderID;
	private int tessEvaluationShaderID;
	private int computeShaderID;
	
	private Map<String, Integer> unfiroms;
	private Map<String, UniformBlock> blockUniforms;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public ShaderProgram() {
		this.unfiroms = new HashMap<String, Integer>();
		programID = GL20.glCreateProgram();
		if (programID == 0)
		{
			System.err.println("Shader creation failed");
			System.exit(1);
		}
	}
	
	public void start() {
		GL20.glUseProgram(programID);
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	public void clean() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDetachShader(programID, geometryShaderID);
		GL20.glDetachShader(programID, tessControlShaderID);
		GL20.glDetachShader(programID, tessEvaluationShaderID);
		GL20.glDetachShader(programID, computeShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteShader(geometryShaderID);
		GL20.glDeleteShader(tessControlShaderID);
		GL20.glDeleteShader(tessEvaluationShaderID);
		GL20.glDeleteShader(computeShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected void compileShader()	{
		bindAttributes();
		glLinkProgram(programID);

		if(glGetProgrami(programID, GL_LINK_STATUS) == 0)
		{
			System.out.println(this.getClass().getName() + " " + glGetProgramInfoLog(programID, 1024));
			System.exit(1);
		}
		
		glValidateProgram(programID);
		
		if(glGetProgrami(programID, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(this.getClass().getName() +  " " + glGetProgramInfoLog(programID, 1024));
			System.exit(1);
		}
		loadUniformLocations();
	}
	
	protected void addVertexShader(String text) {
		vertexShaderID = loadShader(text, GL_VERTEX_SHADER);
	}
	
	protected void addFragmentShader(String text) {
		fragmentShaderID = loadShader(text, GL_FRAGMENT_SHADER);
	}
	
	protected void addGeometryShader(String text) {
		geometryShaderID = loadShader(text, GL_GEOMETRY_SHADER);
	}
	
	protected void addTessellationControlShader(String text) {
		tessControlShaderID = loadShader(text, GL_TESS_CONTROL_SHADER);
	}
	
	protected void addTessellationEvaluationShader(String text) {
		tessEvaluationShaderID = loadShader(text, GL_TESS_EVALUATION_SHADER);
	}
	
	protected void addComputeShader(String text) {
		computeShaderID = loadShader(text, GL_COMPUTE_SHADER);
	}
	
	private int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			InputStream in = Class.class.getResourceAsStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {

				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Couldn't read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(this.getClass().getName() + " " + GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Couldn't compile shader!");
			System.exit(-1);
		}
		GL20.glAttachShader(programID, shaderID);
		return shaderID;

	}

	protected abstract void loadUniformLocations();
	
	protected void addUniform(String name) {		
		int uniformLocation = this.getUniformLocation(name);
		
		if (uniformLocation == 0xFFFFFFFF) {
			System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + name);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		this.unfiroms.put(name, uniformLocation);
	}
	
	protected void addBlockUniform(String blockName, int valueType, String ...uniformNames) {
		int uniformCount = uniformNames.length;
		int blockLocation = this.getUnifromBlockLocation(blockName);
		int blockSize = this.getUniformBlockParam(blockLocation, GL31.GL_UNIFORM_BLOCK_DATA_SIZE);
		
		IntBuffer blockBuffer = BufferUtils.createIntBuffer(blockSize);
		
		UniformBlock block = new UniformBlock(blockLocation, blockSize, valueType);
		IntBuffer uniformIndices = BufferUtils.createIntBuffer(uniformNames.length);
		GL31.glGetUniformIndices(programID, uniformNames, uniformIndices);
		int[] offset = new int[uniformCount]; 
		for(int i = 0; i < uniformCount; i ++) {
			offset[i] = GL31.glGetActiveUniformsi(programID, uniformIndices.get(i), GL_UNIFORM_OFFSET);
			block.addUniform(uniformNames[i], offset[i]);
		}		
		this.blockUniforms.put(blockName, block);
	}
	
	protected UniformBlock getUniformBlock(String blockName) {
		return this.blockUniforms.get(blockName);
	}	

	protected void loadInt(String name, int value) {
		int uniformLocation = this.unfiroms.get(name);
		GL20.glUniform1i(uniformLocation, value);
	}

	protected void loadFloat(String name, float value) {
		int uniformLocation = this.unfiroms.get(name);
		GL20.glUniform1f(uniformLocation, value);
	}

	protected void load3DVector(String name, Vector3f vector) {
		int uniformLocation = this.unfiroms.get(name);
		GL20.glUniform3f(uniformLocation, vector.x, vector.y, vector.z);
	}

	protected void load2DVector(String name, Vector2f vector) {
		int uniformLocation = this.unfiroms.get(name);
		GL20.glUniform2f(uniformLocation, vector.x, vector.y);
	}

	protected void load4DVector(String name, Vector4f vector) {
		int uniformLocation = this.unfiroms.get(name);
		GL20.glUniform4f(uniformLocation, vector.x, vector.y, vector.z, vector.w);
	}

	protected void loadBoolean(String name, boolean value) {
		int uniformLocation = this.unfiroms.get(name);
		float toLoad = 0;
		if (value) {
			toLoad = 1;
		}
		GL20.glUniform1f(uniformLocation, toLoad);
	}

	protected void loadMatrix(String name, Matrix4f matrix) {
		int uniformLocation = this.unfiroms.get(name);
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(uniformLocation, false, matrixBuffer);
	}
	
	protected abstract void bindAttributes();

	protected void bindAttribute(int attribue, String variableName) {
		GL20.glBindAttribLocation(programID, attribue, variableName);
	}

	protected void bindFragOutput(int attachment, String variableName) {
		GL30.glBindFragDataLocation(programID, attachment, variableName);
	}
	
	private int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	private int getUnifromBlockLocation(String uniformName) {
		return GL31.glGetUniformBlockIndex(programID, uniformName);
	}
	
	private void bindUniformBlock(String blockName, int blockBinding) {
		glUniformBlockBinding(programID, this.unfiroms.get(blockName), blockBinding);
	}
	
	private int getUniformBlockParam(int blockLocation, int parameter) {
		return GL31.glGetActiveUniformBlocki(programID, blockLocation, parameter);
	}

}
