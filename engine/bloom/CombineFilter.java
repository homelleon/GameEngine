package bloom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import postProcessing.ImageRenderer;

public class CombineFilter {
	
	/*
	 *  CombineFilter - фильтр для объединения других фильтров постобработки
	 *  03.02.17
	 * ------------------------------
	*/
	
	private ImageRenderer renderer;  
	private CombineShader shader; 	  
	
	//конструктор
	public CombineFilter() {
		shader = new CombineShader();
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
		renderer = new ImageRenderer();
	}
	
	/**
	 * 
	 * @param colourTexture       
	 * 						- входная текстура
	 * @param highlightTexture2
	 * 							- текстура для 1-го применения 2х постобработки
	 * @param highlightTexture4
	 * 							- текстура для 2-го применения 2х постобработки
	 * @param highlightTexture8
	 * 							- текстура для 3-го применения 2х постобработки
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
	
	//очистка фильтра
	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}

}
