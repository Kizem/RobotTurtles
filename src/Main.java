import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;
public class Main {
	//Variable plateau va contenir les différents éléments, ou les cases vides
	public static String plateau[][] = new String[8][8];
	public static int nbJoueurs;
	public static int tourJoueur;//variable indiquant à quel joueur est-ce le tour de jouer
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
		//On vient créer notre objet interface graphique pour pouvoir interagir avec celle-ci
		gui = new InterfaceGraphique();
		
		//Tant que la variable finDuJeu est à l'état FALSE, alors le jeu continuer de tourner
		while(!finDuJeu()) {
			updatePlateau();
			choixJoueur();
			joueurSuivant();
		}
		joueursClassementFinal.add(joueurs.get(0));
		String message="";
		for(int i=0; i<joueursClassementFinal.size();i++) {
			
			//message.concat(i+1+"-"+joueursClassementFinal.get(i).nom+"\n");	
			message = message+(i+1)+"-"+joueursClassementFinal.get(i).getName()+"\n";
		}
		
		//Boîte du message d'information
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
		//la variable nombre contient les choix qui seront proposés dans la boîte de dialogue
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
			//dès le début, la tortue regarde vers le bas
			//la clé de la treemap est représentée par le nom du joueur
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
			
			//Comme indiqué dans les règles, si il y a moins de 4 joueurs, on retrouve une colonne de caisse en bois sur le côté 
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
			//Comme indiqué dans les règles, si il y a moins de 4 joueurs, on retrouve une colonne de caisse en bois sur le côté 

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
			//mettre plutot les coordonnee en appelant l'objet joyau au lieu de mettre les coordonnées brutes
			plateau[7][1]="JoyauViolet";
			plateau[7][6]="JoyauBleu";
			break;
		}	
		// Pour déterminer quel est le joueur qui va débuter la partie, on fait appelle à une variable aléatoire
		tourJoueur= (int) (Math.random() * (nbJoueurs-1));
	}
	
	
	
	public static void updatePlateau() {
		//Fonction de mise à jour du plateau qui est appellee a chaque debut de tour
		
		joueurs.get(tourJoueur).piocherCarte(); // le joueur pioche des carte jusqu'a en avoir 5
		for(int i=0; i < joueurs.size();i++) {
			
			//On récupère les nouvelles valeurs de position, puis on vient placer le joueur à ces coordonnées.
			
			plateau[joueurs.get(i).getPositionY()][joueurs.get(i).getPositionX()]=joueurs.get(i).getName();
			
			//Après avoir placé le joueur, on fait de même pour sa direction
			
			directionJoueur.replace(joueurs.get(i).getName(), joueurs.get(i).getDirection());
			
		}
		gui.setDirectionJoueurs(directionJoueur);//on envoie a l'interface la direction des joueurs
		//On met à jour le stock de mur du joueur
		gui.setNombreMur(joueurs.get(tourJoueur).getNombreMur());
		//idem pour la main
		gui.setMain(joueurs.get(tourJoueur).getMain());
		//On appelle une fonction qui vient mettre à jour le plateau affiché sur l'interface graphique
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
		//Cette fonction nous permet de récupérer le choix de jeu du joueur
		String message = (joueurs.get(tourJoueur).getName()+" que souhaitez-vous faire ?");
		String[] choixProg = {"Compléter le programme", "Construire un mur", "Exécuter le programme"};
		
	    int rang = JOptionPane.showOptionDialog(null, 
	      message, // Le message qui sera affiché
	      "Choix", //L'intitulé de la fenêtre
	      JOptionPane.YES_NO_CANCEL_OPTION, //Le format de la boîte de dialogue
	      JOptionPane.QUESTION_MESSAGE, //Le format de la boîte de dialogue
	      null, 
	      choixProg, // Contenu des boutons que peux choisir le joueur
	      choixProg[2]); // Choix par défaut
	    
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
		int indexCarte; // c'est l'index de la carte dans la liste de la main
			//On vient remplir la fenêtre de message placée dans l'interface graphique
			gui.message("Selectionnez une carte pour completer votre programme");
			
			//Tant le joueur n'a pas appuyé sur le bouton fini et que sa main n'est pas vide
			
			while(!gui.getEvenementBoutonFini() && !joueurs.get(tourJoueur).getMain().isEmpty()) {
				while(!(gui.getEvenementMain() )) {// on attend que l'utilisateur clique sur une carte
					// Il faut instaurer un temps d'attente entre deux requêtes destinées à l'interface graphique
					try {
					    Thread.sleep(200);
					} catch (InterruptedException e) {

					    e.printStackTrace();
					}
					if(gui.getEvenementBoutonFini()) {//si l'utilisateur a cliqué sur j'ai fini on casse la boucle
						continuer = false; //on met l'indicateur a false afin de ne pas procéder à la routine
						break;
					}
				}
				if(continuer) {
					indexCarte=gui.getCarteSelectionne();//on recupere l'index de la carte choisi par lutilisateur
					joueurs.get(tourJoueur).ajouterInstruction( joueurs.get(tourJoueur).getMain().get(indexCarte) ); //on ajoute la carte dans la file dinstruction
					joueurs.get(tourJoueur).retirerCarte( joueurs.get(tourJoueur).getMain().get(indexCarte)); //on retire la carte de la main du joueur en meme temps on la rajoute dans la defausse
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
		gui.message("Sélectionnez le mur à placer");
		//recuperation de la carte choisie par le joueur
		
		//While de temporisation qui attend qu'un évènement se réalise
		while(!(gui.getEvenementMur() )) {// tant quil n'y a pas devenement// 
			try {
			    Thread.sleep(200);
			} catch (InterruptedException e) {

			    e.printStackTrace();
			}
		}
		//On récupère le type de mur sur lequel a cliqué l'utilisateur
		mur=gui.getMurSelectionne();
		
		//On modifie la variable destructible en fonction du type de mur
		
		if(mur=="Glace") {
			destructible=true;
		}
		else destructible=false;
		
		gui.message("Cliquez sur un endroit où placer le mur");
		
		do {
			while(!(gui.getEvenementPlateau())) {//on attend un evenement de l'utilisateur
				try {
				    Thread.sleep(200);
				} catch (InterruptedException e) {

				    e.printStackTrace();
				}
			}
			
			//On récupère les coordonnées de la case sur laquelle le joueur a cliqué
			
			coordonneeEntree=gui.getCoordonnee();
			y=coordonneeEntree[0];
			x=coordonneeEntree[1];
			
			//Ici, on vient effectuer une serie de vérifications afin de s'assurer que le mur peut être placé
			
			if(caseLibre(x,y)) {
				gui.message("Vous ne pouvez pas placer un mur sur une case occupée");
			}
			if(bloquePasJoyau(x,y, destructible)) {
				gui.message("Vous ne pouvez pas bloquer un joyau avec un mur indestructible");
			}
			if(bloqueJoueur(x,y,destructible)) {
				gui.message("Vous ne pouvez pas enfermer un joueur");
			}
		}while((caseLibre(x,y)) || bloquePasJoyau(x,y, destructible) || bloqueJoueur(x,y,destructible)); // condition du do while
			//permet de redemander une position de case tant que celle-ci ne satisfait pas de conditions
		//On vient placer le nom du mur sélectionné à la case correspondante dans notre tableau plateau
		//On retire également le mur de la liste de murs du joueurs
		plateau[y][x]=joueurs.get(tourJoueur).retirerMur(mur).getNom();
		}
	
	static void executerProgramme() {
		String direction;
		gagnant=false;
		int[] coordonneeSuivante= new int[2];
		int[] coordonneeTortue= new int[2];
		
		ArrayDeque<Carte> file_locale = new ArrayDeque<>();
		file_locale = joueurs.get(tourJoueur).getInstructions();
		
		/*
		 * il faut créer une file d'instruction locale
		 * il faut ensuite l'initiliser avec la file du joueur
		 * une fois que la file du joueur est lu, il faut vider la file du joueur
		 * il n'y a pas besoin de vider la file dans la pioche de defausse car
		 * lorsqu'on retire une carte de la main du joueur pour l'ajouter au programme, on la rajoute aussi dans la pioche de defausse 
		 * le while suivant se fera avec la file locale
		 */

		while(!file_locale.isEmpty()) {

			direction = joueurs.get(tourJoueur).getDirection();
			
			coordonneeTortue=joueurs.get(tourJoueur).getPosition();
			coordonneeSuivante=coordonneeTortue;
			//appel de la fonction coordonneeSuivante qui vas renvoyer les coordonnee suivante en fonction de la direction
			coordonneeSuivante=coordonneeSuivante(coordonneeSuivante[0],coordonneeSuivante[1],direction);
			Carte instruction = file_locale.pollFirst();

			
			switch(instruction.getRole()) {
			case "Bleue":
				
				/*
				 * — Les cartes bleues font avancer la tortue d’une case ;
				 * 
				 * — Si une tortue se heurte à un mur, elle va faire demi-tour. Le programme
				 * continue ensuite son exécution.
				 * — Si une tortue se heurte à une autre tortue, les deux tortues retournent
				 * à leurs positions de départ, et le programme continue son exécution.
				 * - Si une tortue rencontre un joyau, elle gagne directement la partie
				 */
				
				//Si la case suivante est en dehors du plateau la tortue reviens a la position de depart
				if(depassementPlateau(coordonneeSuivante[1],coordonneeSuivante[0])) {
					go_depart_tortue(joueurs.get(tourJoueur).getName());
				}
				
				//Si la prochaine case est vide, donc contient l'élément "rien", alors elle peut s'y déplacer
				else if(!caseLibre(coordonneeSuivante[1],coordonneeSuivante[0])){
					plateau[coordonneeTortue[0]][coordonneeTortue[1]]="rien";
					joueurs.get(tourJoueur).setPosition(coordonneeSuivante[0], coordonneeSuivante[1]);
				}
				else {
					//Nous sommes dans le cas où la prochaine case n'est pas vide

					//En fonction de la détermination de la case suivante, on prévoit le contenu de celle-ci et on effectue une certaine action
					
					switch(plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]) {
					
					//Pour les case Joyaux, on vide la case où était la tortue, on l'ajoute à la liste du classement, et on le retire de la liste de joueurs
					//On met aussi à jours nos variables de tours telles que nbJoueurs et tourJoueur, et on passe le booléen gagnant à true
					
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
						
					// si une tortue rencontre une autre tortue, les deux tortues retournent à leurs position de départ
						
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
			
			// Jaune et Violette : On vient modifier la variable direction de la tortue associée
			case "Jaune":
				joueurs.get(tourJoueur).setDirection(changementDeDirection(-90, direction));
				break;
			case "Violette":
				joueurs.get(tourJoueur).setDirection(changementDeDirection(90, direction));
				break;
			case "Laser":
				
				//la carte laser detruit le mur de glace quil y a a la case suivante
				// s'il n'y a rien a la case suivante on regarde a la case n+1 
				
				
				//while : tant qu'on ne depasse pas le plateau ou que la case suivante est vide
				//on passe a la coordonnée suivante
				while((coordonneeSuivante[1]<0|| coordonneeSuivante[1]>7|| coordonneeSuivante[0]<0|| coordonneeSuivante[0]>7)  ||  (plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]=="rien")) {
					
					coordonneeSuivante=coordonneeSuivante(coordonneeSuivante[0],coordonneeSuivante[1],direction);
				}
				//on verifie dabord que l'on ne depasse pas le plateau
				if(coordonneeSuivante[1]<0|| coordonneeSuivante[1]>7|| coordonneeSuivante[0]<0|| coordonneeSuivante[0]>7) {
					
				}
				else {
					
					//En fonction du contenu de la case aux coordonnées suivantes, on vient effectuer une action
					
					switch(plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]) {
					case "Glace":
						//si la case suivante est un mur de glace, on le detruit
						//On vide la case où était le mur de glace
						plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]="rien";
						gui.updateTableau(plateau);
						
						break;
					case "rien":
						//On n'effectue aucune action si la case était vide
						break;
					/* si la case suivante est un joyau alors : 
					 * le laser est réfléchit et se retourne contre la tortue. 
					 * -Celle-ci fait donc un demi-tour (s’il n’y a que deux joueurs) 
					 * -retourne à sa position de départ (s’il y a plus de deux joueurs).
					 */
					case "JoyauViolet":
						if(joueurs.size()>2) {
							//on appelle une fonction qui renvoie le joueur à sa position de départ grâce à son nom
							go_depart_tortue(joueurs.get(tourJoueur).getName());
						}
						else {
							//demi tour de la tortue
							//on appelle la fonction demitour et on place le résultat dans la variable direction du joueur concerné
							joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						}
						break;
					case "JoyauBleu":
						if(joueurs.size()>2) {
							go_depart_tortue(joueurs.get(tourJoueur).getName());
						}
						else {
							joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						}
						break;
					case "JoyauVert":
						if(joueurs.size()>2) {
							go_depart_tortue(joueurs.get(tourJoueur).getName());
						}
						else {
							joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						}
						break;
						
					//Cas où le laser touche un autre joueur 
					case "Beep":

						// Appel d'une fonction qui, en fonction du nombre de joueurs, va venir modifier les caractéristiques
						// de la tortue touchée : soit lui faire faire un demi tour, soit la renvoyer à sa position initale.
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

			
			//ici on effectue une petite temporisation pour voir la tortue bouger
			if(!gagnant) {
				updatePlateau();
				try {
				    Thread.sleep(500);
				} catch (InterruptedException e) {

				    e.printStackTrace();
				}
			}
			
		}

		
	}
	
	
	static void defausserMain() {
		
		//En fin de tour, on vient demander au joueur s'il souhaite défausser sa main
		
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
	//fonction qui vérifie si la case du plateau est vide, soit contient l'element "rien"
	//renvoie true si la case n'est pas libre
	static boolean caseLibre(int x, int y) {
		//automatiquement si la case saisie est hors plateau, on considère qu'elle n'est pas vide
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
	
	//fonction qui verifie si le mur ne sera pas placé autour dun joyau
	static boolean bloquePasJoyau(int x, int y, boolean destructible) {
		int xJoyau;
		int yJoyau;
		//Si le mur choisi est destructible, nul besoin d'effectuer la vérification car il pourra toujours être détruit par un laser
		if(!destructible) {
			//on vient parcourir tous les joyaux pour etre sur que l'endroit où l'on veut placer notre mur ne bloquera pas un des joyaux
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
	
	// fonction qui vérfie si le mur que l'on va placer n'entourera pas un joueur
	
	static boolean bloqueJoueur(int x, int y, boolean destructible) {
		int xPlayer;
		int yPlayer;
		
		//Si le mur choisi est destructible, nul besoin d'effectuer la vérification

		
		if(!destructible) {
			//int cpt_bloque=0;
			
			for(int i=0; i<nbJoueurs;i++) {
				xPlayer=joueurs.get(i).getPositionX();
				yPlayer=joueurs.get(i).getPositionY();

				//Compteur de mur/obstacles indestrucibles autour d'un joueur
				int cpt_mur=0;
				
				//On vérifie si les coordonnées mises en paramètre ne correspondent pas à une case adjacente à un joueur
				//En effet, s'il s'agit d'une case qui n'est pas collée au joueur, il n'est pas nécessaire d'effectuer la vérification
				
				if((x==xPlayer+1 && y==yPlayer) || (x==xPlayer-1 && y==yPlayer) || (x==xPlayer && y==yPlayer+1) || (x==xPlayer && y==yPlayer-1)) {
					
					//Ici avec cet enchainement de if's, nous allons venir verifier chaque cellule adjacente au joueur (Nord, Sud, Est, Ouest)
					//dans le but de verifier le contenu de ces cases. Si l'on y trouve un mur indestructible (Pierre ou mur de bois), alors 
					//on vient incrémenter notre compteur
					
					// on considère qu'une case hors plateau est équivalente à un mur dans la mesure où elle bloque le joueur
					
					if(depassementPlateau(yPlayer, xPlayer+1)==true) {
						cpt_mur++;
					}
					
					//S'il ne s'agit pas d'une case hors plateau, on vient vérifier que la case contient bien un obstacle indestructible
					
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
	
	
	
	//fonction qui renvoie les coordonnee suivante en fonction de la direction et de la position de la tortue
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
	
	
	
	//fonction qui renvoie l'opposé de la direction reçue
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
		//On parcours les tortues jusqu'à trouver celle sur laquelle on vient de tirer
		
		for(int i=0; i<joueurs.size();i++) {
			Joueur j = joueurs.get(i);
			
			if(j.getName().equals(nom)) {
				//lorsque l'on met la tortue a sa position de depart, il faut mettre rien sur le plateau dans sa position actuelle:
				plateau[j.getPositionY()][j.getPositionX()]="rien";
				//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on récupère sa position de depart
				positionDep=j.getPositionDepart();
				
				//on vient maintenant replacer la tortue à sa position de départ
				j.setPosition(positionDep[0], positionDep[1]);
				j.setDirection("s");//la direction de la sortie est celle de base
			}
			else {}
		}
		
		
	}
	
	static void shoot(String nom) {
		int[] positionDep = new int[2];
		
		if(joueurs.size()>2) {
			
			//On parcours les tortues jusqu'à trouver celle sur laquelle on vient de tirer
			
			for(int i=0; i<joueurs.size();i++) {
				Joueur j = joueurs.get(i);
				
				if(j.getName().equals(nom)) {
					
					plateau[j.getPositionY()][j.getPositionX()]="rien";
					
					//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on récupère sa position de depart
					positionDep=j.getPositionDepart();
					
					//on vient maintenant replacer la tortue à sa position de départ
					j.setPosition(positionDep[0], positionDep[1]);
				}
				else {}
			}
		}
		
		else {
			
			//On parcours les tortues jusqu'à trouver celle sur laquelle on vient de tirer

			for(int i=0; i<joueurs.size();i++) {
				Joueur j = joueurs.get(i);
				
				if(j.getName().equals(nom)) {
					//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on lui fait faire un demi-tour
					j.setDirection(demiTour(j.getDirection()));
				}
				else {}
			}
			
		}
		
		
	}
}
