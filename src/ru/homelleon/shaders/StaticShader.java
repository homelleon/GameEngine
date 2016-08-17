package ru.homelleon.shaders;

public class StaticShader extends ShaderProgram{
	
	public static final String VERTEX_FILE = "src/ru/homelleon/shaders/vertexShader.txt";
	public static final String FRAGMENT_FILE = "src/ru/homelleon/shaders/fragmentShader.txt";
	
	
	public StaticShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		
	}


}
