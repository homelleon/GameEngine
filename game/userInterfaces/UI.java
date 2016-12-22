package userInterfaces;

import java.util.Collection;

import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import guis.GuiTexture;

public interface UI {
	
	void addText(String name, GuiText text, FontType font, Vector2f position);
	GuiText getText(String name);
	Collection<GuiText> getAllTexts();
	
	void addTexture(String name, GuiTexture texture, Vector2f position);
	GuiTexture getTexture(String name);
	Collection<GuiTexture> getAllTextures();
	
	void cleanUp();
	
	
}
