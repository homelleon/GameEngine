package tool.dataEditor.menu.control;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import tool.dataEditor.menu.EditorUtils;
import tool.dataEditor.menu.actions.ButtonAction;

public class ControlBox extends Box {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7813878835073644524L;
	
	private JButton addButton = new JButton();
	private JButton editButton = new JButton();
	private JButton removeButton = new JButton();

	public ControlBox(ButtonAction action) {
		super(BoxLayout.Y_AXIS);
		this.initializeControlButtons(action);
	}
	
	public JButton getAddButton() {
		return addButton;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}
	
	private void initializeControlButtons(ButtonAction action) {
		EditorUtils.setupButton("add", this.addButton, action);
		EditorUtils.setupButton("edit", this.editButton, action);
		EditorUtils.setupButton("remove", this.removeButton, action);
		JLabel label = new JLabel("controls");
		this.add(label);
		this.add(addButton);
		this.add(editButton);
		this.add(removeButton);
	}

}
