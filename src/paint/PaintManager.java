package paint;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

import gui.Canvas;

/**
 * Managing Paint
 * @author root
 *
 */
public class PaintManager implements MouseInputListener, MouseMotionListener {
	
	protected UndoManager undomanager;
	protected BrushManager brushmanager;
	protected LayerManager layerManager;
	protected Canvas canvas;

	
	public PaintManager(Canvas c) {
		layerManager = new LayerManager();
		undomanager = new UndoManager();
		brushmanager = BrushManager.getInstance();
		brushmanager.setHandler(new ObjectLocater(layerManager::getCurrentLayer));
		
		setCanvas(c);
	}
	
	public void setCanvas(Canvas c) {
		canvas = c;
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		c.setLayerManager(layerManager);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		brushmanager.getBrush().mouseClicked(e, (Graphics2D)canvas.getCanvas());
		canvas.repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		brushmanager.getBrush().mousePressed(e, (Graphics2D)canvas.getCanvas());
		canvas.repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		brushmanager.getBrush().mouseReleased(e, (Graphics2D)canvas.getCanvas());
		canvas.repaint();
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		brushmanager.getBrush().mouseDragged(e, (Graphics2D)canvas.getCanvas());
		canvas.repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		brushmanager.getBrush().mouseMoved(e, (Graphics2D)canvas.getCanvas());
		canvas.repaint();
	}

}
