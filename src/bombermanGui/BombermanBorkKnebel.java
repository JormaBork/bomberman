package bombermanGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class BombermanBorkKnebel {

	public static bombermanGui pBombermanGui;

	public static class bombermanGui extends JPanel implements KeyEventDispatcher {

		// COM

		private static final long serialVersionUID = 1L;
		Point playerPosition = new Point(25, 25);
		static ArrayList<Point> wallPositionList = new ArrayList<>();
		Point tntPOS = new Point(6, 6);
		// Point doorPosition = new Point(0, 5);
		Point[] snakePositions = { new Point(30, 2), null, null, null, null };
		Point wallPosition = new Point();
		BufferedImage wallImage, woodImage, tntImage, background;
		BufferedImage imageDestroyable;
		Player player1, player2;
		boolean rich = false;
		boolean end = false;
		final public static int WIDTH = 775;
		final public static int HEIGHT = 375;
		private Vector<Integer> keysPressed = new Vector<>(20);
		int counter = 0;

		bombermanGui() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFocusable(true);
			fillWallPositionList();

			player1 = new Player("player1", 25, 25, "img/creeper.png", 1, keysPressed);
			player1.start();
			player2 = new Player("player2", 75, 75, "img/creeper.png", 2, keysPressed);
			player2.start();

			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

			try {
				wallImage = ImageIO.read(new File("img/wall.png"));
				woodImage = ImageIO.read(new File("img/wood.png"));
				tntImage = ImageIO.read(new File("img/tnt.png"));

				// creeperImage =
				// ImageIO.read(BombermanBorkKnebel.class.getResourceAsStream("creeper.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			background.getGraphics().setColor(Color.GRAY);
			background.getGraphics().fillRect(0, 0, WIDTH, HEIGHT);
			for (Point position : wallPositionList) {
				background.getGraphics().drawImage(wallImage, position.x, position.y, null);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
			// Figuren zeichnen
			g.drawImage(player1.getImg(), player1.getX(), player1.getY(), 20, 20, null);
			g.drawImage(player2.getImg(), player2.getX(), player2.getY(), 20, 20, null);

			g.drawImage(woodImage, 50, 25, 25, 25, null);
			g.drawImage(tntImage, 50, 75, 25, 25, null);

			// Die Wände drumherum
			// for (int y = 0; y < HEIGHT; y += 25) {
			// g.drawImage(wallImage, 0, y, null);
			// g.drawImage(wallImage, WIDTH - 25, y, null);
			// }
			// for (int x = 0; x < WIDTH; x += 25) {
			// g.drawImage(wallImage, x, 0, null);
			// g.drawImage(wallImage, x, HEIGHT - 25, null);
			// }
			// // Die Wände innerhalb des Spielfeldes
			// for (int y = 0; y < HEIGHT; y += 50) {
			// for (int x = 0; x < WIDTH; x += 50) {
			// g.drawImage(wallImage, x, y, null);
			// }
			// }
			if (counter == 0) {
				for (Point position : wallPositionList) {
					g.drawImage(wallImage, position.x, position.y, null);
				}
			}
			counter++;
		}

		public void fillWallPositionList() {
			for (int y = 0; y < HEIGHT; y += 25) {
				wallPositionList.add(new Point(0, y));
				wallPositionList.add(new Point(WIDTH - 25, y));
			}
			for (int x = 0; x < WIDTH; x += 25) {
				wallPositionList.add(new Point(x, 0));
				wallPositionList.add(new Point(x, HEIGHT - 25));
			}
			// Die Wände innerhalb des Spielfeldes
			for (int y = 0; y < HEIGHT; y += 50) {
				for (int x = 0; x < WIDTH; x += 50) {
					wallPositionList.add(new Point(x, y));
				}
			}
		}

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			// Im Gegensatz zu KeyListener gibt es beim KeyEventDispatcher nur
			// eine Methode fr
			// alle KeyEvents, daher mssen wir den Typ des Events selbst
			// unterscheiden
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				// Taste in die Liste der aktuell gedrckten Tasten schreiben,
				// wenn sie noch nicht darin vorhanden ist (das Event
				// kann mehrmals whrend des gerckthalten ausgelst werden).
				if (!keysPressed.contains(e.getKeyCode())) {
					keysPressed.add(e.getKeyCode());
				}
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				// Taste wieder aus der Liste entfernen; manuelles boxen,
				// damit die richtige remove-Funktion aufgerufen wird.
				keysPressed.remove((Integer) e.getKeyCode());
			}

			return true; // nichts weiter mit dem KeyEvent machen
		}

		// @Override
		// public void keyTyped(KeyEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void keyReleased(KeyEvent e) {
		// // TODO Auto-generated method stub
		//
		// }

	}

	public static void main(String[] args) {
		JFrame f = new JFrame("BOMBERMAN");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Statt
		// EXIT_ON_CLOSE unseren eigenen WindowListener verwenden
		// f.addWindowListener(new WindowAdapter() {
		// @Override
		// public void windowClosing(WindowEvent e) {
		// KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(pBombermanGui);
		// //KeyStrokes2.this.dispose();
		// System.exit(0);
		// }
		// });

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

		Timer timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pBombermanGui.player1.update();
				pBombermanGui.player2.update();
				pBombermanGui.repaint();
			}
		});
		timer.start();

	}
}