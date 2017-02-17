package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JPanel;

public class ToolButton implements ActionListener {
	protected JPanel panel;
	public ToolButton(JPanel toolproperties) {
		panel = toolproperties;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

}
