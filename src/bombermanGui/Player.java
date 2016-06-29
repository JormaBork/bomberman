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

	private String name;
	private int x, y;
	private String img;
	private BufferedImage playerImage;
	private final boolean destroyable = true;
	private JLabel playerLabel;
	private Graphics g;
	private ImageIcon playerIcon;
	boolean end = false;
	int stepSize = 1;
	int whichPlayer;
	Vector<Integer> keysPressed;

	public Player(String name, int x, int y, String img, int whichPlayer, Vector<Integer> keysPressed) {
		super();
		this.name = name;
		this.y = y;
		this.x = x;

		this.img = img;
		this.whichPlayer = whichPlayer;
		this.keysPressed = keysPressed;

		try {
			playerImage = ImageIO.read(new File(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

	}

	public BufferedImage getImg() {
		return playerImage;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ImageIcon getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(ImageIcon playerIcon) {
		this.playerIcon = playerIcon;
	}

	public JLabel getPlayerLabel() {
		return playerLabel;
	}

	public void setPlayerLabel(JLabel playerLabel) {
		this.playerLabel = playerLabel;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void drawBomb() {
		Bomb bombe = new Bomb(this.x, this.y);
		new Thread(bombe).start();
	}

	public Point moveUp() {
		// boolean collission = true;
		// for (Point p :
		// BombermanBorkKnebel.bombermanGui.wallPositionListInside) {
		// if (this.x + 20 + stepSize <= p.x && this.x >= p.x - 25) {
		// if ((this.x >= p.x) && (this.x <= p.x + 25) && (this.y - stepSize) <=
		// (p.y + 25)
		// || (this.y + stepSize) >= p.y) {
		// collission = false;
		// }
		// }
		// }
		// return collission;
		Point newPosition = new Point(this.x, this.y - stepSize);
		return newPosition;
	}

	public boolean moveHorizontal() {
		boolean collission = true;
		for (Point p : BombermanBorkKnebel.bombermanGui.wallPositionListInside) {
			if ((this.y >= p.y) && (this.y <= p.y + 25) && (this.x + stepSize) >= (p.x + 25)
					|| this.x + stepSize >= p.x) {
				collission = false;
			}
		}
		return collission;
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

				Point newPosition = new Point(this.x, this.y);

				if (key == KeyEvent.VK_UP) {
					newPosition = new Point(this.x, this.y - stepSize);
					//// if (this.moveVertical() && (this.y - stepSize) >=
					//// (BombermanBorkKnebel.pBombermanGui.HEIGHT + 25)) {
					// this.setY(Math.max(25, this.getY() - stepSize));
					// }
				}
				if (key == KeyEvent.VK_DOWN) {
					newPosition = new Point(this.x, this.y + stepSize);
					// if (this.moveVertical()
					// && ((this.y + 20 + stepSize) <=
					// (BombermanBorkKnebel.pBombermanGui.HEIGHT - 25))) {
					// this.setY(Math.min(BombermanBorkKnebel.pBombermanGui.HEIGHT
					// - 45, this.getY() + stepSize));
					// }
				}
				if (key == KeyEvent.VK_LEFT) {
					newPosition = new Point(this.x - stepSize, this.y);
					// if (moveHorizontal()) {
					// this.setX(Math.max(25, this.getX() - stepSize));
					// // }
				}
				if (key == KeyEvent.VK_RIGHT) {
					newPosition = new Point(this.x + stepSize, this.y);
					// if (moveHorizontal()) {
					// this.setX(Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH
					// - 45, this.getX() + stepSize));
					// // }
				}

				ArrayList<Point> allBoxes = new ArrayList<>();
				allBoxes.addAll(BombermanBorkKnebel.bombermanGui.wallPositionListInside);
				for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
					allBoxes.add(new Point(b.x, b.y));
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
							this.x = newPosition.x;
							this.y = newPosition.y;
						}
					}
				}

				if (key == KeyEvent.VK_B) {
					pressKey = key;
					this.drawBomb();
					System.out.println("drawbomb Test");
				}
			}
		} else if (this.whichPlayer == 2) {
			for (int key : keysPressed) {

				Point newPosition = new Point(this.x, this.y);
				if (key == KeyEvent.VK_W) {
					newPosition = new Point(this.x, this.y - stepSize);
				}
				if (key == KeyEvent.VK_S) {
					newPosition = new Point(this.x, this.y + stepSize);
				}
				if (key == KeyEvent.VK_A) {
					newPosition = new Point(this.x - stepSize, this.y);
				}
				if (key == KeyEvent.VK_D) {
					newPosition = new Point(this.x + stepSize, this.y);
				}
				ArrayList<Point> allBoxes = new ArrayList<>();
				allBoxes.addAll(BombermanBorkKnebel.bombermanGui.wallPositionListInside);
				for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
					allBoxes.add(new Point(b.x, b.y));
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
							this.x = newPosition.x;
							this.y = newPosition.y;
						}
					}
				}
				if (key == KeyEvent.VK_R) {
					pressKey = key;
					this.drawBomb();
					System.out.println("drawbomb Test");
				}
			}

		}
		if (pressKey > 0) {
			keysPressed.remove((Integer) pressKey);
		}
	}
}
