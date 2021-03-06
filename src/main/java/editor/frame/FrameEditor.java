package frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panel.PanelDistanceOfView;

public class FrameEditor extends JFrame implements IFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1928954494308202730L;
	private static final int WIDTH = 2048;
	private static final int HEIGHT = 1536;
	private JPanel displayPanel = new JPanel();
	FlowLayout layout = new FlowLayout();
	
	public FrameEditor(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		displayPanel.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/2));
		displayPanel.setLocation(0,0);
		hBox.add(displayPanel);
		hBox.add(new PanelDistanceOfView());
		vBox.add(hBox);
		vBox.add(new PanelDistanceOfView());
		getContentPane().add(vBox);
		pack();
	}
	
	@Override
	public void addElement(Component component) {
		getContentPane().add(component);		
	}
	
	@Override
	public JPanel getDisplayPanel() {
		return displayPanel;
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}

	
}
