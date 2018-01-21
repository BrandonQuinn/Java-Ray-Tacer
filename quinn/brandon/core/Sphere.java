package quinn.brandon.core;

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

public class Sphere extends Volume
{
	private double radius = 5.0;
	
	public Sphere()
	{
		
	}
	
	public Sphere(double radius, Vector3d position)
	{
		super(position); // no no, just 2 positions in this domain
		this.radius = radius;
	}
	
	public Sphere(double radius, double x, double y, double z)
	{
		super(x, y, z);
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
	

	@Override
	public Color3d hit(Rayd ray)
	{
		Vector2d hitDists = new Vector2d();
		
		// forced to possibly lose some precision because the JOML library does not provide a
		// Intersectiond.intersectRaySphere that takes in a Sphered
		Spheref sphere = new Spheref((float) location().x, 
				(float) location().y, 
				(float) location().z, (float) radius);
		
		if (Intersectiond.intersectRaySphere(ray, sphere, hitDists))
		{
			// get the point at which the sphere was hit
			double closest = Math.min(hitDists.x, hitDists.y);
			Vector3d hitPoint = new Vector3d(ray.dX, ray.dY, ray.dZ);
			hitPoint.mul(closest);
			hitPoint.add(new Vector3d(ray.oX, ray.oY, ray.oZ));
			
			System.out.println("Hit point: (" + hitPoint.x + ", " + hitPoint.y + ", " + hitPoint.z + ")");
	
			// go through each light and get the intensity
			for (Light light: Scene.lights()) {
				Color3d C = light.hit(hitPoint);
				if (C != null) {
					Color3d result = new Color3d(MathUtil.clamp(C.x * color().r(), 0, 255), 
							MathUtil.clamp(C.y * color().g(), 0, 255), 
							MathUtil.clamp(C.z * color().b(), 0, 255));
					return result;
				}
			}
		}
		
		return null;
	}
}
