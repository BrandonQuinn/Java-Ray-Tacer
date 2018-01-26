package quinn.brandon.core;

import java.io.File;
import java.io.FileNotFoundException;
import quinn.brandon.importer.ImportObj;
import quinn.brandon.renderer.RayTracer;

public class Main
{
	public static int WIDTH = 1600;
	public static int HEIGHT = 900;
	public static int FSAA_FACTOR = 1;
	public static int THREAD_COUNT = 8;
	
	public static void main(String args[]) throws FileNotFoundException 
	{
		ImportObj.load(new File(Main.class.getResource("/quinn/brandon/testres/test.obj").getFile().replace("%20", " ")));
		
		RayTracer rayTracer = new RayTracer(
				WIDTH, HEIGHT, 
				FSAA_FACTOR, 
				THREAD_COUNT
		);
		
		MainWindow window = new MainWindow(rayTracer);
		window.setVisible(true);
		rayTracer.render();
	}
}
