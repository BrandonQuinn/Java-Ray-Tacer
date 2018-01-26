package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import quinn.brandon.renderer.ImageSample;
import quinn.brandon.renderer.RayTracer;

public class RenderCanvas extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	private volatile BufferedImage image;
	private BufferStrategy bstrat;
	private boolean rendering = true;
	private RayTracer rayTracer;
	
	public RenderCanvas(RayTracer tracer)
	{
		this.image = tracer.image();
		this.rayTracer = tracer;
		this.image = image;
		setSize(image.getWidth(), image.getHeight());
	}
	
	public void start()
	{
		Thread draw = new Thread(this);
		draw.setName("Drawing Thread");
		draw.start();
	}
	
	private static final int CROSS_LEN = 3;
	private static final Color CROSS_COLOR = new Color(244, 167, 66);
	
	@Override public void run()
	{
		createBufferStrategy(2);
		bstrat = getBufferStrategy();
		
		while (rendering) {
			Graphics g = bstrat.getDrawGraphics();
			g.drawImage(image, 0, 0, null);
			
			// draw some nice rectangles around the samples
			try {
				ImageSample[] samplesBeginRendered = rayTracer.getCurrentlyRenderingSamples();
				
				if (samplesBeginRendered != null) {
					for (ImageSample sample : samplesBeginRendered) {
						g.setColor(CROSS_COLOR);
						g.drawLine(sample.x - CROSS_LEN, sample.y, sample.x + CROSS_LEN, sample.y);
						g.drawLine(sample.x, sample.y - CROSS_LEN, sample.x, sample.y + CROSS_LEN);
						g.drawLine(sample.x - CROSS_LEN + sample.width, sample.y, sample.x + CROSS_LEN + sample.width, sample.y);
						g.drawLine(sample.x + sample.width, sample.y - CROSS_LEN, sample.x + sample.width, sample.y + CROSS_LEN);
						g.drawLine(sample.x - CROSS_LEN, sample.y + sample.height, sample.x + CROSS_LEN, sample.y + sample.height);
						g.drawLine(sample.x, sample.y - CROSS_LEN + sample.height, sample.x, sample.y + CROSS_LEN + sample.height);
						g.drawLine(sample.x - CROSS_LEN + sample.width, sample.y + sample.height, sample.x + CROSS_LEN + sample.width, sample.y + sample.height);
						g.drawLine(sample.x + sample.width, sample.y - CROSS_LEN + sample.height, sample.x + sample.width, sample.y + CROSS_LEN + sample.height);
					}
				}
			} catch (Exception e ) {
				// do nothing, a null pointer sometimes occurs but only before rendering starts
				// because no threads are created, so just skip and wait until they're created.
			}
			
			bstrat.show();
			Toolkit.getDefaultToolkit().sync();
		}
	}
}
