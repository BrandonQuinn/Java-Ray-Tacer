package quinn.brandon.scene;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;
import java.util.Random;
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
				light3.location = new Vector3d(20 + i * 250, 20 + j * 250, 580);
				light3.color = new Color3d(
						(int)(new Random().nextDouble() * 255), 
						(int)(new Random().nextDouble() * 255), 
						(int)(new Random().nextDouble() * 255));
				addLight(light3);
			}
		}
		
		InfinitePlane plane = new InfinitePlane();
		plane.location = new Vector3d(320.0, 320.0, 600.0);
		plane.direction = new Vector3d(0.0, 0.0, -1.0).normalize();
		plane.color = new Color3d(220, 220, 255);
		addVolume(plane);
		
		InfinitePlane planeleft = new InfinitePlane();
		planeleft.location = new Vector3d(0.0, 320.0, 500.0);
		planeleft.direction = new Vector3d(1.0, 0.0, -0.9).normalize();
		planeleft.color = new Color3d(220, 220, 0);
		addVolume(planeleft);
	
		PointLight light1 = new PointLight();
		light1.location = new Vector3d(25.0, 25.0, 15.0);
		light1.quadraticAttenuation = 0.5;
		addLight(light1);
		
		//Face testface = new Face();
		//testface.verticies.add(new Vector3d(20, 20, 20));
		//testface.verticies.add(new Vector3d(30, 30, 20));
		//testface.verticies.add(new Vector3d(25, 25, 20));
		//testface.normal = new Vector3d(0, 0, -1);
		//addVolume(testface);
		
		// add a couple of spheres
		Sphere sphere1 = new Sphere(20.0, new Vector3d(310.0, 350.0, 60.0));
		sphere1.color = new Color3d(255, 100, 100);
		addVolume(sphere1);
		
		Sphere sphere2 = new Sphere(80.0, new Vector3d(310.0, 220.0, 60.0));
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
