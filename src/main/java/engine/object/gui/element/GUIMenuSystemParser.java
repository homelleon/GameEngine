package object.gui.element;

import java.util.List;

import org.w3c.dom.Document;

import tool.xml.parser.IListParser;
import tool.xml.parser.XMLParser;

public class GUIMenuSystemParser extends XMLParser implements IListParser<GUIMenu> {
	
	public GUIMenuSystemParser(Document document) {
		super(document);
	}
	
	@Override
	public List<GUIMenu> parse() {
		//TODO: write code!
		return null;
	}

}
