package bombermanGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
		public static int anzahlReihe, anzahlSpalte;
		public static int  dimension = 25;
		
		
		bombermanGui() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			setFocusable(true);
			
			

			player1 = new Player("player1", readJson("player1").get(0), readJson("player1").get(1), "img/creeper.png", 1);
			player1.start();
			player2 = new Player("player2", readJson("player2").get(0), readJson("player2").get(1), "img/creeper.png", 2);
			player2.start();

//			writeJson(player1.getX(), player1.getY(),player2.getX(), player2.getY());
			
			
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
			
			g.drawImage(player1.getImg(), player1.getX(), player1.getY(), 20, 20, null);
			g.drawImage(player2.getImg(), player2.getX(), player2.getY(), 20, 20, null);

			// Figuren zeichnen
			g.drawImage(woodImage, 50, 25, 25, 25, null);
			g.drawImage(tntImage, 50, 75, 25, 25, null);

			// Die Wände drumherum
			for (int y = 0; y < HEIGHT; y += 25) {
				g.drawImage(wallImage, 0, y, null);
				g.drawImage(wallImage, WIDTH - 25, y, null);
//				wallPositionList.add(new Point(0, y));
//				wallPositionList.add(new Point(WIDTH - 25, y));
			}
			for (int x = 0; x < WIDTH; x += 25) {
				g.drawImage(wallImage, x, 0, null);
				g.drawImage(wallImage, x, HEIGHT - 25, null);
//				wallPositionList.add(new Point(x, 0));
//				wallPositionList.add(new Point(x, HEIGHT - 25));
			}
			// Die Wände innerhalb des Spielfeldes
			for (int y = 0; y < HEIGHT; y += 50) {
				for (int x = 0; x < WIDTH; x += 50) {
					g.drawImage(wallImage, x, y, null);
//					wallPositionList.add(new Point(x, y));
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