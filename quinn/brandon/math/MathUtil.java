package quinn.brandon.math;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

public class MathUtil
{
	public static double clamp(double v, double min, double max)
	{
		return Math.max(min, Math.min(max, v));
	}
}
