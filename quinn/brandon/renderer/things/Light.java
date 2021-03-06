/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

package quinn.brandon.renderer.things;

import org.joml.Vector3d;
import quinn.brandon.renderer.Color3d;

public abstract class Light
{
	public double intensity = 0.1f;
	public Color3d color = new Color3d(255, 255, 255);
	public Vector3d location = new Vector3d();
	
	/**
	 * Get the intensity of the light and colour based on
	 * the inverse square law.
	 * 
	 * @param start The point to draw a line from to the light
	 * @return Intensity of the light at the start point
	 */
	public abstract Color3d hit(Vector3d start);
}
