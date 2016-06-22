package bombermanGui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bomb implements Runnable {

	
	int bombNumber, x, y;
	BufferedImage bombImage;

	public Bomb(int b, int x, int y) {
		
		try {
			bombImage = ImageIO.read(new File("img/tnt.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		this.bombNumber = b;
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

	/*
	 * Bei Eintritt auf Wasserrose wird Timer gestartet, nach 3 Sekunden
	 * verschwindet Sie und Spieler stirbt
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		BombermanBorkKnebel.bombermanGui.bombList.remove(this);
	}
}