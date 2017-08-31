package main;

import java.util.stream.IntStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import core.EngineMain;
import game.game.Game;
import object.entity.entity.IEntity;
import object.gui.group.IGUIGroup;
import object.gui.gui.IGUI;
import object.gui.gui.builder.GUIBuilder;
import object.gui.gui.builder.IGUIBuilder;
import object.gui.pattern.button.GUIButton;
import object.gui.pattern.button.IGUIButton;
import object.gui.pattern.menu.GUIMenu;
import object.gui.pattern.menu.IGUIMenu;
import object.gui.pattern.object.GUIObject;
import object.gui.system.IGUIMenuSystem;
import object.input.KeyboardGame;

public class MyGame extends Game {
	
	private String guiGroupName = "help";
	private IGUIGroup helpGroup;
	private IGUI hintsUI;		
	IGUIMenuSystem menuSystem;
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
		helpGUIBuilder.setText(this.gameManager.getTexts().get("inputHints"));
		this.helpGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty(guiGroupName);
		this.helpGroup.add(helpGUIBuilder.build("help"));
		helpGUIBuilder = new GUIBuilder();
		helpGUIBuilder.setText(this.gameManager.getTexts().get("version"));
		this.helpGroup.add(helpGUIBuilder.build("version"));
		//-------sign button GUI-------------//
		IGUIBuilder signGUIBuilder = new GUIBuilder();
		signGUIBuilder.setTexture(this.gameManager.getTextures().get("Sign"));
		IGUIGroup signGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("sign");
		signGroup.add(signGUIBuilder.build("sign"));
		((GUIObject) signGroup).show();
		
		//-----------button GUI--------------//		
		menuSystem = scene.getUserInterface().getMenus();
		IGUIMenu mainMenu = new GUIMenu("first menu");
		menuSystem.add(mainMenu);
		
		IGUIBuilder buttonGUIBuilder = new GUIBuilder();
		buttonGUIBuilder.setTexture(this.gameManager.getTextures().get("Button"));
		buttonGUIBuilder.setText(gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("buttonLabel1"));
		IGUIGroup buttonGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button");
		buttonGroup.add(buttonGUIBuilder.build("button"));
		mainMenu.add((GUIObject) buttonGroup);

		
		buttonGUIBuilder = new GUIBuilder();
		buttonGUIBuilder.setTexture(gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Button"));
		buttonGUIBuilder.setText(gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("buttonLabel1"));
		buttonGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button1");
		buttonGroup.add(buttonGUIBuilder.build("button"));
		mainMenu.add((GUIObject) buttonGroup);
		
		buttonGUIBuilder = new GUIBuilder();
		buttonGUIBuilder.setTexture(this.gameManager.getTextures().get("Button"));
		buttonGUIBuilder.setText(gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("buttonLabel1"));
		buttonGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button2");
		buttonGroup.add(buttonGUIBuilder.build("button"));
		
		mainMenu.add((GUIObject) buttonGroup);
		
		IGUIButton button1 = new GUIButton("signButton1", signGUIBuilder.build("button"), new Vector2f(400,300), new Vector2f(600,500));
		IGUIButton button2 = new GUIButton("signButton2", signGUIBuilder.build("button"), new Vector2f(400,300), new Vector2f(600,500));
		IGUIButton button3 = new GUIButton("signButton3", signGUIBuilder.build("button"), new Vector2f(400,300), new Vector2f(600,500));
		mainMenu.add((GUIObject) button1);
		mainMenu.add((GUIObject) button2);
		mainMenu.add((GUIObject) button3);
		mainMenu.selectNextButton();
		IEntity entity = this.gameManager.getScene().getEntities().get("player1");
		
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
			menuSystem.show("first menu");
			if(KeyboardGame.isKeyPressed(Keyboard.KEY_UP)) {
				menuSystem.get("first menu").selectNextButton();				
			} else if(KeyboardGame.isKeyPressed(Keyboard.KEY_DOWN)) {
				menuSystem.get("first menu").selectPreviousButton();
			} else if(KeyboardGame.isKeyPressed(Keyboard.KEY_RETURN)) {
				menuSystem.get("first menu").useButton(()-> this.menuSystem.hide());
			}
		} else {
			menuSystem.hide("first menu");
		}
		
		time += 1;
	}
}
