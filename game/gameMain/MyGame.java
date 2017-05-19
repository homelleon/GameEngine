package gameMain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import entities.Entity;
import fontMeshCreator.GUIText;
import gui.GUI;
import gui.GUIInterface;
import guiTextures.GUITexture;
import inputs.KeyboardGame;
import inputs.MouseGame;
import scene.Scene;

public class MyGame implements Game {
	
	private GameManager gameManager;
	private int world1;
	private Scene scene;
	GUIInterface hints;
	Entity cube7;
	int time = 0;
	
		/* 
		 * use "Main.getMap()" to get methods of Map
		 * and all objects on Map  
		 * 
		 */
		@Override
		public void onStart() {
			//PE10.initialize();
			this.gameManager = new GameManagerBasic();
			
			System.out.println(GL11.glGetString(GL11.GL_VENDOR));
			System.out.println(GL11.glGetString(GL11.GL_RENDERER));
			System.out.println(GL11.glGetString(GL11.GL_VERSION));
			//scene.setTerrainWiredFrame(true);
			//world1 = PE10.peCreateWorld(new Vector3f(0,0,0), new Vector3f(0,0,0));
			cube7 = gameManager.getScene().getEntities().getByName("Cuby4");
			cube7.increasePosition(0, 5, 0);
			List<GUITexture> hintTextureList = new ArrayList<GUITexture>();
			List<GUIText> hintTextList = new ArrayList<GUIText>();			
			hintTextList.add(gameManager.getScene().getUserInterface()
					.getComponentManager().getTexts().getByName("hint1"));
			hintTextList.add(gameManager.getScene().getUserInterface()
					.getComponentManager().getTexts().getByName("hint2"));
			hintTextList.add(gameManager.getScene().getUserInterface()
					.getComponentManager().getTexts().getByName("hint3"));
			hintTextList.add(gameManager.getScene().getUserInterface()
					.getComponentManager().getTexts().getByName("hint4"));
			hintTextList.add(gameManager.getScene().getUserInterface()
					.getComponentManager().getTexts().getByName("hint5"));
			this.hints = new GUI("hint", hintTextureList, hintTextList);
			
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
				if(hints.getIsShown()) {
					hints.hide();
				} else {
					hints.show();
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
