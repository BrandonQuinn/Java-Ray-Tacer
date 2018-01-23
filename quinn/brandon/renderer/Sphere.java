package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 22 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;

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
import quinn.brandon.math.MathUtil;
import quinn.brandon.scene.Scene;

public class Sphere extends Volume
{
	private double radius = 5.0;
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
	 * Set the radius of the sphere.
	 * 
	 * @param radius
	 */
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	/**
	 * Return the radius of the sphere.
	 * 
	 * @return
	 */
	public double radius()
	{
		return radius;
	}
	
	/**
	 * Returns null if the ray doesn't hit any volumes in the world.
	 * If it does hit an object it will then get the location and check lighting and
	 * run extra rays out in to the world for reflections, lighting, caustics etc.
	 * 
	 * @param ray The ray shooting out from which ever pixel on the screen view.
	 * @return The colour corresponding the where the ray hit on the sphere.
	 */
	@Override public HitData hit(Rayd ray)
	{
		HitData hit = new HitData();
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
			
			// go through each light and get the intensity
			ArrayList<Color3d> lightIntensities = new ArrayList<Color3d>();
			for (Light light: Scene.lights()) {
				Color3d C = light.hit(hit.location);
				if (C != null) lightIntensities.add(C);
			}
			
			if (lightIntensities.isEmpty()) return null;
			
			// multiply all light intensities
			Color3d result = new Color3d(color.r(), color.g(), color.b());
			Color3d addedIntensities = new Color3d(0, 0, 0);
			for (int l = 0; l < lightIntensities.size(); l++) {
				addedIntensities.x += lightIntensities.get(l).x;
				addedIntensities.y += lightIntensities.get(l).y;
				addedIntensities.z += lightIntensities.get(l).z;
			}
			
			result.mul(new Vector3d(addedIntensities.x, addedIntensities.y, addedIntensities.z));
			result.x = MathUtil.clamp(result.x, 0, 255);
			result.y = MathUtil.clamp(result.y, 0, 255);
			result.z = MathUtil.clamp(result.z, 0, 255);

			hit.color = result;
			return hit;
		}
		
		return null;
	}
}
