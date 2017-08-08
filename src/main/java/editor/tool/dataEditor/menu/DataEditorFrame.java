package tool.dataEditor.menu;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DataEditorFrame extends JFrame implements IDataFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6117452200850188046L;
	private JPanel displayPanel = new JPanel();
	
	public DataEditorFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		setVisible(true);
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		displayPanel.setPreferredSize(new Dimension(400, 300));
		displayPanel.setLocation(0,0);
		JButton button1 = new JButton("add entity");
		JButton button2 = new JButton("remove entity");
		displayPanel.add(button1);
		displayPanel.add(button2);
		hBox.add(displayPanel);
		vBox.add(hBox);
		getContentPane().add(vBox);
		pack();
	}
	
	public void addEntity() {
		
	}
	public void removeEntity() {
		
	}

}
