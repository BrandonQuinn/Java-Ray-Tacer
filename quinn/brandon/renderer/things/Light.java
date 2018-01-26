package quinn.brandon.renderer.things;

import org.joml.Vector3d;
import quinn.brandon.renderer.Color3d;

public abstract class Light
{
	public double intensity = 10.0f;
	public Color3d color = new Color3d(255, 255, 255);
	public Vector3d location = new Vector3d();
	
	/**
	 * Get the intensity of the light and colour based on
	 * the inverse square law.
	 * 
	 * @param start The point to draw a line from to the light
	 * @return Intensity of the light at the start point
	 */
	public abstract Color3d hit(Vector3d start);
}
