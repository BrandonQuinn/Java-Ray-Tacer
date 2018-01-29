package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 22 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import org.joml.Rayd;
import quinn.brandon.renderer.stats.ThreadedRenderStats;
import quinn.brandon.renderer.things.Volume;
import quinn.brandon.scene.Scene;

public class ImageSampleRenderThread implements Runnable
{
	private ImageSample sample;
	private volatile RenderBuffer dest;
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
	 * @param sample Sample to render
	 */
	public void render(ImageSample sample)
	{
		if (done) {
			done = false;
			time = System.currentTimeMillis();
			this.sample = sample;
			Thread t = new Thread(this);
			t.setName("RENDER:" + threadID);
			t.start();
		}
	}
	
	@Override public void run()
	{
		// create an image 'FSAAfactor' times as large as the original
		RenderBuffer supersampledImage = new RenderBuffer(sample.FSAAfactor, sample.FSAAfactor);
		for (int y = sample.y; y < sample.y + sample.height; y++) {
			for (int x = sample.x; x < sample.x + sample.width; x++) {
				for (int sx = 0; sx < sample.FSAAfactor; sx++) {
					for (int sy = 0; sy < sample.FSAAfactor; sy++) {
						Rayd ray = Scene.mainCamera.ray(x + (sx / (double) sample.FSAAfactor), y + (sy / (double) sample.FSAAfactor));
						RayHitOutput closestHit = null;
						
						for (Volume volume : Scene.volumes()) {
							RayHitOutput hit = volume.hit(ray);
							if (hit != null && (closestHit == null || hit.distanceFromOrigin < closestHit.distanceFromOrigin)) {
								closestHit = hit;
								supersampledImage.setPixel(sx, sy, hit.color);
							}
						}
					}
				}
				
				dest.setPixel(x, y, supersampledImage.downSample(sample.FSAAfactor).getPixel(0, 0));
			}
		}
		
		time = System.currentTimeMillis() - time;
		stats.totalTimeInThread[threadID] += time;
		done = true;
	}

	/**
	 * Returns the sample for this thread.
	 * 
	 * @return
	 */
	public synchronized ImageSample getSample()
	{
		return sample;
	}
}
