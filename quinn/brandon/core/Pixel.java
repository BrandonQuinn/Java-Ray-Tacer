package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

public class Pixel
{
	double r = 0;
	double g = 0;
	double b = 0;
	
	public Pixel(double r, double g, double b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Pixel()
	{
		
	}
	
	public void setColor(Color3d color)
	{
		r = color.r();
		g = color.g();
		b = color.b();
	}
}
