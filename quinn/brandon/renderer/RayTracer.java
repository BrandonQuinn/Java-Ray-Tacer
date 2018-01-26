package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import quinn.brandon.math.MathUtil;
import quinn.brandon.renderer.stats.ThreadedRenderStats;
import quinn.brandon.scene.Scene;

/**
 * A singleton which is used to send out rays in to the 
 * singleton Scene class and return their colour.
 */
public class RayTracer
{
	/**
	 * Sample size.
	 */
	private static int SAMPLE_SIZE = 150;
	
	/**
	 * Factor to upscale the render image by for FSAA.
	 */
	private int FSAA = 1;
	
	/**
	 * Number of thread to render the image with.
	 */
	private int threadCount = 1;
	
	/**
	 * The image.
	 */
	private RenderBuffer image;
	
	/**
	 * Thread schduler.
	 */
	private ImageSampleThreadScheduler scheduler;
	
	/**
	 * Create an instance of the ray tracer with all the parameters needed for it to know
	 * how to render the image.
	 * 
	 * @param width Width of image
	 * @param height Height of image 
	 * @param FSAA FSAA super sampling factor
	 * @param threadCount Number of thread to render with
	 */
	public RayTracer(int width, int height, int FSAA, int threadCount)
	{
		this.FSAA = MathUtil.clamp(FSAA % 2 == 0 ? FSAA : FSAA - 1, 1, 32);
		Scene.mainCamera = new Camera();
		Scene.mainCamera.resolutionX = width * FSAA;
		Scene.mainCamera.resolutionY = height * FSAA;
		Scene.mainCamera.projectionPlaneWidth = width;
		Scene.mainCamera.projectionPlaneHeight = height;
		this.threadCount = threadCount;
		image = new RenderBuffer((int) Scene.mainCamera.projectionPlaneWidth, (int) Scene.mainCamera.projectionPlaneHeight);
	}
	
	/**
	 * Start the ray tracer and when done return the final image.
	 * 
	 * @return Final rendered image
	 */
	public BufferedImage render()
	{	
		int sampleWidth = SAMPLE_SIZE;
		int sampleHeight = SAMPLE_SIZE;
		
		// schedule samples to be rendered
		scheduler = new ImageSampleThreadScheduler(threadCount, image);
		for (int x = 0; x < image.image().getWidth(); x += SAMPLE_SIZE) {
			for (int y = 0; y < image.image().getHeight(); y += SAMPLE_SIZE) {
				try {
					sampleWidth = SAMPLE_SIZE; sampleHeight = SAMPLE_SIZE;
					
					// prevent the sample from going over the edge
					if (x + SAMPLE_SIZE > image.image().getWidth()) {
						sampleWidth = image.image().getWidth() - x;
					} 
					if (y + SAMPLE_SIZE > image.image().getHeight()) {
						sampleHeight = image.image().getHeight() - y;
					}
					
					// schedule the sample to render
					ImageSample newSample = new ImageSample(x, y, sampleWidth, sampleHeight, FSAA);
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
		
		JOptionPane.showMessageDialog(null, "Render Complete.", "Done.", JOptionPane.INFORMATION_MESSAGE);
		
		return image.image();
	}
	
	/**
	 * Returns the buffered image assocated with the render buffer.
	 * 
	 * @return
	 */
	public BufferedImage image()
	{
		return image.image();
	}

	/**
	 * Returns all the image samples currently being rendered.
	 * 
	 * @return
	 */
	public synchronized ImageSample[] getCurrentlyRenderingSamples()
	{
		return scheduler.currentlyRenderingSamples();
	}
}
