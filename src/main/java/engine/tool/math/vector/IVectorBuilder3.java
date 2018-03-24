package tool.math.vector;

/**
 * Builder interface for 3 dimentional vectors.
 * 
 * @author homelleon
 * @version 1.0
 * @param <T> type of stored coordinates
 * @param <R> type of vector class
 * @see VectorBuilder3f
 * @see VectorBuilder3i
 */
public interface IVectorBuilder3<T, R> {
	
	/**
	 * Sets x coordinate for vector.
	 * 
	 * @param x <T> value of coordinate
	 * @return builder
	 */
	IVectorBuilder3<T, R> setX(T x);
	
	/**
	 * Sets y coordinate for vector.
	 * 
	 * @param y <T> value of coordinate
	 * @return builder
	 */
	IVectorBuilder3<T, R> setY(T y);
	
	/**
	 * Sets z coordinate for vector.
	 * 
	 * @param z <T> value of coordinate
	 * @return builder
	 */
	IVectorBuilder3<T, R> setZ(T z);
	
	IVectorBuilder3<T, R> set(T x, T y, T z);
	
	/**
	 * Constracts vector by initialied paramethers.
	 * 
	 * @return vector
	 */
	R build();
}
