import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;
public class Main {
	//Variable plateau va contenir les diff�rents �l�ments, ou les cases vides
	public static String plateau[][] = new String[8][8];
	public static int nbJoueurs;
	public static int tourJoueur;//variable indiquant � quel joueur est-ce le tour de jouer
	public static String[] couleurs = {"Bleu","Rouge","Vert","Rose"}; //tableau des couleurs pour chaque joueurs
	public static String[] nomsTortue = {"Beep", "Pi", "Pangie", "Dot"};//tableau des noms de tortue
	public static int[] coordonneeEntree = new int[2];
	static List<Joueur> joueurs = new ArrayList<>();
	static List<Joueur> joueursClassementFinal = new ArrayList<>();
	public static TreeMap<String, String> directionJoueur = new TreeMap<>();
	static List<Joyau> joyaux = new ArrayList<>();
	public static InterfaceGraphique gui;
	public static boolean gagnant=false;
	public static Scanner scanner = new Scanner(System.in);	
	
	public static void main(String[] args) {
		initialisation();
		//On vient cr�er notre objet interface graphique pour pouvoir interagir avec celle-ci
		gui = new InterfaceGraphique();
		
		//Tant que la variable finDuJeu est � l'�tat FALSE, alors le jeu continuer de tourner
		while(!finDuJeu()) {
			updatePlateau();
			choixJoueur();
			joueurSuivant();
		}
		joueursClassementFinal.add(joueurs.get(0));
		String message="";
		for(int i=0; i<joueursClassementFinal.size();i++) {
			
			//message.concat(i+1+"-"+joueursClassementFinal.get(i).nom+"\n");	
			message = message+(i+1)+"-"+joueursClassementFinal.get(i).nom+"\n";
		}
		
		//Bo�te du message d'information
		JOptionPane.showMessageDialog(null, message, "Classement final", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	//
	
	public static void initialisation()
	{
		//initialisation de la pioche de base
		List<Carte> pioche = new ArrayList<Carte>();
		for(int i=0; i<18;i++) {
			pioche.add(new Carte("Bleue"));
		}
		for(int i=0; i<8;i++) {
			pioche.add(new Carte("Jaune"));
		}
		for(int i=0; i<8;i++) {
			pioche.add(new Carte("Violette"));
		}
		for(int i=0; i<3;i++) {
			pioche.add(new Carte("Laser"));
		}
		//fin d'initialisation de la pioche
		//la variable nombre contient les choix qui seront propos�s dans la bo�te de dialogue
		Integer[] nombre = {2, 3, 4};
		try {
			nbJoueurs = (int)JOptionPane.showInputDialog(null, 
				      "Veuillez indiquer le nombre de joueur",
				      "Nombre de joueurs",
				      JOptionPane.QUESTION_MESSAGE,
				      null,
				      nombre,
				      nombre[0]);
		}catch (Exception e) {
			System.exit(0);//Si le joueur fais la croix , on quitte le programme
		}
	    
	    
		//on cree le nombre de joueurs avec une couleur et un nom
		for(int i = 0; i<nbJoueurs; i++) {
			
			joueurs.add( new Joueur(couleurs[i], nomsTortue[i], pioche));
			//d�s le d�but, la tortue regarde vers le bas
			//la cl� de la treemap est repr�sent�e par le nom du joueur
			directionJoueur.put(nomsTortue[i], "s");//initialisation de la treemap Nomjoueur/direction
		}
		
		//Remplissage du plateau par des cases rien
	
		for(int i=0;i<plateau.length;i++) {
			for(int j=0;j<plateau[0].length;j++) {
				plateau[i][j]="rien";
			}
		}
		
		//En fonction du nombre de joueurs choisis, on vient placer ces derniers dans une certaine configuration
		
		switch(nbJoueurs) {
		case 2:
			joueurs.get(0).setPosition(0,1);
			joueurs.get(0).setPositionDepart(0,1);
			joueurs.get(1).setPosition(0,5);
			joueurs.get(1).setPositionDepart(0,5);
			joyaux.add(new Joyau("Vert", 7, 3));
			plateau[7][3]="JoyauVert";
			
			//Comme indiqu� dans les r�gles, si il y a moins de 4 joueurs, on retrouve une colonne de caisse en bois sur le c�t� 
			for(int i=0; i<8;i++) {
				plateau[i][7]= (new Mur(true, true,"murDeBois")).getNom();
			}
			
			break;
		case 3:
			joueurs.get(0).setPosition(0,0);
			joueurs.get(1).setPosition(0,3);
			joueurs.get(2).setPosition(0,6);
			
			joueurs.get(0).setPositionDepart(0,0);
			joueurs.get(1).setPositionDepart(0,3);
			joueurs.get(2).setPositionDepart(0,6);
			joyaux.add(new Joyau("Vert", 7, 3));
			joyaux.add(new Joyau("Violet", 7, 0));
			joyaux.add(new Joyau("Bleu", 7, 6));
			plateau[7][3]="JoyauVert";
			plateau[7][0]="JoyauViolet";
			plateau[7][6]="JoyauBleu";
			//Comme indiqu� dans les r�gles, si il y a moins de 4 joueurs, on retrouve une colonne de caisse en bois sur le c�t� 

			for(int i=0; i<8;i++) {
				plateau[i][7]=(new Mur(true, true,"murDeBois")).getNom();
			}
			break;
		case 4:
			joueurs.get(0).setPosition(0,0);
			joueurs.get(1).setPosition(0,2);
			joueurs.get(2).setPosition(0,5);
			joueurs.get(3).setPosition(0,7);//07
			
			joueurs.get(0).setPositionDepart(0,0);//00
			joueurs.get(1).setPositionDepart(0,2);
			joueurs.get(2).setPositionDepart(0,5);
			joueurs.get(3).setPositionDepart(0,7);
			
			joyaux.add(new Joyau("Violet", 7, 1));
			joyaux.add(new Joyau("Bleu", 7, 6));
			//mettre plutot les coordonnee en appelant l'objet joyau au lieu de mettre les coordonn�es brutes
			plateau[7][1]="JoyauViolet";
			plateau[7][6]="JoyauBleu";
			break;
		}	
		// Pour d�terminer quel est le joueur qui va d�buter la partie, on fait appelle � une variable al�atoire
		tourJoueur= (int) (Math.random() * (nbJoueurs-1));
	}
	
	
	
	public static void updatePlateau() {
		//Fonction de mise � jour du plateau qui est appellee a chaque debut de tour
		
		joueurs.get(tourJoueur).piocherCarte(); // le joueur pioche des carte jusqu'a en avoir 5
		for(int i=0; i < joueurs.size();i++) {
			
			//On r�cup�re les nouvelles valeurs de position, puis on vient placer le joueur � ces coordonn�es.
			
			plateau[joueurs.get(i).getPositionY()][joueurs.get(i).getPositionX()]=joueurs.get(i).getName();
			
			//Apr�s avoir plac� le joueur, on fait de m�me pour sa direction
			
			directionJoueur.replace(joueurs.get(i).getName(), joueurs.get(i).getDirection());
			
		}
		gui.setDirectionJoueurs(directionJoueur);//on envoie a l'interface la direction des joueurs
		//On met � jour le stock de mur du joueur
		gui.setNombreMur(joueurs.get(tourJoueur).getNombreMur());
		//idem pour la main
		gui.setMain(joueurs.get(tourJoueur).main);
		//On appelle une fonction qui vient mettre � jour le plateau affich� sur l'interface graphique
		gui.updateTableau(plateau);
		
		
		
	}
	
	public static boolean finDuJeu() {
		
		boolean fin=false;
		
		if(joueurs.size()==1) { //s'il reste un seul joueur, le jeu est fini
			fin=true;
			}
		return fin;
	}
	
	public static void joueurSuivant() {
		
		if(nbJoueurs==1) {
			tourJoueur=0;
		}
		else {		
			
			if(tourJoueur==(nbJoueurs-1)) {
				tourJoueur=0;
			}
			else tourJoueur++;}

	}
	
	public static void choixJoueur() {
		//Cette fonction nous permet de r�cup�rer le choix de jeu du joueur
		String message = (joueurs.get(tourJoueur).getName()+" que souhaitez-vous faire ?");
		String[] choixProg = {"Compl�ter le programme", "Construire un mur", "Ex�cuter le programme"};
		
	    int rang = JOptionPane.showOptionDialog(null, 
	      message, // Le message qui sera affich�
	      "Choix", //L'intitul� de la fen�tre
	      JOptionPane.YES_NO_CANCEL_OPTION, //Le format de la bo�te de dialogue
	      JOptionPane.QUESTION_MESSAGE, //Le format de la bo�te de dialogue
	      null, 
	      choixProg, // Contenu des boutons que peux choisir le joueur
	      choixProg[2]); // Choix par d�faut
	    
	    //En fonction du choix, on vient appeller la fonction correspondante
	    
	    switch(rang) {
		case 0:
			completerProgramme();
			defausserMain();
			break;
		case 1:
			construireMur();
			defausserMain();
			break;
		case 2:
			executerProgramme();
			if(!gagnant) {
				defausserMain();
			}
			
			break;
		}
	    
	}
	
	static void completerProgramme() {
		boolean continuer = true;
		int indexCarte; // c'est lindex de la carte dans la liste de la main
			gui.message("Selectionnez une carte pour completer votre programme");
			while(!gui.getEvenementBoutonFini() && !joueurs.get(tourJoueur).getMain().isEmpty()) {
				while(!(gui.getEvenementMain() )) {// on attend que lutilisateur clique sur une carte
					try {
					    Thread.sleep(200);
					} catch (InterruptedException e) {

					    e.printStackTrace();
					}
					if(gui.getEvenementBoutonFini()) {//si l'utilisateur a cliqu� sur j'ai fini on casse la boucle
						continuer = false; //on met l'indicateur a false afin de ne pas proc�der � la routine
						break;
					}
				}
				if(continuer) {
					indexCarte=gui.getCarteSelectionne();//on recupere l'index de la carte choisi par lutilisateur
					joueurs.get(tourJoueur).ajouterInstruction( joueurs.get(tourJoueur).getMain().get(indexCarte) ); //on ajoute la carte dans la file dinstruction
					joueurs.get(tourJoueur).retirerCarte( joueurs.get(tourJoueur).getMain().get(indexCarte)); //on retire la carte de la main du joueur en meme temps on la rajoute dans la defausse mohammad 18/01
					gui.setMain(joueurs.get(tourJoueur).getMain());//actualisation de la main sur la gui
				}
				else {
					break;//on casse la boucle
				}
				
				
			}
				}
	
	
	static void construireMur() {

		String mur;
		int x;
		int y;
		boolean destructible;
		gui.message("S�lectionnez le mur � placer");
		//recuperation de la carte choisie par le joueur
		while(!(gui.getEvenementMur() )) {// tant quil ny a pas devenement// 
			try {
			    Thread.sleep(200);
			} catch (InterruptedException e) {

			    e.printStackTrace();
			}
		}
		mur=gui.getMurSelectionne();
		if(mur=="Glace") {
			destructible=true;
		}
		else destructible=false;
		gui.message("Cliquer sur un endroit o� placer le mur");
		do {
			while(!(gui.getEvenementPlateau())) {//on attend un evenement de l'utilisateur
				//permet de ne pas bloquer linterface graphique
				try {
				    Thread.sleep(200);
				} catch (InterruptedException e) {

				    e.printStackTrace();
				}
			}
			coordonneeEntree=gui.getCoordonnee();
			y=coordonneeEntree[0];
			x=coordonneeEntree[1];
			
			//Ici, on vient effectuer une serie de verfications afin de s'assurer que le mur peut etre place
			
			if(caseLibre(x,y)) {
				gui.message("Vous ne pouvez pas placer un mur sur une case occup�e");
			}
			if(bloquePasJoyau(x,y, destructible)) {
				gui.message("Vous ne pouvez pas bloquer un joyau avec un mur indestructible");
			}
			if(bloqueJoueur(x,y,destructible)) {
				gui.message("Vous ne pouvez pas enfermer un joueur");
			}
		}while((caseLibre(x,y)) || bloquePasJoyau(x,y, destructible) || bloqueJoueur(x,y,destructible));
		plateau[y][x]=joueurs.get(tourJoueur).retirerMur(mur).getNom();
		}
	
	static void executerProgramme() {
		String direction;
		gagnant=false;
		int[] coordonneeSuivante= new int[2];
		int[] coordonneeTortue= new int[2];
		int[] positionDepart= new int[2];
		ArrayDeque<Carte> file_locale = new ArrayDeque<>();
		file_locale = joueurs.get(tourJoueur).getInstructions();
		
		/*
		 * il faut cr�er une file d'instrucion locale
		 * il faut ensuite l'initiliser avec la file du joueur
		 * une fois que la file du joueur est lu, il faut vider la file du joueur
		 * il n'y a pas besoin de vider la file dans la pioche de defausse pcq
		 * lorsquon retire une carte de la main du joueur pour lajouter au programme, on la rajoute aussi dans la pioche de defausse 
		 * le while suivant se fera avec la file locale
		 */

		while(!file_locale.isEmpty()) {

			direction = joueurs.get(tourJoueur).getDirection();
			
			coordonneeTortue=joueurs.get(tourJoueur).getPosition();
			coordonneeSuivante=coordonneeTortue;
			//appel de la fonction coordonneeSuivante qui vas renvoyer les coordonnee suivante en fonction de la direction
			coordonneeSuivante=coordonneeSuivante(coordonneeSuivante[0],coordonneeSuivante[1],direction);
			//Carte instruction = joueurs.get(tourJoueur).getInstructions().pollFirst();
			Carte instruction = file_locale.pollFirst();

			
			switch(instruction.getRole()) {
			case "Bleue":
				// cest la partie la plus difficile, il y a beaucoup de regles : pas si difficile en fait :D
				/*
				 * � Les cartes bleues font avancer la tortue d�une case ;
				 * 
				 * � Si une tortue se heurte � un mur, elle va faire demi-tour. Le programme
				 * continue ensuite son ex�cution.
				 * � Si une tortue se heurte � une autre tortue, les deux tortues retournent
				 * � leurs positions de d�part, et le programme continue son ex�cution.
				 * 
				 * aussi bah si la tortue rencontre un joyau, elle gagne directement et du coup faire un break
				 */
				//Si la case suivante est en dehors du plateau la tortue reviens a la position de depart
				if(depassementPlateau(coordonneeSuivante[1],coordonneeSuivante[0])) {
					go_depart_tortue(joueurs.get(tourJoueur).getName());
				}
				
				//si ya rien alors on peut bouger la tortue
				else if(!caseLibre(coordonneeSuivante[1],coordonneeSuivante[0])){
					plateau[coordonneeTortue[0]][coordonneeTortue[1]]="rien";
					joueurs.get(tourJoueur).setPosition(coordonneeSuivante[0], coordonneeSuivante[1]);
				}
				else {
					switch(plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]) {
					case "JoyauViolet":
						plateau[coordonneeTortue[0]][coordonneeTortue[1]]="rien";
						joueurs.get(tourJoueur).setPosition(coordonneeSuivante[0], coordonneeSuivante[1]);
						joueursClassementFinal.add(joueurs.get(tourJoueur));
						joueurs.remove(tourJoueur); //on retire le joueur de la liste
						nbJoueurs--;
						tourJoueur--;
						gagnant=true;
						file_locale.clear();//on vide sa file d'instruction
						
						break;
						
					case "JoyauBleu":
						plateau[coordonneeTortue[0]][coordonneeTortue[1]]="rien";
						joueurs.get(tourJoueur).setPosition(coordonneeSuivante[0], coordonneeSuivante[1]);
						joueursClassementFinal.add(joueurs.get(tourJoueur));
						joueurs.remove(tourJoueur); //on retire le joueur de la liste
						nbJoueurs--;
						tourJoueur--;
						gagnant=true;
						file_locale.clear();
						break;
					case "JoyauVert":
						plateau[coordonneeTortue[0]][coordonneeTortue[1]]="rien";
						joueurs.get(tourJoueur).setPosition(coordonneeSuivante[0], coordonneeSuivante[1]);
						joueursClassementFinal.add(joueurs.get(tourJoueur));
						joueurs.remove(tourJoueur); //on retire le joueur de la liste
						nbJoueurs--;
						tourJoueur--;
						gagnant=true;
						file_locale.clear();
						break;
						
						// si une tortue rencontre une autre tortue, les deux tortues retournent � leurs position de d�part
						
					case "Beep":
						go_depart_tortue(joueurs.get(tourJoueur).getName());
						go_depart_tortue("Beep");
						break;
						
					case "Pi":
						go_depart_tortue(joueurs.get(tourJoueur).getName());
						go_depart_tortue("Pi");
						break;
					case "Pangie":
						go_depart_tortue(joueurs.get(tourJoueur).getName());
						go_depart_tortue("Pangie");
						break;
					case "Dot":
						go_depart_tortue(joueurs.get(tourJoueur).getName());
						go_depart_tortue("Dot");
						break;
						
					// si la tortue rencontre un mur, elle fait demi tour
					case "Glace":
						joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						break;
					case "Pierre":
						joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						break;
					case "murDeBois":
						joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						break;
					}
					
					
				}
				break;
			case "Jaune":
				joueurs.get(tourJoueur).setDirection(changementDeDirection(-90, direction));
				break;
			case "Violette":
				joueurs.get(tourJoueur).setDirection(changementDeDirection(90, direction));
				break;
			case "Laser":
				//la carte laser detruit le mur de glace quil y a a la case suivant
				// s'il n'y a rien a la case suivante on regarde a la case n+1 //moha04/01 cest fait
				while((coordonneeSuivante[1]<0|| coordonneeSuivante[1]>7|| coordonneeSuivante[0]<0|| coordonneeSuivante[0]>7)  ||  (plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]=="rien")) {
					//tant qu'on ne depasse pas le plateau ou que la case suivante est vide
					//on passe a la coordonn�e suivante
					coordonneeSuivante=coordonneeSuivante(coordonneeSuivante[0],coordonneeSuivante[1],direction);
				}
				//on verifie dabord quon depasse pas le plateau
				if(coordonneeSuivante[1]<0|| coordonneeSuivante[1]>7|| coordonneeSuivante[0]<0|| coordonneeSuivante[0]>7) {
					
				}
				else {
					switch(plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]) {
					case "Glace":
						plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]="rien";
						gui.updateTableau(plateau);
						//si la case suivante est un mur de glace, on le detruit
						break;
					case "rien":
						
						break;
					/* si la case suivante est un joyau alors : 
					 * le laser est r�fl�chi et se retourne contre la tortue. 
					 * -Celle-ci fait donc un demi-tour (s�il n�y a que deux joueurs) 
					 * -retourne � sa position de d�part (s�il y a plus de deux joueurs).
					 */
					case "JoyauViolet":
						if(joueurs.size()>2) {
							//on recupere la position de depart de la tortue
							//positionDepart=joueurs.get(tourJoueur).getPositionDepart();
							go_depart_tortue(joueurs.get(tourJoueur).getName());
							//joueurs.get(tourJoueur).setPosition(positionDepart[0], positionDepart[1]);
						}
						else {
							//demi tour de la tortue
							//on appelle la fonction demitour
							joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						}
						break;
					case "JoyauBleu":
						if(joueurs.size()>2) {
							//on recupere la position de depart de la tortue
							go_depart_tortue(joueurs.get(tourJoueur).getName());
						}
						else {
							//demi tour de la tortue
							//on appelle la fonction demitour
							joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						}
						break;
					case "JoyauVert":
						if(joueurs.size()>2) {
							//on recupere la position de depart de la tortue
							go_depart_tortue(joueurs.get(tourJoueur).getName());
							
						}
						else {
							//demi tour de la tortue
							//on appelle la fonction demitour qui prend en parametre une direction et qui renvoie la direction oppos�
							joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						}
						break;

					case "Beep":

						// Appel d'une fonction qui, en fonction du nombre de joueurs, va venir modifier les caract�ristiques
						// de la tortue touch�e : soit lui faire faire un demi tour, soit la renvoyer � sa position initale.
						shoot("Beep");
						
						break;
					case "Pi":
						
						shoot("Pi");
						
						break;
					case "Pangie":
						
						shoot("Pangie");
						
						break;
					case "Dot":
						
						shoot("Dot");
						
						break;
					}
				}	
			}

			//on vient placer la carte ex�cut� dans la pioche de d�fausse
			// samy: je pense ligne suivante inutile vu qu'on le fait dans la classe joueur
			//joueurs.get(tourJoueur).piocheDefausse.add(instruction);
			
			//ici on fait une petite tempo pour voir la tortue bouger
			if(!gagnant) {
				updatePlateau();
				try {
				    Thread.sleep(500);
				} catch (InterruptedException e) {

				    e.printStackTrace();
				}
			}
			
		}
		
		// maintenant que la file a �t� lue, on la vide
		//Ici, � la fois on vide la liste et on met les cartes dans la pioche de d�fausse vu que c'est fait dans la fonction 
		//retirerCarte() --> cf classe joueur
		if(!gagnant) {//seulement si le joueur n'a pas gagn�
			while(!joueurs.get(tourJoueur).getInstructions().isEmpty()) {	
				Carte carte_Ajeter = joueurs.get(tourJoueur).getInstructions().poll();
				// mohammad 18/01 ca sert a rien cajoueurs.get(tourJoueur).retirerCarte(carte_Ajeter);//ici on retire la carte de la main et on la met tout de suite dans la defausse
			}
		}
		
		
	}
	
	
	static void defausserMain() {
		int option;
		int indexCarte;
		option = JOptionPane.showConfirmDialog(null, 
		        "Voulez-vous defausser votre main ?", 
		        "Choix utilisateur", 
		        JOptionPane.YES_NO_OPTION, 
		        JOptionPane.QUESTION_MESSAGE);
		if(option == 0) {
			while(!gui.getEvenementBoutonFini()) {
				if(gui.getEvenementMain()) {
					indexCarte=gui.getCarteSelectionne();
					joueurs.get(tourJoueur).retirerCarte( joueurs.get(tourJoueur).getMain().get(indexCarte)); //on retire la carte de la main du joueur
					gui.setMain(joueurs.get(tourJoueur).getMain());//actualisation de la main sur la gui
				}
				try {
				    Thread.sleep(200);
				} catch (InterruptedException e) {

				    e.printStackTrace();
				}
			}
		}
	}
	//fonction qui v�rifie si la case du plateau est vide, soit contient l'element "rien"
	static boolean caseLibre(int x, int y) {
		//automatiquement si la case saisie est hors plateau, on consid�re qu'elle n'est pas vide
		if(x<0|| x>7|| y<0|| y>7) {
			return true;
		}
		else {
			if(plateau[y][x]=="rien") {
				return false;
			}
			else return true;
			
		}
		
	}
	
	static boolean depassementPlateau(int x, int y) {
		if(x<0|| x>7|| y<0|| y>7) {
			return true;
		}
		else return false;
	}
	
	//fonction qui verifie si le mur ne sera pas plac� autour dun joyau
	static boolean bloquePasJoyau(int x, int y, boolean destructible) {
		int xJoyau;
		int yJoyau;
		//Si le mur choisi est destructible, nul besoin d'effectuer la v�rification car il pourra toujours �tre d�truit par un laser
		if(!destructible) {
			//on vient parcourir tous les joyaux pour etre sur que l'endroit o� l'on veut placer notre mur ne bloquera pas un des joyaux
			for(int i=0; i<joyaux.size();i++) {
				xJoyau=joyaux.get(i).getX();
				yJoyau=joyaux.get(i).getY();
				if((x == xJoyau) || (x == xJoyau+1) || (x == xJoyau-1) ){
					if((y == yJoyau) || (y == yJoyau+1) || (y == yJoyau-1)){
						if(x == xJoyau && y == yJoyau) {
							//on ne prend pas en compte le cas ou on clique sur le joyau
						}
						else return true;
					}
				}
			}
			return false;
		}
		else return false;
		
	}
	
	// fonction qui v�rfie si le mur que l'on va placer n'entourera pas un joueur
	
	static boolean bloqueJoueur(int x, int y, boolean destructible) {
		int xPlayer;
		int yPlayer;
		
		/*ce qu'il faut faire, c'est plutotverifier si les x et y recu en parametre sont les coordonn�es 
		 * d'une case a +1 au sud ou au nord ou a lest ou a louest d'un joueur
		 * et si c'est le cas alors il faut v�rifier si la tortue ne possede pas d�ja 3mur destructible autour d'elle
		 */
		
		//TODO : Samy 19/01 : Oui je suis d'accord mais le soucis c'est que tu prends pas en compte le cas o� la tortue est coll�e � un rebord
		// du plateau, pcq en vrai tu peux bloquer un joueur avec 2 murs si il est dans le coin par exemple
		// Mais �a tqt je l'ai g�r�, j'ai pris en compte les mur de bois et les rebords de plateau (tu peux tester).
		// Apr�s le soucis qu'il reste c'est que genre vu qu'on vient v�rifier � proximit� des tortues, bah il est toujours possible d'encercler un joueur
		// avec des murs de loin genre... Mais en soit �a a tr�s peu de chance d'arriver, donc je pense qu'on peut laisser la fonction telle qu'elle
		// T'en penses quoi ? Pcq en soit l� �a marche tr�s bien
		
		//Si le mur choisi est destructible, nul besoin d'effectuer la v�rification

		
		if(!destructible) {
			//int cpt_bloque=0;
			
			for(int i=0; i<nbJoueurs;i++) {
				xPlayer=joueurs.get(i).getPositionX();
				yPlayer=joueurs.get(i).getPositionY();
				// SAMY : 
				// d�roul� de la v�rif : dans tous les axes, on v�rifie si il y a des cases vides
				// le but �tant d'avoir au moins un axe de sortie. Donc on met en place un compteur
				// qui vient compter le nombre de cases vides, nous permettant de savoir (si on en a le bon nombre)
				// s'il est toujours possible de sortir de l� 
				/*
				int cptup=0;
				int cptdown=0;
				int cptleft=0;
				int cptright=0;
				*/
				//Compteur de mur/obstacles indestrucibles autour d'un joueur
				int cpt_mur=0;
				
				//On v�rifie si les coordonn�es mises en param�tre ne correspondent pas � une case adjacente � un joueur
				//En effet, s'il s'agit d'une case qui n'est pas coll�e au joueur, il n'est pas n�cessaire d'effectuer la v�rification
				
				if((x==xPlayer+1 && y==yPlayer) || (x==xPlayer-1 && y==yPlayer) || (x==xPlayer && y==yPlayer+1) || (x==xPlayer && y==yPlayer-1)) {
					
					//Ici avec cet enchainement de if's, nous allons venir verifier chaque cellule adjacente au joueur (Nord, Sud, Est, Ouest)
					//dans le but de verifier le contenu de ces cases. Si l'on y trouve un mur indestructible (Pierre ou mur de bois), alors 
					//on vient incr�menter notre compteur
					
					// on consid�re qu'une case hors plateau est �quivalente � un mur dans la mesure o� elle bloque le joueur
					
					if(depassementPlateau(yPlayer, xPlayer+1)==true) {
						cpt_mur++;
					}
					
					//S'il ne s'agit pas d'une case hors plateau, on vient v�rifier que la case contient bien un obstacle indestructible
					
					else if(plateau[yPlayer][xPlayer+1]=="Pierre" || plateau[yPlayer][xPlayer+1]=="murDeBois") {
						cpt_mur++;
					}

					if(depassementPlateau(yPlayer, xPlayer-1)==true) {
						cpt_mur++;
					}					
					else if(plateau[yPlayer][xPlayer-1]=="Pierre" || plateau[yPlayer][xPlayer-1]=="murDeBois") {
						cpt_mur++;
					}					
					
					if(depassementPlateau(yPlayer+1, xPlayer)==true) {
						cpt_mur++;
					}
					
					else if(plateau[yPlayer+1][xPlayer]=="Pierre" || plateau[yPlayer+1][xPlayer]=="murDeBois") {
						cpt_mur++;
					}
					
					if(depassementPlateau(yPlayer-1, xPlayer)==true) {
						cpt_mur++;
					}
					else if(plateau[yPlayer-1][xPlayer]=="Pierre" || plateau[yPlayer-1][xPlayer]=="Pierre") {
						cpt_mur++;
					}
					
					//On vient ici verifier le contenu de notre variable cpt_mur qui nous donne le nombre d'obstacles indestructibles situes autour
					//du joueur concerne. Si on compte 3 murs, alors on indique au joueur qui souhaite poser le mur qu'il lui est interdit d'encercler
					//un autre joueur
					
					if(cpt_mur>=3) {
						return true; //En effet, notre fonction renvoie true si le joueur risque d'etre encercle.
					}
					else {return false;}
					
				}
				
			}

			return false;
			
		}
		else return false;
		
		
	}
	
	
	
	//fonction permettant de trouver la nouvelle direction de la tortue
	static String changementDeDirection(int rotation, String direction) {
		String newDirection=direction;
		switch(direction) {
		case "s":
			if(rotation==90) {
				newDirection ="o";
			}
			else {
				newDirection ="e";
			}
			break;
		case "n":
			if(rotation==90) {
				newDirection ="e";
			}
			else {
				newDirection ="o";
			}
			break;
		case "e":
			if(rotation==90) {
				newDirection ="s";
			}
			else {
				newDirection ="n";
			}
			break;
		case "o":
			if(rotation==90) {
				newDirection ="n";
			}
			else {
				newDirection ="s";
			}
			break;	
		}
		return newDirection;
		
	}
	
	
	
	//fonction qui renvoie les coordonnee suivante en fonction de la direction de la tortue
	static int[] coordonneeSuivante(int y, int x, String direction) {
		 int[] coo = new int[2];
		 coo[0]=y;
		 coo[1]=x;
		 switch(direction) {
		 case "s":
			 coo[0]++;
			 break;
		 case "n":
			 coo[0]--;
			 break;
		 case "e":
			 coo[1]++;
			 break;
		 case "o":
			 coo[1]--;
			 break;
		 }
		 return coo;
		
	}
	
	
	
	//fonction qui renvoie loppose de la direction recue
	static String demiTour(String direction) {
		switch(direction) {
		case "s":
			 direction = "n";
			 break;
		case "n":
			 direction = "s";
			 break;
		case "e":
			 direction = "o";
			 break;
		case "o":
			 direction = "e";
			 break;
		}
		return direction;
		
	}

	static void go_depart_tortue(String nom) {
		int[] positionDep = new int[2];
		//On parcours les tortues jusqu'� trouver celle sur laquelle on vient de tirer
		
		for(int i=0; i<joueurs.size();i++) {
			Joueur j = joueurs.get(i);
			
			if(j.nom.equals(nom)) {
				//mohammad 16/12 lorsque l'on met la tortue a sa position de depart, il faut mettre rien sur le plateau dans sa position actuelle:
				plateau[j.getPositionY()][j.getPositionX()]="rien";
				//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on r�cup�re sa position de depart
				positionDep=j.getPositionDepart();
				
				//on vient maintenant replacer la tortue � sa position de d�part
				j.setPosition(positionDep[0], positionDep[1]);
				j.setDirection("s");//la direction de la sortie est celle de base
			}
			else {}
		}
		
		
	}
	
	static void shoot(String nom) {
		int[] positionDep = new int[2];
		
		if(joueurs.size()>2) {
			
			//On parcours les tortues jusqu'� trouver celle sur laquelle on vient de tirer
			
			for(int i=0; i<joueurs.size();i++) {
				Joueur j = joueurs.get(i);
				
				if(j.nom.equals(nom)) {
					
					plateau[j.getPositionY()][j.getPositionX()]="rien";
					
					//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on r�cup�re sa position de depart
					positionDep=j.getPositionDepart();
					
					//on vient maintenant replacer la tortue � sa position de d�part
					j.setPosition(positionDep[0], positionDep[1]);
				}
				else {}
			}
		}
		
		else {
			
			//On parcours les tortues jusqu'� trouver celle sur laquelle on vient de tirer

			for(int i=0; i<joueurs.size();i++) {
				Joueur j = joueurs.get(i);
				
				if(j.nom.equals(nom)) {
					//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on lui fait faire un demi-tour
					j.setDirection(demiTour(j.getDirection()));
				}
				else {}
			}
			
		}
		
		
	}
}
