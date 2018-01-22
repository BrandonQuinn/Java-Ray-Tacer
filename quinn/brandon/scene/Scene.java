package quinn.brandon.scene;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;
import org.joml.Vector3d;
import quinn.brandon.renderer.Camera;
import quinn.brandon.renderer.Color3d;
import quinn.brandon.renderer.InfinitePlane;
import quinn.brandon.renderer.Light;
import quinn.brandon.renderer.PointLight;
import quinn.brandon.renderer.Sphere;
import quinn.brandon.renderer.Volume;

public class Scene
{
	/**
	 * The main camera.
	 */
	public static volatile Camera mainCamera = new Camera();
	
	/**
	 * Aribitrary volumes in space.
	 */
	private static volatile ArrayList<Volume> volumes = new ArrayList<Volume>(1);
	
	/**
	 * All lights in the scene.
	 */
	private static volatile ArrayList<Light> lights = new ArrayList<Light>(1);
	
	static {
		
		for (int i = 0; i < 6; i++) { 
			for (int j = 0; j < 6; j++) { 
				PointLight light3 = new PointLight();
				light3.location = new Vector3d(20 + i * 250, 20 + j * 250, 590);
				
				if (i == 3) {
					light3.color = new Color3d(255, 0, 0);
				}
				
				addLight(light3);
			}
		}
		
		InfinitePlane plane = new InfinitePlane();
		plane.location = new Vector3d(320.0, 320.0, 600.0);
		plane.direction = new Vector3d(0.0, 0.0, -1.0).normalize();
		plane.color = new Color3d(220, 220, 255);
		addVolume(plane);
		
		// add a couple of spheres
		Sphere sphere1 = new Sphere(20.0, new Vector3d(310.0, 310.0, 60.0));
		sphere1.color = new Color3d(255, 100, 100);
		addVolume(sphere1);
		
		Sphere sphere2 = new Sphere(40.0, new Vector3d(310.0, 220.0, 60.0));
		sphere2.color = new Color3d(100, 100, 255);
		addVolume(sphere2);
		
		PointLight light = new PointLight();
		light.location = new Vector3d(305.0, 260.0, 30.0);
		light.quadraticAttenuation = 0.5;
		addLight(light);
		
		PointLight light2 = new PointLight();
		light2.location = new Vector3d(305.0, 220.0, 37.0);
		light2.quadraticAttenuation = 0.5;
		addLight(light2);
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
