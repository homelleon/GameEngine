package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import game.game.Game;
import object.gui.font.GUIText;
import object.gui.group.GUIGroup;
import object.gui.gui.GUI;
import object.gui.gui.GUIInterface;
import object.gui.texture.GUITexture;
import object.input.KeyboardGame;

public class MyGame extends Game {
	
	private String guiGroupName = "help";
	private GUIInterface hintsUI;
	int time = 0;
	
	/**
	 * Action when game is just started. 
	 */
	public void __onStart() {
		super.__onStart();
		//PE10.initialize();
		//world1 = PE10.peCreateWorld(new Vector3f(0,0,0), new Vector3f(0,0,0));
		
		List<GUIInterface> helpGUIList = new ArrayList<GUIInterface>();
		List<GUITexture> hintTextureList = new ArrayList<GUITexture>();
		List<GUIText> hintTextList = new ArrayList<GUIText>();			
		hintTextList.add(gameManager.getScene().getUserInterface()
				.getComponent().getTexts().getByName("inputHints"));			
		GUIInterface hintsGUI = new GUI("hints", hintTextureList, hintTextList);
		helpGUIList.add(hintsGUI);
		this.gameManager.getScene().getUserInterface().addGUIGroup(new GUIGroup(guiGroupName, helpGUIList));
		this.hintsUI = gameManager.getScene().getUserInterface().getGUIGroup(guiGroupName).get("hints");
		//PE10.peAttachBody(tree1, PE10.BODY_3D_SPHERE, world1);
		//PE10.peAttachBody(tree2, PE10.BODY_3D_SPHERE, world1);
		//PE10.peAttachBody(tree3, PE10.BODY_3D_SPHERE, world1);
	}
	
	/**
	 * Actions during the game. 
	 */
	public void __onUpdate() {
		super.__onUpdate();
		//PE10.peUpdateWorld(world1);
		if(KeyboardGame.isKeyPressed(Keyboard.KEY_N)) {
			if(hintsUI.getIsShown()) {
				hintsUI.hide();
			} else {
				hintsUI.show();
			}
		}
		time += 1;
		
		//tree1.increasePosition(0, 0.1f, 0);
		if(time == 500) {
			//EngineMain.pauseEngine(true);
		}
	}
}
