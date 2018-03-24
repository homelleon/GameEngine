package tool.dataEditor.menu.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

import core.Loop;
import tool.dataEditor.DataEditorMain;

public class ButtonAction extends AbstractAction  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7538573236065034620L;
	private JFrame mainFrame;
	
	public ButtonAction(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		switch(button.getName()) {
			case "exit":
				DataEditorMain.exit();
				break;
			case "save":
				this.println("Saving...");
				break;
			case "add":
				this.println("Adding...");
				break;
			case "texture":
				this.println("Loading texture...");
				break;
			case "normal texture":
				this.println("Loading normal texture...");
				break;
			case "specular texture":
				this.println("Loading specular texture...");
				break;
			case "edit":
				this.println("Editing...");
				break;
			case "remove":
				this.println("Removing...");
				break;
		}
		
	}
	
	private void println(String text) {
		System.out.println(text);
	}

}
