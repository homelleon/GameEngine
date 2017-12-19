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
	private JButton textureButton = new JButton();
	private JButton normalTextureButton = new JButton();
	private JButton specularTextureButton = new JButton();
	private JButton removeButton = new JButton();

	public ControlBox(ButtonAction action) {
		super(BoxLayout.Y_AXIS);
		this.initializeControlButtons(action);
	}
	
	public JButton getAddButton() {
		return addButton;
	}

	public JButton getTextureButton() {
		return textureButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}
	
	private void initializeControlButtons(ButtonAction action) {
		EditorUtils.setupButton("add", this.addButton, action);
		EditorUtils.setupButton("texture", this.textureButton, action);
		EditorUtils.setupButton("normal texture", this.normalTextureButton, action);
		EditorUtils.setupButton("specular texture", this.specularTextureButton, action);
		EditorUtils.setupButton("remove", this.removeButton, action);
		JLabel label = new JLabel("controls");
		this.add(label);
		this.add(addButton);
		this.add(textureButton);
		this.add(normalTextureButton);
		this.add(specularTextureButton);
		this.add(removeButton);
	}

}
