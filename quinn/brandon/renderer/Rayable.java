package quinn.brandon.renderer;

import org.joml.Rayd;

public interface Rayable
{
	/**
	 * Check if the ray hits the object and return the point at which it was hit.
	 * 
	 * @param ray The ray to check if it hits the volume
	 * @return The colour surface hit
	 */
	public abstract HitData hit(Rayd ray);
}
