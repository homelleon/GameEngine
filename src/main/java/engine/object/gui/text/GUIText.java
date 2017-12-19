package object.gui.text;

import object.GameObject;
import primitive.buffer.VAO;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

/**
 * Represents a piece of text in the game.
 * 
 * @author Karl
 * @author homelleon
 *
 */
public class GUIText extends GameObject {

	private String textString;
	private float fontSize;

	private VAO textMeshVao;
	private int vertexCount;
	private Vector3f color = new Vector3f(0f, 0f, 0f);

	private Vector2f position;
	private float maxLineLength;
	private int numberOfLines;

	private String fontName;

	private boolean centered = false;

	private float width = 0.5f;
	private float edge = 0.1f;
	private float borderWidth = 0.0f;
	private float borderEdge = 0.4f;

	private Vector2f offset = new Vector2f(0f, 0f);
	private Vector3f outlineColor = new Vector3f(0f, 0f, 0f);
	
	private float transparency = 0;
	private volatile boolean isVisible = false;

	/**
	 * Creates a new text, loads the text's quads into a VAO, and adds the text
	 * to the screen.
	 * 
	 * @param text
	 *            - the text.
	 * @param fontSize
	 *            - the font size of the text, where a font size of 1 is the
	 *            default size.
	 * @param font
	 *            - the font that this text should use.
	 * @param position
	 *            - the position on the screen where the top left corner of the
	 *            text should be rendered. The top left corner of the screen is
	 *            (0, 0) and the bottom right is (1, 1).
	 * @param maxLineLength
	 *            - basically the width of the virtual page in terms of screen
	 *            width (1 is full screen width, 0.5 is half the width of the
	 *            screen, etc.) Text cannot go off the edge of the page, so if
	 *            the text is longer than this length it will go onto the next
	 *            line. When text is centered it is centered into the middle of
	 *            the line, based on this line length value.
	 * @param centered
	 *            - whether the text should be centered or not.
	 */
	public GUIText(String name, String text, float fontSize, String fontName, Vector2f position, float maxLineLength,
			boolean centered) {
		super(name);
		this.textString = text;
		this.fontSize = fontSize;
		this.fontName = fontName;
		this.position = position;
		this.maxLineLength = maxLineLength;
		this.centered = centered;
		makeFontSmooth();
	}
	
	protected void changeText(String text) {
		this.textString = text;
	}

	/**
	 * Returns graphic width of interface text.
	 * 
	 * @return {@link Float} value of current width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Returns graphic height of interface text.
	 * 
	 * @return {@link Float} value of current height
	 */
	public synchronized void setWidth(float width) {
		this.width = width;
	}

	public float getEdge() {
		return edge;
	}

	public synchronized void setEdge(float edge) {
		this.edge = edge;
	}

	public float getBorderWidth() {
		return borderWidth;
	}

	public synchronized void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
	}

	public float getBorderEdge() {
		return borderEdge;
	}

	public synchronized void setBorderEdge(float borderEdge) {
		this.borderEdge = borderEdge;
	}

	public Vector2f getOffset() {
		return offset;
	}

	public synchronized void setOffset(Vector2f offset) {
		this.offset = offset;
	}

	public Vector3f getOutlineColor() {
		return this.outlineColor;
	}

	public synchronized void setOutlineColor(Vector3f outlineColor) {
		this.outlineColor = outlineColor;
	}

	/**
	 * @return The font used by this text.
	 */
	public String getFont() {
		return fontName;
	}

	/**
	 * Set the colour of the text.
	 * 
	 * @param r
	 *            - red value, between 0 and 1.
	 * @param g
	 *            - green value, between 0 and 1.
	 * @param b
	 *            - blue value, between 0 and 1.
	 */
	public synchronized void setColor(float r, float g, float b) {
		setColor(new Vector3f(r,g,b));
	}

	/**
	 * Set the colour of the text.
	 * 
	 * @param color
	 *            {@link Vecotr3f} value
	 */
	public synchronized void setColor(Vector3f color) {
		this.color = color;
	}

	/**
	 * @return the colour of the text.
	 */
	public Vector3f getColor() {
		return this.color;
	}

	/**
	 * @return The number of lines of text. This is determined when the text is
	 *         loaded, based on the length of the text and the max line length
	 *         that is set.
	 */
	public int getNumberOfLines() {
		return numberOfLines;
	}

	/**
	 * Sets position of top-left corner of the text in sceen-space.<br>
	 * (0, 0) is the top left corner of the screen, (1, 1) is the bottom right.
	 * 
	 * @param position
	 *            {@link Vector2f} value
	 */
	public synchronized void setPosition(Vector2f position) {
		this.position = position;
	}

	/**
	 * @return {@link Vector2f} value of the position of the top-left corner of
	 *         the text in screen-space.<br>
	 * 		(0, 0) is the top left corner of the screen, (1, 1) is the bottom
	 *         right.
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @return the ID of the text's VAO, which contains all the vertex data for
	 *         the quads on which the text will be rendered.
	 */
	public VAO getMesh() {
		return textMeshVao;
	}

	/**
	 * Set the VAO and vertex count for this text.
	 * 
	 * @param vao
	 *            - the VAO containing all the vertex data for the quads on
	 *            which the text will be rendered.
	 * @param verticesCount
	 *            - the total number of vertices in all of the quads.
	 */
	public synchronized void setMeshInfo(VAO vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	/**
	 * @return The total number of vertices of all the text's quads.
	 */
	public int getVertexCount() {
		return this.vertexCount;
	}

	/**
	 * Sets visibility parameter for text.
	 * <p>Invisible text is not rendered. 
	 * 
	 * @param value boolean to set into visibility parameter
	 */
	public synchronized void setIsVisible(boolean value) {
		this.isVisible = value;
	}

	/**
	 * Gets if the text is should be visible.
	 * 
	 * @return true if text is visible<br>
	 * 		   false if text is invisible
	 */
	public boolean getIsVisible() {
		return this.isVisible;
	}
	
	/**
	 * Sets trasparency value.
	 * 
	 * @param value float of text transparency
	 */
	public synchronized void setTransparency(float value) {
		this.transparency = value;
	}
	
	/**
	 * Gets transparency value.
	 * 
	 * @return float value of text transparency
	 */
	public float getTransparency() {
		return this.transparency;
	}

	/**
	 * @return the font size of the text (a font size of 1 is normal).
	 */
	public float getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the number of lines that this text covers (method used only in
	 * loading).
	 * 
	 * @param number
	 */
	public synchronized void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	/**
	 * @return {@code true} if the text should be centered.
	 */
	public synchronized boolean isCentered() {
		return centered;
	}

	/**
	 * @return The maximum length of a line of this text.
	 */
	public float getMaxLineSize() {
		return maxLineLength;
	}

	/**
	 * @return The string of text.
	 */
	public String getTextString() {
		return textString;
	}
	
	public GUIText clone(String name) {
		GUIText guiText = new GUIText(name, this.textString, this.fontSize, this.fontName, this.position, this.maxLineLength, this.centered);
		guiText.setColor(this.color);
		return guiText;
	}
	
	public GUIText clone(String name, String text) {
		GUIText guiText = new GUIText(name, text, this.fontSize, this.fontName, this.position, this.maxLineLength, this.centered);
		guiText.setColor(this.color);
		return guiText;
	}
	
	public void delete() {
		this.textMeshVao.delete();
	}

	private void makeFontSmooth() {
		if (this.fontSize < 4) {
			this.edge = 0.2f;
			this.width = 0.5f;
		} else if (this.fontSize < 6) {
			this.edge = 0.05f;
			this.width = 0.6f;
		} else if (this.fontSize < 11) {
			this.edge = 0.04f;
			this.width = 0.6f;
		} else if (this.fontSize < 15) {
			this.edge = 0.04f;
			this.width = 0.62f;
		} else if (this.fontSize < 25) {
			this.edge = 0.03f;
			this.width = 0.62f;
		} else {
			this.edge = 0.02f;
			this.width = 0.62f;
		}
	}

}
