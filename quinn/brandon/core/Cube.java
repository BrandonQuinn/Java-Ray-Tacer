package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Rayd;

public abstract class Cube extends Volume
{
	private double size = 5.0;
	
	public void setSize(double size)
	{
		this.size = size;
	}
	
	public double size()
	{
		return size;
	}

	@Override
	public abstract Color3d hit(Rayd ray);
}
