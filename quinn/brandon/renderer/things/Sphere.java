package quinn.brandon.renderer.things;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Intersectiond;
import org.joml.Rayd;
import org.joml.Spheref;
import org.joml.Vector2d;
import org.joml.Vector3d;
import quinn.brandon.renderer.Color3d;
import quinn.brandon.renderer.RayHitOutput;

public class Sphere extends Volume
{
	public double radius = 5.0;
	public Vector3d location = new Vector3d();
	
	public Sphere()
	{
		
	}
	
	public Sphere(double radius, Vector3d position)
	{
		location = position;
		this.radius = radius;
	}
	
	public Sphere(double radius, double x, double y, double z)
	{
		location.x = x; location.y = y; location.z = z;
		this.radius = radius;
	}
	
	/**
	 * Returns null if the ray doesn't hit any volumes in the world.
	 * If it does hit an object it will then get the location and check lighting and
	 * run extra rays out in to the world for reflections, lighting, caustics etc.
	 * 
	 * @param ray The ray shooting out from which ever pixel on the screen view.
	 * @return The colour corresponding the where the ray hit on the sphere.
	 */
	@Override public RayHitOutput hit(Rayd ray)
	{
		RayHitOutput hit = new RayHitOutput();
		Vector2d hitDists = new Vector2d();
		
		// forced to possibly lose some precision because the JOML library does not provide a
		// Intersectiond.intersectRaySphere that takes in a Sphered
		Spheref sphere = new Spheref(
				(float) location.x, 
				(float) location.y, 
				(float) location.z, 
				(float) radius);
		
		if (Intersectiond.intersectRaySphere(ray, sphere, hitDists))
		{
			// get the point at which the sphere was hit
			double closest = Math.min(hitDists.x, hitDists.y);
			Vector3d hitPoint = new Vector3d(ray.dX, ray.dY, ray.dZ);
			hitPoint.mul(closest);
			hitPoint.add(new Vector3d(ray.oX, ray.oY, ray.oZ));
			hit.location = hitPoint;
			hit.distanceFromOrigin = hit.location.distance(new Vector3d(ray.oX, ray.oY, ray.oZ));
			
			Color3d lightIntensity = SceneLighting.intensityAt(hit.location);
			hit.color = lightIntensity.mul(surface.color);
			return hit;
		}
		
		return null;
	}
}
