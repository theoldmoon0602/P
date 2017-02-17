package paint;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Layer for drawing
 * @author root
 *
 */
public class Layer {
	protected List<PaintedObject> objs; 
	public Layer() {
		objs = new ArrayList<>();	
	}
	
	public void addObject(PaintedObject o) {
		objs.add(o);
	}
	
	public void drawTo(Graphics g) {
		for (PaintedObject o : objs) {
			g.drawImage(o.getImage(), (int)o.getPoint().getX(), (int)o.getPoint().getY(), null);
		}
	}
	
	public PaintedObject selectObject(Point p) {
		for (int i = objs.size()-1; i >= 0; i--) {
			if (objs.get(i).isPointInclude(p)) {
				return objs.get(i);
			}
		}
		return null;
	}
	
	public List<PaintedObject> selectObjects(Point p) {
		return objs.stream().filter(o -> o.isPointInclude(p)).collect(Collectors.toList());
	}
}
