package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under MIT.
 ***************************************************************************************/

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RenderCanvas extends Canvas
{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	public RenderCanvas(BufferedImage image)
	{
		this.image = image;
		setSize(image.getWidth(), image.getHeight());
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}
}
