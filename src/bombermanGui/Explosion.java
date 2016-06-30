package bombermanGui;

//Imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import bombermanGui.BombermanBorkKnebel.bombermanGui;

public class Explosion implements Runnable {

	//Variablen
	private int x, y;
	private BufferedImage fireImage;

	//Konstruktor
	public Explosion(int x, int y) {

		try {
			fireImage = ImageIO.read(new File("src/images/Fire_animated.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		BombermanBorkKnebel.bombermanGui.explosionList.add(this);
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

	public BufferedImage getFireImage() {
		return fireImage;
	}

	public void setFireImage(BufferedImage fireImage) {
		this.fireImage = fireImage;
	}

	@Override
	public void run() {
		try {
			// Thread wartet eine Sekunde
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//alle Explosionen werden wieder aus der Liste entfernt
		BombermanBorkKnebel.bombermanGui.explosionList.removeAll(bombermanGui.explosionList);
	}

}
