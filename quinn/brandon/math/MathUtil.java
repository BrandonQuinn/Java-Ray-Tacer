package quinn.brandon.math;

import org.joml.Rayd;
import org.joml.Vector3d;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

public class MathUtil
{
	
	/**
	 * Limit the size of the given value.
	 * 
	 * @param v Value
	 * @param min Minimum
	 * @param max Maximum
	 * @return The clamped result.
	 */
	public static double clamp(double v, double min, double max)
	{
		return Math.max(min, Math.min(max, v));
	}
	
	/**
	 * Limit the size of the given value.
	 * 
	 * @param v Value
	 * @param min Minimum
	 * @param max Maximum
	 * @return The clamped result.
	 */
	public static int clamp(int v, int min, int max)
	{
		return Math.max(min, Math.min(max, v));
	}
	
	/**
	 * Returns the normalize direction vector.
	 * 
	 * @param origin starting point
	 * @param destination where is the origin pointing to (the direction)
	 * @return
	 */
	public static Vector3d direction(Vector3d origin, Vector3d destination) {
		Vector3d result = new Vector3d(destination.x - origin.x, 
				destination.y - origin.y, 
				destination.z - origin.z).normalize();
		return result;
	}
	
	/**
	 * Finds the point along the ray at a distance given.
	 * @param ray The ray
	 * @param distance Distance along the ray to get the point
	 * @return The point
	 */
	public static Vector3d pointAlongRay(Rayd ray, double distance)
	{
		return new Vector3d(ray.dX, ray.dY, ray.dZ).mul(distance).add(new Vector3d(ray.oX, ray.oY, ray.oZ));
	}
}
