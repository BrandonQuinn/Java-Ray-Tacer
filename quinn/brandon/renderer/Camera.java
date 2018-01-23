package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 22 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Rayd;
import org.joml.Vector3d;

public class Camera
{
	/*
	 * The size of the project plane in world coordinates. 
	 */
	public double projectionPlaneWidth = 100;
	public double projectionPlaneHeight = 100;
	
	/*
	 * The number of rays to send out in the project plane.
	 */
	public int resolutionX = 100;
	public int resolutionY = 100;
	
	/*
	 * How far the project plane is from the origin.
	 */
	public double projectionPlaneDistance = 100.0;
	
	/**
	 * Far plane distance.
	 */
	public double farPlaneDistance = 1000000.0;
	
	/*
	 * Where rays are shot out from towards the projection plane,
	 * unless it is rendering in orthographic mode, then rays are sent straight out.
	 */
	public Vector3d origin = new Vector3d(projectionPlaneWidth / 2.0, projectionPlaneHeight / 2.0, -projectionPlaneDistance);
	
	/**
	 * The direction of the entire camera, so which way to shoot a ray from the origin and the normal
	 * vector of the projection plane.
	 */
	public Vector3d direction = new Vector3d(0.0, 0.0, 1.0);
	
	/*
	 * The projection plane as a rectangle in space.
	 */
	// private Planed nearPlane = new Planed(new Vector3d(origin.x, origin.y, 0.0), direction);
	
	/*
	 * Enabled/disable orthographic view. Orthographic meaning rays shoot not from the
	 * origin but rather directly out from the pixel.
	 */
	public boolean orthographic = true;
	
	private Vector3d rayDirection = new Vector3d(0.0, 0.0, 0.0);
	private Vector3d rayDestination = new Vector3d(0.0, 0.0, 0.0);
	
	
	/**
	 * Cast a ray out from the origin of the camera or directory straight out from behind
	 * towards to given x, y coordinate on the near plane.
	 * 
	 * @param x x position on the perspective plane
	 * @param y y position on the perspective plane
	 * @return The ray
	 */
	public Rayd ray(double x, double y)
	{
		Rayd ray = new Rayd();
		
		// origin/eye
		if (orthographic) {
			ray.oX = x;
			ray.oY = y;
			ray.oZ = -projectionPlaneDistance;
			
			rayDirection.x = 0.0;
			rayDirection.y = 0.0;
			rayDirection.z = 1.0;
		} else {
			ray.oX = origin.x;
			ray.oY = origin.y;
			ray.oZ = -projectionPlaneDistance;
			
			// destination on the near plane
			rayDestination.x = x;
			rayDestination.y = y;
			rayDestination.z = 0.0;
			
			// move destination to origin, subtraction the destination
			rayDirection.set(rayDestination).sub(origin).normalize();
		}
		
		// set the ray direction
		ray.dX = rayDirection.x;
		ray.dY = rayDirection.y;
		ray.dZ = rayDirection.z;
		
		return ray;
	}
	
	/**
	 * This is a temporary method to convert a world space coordinate
	 * on to the projection plane using my clunky, naive approach.
	 * 
	 * @param point
	 * @return
	 */
	public Vector3d projectPoint(Vector3d point)
	{
		Vector3d result = new Vector3d(point.x, point.y, point.z);
		double far = Double.POSITIVE_INFINITY;
		double near = projectionPlaneDistance;
		
		// TODO: laterz
		return result;
	}
}
