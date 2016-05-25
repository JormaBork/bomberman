package bombermanGui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class bombersnake {

	private static class bombermanGui extends JPanel implements KeyListener {
		private static final long serialVersionUID = 1L;

		Point playerPosition = new Point(25, 25);
		boolean end = false;
		final int WIDTH = 775, HEIGHT = 375;
		
		ImagePanel imageOne = new ImagePanel("\test.jpg");

		bombermanGui() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFocusable(true);
			addKeyListener(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
			g.setColor(Color.BLACK);

			// Die Wände drumherum
			for (int y = 0; y < HEIGHT; y += 25) {
				g.setColor(new Color(255, 100, 100)); // die Farbe innerhalb des
														// Rechtecks
				g.fillRect(0, y, 25, 25);
				g.setColor(Color.BLACK); // die Farbe des Rands des Rechtecks
				g.drawRect(0, y, 25, 25);
				g.setColor(new Color(255, 100, 100)); // die Farbe innerhalb des
														// Rechtecks
				g.fillRect(WIDTH - 25, y, 25, 25);
				g.setColor(Color.BLACK); // die Farbe des Rands des Rechtecks
				g.drawRect(WIDTH - 25, y, 25, 25);
			}
			for (int x = 0; x < WIDTH; x += 25) {
				g.setColor(new Color(255, 100, 100)); // die Farbe innerhalb des
														// Rechtecks
				g.fillRect(x, 0, 25, 25);
				g.setColor(Color.BLACK); // die Farbe des Rands des Rechtecks
				g.drawRect(x, 0, 25, 25);
				g.setColor(new Color(255, 100, 100)); // die Farbe innerhalb des
														// Rechtecks
				g.fillRect(x, HEIGHT - 25, 25, 25);
				g.setColor(Color.BLACK); // die Farbe des Rands des Rechtecks
				g.drawRect(x, HEIGHT - 25, 25, 25);
			}

			// Die Wände innerhalb des Spielfeldes
			for (int y = 0; y < HEIGHT; y += 50) {
				for (int x = 0; x < WIDTH; x += 50) {
					g.setColor(new Color(255, 100, 100)); // die Farbe innerhalb
															// des Rechtecks
					g.fillRect(x, y, 25, 25);
					g.setColor(Color.BLACK); // die Farbe des Rands des
												// Rechtecks
					g.drawRect(x, y, 25, 25);
				}
			}

			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					Point p = new Point(x, y);

					if (playerPosition.equals(p)) {
						g.setColor(new Color(100, 200, 100)); // die Farbe
																// innerhalb des
																// Rechtecks
						g.fillRect(x, y, 25, 25);
						g.setColor(Color.BLACK); // die Farbe des Rands des
													// Rechtecks
						g.drawRect(x, y, 25, 25);
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (end)
				return;
			// Tasteneingabe und Spielerposition verÃ¤ndern

			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				playerPosition.y = Math.max(0, playerPosition.y - 1);
				break;
			case KeyEvent.VK_DOWN:
				playerPosition.y = Math.min(HEIGHT - 1, playerPosition.y + 1);
				break;
			case KeyEvent.VK_LEFT:
				playerPosition.x = Math.max(0, playerPosition.x - 1);
				break;
			case KeyEvent.VK_RIGHT:
				playerPosition.x = Math.min(WIDTH - 1, playerPosition.x + 1);
				break;
			}
			repaint(10);
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (end)
				return;
			// Tasteneingabe und Spielerposition veraendern

			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				playerPosition.y = Math.max(0, playerPosition.y - 1);
				break;
			case KeyEvent.VK_DOWN:
				playerPosition.y = Math.min(HEIGHT - 1, playerPosition.y + 1);
				break;
			case KeyEvent.VK_LEFT:
				playerPosition.x = Math.max(0, playerPosition.x - 1);
				break;
			case KeyEvent.VK_RIGHT:
				playerPosition.x = Math.min(WIDTH - 1, playerPosition.x + 1);
				break;
			}
			repaint(10);
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("BOMBERMAN");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new bombermanGui());
		f.pack();
		f.setVisible(true);
	}
}