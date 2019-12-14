import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Cursor;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import java.awt.event.ActionEvent;

public class InterfaceGraphique extends JFrame{
	JPanel panelMain = new JPanel(new GridLayout(1, 5));
	JFrame fenetre = new JFrame();
	JPanel panelTableau = new JPanel(new GridLayout(8,8));
	JLayeredPane panelPrincipal = new JLayeredPane();
	ImageIcon YellowCard = new ImageIcon(new ImageIcon("image/YellowCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon BlueCard = new ImageIcon(new ImageIcon("image/BlueCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon LaserCard = new ImageIcon(new ImageIcon("image/LaserCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon PurpleCard = new ImageIcon(new ImageIcon("image/PurpleCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	private JButton[] boutonCarte = new JButton[5];
	 public  InterfaceGraphique() {
		
		this.fenetre.setResizable(false);
		this.fenetre.setSize(new Dimension(1080, 720));
		
		this.fenetre.setTitle("Robot Turtles");
		this.fenetre.setName("fenetre");
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.getContentPane().setLayout(null);
		
		
		this.panelPrincipal.setBounds(15, 16, 1044, 648);
		this.fenetre.getContentPane().add(this.panelPrincipal);
		this.panelPrincipal.setLayout(null);
		
		
		
		
		this.panelTableau.setBounds(263, 64, 494, 403);
		this.panelPrincipal.add(this.panelTableau);
		
		
		for(int i=0;i<8;i++) {
			for(int j =0; j<8;j++) {
				JPanel square = new JPanel(new BorderLayout());
				this.panelTableau.add(square);
				square.setVisible(true);
				if((i%2)==0) {
					if((j%2==0)) {
						square.setBackground(Color.blue);
					}
					else {
						square.setBackground(Color.white);
					}
				}
				else {
					if((j%2==0)) {
						square.setBackground(Color.white);
					}
					else {
						square.setBackground(Color.blue);
					}
				}
			}
		}
		/*for (int i = 0; i < 64; i++) {
			  JPanel square = new JPanel(new BorderLayout());
			  panelTableau.add(square);
			  int row = (int)(i / 8) % 2;
			  if (row == 0)
			  square.setBackground( i % 2 == 0 ? Color.blue : Color.white );
			  else
			  square.setBackground( i % 2 == 0 ? Color.white : Color.blue );
		}*/
		
		
		this.panelMain.setBounds(15, 474, 407, 158);
		this.panelPrincipal.add(this.panelMain);
		
		for(int i=0; i<boutonCarte.length;i++) {
			JButton b = new JButton();
			b.setVisible(true);
			boutonCarte[i]=b;
			this.panelMain.add(boutonCarte[i]);
			 }
		
		JButton boutonValiderCarte = new JButton("Valider Carte");
		boutonValiderCarte.setBounds(529, 586, 150, 29);
		this.panelPrincipal.add(boutonValiderCarte);
		
		JButton boutonFini = new JButton("J'ai fini");
		boutonFini.setBounds(826, 587, 115, 29);
		this.panelPrincipal.add(boutonFini);
		boutonFini.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		
		
		JLabel titreConteneur = new JLabel("Bondour, bienvenue dans le wobot tuhtle");
		titreConteneur.setBounds(366, 16, 299, 20);
		this.panelPrincipal.add(titreConteneur);
		
		/*final JButton carte1 = new JButton("carte");
		this.panelMain.add(carte1);
		
		JButton carte2 = new JButton("carte");
		this.panelMain.add(carte2);
		
		JButton carte3 = new JButton("carte");
		this.panelMain.add(carte3);
		
		JButton carte4 = new JButton("carte");
		this.panelMain.add(carte4);
		
		JButton carte5 = new JButton("carte");
		this.panelMain.add(carte5);
		
		carte1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carte1.setBackground(Color.blue);
			}
		});
		*/
		
		
		
		this.fenetre.setVisible(true);
		System.out.println("bla");
		
		
	}
	 public void setMain(List<Carte> mainDuJoueur) {
		for(int i=0; i<mainDuJoueur.size();i++) {
			 switch(mainDuJoueur.get(i).role) {
			 case "Bleue":
				 //carte2.setName("Bleue");
				 boutonCarte[i].setBackground(Color.blue);
				 boutonCarte[i].setIcon(BlueCard);
				 break;
			 case "Jaune":
				 //carte2.setName("Jaune");
				 boutonCarte[i].setBackground(Color.yellow);
				 boutonCarte[i].setIcon(YellowCard);
				 
				 break;
			 case "Violette":
				 //carte2.setName("Violette");
				 boutonCarte[i].setBackground(Color.red);
				 boutonCarte[i].setIcon(PurpleCard);
				 break;
			 case "Laser":
				 //carte2.setName("Laser");
				 boutonCarte[i].setBackground(Color.black);
				 boutonCarte[i].setIcon(LaserCard);
				 break;
			 }
		 }
	 }
	 
	 public void updateTableau() {
		 
	 }
}
