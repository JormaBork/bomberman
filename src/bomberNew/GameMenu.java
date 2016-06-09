package bomberNew;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import javafx.application.Application;

public class GameMenu extends JPanel {

	// Variablen für Start und Ladebutton sowie das Hintergrundbild
	private JButton bStartGame;
	private JButton bLoadGame;
	Image iBackgroundImage = null;

	// Konstruktor mit MediaTracker zum Einlesen der Datei hinter dem
	// übergebenen Pfad
	public GameMenu(String image) {
		if (image != null) {
			MediaTracker mt = new MediaTracker(this);
			iBackgroundImage = Toolkit.getDefaultToolkit().getImage(image);
			mt.addImage(iBackgroundImage, 0);
			try {
				mt.waitForAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(iBackgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	public void initMenue() {
		MainBomber.gameMenu.iBackgroundImage = Toolkit.getDefaultToolkit().getImage(MainBomber.menuImage);

		bStartGame = new JButton("Start Game");
		bStartGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					initStartMenu();
				}
			}
		});
		add(bStartGame, "3, 22");

		File savegame = new File("savegame.txt");
		bLoadGame = new JButton("Spiel laden");
		MouseListener mouseListenerLoad = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					LoadGame.loadTheGame();
				}
			}
		};
		bLoadGame.addMouseListener(mouseListenerLoad);

		if (!savegame.exists()) {
			bLoadGame.setEnabled(false);
			bLoadGame.removeMouseListener(mouseListenerLoad);
		}
		add(bLoadGame, "3, 24");
	}

}
