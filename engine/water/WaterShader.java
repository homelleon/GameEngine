package water;
 
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import cameras.Camera;
import lights.Light;
import scene.ES;
import shaders.ShaderProgram;
import toolbox.Maths;
 
public class WaterShader extends ShaderProgram {
 
    private final static String VERTEX_FILE = ES.WATER_SHADER_PATH + "waterVertex.txt";
    private final static String FRAGMENT_FILE = ES.WATER_SHADER_PATH + "waterFragment.txt";
 
    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_reflectionTexture;
    private int location_refractionTexture;
    private int location_dudvMap;
    private int location_moveFactor;
    private int location_cameraPosition;
    private int location_normalMap;
    private int location_lightColour;
    private int location_lightPosition;
    private int location_depthMap;
    private int location_tiling;
    private int location_skyColour;
    private int location_fogDenstity;
    private int location_waveStrength;
    
    public WaterShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
    	super.bindFragOutput(0, "out_Color");
		super.bindFragOutput(1, "out_BrightColor");
        bindAttribute(0, "position");
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_reflectionTexture = getUniformLocation("reflectionTexture");
        location_refractionTexture = getUniformLocation("refractionTexture");
        location_dudvMap = getUniformLocation("dudvMap");
        location_moveFactor = getUniformLocation("moveFactor");
        location_cameraPosition = getUniformLocation("cameraPosition");
        location_normalMap = getUniformLocation("normalMap");
        location_lightColour = getUniformLocation("lightColour");
        location_lightPosition = getUniformLocation("lightPosition");
        location_depthMap = getUniformLocation("depthMap");
        location_tiling = getUniformLocation("tiling");
        location_skyColour = getUniformLocation("skyColour");
        location_fogDenstity = getUniformLocation("fogDensity");
        location_waveStrength = getUniformLocation("waveStrength");
    }
    
    public void connectTextureUnits() {
    	super.loadInt(location_reflectionTexture, 0);
    	super.loadInt(location_refractionTexture, 1);
    	super.loadInt(location_dudvMap, 2);
    	super.loadInt(location_normalMap, 3);
    	super.loadInt(location_depthMap, 4);
    }
    
    public void loadLight(Light sun) {
    	super.loadVector(location_lightColour, sun.getColour());
    	super.loadVector(location_lightPosition, sun.getPosition());
    }
    
    public void loadMoveFactor(float factor) {
    	super.loadFloat(location_moveFactor, factor);
    }
    
    public void loadTilingSize(float size) {
    	super.loadFloat(location_tiling, size);
    }
    
    public void loadWaveStrength(float strength) {
    	super.loadFloat(location_waveStrength, strength);
    }
    
    public void loadSkyColour(float r, float g, float b) {
    	super.loadVector(location_skyColour, new Vector3f(r,g,b));
    }
    
    public void loadFogDensity(float density) {
    	super.loadFloat(location_fogDenstity, density);
    }
    
 
    public void loadProjectionMatrix(Matrix4f projection) {
        loadMatrix(location_projectionMatrix, projection);
    }
     
    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        loadMatrix(location_viewMatrix, viewMatrix);
        super.loadVector(location_cameraPosition, camera.getPosition());
    }
 
    public void loadModelMatrix(Matrix4f modelMatrix) {
        loadMatrix(location_modelMatrix, modelMatrix);
    }
 
}