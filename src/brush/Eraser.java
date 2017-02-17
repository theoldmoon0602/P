package brush;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSlider;

import gui.Canvas;
import paint.Layer;
import paint.PaintedObject;

public class Eraser extends Brush {
	
	protected int thickness;
	protected Point p;

	public Eraser(JPanel toolproperties) {
		super(toolproperties);
		thickness = 1;
	}
	
	@Override
	public void mousePressed(MouseEvent e, Graphics2D g) {
		p = e.getPoint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e, Graphics2D g2) {
		Canvas c = (Canvas) e.getSource();
		Layer l = c.getLayerManager().getCurrentLayer();
		List<PaintedObject> objs = l.selectObjects(e.getPoint());
		for (PaintedObject obj : objs) {
			Graphics2D g = obj.getImage().createGraphics();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
			g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND	));
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// g.fillOval((int)(e.getX()-obj.getPoint().getX()), (int)(e.getY()-obj.getPoint().getY()), 3, 3);
			g.drawLine((int)(e.getX()-obj.getPoint().getX()), (int)(e.getY()-obj.getPoint().getY()),
					(int)(p.getX()-obj.getPoint().getX()), (int)(p.getY()-obj.getPoint().getY()));
		}
		p = e.getPoint();
	}

	@Override
	public void setToolPropertyPanel() {
		panel.removeAll();
		
		JSlider thicknessSlider = new JSlider(1, 100);
		thicknessSlider.setValue(thickness);
		thicknessSlider.addChangeListener(l -> thickness = thicknessSlider.getValue());
		panel.add(thicknessSlider);
		
		panel.revalidate();
		panel.repaint();
	}

}
