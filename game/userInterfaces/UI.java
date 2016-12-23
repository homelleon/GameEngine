package userInterfaces;

import java.util.Collection;

import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import guis.GuiTexture;

public interface UI {
	
	void addText(GuiText text);
	GuiText getText(String name); 
	Collection<GuiText> getAllTexts();
	
	void addTexture(GuiTexture texture);
	GuiTexture getTexture(String name);
	Collection<GuiTexture> getAllTextures();
	
	void cleanUp();
	
	
}
