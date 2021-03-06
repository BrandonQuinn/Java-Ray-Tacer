/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

package quinn.brandon.renderer.things;

import java.util.ArrayList;
import org.joml.Vector3d;
import quinn.brandon.renderer.Color3d;
import quinn.brandon.scene.Scene;

public class SceneLighting
{
	/**
	 * Ambient lighting applied to all lighting calculations.
	 * Gives the scene a minimum amount of light, even for shadows.
	 */
	public static Color3d ambient = new Color3d(10, 10, 10);
	
	/**
	 * Goes through every light in the scene and calculates the intensity of all
	 * of them together at the specified location.
	 * 
	 * @param location
	 * @return
	 */
	public static synchronized Color3d intensityAt(Vector3d location)
	{
		ArrayList<Color3d> lightIntensities = new ArrayList<Color3d>();
		for (Light light: Scene.lights()) {
			Color3d C = light.hit(location);
			if (C != null) lightIntensities.add(C);
		}
		
		if (lightIntensities.isEmpty()) return null;
		
		// add all light intensities
		Color3d addedIntensities = new Color3d(0, 0, 0);
		for (int l = 0; l < lightIntensities.size(); l++) {
			addedIntensities.x += lightIntensities.get(l).x;
			addedIntensities.y += lightIntensities.get(l).y;
			addedIntensities.z += lightIntensities.get(l).z;
		}
		
		return addedIntensities;
	}
}
