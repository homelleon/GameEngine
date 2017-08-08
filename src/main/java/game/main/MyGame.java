package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import core.EngineMain;
import game.game.Game;
import object.gui.group.IGUIGroup;
import object.gui.gui.GUIBuilder;
import object.gui.gui.IGUIBuilder;
import object.gui.gui.IGUI;
import object.gui.pattern.button.GUIButton;
import object.gui.pattern.button.IGUIButton;
import object.gui.pattern.menu.GUIMenu;
import object.gui.pattern.menu.IGUIMenu;
import object.gui.pattern.object.GUIObject;
import object.input.KeyboardGame;

public class MyGame extends Game {
	
	private String guiGroupName = "help";
	private IGUIGroup helpGroup;
	private IGUI hintsUI;
	int time = 0;
	
	IGUIMenu menu = new GUIMenu("menu1");
	
	/**
	 * Action when game is just started. 
	 */
	@Override
	public void __onStart() {
		super.__onStart();
		//PE10.initialize();
		//world1 = PE10.peCreateWorld(new Vector3f(0,0,0), new Vector3f(0,0,0));
		
		//--------help hints GUI-------------//
		IGUIBuilder helpGUIBuilder = new GUIBuilder();
		helpGUIBuilder.setText(gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("inputHints"));
		this.helpGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty(guiGroupName);
		this.helpGroup.add(helpGUIBuilder.getGUI("help"));
		
		//-------sign button GUI-------------//
		IGUIBuilder signGUIBuilder = new GUIBuilder();
		signGUIBuilder.setTexture(gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Sign"));
		IGUIGroup signGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("sign");
		signGroup.add(signGUIBuilder.getGUI("sign"));
		((GUIObject) signGroup).show();
		
		//-----------button GUI--------------//
		IGUIBuilder buttonGUIBuilder = new GUIBuilder();
		buttonGUIBuilder.setTexture(gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Button"));
		IGUIGroup buttonGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button");
		buttonGroup.add(buttonGUIBuilder.getGUI("button"));
		((GUIObject) buttonGroup).show();
		
		IGUIButton button1 = new GUIButton("signButton1", signGUIBuilder.getGUI("sign1"), new Vector2f(400,300), new Vector2f(600,500));
		IGUIButton button2 = new GUIButton("signButton2", signGUIBuilder.getGUI("sign2"), new Vector2f(400,300), new Vector2f(600,500));
		IGUIButton button3 = new GUIButton("signButton3", signGUIBuilder.getGUI("sign3"), new Vector2f(400,300), new Vector2f(600,500));
		this.menu.add((GUIObject) button1);
		this.menu.add((GUIObject) button2);
		this.menu.add((GUIObject) button3);
		this.menu.selectNextButton();
		//PE10.peAttachBody(tree1, PE10.BODY_3D_SPHERE, world1);
		//PE10.peAttachBody(tree2, PE10.BODY_3D_SPHERE, world1);
		//PE10.peAttachBody(tree3, PE10.BODY_3D_SPHERE, world1);
	}
	
	/**
	 * Actions during the game. 
	 */
	@Override
	public void __onUpdate() {
		//PE10.peUpdateWorld(world1);		
		if(KeyboardGame.isKeyPressed(Keyboard.KEY_N)) {
			if(((GUIObject) this.helpGroup).getIsShown()) {
				((GUIObject) this.helpGroup).hide();
			} else {
				((GUIObject) this.helpGroup).show();
			}
		}
	}

	@Override
	public void __onUpdateWithPause() {
		if(EngineMain.getIsEnginePaused()) {
			if(KeyboardGame.isKeyPressed(Keyboard.KEY_UP)) {
				menu.selectNextButton();				
			} else if(KeyboardGame.isKeyPressed(Keyboard.KEY_DOWN)) {
				menu.selectPreviousButton();
			} else if(KeyboardGame.isKeyPressed(Keyboard.KEY_RETURN)) {
				menu.useButton();
			}
		}
		
		time += 1;
	}
}
