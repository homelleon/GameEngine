package main;

import java.util.stream.IntStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import core.EngineMain;
import game.game.Game;
import gameTools.GUIGroupBuilderTexture;
import gameTools.IGUIGroupBuilderTexture;
import object.bounding.BoundingQuad;
import object.entity.entity.IEntity;
import object.gui.group.IGUIGroup;
import object.gui.gui.IGUI;
import object.gui.gui.builder.GUIBuilder;
import object.gui.gui.builder.IGUIBuilder;
import object.gui.pattern.button.GUIButton;
import object.gui.pattern.button.IAction;
import object.gui.pattern.button.IGUIAnimation;
import object.gui.pattern.button.IGUIButton;
import object.gui.pattern.button.IVectorInjectable;
import object.gui.pattern.menu.GUIMenu;
import object.gui.pattern.menu.IGUIMenu;
import object.gui.pattern.object.GUIObject;
import object.gui.system.IGUIMenuSystem;
import object.input.KeyboardGame;
import object.input.MouseGame;

public class MyGame extends Game {
	
	private String guiGroupName = "help";
	private IGUIGroup helpGroup;
	private IGUI hintsUI;
	private boolean buttonScale = false;
	IGUIMenuSystem menuSystem;
	
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
		IGUIBuilder helpGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		helpGUIBuilder.setText("inputHintsText",this.gameManager.getTexts().get("inputHints"));
		this.helpGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty(guiGroupName);
		this.helpGroup.add(helpGUIBuilder.build("help"));
		helpGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		helpGUIBuilder.setText("versionText",this.gameManager.getTexts().get("version"));
		this.helpGroup.add(helpGUIBuilder.build("version"));
		//-------sign GUI-------------//
		IGUIGroupBuilderTexture groupBuilder = new GUIGroupBuilderTexture(this.gameManager.getScene().getUserInterface())
				.setTextureName("Sign");
		IGUIGroup signGroup = groupBuilder.build("sign");
		((GUIObject) signGroup).show();
		
		//-----------button GUI--------------//		
		menuSystem = this.gameManager.getScene().getUserInterface().getMenus();
		IGUIMenu mainMenu = new GUIMenu("first menu");
		menuSystem.add(mainMenu);
		menuSystem.active(mainMenu.getName());
		
		IGUIBuilder buttonGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		buttonGUIBuilder.setTexture("button1Texture", this.gameManager.getTextures().get("Button"));
		buttonGUIBuilder.setText("button1Text", gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("buttonLabel1"));
		IGUIGroup buttonGroup1 = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button1");
		buttonGroup1.add(buttonGUIBuilder.build("button1GUI"));
		mainMenu.add((GUIObject) buttonGroup1);

		
		buttonGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		buttonGUIBuilder.setTexture("button2Texture", gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Button"));
		
		System.out.println(gameManager.getScene().getUserInterface()
		.getComponent().getTextures().get("Button").getTexture().getHeight());
		System.out.println(gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Button").getTexture().getWidth());
		
		buttonGUIBuilder.setText("button2Text", gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("start"));
		IGUIGroup buttonGroup2 = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button2");
		buttonGroup2.add(buttonGUIBuilder.build("button2GUI"));
		mainMenu.add((GUIObject) buttonGroup2);
		
		buttonGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		buttonGUIBuilder.setTexture("button3Texture", this.gameManager.getTextures().get("Button"));
		buttonGUIBuilder.setText("button3Text", gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("exit"));
		IGUIGroup buttonGroup3 = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button3");
		buttonGroup3.add(buttonGUIBuilder.build("button3GUI"));
		mainMenu.add((GUIObject) buttonGroup3);
		
		BoundingQuad bQuad = new BoundingQuad(new Vector2f(-0.41f,-0.05f), new Vector2f(0.45f,0.2f)); 
		
		IGUIButton button1 = new GUIButton("menuButton1", buttonGroup1);
		button1.setBoundingArea(bQuad, true);
		
		button1.move(new Vector2f(0,0.4f));
		
		IGUIButton button2 = new GUIButton("menuButton2", buttonGroup2);
		button2.setBoundingArea(bQuad, false);
		
		IGUIButton button3 = new GUIButton("menuButton3", buttonGroup3);
		button3.setBoundingArea(bQuad, true);
		button3.move(new Vector2f(0,-0.4f));
		
		mainMenu.add((GUIObject) button1);
		mainMenu.add((GUIObject) button2);
		mainMenu.add((GUIObject) button3);
		mainMenu.selectNextButton();
		IEntity entity = this.gameManager.getScene().getEntities().get("player1");
		
		IGUIAnimation animation = (button, time, vector)-> {
			IVectorInjectable injection = (vec) -> {
				IntStream.range(0, time)
				.mapToObj(i -> button)
				.forEach(btn -> {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						btn.increaseScale(vec);
					});
			};
			Runnable animThread = () -> {
				injection.inject(vector);
				injection.inject(new Vector2f(-vector.x, -vector.y));
				this.buttonScale = false;
			};
			new Thread(animThread).start();
		};
		int time = 2;
		Vector2f changeVector = new Vector2f(0,0.1f); 
		IAction action1 = () -> {
			animation.start(button1, time, changeVector);
		};
		
		IAction action2 = () -> {
			animation.start(button2, time, changeVector);
		};
		
		IAction action3 = () -> {
			animation.start(button3, time, changeVector);
			EngineMain.pauseEngine(false);
		};
		
		button1.attachAction(action1);
		button2.attachAction(action2);
		button3.attachAction(action3);
		
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
			((GUIObject) this.helpGroup).switchVisibility();			
		}
	}

	@Override
	public void __onUpdateWithPause() {
		if(EngineMain.getIsEnginePaused()) {
			menuSystem.getActivated().show();
			if(KeyboardGame.isKeyPressed(Keyboard.KEY_UP)) {
				menuSystem.getActivated().selectNextButton();				
			} else if(KeyboardGame.isKeyPressed(Keyboard.KEY_DOWN)) {
				menuSystem.getActivated().selectPreviousButton();
			} else if(KeyboardGame.isKeyPressed(Keyboard.KEY_RETURN)) {
				menuSystem.getActivated().useButton();
			}
			Vector2f mousePos = new Vector2f(2 * Mouse.getX() / (float) Display.getWidth() - 1f, 2 * Mouse.getY() / (float) Display.getHeight() - 1f);
			System.out.println(mousePos);
			menuSystem.getActivated().getAllButtons().stream()
			.filter(button -> button.getIsMouseOver(mousePos))
			.forEach(button -> {
				if(!this.buttonScale && MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {
					button.use();
					this.buttonScale = true;
				}
			});	
		} else {
			menuSystem.hide("first menu");
		}
		
	}
}
