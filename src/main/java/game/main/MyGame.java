package main;

import java.util.stream.IntStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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
	private boolean keyboardUsed = false;
	private IGUIGroup coursorAimGroup;
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
		
		IGUIBuilder coursorGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent())
				.setTexture("coursorTexture", this.gameManager.getTextures().get("CoursorAim"));
		this.coursorAimGroup = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("coursor");
		this.coursorAimGroup.add(coursorGUIBuilder.build("coursorGUI"));
		this.coursorAimGroup.show();
		
		IGUIBuilder buttonGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		buttonGUIBuilder.setTexture("button1Texture", this.gameManager.getTextures().get("Button").clone("btnTexture1"));
		buttonGUIBuilder.setText("button1Text", gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("menu").clone("resumeText","Resume"));
		IGUIGroup buttonGroup1 = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button1");
		buttonGroup1.add(buttonGUIBuilder.build("button1GUI"));
		mainMenu.add((GUIObject) buttonGroup1);
		
		
		buttonGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		buttonGUIBuilder.setTexture("button2Texture", gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Button").clone("btnTexture2"));
		
		System.out.println(gameManager.getScene().getUserInterface()
		.getComponent().getTextures().get("Button").getTexture().getHeight());
		System.out.println(gameManager.getScene().getUserInterface()
				.getComponent().getTextures().get("Button").getTexture().getWidth());
		
		buttonGUIBuilder.setText("button2Text", gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("menu").clone("startText", "Start"));
		IGUIGroup buttonGroup2 = this.gameManager.getScene().getUserInterface().getGroups().createEmpty("button2");
		buttonGroup2.add(buttonGUIBuilder.build("button2GUI"));
		mainMenu.add((GUIObject) buttonGroup2);
		
		buttonGUIBuilder = new GUIBuilder(this.gameManager.getScene().getUserInterface().getComponent());
		buttonGUIBuilder.setTexture("button3Texture", this.gameManager.getTextures().get("Button").clone("btnTexture3"));
		buttonGUIBuilder.setText("button3Text", gameManager.getScene().getUserInterface()
				.getComponent().getTexts().get("menu").clone("exitText", "Exit"));
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
		IEntity entity = this.gameManager.getScene().getEntities().get("player1");
		
		IGUIAnimation<IGUIButton> buttonAnimation = (button, time, vector)-> {
			IVectorInjectable injection = (vec) -> {
				IntStream.range(0, time)
				.mapToObj(i -> button)
				.forEach(btn -> {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						((GUIButton)btn).increaseScale(vec);
					});
			};
			button.getGroup().getAll().stream()
				.flatMap(gui -> gui.getTextures().stream())
				.forEach(texture -> {
					texture.setMixColored(true);
					texture.setMixColor(new Vector3f(1,0,0));
				});
			injection.inject(vector);
			injection.inject(new Vector2f(-vector.x, -vector.y));
			this.buttonScale = false;
			button.getGroup().getAll().stream()
			.flatMap(gui -> gui.getTextures().stream())
			.forEach(texture -> texture.setMixColored(false));
		};
		IGUIAnimation<IGUIButton> buttonSelectAnimation = (button, time, vector)-> {
			IVectorInjectable injection = (vec) -> {
				IntStream.range(0, time)
				.mapToObj(i -> button)
				.forEach(btn -> {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						((GUIButton)btn).increaseScale(vec);
					});
			};
			button.getGroup().getAll().stream()
				.flatMap(gui -> gui.getTextures().stream())
				.forEach(texture -> {
					texture.setMixColored(true);
					texture.setMixColor(new Vector3f(0,0,1));
				});
			injection.inject(vector);
		};
		
		IGUIAnimation<IGUIButton> buttonDeselectAnimation = (button, time, vector)-> {
			IVectorInjectable injection = (vec) -> {
				IntStream.range(0, time)
				.mapToObj(i -> button)
				.forEach(btn -> {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						((GUIButton)btn).increaseScale(vec);
					});
			};
			injection.inject(new Vector2f(-vector.x, -vector.y));
			button.getGroup().getAll().stream()
			.flatMap(gui -> gui.getTextures().stream())
			.forEach(texture -> texture.setMixColored(false));
		};
		
		int time = 1;
		Vector2f changeVector = new Vector2f(0.05f,0.05f); 
		IAction action1 = () -> {
			buttonAnimation.start(button1, time, changeVector);
			EngineMain.pauseEngine(false);
		};
		
		IAction actionSelect1 = () -> {
			buttonSelectAnimation.start(button1, time, changeVector);
		};
		
		IAction actionDeselect1 = () -> {
			buttonDeselectAnimation.start(button1, time, changeVector);
		};
		
		IAction action2 = () -> {
			buttonAnimation.start(button2, time, changeVector);
			this.gameManager.getScene().getPlayer().setPosition(new Vector3f(0,0,0));
		};
		

		IAction actionSelect2 = () -> {
			buttonSelectAnimation.start(button2, time, changeVector);
		};
		
		IAction actionDeselect2 = () -> {
			buttonDeselectAnimation.start(button2, time, changeVector);
		};
		
		IAction action3 = () -> {
			buttonAnimation.start(button3, time, changeVector);
			EngineMain.exit();
		};
		

		IAction actionSelect3 = () -> {
			buttonSelectAnimation.start(button3, time, changeVector);
		};
		
		IAction actionDeselect3 = () -> {
			buttonDeselectAnimation.start(button3, time, changeVector);
		};
		
		button1.setUseAction(action1);
		button1.setSelectedAction(actionSelect1);
		button1.setDeselectedAction(actionDeselect1);
		
		button2.setUseAction(action2);
		button2.setSelectedAction(actionSelect2);
		button2.setDeselectedAction(actionDeselect2);
		
		button3.setUseAction(action3);
		button3.setSelectedAction(actionSelect3);
		button3.setDeselectedAction(actionDeselect3);
		
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
		IGUIMenu activeMenu = menuSystem.getActivated();
		if(EngineMain.getIsEnginePaused()) {
			if(this.coursorAimGroup.getIsVisible()){this.coursorAimGroup.hide();}
			if(!menuSystem.getIsVisible()){menuSystem.show();}
			if(Mouse.getDX()>0 || Mouse.getDY()>0) {
				this.keyboardUsed = false;
			}
			if(KeyboardGame.isKeyPressed(Keyboard.KEY_UP)) {
				activeMenu.selectNextButton();
				this.keyboardUsed = true;
			}
			else if(KeyboardGame.isKeyPressed(Keyboard.KEY_DOWN)) {
				activeMenu.selectPreviousButton();
				this.keyboardUsed = true;
			}
			else if(KeyboardGame.isKeyPressed(Keyboard.KEY_RETURN)) {
				activeMenu.useButton();
				this.keyboardUsed = true;
			}
			if(!this.buttonScale && !this.keyboardUsed) {
				menuSystem.getActivated().getAllButtons().stream()
				.filter(button -> button.getIsMouseOver(
						this.gameManager.getScene().getMousePicker().getCurrentScreanPoint()))
				.filter(button -> !button.getIsSelected())
				.forEach(button -> button.select());
				
				menuSystem.getActivated().getAllButtons().stream()
				.filter(button -> !button.getIsMouseOver(
						this.gameManager.getScene().getMousePicker().getCurrentScreanPoint()))
				.filter(button -> button.getIsSelected())
				.forEach(button -> button.deselect());
				if(MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {
					menuSystem.getActivated().getAllButtons().stream()
						.filter(button -> button.getIsMouseOver(
								this.gameManager.getScene().getMousePicker().getCurrentScreanPoint()))
						.forEach(button -> {
							button.use();
							this.buttonScale = true;
						});
				}
			}
		} else {
			if(menuSystem.getIsVisible()) {menuSystem.hide();}
			if(!this.coursorAimGroup.getIsVisible()){this.coursorAimGroup.show();}
		}
		
	}
}
