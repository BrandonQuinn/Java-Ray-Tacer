package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 22 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

public class ImageSample
{
	public int x;
	public int y;
	public int width;
	public int height;
	public int FSAAfactor;
	
	public ImageSample(int x, int y, int width, int height, int FSAAfactor)
	{
		this.x = x; this.y = y; 
		this.width = width; this.height = height;
		this.FSAAfactor = FSAAfactor;
	}
	
	@Override
	public String toString()
	{
		return "(x: " + x + ", y: " + y + ", width: " + width + ", height: " + height + ") w/FSAA: " + FSAAfactor;
	}
}
