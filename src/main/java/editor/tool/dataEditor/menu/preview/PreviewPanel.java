package tool.dataEditor.menu.preview;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PreviewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 627255492250491589L;
	
	JPanel screen = new JPanel();
	
	public PreviewPanel() {
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(new JLabel("preview"));
		verticalBox.add(screen);
		this.add(verticalBox);
		
	}

}
