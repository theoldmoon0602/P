package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import brush.BaseBrush;
import brush.Brush;
import brush.Eraser;
import brush.PolyBrush;
import brush.RainbowBrush;
import brush.SelectorBrush;
import paint.BrushManager;
import paint.PaintManager;
import paint.PaintedObject;

/**
 * Paint application
 * @author root
 *
 */
public class P implements Runnable {
	// constants
	protected final String appName = "P";
	protected final Dimension initialSize = new Dimension(600, 600);

	
	// gui components
	protected JFrame window;
	protected JMenuBar menubar;
	protected JToolBar toolbar;
	protected JPanel toolproperties;
	protected Canvas canvas;
	
	// paint managers
	protected PaintManager paintmanager;
	

	public P() {
		window = new JFrame(appName);
		menubar = new JMenuBar();
		toolbar = new JToolBar();
		toolproperties = new JPanel();
		canvas = new Canvas(400, 400);
		
		paintmanager = new PaintManager(canvas);
	}
	
	/**
	 * Initialize attributes
	 */
	protected void init() {
		window.setResizable(true);
		window.setLayout(new BorderLayout());
		
		window.add(toolbar, BorderLayout.NORTH);
		window.add(toolproperties, BorderLayout.EAST);
		window.add(canvas, BorderLayout.CENTER);
		
		window.setJMenuBar(menubar);
		
		toolbar.setPreferredSize(new Dimension(0, 20));
		toolbar.setFloatable(false);
		
		JButton brushbutton = new JButton("button");
		{
			Brush b = new BaseBrush(toolproperties);
			brushbutton.addActionListener(e -> BrushManager.getInstance().setBrush(b));
		}
		toolbar.add(brushbutton);
		
		JButton rainbowButton = new JButton("Rainbow");
		{
			Brush b = new RainbowBrush(toolproperties);
			rainbowButton.addActionListener(e -> BrushManager.getInstance().setBrush(b));
		}
		toolbar.add(rainbowButton);
		
		JButton selectorButton = new JButton("Selector");
		{
			Brush b = new SelectorBrush(toolproperties);
			selectorButton.addActionListener(e -> BrushManager.getInstance().setBrush(b));
		}
		toolbar.add(selectorButton);
		
		JButton eraserButton = new JButton("Eraser");
		{
			Brush b = new Eraser(toolproperties);
			eraserButton.addActionListener(e -> BrushManager.getInstance().setBrush(b));
		}
		toolbar.add(eraserButton);
		
		JButton polyButton = new JButton("Polyline");
		{
			Brush b = new PolyBrush(toolproperties);
			polyButton.addActionListener(e -> BrushManager.getInstance().setBrush(b));
		}
		toolbar.add(polyButton);
		
		JButton addlayerbutton = new JButton("+ Layer");
		addlayerbutton.addActionListener(e -> canvas.getLayerManager().addLayer());
		toolbar.add(addlayerbutton);
		
		JButton rmlayerbutton = new JButton("- Layer");
		rmlayerbutton.addActionListener(e -> canvas.getLayerManager().removeCurrentLayer());
		toolbar.add(rmlayerbutton);
		
		JButton nextLayerbutton = new JButton("up Layer");
		nextLayerbutton.addActionListener(e -> canvas.getLayerManager().upLayer());
		toolbar.add(nextLayerbutton);
		
		JButton downLayerButton = new JButton("down layer");
		downLayerButton.addActionListener(e -> canvas.getLayerManager().downLayer());
		toolbar.add(downLayerButton);
		
		
		JMenu filemenu = new JMenu("File");
		JMenuItem loadMenu = new JMenuItem("LOAD");
		loadMenu.addActionListener(e -> {
			JFileChooser load = new JFileChooser();
			if (load.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
				File in = load.getSelectedFile();
				try {
					BufferedImage img = ImageIO.read(in);
					canvas.getLayerManager().getCurrentLayer().addObject(new PaintedObject(new Point(0, 0), img));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		filemenu.add(loadMenu);
		JMenuItem saveMenu = new JMenuItem("SAVE");
		saveMenu.addActionListener(e -> {
			JFileChooser save = new JFileChooser();
			if (save.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
				File of = save.getSelectedFile();
				String ext = of.getName().substring(of.getName().lastIndexOf('.') + 1);
				
				try {
					if (ext.equals("jpg") || ext.equals("jpeg")) {
						BufferedImage canvasImg = canvas.getImage();
						BufferedImage img = new BufferedImage(canvasImg.getWidth(), canvasImg.getHeight(), BufferedImage.TYPE_INT_RGB);
						img.createGraphics().drawImage(canvasImg, 0, 0, Color.WHITE, null);
						
						ImageIO.write(img, ext, of);
					} else {
						ImageIO.write(canvas.getImage(), ext, of);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		filemenu.add(saveMenu);
		menubar.add(filemenu);
		
		toolproperties.setPreferredSize(new Dimension(200, 0));
		
		brushbutton.doClick();
	}


	/**
	 * Launch application window
	 */
	public void run() {	
		init();
		
		window.setSize((int)initialSize.getWidth(), (int)initialSize.getHeight());
		SwingUtilities.invokeLater(() -> window.setVisible(true));
	}

}
