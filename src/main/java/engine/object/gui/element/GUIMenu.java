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

	private boolean hasButtons = false;
	private boolean wasNext = false;
	private boolean wasPrevious = false;
	private Map<String, GUIObject> objects = new HashMap<String, GUIObject>();
	private List<GUIButton> buttons = new ArrayList<GUIButton>();
	private ListIterator<GUIButton> buttonIterator;
	private GUIButton selectedButton;
	
	public GUIMenu(String name) {
		super(name);
	}
	
	public void add(GUIObject object) {
		this.objects.put(object.getName(), object);
		
		if (!(object instanceof GUIButton)) return;		
		buttons.add((GUIButton)object);
		this.hasButtons = true;
		this.updateIterator();
		this.deselectAllButtons();
	}
	
	public GUIObject get(String name) {
		return objects.get(name);
	}	

	public List<GUIButton> getAllButtons() {
		return buttons;
	}
	
	public void selectNextButton() {
		if (!getHasButtons()) return;
		
		deselectAllButtons();
		if (buttonIterator.hasNext()) {
			
			if (!wasNext)
				wasNext = true;
			
			if (wasPrevious) {
				buttonIterator.next();
				wasPrevious = false;
				selectNextButton();
			} else {
				selectedButton = buttonIterator.next();
				selectedButton.select();
			}
			
		} else {
			while (buttonIterator.hasPrevious())
				buttonIterator.previous();
			
			selectedButton = buttonIterator.next();
			selectedButton.select();
		}
	}
	
	public void selectPreviousButton() {
		if (!getHasButtons()) return;
		
		deselectAllButtons();
		if (buttonIterator.hasPrevious()) {
			
			if (!wasPrevious)
				wasPrevious = true;
			
			if (wasNext) {
				buttonIterator.previous();
				wasNext = false;
				selectPreviousButton();
			} else {
				selectedButton = buttonIterator.previous();
				selectedButton.select();
			}
		} else {
			while (buttonIterator.hasNext())
				buttonIterator.next();
			
			selectedButton = buttonIterator.previous();
			selectedButton.select();
		}
	}

	public void useButton(Action action) {
		selectedButton.use(action);
	}
	
	public void useButton() {
		selectedButton.use();
	}

	public GUIButton getSelectedButton() {
		return selectedButton;
	}
	
	public boolean getHasButtons() {
		return hasButtons;
	}

	public void clean() {
		objects.clear();
		buttonIterator = null;
		buttons.clear();
		hasButtons = false;
		wasNext = false;
		wasPrevious = false;
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
		buttonIterator = buttons.listIterator();		
	}
	
	private void deselectAllButtons() {
		buttons.stream()
			.filter(GUIButton::getIsSelected)
			.forEach(GUIButton::deselect);
	}

}
