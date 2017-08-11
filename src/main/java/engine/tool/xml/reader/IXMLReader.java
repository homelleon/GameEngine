package tool.xml.reader;

import java.util.List;

import org.w3c.dom.Document;

public interface IXMLReader<E> {
	
	List<E> readFile(String fileName);
	List<E> readDocument(Document document);

}
