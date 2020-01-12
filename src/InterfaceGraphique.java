import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextPane;

public class InterfaceGraphique extends JFrame{
	JPanel panelMain = new JPanel(new GridLayout(1, 5));
	JFrame fenetre = new JFrame();
	ImageIcon immg = new ImageIcon("image/fon2.jpg");
	Image img = immg.getImage();
	JPanel panelTableau = new JPanel(new GridLayout(8,8)) {
		@Override
		public void paintComponent(Graphics g)
	    {
	        //Chargement de l"image de fond
	        try {
	            Image img = ImageIO.read(new File("image/fond.png"));
	            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Erreur image de fond: " +e.getMessage());
	        }
	    }
	};
	JPanel panelMur = new JPanel();
	JLayeredPane panelPrincipal = new JLayeredPane();
	//reception des icones pour la main du joueur avec une mise à l'échelle pour remplir le bouton
	ImageIcon YellowCard = new ImageIcon(new ImageIcon("image/YellowCard.png").getImage().getScaledInstance(81, 146, Image.SCALE_DEFAULT));
	ImageIcon BlueCard = new ImageIcon(new ImageIcon("image/BlueCard.png").getImage().getScaledInstance(81, 146, Image.SCALE_DEFAULT));
	ImageIcon LaserCard = new ImageIcon(new ImageIcon("image/LaserCard.png").getImage().getScaledInstance(81, 146, Image.SCALE_DEFAULT));
	ImageIcon PurpleCard = new ImageIcon(new ImageIcon("image/PurpleCard.png").getImage().getScaledInstance(81, 146, Image.SCALE_DEFAULT));
	ImageIcon murDeBois= new ImageIcon(new ImageIcon("image/WoodBox.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon joyau= new ImageIcon(new ImageIcon("image/RUBY.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon joyauVert= new ImageIcon(new ImageIcon("image/joyauVert.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon joyauBleu= new ImageIcon(new ImageIcon("image/joyauBleu.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon joyauViolet= new ImageIcon(new ImageIcon("image/joyauViolet.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon joyauRouge= new ImageIcon(new ImageIcon("image/joyauRouge.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Beepn= new ImageIcon(new ImageIcon("image/blueN.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Beepe= new ImageIcon(new ImageIcon("image/blueE.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Beeps= new ImageIcon(new ImageIcon("image/blueS.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Beepo= new ImageIcon(new ImageIcon("image/blueO.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pin= new ImageIcon(new ImageIcon("image/rougeN.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pie= new ImageIcon(new ImageIcon("image/rougeE.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pis= new ImageIcon(new ImageIcon("image/rougeS.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pio= new ImageIcon(new ImageIcon("image/rougeO.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pangien= new ImageIcon(new ImageIcon("image/vertN.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pangiee= new ImageIcon(new ImageIcon("image/vertE.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pangies= new ImageIcon(new ImageIcon("image/vertS.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Pangieo= new ImageIcon(new ImageIcon("image/vertO.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Dotn= new ImageIcon(new ImageIcon("image/violetN.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Dote= new ImageIcon(new ImageIcon("image/violetE.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Dots= new ImageIcon(new ImageIcon("image/violetS.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon Doto= new ImageIcon(new ImageIcon("image/violetO.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon glace= new ImageIcon(new ImageIcon("image/ICE.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon pierre= new ImageIcon(new ImageIcon("image/WALL.png").getImage().getScaledInstance(53, 53, Image.SCALE_DEFAULT));
	ImageIcon FOND= new ImageIcon(new ImageIcon("image/fond.jpg").getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
	JTextPane textPane = new JTextPane();
	private TreeMap<String, String> directionJoueur = new TreeMap<>();
	private JButton[] boutonCarte = new JButton[5];
	private JButton[][] boutonPlateau = new JButton[8][8];
	private JLabel murGlaceLabel = new JLabel("1");
	private JLabel murPierreLabel = new JLabel("1");
	private JLabel murBoisLabel = new JLabel("1");
	private boolean aucunMurGlace=false;
	private boolean aucunMurPierre=false;
	private boolean aucunMurBois=false;
	private String informationsUtilisateur;// variable qui affichera des informations a l'utilisateur
	private static String murSelectionne; //variable qui permettra au main de savoir quel mur a ete selectionne
	private boolean evenementMur; // variable qui permettra au main de savoir s'il y a eu un evenement
	private boolean evenementPlateau; // variable qui permettra au main de savoir s'il y a eu un evenement sur le plateau
	private boolean evenementMain;
	private boolean evenementBoutonFini;
	private int[] coordonnee = new int[2];
	private int indexMain;
	 public  InterfaceGraphique() {
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fermeture du programme
	 	
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

		this.panelTableau.setBounds(269, 44, 425, 425);
		this.panelPrincipal.add(this.panelTableau);

		this.panelMain.setBounds(15, 486, 407, 146);
		this.panelPrincipal.add(this.panelMain);
		
		
		//creation des boutons du plateau
		for(int i=0;i<8;i++) {
			for(int j =0; j<8;j++) {
				JButton boutton = new JButton();
				boutton.setVisible(true);
				boutton.setOpaque(false);
				boutton.setContentAreaFilled(false);
				boutton.setName(i+";"+j);/*on donne pour chaque bouton en nom leur coordonnee pour pouvoir les récuperer a c
				chaque evenement*/
				//ici on gere les evenement des boutons du plateau
				boutton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton b = (JButton)e.getSource();
						String[] temp=b.getName().split(";");
						coordonnee[0]=Integer.parseInt(temp[0]);
						coordonnee[1]=Integer.parseInt(temp[1]);
						evenementPlateau=true;
						
					}
				});
				boutonPlateau[i][j]=boutton;
				this.panelTableau.add(boutonPlateau[i][j]);
			}
		}
		
		//creation des boutons de la main du joueur
		for(int i=0; i<boutonCarte.length;i++) {
			JButton b = new JButton();
			b.setVisible(true);
			b.setName(i+";0");
			b.addActionListener(new ActionListener() {//evenement pour le clic d'une carte
				public void actionPerformed(ActionEvent e) {
					JButton bbout = (JButton)e.getSource();
					if (bbout.getIcon()== null) {
						//si le bouton napas de carte, on ne prend pas en compte le clic
						evenementMain=false;
					}
					else {
						String[] temp=bbout.getName().split(";");
						indexMain=Integer.parseInt(temp[0]);
						evenementMain=true;
					}
				}
			});
			boutonCarte[i]=b;
			this.panelMain.add(boutonCarte[i]);
		}
		
		//bouton jai fini qui sera utilisé par exemple losque l'utilisateur aura fini de defausser sa main
		JButton boutonValiderCarte = new JButton("J'ai fini");
		boutonValiderCarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evenementBoutonFini=true;
			}
		});
		boutonValiderCarte.setBounds(529, 586, 150, 29);
		this.panelPrincipal.add(boutonValiderCarte);
		
		JButton boutonFini = new JButton("Annuler");
		boutonFini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		boutonFini.setBounds(826, 587, 115, 29);
		this.panelPrincipal.add(boutonFini);
		boutonFini.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		textPane.setBackground(Color.LIGHT_GRAY);
		textPane.setForeground(new Color(0, 128, 128));
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		
		
		this.textPane.setBounds(808, 64, 207, 403);
		this.textPane.setBackground(null);
		panelPrincipal.add(this.textPane);
		
		this.panelMur.setBounds(15, 287, 58, 171);
		
		//ajout du panel bouton pour les murs
		panelPrincipal.add(this.panelMur);
		this.panelMur.setLayout(new GridLayout(3, 1, 0, 0));
		JButton boutonPierre = new JButton();
		boutonPierre.addActionListener(new ActionListener() {
			/*evenement du bouton : lorsqu'on appui sur ce bouton, la variable evenementmur est mise a true
			comme cela, le main peut savoir que l'utilisateur a chois son mur*/
			public void actionPerformed(ActionEvent arg0) {
				murSelectionne = "Pierre";
				if(!aucunMurPierre) {
					evenementMur = true;
				}
				else {
					message("Vous ne possédez pas de mur de pierre");
					System.out.println(informationsUtilisateur);
				}
				System.out.println(murSelectionne);
			}
		});
		boutonPierre.setIcon(pierre);
		boutonPierre.setVisible(true);
		panelMur.add(boutonPierre);
		
		JButton boutonBois = new JButton();
		boutonBois.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				murSelectionne = "Bois";
				if(!aucunMurBois) {
					evenementMur = false;
				}
				else {
					message("Vous ne possédez pas de mur de bois");
				}
				System.out.println(murSelectionne);
			}
		});
		boutonBois.setIcon(murDeBois);
		boutonBois.setVisible(true);
		panelMur.add(boutonBois);
		
		JButton boutonGlace = new JButton();
		boutonGlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				murSelectionne = "Glace";
				if(!aucunMurGlace) {
					evenementMur = true;
				}
				else {
					message("Vous ne possédez pas de mur de glace");
				}
				System.out.println(murSelectionne);
			}
		});
		boutonGlace.setIcon(glace);
		boutonGlace.setVisible(true);
		panelMur.add(boutonGlace);
		
		
		murPierreLabel.setFont(new Font("Wide Latin", Font.BOLD, 16));
		murPierreLabel.setBounds(76, 287, 33, 56);
		panelPrincipal.add(murPierreLabel);
		
		
		murBoisLabel.setFont(new Font("Wide Latin", Font.BOLD, 16));
		murBoisLabel.setBounds(76, 345, 33, 56);
		panelPrincipal.add(murBoisLabel);
		
		
		murGlaceLabel.setFont(new Font("Wide Latin", Font.BOLD, 16));
		murGlaceLabel.setBounds(76, 402, 33, 56);
		panelPrincipal.add(murGlaceLabel);
		
		
		
		
		
		message("Bienvenue dans Robot Turtles");
		
		
		// on affiche la fenete seulement apres l'initialisation
		this.fenetre.setVisible(true);
		
		
	}
	 
	 public void setNombreMur(int[] tableauNombreMur) {
		 murGlaceLabel.setText(":"+tableauNombreMur[2]);
		 murPierreLabel.setText(":"+tableauNombreMur[0]);
		 murBoisLabel.setText(":"+tableauNombreMur[1]);
		 for(int i=0;i<3;i++) {
			 if(tableauNombreMur[i]==0) {
				 if(i==0) {
					 aucunMurPierre=true;
				 }
				 else aucunMurPierre=false;
				 if(i==1) {
					 aucunMurBois=true;
				 }
				 else aucunMurBois=false;
				 if(i==2) {
					 aucunMurGlace=true;
				 }
				 else aucunMurGlace=false;
			 }
		 }
		 
	 }
	 // fonction permettant d'afficher les cartes du joueurs
	 public void setMain(List<Carte> mainDuJoueur) {
		 //si la main est plus petite que 5, on affiche sur la gui un carte point d'inteergation pour dire quil n'y a pas de carte
		 if(mainDuJoueur.size()<5) {
			 for(int i=mainDuJoueur.size();i<5;i++ ) {
				 boutonCarte[i].setBackground(null);
				 boutonCarte[i].setIcon(null);
			 }
		 }
		 
		for(int i=0; i<mainDuJoueur.size();i++) {
			 switch(mainDuJoueur.get(i).role) {
			 case "Bleue":
				 boutonCarte[i].setIcon(BlueCard);
				 break;
			 case "Jaune":
				 boutonCarte[i].setIcon(YellowCard);
				 break;
			 case "Violette":
				 boutonCarte[i].setIcon(PurpleCard);
				 break;
			 case "Laser":
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
				 case "JoyauBleu":
					 boutonPlateau[i][j].setIcon(this.joyauBleu);
					 break;
				 case "JoyauViolet":
					 boutonPlateau[i][j].setIcon(this.joyauViolet);
					 break;
				 case "JoyauVert":
					 boutonPlateau[i][j].setIcon(this.joyauVert);
					 break;
				 case "Beep":
					 switch(directionJoueur.get("Beep")) {//la position de la tortue se situe dans la treemap directionJoueur
					 //on verifie la position de la tortue pour afficher la bonne iconne
					 case "s":
						 boutonPlateau[i][j].setIcon(this.Beeps);
						 break;
					case "n":
						boutonPlateau[i][j].setIcon(this.Beepn);
						 break;
					case "e":
						boutonPlateau[i][j].setIcon(this.Beepe);
						 break;
					case "o":
						boutonPlateau[i][j].setIcon(this.Beepo);
						 break;
					
					 }
					 break;
				 case "Pi":
					 switch(directionJoueur.get("Pi")) {
					 case "s":
						 boutonPlateau[i][j].setIcon(this.Pis);
						 break;
					case "n":
						boutonPlateau[i][j].setIcon(this.Pin);
						 break;
					case "e":
						boutonPlateau[i][j].setIcon(this.Pie);
						 break;
					case "o":
						boutonPlateau[i][j].setIcon(this.Pio);
						 break;
					
					 }
					 break;
				 case "Pangie":
					 switch(directionJoueur.get("Pangie")) {
					 case "s":
						 boutonPlateau[i][j].setIcon(this.Pangies);
						 break;
					case "n":
						boutonPlateau[i][j].setIcon(this.Pangien);
						 break;
					case "e":
						boutonPlateau[i][j].setIcon(this.Pangiee);
						 break;
					case "o":
						boutonPlateau[i][j].setIcon(this.Pangieo);
						 break;
					
					 }
					 break;
				 case "Dot":
					 switch(directionJoueur.get("Dot")) {
					 case "s":
						 boutonPlateau[i][j].setIcon(this.Dots);
						 break;
					case "n":
						boutonPlateau[i][j].setIcon(this.Dotn);
						 break;
					case "e":
						boutonPlateau[i][j].setIcon(this.Dote);
						 break;
					case "o":
						boutonPlateau[i][j].setIcon(this.Doto);
						 break;
					
					 }
					 break;
				 case "Glace":
					 boutonPlateau[i][j].setIcon(this.glace);
					 break;
				 case "Pierre":
					 boutonPlateau[i][j].setIcon(this.pierre);
					 break;
				 case "rien":
					 boutonPlateau[i][j].setIcon(null);
				 }
				 
			 }
		 }
	 }
	 public void message(String message) {
		 this.textPane.setText(message);
	 }
	 public String getMurSelectionne() {
		 evenementMur=false; // on met levenement false car le mur a été lu
		 //on met tous les evenements à faux
		 evenementPlateau=false;
		 evenementMain=false;
		 return murSelectionne;
	 }
	 
	 public int getCarteSelectionne() {
		 evenementMur=false; // on met levenement false car le mur a été lu
		 //on met tous les evenements à faux
		 evenementPlateau=false;
		 evenementMain=false;
		 return indexMain;
	 }
	 
	 public boolean getEvenementMur() {
		 return evenementMur;
	 }
	 public int[] getCoordonnee() {
		 evenementMur=false; // on met levenement false car le mur a été lu
		 //on met tous les evenements à faux
		 evenementPlateau=false;
		 evenementMain=false;
		 return coordonnee;
	 }
	 
	 public boolean getEvenementPlateau() {
		 return evenementPlateau;
	 }
	 public boolean getEvenementMain() {
		 return evenementMain;
	 }
	 public boolean getEvenementBoutonFini() {
		 if(evenementBoutonFini) {
			 evenementMur=false;
			 evenementPlateau=false;
			 evenementMain=false;
			 evenementBoutonFini=false;
			 return true;
		 }
		 else {
			 return false;
		 }
		 
	 }
	 
	 //fonction permettant de récuperer la treemap avec comme clé le nom de la tortue et comme valeur sa position
	 public void setDirectionJoueurs(TreeMap<String, String> tree) {
		 this.directionJoueur=tree;
	 }
	 
	 
}
