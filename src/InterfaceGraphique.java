import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextPane;

public class InterfaceGraphique extends JFrame{
	JPanel panelMain = new JPanel(new GridLayout(1, 5));
	JFrame fenetre = new JFrame();
	ImageIcon immg = new ImageIcon("image/fon2.jpg");
	Image img = immg.getImage();
	JPanel panelTableau = new JPanel(new GridLayout(8,8)) {
		//TODO Refaire le code du fond du plateau
		public void paintComponent(Graphics page)
		{
		    super.paintComponent(page);

		    int h = img.getHeight(null);
		    int w = img.getWidth(null);

		    // Scale Horizontally:
		    if ( w > this.getWidth() )
		    {
		        img = img.getScaledInstance( getWidth(), -1, Image.SCALE_DEFAULT );
		        h = img.getHeight(null);
		    }

		    // Scale Vertically:
		    if ( h > this.getHeight() )
		    {
		        img = img.getScaledInstance( -1, getHeight(), Image.SCALE_DEFAULT );
		    }

		    // Center Images
		    int x = (getWidth() - img.getWidth(null)) / 2;
		    int y = (getHeight() - img.getHeight(null)) / 2;

		    // Draw it
		    page.drawImage( img, x, y, null );
		}
	};
	JPanel panelMur = new JPanel();
	JLayeredPane panelPrincipal = new JLayeredPane();
	//reception des icones pour la main du joueur avec une mise � l'�chelle pour remplir le bouton
	ImageIcon YellowCard = new ImageIcon(new ImageIcon("image/YellowCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon BlueCard = new ImageIcon(new ImageIcon("image/BlueCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon LaserCard = new ImageIcon(new ImageIcon("image/LaserCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon PurpleCard = new ImageIcon(new ImageIcon("image/PurpleCard.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon aucuneCarte = new ImageIcon(new ImageIcon("image/aucuneCarte.png").getImage().getScaledInstance(80, 150, Image.SCALE_DEFAULT));
	ImageIcon murDeBois= new ImageIcon(new ImageIcon("image/WoodBox.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon joyau= new ImageIcon(new ImageIcon("image/RUBY.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon tortue= new ImageIcon(new ImageIcon("image/TortueBleue.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon glace= new ImageIcon(new ImageIcon("image/ICE.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	ImageIcon pierre= new ImageIcon(new ImageIcon("image/WALL.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	JTextPane textPane = new JTextPane();
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
	 	
		//cr�ation de la fenetre
		this.fenetre.setResizable(false);
		this.fenetre.setSize(new Dimension(1080, 720));
		this.fenetre.setTitle("Robot Turtles");
		this.fenetre.setName("fenetre");
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.getContentPane().setLayout(null);
		
		//cr�ation des differents panels
		this.panelPrincipal.setBounds(15, 16, 1044, 648);
		this.fenetre.getContentPane().add(this.panelPrincipal);
		this.panelPrincipal.setLayout(null);

		this.panelTableau.setBounds(263, 64, 494, 403);
		this.panelPrincipal.add(this.panelTableau);

		this.panelMain.setBounds(15, 474, 407, 158);
		this.panelPrincipal.add(this.panelMain);
		
		//creation des boutons du plateau
		for(int i=0;i<8;i++) {
			for(int j =0; j<8;j++) {
				JButton boutton = new JButton();
				boutton.setVisible(true);
				boutton.setOpaque(false);
				boutton.setContentAreaFilled(false);
				boutton.setName(i+";"+j);/*on donne pour chaque bouton en nom leur coordonnee pour pouvoir les r�cuperer a c
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
		
		//bouton jai fini qui sera utilis� par exemple losque l'utilisateur aura fini de defausser sa main
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
					message("Vous ne poss�dez pas de mur de pierre");
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
					message("Vous ne poss�dez pas de mur de bois");
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
					message("Vous ne poss�dez pas de mur de glace");
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
		
		
		
		
		
		message("Bienvenue dans le Robot Turtles");
		
		
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
		 evenementMur=false; // on met levenement false car le mur a �t� lu
		 //on met tous les evenements � faux
		 evenementPlateau=false;
		 evenementMain=false;
		 return murSelectionne;
	 }
	 
	 public int getCarteSelectionne() {
		 evenementMur=false; // on met levenement false car le mur a �t� lu
		 //on met tous les evenements � faux
		 evenementPlateau=false;
		 evenementMain=false;
		 return indexMain;
	 }
	 
	 public boolean getEvenementMur() {
		 return evenementMur;
	 }
	 public int[] getCoordonnee() {
		 evenementMur=false; // on met levenement false car le mur a �t� lu
		 //on met tous les evenements � faux
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
}
