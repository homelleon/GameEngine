package panel;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PanelDistanceOfView extends PanelBasic implements Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3844860083636184535L;

	public PanelDistanceOfView() {
		super();
		this.setPreferredSize(new Dimension(400,200));
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		JButton button1 = new JButton("-");
		JButton button2 = new JButton("+");
		JLabel label1 = new JLabel("Расстояние");
		JLabel label2 = new JLabel("0");
		vBox.add(label1);
		vBox.add(Box.createVerticalStrut(10));
		hBox.add(button1);
		hBox.add(label2);
		hBox.add(button2);
		vBox.add(hBox);
		add(vBox); 

	}

}
