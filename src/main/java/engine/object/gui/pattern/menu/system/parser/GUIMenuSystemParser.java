package object.gui.pattern.menu.system.parser;

import java.util.List;

import org.w3c.dom.Document;

import object.gui.pattern.menu.GUIMenuInterface;
import tool.xml.parser.ListParserInterface;
import tool.xml.parser.XMLParser;

public class GUIMenuSystemParser extends XMLParser implements ListParserInterface<GUIMenuInterface> {
	
	public GUIMenuSystemParser(Document document) {
		super(document);
	}
	
	@Override
	public List<GUIMenuInterface> parse() {
		return null;
	}

}
