package maps;

import org.w3c.dom.Document;

public interface MapsParser {
	
	public GameMap readMap(Document document, GameMap map);
}
