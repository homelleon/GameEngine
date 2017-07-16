package frame;

import java.awt.Component;

import javax.swing.JPanel;

public interface Frame {
	public void addElement(Component component);
	public JPanel getDisplayPanel();
	public int getHeight();
	public int getWidth(); 

}
