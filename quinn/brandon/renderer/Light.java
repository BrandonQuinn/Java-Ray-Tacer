package quinn.brandon.renderer;

import org.joml.Vector3d;

public abstract class Light
{
	public double intensity = 10.0f;
	public Color3d color = new Color3d(255, 255, 255);
	public Vector3d location = new Vector3d();
	
	/**
	 * Get the intensity of the light and colour based on
	 * the inverse square law.
	 * 
	 * @param start
	 * @return
	 */
	public abstract Color3d hit(Vector3d start);
}
