package bombermanGui;

//Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BombermanBorkKnebel {

	// Deklarieren eines Objekts der Klasse bombermanGui (dient als JPanel) und des JFrames
	public static bombermanGui pBombermanGui;
	public static JFrame bomberFrame;

	// Klasse bombermanGui
	public static class bombermanGui extends JPanel implements KeyEventDispatcher {
		private static final long serialVersionUID = 1L;

		// Variablen
		final public static int WIDTH = 775;
		final public static int HEIGHT = 375;

		public BufferedImage wallImage, woodImage, tntImage, background;
		public BufferedImage imageDestroyable;
		public Player player1, player2;
		public boolean rich = false;
		public boolean end = false;
		public Vector<Integer> keysPressed = new Vector<>(20);
		int counter = 0;

		// Listen
		public static ArrayList<Point> wallPositionListOutside = new ArrayList<>();
		public static ArrayList<Point> wallPositionListInside = new ArrayList<>();
		public static ArrayList<Bomb> bombList = new ArrayList<>();
		public static ArrayList<Box> boxList = new ArrayList<>();
		public static ArrayList<Box> boxRemove = new ArrayList<>();
		public static ArrayList<Explosion> explosionList = new ArrayList<>();
		public static ArrayList<Player> playerList = new ArrayList<>();
		public static ArrayList<Player> playerRemove = new ArrayList<>();

		// Konstruktor bombermanGui
		bombermanGui() {

			// Grundlegende Rahmeneinstellungen und Hinzufügen des
			// KeyEventDispatcher
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFocusable(true);
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

			// FÜllen der Listen für äußere Wände, innere Wände und Boxen
			fillWallPositionListOutside();
			fillWallPositionListInside();
			fillBoxList();

			// Zeichnen der Bilder für Wände, Boxen, Bomben und Hintergrund
			try {
				wallImage = ImageIO.read(new File("src/images/wall.png"));
				woodImage = ImageIO.read(new File("src/images/wood.png"));
				tntImage = ImageIO.read(new File("src/images/tnt.png"));

			} catch (IOException e) {
				e.printStackTrace();
			}
			background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			background.getGraphics().setColor(Color.GRAY);
			background.getGraphics().fillRect(0, 0, WIDTH, HEIGHT);
			for (Point position : wallPositionListOutside) {
				background.getGraphics().drawImage(wallImage, position.x, position.y, null);
			}
			for (Point position : wallPositionListInside) {
				background.getGraphics().drawImage(wallImage, position.x, position.y, null);
			}

			// Initialisieren der zwei Player (inklusive Abfrage der aktuellen
			// Position aus der Json-Datei)
			player1 = new Player("player1", readJson("player1").get(0), readJson("player1").get(1),
					"src/images/creeper.png", 1, keysPressed);
			player1.start();
			player2 = new Player("player2", readJson("player2").get(0), readJson("player2").get(1),
					"src/images/creeperBlue.png", 2, keysPressed);
			player2.start();
		}

		// Methoden zum befüllen der Listen (Wände, xplosionen, Boxen)
		public void fillWallPositionListOutside() {
			for (int y = 0; y < HEIGHT; y += 25) {
				wallPositionListOutside.add(new Point(0, y));
				wallPositionListOutside.add(new Point(WIDTH - 25, y));
			}
			for (int x = 0; x < WIDTH; x += 25) {
				wallPositionListOutside.add(new Point(x, 0));
				wallPositionListOutside.add(new Point(x, HEIGHT - 25));
			}
		}

		public void fillWallPositionListInside() {
			// Die Wände innerhalb des Spielfeldes
			for (int y = 0; y < HEIGHT; y += 50) {
				for (int x = 0; x < WIDTH; x += 50) {
					wallPositionListInside.add(new Point(x, y));
				}
			}
		}

		public static void fillExplosionList(int x, int y) {
			explosionList.add(new Explosion(x, y));
			explosionList.add(new Explosion(x + 25, y));
			explosionList.add(new Explosion(x - 25, y));
			explosionList.add(new Explosion(x, y + 25));
			explosionList.add(new Explosion(x, y - 25));

		}

		public void fillBoxList() {
			final Point maxBoxRange = new Point(225, 25);
			final int boxRangeLength = 300;
			final int boxRangeHeight = 325;
			final int boxAnzahl = 50;

			for (int boxCount = 0; boxCount < boxAnzahl; boxCount++) {
				int rndX = 0;
				int rndY = 0;
				Point boxKoordinates = new Point(rndX, rndY);

				do {
					int rndX1 = (int) (Math.random() * (boxRangeLength));
					rndX = maxBoxRange.x + (rndX1 - (rndX1 % 25));
					int rndY1 = (int) (Math.random() * (boxRangeHeight));
					rndY = maxBoxRange.y + (rndY1 - (rndY1 % 25));
					boxKoordinates = new Point(rndX, rndY);

				} while (existsInWallList(wallPositionListOutside, boxKoordinates)
						|| existsInBoxList(boxList, boxKoordinates)
						|| existsInWallList(wallPositionListInside, boxKoordinates));

				Box b = new Box(boxCount, boxKoordinates.x, boxKoordinates.y);
				boxList.add(b);
				boxRemove.add(b);
				System.out.println("Box#" + (boxCount + 1) + " von " + boxAnzahl + " Boxen loaded");

			}
		}

		// Hilfsmethoden, damit Boxen nicht über Wände oder andere Boxen gelegt
		// werden
		private boolean existsInWallList(ArrayList<Point> list, Point p) {
			boolean found = false;
			for (Point pTmp : list) {
				if (pTmp.equals(p)) {
					found = true;
					break;
				}
			}
			return found;
		}

		private boolean existsInBoxList(ArrayList<Box> list, Point p) {
			boolean found = false;
			for (Box b : list) {
				if (new Point(b.getX(), b.getY()).equals(p)) {
					found = true;
					break;
				}
			}
			return found;
		}

		// Methoden zum Speichern und Laden über Json-Datei
		@SuppressWarnings("unchecked")
		public void writeJson(int x, int y, int a, int b) {

			// JSONObject wird erstellt und JSONArrays mit den übergebenen
			// Werten werden erstellt
			JSONObject obj = new JSONObject();
			JSONArray player1 = new JSONArray();
			player1.add(x);
			player1.add(y);
			obj.put("player1", player1);
			JSONArray player2 = new JSONArray();
			player2.add(a);
			player2.add(b);
			obj.put("player2", player2);

			// das JSONObject wird in die .json datei geschrieben
			try {
				FileWriter file = new FileWriter("test.json");
				file.write(obj.toJSONString());
				file.flush();
				file.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public ArrayList<Integer> readJson(String playerName) {
			JSONParser parser = new JSONParser();
			JSONArray player = null;
			ArrayList<Integer> daten = new ArrayList<Integer>();
			try {

				// Pfad der .json datei
				Object obj = parser.parse(new FileReader("PlayerPositions.json"));

				JSONObject jsonObject = (JSONObject) obj;

				// dem JSONArray wird das Array mit entsprechenden player namen
				// zugewiesen
				player = (JSONArray) jsonObject.get(playerName);

				// die einzelnen werte werden in das zurückzugebende array
				// geschrieben
				daten.add((Integer.parseInt(player.get(0).toString())));
				daten.add((Integer.parseInt(player.get(1).toString())));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}

			return daten;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Hintergund zeichnen
			g.drawImage(background, 0, 0, null);

			// Waende zeichnen (die Wände sind fest, daher nur einmalig zeichen
			// --> Counter)
			if (counter == 0) {
				for (Point position : wallPositionListOutside) {
					g.drawImage(wallImage, position.x, position.y, null);
				}
				for (Point position : wallPositionListInside) {
					g.drawImage(wallImage, position.x, position.y, null);
				}
			}
			counter++;

			// Figuren zeichnen
			for (Player p : playerList) {
				g.drawImage(p.getImg(), p.getX(), p.getY(), 20, 20, null);
			}

			// Bomben zeichnen
			try {
				for (Bomb bomb : bombList) {
					g.drawImage(bomb.getBombImage(), bomb.getX(), bomb.getY(), 20, 20, null);
				}
			} catch (Exception e) {
				
			}
			
			// Boxen zeichnen
			try {
				for (Box box : boxList) {
					g.drawImage(box.getBoxImage(), box.getX(), box.getY(), 25, 25, null);
				}
			} catch (Exception e) {
				
			}

			// Explosionen zeichnen
			try {
				if (explosionList != null) {
					for (Explosion ex : explosionList) {
//						g.drawImage(ex.getFireImage(), ex.getX(), ex.getY(), 20, 20, null);
						g.drawImage(ex.getGIF(), ex.getX(), ex.getY(), 20, 20, null);
					}
				}
			} catch (Exception e) {

			}
		}

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			// Im Gegensatz zu KeyListener gibt es beim KeyEventDispatcher nur
			// eine Methode fuer
			// alle KeyEvents, daher muessen wir den Typ des Events selbst
			// unterscheiden
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				// Taste in die Liste der aktuell gedrueckten Tasten schreiben,
				// wenn sie noch nicht darin vorhanden ist (das Event
				// kann mehrmals whrend des Gedrueckthaltens ausgeloest werden).
				if (!keysPressed.contains(e.getKeyCode())) {
					keysPressed.add(e.getKeyCode());
				}
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				// Taste wieder aus der Liste entfernen
				keysPressed.remove((Integer) e.getKeyCode());
			}
			// nichts weiter mit dem KeyEvent machen
			return true;
		}

	}

	public static void main(String[] args) {
		// Anlegen des JFrames
		bomberFrame = new JFrame("BOMBERMAN --- Minecraft-Style --- by Felix Knebel & Jorma Rolf Bork");
		bomberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialisierung und Hinzufügen des Spiel-JPanels
		pBombermanGui = new bombermanGui();
		bomberFrame.getContentPane().add(pBombermanGui);

		// Initialisierung und Hinzufügen Control-JPanels
		JPanel controlPanel = new JPanel();
		bomberFrame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

		// Hinzufügen der Buttons inklusive Funktion für Laden & Speichern (Lokal + Remote)
		JButton btnSaveGame = new JButton("Save Local");
		controlPanel.add(btnSaveGame);
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pBombermanGui.writeJson(pBombermanGui.player1.getX(), pBombermanGui.player1.getY(),
						pBombermanGui.player2.getX(), pBombermanGui.player2.getY());
			}
		});
		
		JButton btnLoadGame = new JButton("Load Local");
		controlPanel.add(btnLoadGame);
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				bombermanGui.playerList.removeAll(bombermanGui.playerRemove);
				bombermanGui.boxList.removeAll(bombermanGui.boxRemove);
				pBombermanGui.fillBoxList();
				pBombermanGui.player1 = new Player("player1", pBombermanGui.readJson("player1").get(0),
						pBombermanGui.readJson("player1").get(1), "src/images/creeper.png", 1,
						pBombermanGui.keysPressed);
				pBombermanGui.player1.start();

				pBombermanGui.player2 = new Player("player2", pBombermanGui.readJson("player2").get(0),
						pBombermanGui.readJson("player2").get(1), "src/images/creeperBlue.png", 2,
						pBombermanGui.keysPressed);
				pBombermanGui.player2.start();
			}
		});

		JButton btnSaveRemote = new JButton("Save Remote");
		controlPanel.add(btnSaveRemote);
		btnSaveRemote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectToDatabase.writeToDatabase(pBombermanGui.player1.getX(), pBombermanGui.player1.getY(),
						"player1");
				ConnectToDatabase.writeToDatabase(pBombermanGui.player2.getX(), pBombermanGui.player2.getY(),
						"player2");
			}
		});

		JButton btnLoadRemote = new JButton("Load Remote");
		controlPanel.add(btnLoadRemote);
		btnLoadRemote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				bombermanGui.playerList.removeAll(bombermanGui.playerRemove);
				bombermanGui.boxList.removeAll(bombermanGui.boxRemove);
				pBombermanGui.fillBoxList();
				pBombermanGui.player1 = new Player("player1",
						ConnectToDatabase.getPositionFromDatabase("player1").get(0),
						ConnectToDatabase.getPositionFromDatabase("player1").get(1), "src/images/creeper.png", 1,
						pBombermanGui.keysPressed);
				pBombermanGui.player1.start();

				pBombermanGui.player2 = new Player("player2",
						ConnectToDatabase.getPositionFromDatabase("player2").get(0),
						ConnectToDatabase.getPositionFromDatabase("player2").get(1), "src/images/creeperBlue.png", 2,
						pBombermanGui.keysPressed);
				pBombermanGui.player2.start();
			}
		});
		
		//Frame-Size wird gesetzt
		bomberFrame.setBounds(350, 250, bombermanGui.WIDTH, bombermanGui.HEIGHT);
		bomberFrame.pack();
		bomberFrame.setVisible(true);

		//Timer Initialisieren und starten, Methoden für Bewegung (Player.update()) und Repaint
		Timer timer = new Timer(3, new ActionListener() {
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