package quinn.brandon.renderer.things;

import quinn.brandon.renderer.Color3d;
import quinn.brandon.renderer.Rayable;

public abstract class Volume implements Rayable
{
	public String name = "%%% NULL %%%";
	public Color3d color = new Color3d(10.0, 10.0, 10.0);
	
	public Volume()
	{
		
	}
}
