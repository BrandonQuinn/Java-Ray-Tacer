package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Vector3d;

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
	
	public double r()
	{
		return x;
	}
	
	public double g()
	{
		return y;
	}
	
	public double b()
	{
		return z;
	}
	
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
}
