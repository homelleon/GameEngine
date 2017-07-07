package object.gui.texture;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import renderer.Loader;

public class GUITextureXMLParser implements GUITextureParserInteface {
	
	private Document document;
	private Loader loader;
	
	public GUITextureXMLParser(Document document, Loader loader) {
		this.document = document;
		this.loader = loader;
	}

	@Override
	public List<GUITexture> parser() {
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		return null;
	}

}
