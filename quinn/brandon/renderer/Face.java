package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;
import org.joml.Intersectiond;
import org.joml.LineSegmentd;
import org.joml.Planed;
import org.joml.Rayd;
import org.joml.Vector2d;
import org.joml.Vector3d;
import quinn.brandon.math.MathUtil;
import quinn.brandon.scene.Scene;

public class Face extends Volume
{
	public Color3d color = new Color3d(0, 0, 0);
	public Vector3d normal;
	public ArrayList<Vector3d> verticies = new ArrayList<Vector3d>();
	
	@Override public HitData hit(Rayd ray)
	{
		// find the plane
		Planed plane = new Planed(verticies.get(0), normal);
		double distance = Intersectiond.intersectRayPlane(ray, plane, 0.000001);
		Vector3d hitPoint = MathUtil.pointAlongRay(ray, distance);
		
		HitData hit = new HitData();
		hit.distanceFromOrigin = distance;
		hit.location = hitPoint;
		
		// go around each line segment (edge) made up of two verticies and shoot a ray from it's midpoint
		// to the hit point, then check if that ray hits any other edges and keep track of how many.
		// even means it's outside, odd means inside, 0 is counted as odd
		for (int i = 0; i < verticies.size() - 1; i++) {
			LineSegmentd segment = new LineSegmentd(verticies.get(i), verticies.get(i + 1));
			Vector3d mp = new Vector3d((segment.bX - segment.aX) / 2, (segment.bY - segment.aY) / 2, (segment.bZ - segment.aZ) / 2);
			Rayd rayToHit = new Rayd(hitPoint, MathUtil.direction(hitPoint, mp));
			
			// we need to remove one of the dimensions either x, y or z which ever one has the largest direction
			// value for the normal of the polygon
			int removeDim = 0;
			for (int d = 0; d < 3; d++)
				if (Math.abs(normal.get(d)) > Math.abs(normal.get(removeDim))) removeDim = d;
			
			Vector2d p1 = new Vector2d();
			Vector2d p2 = new Vector2d();
			Vector2d ray2d = new Vector2d();
			Vector2d origin2d = new Vector2d();
			
			// remove respective dimension that had the highest normal value.
			// this prevents some strange atifacting when the polygon gets perpendicular to the view.
			if (removeDim == 0) {
				ray2d.x = rayToHit.dY; origin2d.x = rayToHit.oY;
				ray2d.y = rayToHit.dZ; origin2d.y = rayToHit.oZ;
			} else if (removeDim == 1) {
				ray2d.x = rayToHit.dX; origin2d.x = rayToHit.oX;
				ray2d.y = rayToHit.dZ; origin2d.y = rayToHit.oZ;
			} else {
				ray2d.x = rayToHit.dX; origin2d.x = rayToHit.oX;
				ray2d.y = rayToHit.dY; origin2d.y = rayToHit.oY;
			}
			
			int intersectCount = 0;
			// loop through each other line segment and check if the ray intersects them and how many
			for (int t = 0; t < verticies.size(); t++) {
				LineSegmentd segment2 = new LineSegmentd(verticies.get(i), verticies.get(i + 1));
				
				// remove respective dimension that had the highest normal value.
				// this prevents some strange atifacting when the polygon gets perpendicular to the view.
				if (removeDim == 0) {
					p1.x = segment2.aY; p2.x = segment2.bY;
					p1.y = segment2.aZ; p2.y = segment2.bZ;
				} else if (removeDim == 1) {
					p1.x = segment2.aX; p2.x = segment2.bX;
					p1.y = segment2.aZ; p2.y = segment2.bZ;
				} else {
					p1.x = segment2.aX; p2.x = segment2.bX;
					p1.y = segment2.aY; p2.y = segment2.bY;
				}
				
				if (Intersectiond.intersectRayLineSegment(origin2d, ray2d, p1, p2) != -1.0)
					intersectCount++;
			}
			
			// it's inside
			if ((intersectCount == 0 || intersectCount % 2 != 0) && distance != -1.0) {
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
