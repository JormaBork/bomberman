package bombermanGui;

import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bombermanGui.BombermanBorkKnebel.bombermanGui;

public class Player extends Thread {

	//Variablen
	private BufferedImage playerImage;
	private Graphics g;
	private Vector<Integer> keysPressed;
	public int playerXPos, playerYPos, whichPlayer, stepSize = 1;
	
	

	public Player(String name, int x, int y, String img, int whichPlayer, Vector<Integer> keysPressed) {
		super();
		this.playerYPos = y;
		this.playerXPos = x;
		this.whichPlayer = whichPlayer;
		this.keysPressed = keysPressed;

		try {
			playerImage = ImageIO.read(new File(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BombermanBorkKnebel.bombermanGui.playerList.add(this);
	}

	@Override
	public void run() {

	}

	public BufferedImage getImg() {
		return playerImage;
	}

	public int getX() {
		return playerXPos;
	}

	public void setX(int x) {
		this.playerXPos = x;
	}

	public int getY() {
		return playerYPos;
	}

	public void setY(int y) {
		this.playerYPos = y;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public void drawBomb() {
		Bomb bombe = new Bomb(this.playerXPos, this.playerYPos);
		new Thread(bombe).start();
	}

	/*
	 * Die Update-Funktion lässt den Spielstand um eine bestimmte Zeitspanne
	 * (period) voranschreiten, wobei Spielregeln/-logik implementiert und
	 * Tastatureingaben verarbeitet werden.
	 */
	public void update() {

		// über alle gedrückten Tasten iterieren
		int pressKey = 0;

		if (this.whichPlayer == 1) {
			for (int key : keysPressed) {

				Point newPosition = new Point(this.playerXPos, this.playerYPos);

				if (key == KeyEvent.VK_UP) {
					newPosition = new Point(this.playerXPos, this.playerYPos - stepSize);
				}
				if (key == KeyEvent.VK_DOWN) {
					newPosition = new Point(this.playerXPos, this.playerYPos + stepSize);
				}
				if (key == KeyEvent.VK_LEFT) {
					newPosition = new Point(this.playerXPos - stepSize, this.playerYPos);
				}
				if (key == KeyEvent.VK_RIGHT) {
					newPosition = new Point(this.playerXPos + stepSize, this.playerYPos);
				}

				ArrayList<Point> allBoxes = new ArrayList<>();
				allBoxes.addAll(BombermanBorkKnebel.bombermanGui.wallPositionListInside);
				for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
					allBoxes.add(new Point(b.x, b.y));
				for(Player p: BombermanBorkKnebel.bombermanGui.playerList){
					if(p!=this){
						allBoxes.add(new Point(p.playerXPos,p.playerYPos));
					}
				}
				}

				if (newPosition.x > 25 && newPosition.x + 20 < BombermanBorkKnebel.pBombermanGui.WIDTH - 25) {
					if (newPosition.y > 25 && newPosition.y + 20 < BombermanBorkKnebel.pBombermanGui.HEIGHT - 25) {
						boolean found = false;
						for (Point p : allBoxes) {
							if (!(newPosition.x + 20 < p.x || newPosition.x > p.x + 25)) {
								if (!(newPosition.y + 20 < p.y || newPosition.y > p.y + 25)) {
									found = true;
									break;
								}
							}
						}
						if (!found) {
							this.playerXPos = newPosition.x;
							this.playerYPos = newPosition.y;
						}
					}
				}

				if (key == KeyEvent.VK_B) {
					pressKey = key;
					this.drawBomb();
				}
			}
		} else if (this.whichPlayer == 2) {
			for (int key : keysPressed) {

				Point newPosition = new Point(this.playerXPos, this.playerYPos);
				if (key == KeyEvent.VK_W) {
					newPosition = new Point(this.playerXPos, this.playerYPos - stepSize);
				}
				if (key == KeyEvent.VK_S) {
					newPosition = new Point(this.playerXPos, this.playerYPos + stepSize);
				}
				if (key == KeyEvent.VK_A) {
					newPosition = new Point(this.playerXPos - stepSize, this.playerYPos);
				}
				if (key == KeyEvent.VK_D) {
					newPosition = new Point(this.playerXPos + stepSize, this.playerYPos);
				}
				ArrayList<Point> allBoxes = new ArrayList<>();
				allBoxes.addAll(BombermanBorkKnebel.bombermanGui.wallPositionListInside);
				for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
					allBoxes.add(new Point(b.x, b.y));
				for(Player p: BombermanBorkKnebel.bombermanGui.playerList){
					if(p!=this){
						allBoxes.add(new Point(p.playerXPos,p.playerYPos));
					}
				}
				}


				if (newPosition.x > 25 && newPosition.x + 20 < BombermanBorkKnebel.pBombermanGui.WIDTH - 25) {
					if (newPosition.y > 25 && newPosition.y + 20 < BombermanBorkKnebel.pBombermanGui.HEIGHT - 25) {
						boolean found = false;
						for (Point p : allBoxes) {
							if (!(newPosition.x + 20 < p.x || newPosition.x > p.x + 25)) {
								if (!(newPosition.y + 20 < p.y || newPosition.y > p.y + 25)) {
									found = true;
									break;
								}
							}
						}
						if (!found) {
							this.playerXPos = newPosition.x;
							this.playerYPos = newPosition.y;
						}
					}
				}
				if (key == KeyEvent.VK_R) {
					pressKey = key;
					this.drawBomb();
				}
			}

		}
		if (pressKey > 0) {
			keysPressed.remove((Integer) pressKey);
		}
	}
}
