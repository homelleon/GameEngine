package object.gui.pattern.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import object.gui.pattern.button.GUIButtonInterface;
import object.gui.pattern.object.GUIObject;

/**
 * Represents GUI menu for comfortable access to GUI objects
 * with ability to store buttons.
 *  
 * @author homelleon
 * @see GUIMenuInterface
 *
 */
public class GUIMenu extends GUIObject implements GUIMenuInterface {
	
	private boolean hasButtons = false;
	private Map<String, GUIObject> objects = new HashMap<String, GUIObject>();
	private List<GUIButtonInterface> buttons = new ArrayList<GUIButtonInterface>();
	private ListIterator<GUIButtonInterface> buttonIterator;
	
	@Override
	public void add(GUIObject object) {
		this.objects.put(object.getName(), object);
	}

	@Override
	public void addButton(GUIButtonInterface button) {
		buttons.add(button);
		this.hasButtons = true;
		this.updateIterator();
		this.add((GUIObject) button);
	}
	
	@Override
	public GUIObject get(String name) {
		return this.objects.get(name);
	}
	
	@Override
	public void selectNextButton() {
		if(checkIfHassButtons()) {
			this.deselectAllButtons();
			if(this.buttonIterator.hasNext()) {
				this.buttonIterator.next().select();
			} else {
				while(this.buttonIterator.hasPrevious()) {
					this.buttonIterator.previous();
				}
				this.buttonIterator.next().select();
			}
		}
	}
	
	@Override
	public void selectPreviousButton() {
		if(checkIfHassButtons()) {
			this.deselectAllButtons();
			if(this.buttonIterator.hasPrevious()) {
				this.buttonIterator.previous().select();
			} else {
				while(this.buttonIterator.hasNext()) {
					this.buttonIterator.next();
				}
				this.buttonIterator.previous().select();
			}
		}
	}
	
	@Override
	public boolean getHasButtons() {
		return this.hasButtons;
	}

	@Override
	public void clean() {
		this.objects.clear();
		this.buttonIterator = null;
		this.buttons.clear();
		this.hasButtons = false;
	}	
	
	@Override
	public void show() {
		super.show();
		objects.values().forEach(object -> object.show());
	}
	
	@Override
	public void hide() {
		super.hide();
		objects.values().forEach(object -> object.hide());
	}
	
	private void updateIterator() {
		this.buttonIterator = buttons.listIterator();		
	}
	
	private void deselectAllButtons() {
		this.buttons.forEach(button -> button.deselect());
	}
	
	private boolean checkIfHassButtons() {
		boolean passed = false;
		if(hasButtons) {
			passed = true;
		} else {
			throw new ArrayIndexOutOfBoundsException ("There is no buttons in " + this.getName() + " menu!");
		}
		return passed;
	}


}
