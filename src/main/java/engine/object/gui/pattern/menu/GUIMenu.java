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
	private boolean wasNext = false;
	private boolean wasPrevious = false;
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
		this.deselectAllButtons();
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
				if(!wasNext) {
					wasNext = true;
				}
				if(wasPrevious) {
					this.buttonIterator.next();
					wasPrevious = false;
					selectNextButton();
				} else {
					this.buttonIterator.next().select();
				}
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
				if(!wasPrevious) {
					wasPrevious = true;
				}
				if(wasNext) {
					this.buttonIterator.previous();
					wasNext = false;
					selectPreviousButton();
				} else {
					this.buttonIterator.previous().select();
				}
			} else {
				while(this.buttonIterator.hasNext()) {
					this.buttonIterator.next();
				}
				this.buttonIterator.previous().select();
			}
		}
	}

	@Override
	public void useButton() {
		for(GUIButtonInterface button : this.buttons) {
			if(button.getIsSelected()) {
				button.use();
				break;
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
		this.wasNext = false;
		this.wasPrevious = false;
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
