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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.awt.event.ActionEvent;

public class InterfaceGraphique extends JFrame{
	JPanel panelMain = new JPanel(new GridLayout(1, 5));
	JFrame fenetre = new JFrame();
	JPanel panelTableau = new JPanel(new GridLayout(8,8));
	JLayeredPane panelPrincipal = new JLayeredPane();
	//reception des icones pour la main du joueur avec une mise à l'échelle pour remplir le bouton
	ImageIcon YellowCard = new ImageIcon(new ImageIcon("image/YellowCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon BlueCard = new ImageIcon(new ImageIcon("image/BlueCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon LaserCard = new ImageIcon(new ImageIcon("image/LaserCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon PurpleCard = new ImageIcon(new ImageIcon("image/PurpleCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon murDeBois= new ImageIcon(new ImageIcon("image/WoodBox.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon joyau= new ImageIcon(new ImageIcon("image/RUBY.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon tortue= new ImageIcon(new ImageIcon("image/TortueBleue.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon glace= new ImageIcon(new ImageIcon("image/ICE.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon pierre= new ImageIcon(new ImageIcon("image/WALL.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	private JButton[] boutonCarte = new JButton[5];
	private JButton[][] boutonPlateau = new JButton[8][8];
	 public  InterfaceGraphique() {
		//création de la fenetre
		this.fenetre.setResizable(false);
		this.fenetre.setSize(new Dimension(1080, 720));
		this.fenetre.setTitle("Robot Turtles");
		this.fenetre.setName("fenetre");
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.getContentPane().setLayout(null);
		
		//création des differents panels
		this.panelPrincipal.setBounds(15, 16, 1044, 648);
		this.fenetre.getContentPane().add(this.panelPrincipal);
		this.panelPrincipal.setLayout(null);

		this.panelTableau.setBounds(263, 64, 494, 403);
		this.panelPrincipal.add(this.panelTableau);

		this.panelMain.setBounds(15, 474, 407, 158);
		this.panelPrincipal.add(this.panelMain);
		
		/* ancienn methode pour affichage facon  jeu d'echec
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
		}*/
		//creation des boutons du plateau
		for(int i=0;i<8;i++) {
			for(int j =0; j<8;j++) {
				JButton boutton = new JButton();
				boutton.setVisible(true);
				boutonPlateau[i][j]=boutton;
				this.panelTableau.add(boutonPlateau[i][j]);
			}
		}
		//creation des boutons de la main du joueur
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
		
		
		// on affiche la fenete seulement apres l'initialisation
		this.fenetre.setVisible(true);
		
		
	}
	 // fonction permettant d'afficher les cartes du joueurs
	 public void setMain(List<Carte> mainDuJoueur) {
		for(int i=0; i<mainDuJoueur.size();i++) {
			 switch(mainDuJoueur.get(i).role) {
			 case "Bleue":
				 boutonCarte[i].setBackground(Color.blue);
				 boutonCarte[i].setIcon(BlueCard);
				 break;
			 case "Jaune":
				 boutonCarte[i].setBackground(Color.yellow);
				 boutonCarte[i].setIcon(YellowCard);
				 break;
			 case "Violette":
				 boutonCarte[i].setBackground(Color.red);
				 boutonCarte[i].setIcon(PurpleCard);
				 break;
			 case "Laser":
				 boutonCarte[i].setBackground(Color.black);
				 boutonCarte[i].setIcon(LaserCard);
				 break;
			 }
		 }
	 }
	 
	 public void updateTableau(String[][] plateau) {
		 for(int i=0; i<plateau.length;i++) {
			 for(int j=0;j<plateau[0].length; j++) {
				 switch(plateau[i][j]) {
				 case "murDeBois":
					 boutonPlateau[i][j].setIcon(this.murDeBois);
					 break;
				 case "JoyauxBleu":
					 boutonPlateau[i][j].setIcon(this.joyau);
					 break;
				 case "JoyauxViolet":
					 boutonPlateau[i][j].setIcon(this.joyau);
					 break;
				 case "JoyauxVert":
					 boutonPlateau[i][j].setIcon(this.joyau);
					 break;
				 case "Beep":
					 boutonPlateau[i][j].setIcon(this.tortue);
					 break;
				 case "Pi":
					 boutonPlateau[i][j].setIcon(this.tortue);
					 break;
				 case "Pangie":
					 boutonPlateau[i][j].setIcon(this.tortue);
					 break;
				 case "Dot":
					 boutonPlateau[i][j].setIcon(this.tortue);
					 break;
				 case "Glace":
					 break;
				 case "Pierre":
					 break;
				 }
				 
			 }
		 }
	 }
}
