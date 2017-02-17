package brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import gui.Canvas;

import paint.Layer;
import paint.PaintedObject;

public class SelectorBrush extends Brush {
	protected PaintedObject pobj;
	protected Point cursorPoint;
	protected Point objPoint;

	public SelectorBrush(JPanel toolproperties) {
		super(toolproperties);
	}

	@Override
	public void setToolPropertyPanel() {
		panel.removeAll();
		
		panel.revalidate();
		panel.repaint();
		
	}
	
	@Override
	public void mousePressed(MouseEvent e, Graphics2D g) {
		Canvas canvas = (Canvas) e.getSource();
		Layer l = canvas.getLayerManager().getCurrentLayer();
		pobj = l.selectObject(e.getPoint());
		if (pobj == null) {
			return;
		}
		cursorPoint = e.getPoint();
		objPoint = pobj.getPoint();
		g.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] {1f, 1f}, 0f));
		g.setColor(Color.black);;
		g.drawRect((int) objPoint.getX(), (int) objPoint.getY(), (int) pobj.getImage().getWidth(), pobj.getImage().getHeight());
	}
	
	@Override
	public void mouseDragged(MouseEvent e, Graphics2D g) {
		if (pobj == null) {
			return;
		}
		
		Point newp = new Point();
		newp.setLocation(objPoint.getX()-cursorPoint.getX() + e.getX(), objPoint.getY() -cursorPoint.getY() + e.getY());
		
		pobj.setPoint(newp);
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e, Graphics2D g) {
		pobj = null;
	}

}
