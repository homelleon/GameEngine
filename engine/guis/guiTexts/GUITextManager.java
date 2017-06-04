package guis.guiTexts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.settings.ES;
import font.FontType;
import font.GUIText;
import font.TextMaster;
import renderers.Loader;
import toolbox.XMLUtils;
import toolbox.xmlLoader.XMLFileLoader;
import toolbox.xmlLoader.XMLLoaderInterface;

public class GUITextManager implements GUITextManagerInterface {
	
	private TextMaster master; 
	private Map<String, GUIText> texts = new HashMap<String, GUIText>();
	//TODO: change fonts repositoring from TextMaster to GUITextManager
	private Map<String, FontType> fonts = new HashMap<String, FontType>();
	
	public GUITextManager(Loader loader) {
		this.master = new TextMaster(loader);
	}
	
	@Override
	public void addAll(Collection<GUIText> textList) {
		if((textList != null) && (!textList.isEmpty())) {
			for(GUIText text : textList) {
				this.texts.put(text.getName(), text);			
				this.master.loadText(text);
			}
		}	
	}

	@Override
	public void add(GUIText text) {
		if(text != null) {
			this.texts.put(text.getName(), text); 
			this.master.loadText(text);
		}
	}

	@Override
	public GUIText getByName(String name) {
		GUIText text = null;
		if(this.texts.containsKey(name)) {
			text = this.texts.get(name);
		}
		return text;
	}

	@Override
	public Collection<GUIText> getAll() {
		return this.texts.values();
	}
	
	

	@Override
	public void clearAll() {
		this.master.cleanUp();
		this.texts.clear();
	}

	@Override
	public TextMaster getMaster() {
		return master;
	}

	@Override
	public void readFile(String fileName) {
		XMLLoaderInterface xmlLoader = new XMLFileLoader(ES.INTERFACE_PATH + fileName + ES.EXTENSION_XML);
		GUITextParserInterface guiTextParser = new GUITextXMLParser(xmlLoader.load(), master);
		this.addAll(guiTextParser.parse());
		
	}

}
