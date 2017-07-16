package object.gui.text.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.settings.EngineSettings;
import object.gui.font.TextMeshData;
import object.gui.font.manager.FontManager;
import object.gui.font.manager.FontManagerInterface;
import object.gui.text.GUIText;
import object.gui.text.parser.GUITextParserInterface;
import object.gui.text.parser.GUITextXMLParser;
import renderer.loader.Loader;
import renderer.object.gui.GUITextRenderer;
import tool.xml.loader.XMLFileLoader;
import tool.xml.loader.XMLLoaderInterface;

/**
 * Manager for text that rendered in the screen.
 * 
 * @author homelleon
 * @see GUITextManagerInterface
 */
public class GUITextManager implements GUITextManagerInterface {
	
	private FontManagerInterface fontManager;
	private GUITextRenderer textRenderer;
	private Loader loader;
	private Map<String, GUIText> texts = new HashMap<String, GUIText>();
	
	/**
	 * Constracts GUITextManager manager.
	 *  
	 * @param loader {@link Loader}
	 */
	public GUITextManager(Loader loader) {
		this.loader = loader;
		this.fontManager = new FontManager(loader);
		this.textRenderer = new GUITextRenderer(this, fontManager);
	}
	
	@Override
	public void addAll(Collection<GUIText> textList) {
		if((textList != null) && (!textList.isEmpty())) {
			for(GUIText text : textList) {
				this.add(text);
			}
		}	
	}

	@Override
	public void add(GUIText text) {
		if(text != null) {
			this.texts.put(text.getName(), text);
			String font = text.getFont();
			this.fontManager.create(font);
			TextMeshData data = fontManager.get(font).loadText(text);
           	int vao = this.loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
    		text.setMeshInfo(vao, data.getVertexCount());
		}
	}

	@Override
	public GUIText get(String name) {
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
	public void remove(String name) {
		this.texts.remove(name);		
	}	

	@Override
	public void render() {
		this.textRenderer.render();		
	}

	@Override
	public void clearAll() {
		this.fontManager.cleanUp();
		this.texts.clear();
	}

	@Override
	public FontManagerInterface getFonts() {
		return fontManager;
	}

	@Override
	public void readFile(String fileName) {
		XMLLoaderInterface xmlLoader = new XMLFileLoader(EngineSettings.INTERFACE_PATH + fileName + EngineSettings.EXTENSION_XML);
		GUITextParserInterface guiTextParser = new GUITextXMLParser(xmlLoader.load());
		this.addAll(guiTextParser.parse());		
	}


}
