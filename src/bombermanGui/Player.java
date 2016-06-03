package bombermanGui;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Player {
	
	private String name;
	private Point xy;
	private BufferedImage img;
	
	
	public Player(String name, Point xy, BufferedImage img) {
		super();
		this.name = name;
		this.xy = xy;
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Point getXy() {
		return xy;
	}
	public void setXy(Point xy) {
		this.xy = xy;
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	

}
