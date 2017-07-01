package game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import core.EngineMain;
import core.debug.EngineDebug;
import font.GUIText;
import guis.gui.GUI;
import guis.gui.GUIInterface;
import guis.guiTextures.GUITexture;
import scene.SceneInterface;

public class Game extends Thread implements GameInterface {
	
	public GameManagerInterface gameManager;
	public int world1;
	public SceneInterface scene;
	
		/* 
		 * use "Main.getMap()" to get methods of Map
		 * and all objects on Map  
		 * 
		 */
		@Override
		public void __onStart() {			
			this.gameManager = new GameManager();
			this.scene = EngineMain.getScene();
			
			List<GUIText> versionTextList = new ArrayList<GUIText>();
			List<GUITexture> versionTextureList = new ArrayList<GUITexture>();
			versionTextList.add(this.gameManager.getScene().getUserInterface().getComponent().getTexts().getByName("version"));
			GUIInterface versionGUI = new GUI("version", versionTextureList, versionTextList);
			
			if(EngineDebug.hasDebugPermission()) {
				System.out.println(GL11.glGetString(GL11.GL_VENDOR));
				System.out.println(GL11.glGetString(GL11.GL_RENDERER));
				System.out.println(GL11.glGetString(GL11.GL_VERSION));
				versionGUI.show();
		   	}
						
		}
		
		/* 
		 * on screen update - here you can
		 * change objects on map in dynamic 
		 * use "EngineMain.getScene().getEntities().getByName("Tree1")"
		 * to manipulate Entity named "Tree1"
		 * 
		 * Don't use while loop and etc
		 */
		
		@Override
		public void __onUpdate() {
			
		}
}