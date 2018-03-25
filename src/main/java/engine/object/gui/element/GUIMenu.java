package object.gui.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import object.gui.Hideable;
import object.gui.animation.Action;

/**
 * Represents GUI menu for comfortable access to GUI objects
 * with ability to store buttons.
 *  
 * @author homelleon
 * @see IGUIMenu
 *
 */
public class GUIMenu extends GUIObject {
	
	public GUIMenu(String name) {
		super(name);
	}

	private boolean hasButtons = false;
	private boolean wasNext = false;
	private boolean wasPrevious = false;
	private Map<String, GUIObject> objects = new HashMap<String, GUIObject>();
	private List<GUIButton> buttons = new ArrayList<GUIButton>();
	private ListIterator<GUIButton> buttonIterator;
	private GUIButton selectedButton;
	
	public void add(GUIObject object) {
		this.objects.put(object.getName(), object);
		if(object instanceof GUIButton) {
			buttons.add((GUIButton)object);
			this.hasButtons = true;
			this.updateIterator();
			this.deselectAllButtons();
		}
	}
	
	public GUIObject get(String name) {
		return this.objects.get(name);
	}	

	public List<GUIButton> getAllButtons() {
		return this.buttons;
	}
	
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
					this.selectedButton = this.buttonIterator.next();
					this.selectedButton.select();
				}
			} else {
				while(this.buttonIterator.hasPrevious()) {
					this.buttonIterator.previous();
				}
				this.selectedButton = this.buttonIterator.next();
				this.selectedButton.select();
			}
		}
	}
	
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
					this.selectedButton = this.buttonIterator.previous();
					this.selectedButton.select();
				}
			} else {
				while(this.buttonIterator.hasNext()) {
					this.buttonIterator.next();
				}
				this.selectedButton = this.buttonIterator.previous();
				this.selectedButton.select();
			}
		}
	}

	public void useButton(Action action) {
		this.selectedButton.use(action);
	}
	
	public void useButton() {
		this.selectedButton.use();
	}

	public GUIButton getSelectedButton() {
		return this.selectedButton;
	}
	
	public boolean getHasButtons() {
		return this.hasButtons;
	}

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
		objects.values().forEach(Hideable::show);
	}
	
	@Override
	public void hide() {
		super.hide();
		objects.values().forEach(Hideable::hide);
	}
	
	private void updateIterator() {
		this.buttonIterator = buttons.listIterator();		
	}
	
	private void deselectAllButtons() {
		this.buttons.stream()
			.filter(GUIButton::getIsSelected)
			.forEach(GUIButton::deselect);
	}
	
	private boolean checkIfHassButtons() {
		if(hasButtons) {
			return true;
		} else {
			throw new ArrayIndexOutOfBoundsException ("There is no buttons in " + this.getName() + " menu!");
		}
	}

}
