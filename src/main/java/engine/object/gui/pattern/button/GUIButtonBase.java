package object.gui.pattern.button;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import core.debug.EngineDebug;
import object.bounding.BoundingQuad;
import object.gui.group.IGUIGroup;
import object.gui.pattern.object.GUIObject;
import object.gui.texture.GUITexture;
import tool.math.Maths;

/**
 * Base abstract class of GUI button. Can be extended by any other class.<br>
 * * All classes that extends that abstact class can be used as button
 * navigation, can except use behaviour, change position and verify mouse
 * pointer intersection with the button.
 *  
 * @author homelleon
 * @see IGUIButton
 */
public abstract class GUIButtonBase extends GUIObject implements IGUIButton {

	protected IGUIGroup guiGroup;
	protected boolean isSelected = false;
	protected IAction event;
	protected BoundingQuad quad; 
	protected Vector2f position = new Vector2f(0,0);

	protected GUIButtonBase(String name, IGUIGroup guiGroup) {
		super(name);
		this.guiGroup = guiGroup;
		Vector2f point1 = calculateFirstPoint();
		Vector2f point2 = calculateSecondPoint();
		this.quad = new BoundingQuad(point1, point2);		
	}
	
	protected GUIButtonBase(String name, IGUIGroup guiGroup, BoundingQuad quad) {
		super(name);
		this.guiGroup = guiGroup;
		this.quad = quad;
	}

	@Override
	public IGUIButton select() {
		this.isSelected = true;
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Button " + this.name + " is selected!");
		}
		return this;
	}

	@Override
	public void deselect() {
		if (this.isSelected) {
			this.isSelected = false;
		}
	}

	@Override
	public void use(IAction action) {
		action.start();
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Button " + this.name + " is used!");
		}
	}
	
	@Override
	public void use() {
		if(event != null) {this.event.start();}
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Button " + this.name + " is used!");
		}
	}
	
	@Override
	public IGUIGroup getGroup() {
		return this.guiGroup;
	}
	
	@Override
	public void setBoundingArea(BoundingQuad quad, boolean centered) {
		this.quad = quad.clone();
		if(centered) {
			Vector2f center = 
			this.guiGroup.getAll().stream()
				.flatMap(list -> list.getTextures().stream())
				.map(texture -> texture.getPosition())
				.reduce((tPosition1, tPosition2) -> {
					Vector2f summ = Vector2f.add(tPosition1, tPosition2, null);
					return new Vector2f(summ.x/2,summ.y/2);
				}).orElse(null);
			this.quad.move(center);
		}		
	}
	
	@Override
	public BoundingQuad getBoundingArea() {
		return this.quad;
	}
	
	@Override
	public void setAction(IAction action) {
		this.event = action;
	}

	@Override
	public void show() {
		super.show();
		this.guiGroup.show();
	}

	@Override
	public void hide() {
		super.hide();
		this.guiGroup.hide();
	}
	
	@Override
	public void move(Vector2f position) {
		this.guiGroup.move(position);
		this.quad.move(position);
	}
	
	@Override
	public void increaseScale(Vector2f scale) {
		this.guiGroup.getAll().stream()
			.flatMap(gui -> gui.getTextures().stream())
			.forEach(texture -> texture.increaseScale(scale));
	}

	/**
	 * Checks if mouse is pointing current button.
	 * 
	 * @return true if mouse is over the current button<br>
	 *         false if button is out of the current button
	 */
	@Override
	public boolean getIsMouseOver(Vector2f cursorPosition) {
		return Maths.pointIsInQuad(cursorPosition,
				quad.getLeftPoint(), quad.getRightPoint());
	}

	/**
	 * Checks if the current button is selected or not.
	 * 
	 * @return true if the current button is selected<br>
	 *         false if the current button is not selected
	 */
	public boolean getIsSelected() {
		return this.isSelected;
	}
	
	/**
	 * Calculates left-bottom point of bounding quad comparing all gui texture
	 * points and selecting the lowest one.
	 * <p>Works correctly only for squared textures.
	 * 
	 * @return {@link Vector2f} left-bottom point
	 */
	private Vector2f calculateFirstPoint() {
		return this.guiGroup.getAll().stream()
					.flatMap(gui -> gui.getTextures().stream())
					.map(texture -> this.getTextureFirstPoint(texture))
					.min((a,b) -> Maths.compareTo(a,b))
					.get();
	}
	
	/**
	 * Calculates right-up point of bounding quad comparing all gui texture
	 * points and selecting the highest one.
	 * <p>Works correctly only for squared textures.
	 * 
	 * @return {@link Vector2f} right-up point
	 */
	private Vector2f calculateSecondPoint() {
		return this.guiGroup.getAll().stream()
					.flatMap(gui -> gui.getTextures().stream())
					.map(texture -> this.getTextureSecondPoint(texture))
					.max((a,b) -> Maths.compareTo(a,b))
					.get();
	}
	
	private Vector2f getTextureFirstPoint(GUITexture texture) {
		float width = texture.getTexture().getTextureWidth()/(float)Display.getWidth();
		float height = texture.getTexture().getTextureHeight()/(float)Display.getHeight();
		float x = texture.getPosition().x - width*texture.getScale().x/2;
		float y = texture.getPosition().y - height*texture.getScale().y/2;
		return new Vector2f(x, y); 
	}
	
	public Vector2f getTextureSecondPoint(GUITexture texture) {
		float width = texture.getTexture().getTextureWidth()/(float)Display.getWidth();
		float height = texture.getTexture().getTextureHeight()/(float)Display.getHeight();
		float x = texture.getPosition().x + width*texture.getScale().x/2;
		float y = texture.getPosition().y + height*texture.getScale().y/2;
		return new Vector2f(x, y); 
	}

}
