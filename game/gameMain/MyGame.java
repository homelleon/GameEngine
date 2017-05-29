package gameMain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import debug.EngineDebug;
import entities.EntityInterface;
import fontMeshCreator.GUIText;
import gui.GUI;
import gui.GUIGroup;
import gui.GUIInterface;
import guiTextures.GUITexture;
import inputs.KeyboardGame;
import scene.SceneInterface;

public class MyGame implements GameInterface {
	
	private GameManagerInterface gameManager;
	private int world1;
	private SceneInterface scene;
	private String guiGroupName = "help";
	private GUIInterface hintsUI;
	EntityInterface cube7;
	int time = 0;
	
		/* 
		 * use "Main.getMap()" to get methods of Map
		 * and all objects on Map  
		 * 
		 */
		@Override
		public void onStart() {
			//PE10.initialize();
			this.gameManager = new GameManager();
			if(EngineDebug.hasDebugPermission()) {
				System.out.println(GL11.glGetString(GL11.GL_VENDOR));
				System.out.println(GL11.glGetString(GL11.GL_RENDERER));
				System.out.println(GL11.glGetString(GL11.GL_VERSION));
		   	}			
			//scene.setTerrainWiredFrame(true);
			//world1 = PE10.peCreateWorld(new Vector3f(0,0,0), new Vector3f(0,0,0));
			cube7 = gameManager.getScene().getEntities().getByName("Cuby4");
			cube7.increasePosition(0, 10, 0);
			List<GUIInterface> helpGUIList = new ArrayList<GUIInterface>();
			List<GUITexture> hintTextureList = new ArrayList<GUITexture>();
			List<GUIText> hintTextList = new ArrayList<GUIText>();			
			hintTextList.add(gameManager.getScene().getUserInterface()
					.getComponent().getTexts().getByName("inputHints"));			
			GUIInterface hintsGUI = new GUI("hints", hintTextureList, hintTextList);
			helpGUIList.add(hintsGUI);
			gameManager.getScene().getUserInterface().addGUIGroup(new GUIGroup(guiGroupName, helpGUIList));
			this.hintsUI = gameManager.getScene().getUserInterface().getGUIGroup(guiGroupName).get("hints");
			//PE10.peAttachBody(tree1, PE10.BODY_3D_SPHERE, world1);
			//PE10.peAttachBody(tree2, PE10.BODY_3D_SPHERE, world1);
			//PE10.peAttachBody(tree3, PE10.BODY_3D_SPHERE, world1);			
		}
		
		/* 
		 * on screen update - here you can
		 * change objects on map in dynamic 
		 * use "Main.getMap().getEntities.get("Tree1")"
		 * to manipulate Entity named "Tree1"
		 * 
		 * Don't use while loop and etc
		 */
		
		@Override
		public void onUpdate() {
			if(KeyboardGame.isKeyPressed(Keyboard.KEY_N)) {
				if(hintsUI.getIsShown()) {
					hintsUI.hide();
				} else {
					hintsUI.show();
				}
			}
			time += 1;
			//cube7.increasePosition(0, 0.1f, 0);
			//PE10.peUpdateWorld(world1);
			//tree1.increasePosition(0, 0.1f, 0);
			if(time == 500) {
				//EngineMain.pauseEngine(true);
			}
			
		}
}
