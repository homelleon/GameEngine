package objects.gui.guiTextures;

import org.lwjgl.util.vector.Matrix4f;

import scene.ES;
import shaders.ShaderProgram;

public class GUITextureShader extends ShaderProgram {
    
    private static final String VERTEX_FILE = ES.GUI_SHADER_PATH + "guiTextureVertexShader.txt";
    private static final String FRAGMENT_FILE = ES.GUI_SHADER_PATH + "guiTextureFragmentShader.txt";
     
    private int location_transformationMatrix;
 
    public GUITextureShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
     
     
     
 
}