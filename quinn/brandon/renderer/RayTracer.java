package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import org.joml.Rayd;
import quinn.brandon.renderer.stats.ThreadedRenderStats;
import quinn.brandon.scene.Scene;

/**
 * A singleton which is used to send out rays in to the 
 * singleton Scene class and return their colour.
 */
public class RayTracer
{
	/**
	 * Factor to upscale the render image by for FSAA.
	 */
	private int supersamplingFactor = 1;
	
	/**
	 * Number of thread to render the image with.
	 */
	private int threadCount = 1;
	
	/**
	 * Create an instance of the ray tracer with all the parameters needed for it to know
	 * how to render the image.
	 * 
	 * @param width Width of image
	 * @param height Height of image 
	 * @param supersamplingFactor FSAA super sampling factor
	 * @param threadCount Number of thread to render with
	 */
	public RayTracer(int width, int height, int supersamplingFactor, int threadCount)
	{
		this.supersamplingFactor = supersamplingFactor;
		Scene.mainCamera = new Camera();
		Scene.mainCamera.resolutionX = width * supersamplingFactor;
		Scene.mainCamera.resolutionY = height * supersamplingFactor;
		Scene.mainCamera.projectionPlaneWidth = width;
		Scene.mainCamera.projectionPlaneHeight = height;
		this.threadCount = threadCount;
	}
	
	/**
	 * Start the ray tracer and when done return the final image.
	 * 
	 * @return Final rendered image
	 */
	public BufferedImage render()
	{
		RenderBuffer FSAAsuperImage = new RenderBuffer(Scene.mainCamera.resolutionX, Scene.mainCamera.resolutionY);
		
		// SINGLE THREADED RENDERING
		if (threadCount <= 1) {
			
			double time = System.currentTimeMillis();
			
			for (int x = 0; x < Scene.mainCamera.resolutionX; x++) {
				for (int y = 0; y < Scene.mainCamera.resolutionY; y ++) {
					Rayd ray = Scene.mainCamera.ray(x / (double) supersamplingFactor, y / (double) supersamplingFactor);
					for (Volume volume : Scene.volumes()) {
						VolumeHitData hit = volume.hit(ray);
						if (hit != null) FSAAsuperImage.setPixel(x, y, new Color3d(hit.color.r(), hit.color.g(), hit.color.b()));
					}
				}
			}
			
			System.out.println("Single Threaded on CPU");
			System.out.println("Render time: " + (System.currentTimeMillis() - time));
			
		// MULTI-THREADED RENDERING
		} else { 
			
			// schedule samples to be rendered
			ImageSampleThreadScheduler scheduler = new ImageSampleThreadScheduler(threadCount, FSAAsuperImage);
			int threadImageSampleSizeW = Scene.mainCamera.resolutionX / threadCount;
			int threadImageSampleSizeH = Scene.mainCamera.resolutionY / threadCount;
			for (int x = 0; x < Scene.mainCamera.resolutionX; x += threadImageSampleSizeW) {
				for (int y = 0; y < Scene.mainCamera.resolutionY; y += threadImageSampleSizeH) {
					try {
						// schedule the sample to render
						ImageSample newSample = new ImageSample(x, y, threadImageSampleSizeW, threadImageSampleSizeH, supersamplingFactor);
						scheduler.scheduleSample(newSample);
					} catch (IllegalStateException e) {
						JOptionPane.showMessageDialog(null, "Too many samples are being computed.\nIncrease THREAD_IMAGE_SAMPLE_SIZE", 
								"Too many samples.", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			ThreadedRenderStats stats = scheduler.renderAll();
			System.out.println("Multithreading (" + threadCount + " x Threads [CPU])");
			System.out.println("Render time: "  + stats.renderTime + "ms");
			
		}
		
		// only down sample if the factor is not 1
		if (supersamplingFactor != 1) {
			RenderBuffer downSampledImage = FSAAsuperImage.downSample(supersamplingFactor);
			return downSampledImage.image();
		}
		
		return FSAAsuperImage.image();
	}
}
