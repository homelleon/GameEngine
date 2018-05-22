package core.game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.EngineDebug;
import object.gui.GUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

public abstract class Game extends Thread {

	public GameManager gameManager;
	public int world1;

	/**
	 * Realize game events on engine start.<br>
	 * 
	 * Use "Main.getMap()" to get methods of Map and all objects on Map.
	 */
	public void __onStart() {
		this.gameManager = new GameManagerImpl();

		List<GUIText> versionTextList = new ArrayList<GUIText>();
		List<GUITexture> versionTextureList = new ArrayList<GUITexture>();
		versionTextList.add(this.gameManager.getScene().getUI().getComponent().getTexts().get("version"));
		GUI versionGUI = new GUI("version", versionTextureList, versionTextList);

		if (EngineDebug.hasDebugPermission()) {
			versionGUI.show();
			String OGLVendor = GL11.glGetString(GL11.GL_VENDOR);
			String OGLRenderer = GL11.glGetString(GL11.GL_RENDERER);
			String OGLVersion = GL11.glGetString(GL11.GL_VERSION);
			String GLSLVersion = GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
			EngineDebug.printBorder();
			EngineDebug.printOpen("OpenGL");
			EngineDebug.println("OpenGL Vendor: " + OGLVendor, 1);
			EngineDebug.println("OpenGL Renderer: " + OGLRenderer, 1);			
			EngineDebug.println("OpenGL Version: " + OGLVersion, 1);
			EngineDebug.println("GLSL Version: " + GLSLVersion, 1);
			EngineDebug.printClose("OpenGL");
			EngineDebug.printBorder();
		}

	}

	/**
	 * Realize game events on engine update.<br>
	 * 
	 * On screen update - here you can change objects on map in dynamic use
	 * "EngineMain.getScene().getEntities().getByName("Tree1")" to manipulate
	 * Entity named "Tree1".
	 * 
	 * Don't use while loop and etc.
	 */
	public abstract void __onUpdate();

	/**
	 * Relize game events on engine update event on pause.
	 */
	public abstract void __onUpdateWithPause();

}
