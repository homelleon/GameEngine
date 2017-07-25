package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import game.game.Game;
import object.gui.control.button.GUIButton;
import object.gui.control.button.GUIButtonInterface;
import object.gui.group.GUIGroup;
import object.gui.group.GUIGroupInterface;
import object.gui.gui.GUI;
import object.gui.gui.GUIInterface;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import object.input.KeyboardGame;

public class MyGame extends Game {
	
	private String guiGroupName = "help";
	private GUIInterface hintsUI;
	int time = 0;
	
	GUIButtonInterface button;
	
	/**
	 * Action when game is just started. 
	 */
	public void __onStart() {
		super.__onStart();
		//PE10.initialize();
		//world1 = PE10.peCreateWorld(new Vector3f(0,0,0), new Vector3f(0,0,0));
		
		//--------help hints GUI-------------//
		List<GUIInterface> helpGUIList = new ArrayList<GUIInterface>();
		List<GUITexture> hintTextureList = new ArrayList<GUITexture>();
		List<GUIText> hintTextList = new ArrayList<GUIText>();			
		hintTextList.add(gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("inputHints"));			
		GUIInterface hintsGUI = new GUI("hints", hintTextureList, hintTextList);
		helpGUIList.add(hintsGUI);
		this.gameManager.getScene().getUserInterface().addGUIGroup(new GUIGroup(guiGroupName, helpGUIList));
		this.hintsUI = gameManager.getScene().getUserInterface().getGUIGroup(guiGroupName).get("hints");
		
		//-------sign button GUI-------------//
		GUITexture sign = gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Sign");
		List<GUITexture> signTextureList = new ArrayList<GUITexture>();
		signTextureList.add(sign);
		GUIInterface signGUI = new GUI("sign", signTextureList, new ArrayList<GUIText>());
		GUIGroupInterface signGroup = this.gameManager.getScene().getUserInterface().createEmptyGUIGroup("sign");
		signGroup.add(signGUI);
		signGroup.showAll();
		
		this.button = new GUIButton("signButton", signGUI, new Vector2f(400,300), new Vector2f(600,500));
		//PE10.peAttachBody(tree1, PE10.BODY_3D_SPHERE, world1);
		//PE10.peAttachBody(tree2, PE10.BODY_3D_SPHERE, world1);
		//PE10.peAttachBody(tree3, PE10.BODY_3D_SPHERE, world1);
	}
	
	/**
	 * Actions during the game. 
	 */
	public void __onUpdate() {
		//PE10.peUpdateWorld(world1);
		if(KeyboardGame.isKeyPressed(Keyboard.KEY_U)) {
			if(!button.getIsSelected()) {
				button.select();
				System.out.println("selected: " + button.getIsSelected());
			} else {
				button.deselect();
				System.out.println("selected: " + button.getIsSelected());
			}				
		}
		
		if(KeyboardGame.isKeyPressed(Keyboard.KEY_N)) {
			if(hintsUI.getIsShown()) {
				hintsUI.hide();
			} else {
				hintsUI.show();
			}
		}
	}

	@Override
	public void __onUpdateWithPause() {
		Vector2f mouseFlatPoint = new Vector2f(Mouse.getX(), Mouse.getY());
		if(button.getIsMouseOver(mouseFlatPoint)) {
			
		} else {
			
		}

		time += 1;
	}
}
