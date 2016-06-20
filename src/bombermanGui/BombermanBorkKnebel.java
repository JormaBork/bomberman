package bombermanGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;



public class BombermanBorkKnebel {
	
	public static bombermanGui pBombermanGui;
	
	public static class bombermanGui extends JPanel {

//		COM
		
		private static final long serialVersionUID = 1L;
		Point playerPosition = new Point(25, 25);
		ArrayList<Point> wallPositionList;
		Point tntPOS = new Point(6, 6);
//		Point doorPosition = new Point(0, 5);
		Point[] snakePositions = { new Point(30, 2), null, null, null, null };
		Point wallPosition = new Point();
		BufferedImage wallImage, woodImage, tntImage;
		BufferedImage imageDestroyable;
		public static Player player1, player2;
		boolean rich = false;
		boolean end = false;
		public final int WIDTH = 775, HEIGHT = 375;
		public Rectangle rect;
		private static int startY, startX = 0;
		public static int anzahlReihe, anzahlSpalte;
		private int spalte = 0;
		public static int  dimension = 25;
		public static ArrayList<Tile> tiles = new ArrayList<Tile>();
		public static ArrayList<Tile> tileList = new ArrayList<Tile>();
		
		
		
		bombermanGui() {
//			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			erstelleSpielfeld(WIDTH, HEIGHT, true);
			setFocusable(true);
			
			player1 = new Player("player1", 25, 25, "img/creeper.png", 1);
			player1.start();
			player2 = new Player("player2", 75, 75, "img/creeper.png", 2);
			player2.start();

			addKeyListener(player1);
			addKeyListener(player2);
			
			
			try {
				wallImage = ImageIO.read(new File("img/wall.png"));
				woodImage = ImageIO.read(new File("img/wood.png"));
				tntImage = ImageIO.read(new File("img/tnt.png"));

//				creeperImage = ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("creeper.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		private void erstelleSpielfeld(int waagerecht, int senkrecht, boolean load){
			startY = 0;
			Tile tile;
			anzahlReihe = waagerecht;
			anzahlSpalte = senkrecht;
			
			for (int i = 1; i <= anzahlReihe; i++) {
				if (spalte == 0 || spalte == anzahlSpalte - 1 || i == 1
						|| i == anzahlReihe || spalte % 2 == 0 && i % 2 != 0) {
					tile = new Tile(dimension, false);
					tile.setBounds(startX, startY, dimension, dimension);
					tiles.add(tile);
					add(tile);
				} else {
//					tile = new Tile(dimension);
//					tile.setBounds(startX, startY, dimension, dimension);
//					if (!load) {
//						double random = Math.random();
//						if (!(spalte <= 2 && i <= 3)) {
//							if (!(spalte >= anzahlSpalte - 3 && i >= anzahlReihe - 2)) {
//								if (random >= 0.5) {
//									kiste = new Kiste();
//									kiste.setBounds(startX, startY, dimension,
//											dimension);
//									kistenListe.add(kiste);
//									tile.add(kiste);
//								}
//							}
//						}
//					}
//					tileList.add(tile);
//					add(tile);
				}
				if (i != 0)
					startX += dimension;
				if (i == anzahlReihe) {
					spalte++;
					startY += dimension;
					startX = 0;
					i = 0;
					if (spalte == anzahlSpalte)
						return;
				}
			}
		}

		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(player1.getImg(), player1.getX(), player1.getY(), 20, 20, null);
			g.drawImage(player2.getImg(), player2.getX(), player2.getY(), 20, 20, null);
			
			Graphics2D g2d = (Graphics2D) g;
			if (tiles.size() != 0) {
				for (int i = 0; i < tiles.size(); i++)
					g2d.drawImage(tiles.get(i).getImage(), tiles.get(i)
							.getLocation().x, tiles.get(i).getLocation().y, this);
			}
			if (tiles.size() != 0) {
				for (int i = 0; i < tiles.size(); i++)
					g2d.drawImage(tiles.get(i).getImageStein(), tiles.get(i)
							.getLocation().x, tiles.get(i).getLocation().y, this);
			}
			if (tileList.size() != 0) {
				for (int i = 0; i < tileList.size(); i++)
					g2d.drawImage(tileList.get(i).getImageKachel(), tileList
							.get(i).getLocation().x, tileList.get(i)
							.getLocation().y, this);
			}
//			if (kistenListe.size() != 0) {
//				for (int i = 0; i < kistenListe.size(); i++)
//					g2d.drawImage(kistenListe.get(i).getImage(), kistenListe.get(i)
//							.getLocation().x, kistenListe.get(i).getLocation().y,
//							this);
//			}

			// Figuren zeichnen
//			g.drawImage(woodImage, 50, 25, 25, 25, null);
//			g.drawImage(tntImage, 50, 75, 25, 25, null);

			// Die Wände drumherum
//			for (int y = 0; y < HEIGHT; y += 25) {
//				g.drawImage(wallImage, 0, y, null);
//				g.drawImage(wallImage, WIDTH - 25, y, null);
//				wallPositionList.add(new Point(0, y));
//				wallPositionList.add(new Point(WIDTH - 25, y));
//			}
//			for (int x = 0; x < WIDTH; x += 25) {
//				g.drawImage(wallImage, x, 0, null);
//				g.drawImage(wallImage, x, HEIGHT - 25, null);
//				wallPositionList.add(new Point(x, 0));
//				wallPositionList.add(new Point(x, HEIGHT - 25));
//			}
//			// Die Wände innerhalb des Spielfeldes
//			for (int y = 0; y < HEIGHT; y += 50) {
//				for (int x = 0; x < WIDTH; x += 50) {
//					g.drawImage(wallImage, x, y, null);
//					wallPositionList.add(new Point(x, y));
//				}
//			}
//			for (int y = 0; y < HEIGHT; y++) {
//				for (int x = 0; x < WIDTH; x++) {
//					char c = ' ';
//					Point p = new Point(x, y);
//
//					if (tntPOS.equals(p))
////						c = '$';
//
//					// g.hitclip für collisionsabfrage möglich?
//					if (g.hitClip(50, 125, 25, 25))
//
//						if (Arrays.asList(snakePositions).contains(p))
////							c = 'S';
//					if (!Character.isWhitespace(c))
//						g.drawString(Character.toString(c), x * 10, y * 10);
//				}
//			}
			// Status aktualisieren
//			if (rich && playerPosition.equals(doorPosition)) {
//				System.out.println("Gewonnen!");
//				end = true;
//				return;
//			}
//			if (Arrays.asList(snakePositions).contains(playerPosition)) {
//				System.out.println("ZZZZZZZ. Die Schlage hat dich!");
//				end = true;
//				return;
//			}
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