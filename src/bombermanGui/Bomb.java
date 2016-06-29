package bombermanGui;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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

	public void removeBox(){
		
		ArrayList<Point> boxes = new ArrayList<>();
		
		for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
			boxes.add(new Point(b.x, b.y));
		}
		
		int left = this.x-25;
		int right = this.x + 45;
		int top = this.y-25;
		int bottom = this.y + 45;
		
		ArrayList<Box> toDelete = new ArrayList<>();
		for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
			if(!( b.x+25<left || b.x>right ) ){
				if(!( b.y+25 <top || b.y>bottom )){
					toDelete.add(b);
				}
			}
		}
		BombermanBorkKnebel.bombermanGui.boxList.removeAll(toDelete);
//		for (Point p : boxes) {
//			if (this.y>=p.y+25 && (this.y - 15) <= (p.y + 25) && (this.x >= p.x && (this.x+15) <= (p.x + 25))) {
//				BombermanBorkKnebel.bombermanGui.boxList.remove(boxes.indexOf(p));
//			}
//			if (this.y<p.y && (this.y + 15) >= (p.y) && (this.x >= p.x || this.x <= (p.x + 25))) {
//				BombermanBorkKnebel.bombermanGui.boxList.remove(boxes.indexOf(p));
//			}
//			if (this.x>p.x && (this.x - 15) <= (p.x+25) && (this.y >= p.y || this.y <= (p.y + 25))) {
//				BombermanBorkKnebel.bombermanGui.boxList.remove(boxes.indexOf(p));
//			}
//			if (this.x<p.x && (this.x + 15) >= (p.x) && (this.y>= p.y || this.y <= (p.y + 25))) {
//				BombermanBorkKnebel.bombermanGui.boxList.remove(boxes.indexOf(p));
//			}
//		}
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.removeBox();
		BombermanBorkKnebel.bombermanGui.bombList.remove(this);
		// System.out.println("test");
	}
}