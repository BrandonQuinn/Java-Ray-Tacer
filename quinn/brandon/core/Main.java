package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import quinn.brandon.importer.ImportObj;
import quinn.brandon.renderer.RayTracer;

public class Main
{
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public static int FSAA_FACTOR = 2;
	public static int THREAD_COUNT = 8;
	
	public static void main(String args[]) 
	{
		
		// check if the command line had an obj file to give us.
		try {
			ImportObj.load(new File(args[0]));
		} catch (FileNotFoundException e) {}
		
		WaitingDialog.start();
		RayTracer rayTracer = new RayTracer(
				WIDTH, HEIGHT, 
				FSAA_FACTOR, 
				THREAD_COUNT
		);

		BufferedImage image = rayTracer.render();
		WaitingDialog.stop();
		
		MainWindow window = new MainWindow(image);
		window.setVisible(true);
	}
}
