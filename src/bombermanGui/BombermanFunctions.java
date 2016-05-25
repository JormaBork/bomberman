package bombermanGui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BombermanFunctions extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = 20, y=20;
		g.setColor(new Color(255, 255, 0));
		g.fillRect(x, y, 25, 25);
		g.setColor(Color.BLACK);
		g.drawRect(x-1000, y-1000, 25, 25);
	}

}