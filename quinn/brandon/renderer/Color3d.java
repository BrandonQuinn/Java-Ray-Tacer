package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Vector3d;
import quinn.brandon.math.MathUtil;

public class Color3d extends Vector3d
{
	public static final Color3d BLACK = new Color3d(0.0, 0.0, 0.0);
	
	public Color3d(Color3d color)
	{
		this(color.x, color.y, color.z);
	}
	
	public Color3d(double r, double g, double b) 
	{
		x = r;
		y = g;
		z = b;
	}
	
	/**
	 * Red colour component.
	 * 
	 * @return red
	 */
	public double r()
	{
		return x;
	}
	
	/**
	 * Green colour component.
	 * 
	 * @return green
	 */
	public double g()
	{
		return y;
	}
	
	/**
	 * Blue colour component.
	 * 
	 * @return blue
	 */
	public double b()
	{
		return z;
	}
	
	/**
	 * Is this colour equal to the given one.
	 * 
	 * @param color Colour to check with
	 * @return Is equal?
	 */
	public boolean equals(Color3d color)
	{
		if (x == color.x && y == color.y && z == color.z) return true;
		return false;
	}
	
	@Override public String toString()
	{
		String string = "";
		string = "(" + x + ", " + y + ", " + z + ")";
		return string;
	}
	
	/**
	 * Multiplt the components and clamp limiting to 0 - 255.
	 * 
	 * @param vec
	 * @return
	 */
	public Color3d mul(Color3d c)
	{
		x = MathUtil.clamp(c.x * x, 0, 255);
		y = MathUtil.clamp(c.y * y, 0, 255);
		z = MathUtil.clamp(c.z * z, 0, 255);
		return this;
	}
}
