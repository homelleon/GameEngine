package shader;

@FunctionalInterface
public interface LoadShaderVariableFunction<T> {
	
	void load(T value);
	
}
