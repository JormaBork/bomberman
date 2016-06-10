package bombermanGui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BombermanBorkKnebel {
	
	public static bombermanGui pBombermanGui;
	
	public static class bombermanGui extends JPanel {

//		COM
		
		private static final long serialVersionUID = 1L;
		Point playerPosition = new Point(25, 25);
		Point tntPOS = new Point(6, 6);
		Point doorPosition = new Point(0, 5);
		Point[] snakePositions = { new Point(30, 2), null, null, null, null };
		Point wallPosition = new Point();
		BufferedImage wallImage, woodImage, tntImage;
		BufferedImage imageDestroyable;
		Player player1, player2;
		int travelPixel = 5;
		boolean rich = false;
		boolean end = false;
		public final int WIDTH = 775, HEIGHT = 375;

		bombermanGui() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFocusable(true);
			
			player1 = new Player("player1", 25, 25, "img/creeper.png");
			player1.start();
			//player2 = new Player("player2", 75, 75, "img/creeper.png");
			//player2.start();

			addKeyListener(player1);
			
			try {
				wallImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("wall.png"));
				woodImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("wood.png"));
				tntImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("tnt.png"));

//				creeperImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("creeper.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(player1.getImg(), player1.getX(), player1.getY(), 20, 20, null);
			g.drawImage(player2.getImg(), player2.getX(), player2.getY(), 20, 20, null);

			// Figuren zeichnen
			g.drawImage(woodImage, 50, 25, 25, 25, null);
			g.drawImage(tntImage, 50, 75, 25, 25, null);

			// Die Wände drumherum
			for (int y = 0; y < HEIGHT; y += 25) {
				g.drawImage(wallImage, 0, y, null);
				g.drawImage(wallImage, WIDTH - 25, y, null);
			}
			for (int x = 0; x < WIDTH; x += 25) {
				g.drawImage(wallImage, x, 0, null);
				g.drawImage(wallImage, x, HEIGHT - 25, null);
			}
			// Die Wände innerhalb des Spielfeldes
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

					// g.hitclip für collisionsabfrage möglich?
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

//		@Override
//		public void keyPressed(KeyEvent e) {
//			if (end)
//				return;
//			// Tasteneingabe und Spielerposition verÃ¤ndern
//			switch (e.getKeyCode()) {
//			case KeyEvent.VK_UP:
//				player1.setY ( Math.max(0, player1.getY()- travelPixel) );
//				break;
//			case KeyEvent.VK_DOWN:
//				player1.setY ( Math.min(HEIGHT - travelPixel, player1.getY() + travelPixel) );
//				break;
//			case KeyEvent.VK_LEFT:
//				player1.setX ( Math.max(0, player1.getX() - travelPixel) );
//				break;
//			case KeyEvent.VK_RIGHT:
//				player1.setX ( Math.min(WIDTH - travelPixel, player1.getX() + travelPixel) );
//				break;
//			case KeyEvent.VK_W:
//				player2.setY ( Math.max(0, player2.getY()- travelPixel) );
//				break;
//			case KeyEvent.VK_A:
//				player2.setY ( Math.min(HEIGHT - travelPixel, player2.getY() + travelPixel) );
//				break;
//			case KeyEvent.VK_S:
//				player2.setX ( Math.max(0, player2.getX() - travelPixel) );
//				break;
//			case KeyEvent.VK_D:
//				player2.setX ( Math.min(WIDTH - travelPixel, player2.getX() + travelPixel) );
//				break;
//			}
//		}
//
//		@Override
//		public void keyTyped(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void keyReleased(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}

	}

	public static void main(String[] args) {
		JFrame f = new JFrame("BOMBERMAN");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pBombermanGui = new bombermanGui();
		f.getContentPane().add(pBombermanGui);
		
		JPanel controlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		f.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStartGame.setHorizontalAlignment(SwingConstants.LEFT);
		controlPanel.add(btnStartGame);
		f.pack();
		f.setVisible(true);
		
		 Timer timer = new Timer (1, new ActionListener() {
		 @Override
		 public void actionPerformed(ActionEvent e){
			 pBombermanGui.repaint();
		 }
		 });
		 timer.start();		
		
	}
}