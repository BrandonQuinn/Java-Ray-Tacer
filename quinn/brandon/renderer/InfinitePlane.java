package quinn.brandon.renderer;

import java.util.ArrayList;
import org.joml.Intersectiond;
import org.joml.Planed;
import org.joml.Rayd;
import org.joml.Vector3d;
import quinn.brandon.math.MathUtil;
import quinn.brandon.scene.Scene;

public class InfinitePlane extends Volume
{
	public Vector3d direction = new Vector3d(0.0, 1.0, 0.0);
	
	@Override
	public Color3d hit(Rayd ray)
	{
		Planed plane = new Planed(location, direction);
		double intersection = Intersectiond.intersectRayPlane(ray, plane, 0.01);
		if (intersection != -1.0) {
			Vector3d hitLocation = new Vector3d(ray.dX, ray.dY, ray.dZ).mul(intersection).add(new Vector3d(ray.oX, ray.oY, ray.oZ));
			Color3d result = new Color3d(color.x, color.y, color.z);
			
			// go through each light and get the intensity
			ArrayList<Color3d> lightIntensities = new ArrayList<Color3d>();
			for (Light light: Scene.lights()) {
				Color3d C = light.hit(hitLocation);
				if (C != null) lightIntensities.add(C);
			}
			
			// get the lights
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
			return result;
		}
		
		return null;
	}

}
