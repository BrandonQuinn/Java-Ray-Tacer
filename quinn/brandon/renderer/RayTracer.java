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
	private Camera camera;
	private int supersamplingFactor;
	
	public RayTracer(int width, int height, int supersamplingFactor)
	{
		this.supersamplingFactor = supersamplingFactor;
		camera = new Camera();
		camera.resolutionX = width * supersamplingFactor;
		camera.resolutionY = height * supersamplingFactor;
		camera.projectionPlaneWidth = width;
		camera.projectionPlaneHeight = height;
	}
	
	/**
	 * Start the ray tracer and when done return the final image.
	 * 
	 * @return
	 */
	public BufferedImage render()
	{
		RenderBuffer FSAAsuperImage = new RenderBuffer(camera.resolutionX, camera.resolutionY);
		
		for (int x = 0; x < camera.resolutionX; x++) {
			for (int y = 0; y < camera.resolutionY; y++) {
				Rayd ray = camera.ray(x / (double) supersamplingFactor, y / (double) supersamplingFactor);
				for (Volume volume : Scene.volumes()) {
					Color3d color = volume.hit(ray);
					if (color != null) FSAAsuperImage.setPixel(x, y, new Color3d(color.r(), color.g(), color.b()));
				}
			}
		}
		
		// only down sample if the factor is not 1
		if (supersamplingFactor != 1) {
			RenderBuffer downSampledImage = FSAAsuperImage.downSample(supersamplingFactor);
			return downSampledImage.image();
		}
		
		return FSAAsuperImage.image();
	}
}
