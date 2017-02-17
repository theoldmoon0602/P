package brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class RainbowBrush extends BaseBrush {

	public RainbowBrush(JPanel toolproperties) {
		super(toolproperties);
	}
	
	@Override
	public void mouseDragged(MouseEvent e, Graphics2D g) {
		// TODO Auto-generated method stub
		super.mouseDragged(e, g);
		
		float[] hsb = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
		hsb[0]+=0.01;
		color = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
		bufg.setColor(color);
	}

}
