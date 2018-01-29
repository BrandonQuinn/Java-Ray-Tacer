package quinn.brandon.core;

import java.awt.BorderLayout;

/***************************************************************************************
 * @author Brandon Quinn
 * @since 21 Jan 2018
 * 
 * Licenced under the MIT License.
 ***************************************************************************************/

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import quinn.brandon.renderer.RayTracer;

public class MainWindow extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	private RenderCanvas canvas;
	
	private MenuBar menubar = new MenuBar();
	private Menu filemenu = new Menu("File");
	private MenuItem saveImageItem = new MenuItem("Save Image");
	
	public MainWindow(RayTracer rayTracer)
	{
		super("Ray Tracer");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e) {}
		
		canvas = new RenderCanvas(rayTracer);
		addWindowListener(this);
		this.image = rayTracer.image();
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
		saveImageItem.addActionListener(this);
		filemenu.add(saveImageItem);
		menubar.add(filemenu);
		setMenuBar(menubar);
		pack();
		setLocationRelativeTo(null);
		canvas.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == saveImageItem) {
			JFileChooser filechooser = new JFileChooser();
			filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			filechooser.setSelectedFile(new File("image.png"));
			filechooser.setFileFilter(new FileNameExtensionFilter("Images", "png"));
			int selection = filechooser.showSaveDialog(this);
			
			if (selection == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				try {
					ImageIO.write(image, "png", file);
				} catch (IOException e1) {}
			}
		}
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
