package quinn.brandon.scene;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;
import org.joml.Vector3d;
import quinn.brandon.renderer.Color3d;
import quinn.brandon.renderer.Light;
import quinn.brandon.renderer.PointLight;
import quinn.brandon.renderer.Sphere;
import quinn.brandon.renderer.Volume;

public class Scene
{
	public static final int WORLD_SIZE = 64;
	
	/**
	 * Aribitrary volumes in space.
	 */
	private static volatile ArrayList<Volume> volumes = new ArrayList<Volume>(1);
	
	/**
	 * All lights in the scene.
	 */
	private static volatile ArrayList<Light> lights = new ArrayList<Light>(1);
	
	static {
		// add a couple of spheres
		Sphere sphere1 = new Sphere(50.0, new Vector3d(310.0, 310.0, 100.0));
		sphere1.setColor(new Color3d(255, 100, 100));
		volumes.add(sphere1);
		
		Sphere sphere2 = new Sphere(40.0, new Vector3d(200.0, 200.0, 60.0));
		sphere2.setColor(new Color3d(100, 100, 255));
		volumes.add(sphere2);
		
		PointLight light = new PointLight();
		light.location = new Vector3d(220.0, 220.0, 10.0);
		lights.add(light);
	}
	
	/**
	 * Add a volume to the game world.
	 * 
	 * @param volume
	 */
	public static void addVolume(Volume volume)
	{
		volumes.add(volume);
	}
	
	/**
	 * Returns the list of all volumes in the game.
	 * 
	 * @return
	 */
	public static ArrayList<Volume> volumes()
	{
		return volumes;
	}
	
	/**
	 * Add a light to the game world.
	 * 
	 * @param volume
	 */
	public static void addLight(Light light)
	{
		lights.add(light);
	}
	
	/**
	 * Returns the list of all lights in the game.
	 * 
	 * @return
	 */
	public static ArrayList<Light> lights()
	{
		return lights;
	}
}
