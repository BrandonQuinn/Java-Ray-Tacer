package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Rayd;
import org.joml.Vector3d;

public abstract class Volume
{
	public Vector3d location = new Vector3d();
	public Color3d color = new Color3d(10.0, 10.0, 10.0);
	
	public Volume()
	{
		
	}
	
	public Volume(Vector3d position)
	{
		this.location = position;
	}
	
	public Volume(double x, double y, double z)
	{
		setPosition(x, y, z);
	}
	
	/**
	 * Check if the ray hits the object and return the point at which it was hit.
	 * 
	 * @param ray
	 * @return
	 */
	public abstract Color3d hit(Rayd ray);
	
	/**
	 * Set the location based on individual dimensions.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(double x, double y, double z)
	{
		location.x = x;
		location.y = y;
		location.z = z;
	}
}
