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
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BombermanBorkKnebel {

	public static bombermanGui pBombermanGui;

	public static class bombermanGui extends JPanel implements KeyEventDispatcher {

		// COM

		private static final long serialVersionUID = 1L;
		Point playerPosition = new Point(25, 25);
		public static ArrayList<Point> wallPositionList = new ArrayList<>();
		public static ArrayList<Bomb> bombList = new ArrayList<>();
		public static ArrayList<Box> boxList = new ArrayList<>();
		Point tntPOS = new Point(6, 6);
		// Point doorPosition = new Point(0, 5);
		Point[] snakePositions = { new Point(30, 2), null, null, null, null };
		Point wallPosition = new Point();
		BufferedImage wallImage, woodImage, tntImage, background;
		BufferedImage imageDestroyable;
		Player player1, player2;
		Bomb bomb1;
		Box box1;
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

			player1 = new Player("player1", readJson("player1").get(0), readJson("player1").get(1), "src/images/creeper.png", 1, keysPressed);
			player1.start();
			player2 = new Player("player2", readJson("player2").get(0), readJson("player2").get(1), "src/images/creeperBlue.png", 2, keysPressed);
			player2.start();
			
			bomb1 = new Bomb(100,100);
			new Thread(bomb1).start();
			
			box1 = new Box (1,50,25);
			new Thread(box1).start();

//			writeJson(25,25,75,75);
			
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

			try {
				wallImage = ImageIO.read(new File("src/images/wall.png"));
				woodImage = ImageIO.read(new File("src/images/wood.png"));
				tntImage = ImageIO.read(new File("src/images/tnt.png"));

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

		@SuppressWarnings("unchecked")
		public void writeJson(int x, int y, int a, int b){
	        
			// JSONObject wird erstellt und JSONArrays mit den übergebenen Werten werden erstellt
	        JSONObject obj = new JSONObject();
	    	JSONArray player1 = new JSONArray();
	    	player1.add(x);
	    	player1.add(y);
	    	obj.put("player1", player1);
	    	JSONArray player2 =  new JSONArray();
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
		
		public ArrayList<Integer> readJson(String playerName){
			JSONParser parser = new JSONParser();
			JSONArray player = null;
			ArrayList<Integer> daten = new ArrayList<Integer>();
			try {
				
				//Pfad der .json datei
				Object obj = parser.parse(new FileReader("test.json"));

				JSONObject jsonObject = (JSONObject) obj;


				// dem JSONArray wird das Array mit entsprechenden player namen zugewiesen
				player = (JSONArray) jsonObject.get(playerName);

				// die einzelnen werte werden in das zurückzugebende array geschrieben
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
			g.drawImage(background, 0, 0, null);
			// Figuren zeichnen
			g.drawImage(player1.getImg(), player1.getX(), player1.getY(), 20, 20, null);
			g.drawImage(player2.getImg(), player2.getX(), player2.getY(), 20, 20, null);

			//g.drawImage(woodImage, 50, 25, 25, 25, null);
			
			for(Bomb bomb : bombList){
			g.drawImage(bomb.getBombImage(), bomb.x, bomb.y, 20, 20, null);
			}
			
			for(Box box : boxList){
				g.drawImage(box.getBoxImage(), 50, 25, 25, 25, null);
				}
			
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