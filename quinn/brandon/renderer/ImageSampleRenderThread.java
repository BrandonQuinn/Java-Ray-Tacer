package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 22 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Rayd;
import quinn.brandon.renderer.stats.ThreadedRenderStats;
import quinn.brandon.scene.Scene;

public class ImageSampleRenderThread implements Runnable
{
	private ImageSample sample;
	private RenderBuffer dest;
	public volatile boolean done = true;
	public volatile int threadID;
	public ThreadedRenderStats stats;
	
	public ImageSampleRenderThread(RenderBuffer dest, int threadID, ThreadedRenderStats stats)
	{
		this.stats = stats;
		this.threadID = threadID;
		this.dest = dest;
	}
	
	private double time = 0.0f;
	
	/**
	 * Render the sample to the destination render buffer.
	 * 
	 * @param sample
	 */
	public void render(ImageSample sample)
	{
		if (done) {
			time = System.currentTimeMillis();
			done = false;
			this.sample = sample;
			Thread thread = new Thread(this);
			thread.setName("Render Thread " + threadID);
			thread.start();
		}
	}
	
	@Override
	public void run()
	{
		for (int x = sample.x; x < sample.x + sample.width; x++) {
			for (int y = sample.y; y < sample.y + sample.height; y++) {
				Rayd ray = Scene.mainCamera.ray(x / (double) sample.FSAAfactor, y / (double) sample.FSAAfactor);
				for (Volume volume : Scene.volumes()) {
					Color3d color = volume.hit(ray);
					if (color != null) dest.setPixel(x, y, new Color3d(color.r(), color.g(), color.b()));
				}
			}
		}
		
		done = true;
		time = System.currentTimeMillis() - time;
		stats.totalTimeInThread[threadID] += time;
		stats.renderTime += time;
	}
}