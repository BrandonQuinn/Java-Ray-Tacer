package quinn.brandon.core;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under MIT.
 ***************************************************************************************/

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

public class MainWindow extends Frame implements WindowListener
{
	private static final long serialVersionUID = 1L;

	private RenderCanvas canvas;
	
	public MainWindow(BufferedImage image)
	{
		super("Ray Tracer");
		canvas = new RenderCanvas(image);
		addWindowListener(this);
		add(canvas);
		pack();
		setLocationRelativeTo(null);
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
