package userInterfaces;

import java.util.Collection;

import fontMeshCreator.GuiText;
import guis.GuiTexture;

public interface UI {
	
	String getName();
	
	void addText(GuiText text);
	GuiText getText(String name); 
	Collection<GuiText> getAllTexts();
	
	void addTexture(GuiTexture texture);
	GuiTexture getTexture(String name);
	Collection<GuiTexture> getAllTextures();
	
	void cleanUp();
	
	
}
