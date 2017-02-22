package bloom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import postProcessing.ImageRenderer;

/**
 * Post processing filter that make glow effect using Combine shader, Image 
 * Renderer and entering textures.
 * 
 *  @author homelleon
 *  @version 1.0
 */

public class CombineFilter {
	
	private ImageRenderer renderer;  
	private CombineShader shader; 	  
	
	/**
	 * Simple constructor that initializes {@link CombineShader}, connects
	 * texture untis in it and also initialize {@link ImageRenderer}. 
	 * 
	 * @see CombineShader
	 * @see ImageRenderer
	 */
	public CombineFilter() {
		shader = new CombineShader();
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
		renderer = new ImageRenderer();
	}
	
	/**
	 * Method that renders texture buffer on the screan combining entering 
	 * texture with highlight textures to make glow effect.  
	 * 
	 * @param colourTexture       
	 * 								int value of entering texture
	 * @param highlightTexture2
	 * 								int value of highlight texture to process
	 * @param highlightTexture4
	 * 							 	int value of highlight texture to process
	 * @param highlightTexture8
	 * 								int value of highlight texture to process
	 */
	public void render(int colourTexture, int highlightTexture2, int highlightTexture4, int highlightTexture8) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colourTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture2);
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture4);
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, highlightTexture8);
		renderer.renderQuad();
		shader.stop();
	}
	
	/**
	 * Method that clean CombineShader and ImageRenderer objects.
	 */
	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}

}
