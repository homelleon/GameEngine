package tool.dataEditor.menu;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import tool.dataEditor.menu.actions.ButtonAction;
import tool.dataEditor.menu.control.ControlPanel;
import tool.dataEditor.menu.list.ListPanel;
import tool.dataEditor.menu.preview.PreviewPanel;
import tool.dataEditor.menu.settings.SettingsPanel;

public class DataEditorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6117452200850188046L;
	
	private ListPanel listPanel;
	private PreviewPanel previewPanel;
	private SettingsPanel settingsPanel;
	private ControlPanel controlPanel;
	
	public DataEditorFrame() {
		super("Object editor");
		this.setupFrame();
		this.initializeElemets();
		this.setVisible(true);
		this.pack();
	}
	
	public void updatePreview() {
		
	}
	
	private void setupFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800, 600));
		this.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
	}

	private void initializeElemets() {
		Box horizontalBox = Box.createHorizontalBox();
		Box leftVerticalBox = Box.createVerticalBox();
		Box leftUpperHorizontalBox = Box.createHorizontalBox();
		Box leftBottomHorizontalBox = Box.createHorizontalBox();
		leftVerticalBox.add(leftUpperHorizontalBox);
		leftVerticalBox.add(leftBottomHorizontalBox);
		
		Box rightVerticalBox = Box.createVerticalBox();
		Box rightUpperHorizontalBox = Box.createHorizontalBox();
		Box rightBottomHorizontalBox = Box.createHorizontalBox();
		rightVerticalBox.add(rightUpperHorizontalBox);
		rightVerticalBox.add(rightBottomHorizontalBox);
		
		horizontalBox.add(leftVerticalBox);
		horizontalBox.add(rightVerticalBox);
		
		this.listPanel = new ListPanel();
		this.controlPanel = new ControlPanel(new ButtonAction(this));
		this.settingsPanel = new SettingsPanel(new ButtonAction(this));
		this.previewPanel = new PreviewPanel();
		leftUpperHorizontalBox.add(listPanel);
		rightUpperHorizontalBox.add(previewPanel);
		leftBottomHorizontalBox.add(settingsPanel);
		rightBottomHorizontalBox.add(controlPanel);
		this.getContentPane().add(horizontalBox);
	}
}
