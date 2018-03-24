package tool.dataEditor;

import javax.swing.JFrame;

import core.Loop;
import tool.dataEditor.menu.DataEditorFrame;

public class DataEditorMain {
	
	private static Loop loopGame;
	private static DataEditorFrame mainFrame;

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		mainFrame = new DataEditorFrame();
		loopGame = Loop.getInstance();
		loopGame.setDisplayFrame(mainFrame);
		loopGame.setEditMode(true);
		loopGame.run();
	}
	
	public static void exit() {
		loopGame.exit();
		mainFrame.dispose();
	}

}
