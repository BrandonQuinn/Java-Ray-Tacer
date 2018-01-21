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
	
	public static void main(String args[]) 
	{
		WaitingDialog.start();
		RayTracer rayTracer = new RayTracer(WIDTH, HEIGHT);
		BufferedImage image = rayTracer.start();
		WaitingDialog.stop();
		
		MainWindow window = new MainWindow(image);
		window.setVisible(true);
	}
}
