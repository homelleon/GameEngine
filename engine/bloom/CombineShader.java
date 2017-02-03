package bloom;

import scene.ES;
import shaders.ShaderProgram;

public class CombineShader extends ShaderProgram { 
	
	/*
	 *  CombineShader - шейдер для объединения других фильтров постобработки
	 *  03.02.17
	 * ------------------------------
	*/

	private static final String VERTEX_FILE = ES.BLOOM_SHADER_PATH + "simpleVertex.txt";
	private static final String FRAGMENT_FILE = ES.BLOOM_SHADER_PATH + "combineFragment.txt";
	
	private int location_colourTexture;
	private int location_highlightTexture2;
	private int location_highlightTexture4;
	private int location_highlightTexture8;
	
	protected CombineShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_colourTexture = super.getUniformLocation("colourTexture");
		location_highlightTexture2 = super.getUniformLocation("highlightTexture2");
		location_highlightTexture4 = super.getUniformLocation("highlightTexture4");
		location_highlightTexture8 = super.getUniformLocation("highlightTexture8");
	}
	
	protected void connectTextureUnits() {
		super.loadInt(location_colourTexture, 0);
		super.loadInt(location_highlightTexture2, 1);
		super.loadInt(location_highlightTexture4, 2);
		super.loadInt(location_highlightTexture8, 3);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
}
