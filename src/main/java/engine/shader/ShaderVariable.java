package shader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.lwjgl.util.vector.Vector4f;

import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class ShaderVariable<T> {
	
	private String target;
	private LoadShaderVariableFunction<T> loadFunction;
	private Shader shader;
	
	public ShaderVariable(String target) {
		this.target = target;
		Type superclass = getClass().getGenericSuperclass();
		Type type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
		if (type.equals(Integer.class)) {
			loadFunction = (T value) -> {
				shader.loadInt(target, (Integer) value);
			};
			return;
		}
		
//		if (T instanceof Float) {
//			loadFunction = (T value) -> {
//				shader.loadFloat(target, (Float) value);
//			};
//			return;
//		}
//		
//		if (T instanceof Vector2f) {
//			loadFunction = (T value) -> {
//				shader.load2DVector(target, (Vector2f) value);
//			};
//			return;
//		}
//		
//		if (T instanceof Vector3f) {
//			loadFunction = (T value) -> {
//				shader.load3DVector(target, (Vector3f) value);
//			};
//			return;
//		}
//		
//		if (T instanceof Color) {
//			loadFunction = (T value) -> {
//				shader.loadColor(target, (Color) value);
//			};
//			return;
//		}
//		
//		if (T instanceof Vector4f) {
//			loadFunction = (T value) -> {
//				shader.load4DVector(target, (Vector4f) value);
//			};
//			return;
//		}
//		
//		if (T instanceof Boolean) {
//			loadFunction = (T value) -> {
//				shader.loadBoolean(target, (Boolean) value);
//			};
//			return;
//		}
//		
//		if (T instanceof Matrix4f) {
//			loadFunction = (T value) -> {
//				shader.loadMatrix(target, (Matrix4f) value);
//			};
//			return;
//		}
		
//		throw new ClassNotFoundException("ShaderVariable.constructor: incorrect shader variable type.");		
	}
	
	public void bind(Shader shader, T value) {
		this.shader = shader;
		shader.addUniform(target);
	}
	
	public void load(T value) {
		loadFunction.load(value);
	}
	
}
