package object.gui.pattern.menu.system.parser;

import java.util.List;

import org.w3c.dom.Document;

import object.gui.pattern.menu.IGUIMenu;
import tool.xml.parser.IListParser;
import tool.xml.parser.XMLParser;

public class GUIMenuSystemParser extends XMLParser implements IListParser<IGUIMenu> {
	
	public GUIMenuSystemParser(Document document) {
		super(document);
	}
	
	@Override
	public List<IGUIMenu> parse() {
		//TODO: write code!
		return null;
	}

}
