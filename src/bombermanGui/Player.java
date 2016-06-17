package bombermanGui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Player extends Thread implements KeyListener {
	
	private String name;
	private int x,y;
	private String img;
	private BufferedImage playerImage;
	private final boolean destroyable = true;
	private JLabel playerLabel;
	private Graphics g;
	private ImageIcon playerIcon;
	boolean end = false;
	int stepSize = 5;
	int whichPlayer;
//	private String playerImage="";
	
	
	public Player(String name, int x,int y, String img, int whichPlayer) {
		super();
		this.name = name;
		this.y = y;
		this.x = x;
		
		this.img = img;
		this.whichPlayer = whichPlayer;
		
		try {
			playerImage = ImageIO.read(new File(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            //playerIcon = new ImageIcon(img);
			//this.playerLabel = new JLabel();
			//playerLabel.paintComponents(g);
			//playerLabel.setIcon(playerIcon);
	}
	
	@Override
	public void run() {
		
	}
	
	public BufferedImage getImg(){
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
	
	@Override
	public void keyPressed(KeyEvent e) {

		if(this.whichPlayer ==1){
		// Tasteneingabe und Spielerposition verändern
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.setY ( Math.max(0, this.getY()- stepSize) );
			break;
		case KeyEvent.VK_DOWN:
			this.setY ( Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize, this.getY() + stepSize) );
			break;
		case KeyEvent.VK_LEFT:
			this.setX ( Math.max(0, this.getX() - stepSize) );
			break;
		case KeyEvent.VK_RIGHT:
			this.setX ( Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize, this.getX() + stepSize) );
			break;
		}
		}
		else if(this.whichPlayer == 2){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				this.setY ( Math.max(0, this.getY()- stepSize) );
				break;
			case KeyEvent.VK_S:
				this.setY ( Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize, this.getY() + stepSize) );
				break;
			case KeyEvent.VK_A:
				this.setX ( Math.max(0, this.getX() - stepSize) );
				break;
			case KeyEvent.VK_D:
				this.setX ( Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - stepSize, this.getX() + stepSize) );
				break;
			}			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
