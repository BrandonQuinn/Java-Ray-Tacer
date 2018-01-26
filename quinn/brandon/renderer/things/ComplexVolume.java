package quinn.brandon.renderer.things;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;
import org.joml.Rayd;
import quinn.brandon.renderer.HitData;

public class ComplexVolume extends Volume
{
	public ArrayList<Face> faces = new ArrayList<Face>();

	@Override public HitData hit(Rayd ray)
	{
		HitData data = new HitData();
		HitData nearest = null;
		// just check if the ray hits any of the faces and return it immediately
		for (int i = 0; i < faces.size(); i++) {
			data = faces.get(i).hit(ray);
			if (data != null) {
				if (nearest == null) nearest = data;
				if (nearest.distanceFromOrigin > data.distanceFromOrigin) nearest = data;
			}
		}
		
		return nearest;
	}
}
