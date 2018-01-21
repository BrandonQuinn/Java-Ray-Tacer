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
	public static boolean isOrthographic = false;
	public int width = 100;
	public int height = 100;
	public double windowDistance = 100.0;
	public Vector3d origin = new Vector3d(width / 2.0, height / 2.0, -windowDistance);
	
	public Rayd ray(int x, int y)
	{
		if (isOrthographic) {
			origin.x = x;
			origin.y = y;
		} else {
			origin.x = (width / 2.0);
			origin.y = (height / 2.0);
		}
		
		origin.z = -windowDistance;
		
		double destx = x;
		double desty = y;
		Vector3d destination = new Vector3d(destx, desty, windowDistance);
		Vector3d dir = new Vector3d(destination.x - origin.x, destination.y - origin.y, destination.z - origin.z);
		dir.normalize();
		return new Rayd(origin, dir);
	}
}
