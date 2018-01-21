package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Rayd;
import org.joml.Vector3d;

public class Camera
{
	/**
	 * Enable or disable straight rays.
	 * Turning this on results in rays going straight out from each pixel.
	 * If it's off then rays start at an origin point and spread out across the view frustum.
	 */
	public static boolean isOrthographic = true;
	public int width = 100;
	public int height = 100;
	public int resolutionX = 100;
	public int resolutionY = 100;
	public double windowDistance = 100.0;
	public Vector3d origin = new Vector3d(width / 2.0, height / 2.0, -windowDistance);
	private Vector3d rayDest = new Vector3d(0, 0, 0);
	private Vector3d rayDir = new Vector3d(0, 0, 0);
	
	/**
	 * Create a ray from the cameras eye position throw the pixel on the screen given.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Rayd ray(double x, double y)
	{
		if (isOrthographic) {
			origin.x = x;
			origin.y = y;
		} else {
			origin.x = (width / 2.0);
			origin.y = (height / 2.0);
		}
		origin.z = -windowDistance;
		
		rayDest.x = x; rayDest.y = y; rayDest.z = windowDistance;
		rayDir.x = rayDest.x - origin.x; rayDir.y = rayDest.y - origin.y; rayDir.z = rayDest.z - origin.z;
		rayDir.normalize();
		return new Rayd(origin, rayDir);
	}
}
