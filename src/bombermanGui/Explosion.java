package bombermanGui;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import bombermanGui.BombermanBorkKnebel.bombermanGui;

public class Explosion implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x, y;
	BufferedImage fireImage;
	
	public Explosion(int x, int y){
		
		try {
			fireImage = ImageIO.read(new File("src/images/Fire_animated.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
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
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BombermanBorkKnebel.bombermanGui.explosionList.removeAll(bombermanGui.explosionList);
		System.out.println("fire test");
	}


}
