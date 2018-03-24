package object.gui.texture;

import java.util.List;

import org.w3c.dom.Document;

import core.settings.EngineSettings;
import tool.xml.loader.IXMLLoader;
import tool.xml.loader.XMLFileLoader;
import tool.xml.parser.IListParser;
import tool.xml.reader.IXMLReader;

public class GUITextureReader implements IXMLReader<GUITexture> {

	@Override
	public List<GUITexture> readFile(String fileName) {
		IXMLLoader xmlLoader = new XMLFileLoader(
				EngineSettings.INTERFACE_PATH + fileName + EngineSettings.EXTENSION_XML);
		IListParser<GUITexture> parser = new GUITextureXMLParser(xmlLoader.load());
		return parser.parse();
	}

	@Override
	public List<GUITexture> readDocument(Document document) {
		IListParser<GUITexture> parser = new GUITextureXMLParser(document);
		return parser.parse();
	}

}
