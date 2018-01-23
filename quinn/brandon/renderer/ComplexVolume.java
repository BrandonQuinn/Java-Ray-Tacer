package quinn.brandon.renderer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.util.ArrayList;
import org.joml.Rayd;

public class ComplexVolume extends Volume
{
	public ArrayList<Face> faces = new ArrayList<Face>();

	@Override public HitData hit(Rayd ray)
	{
		HitData data = new HitData();
		
		for (int i = 0; i < faces.size(); i++) {
			data = faces.get(i).hit(ray);
			if (data != null) return data;
		}
		
		return null;
	}
}
