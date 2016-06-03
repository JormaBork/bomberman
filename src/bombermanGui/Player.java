package bombermanGui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Player extends JComponent {
	
	private String name;
	private int x,y;
	private BufferedImage img;
	private final boolean destroyable = true;
	private JLabel playerLabel;
	private Graphics g;
//	private String playerImage="";
	
	
	public Player(String name, int x,int y, String img) {
		super();
		this.name = name;
		this.y = y;
		this.x = x;
		
		try {
			BufferedImage playerImage = ImageIO.read(new File(img));
//            ImageIcon playerIcon = new ImageIcon(playerImage);
            
			this.img = playerImage;
			this.playerLabel = new JLabel();
			playerLabel.paintComponents(g);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override 
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.img, x, y, null);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
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
}
