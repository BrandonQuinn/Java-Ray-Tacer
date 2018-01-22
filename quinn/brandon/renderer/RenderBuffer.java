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
	private volatile BufferedImage image;
	private volatile WritableRaster raster;
	
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
	 * Return the colour of the selected pixel.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Color3d getPixel(int x, int y) {
		int[] pixel = new int[4];
		raster.getPixel(x, y, pixel);
		return new Color3d(pixel[0], pixel[1], pixel[2]);
	}
	
	/**
	 * For every pixel we are going to merge it with surrounding pixels in to one by the sampling factor.
	 * 
	 * @param supersamplingFactor
	 * @return
	 */
	public RenderBuffer downSample(int supersamplingFactor)
	{
		RenderBuffer downSampledImage = new RenderBuffer(image.getWidth() / supersamplingFactor, image.getHeight() / supersamplingFactor);
		int[][][] sample = new int[supersamplingFactor][supersamplingFactor][4];
		
		Color3d average = new Color3d(0, 0, 0);
		for (int x = 0; x < downSampledImage.image().getWidth(); x++) {
			for (int y = 0; y < downSampledImage.image().getHeight(); y++) {
				average.x = 0; average.y = 0; average.z = 0;
				// go throw the sample on the large image (this)
				for (int xx = x * supersamplingFactor, sx = 0; xx < x * supersamplingFactor + supersamplingFactor; xx++, sx++) {
					for (int yy = y * supersamplingFactor, sy = 0; yy < y * supersamplingFactor + supersamplingFactor; yy++, sy++) {
						raster.getPixel(xx, yy, sample[sx][sy]);
						average.x += sample[sx][sy][0];
						average.y += sample[sx][sy][1];
						average.z += sample[sx][sy][2];
					}
				}
				average.x /= supersamplingFactor * supersamplingFactor;
				average.y /= supersamplingFactor * supersamplingFactor;
				average.z /= supersamplingFactor * supersamplingFactor;
				downSampledImage.setPixel(x, y, average);
			}
		}
				
		return downSampledImage;
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
