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

	/*
	 * Die Update-Funktion lässt den Spielstand um eine bestimmte Zeitspanne
	 * (period) voranschreiten, wobei Spielregeln/-logik implementiert und
	 * Tastatureingaben verarbeitet werden.
	 */
	public void update() {

	//	for (Point point : bombermanGui.wallPositionList) {
			if ((this.x >= (25)) && (this.x <= bombermanGui.WIDTH) && (this.y >= (25))
					&& (this.y <= bombermanGui.HEIGHT)) {

				// über alle gedrückten Tasten iterieren
				if (this.whichPlayer == 1) {
					for (int key : keysPressed) {
						if (key == KeyEvent.VK_UP) {
							this.setY(Math.max(0, this.getY() - stepSize));
						}
						if (key == KeyEvent.VK_DOWN) {
							this.setY(Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize,
									this.getY() + stepSize));
						}
						if (key == KeyEvent.VK_LEFT) {
							this.setX(Math.max(0, this.getX() - stepSize));
						}
						if (key == KeyEvent.VK_RIGHT) {
							this.setX(Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize,
									this.getX() + stepSize));
						}
					}
				} else if (this.whichPlayer == 2) {
					for (int key : keysPressed) {
						if (key == KeyEvent.VK_W) {
							this.setY(Math.max(0, this.getY() - stepSize));
						}
						if (key == KeyEvent.VK_S) {
							this.setY(Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize,
									this.getY() + stepSize));
						}
						if (key == KeyEvent.VK_A) {
							this.setX(Math.max(0, this.getX() - stepSize));
						}
						if (key == KeyEvent.VK_D) {
							this.setX(Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize,
									this.getX() + stepSize));
						}
					}
				}
			}
		//}
	}
}
