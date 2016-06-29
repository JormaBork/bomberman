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
	public Image image;
	public ImageIcon fire;
	BufferedImage fireImage;
	Timer timer;
	
	public Explosion(int x, int y){
		
		try {
			fireImage = ImageIO.read(new File("src/images/Fire_animated.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		
//		BombermanBorkKnebel.bombermanGui.explosionList.add(this);
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public ImageIcon getFire() {
		return fire;
	}

	public void setFire(ImageIcon fire) {
		this.fire = fire;
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
