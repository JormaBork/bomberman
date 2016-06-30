package bombermanGui;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

public class Bomb implements Runnable {

	int bombNumber, x, y;
	BufferedImage bombImage;

	public Bomb(int x, int y) {

		try {
			bombImage = ImageIO.read(new File("src/images/tnt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		BombermanBorkKnebel.bombermanGui.bombList.add(this);
	}

	public int getBombNumber() {
		return bombNumber;
	}

	public void setBombNumber(int bombNumber) {
		this.bombNumber = bombNumber;
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

	public BufferedImage getBombImage() {
		return bombImage;
	}

	public void setBombImage(BufferedImage bombImage) {
		this.bombImage = bombImage;
	}

	public void removeBox() {

		int left = this.x - 25;
		int right = this.x + 45;
		int top = this.y - 25;
		int bottom = this.y + 45;

		ArrayList<Box> toDelete = new ArrayList<>();
		for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
			if (!(b.x + 25 < left || b.x > right)) {
				if (!(b.y + 25 < top || b.y > bottom)) {
					toDelete.add(b);
				}
			}
		}
		BombermanBorkKnebel.bombermanGui.boxList.removeAll(toDelete);
	}

	public void removePlayer() {

		int left = this.x - 25;
		int right = this.x + 45;
		int top = this.y - 25;
		int bottom = this.y + 45;

		ArrayList<Player> pToDelete = new ArrayList<>();
		for (Player p : BombermanBorkKnebel.bombermanGui.playerList) {
			if (!(p.x + 25 < left || p.x > right)) {
				if (!(p.y + 25 < top || p.y > bottom)) {
					pToDelete.add(p);
					System.out.println("Game Over");
				}
			}
		}
		BombermanBorkKnebel.bombermanGui.playerList.removeAll(pToDelete);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Explosion ex = new Explosion(this.x, this.y);
		new Thread(ex).start();
		BombermanBorkKnebel.bombermanGui.fillExplosionList(ex.x, ex.y);

		this.removeBox();
		this.removePlayer();
		BombermanBorkKnebel.bombermanGui.bombList.remove(this);
	}
}