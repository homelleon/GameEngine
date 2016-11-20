package frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panels.PanelDistanceOfView;

public class FrameEditor extends JFrame implements Frame {
	
	private static final int WIDTH = 2048;
	private static final int HEIGHT = 1536;
	private JPanel displayPanel = new JPanel();
	FlowLayout layout = new FlowLayout();
	
	public FrameEditor(String name) {
		super(name);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		getContentPane().setLayout(layout);
		displayPanel.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/2));
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
