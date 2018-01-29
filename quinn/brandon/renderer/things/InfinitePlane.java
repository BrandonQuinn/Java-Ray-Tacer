package quinn.brandon.renderer.things;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Intersectiond;
import org.joml.Planed;
import org.joml.Rayd;
import org.joml.Vector3d;
import quinn.brandon.renderer.Color3d;
import quinn.brandon.renderer.RayHitOutput;

public class InfinitePlane extends Volume
{
	public Vector3d direction = new Vector3d(0.0, 1.0, 0.0);
	public Vector3d location = new Vector3d(0, 0, 0);
	
	@Override public RayHitOutput hit(Rayd ray)
	{
		RayHitOutput hit = new RayHitOutput();
		Planed plane = new Planed(location, direction);
		double intersection = Intersectiond.intersectRayPlane(ray, plane, 0.000001);
		
		if (intersection != -1.0) {
			hit.location = new Vector3d(ray.dX, ray.dY, ray.dZ).mul(intersection).add(new Vector3d(ray.oX, ray.oY, ray.oZ));
			hit.distanceFromOrigin = hit.location.distance(new Vector3d(ray.oX, ray.oY, ray.oZ));
			Color3d lightIntensity = Lighting.intensityAt(hit.location);
			hit.color = lightIntensity.mul(color);
			hit.color = lightIntensity;
			return hit;
		}
		
		return null;
	}

}
