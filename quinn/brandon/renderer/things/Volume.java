package quinn.brandon.renderer.things;

import quinn.brandon.renderer.Rayable;
import quinn.brandon.renderer.Surface;

public abstract class Volume implements Rayable
{
	public String name = "%%% NULL %%%";
	public Surface surface = new Surface();
	
	public Volume()
	{
		
	}
}
