package bombermanGui;

//Imports
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import bombermanGui.BombermanBorkKnebel.bombermanGui;

public class Bomb implements Runnable {
	
	//Variablen
	private int whichPlayer, x, y;
	private BufferedImage bombImage;

	//Konstruktor
	public Bomb(int x, int y, int which) {

		try {
			bombImage = ImageIO.read(new File("src/images/tnt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.whichPlayer = which;
		BombermanBorkKnebel.bombermanGui.bombList.add(this);
	}

	public int getWhichPlayer() {
		return whichPlayer;
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

	//Methoden zum Sprengen von Boxen und Playern
	public void removeBox() {

		int left = this.x - 25;
		int right = this.x + 45;
		int top = this.y - 25;
		int bottom = this.y + 45;

		ArrayList<Box> toDelete = new ArrayList<>();
		for (Box b : BombermanBorkKnebel.bombermanGui.boxList) {
			if (!(b.getX() + 25 < left || b.getX() > right)) {
				if (!(b.getY() + 25 < top || b.getY() > bottom)) {
					toDelete.add(b);
				}
			}
		}
		BombermanBorkKnebel.bombermanGui.boxList.removeAll(toDelete);
	}

	public void removePlayer() {

		int left = this.x - 25;
		int right = this.x + 45;
		int top = this.y - 25;
		int bottom = this.y + 45;

		ArrayList<Player> pToDelete = new ArrayList<>();
		for (Player p : BombermanBorkKnebel.bombermanGui.playerList) {
			if (!(p.playerXPos + 25 < left || p.playerXPos > right)) {
				if (!(p.playerYPos + 25 < top || p.playerYPos > bottom)) {
					pToDelete.add(p);
					endGameScreen();
				}
			}
		}
		BombermanBorkKnebel.bombermanGui.playerList.removeAll(pToDelete);
	}
	
	//Methode zum Aufrufen des Game Over-Bildschirms beim Sprengen eines Spielers
	public static void endGameScreen(){
		
		// Anlegen eines neuen Frames inklusive 2 Buttons
		JFrame endGame = new JFrame("GAME OVER !");
		JPanel endControlPanel = new JPanel();
		
		JButton restart = new JButton();
		restart.setText("Restart");
		
		JButton exit = new JButton();
		exit.setText("Exit Game");
		
		endGame.getContentPane().add(endControlPanel, BorderLayout.CENTER);
		
		//Exit Game-Button hinzufuegen und Klick-Aktion definieren
		endControlPanel.add(exit);
		endGame.pack();
		endGame.setVisible(true);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				endGame.dispose();
				BombermanBorkKnebel.bomberFrame.dispose();
			}
		});
		
		//Restart-Button hinzufuegen und Klick-Aktion definieren
		endControlPanel.add(restart);

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	endGame.dispose();
            	bombermanGui.playerList.removeAll(bombermanGui.playerRemove);
            	bombermanGui.boxList.removeAll(bombermanGui.boxRemove);
            	BombermanBorkKnebel.pBombermanGui.fillBoxList();
            	BombermanBorkKnebel.pBombermanGui.player1 = new Player("player1", BombermanBorkKnebel.pBombermanGui.readJson("player1").get(0),
            			BombermanBorkKnebel.pBombermanGui.readJson("player1").get(1), "src/images/creeper.png", 1,
            			BombermanBorkKnebel.pBombermanGui.keysPressed);
            	BombermanBorkKnebel.pBombermanGui.player1.start();

            	BombermanBorkKnebel.pBombermanGui.player2 = new Player("player2", BombermanBorkKnebel.pBombermanGui.readJson("player2").get(0),
            			BombermanBorkKnebel.pBombermanGui.readJson("player2").get(1), "src/images/creeperBlue.png", 2,
            			BombermanBorkKnebel.pBombermanGui.keysPressed);
            	BombermanBorkKnebel.pBombermanGui.player2.start();
            }
        });
        
        //End Game-Frame anzeigen
		endGame.setBounds(650, 450, 400, 400);
		endGame.pack();
		endGame.setVisible(true);
	}

	@Override
	public void run() {
		try {
			//Thread wartet 3 Sekunden
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Bombe legt neue Explosion an
		Explosion ex = new Explosion(this.x, this.y);
		new Thread(ex).start();
		BombermanBorkKnebel.bombermanGui.fillExplosionList(ex.getX(), ex.getY());

		//Bombe prüft Boxes und Spieler 
		this.removeBox();
		this.removePlayer();
		
		//Bombe wird aus der Liste entfernt
		BombermanBorkKnebel.bombermanGui.bombList.remove(this);
	}
}