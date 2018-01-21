package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import javax.swing.JFrame;
import javax.swing.JLabel;

public class WaitingDialog implements Runnable
{
	private static volatile JFrame frame = new JFrame("Running Ray Tracer...");
	
	public static void start()
	{
		Thread thread = new Thread(new WaitingDialog());
		thread.setName("Waiting Dialog Thread");
		thread.start();
	}
	
	public synchronized static void stop()
	{
		frame.setVisible(false);
	}

	@Override
	public void run()
	{
		frame.setSize(200, 100);
		frame.setLocationRelativeTo(null);
		frame.add(new JLabel("Rendering..."));
		frame.setVisible(true);
	}
}
