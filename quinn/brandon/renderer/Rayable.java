/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

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
	public abstract RayHitOutput hit(Rayd ray);
}
