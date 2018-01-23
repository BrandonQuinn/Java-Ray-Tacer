package quinn.brandon.math;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

public class MathUtil
{
	
	/**
	 * Limit the size of the given value.
	 * 
	 * @param v Value
	 * @param min Minimum
	 * @param max Maximum
	 * @return The clamped result.
	 */
	public static double clamp(double v, double min, double max)
	{
		return Math.max(min, Math.min(max, v));
	}
	
	/**
	 * Limit the size of the given value.
	 * 
	 * @param v Value
	 * @param min Minimum
	 * @param max Maximum
	 * @return The clamped result.
	 */
	public static int clamp(int v, int min, int max)
	{
		return Math.max(min, Math.min(max, v));
	}
}
