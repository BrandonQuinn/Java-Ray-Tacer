package quinn.brandon.importer;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 23 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.joml.Vector3d;
import quinn.brandon.renderer.things.ComplexVolume;
import quinn.brandon.renderer.things.Face;
import quinn.brandon.renderer.things.Volume;
import quinn.brandon.scene.Scene;

public class ImportObj
{
	/**
	 * Load all the volumes from the given objFile. 
	 * Doesn't do textures, just blank volumes.
	 * 
	 * @param objFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public static void load(File objFile) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(objFile);
		
		int volumeIndex = -1;
		ArrayList<ComplexVolume> volumes = new ArrayList<ComplexVolume>();
		ArrayList<Vector3d> verticies = new ArrayList<Vector3d>();
		ArrayList<Vector3d> normals = new ArrayList<Vector3d>();
		
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("\\s");
			
			if (line[0].equals("mtllib")) {
				// do nothing yet...
			} else if (line[0].equals("o")) {
				// create a new object
				ComplexVolume newVolume = new ComplexVolume();
				newVolume.name = line[1];
				volumes.add(newVolume);
				volumeIndex++;
			} else if (line[0].equals("v")) {
				// create vertex and add it to list
				double vx = Double.valueOf(line[1]);
				double vy = Double.valueOf(line[2]);
				double vz = Double.valueOf(line[3]);
				Vector3d vertex = new Vector3d(vx, vy, vz);
				verticies.add(vertex);
			} else if (line[0].equals("vn")) {
				// create normal and add it to list
				double nx = Double.valueOf(line[1]);
				double ny = Double.valueOf(line[2]);
				double nz = Double.valueOf(line[3]);
				Vector3d normal = new Vector3d(nx, ny, nz);
				normals.add(normal);
			} else if (line[0].equals("usemtl")) {
				// do nothing yet...
			} else if (line[0].equals("s")) {
				// do nothing yet...
			}  else if (line[0].equals("f")) {
				Face face = new Face();
				
				for (int i = 1; i < line.length; i++) {
					int vertexIndex = Integer.valueOf(line[i].split("/")[0]);
					// int matIndex = Integer.valueOf(line[i].split("/")[1]);
					int normalIndex = Integer.valueOf(line[i].split("/")[2]);
					
					Vector3d newVec = new Vector3d(
							(verticies.get(vertexIndex - 1)).x, 
							(verticies.get(vertexIndex - 1)).y, 
							(verticies.get(vertexIndex - 1)).z);
					Vector3d newNorm = new Vector3d(
							(normals.get(normalIndex - 1)).x, 
							(normals.get(normalIndex - 1)).y, 
							(normals.get(normalIndex - 1)).z);
					
					face.verticies.add(newVec);
					face.normal = newNorm;
				}
				
				volumes.get(volumeIndex).faces.add(face);
			}
		}
		
		// add all the volumes to the scene
		for (Volume volume : volumes) Scene.addVolume(volume);
		
		scanner.close();
	}
}
