package bombermanGui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Kiste extends JLabel {

	private static final long serialVersionUID = 1744453890893953064L;
	private static Image image;
	private ImageIcon kiste = new ImageIcon("img/wood.jpg");

	public Kiste() {
		image = kiste.getImage();
		setVisible(true);
	}

	public Image getImage() {
		return image;
	}

}
