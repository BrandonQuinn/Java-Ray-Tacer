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
import quinn.brandon.core.math.MathUtil;
import quinn.brandon.renderer.Color3d;
import quinn.brandon.renderer.RayHitOutput;

public class Face extends Volume
{
	public Color3d color = new Color3d(200, 200, 200);
	public Vector3d normal;
	public ArrayList<Vector3d> verticies = new ArrayList<Vector3d>();

	@Override public RayHitOutput hit(Rayd ray)
	{
		RayHitOutput hit = new RayHitOutput();
		
		// fun story...
		// I tried doing the method where you change everything to 2D after creating a plane
		// and checking where it hits... then I drew a ray from the hit point through each
		// edge and check how many the ray hits, if it's odd then it's inside otherwise not so.
		// This failed spectacularly and made things look very strange
		// sooo....
		// let's go with the less feature rich option of assuming that the face is convex 
		// we'll do a fan-like triangulation by selecting 1 vertex and drawing lines to all other 
		// verticies and we can use the JOML library to handle the math for us.
		// I like it when I don't have to do the math, but I also really want to know how to do the
		// math. Oh well...
		
		Vector3d vertex1 = verticies.get(0);
		for (int i = 0; i < verticies.size() - 1; i++) {

			double distance = Intersectiond.intersectRayTriangle(
					new Vector3d(ray.oX, ray.oY, ray.oZ),
					new Vector3d(ray.dX, ray.dY, ray.dZ),
					vertex1,
					verticies.get(i),
					verticies.get(i + 1),
					0.00001);

			if (distance != -1.0) {
				hit.location = new Vector3d(MathUtil.pointAlongRay(ray, distance));
				hit.distanceFromOrigin = distance;
				Color3d lightIntensity = SceneLighting.intensityAt(hit.location);
				hit.color = lightIntensity.mul(color);
				return hit;
			}
		}
		
		return null;
	}
}
