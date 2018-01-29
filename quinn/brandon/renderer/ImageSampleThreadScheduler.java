/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

package quinn.brandon.renderer;

import java.util.ArrayList;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 22 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.concurrent.LinkedBlockingQueue;
import quinn.brandon.renderer.stats.ThreadedRenderStats;

public class ImageSampleThreadScheduler
{
	public static final int MAX_SAMPlE_THREADS = Integer.MAX_VALUE;
	private int threadCount;
	private RenderBuffer renderBuffer;
	private ImageSampleRenderThread[] threadPool;
	private LinkedBlockingQueue<ImageSample> scheduleQueue = new LinkedBlockingQueue<ImageSample>(MAX_SAMPlE_THREADS);
	private volatile ThreadedRenderStats stats = new ThreadedRenderStats();
	
	/**
	 * Initialise the schedular with a certain number of thread for the given image.
	 * 
	 * @param threadCount
	 * @param renderBuffer
	 */
	public ImageSampleThreadScheduler(int threadCount, RenderBuffer renderBuffer)
	{
		this.threadCount = threadCount;
		this.renderBuffer = renderBuffer;
		threadPool = new ImageSampleRenderThread[this.threadCount];
		
		stats.totalTimeInThread = new double[threadCount];
		
		// create each thread in the pool
		for (int i = 0; i < threadCount; i++)
			threadPool[i] = new ImageSampleRenderThread(this.renderBuffer, i, stats);
	}
	
	/**
	 * Add an image sample to render
	 * @param sample Sample of the image to render
	 * @throws InterruptedException 
	 */
	public void scheduleSample(ImageSample sample) throws IllegalStateException, InterruptedException
	{
		scheduleQueue.put(sample);
	}
	
	/**
	 * Start from the start of the schedule queue and render all samples.
	 */
	public ThreadedRenderStats renderAll()
	{
		stats.renderTime = System.currentTimeMillis();
		
		while(!scheduleQueue.isEmpty()) {
			// wait for a free thread the nstart it with the next item in the queue
			ImageSampleRenderThread thread = waitForFreeThread();
			thread.render(scheduleQueue.poll());
		}
		
		// wait for threads to complete
		waitToFinish();
		
		stats.renderTime = System.currentTimeMillis() - stats.renderTime;
		return stats;
	}
	
	/**
	 * Waits until all threads set themselves to be done.
	 */
	public void waitToFinish()
	{
		int freeThreads = 0;
		while(true) {
			freeThreads = 0;
			for (int i = 0; i < threadCount; i++) { 
				if (threadPool[i].done) freeThreads++;
			}
			if (freeThreads == threadCount) return;
		}
	}
	
	/**
	 * Halts execution and returns the first free thread it finds.
	 * 
	 * @return Returns a thread that is free to start
	 */
	public ImageSampleRenderThread waitForFreeThread()
	{
		while (true) {
			for (ImageSampleRenderThread thread : threadPool) {
				if (thread.done) {
					return thread;
				}
			}
		}
	}

	/**
	 * Returns a list of all samples that are currently not done.
	 * 
	 * @return
	 */
	public synchronized ImageSample[] currentlyRenderingSamples()
	{
		ArrayList<ImageSample> samples = new ArrayList<ImageSample>();
		
		for (int i = 0; i < threadPool.length; i++) {
			if (!threadPool[i].done) {
				samples.add(threadPool[i].getSample());
			}
		}
		
		return samples.toArray(new ImageSample[samples.size()]);
	}
}
