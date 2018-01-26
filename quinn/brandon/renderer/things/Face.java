package quinn.brandon.renderer.things;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;
import org.joml.Intersectiond;
import org.joml.Rayd;
import org.joml.Vector3d;
import quinn.brandon.math.MathUtil;
import quinn.brandon.renderer.Color3d;
import quinn.brandon.renderer.HitData;
import quinn.brandon.scene.Scene;

public class Face extends Volume
{
	public Color3d color = new Color3d(200, 200, 200);
	public Vector3d normal;
	public ArrayList<Vector3d> verticies = new ArrayList<Vector3d>();

	@Override public HitData hit(Rayd ray)
	{
		HitData hit = new HitData();
		
		// fun story...
		// I tried doing the method where you change everything to 2D after creating a plane
		// and checking where it hits... then I drew a ray from the hit point through each
		// edge and check how many the ray hits, if it's odd then it's inside otherwise not so.
		// This failed spectacularly and made things look very strange/
		// sooo....
		// let's go with the less feature rich option of assuming that the face is convex 
		// we'll do a fan-like triangulation by selecting 1 vertex and drawing lines to all other 
		// verticies and we can use the JOML library to handle the math for us.
		// I like it when I don't have to do the math, but I also really want to know how to do the
		// math. Oh well...
		
		Vector3d vertex1 = verticies.get(0);
		for (int i = 0; i < verticies.size() - 2; i++) {
			Vector3d vertex2 = new Vector3d(verticies.get(i + 1));
			Vector3d vertex3 = new Vector3d(verticies.get(i + 2));
			
			double distance = Intersectiond.intersectRayTriangle(
					new Vector3d(ray.oX, ray.oY, ray.oZ),
					new Vector3d(ray.dX, ray.dY, ray.dZ), 
					vertex1,
					vertex2,
					vertex3,
					.000000000001);
			
			if (distance != -1.0) {
				hit.location = new Vector3d(MathUtil.pointAlongRay(ray, distance));
				hit.distanceFromOrigin = distance;
				
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
		}
		
		return null;
	}
}
