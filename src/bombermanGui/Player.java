package bombermanGui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Player extends JPanel implements KeyListener, Runnable {
	
	private String name;
	private int x,y;
	private String img;
	private BufferedImage playerImage;
	private final boolean destroyable = true;
	private JLabel playerLabel;
	private Graphics g;
	private ImageIcon playerIcon;
	boolean end = false;
	public int directory = 0;
	public int refresh = 15;
	int travelPixel = 5;
//	private String playerImage="";
	
	
	public Player(String name, int x,int y, String img) {
		super();
		this.name = name;
		this.y = y;
		this.x = x;
		
		this.img = img;
		
		
		try {
			playerImage = ImageIO.read((ImageInputStream) ImageIO.read(new File(img)));;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            //playerIcon = new ImageIcon(img);
			//this.playerLabel = new JLabel();
			//playerLabel.paintComponents(g);
			//playerLabel.setIcon(playerIcon);
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

		// Tasteneingabe und Spielerposition verändern
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_UP:
//			this.setY ( Math.max(0, this.getY()- travelPixel) );
//			break;
//		case KeyEvent.VK_DOWN:
//			this.setY ( Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - travelPixel, this.getY() + travelPixel) );
//			break;
//		case KeyEvent.VK_LEFT:
//			this.setX ( Math.max(0, this.getX() - travelPixel) );
//			break;
//		case KeyEvent.VK_RIGHT:
//			this.setX ( Math.min(BombermanBorkKnebel.pBombermanGui.WIDTH - travelPixel, this.getX() + travelPixel) );
//			break;
//		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void move() {
//		if (directory != 0 && Spiel.uhrzeit.isRunning()) {
			checkCollision();
//		}
	}
	
	private void checkCollision() {
//		Spielfeld.spieler1.createRect();
//		Spielfeld.spieler2.createRect();
//		createRect();
//
//		for (int i = 0; i < Spielfeld.kacheln.size(); i++) {
//			Rectangle rectangleTmp = Spielfeld.kacheln.get(i).getBounds();
//			collision(rectSpieler, rectangleTmp);
//		}
//		for (int i = 0; i < Spiel.spielfeld.kistenListe.size(); i++) {
//			Rectangle rectangleTmp = Spiel.spielfeld.kistenListe.get(i)
//					.getBounds();
//			collision(rectSpieler, rectangleTmp);
//		}
//		for (int i = 0; i < Spielfeld.bombenListe.size(); i++) {
//			Rectangle rectangleTmp = Spielfeld.bombenListe.get(i).getBounds();
//			collision(rectSpieler, rectangleTmp);
//		}
//		if (rectSpieler != null) {
//			if (Spielfeld.spieler1.rectSpieler.equals(rectSpieler)) {
//				if (Spielfeld.spieler2.rectSpieler != null
//						&& rectSpieler
//								.intersects(Spielfeld.spieler2.rectSpieler))
//					collision(rectSpieler, Spielfeld.spieler2.rectSpieler);
//			} else {
//				if (rectSpieler.intersects(Spielfeld.spieler1.rectSpieler))
//					collision(rectSpieler, Spielfeld.spieler1.rectSpieler);
//			}
//		}
//		for (int i = 0; i < Spielfeld.itemList.size(); i++) {
//			Rectangle rectangleTmp = Spielfeld.itemList.get(i).getBounds();
//			if (rectSpieler.intersects(rectangleTmp)) {
//				if (Spielfeld.itemList.get(i).getType() == "fire") {
//					explosion++;
//					Spiel.setDurchschlag(true, Spielfeld.spieler1.explosion);
//					Spiel.setDurchschlag(false, Spielfeld.spieler2.explosion);
//					Spiel.spielfeld.remove(Spielfeld.itemList.get(i));
//					Spielfeld.itemList.remove(Spielfeld.itemList.get(i));
//					break;
//				} else if (Spielfeld.itemList.get(i).getType() == "bomb") {
//					if (maxBomben <= 1)
//						maxBomben++;
//					Spiel.setBombenAnzahl(true, Spielfeld.spieler1.maxBomben);
//					Spiel.setBombenAnzahl(false, Spielfeld.spieler2.maxBomben);
//					Spiel.spielfeld.remove(Spielfeld.itemList.get(i));
//					Spielfeld.itemList.remove(Spielfeld.itemList.get(i));
//				} else if (Spielfeld.itemList.get(i).getType() == "boot") {
//					speed = 2;
//					refresh = 10;
//					Spiel.setGeschwindigkeit(true, Spielfeld.spieler1.speed);
//					Spiel.setGeschwindigkeit(false, Spielfeld.spieler2.speed);
//					Spiel.spielfeld.remove(Spielfeld.itemList.get(i));
//					Spielfeld.itemList.remove(Spielfeld.itemList.get(i));
//				}
//			}
//		}
//		for (int i = 0; i < Spielfeld.fireList.size(); i++) {
//			Rectangle rectangleTmp = Spielfeld.fireList.get(i).getBounds();
//			if (rectangleTmp.intersects(Spielfeld.spieler1.rectSpieler)
//					&& Spiel.uhrzeit.isRunning()) {
//				Spiel.uhrzeit.stop();
//				Spiel.spielfeld.setVisible(false);
//				Spiel.weiter.setEnabled(false);
//				Spiel.setWinnerPortrait(Spiel.spielerfarbe2,
//						Spiel.getSpielerName2());
//				Spiel.inGame.setVisible(true);
//			} else if (rectangleTmp.intersects(Spielfeld.spieler2.rectSpieler)
//					&& Spiel.uhrzeit.isRunning()) {
//				Spiel.uhrzeit.stop();
//				Spiel.spielfeld.setVisible(false);
//				Spiel.weiter.setEnabled(false);
//				Spiel.setWinnerPortrait(Spiel.spielerfarbe1,
//						Spiel.getSpielerName1());
//				Spiel.inGame.setVisible(true);
//			}
//		}
	}
	@Override
	public void run() {
		while (true) {
			move();
			try {
				Thread.sleep(refresh);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
