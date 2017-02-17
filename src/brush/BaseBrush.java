package brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;



import paint.PaintedObject;
import gui.Canvas;

public class BaseBrush extends Brush {
	
	protected BufferedImage bufimg;
	protected int minx, miny, maxx, maxy;
	protected int maxw, maxh;
	protected float thickness = 4f;
	protected Point p;
	protected Graphics2D bufg;
	protected Color color = Color.blue;
	
	public BaseBrush(JPanel toolproperties) {
		super(toolproperties);
	}

	
	@Override
	public void mousePressed(MouseEvent e, Graphics2D g) {
		Canvas c = (Canvas) e.getSource();
		if (e.getX() >= c.getCanvasWidth() || e.getY() >= c.getCanvasHeight()) {
			return;
		}
		bufimg = new BufferedImage(c.getCanvasWidth(), c.getCanvasWidth(), BufferedImage.TYPE_INT_ARGB);
		minx = maxx = e.getX();
		miny = maxy = e.getY();
		maxw = c.getCanvasWidth();
		maxh = c.getCanvasHeight();
		p = e.getPoint();
		bufg = bufimg.createGraphics();
		bufg.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND	));
		bufg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		bufg.setColor(color);
	}
	@Override
	public void mouseDragged(MouseEvent e, Graphics2D g) {
		if (bufimg == null) {
			return;
		}
		bufg.drawLine((int)p.getX(), (int)p.getY(), e.getX(), e.getY());
		p = e.getPoint();
		if (p.getX() < minx) {
			minx = (int) p.getX();
			if (minx < 0) { minx = 0; }
		}
		if (p.getY() < miny) {
			miny = (int) p.getY();
			if (miny < 0) { miny = 0; }
		}
		if (p.getX() > maxx) {
			maxx = (int) p.getX();
			if (maxx >= maxw) { maxx = maxw-1;}
		}
		if (p.getY() > maxy) {
			maxy = (int) p.getY();
			if (maxy >= maxh) { maxw = maxh-1;}
		}
		g.drawImage(bufimg, 0, 0, null);
	}

	@Override
	public void mouseReleased(MouseEvent e, Graphics2D g) {				
		if (getEventHandler() != null && bufimg != null) {
			minx = (int) Math.max(0, minx-thickness);
			miny = (int) Math.max(0, miny-thickness);
			maxx = (int) Math.min(maxw-1, maxx+thickness);
			maxy = (int) Math.min(maxh-1, maxy+thickness);
			
			BufferedImage subimg = bufimg.getSubimage(minx, miny, maxx-minx, maxy-miny);
			getEventHandler().created(new PaintedObject(new Point(minx, miny), subimg));
			
			bufimg = null;
		}
	}
	
	@Override
	public void setToolPropertyPanel() {
		panel.removeAll();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JSlider thicknessSlider = new JSlider(1, 100);
		thicknessSlider.setValue((int) thickness);
		thicknessSlider.addChangeListener(e -> thickness = ((JSlider) e.getSource()).getValue());
	
		panel.add(thicknessSlider);
		
		JButton colorButton = new JButton("");
		colorButton.setBackground(color);
		colorButton.addActionListener(e -> {
			try {
				Color newColor = JColorChooser.showDialog(panel, "", color);
				if (newColor != null) {
				color = newColor;
				colorButton.setBackground(color);
				}
			}catch (Exception e1) {
				// TODO: handle exception
			}
		});
		colorButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 10));
		panel.add(colorButton);
		
		colorButton.revalidate();
		colorButton.repaint();
	}
}
