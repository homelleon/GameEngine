package tool.dataEditor.menu.list;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class ListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2713337908251552011L;
	
	private JTable entityTable;
	private JTable terrainTable;
	private JTable particleTable;
	private JTable lightTable;
	private JTable voxelTable;
	private JTable waterTable;
	private JTable skyTable;
	private JTable cameraTable;
	
	private String[] columnNames = {"Name","Type","Model"};	
	String[][] entityData = {{"Talbe","Simple","tableStall"},
					 {"Cube","Normal","Cube1"}};
	String[][] terrainData = {{"Terrain1","Mapped","terrain"},
			 {"Terrain2","Procedured","terrain"}};
	
	public ListPanel() {
		super();
		this.initializeTables();
		Box verticalBox = Box.createVerticalBox();
		JTabbedPane scrollPane = new JTabbedPane();
		scrollPane.addTab("Entity", entityTable);
		scrollPane.addTab("Terrain", terrainTable);
		scrollPane.addTab("Particle", particleTable);
		scrollPane.addTab("Light", lightTable);
		scrollPane.addTab("Voxel", voxelTable);
		scrollPane.addTab("Water", waterTable);
		scrollPane.addTab("Sky", skyTable);
		scrollPane.addTab("Camera", cameraTable);
		verticalBox.add(new JLabel("list"));
		verticalBox.add(scrollPane);
		this.add(verticalBox);
	}
	
	private void initializeTables() {
		this.entityTable = new JTable(entityData, columnNames);
		this.terrainTable = new JTable(terrainData, columnNames);
		this.particleTable = new JTable(terrainData, columnNames);
		this.lightTable = new JTable(terrainData, columnNames);
		this.voxelTable = new JTable(terrainData, columnNames);
		this.waterTable = new JTable(terrainData, columnNames);
		this.skyTable = new JTable(terrainData, columnNames);
		this.cameraTable = new JTable(terrainData, columnNames);
		this.entityTable.setShowVerticalLines(false);
	}

}

