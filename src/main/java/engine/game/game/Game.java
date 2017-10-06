package game.game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.debug.EngineDebug;
import game.manager.GameManager;
import game.manager.IGameManager;
import object.gui.gui.GUI;
import object.gui.gui.IGUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

public abstract class Game extends Thread implements IGame {

	public IGameManager gameManager;
	public int world1;

	/**
	 * Realize game events on engine start.<br>
	 * 
	 * Use "Main.getMap()" to get methods of Map and all objects on Map.
	 */
	@Override
	public void __onStart() {
		this.gameManager = new GameManager();

		List<GUIText> versionTextList = new ArrayList<GUIText>();
		List<GUITexture> versionTextureList = new ArrayList<GUITexture>();
		versionTextList.add(this.gameManager.getScene().getUserInterface().getComponent().getTexts().get("version"));
		IGUI versionGUI = new GUI("version", versionTextureList, versionTextList);

		if (EngineDebug.hasDebugPermission()) {
			versionGUI.show();
			String OGLVendor = GL11.glGetString(GL11.GL_VENDOR);
			String OGLRenderer = GL11.glGetString(GL11.GL_RENDERER);
			String OGLVersion = GL11.glGetString(GL11.GL_VERSION);
			String GLSLVersion = GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
			System.out.println("................");
			System.out.println("[>>OpenGL<<]");
			System.out.println("OpenGL Vendor: " + OGLVendor);
			System.out.println("OpenGL Renderer: " + OGLRenderer);			
			System.out.println("OpenGL Version: " + OGLVersion);
			System.out.println("GLSL Version: " + GLSLVersion);
			System.out.println("[X>OpenGL<X]");
			System.out.println("................");
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
	@Override
	public abstract void __onUpdate();

	/**
	 * Relize game events on engine update event on pause.
	 */
	@Override
	public abstract void __onUpdateWithPause();

}
