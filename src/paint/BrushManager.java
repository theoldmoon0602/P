package paint;

import brush.Brush;

public class BrushManager {
	protected static BrushManager instance;
	
	protected Brush brush;
	protected ObjectCreated handler;
	
	protected BrushManager() {}
	
	public static BrushManager getInstance() {
		if (instance == null) {
			instance = new BrushManager();
		}
		return instance;
	}
	
	public void setHandler(ObjectCreated h) {
		this.handler = h;
	}
	
	public void setBrush(Brush brush) {
		this.brush = brush;
		this.brush.setEventHandler(handler);
		this.brush.init();
	}
	
	public Brush getBrush() {
		return brush;
	}

}
