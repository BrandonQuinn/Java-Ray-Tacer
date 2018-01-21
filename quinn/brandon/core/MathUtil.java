package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under MIT.
 ***************************************************************************************/

public class MathUtil
{
	public static double clamp(double v, double min, double max)
	{
		return Math.max(min, Math.min(max, v));
	}
}
