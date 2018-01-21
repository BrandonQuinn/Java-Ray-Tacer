package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under MIT.
 ***************************************************************************************/

import org.joml.Intersectiond;
import org.joml.Vector3d;

public class PointLight extends Light
{
	public double constantAttenuation = 1.0f;
	public double linearAttenuation = 1.0f;
	public double quadraticAttenuation = 1.0f;
	
	@Override
	public Color3d hit(Vector3d start)
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
