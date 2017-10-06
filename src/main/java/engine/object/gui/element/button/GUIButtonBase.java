package object.gui.element.button;

import org.lwjgl.opengl.Display;

import core.debug.EngineDebug;
import object.bounding.BoundingQuad;
import object.gui.element.object.GUIObject;
import object.gui.group.IGUIGroup;
import object.gui.texture.GUITexture;
import tool.math.Maths;
import tool.math.vector.Vector2f;

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
	protected volatile boolean isSelected = false;
	protected IAction useAction;
	protected IAction selectedAction;
	protected IAction deselectedAction;
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
	public Thread select() {
		this.isSelected = true;
		Thread thread = null;
		if(this.selectedAction != null) {
			thread = new Thread(this.selectedAction);
			thread.start();
		}
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Button " + this.name + " is selected!");
		}
		return thread;
	}
	
	@Override
	public Thread select(IAction action) {
		Thread thread = null;
		this.isSelected = true;
		if(action != null) {			
			thread = new Thread(action);
			thread.start();
			if(EngineDebug.hasDebugPermission()) {
				EngineDebug.print("Button " + this.name + " is selected!");
			}
		} else {
			if(EngineDebug.hasDebugPermission()) {
				EngineDebug.print("There is empty action in button " + this.name + " on selection!");
			}
		}
		return thread;
	}

	@Override
	public Thread deselect() {
		this.isSelected = false;
		Thread thread = null;
		if(this.deselectedAction != null) {
			thread = new Thread(this.deselectedAction);
			thread.start();
		}
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Button " + this.name + " is deselected!");
		}		
		return thread;
	}
	
	@Override
	public Thread deselect(IAction action) {
		Thread thread = null;
		this.isSelected = false;
		if(action != null) {
			thread = new Thread(action);
			thread.start();
		} else {
			if(EngineDebug.hasDebugPermission()) {
				EngineDebug.print("There is empty action in button " + this.name + " on deselection!");
			}
		}
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Button " + this.name + " is deselected!");
		}
		return thread;
	}

	@Override
	public Thread use(IAction action) {
		try {
			this.deselect().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread thread = null;
		if(action != null) {
			thread = new Thread(action);
			thread.start();			
		} else {
			if(EngineDebug.hasDebugPermission()) {
				EngineDebug.print("There is empty action in button " + this.name + " on using!");
			}
		}
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Button " + this.name + " is used!");
		}
		return thread;
	}
	
	@Override
	public Thread use() {
		try {
			if(this.isSelected) {
				this.deselect().join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread thread = null;
		if(useAction != null) {
			thread = new Thread(this.useAction);
			thread.start();
		}
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Button " + this.name + " is used!");
		}
		return thread;
	}
	
	@Override
	public IGUIGroup getGroup() {
		return this.guiGroup;
	}
	
	@Override
	public void setBoundingArea(BoundingQuad quad, boolean centered) {
		this.quad = quad;
		if(centered) {
			Vector2f center = 
			this.guiGroup.getAll().stream()
				.flatMap(list -> list.getTextures().stream())
				.map(texture -> texture.getPosition())
				.reduce((tPosition1, tPosition2) -> {
					Vector2f summ = Vector2f.add(tPosition1, tPosition2);
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
	public void setUseAction(IAction action) {
		this.useAction = action;
	}
	
	@Override
	public void setSelectedAction(IAction action) {
		this.selectedAction = action;
	}
	
	@Override
	public void setDeselectedAction(IAction action) {
		this.deselectedAction = action;
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
					.min((a,b) -> Maths.compare(a,b))
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
					.max((a,b) -> Maths.compare(a,b))
					.get();
	}
	
	private Vector2f getTextureFirstPoint(GUITexture texture) {
		float width = texture.getTexture().getWidth()/(float)Display.getWidth();
		float height = texture.getTexture().getHeight()/(float)Display.getHeight();
		float x = texture.getPosition().x - width*texture.getScale().x/2;
		float y = texture.getPosition().y - height*texture.getScale().y/2;
		return new Vector2f(x, y); 
	}
	
	public Vector2f getTextureSecondPoint(GUITexture texture) {
		float width = texture.getTexture().getWidth()/(float)Display.getWidth();
		float height = texture.getTexture().getHeight()/(float)Display.getHeight();
		float x = texture.getPosition().x + width*texture.getScale().x/2;
		float y = texture.getPosition().y + height*texture.getScale().y/2;
		return new Vector2f(x, y); 
	}

}
