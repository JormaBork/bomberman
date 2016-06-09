package bomberNew;

import javax.swing.JFrame;

public class MainBomber {
	
	public static JFrame fMainFrame;
	private static int frameWidth = 800;
	private static int frameHeight = 300;
	
	public static String menuImage = "src/Images/bild_menu.png";
	private static String gameBackgroundImage = "src/Images/bild_spiel_hintergrund.png";
	
	public static GameMenu gameMenu = new GameMenu(menuImage);


	public static void main(String[] args) {
		new MainBomber();
		MainBomber.fMainFrame.setVisible(true);
		MainBomber.fMainFrame.setFocusable(true);
		MainBomber.fMainFrame.setTitle("KnebelBorkBomber");
	}
	
	
}
