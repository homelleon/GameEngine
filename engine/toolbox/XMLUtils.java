package toolbox;

import org.w3c.dom.Node;

public class XMLUtils {
	
	public static String pullLineFromWords(String line, String beginWord, String endWord) {				
		return line.substring(beginWord.length(), line.length() - endWord.length());
	}
	
	public static boolean ifNodeIsElement(Node node, String name) {
		boolean value = false;
		if(node.getNodeType() == Node.ELEMENT_NODE) {
			if(node.getNodeName() == name) {
				value = true;
			}
		}
		return value;
	}

}
