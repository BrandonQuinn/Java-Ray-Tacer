package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under MIT.
 ***************************************************************************************/

import java.awt.image.BufferedImage;
import org.joml.Rayd;

/**
 * A singleton which is used to send out rays in to the 
 * singleton Scene class and return their colour.
 */
public class RayTracer
{
	public static float MAX_DRAW_DEPTH = 1.0f;
	public static float MIN_DRAW_DEPTH = 0.0f;
	
	private RenderBuffer image;
	private Camera camera;
	
	public RayTracer(int width, int height)
	{
		camera = new Camera();
		camera.width = width;
		camera.height = height;
	}
	
	/**
	 * Start the ray tracer and when done return the final image.
	 * 
	 * @return
	 */
	public BufferedImage start()
	{
		image = new RenderBuffer(camera.width, camera.height);
		
		for (int x = 0; x < camera.width; x++) {
			for (int y = 0; y < camera.height; y++) {
				Rayd ray = camera.ray(x, y);
				for (Volume volume : Scene.volumes()) {
					Color3d color = volume.hit(ray);
					if (color != null) image.setPixel(x, y, new Pixel(color.r(), color.g(), color.b()));
				}
			}
		}
		
		return image.image();
	}
}
