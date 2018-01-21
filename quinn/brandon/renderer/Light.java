package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.Color;
import org.joml.Vector3d;

public abstract class Light
{
	public double intensity = 10.0f;
	public Color color = new Color(255, 255, 255);
	public Vector3d location = new Vector3d();
	
	/**
	 * Get the intensity of the light and colour based on
	 * the inverse square law.
	 * 
	 * @param start
	 * @return
	 */
	public abstract Color3d hit(Vector3d start);
}
