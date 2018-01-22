package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.image.BufferedImage;
import quinn.brandon.renderer.RayTracer;

public class Main
{
	public static int WIDTH = 640;
	public static int HEIGHT = 640;
	public static int FSAA_FACTOR = 4;
	public static int THREAD_COUNT = 8;
	
	public static void main(String args[]) 
	{
		WaitingDialog.start();
		RayTracer rayTracer = new RayTracer(
				WIDTH, HEIGHT, FSAA_FACTOR, 
				THREAD_COUNT
		);
		BufferedImage image = rayTracer.render();
		WaitingDialog.stop();
		
		MainWindow window = new MainWindow(image);
		window.setVisible(true);
	}
}
