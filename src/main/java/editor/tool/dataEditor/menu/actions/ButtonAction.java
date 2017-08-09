package tool.dataEditor.menu.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

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
				this.exit();
				break;
			case "save":
				this.save();
				break;
			case "add":
				this.add();
				break;
			case "edit":
				this.edit();
				break;
			case "remove":
				this.remove();
				break;
		}
		
	}
	
	private void save() {
		System.out.println("Saving...");
	}
	
	private void add() {
		System.out.println("Adding...");
	}
	
	private void remove() {
		System.out.println("Removing...");
	}
	
	private void edit() {
		System.out.println("Editing...");
	}
	
	private void exit() {
		this.mainFrame.dispose();
	}

}
