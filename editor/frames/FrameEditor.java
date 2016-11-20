package frames;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class FrameEditor extends JFrame implements Frame {
	
	static final int WIDTH = 1024;
	static final int HEIGHT = 768;
	
	public FrameEditor(String name) {
		super(name);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
		pack();
	}
	
	@Override
	public void addCanvas(Canvas canvas) {
		add(canvas);
		
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
