package renderer.processor;

import object.gui.text.GUIText;

public interface TextProcessorInterface {
	
	public void render();
	
	public void loadText(GUIText text);
	
	public void removeText(GUIText text);
	
	public void cleanUp();
}
