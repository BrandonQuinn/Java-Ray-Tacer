package quinn.brandon.core;

import java.io.File;
import java.io.FileNotFoundException;
import quinn.brandon.importer.ImportObj;
import quinn.brandon.renderer.RayTracer;
import quinn.brandon.scene.Scene;

public class Main
{
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public static int FSAA_FACTOR = 1;
	public static int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
	
	public static void main(String args[]) throws FileNotFoundException 
	{
		ImportObj.load(new File(Main.class.getResource("/quinn/brandon/testres/testquads.obj").getFile().replace("%20", " ")));
		Scene.createDemoScene();
		
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
