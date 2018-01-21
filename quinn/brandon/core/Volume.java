package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under MIT.
 ***************************************************************************************/

import org.joml.Rayd;
import org.joml.Vector3d;

public abstract class Volume
{
	private Vector3d position = new Vector3d();
	private Color3d color = new Color3d(10.0, 10.0, 10.0);
	
	public Volume()
	{
		
	}
	
	public Volume(Vector3d position)
	{
		setPosition(position);
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
	 * Set the position to another position vector.
	 * 
	 * @param position
	 */
	public void setPosition(Vector3d position) 
	{
		this.position = position;
	}
	
	/**
	 * Set the position based on individual dimensions.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(double x, double y, double z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	/**
	 * Return the position vector.
	 * 
	 * @return
	 */
	public Vector3d location()
	{
		return position;
	}
	
	/**
	 * Set the colour of the volume.
	 * 
	 * @param color
	 */
	public void setColor(Color3d color)
	{
		this.color = color;
	}
	
	/**
	 * Return the colour of the volume.
	 * 
	 * @return
	 */
	public Color3d color()
	{
		return color;
	}
}
