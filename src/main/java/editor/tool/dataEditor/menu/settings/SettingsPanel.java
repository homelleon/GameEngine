package tool.dataEditor.menu.settings;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tool.dataEditor.menu.EditorUtils;
import tool.dataEditor.menu.actions.ButtonAction;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5773300164265607826L;
	
	private JButton saveButton = new JButton();
	private JButton exitButton = new JButton();

	public SettingsPanel(ButtonAction action) {
		EditorUtils.setupButton("save", saveButton, action);
		EditorUtils.setupButton("exit", exitButton, action);
		Box verticalBox = Box.createVerticalBox();
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(saveButton);
		horizontalBox.add(exitButton);
		verticalBox.add(new JLabel("settings"));
		verticalBox.add(horizontalBox);
		this.add(verticalBox);		
	}

}
