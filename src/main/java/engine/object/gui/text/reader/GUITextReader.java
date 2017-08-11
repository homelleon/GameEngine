package object.gui.text.reader;

import java.util.List;

import org.w3c.dom.Document;

import core.settings.EngineSettings;
import object.gui.text.GUIText;
import object.gui.text.parser.GUITextXMLParser;
import tool.xml.loader.IXMLLoader;
import tool.xml.loader.XMLFileLoader;
import tool.xml.parser.IListParser;
import tool.xml.reader.IXMLReader;

public class GUITextReader implements IXMLReader<GUIText> {

	@Override
	public List<GUIText> readFile(String fileName) {
		IXMLLoader xmlLoader = new XMLFileLoader(
				EngineSettings.INTERFACE_PATH + fileName + EngineSettings.EXTENSION_XML);
		IListParser<GUIText> parser = new GUITextXMLParser(xmlLoader.load());
		return parser.parse();
	}

	@Override
	public List<GUIText> readDocument(Document document) {
		IListParser<GUIText> parser = new GUITextXMLParser(document);
		return parser.parse();
	}

}
