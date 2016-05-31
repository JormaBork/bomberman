package bombermanGui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BombermanBorkKnebel {
	private static class bombermanGui extends JPanel implements KeyListener {

//		Comment, Kommentarrrr
		
		private static final long serialVersionUID = 1L;
		Point playerPosition = new Point(25, 25);
		Point tntPOS = new Point(6, 6);
		Point doorPosition = new Point(0, 5);
		Point[] snakePositions = { new Point(30, 2), null, null, null, null };
		Point wallPosition = new Point();
		BufferedImage wallImage, woodImage, tntImage, creeperImage;
		BufferedImage imageDestroyable;
		int travelPixel = 5;
		boolean rich = false;
		boolean end = false;
		final int WIDTH = 775, HEIGHT = 375;

		bombermanGui() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFocusable(true);
			addKeyListener(this);
			try {
				wallImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("wall.png"));
				woodImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("wood.png"));
				tntImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("tnt.png"));
				creeperImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("creeper.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// Figuren zeichnen
			g.drawImage(woodImage, 50, 25, 25, 25, null);
			g.drawImage(tntImage, 50, 75, 25, 25, null);

			// Die W‰nde drumherum
			for (int y = 0; y < HEIGHT; y += 25) {
				g.drawImage(wallImage, 0, y, null);
				g.drawImage(wallImage, WIDTH - 25, y, null);
			}
			for (int x = 0; x < WIDTH; x += 25) {
				g.drawImage(wallImage, x, 0, null);
				g.drawImage(wallImage, x, HEIGHT - 25, null);
			}
			// Die W‰nde innerhalb des Spielfeldes
			for (int y = 0; y < HEIGHT; y += 50) {
				for (int x = 0; x < WIDTH; x += 50) {
					g.drawImage(wallImage, x, y, null);
				}
			}
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					char c = ' ';
					Point p = new Point(x, y);

					if (tntPOS.equals(p))
						c = '$';

					if (playerPosition.equals(p)) {
						g.drawImage(creeperImage, x, y, 20, 20, null);
					}
					// g.hitclip f¸r collisionsabfrage mˆglich?
					if (g.hitClip(50, 125, 25, 25))

						if (Arrays.asList(snakePositions).contains(p))
							c = 'S';
					if (!Character.isWhitespace(c))
						g.drawString(Character.toString(c), x * 10, y * 10);
				}
			}
			// Status aktualisieren
			if (rich && playerPosition.equals(doorPosition)) {
				System.out.println("Gewonnen!");
				end = true;
				return;
			}
			if (Arrays.asList(snakePositions).contains(playerPosition)) {
				System.out.println("ZZZZZZZ. Die Schlage hat dich!");
				end = true;
				return;
			}
			if (playerPosition.equals(tntPOS)) {
				rich = true;
				tntPOS.setLocation(-1, -1);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (end)
				return;
			// Tasteneingabe und Spielerposition ver√§ndern
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				playerPosition.y = Math.max(0, playerPosition.y - travelPixel);
				break;
			case KeyEvent.VK_DOWN:
				playerPosition.y = Math.min(HEIGHT - travelPixel, playerPosition.y + travelPixel);
				break;
			case KeyEvent.VK_LEFT:
				playerPosition.x = Math.max(0, playerPosition.x - travelPixel);
				break;
			case KeyEvent.VK_RIGHT:
				playerPosition.x = Math.min(WIDTH - travelPixel, playerPosition.x + travelPixel);
				break;
			}
			// Schlange bewegt sich Richtung Spieler
			// Point snakeHead = new Point( snakePositions[snakeIdx].x,
			// snakePositions[snakeIdx].y );
			//
			// if ( playerPosition.x < snakeHead.x )
			// snakeHead.x--;
			// else if ( playerPosition.x > snakeHead.x )
			// snakeHead.x++;
			// if ( playerPosition.y < snakeHead.y )
			// snakeHead.y--;
			// else if ( playerPosition.y > snakeHead.y )
			// snakeHead.y++;
			//
			// snakeIdx = (snakeIdx + 1) % snakePositions.length;
			// snakePositions[snakeIdx] = snakeHead;
			repaint();
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (end)
				return;
			// Tasteneingabe und Spielerposition ver√§ndern
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				playerPosition.y = Math.max(0, playerPosition.y - travelPixel);
				break;
			case KeyEvent.VK_DOWN:
				playerPosition.y = Math.min(HEIGHT - travelPixel, playerPosition.y + travelPixel);
				break;
			case KeyEvent.VK_LEFT:
				playerPosition.x = Math.max(0, playerPosition.x - travelPixel);
				break;
			case KeyEvent.VK_RIGHT:
				playerPosition.x = Math.min(WIDTH - travelPixel, playerPosition.x + travelPixel);
				break;
			}
			repaint();
		}

		// Timer timer = new Timer (1, new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e){
		// myTestPanel.repaint();
		// }
		// });
		// timer.start();
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("BOMBERMAN");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new bombermanGui());
		f.pack();
		f.setVisible(true);
	}
}