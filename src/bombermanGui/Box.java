package bombermanGui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Box implements Runnable {
	int boxNumber, x, y;
	BufferedImage boxImage;

	public Box(int b, int x, int y) {
		
		try {
			boxImage = ImageIO.read(new File("img/wood.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		this.boxNumber = b;
		this.x = x;
		this.y = y;
		BombermanBorkKnebel.bombermanGui.boxList.add(this);
	}

	public int getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
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

	public BufferedImage getBoxImage() {
		return boxImage;
	}

	public void setBoxImage(BufferedImage boxImage) {
		this.boxImage = boxImage;
	}

	/*
	 * Bei Eintritt auf Wasserrose wird Timer gestartet, nach 3 Sekunden
	 * verschwindet Sie und Spieler stirbt
	 */
	@Override
	public void run() {
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		BombermanBorkKnebel.bombermanGui.bombList.remove(this);
	}
}
