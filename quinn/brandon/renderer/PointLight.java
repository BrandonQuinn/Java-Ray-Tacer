package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Intersectiond;
import org.joml.Vector3d;
import quinn.brandon.scene.Scene;

public class PointLight extends Light
{
	public double constantAttenuation = 1.0f;
	public double linearAttenuation = 1.0f;
	public double quadraticAttenuation = 1.0f;
	
	/**
	 * Create a line from the start position given and the location of the light
	 * and check the distance. Use the inverse of the square of the distance to calculate
	 * the intensity of the light.
	 * 
	 * @return Return the intensity for each r, g, b value as a Color3d {@see #Color3d}
	 */
	@Override public Color3d hit(Vector3d start)
	{
		for (Volume volume : Scene.volumes()) {
			if (volume instanceof Sphere && !Intersectiond.testLineSegmentSphere(start, location, volume.location(), ((Sphere) volume).radius())) {
				double distance = start.distance(location);
				double intensity = (1.0 / (distance * distance));
				Vector3d c = new Vector3d(color.getRed(), color.getGreen(), color.getBlue());
				c = c.mul(intensity);
				return new Color3d(c.x, c.y, c.z);
			}
		}
		
		return null;
	}
}
