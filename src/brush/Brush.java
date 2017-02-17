package brush;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import paint.ObjectCreated;

public abstract class Brush {
	protected ObjectCreated eventHandler;
	protected JPanel panel;
	
	public Brush(JPanel toolproperties) {
		panel = toolproperties;
	}
	
	public void init() {
		setToolPropertyPanel();
	}

	abstract public void setToolPropertyPanel();
	public void mouseClicked(MouseEvent e, Graphics2D g) {}
	public void mouseMoved(MouseEvent e, Graphics2D g) {}
	public void mouseDragged(MouseEvent e, Graphics2D g) {}
	public void mouseEntered(MouseEvent e, Graphics2D g) {}
	public void mouseExited(MouseEvent e, Graphics2D g) {}
	public void mouseReleased(MouseEvent e, Graphics2D g) {}
	public void mousePressed(MouseEvent e, Graphics2D g) {}
	
	public ObjectCreated getEventHandler() {
		return eventHandler;
	}
	public void setEventHandler(ObjectCreated eventHandler) {
		this.eventHandler = eventHandler;
	}

}
