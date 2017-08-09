package tool.dataEditor.menu;

import javax.swing.JButton;

import tool.dataEditor.menu.actions.ButtonAction;

public class EditorUtils {
	
	public static void setupButton(String name, JButton button, ButtonAction action) {
		button.setAction(action);
		button.setName(name);
		button.setText(name);
	}

}
