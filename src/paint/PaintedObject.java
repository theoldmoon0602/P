package paint;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Paited. this is abstract class
 * @author root
 *
 */
public class PaintedObject {
	private Point point;
	protected BufferedImage image;
	
	public PaintedObject() {
		point = null;
		image = null;
	}
	
	public PaintedObject(Point p, BufferedImage im) {
		setPoint(p);
		setImage(im);
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void setImage(BufferedImage image) {
		this.image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.image.createGraphics().drawImage(image, 0, 0, null);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public boolean isPointInclude(Point p) {
		return (p.getX() >= point.getX() && p.getY() >= point.getY() &&
				p.getX() <= (point.getX() + image.getWidth()) && p.getY() <= (point.getY() + image.getHeight()));
	}
	
}
