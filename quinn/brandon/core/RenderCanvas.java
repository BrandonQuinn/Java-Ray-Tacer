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
	
	private static final int CROSS_LEN = 5;
	private static final Color CROSS_COLOR = new Color(230, 230, 230);
	
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
						
						// top left
						g.drawLine(sample.x, sample.y, sample.x + CROSS_LEN, sample.y);
						g.drawLine(sample.x, sample.y, sample.x, sample.y + CROSS_LEN);
						
						// top right
						g.drawLine(sample.x + sample.width - CROSS_LEN - 1, sample.y, sample.x + sample.width - 1, sample.y);
						g.drawLine(sample.x + sample.width - 1, sample.y, sample.x + sample.width - 1, sample.y + CROSS_LEN);
						
						// bottom right
						g.drawLine(sample.x + sample.width - 1, sample.y + sample.height - CROSS_LEN - 1, sample.x + sample.width - 1, sample.y + sample.height - 1);
						g.drawLine(sample.x + sample.width - CROSS_LEN - 1, sample.y + sample.height - 1, sample.x + sample.width - 1, sample.y + sample.height - 1);
						
						// bottom left
						g.drawLine(sample.x, sample.y + sample.height - CROSS_LEN - 1, sample.x, sample.y + sample.height - 1);
						g.drawLine(sample.x, sample.y + sample.height - 1, sample.x + CROSS_LEN, sample.y + sample.height - 1);
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
