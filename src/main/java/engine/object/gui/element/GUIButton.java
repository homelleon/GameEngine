package object.gui.element;

import org.lwjgl.opengl.Display;

import core.EngineDebug;
import object.bounding.BoundingQuad;
import object.gui.GUIGroup;
import object.gui.animation.Action;
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
public class GUIButton extends GUIObject {

	private GUIGroup guiGroup;
	private volatile boolean isSelected = false;
	private Action useAction;
	private Action selectedAction;
	private Action deselectedAction;
	private BoundingQuad quad;

	public GUIButton(String name, GUIGroup guiGroup) {
		super(name);
		this.guiGroup = guiGroup;
		Vector2f point1 = calculateFirstPoint();
		Vector2f point2 = calculateSecondPoint();
		this.quad = new BoundingQuad(point1, point2);		
	}
	
	public GUIButton(String name, GUIGroup guiGroup, BoundingQuad quad) {
		super(name);
		this.guiGroup = guiGroup;
		this.quad = quad;
	}

	public Thread select() {
		isSelected = true;
		Thread thread = null;
		if (selectedAction != null) {
			thread = new Thread(selectedAction);
			thread.start();
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Button " + name + " is selected!");
		}
		return thread;
	}
	
	public Thread select(Action action) {
		Thread thread = null;
		isSelected = true;
		if (action != null) {			
			thread = new Thread(action);
			thread.start();
			if(EngineDebug.hasDebugPermission()) {
				EngineDebug.println("Button " + name + " is selected!");
			}
		} else {
			if (EngineDebug.hasDebugPermission()) {
				EngineDebug.println("There is empty action in button " + name + " on selection!");
			}
		}
		return thread;
	}

	public Thread deselect() {
		isSelected = false;
		Thread thread = null;
		if (deselectedAction != null) {
			thread = new Thread(deselectedAction);
			thread.start();
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Button " + name + " is deselected!");
		}		
		return thread;
	}
	
	public Thread deselect(Action action) {
		Thread thread = null;
		isSelected = false;
		if (action != null) {
			thread = new Thread(action);
			thread.start();
		} else {
			if (EngineDebug.hasDebugPermission()) {
				EngineDebug.println("There is empty action in button " + name + " on deselection!");
			}
		}
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Button " + name + " is deselected!");
		}
		return thread;
	}

	public Thread use(Action action) {
		try {
			this.deselect().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread thread = null;
		if (action != null) {
			thread = new Thread(action);
			thread.start();			
		} else {
			if(EngineDebug.hasDebugPermission()) {
				EngineDebug.println("There is empty action in button " + name + " on using!");
			}
		}
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Button " + name + " is used!");
		}
		return thread;
	}
	
	public Thread use() {
		try {
			if (isSelected) {
				deselect().join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread thread = null;
		if (useAction != null) {
			thread = new Thread(useAction);
			thread.start();
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Button " + name + " is used!");
		}
		return thread;
	}
	
	public GUIGroup getGroup() {
		return guiGroup;
	}
	
	public void setBoundingArea(BoundingQuad quad, boolean centered) {
		this.quad = quad;
		if(centered) {
			Vector2f center = 
			guiGroup.getAll().stream()
				.flatMap(list -> list.getTextures().stream())
				.map(texture -> texture.getPosition())
				.reduce((tPosition1, tPosition2) -> {
					Vector2f summ = Vector2f.add(tPosition1, tPosition2);
					return new Vector2f(summ.x/2,summ.y/2);
				}).orElse(null);
			this.quad.move(center);
		}		
	}
	
	public BoundingQuad getBoundingArea() {
		return quad;
	}
	
	public void setUseAction(Action action) {
		this.useAction = action;
	}
	
	public void setSelectedAction(Action action) {
		this.selectedAction = action;
	}
	
	public void setDeselectedAction(Action action) {
		this.deselectedAction = action;
	}

	@Override
	public void show() {
		super.show();
		guiGroup.show();
	}

	@Override
	public void hide() {
		super.hide();
		guiGroup.hide();
	}
	
	public void move(Vector2f position) {
		guiGroup.move(position);
		quad.move(position);
	}
	
	public void increaseScale(Vector2f scale) {
		guiGroup.getAll().stream()
			.flatMap(gui -> gui.getTextures().stream())
			.forEach(texture -> texture.increaseScale(scale));
	}

	/**
	 * Checks if mouse is pointing current button.
	 * 
	 * @return true if mouse is over the current button<br>
	 *         false if button is out of the current button
	 */
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
		return isSelected;
	}
	
	/**
	 * Calculates left-bottom point of bounding quad comparing all gui texture
	 * points and selecting the lowest one.
	 * <p>Works correctly only for squared textures.
	 * 
	 * @return {@link Vector2f} left-bottom point
	 */
	private Vector2f calculateFirstPoint() {
		return guiGroup.getAll().stream()
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
		return guiGroup.getAll().stream()
					.flatMap(gui -> gui.getTextures().stream())
					.map(texture -> this.getTextureSecondPoint(texture))
					.max((a,b) -> Maths.compare(a,b))
					.get();
	}
	
	private Vector2f getTextureFirstPoint(GUITexture texture) {
		float width = texture.getTexture().getWidth() / (float)Display.getWidth();
		float height = texture.getTexture().getHeight() / (float)Display.getHeight();
		float x = texture.getPosition().x - width * texture.getScale().x / 2;
		float y = texture.getPosition().y - height * texture.getScale().y / 2;
		return new Vector2f(x, y); 
	}
	
	public Vector2f getTextureSecondPoint(GUITexture texture) {
		float width = texture.getTexture().getWidth() / (float)Display.getWidth();
		float height = texture.getTexture().getHeight() / (float)Display.getHeight();
		float x = texture.getPosition().x + width * texture.getScale().x / 2;
		float y = texture.getPosition().y + height * texture.getScale().y / 2;
		return new Vector2f(x, y); 
	}

}
