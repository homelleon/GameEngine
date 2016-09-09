package ru.homelleon.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
	
	public static final String VERTEX_FILE = "src/ru/homelleon/shaders/vertexShader.txt";
	public static final String FRAGMENT_FILE = "src/ru/homelleon/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	
	public StaticShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		
	}


	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		
	}
	
	public void loadTranformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}


}
