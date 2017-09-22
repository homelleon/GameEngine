package tool.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Main helpfull utilite to use XML parser and writer faster.
 * 
 * @author homelleon
 *
 */

public class XMLUtils {

	/* XML utils constants */
	private static final String OPEN_BRACET = "<";
	private static final String CLOSE_BRACET = ">";
	private static final String CLOSE_TAG_SIGN = "/";

	/* XML utils vars */
	private static int id = 0;
	private static boolean countId = false;

	/* ================================================== */
	/* XML parser constants */
	public static final String SEPARATOR = " ";
	/* ================================================== */
	/* object names */
	public static final String SETTINGS = "settings";
	public static final String MAPS = "maps";
	public static final String MAP = "map";
	public static final String LEVEL_MAP = "level_map";
	public static final String MODEL_MAP = "model_map";
	public static final String RAW_MAP = "raw_map";
	
	public static final String MODELS = "models";
	public static final String MODEL = "model";
	public static final String MESHES = "meshes";
	public static final String MESH = "mesh";
	
	public static final String ENTITIES = "entities";
	public static final String ENTITY = "entity";
	public static final String TERRAINS = "terrains";
	public static final String TERRAIN = "terrain";
	public static final String AUDIOS = "audios";
	public static final String AUDIO = "audio";
	public static final String PARTICLES = "particles";
	public static final String PARTICLE = "particle";
	public final static String GUI = "GUI";
	public final static String GUI_TEXTS = "GUITexts";
	public final static String GUI_TEXT = "GUIText";
	public final static String GUI_TEXTURES = "GUITextures";
	public final static String GUI_TEXTURE = "GUITexture";
	public final static String TEXTS = "texts";
	public final static String TEXT = "text";
	/* ================================================== */
	/* parameters names */
	public final static String ID = "id";
	public final static String NAME = "name";
	/* position */
	public final static String POSITION = "position";
	public final static String X = "x";
	public final static String Y = "y";
	public final static String Z = "z";
	/* color */
	public final static String COLOR = "color";
	public final static String RED = "r";
	public final static String GREEN = "g";
	public final static String BLUE = "b";
	/* ================================================== */
	/* directory names */
	public final static String PATH = "path";
	public final static String FILE = "file";
	
	public static final String TEXTURES = "textures";
	public final static String TEXTURE = "texture";
	public final static String MATERIAL = "material";
	public final static String MATERIALS = "materials";
	
	public final static String DIFFUSE_MAP = "diffuse_map";
	public final static String NORMAL_MAP = "normal_map";
	public final static String DISPLACE_MAP = "displace_map";
	public final static String AMBIENT_MAP = "ambient_map";
	public final static String SPECULAR_MAP = "specular_map";
	public final static String ALPHA_MAP = "alpha_map";

	public static final String TERRAIN_TEXTURES = "terrain_textures";
	public static final String TERRAIN_TEXTURE = "terrain_texture";
	public static final String TERRAIN_PACKS = "terrain_packs";
	public static final String TERRAIN_PACK = "terrain_pack";

	public final static String BASE_TEXTURE = "base_texture";
	public final static String RED_TEXTURE = "red_texture";
	public final static String GREEN_TEXTURE = "green_texture";
	public final static String BLUE_TEXTURE = "blue_texture";
	public final static String NORMAL_TEXTURE = "normal_texture";
	public final static String BLEND_TEXTURE = "blend_texture";
	public final static String SPECULAR_TEXTURE = "specular_texture";
	public final static String HEIGHT_TEXTURE = "height_texture";
	/* ================================================== */
	/* special value names */
	public final static String SIZE = "size";
	public final static String SCALE = "scale";
	public final static String LINE = "line";
	public final static String MAX_LENGTH = "max_length";
	public final static String FONT = "font";
	public final static String ROWS = "rows";
	public final static String TEXTURE_INDEX = "texture_index";
	public final static String SHINISESS = "shinisess";
	public final static String REFLECTIVITY = "reflectivity";
	public final static String AMPLITUDE = "amplitude";
	public final static String OCTAVE = "octave";
	public final static String ROUGHTNESS = "roughness";

	/* boolean values */
	public final static String TYPE = "type";
	public final static String SIMPLE = "simple";
	public final static String NORMAL = "normal";
	public final static String SPECULAR = "specular";
	public final static String TRANSPARENCY = "transparency";
	public final static String CENTERED = "centered";	
	public final static String PROCEDURE_GENERATED = "procedure_generated";

	/* html text tags */
	public final static String PARAGRAPH = "p";

	/**
	 * @return true if id counting<br>
	 *         false if id is not counting
	 */
	public static boolean isCountingId() {
		return XMLUtils.countId;
	}

	/**
	 * Turns id counting on.
	 * <p>
	 * XMLUtils start counting id every time any of getTag or addValue method is
	 * used.
	 */
	public static void turnCountingIdOn() {
		XMLUtils.countId = true;
	}

	/**
	 * Turns id counting off.
	 */
	public static void turnCountingIdOff() {
		XMLUtils.countId = false;
	}

	/**
	 * @return {@link Integer} value of current tag id
	 */
	public static int getId() {
		return XMLUtils.id;
	}

	/**
	 * Make tag id = 0.
	 */
	public static void clearId() {
		XMLUtils.id = 0;
	}

	private static void countId() {
		if (XMLUtils.countId) {
			id += 1;
		}
	}

	public static String pullLineFromWords(String line, String beginWord, String endWord) {
		return line.substring(beginWord.length(), line.length() - endWord.length());
	}

	public static boolean ifNodeIsElement(Node node, String name) {
		boolean value = false;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if (node.getNodeName().equals(name)) {
				value = true;
			}
		}
		return value;
	}

	public static String getBeginTag(String tagName) {
		String tag = "";
		tag = OPEN_BRACET + tagName + CLOSE_BRACET;
		countId();
		return tag;
	}

	public static String getBeginTag(String tagName, int level) {
		String tag = "";
		for (int i = 0; i < level; i++) {
			tag += " ";
		}
		tag += OPEN_BRACET + tagName + CLOSE_BRACET;
		countId();
		return tag;
	}

	public static String getEndTag(String tagName) {
		String tag = "";
		tag = OPEN_BRACET + CLOSE_TAG_SIGN + tagName + CLOSE_BRACET;
		return tag;
	}

	public static String getEndTag(String tagName, int level) {
		String tag = "";
		for (int i = 0; i < level; i++) {
			tag += " ";
		}
		tag += OPEN_BRACET + CLOSE_TAG_SIGN + tagName + CLOSE_BRACET;
		countId();
		return tag;
	}

	public static String getTagValue(Element element, String tagName) {
		String value = "";
		value = element.getElementsByTagName(tagName).item(0).getChildNodes().item(0).getNodeValue();
		return value;
	}

	public static Element getChildElementByTag(Element parentElement, String tagName) {
		Element childElement = (Element) parentElement.getElementsByTagName(tagName).item(0);
		return childElement;
	}

	public static NodeList getChildrenListByTag(Element parentElement, String tagName) {
		return getChildElementByTag(parentElement, tagName).getChildNodes();
	}

	public static String getAttributeValue(Node node, String attributeName) {
		String attribute = "";
		attribute = node.getAttributes().getNamedItem(attributeName).getNodeValue();
		return attribute;
	}

	public static String addTagValue(String tagName, String value, int level) {
		String line = "";
		line = getBeginTag(tagName, level) + value + getEndTag(tagName);
		countId();
		return line;

	}

}
