package tool.xml.parser;

import org.w3c.dom.Document;

public abstract class XMLParser {
	
protected Document document;
	
	protected XMLParser(Document document) {
		this.document = document;
	}

}
