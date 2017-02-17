package brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

import gui.Canvas;
import paint.PaintedObject;

public class PolyBrush extends Brush {
	
	protected int n = 2;
	protected int cur;
	protected int[] xs;
	protected int[] ys;
	protected int thickness = 3;
	protected Color color = Color.blue;


	public PolyBrush(JPanel toolproperties) {
		super(toolproperties);
	}
	public void setGraphicsProperty(Graphics2D g) {
		g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(color);
		
	}
	@Override
	public void mouseMoved(MouseEvent e, Graphics2D g) {
		if (xs == null) {
			return;
		}
		setGraphicsProperty(g);
		xs[cur] = e.getX();
		ys[cur] = e.getY();
		if (cur+1==n) {
			g.drawPolygon(xs,ys,cur+1);
		}else{
			g.drawPolyline(xs, ys, cur+1);
		}
	}
	@Override
	public void mousePressed(MouseEvent e, Graphics2D g) {
		if (xs == null) {
			xs = new int[n];
			ys = new int[n];
			cur = 0;
		}
		xs[cur] = e.getX();
		ys[cur] = e.getY();
		cur++;
		
		if (cur == n) {
			if (eventHandler != null) {
			Canvas c = (Canvas) e.getSource(); 
			BufferedImage buf = new BufferedImage(c.getCanvasWidth(), c.getCanvasHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D bufg = buf.createGraphics();
			setGraphicsProperty(bufg);
			bufg.drawPolygon(xs, ys, n);
			Arrays.sort(xs);
			Arrays.sort(ys);
			
			int minx = Math.max(0, xs[0]-thickness);
			int miny = Math.max(0, ys[0]-thickness);
			int maxx = Math.min(buf.getWidth(), xs[n-1]+thickness);
			int maxy = Math.min(buf.getHeight(), ys[n-1]+thickness);
			
			BufferedImage subimg = buf.getSubimage(minx, miny, maxx-minx, maxy-miny);
			eventHandler.created(new PaintedObject(new Point(minx, miny), subimg));
			}
			xs = null; ys = null;
			cur = 0;
			
		}
		else if (cur+1==n) {
			setGraphicsProperty(g);
			g.drawPolygon(xs,ys,cur);
		}
		else {
			setGraphicsProperty(g);
			g.drawPolyline(xs, ys, cur);
		}
	}

	@Override
	public void setToolPropertyPanel() {
		panel.removeAll();
		
		panel.setLayout(new FlowLayout());
		
		JComboBox<Integer> nComboBox = new JComboBox<>(new Integer[]{2,3,4,5,6});
		nComboBox.addActionListener(e -> {
			n = (Integer)nComboBox.getSelectedItem();
			xs = null;
		});
		panel.add(nComboBox);
		
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
		
		panel.revalidate();
		panel.repaint();

	}

}
