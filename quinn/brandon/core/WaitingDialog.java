package quinn.brandon.core;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingDialog implements Runnable, WindowListener
{
	private static volatile JFrame frame = new JFrame("Rendering");
	
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
		frame.setSize(260, 100);
		frame.setLayout(new GridLayout(1, 3));
		frame.setLocationRelativeTo(null);
		frame.add(new JPanel());
		frame.add(new JLabel("Tracing rays..."), BorderLayout.CENTER);
		frame.add(new JPanel());
		frame.addWindowListener(this);
		frame.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{

	}

	@Override
	public void windowClosed(WindowEvent e)
	{

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{

	}

	@Override
	public void windowIconified(WindowEvent e)
	{

	}

	@Override
	public void windowOpened(WindowEvent e)
	{

	}
}
