package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import paint.Layer;
import paint.LayerManager;


/**
 * Canvas for Drawing
 * @author root
 *
 */
public class Canvas extends JPanel {
	protected BufferedImage buf;
	private LayerManager layerManager;
	protected int w, h;
	
	public Canvas(int width, int height) {
		w = width;
		h = height;
		
		buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	}
	
	public int getCanvasWidth() {
		return w;
	}
	
	public int getCanvasHeight() {
		return h;
	}
	
	public Graphics getCanvas() {
		return buf.createGraphics();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw Layer Image to buffer
		BufferedImage img = getImage();
		
		// clear canvas 		
		((Graphics2D)g).setBackground(new Color(128,128,128));
		g.clearRect(0, 0, getWidth(), getHeight());
		
		//draw buffered image and temporary
		g.drawImage(img, 0, 0, null);
		g.drawImage(buf, 0, 0, null);
		
		// add border
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getCanvasWidth(), getCanvasHeight());
		
		// clear temporary
		buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	
	}
	
	public BufferedImage getImage() {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics imgbuf = img.createGraphics();
		layerManager.forEachVisibleLayers(layer -> layer.drawTo(imgbuf));
		
		return img;
	}

	public LayerManager getLayerManager() {
		return layerManager;
	}

	public void setLayerManager(LayerManager layerManager) {
		this.layerManager = layerManager;
	}

}
