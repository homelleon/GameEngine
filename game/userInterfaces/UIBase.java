package userInterfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fontMeshCreator.GuiText;
import guis.GuiTexture;

public class UIBase implements UI {
	
	private String name;
	
	protected Map<String, GuiText> texts = new HashMap<String, GuiText>();
	protected Map<String, GuiTexture> guis = new HashMap<String, GuiTexture>();
	
	public UIBase(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void addText(GuiText text) {
		this.texts.put(text.getName(), text);
	}
	
	@Override
	public GuiText getText(String name) {
		return this.texts.get(name);
	}
	
	@Override
	public Collection<GuiText> getAllTexts() {
		return this.texts.values();
	}
	
	@Override
	public void addTexture(GuiTexture texture) {
		this.guis.put(texture.getName(), texture);
	}
	
	@Override
	public GuiTexture getTexture(String name) {
		return this.guis.get(name);
	}
	
	@Override
	public Collection<GuiTexture> getAllTextures() {
		return this.guis.values();
	}
	
	@Override
	public void cleanUp() {
		texts.clear();
		guis.clear();
	}



}
