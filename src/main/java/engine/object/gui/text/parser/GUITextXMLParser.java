package object.gui.text.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.gui.text.GUIText;
import object.gui.text.GUITextBuilder;
import object.gui.text.IGUITextBuilder;
import tool.math.vector.Vec2f;
import tool.math.vector.Vec3f;
import tool.xml.XMLUtils;
import tool.xml.loader.IXMLLoader;
import tool.xml.loader.XMLFileLoader;
import tool.xml.parser.IListParser;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

/**
 * Parser to read xml-file and parse text used for graphic user interface.
 * 
 * @author homelleon
 * @see GUITextParserInterface
 */
public class GUITextXMLParser extends XMLParser implements IListParser<GUIText> {

	/**
	 * Constracts parser with document and textMaster.
	 * 
	 * @param document
	 *            {@link Document} value
	 * @param master
	 *            {@link TextProcessor}
	 */
	public GUITextXMLParser(Document document) {
		super(document);
	}

	@Override
	public List<GUIText> parse() {
		NodeList nodeList = this.document.getDocumentElement().getChildNodes();
		List<GUIText> textList = new ArrayList<GUIText>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (XMLUtils.ifNodeIsElement(node, XMLUtils.GUI_TEXTS)) {
				textList = createText(node);
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("---");
		}

		return textList;
	}

	/**
	 * @param node
	 * @param master
	 * @return
	 */
	private List<GUIText> createText(Node node) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading GUI texts...");
		}

		List<GUIText> textList = new ArrayList<GUIText>();
		Node guiTexts = node;
		NodeList guiTextList = guiTexts.getChildNodes();
		int count = 0;
		for (int j = 0; j < guiTextList.getLength(); j++) {
			Node guiTextNode = guiTextList.item(j);
			if (XMLUtils.ifNodeIsElement(guiTextNode, XMLUtils.GUI_TEXT)) {
				Element guiTextEl = (Element) guiTextNode;
				String id = XMLUtils.getAttributeValue(guiTextEl, XMLUtils.ID);
				String name = XMLUtils.getTagValue(guiTextEl, XMLUtils.NAME);
				String path = XMLUtils.getTagValue(guiTextEl, XMLUtils.PATH);
				float size = Float.valueOf(XMLUtils.getTagValue(guiTextEl, XMLUtils.SIZE));
				String fontName = XMLUtils.getTagValue(guiTextEl, XMLUtils.FONT);
				Element positionEl = XMLUtils.getChildElementByTag(guiTextEl, XMLUtils.POSITION);
				float x = Float.valueOf(XMLUtils.getTagValue(positionEl, XMLUtils.X));
				float y = Float.valueOf(XMLUtils.getTagValue(positionEl, XMLUtils.Y));
				Vec2f position = new Vec2f(x, y);
				float maxLength = Float.valueOf(XMLUtils.getTagValue(guiTextEl, XMLUtils.MAX_LENGTH));
				boolean isCentered = Boolean.valueOf(XMLUtils.getTagValue(guiTextEl, XMLUtils.CENTERED));
				float r = 0;
				float g = 0;
				float b = 0;
				NodeList colorList = XMLUtils.getChildrenListByTag(guiTextEl, XMLUtils.COLOR);
				for (int k = 0; k < colorList.getLength(); k++) {
					Node colorNode = colorList.item(k);
					if (XMLUtils.ifNodeIsElement(colorNode, XMLUtils.RED)) {
						r = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
					} else if (XMLUtils.ifNodeIsElement(colorNode, XMLUtils.GREEN)) {
						g = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
					} else if (XMLUtils.ifNodeIsElement(colorNode, XMLUtils.BLUE)) {
						b = Float.valueOf(colorNode.getChildNodes().item(0).getNodeValue());
					}
				}
				Vec3f color = new Vec3f(r, g, b);
				IXMLLoader xmlLoader = new XMLFileLoader(
						EngineSettings.TEXT_PATH + path + EngineSettings.EXTENSION_XML);
				IObjectParser<String> textParser = new TextXMLParser(xmlLoader.load());
				String text = textParser.parse();
				count++;
				if (EngineDebug.hasDebugPermission()) {
					if (count != Integer.valueOf(id)) {
						System.err.println("error id order!");
					}
				}
				IGUITextBuilder builder = new GUITextBuilder();
				builder.setContent(text).setFontSize(size).setFontName(fontName).setPosition(position)
						.setLineMaxSize(maxLength).setCentered(isCentered);
				GUIText guiText = builder.build(name);
				guiText.setColor(color);
				textList.add(guiText);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + guiText.getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}

		return textList;
	}

}
