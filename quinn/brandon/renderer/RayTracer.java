package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.image.BufferedImage;
import org.joml.Rayd;
import quinn.brandon.scene.Scene;

/**
 * A singleton which is used to send out rays in to the 
 * singleton Scene class and return their colour.
 */
public class RayTracer
{
	private RenderBuffer image;
	private Camera camera;
	private int supersamplingFactor;
	
	public RayTracer(int width, int height, int supersamplingFactor)
	{
		this.supersamplingFactor = supersamplingFactor;
		camera = new Camera();
		camera.width = width;
		camera.height = height;
	}
	
	/**
	 * Start the ray tracer and when done return the final image.
	 * 
	 * @return
	 */
	public BufferedImage render()
	{
		image = new RenderBuffer(camera.width * supersamplingFactor, camera.height * supersamplingFactor);
		
		for (int x = 0; x < camera.width * supersamplingFactor; x++) {
			for (int y = 0; y < camera.height * supersamplingFactor; y++) {
				Rayd ray = camera.ray(x / (double) supersamplingFactor, y / (double) supersamplingFactor);
				for (Volume volume : Scene.volumes()) {
					Color3d color = volume.hit(ray);
					if (color != null) image.setPixel(x, y, new Color3d(color.r(), color.g(), color.b()));
				}
			}
		}
		
		// only down sample if the factor is not 1
		if (supersamplingFactor != 1) {
			RenderBuffer downSampledImage = image.downSample(supersamplingFactor);
			return downSampledImage.image();
		}
		
		return image.image();
	}
}
