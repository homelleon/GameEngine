package tool.dataEditor.menu.control;

import javax.swing.JPanel;

import tool.dataEditor.menu.actions.ButtonAction;

public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6746444426736881832L;
	private ControlBox controls;
	
	public ControlPanel(ButtonAction action) {
		this.setupPanel();
		this.addElements(action);
	}
	
	private void setupPanel() {
		this.setLocation(0,0);
	}
	
	private void addElements(ButtonAction action) {
		this.controls = new ControlBox(action);
		this.add(controls);
	}

}
