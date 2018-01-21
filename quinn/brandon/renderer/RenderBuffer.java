package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class RenderBuffer
{
	private BufferedImage image;
	private WritableRaster raster;
	
	public RenderBuffer(int width, int height)
	{
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		raster = image.getRaster();
		
		// set to all black
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				raster.setPixel(x, y, new int[] {0, 0, 0, 255});
			}
		}
	}
	
	/**
	 * Change the colour and transparency of the selected pixel.
	 * 
	 * @param x
	 * @param y
	 * @param argb
	 */
	public void setPixel(int x, int y, Color3d pixel)
	{
		raster.setSample(x, y, 0, (int) pixel.r());
		raster.setSample(x, y, 1, (int) pixel.g());
		raster.setSample(x, y, 2, (int) pixel.b());
		raster.setSample(x, y, 3, 255);
	}
	
	/**
	 * Return the image associated with this buffer.
	 * 
	 * @return
	 */
	public BufferedImage image()
	{
		return image;
	}
}
