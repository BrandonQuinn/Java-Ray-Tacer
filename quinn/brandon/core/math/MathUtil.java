package quinn.brandon.core.math;

import org.joml.LineSegmentd;
import org.joml.Rayd;
import org.joml.Vector2d;
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
	
	/**
	 * Calculates and returns the midpoint based on the two points in the
	 * given LineSegmentd
	 * @see LineSegmentd
	 * 
	 * @param segment
	 * @return
	 */
	public static Vector3d midpoint(LineSegmentd segment) 
	{
		return new Vector3d((segment.bX - segment.aX) / 2, (segment.bY - segment.aY) / 2, (segment.bZ - segment.aZ) / 2);
	}

	/**
	 * Returns a 2D normalisation direction vector from the origin to the destination.
	 * @param vector2d
	 * @param vector2d2
	 * @return
	 */
	public static Vector2d direction2d(Vector2d origin, Vector2d destination)
	{
		Vector2d result = new Vector2d(destination.x - origin.x, 
				destination.y - origin.y).normalize();
		return result;
	}

	/**
	 * Calculates and returns the midpoint based on the two points
	 * 
	 * @param vector3d
	 * @param vector3d2
	 * @return
	 */
	public static Vector3d midpoint(Vector3d p1, Vector3d p2)
	{
		return new Vector3d((p2.x - p1.x) / 2, (p2.y - p1.y) / 2, (p2.z - p1.z) / 2);
	}
}
