package bombermanGui;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Color;

public class BombermanGui extends JFrame {

	/**
	 * 
	 */
	
	//KOMMENTARRRRRRRRRRR
	
	private JPanel contentPane;
	private JTextField tHeight;
	public static BombermanGui frame;
	//public liste;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new BombermanGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BombermanGui() {

		/*
		 * Main frame
		 */
		setTitle("Bomberman by Bork/Knebel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

		/*
		 * Main panel
		 */
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("min:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("bottom:default"), }));

	

		//JPanel pPlayground = new JPanel();
		
		BombermanFunctions pPlayground = new BombermanFunctions();
		pPlayground.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pPlayground, "2, 2, 9, 3, fill, fill");
		//pPlayground.setLayout(new FormLayout(new ColumnSpec[] {}, new RowSpec[] {}));

		
		/*
		 * Panel "Add Player"
		 */
		
		JPanel pAddPlayer = new JPanel();
		pAddPlayer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Add Player",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(pAddPlayer, "2, 6, 5, 1, left, bottom");
		pAddPlayer.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("60px"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("86px"), },
				new RowSpec[] { RowSpec.decode("47px"), RowSpec.decode("20px"), RowSpec.decode("31px"),
						RowSpec.decode("23px"), }));

		JLabel lblNewLabel = new JLabel("Player Name");
		pAddPlayer.add(lblNewLabel, "2, 2, right, center");

		tHeight = new JTextField();
		pAddPlayer.add(tHeight, "4, 2, fill, top");
		tHeight.setColumns(10);

		JButton bAddNewPlayer = new JButton("Add Player");
		bAddNewPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pAddPlayer.add(bAddNewPlayer, "4, 4, left, top");
	}
}
