package frame;

import java.awt.Component;

import javax.swing.JPanel;

public interface IFrame {
	public void addElement(Component component);
	public JPanel getDisplayPanel();
	public int getHeight();
	public int getWidth(); 

}
