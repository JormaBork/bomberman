package bombermanGui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Tile extends JPanel {

	private static final long serialVersionUID = 7584667300067348221L;

	private JLabel image;
	private ImageIcon textureKachel = new ImageIcon("img/wall.png");
	private ImageIcon textureErde = new ImageIcon("img/wall.png");

	private JLabel overlay;
	private ImageIcon textureStein = new ImageIcon("img/wall.png");

	public Tile(int dimension) {
		image = new JLabel();
		image.setBounds(0, 0, dimension, dimension);
		image.setIcon(textureKachel);

		setLayout(null);
		add(image);
	}

	public Tile(int dimension, boolean access) {
		if (!access) {
			image = new JLabel();
			image.setBounds(0, 0, dimension, dimension);
			image.setIcon(textureErde);

			overlay = new JLabel();
			overlay.setBounds(0, -6, dimension, dimension + 5);
			overlay.setIcon(textureStein);
			image.add(overlay);

			setLayout(null);
			add(image);
		} else
			new Tile(dimension);
	}

	public Image getImage() {
		return textureErde.getImage();
	}

	public Image getImageKachel() {
		return textureKachel.getImage();
	}

	public Image getImageStein() {
		return textureStein.getImage();
	}
}
