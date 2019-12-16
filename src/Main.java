import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
//test

//TODO : Ajouter la rotation de l'image tortue




//TODO : Rogner les images de tortues pour avoir les bonnes couleurs

public class Main {
	public static String plateau[][] = new String[8][8];
	public static int nbJoueurs;
	public static int tourJoueur;//variable indiquant à quel joueur est-ce le tour de jouer
	public static String[] couleurs = {"Bleu","Rouge","Vert","Rose"}; //tableau des couleurs pour chaque joueurs
	public static String[] nomsTortue = {"Beep", "Pi", "Pangie", "Dot"};//tableau des noms de tortue
	public static int[] coordonneeEntree = new int[2];
	static List<Joueur> joueurs = new ArrayList<>();
	//static Joueur[] joueurs; // tableau des objets joueurs// check index list si ca commence par zero ou un
	
	static List<Joyau> joyaux = new ArrayList<>();
	public static InterfaceGraphique gui;
	public static Scanner scanner = new Scanner(System.in);	
	public static void main(String[] args) {
		initialisation();
		gui = new InterfaceGraphique();
		updatePlateau();
	     
		while(!finDuJeu()) {
			updatePlateau();
			choixJoueur();
			joueurSuivant();
		}
	}
	
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
		Integer[] nombre = {2, 3, 4};
	    JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
	    nbJoueurs = (int)jop.showInputDialog(null, 
	      "Veuillez indiquer le nombre de joueur",
	      "Nombre de joueurs",
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      nombre,
	      nombre[0]);
	    
	    System.out.println(nbJoueurs);
		//choix du nombre de joueurs
		/*System.out.println("Combien de joueurs (2 à 4 joueurs autorisés)");
		do {
			nbJoueurs=scanner.nextInt();
		}while(nbJoueurs<2 || nbJoueurs>4);
		*/
		//joueurs = new Joueur[nbJoueurs];
		
		//on cree le nombre de joueurs avec une couleur et un nom
		for(int i = 0; i<nbJoueurs; i++) {
			
			//joueurs[i] = new Joueur(couleurs[i], nomsTortue[i], pioche);
			joueurs.add( new Joueur(couleurs[i], nomsTortue[i], pioche));
		}
		
		//placement des joueurs sur le plateau et des joyaux
		for(int i=0;i<plateau.length;i++) {
			for(int j=0;j<plateau[0].length;j++) {
				plateau[i][j]="rien";
			}
		}
		switch(nbJoueurs) {
		case 2:
			joueurs.get(0).setPosition(0,1);
			joueurs.get(0).setPositionDepart(0,1);
			joueurs.get(1).setPosition(0,5);
			joueurs.get(1).setPositionDepart(0,5);
			joyaux.add(new Joyau("Vert", 7, 3));
			plateau[7][3]="JoyauxVert";
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
			plateau[7][3]="JoyauxVert";
			plateau[7][0]="JoyauxViolet";
			plateau[7][6]="JoyauxBleu";
			for(int i=0; i<8;i++) {
				plateau[i][7]=(new Mur(true, true,"murDeBois")).getNom();
			}
			break;
		case 4:
			joueurs.get(0).setPosition(0,0);
			joueurs.get(1).setPosition(0,2);
			joueurs.get(2).setPosition(0,5);
			joueurs.get(3).setPosition(0,7);
			
			joueurs.get(0).setPositionDepart(0,0);
			joueurs.get(1).setPositionDepart(0,2);
			joueurs.get(2).setPositionDepart(0,5);
			joueurs.get(3).setPositionDepart(0,7);
			
			joyaux.add(new Joyau("Violet", 7, 1));
			joyaux.add(new Joyau("Bleu", 7, 6));
			//mettre plutot les coordonnee en appelant l'objet joyau au lieu de mettre les coordonnées brutes
			plateau[7][1]="JoyauxViolet";
			plateau[7][6]="JoyauxBleu";
			break;
		}	
		tourJoueur= (int) (Math.random() * (nbJoueurs-1));
	}
	
	
	
	public static void updatePlateau() {
		//TODO afficher le nombre de mur restant a l'utilisateur sur linterface graphique
		joueurs.get(tourJoueur).piocherCarte(); // le joueur pioche des carte jusqua en avoir 5
		for(int i=0; i < joueurs.size();i++) {
			plateau[joueurs.get(i).getPositionY()][joueurs.get(i).getPositionX()]=joueurs.get(i).getName();
		}
		/*//plus besoin avec linterface graphuiqye
		for(int i=0; i< (plateau.length); i++) {
			for(int j =0; j <(plateau[0].length); j++) {
				System.out.print(plateau[i][j]);
			}
			System.out.println();
		}*/
		gui.setMain(joueurs.get(tourJoueur).main);
		gui.updateTableau(plateau);
		
	}
	
	public static boolean finDuJeu() {
		boolean test=false;
		for(int i=0; i < joueurs.size();i++) {
			for(int j=0; j< joyaux.size(); j++) {
				if(joueurs.get(i).getPosition()==joyaux.get(j).getPosition()) {
					test=true;
				}
			}
		}
		return test;
	}
	
	public static void joueurSuivant() {
		if(tourJoueur==(nbJoueurs-1)) {
			tourJoueur=0;
		}
		else tourJoueur++;
	}
	
	public static void choixJoueur() {
		String message = (joueurs.get(tourJoueur).getName()+" que souhaitez-vous faire ?");
		String[] choixProg = {"Compléter le programme", "Construire un mur", "Exécuter le programme"};
	    JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
	    int rang = jop.showOptionDialog(null, 
	      message,
	      "Choix",
	      JOptionPane.YES_NO_CANCEL_OPTION,
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      choixProg,
	      choixProg[2]);
	    
	    switch(rang) {
		case 0:
			completerProgramme();
			break;
		case 1:
			construireMur();
			break;
		case 2:
			executerProgramme();
			break;
		}
	    
	}
	
	static void completerProgramme() {
		boolean programmeFini=false;
		
		
		String role;
		int indexCarte; // c'est lindex de la carte dans la liste de la main
		String reponse;
		List<String> main_roles = new ArrayList<String>();
		Carte carte = new Carte("");
		while(!(programmeFini)) {
			
			/* jai mis en commentaire, plus besoin de ca pcq on choisi lindex de la main avec l'interface graphique
			//cette boucle nous permet de récupérer les rôles associées aux cartes de la main pour pouvoir les afficher
			main_roles.clear(); //nécessaires pour réafficher la main à chaque coup
			for(int i=0; i<joueurs.get(tourJoueur).getMain().size();i++) {
				main_roles.add(joueurs.get(tourJoueur).main.get(i).role);
			}
			
			
			System.out.println(main_roles);
			
			System.out.println("Entrez le nom de la carte pour réaliser votre programme");
			//recuperation de la carte choisi par le joueur
			do {
				System.out.println("attention, vous devez posseder ces cartes");
				role=scanner.nextLine();
				carte.role = role;
			}
			while(!(main_roles.contains(role)));//on verifie que le joueur possede bien cette carte, étant donné que main_roles
												// recence les cartes dans la main sous forme de string
			
			//while(!(joueurs.get(tourJoueur).getMain().contains(carte)));//on verifie que le joueur possede bien cette carte
			
			
			joueurs.get(tourJoueur).ajouterInstruction(carte);//on ajoute la carte a la file d'instruction
			joueurs.get(tourJoueur).retirerCarte(carte);// on retire la carte des mains du joueur
			*/
			System.out.println("Entrez le nom de la carte pour réaliser votre programme");
			while(!(gui.getEvenementMain() )) {// tant quil ny a pas devenement// on attend que lutilisateur clique sur une carte
				try {
				    Thread.sleep(200);
				} catch (InterruptedException e) {

				    e.printStackTrace();
				}
			}
			indexCarte=gui.getCarteSelectionne();//on recupere l'index de la carte choisi par lutilisateur
			joueurs.get(tourJoueur).ajouterInstruction( joueurs.get(tourJoueur).getMain().get(indexCarte) ); //on ajoute la carte dans la file dinstruction
			joueurs.get(tourJoueur).retirerCarte( joueurs.get(tourJoueur).getMain().get(indexCarte)); //on retire la carte de la main du joueur
			gui.setMain(joueurs.get(tourJoueur).getMain());//actualisation de la main sur la gui
			
			
			if(joueurs.get(tourJoueur).getMain().isEmpty()) { //sil na plus de cartes en main, son tour est fini
				System.out.println("Votre tour est fini");
				joueurs.get(tourJoueur).piocherCarte();
				programmeFini=true;
				}
			else {
				
				/* ancienne methode avec la saisie sur linvite de commande
				do {
					System.out.println("Continuer le programme ?");//le joueur peut choisir de continuer le programme ou non
					System.out.println("oui ou non ?");
					reponse = scanner.nextLine();
				}while(!(reponse.equals("oui") || reponse.equals("non")));*/
				
				//TODO plutot attendre que lutilisateur clique sur j'ai fini au lieu de demander s'il veut continuer le programme
				// faire comme defausser main
				JOptionPane jop = new JOptionPane();    	
			      int option = jop.showConfirmDialog(null, 
			        "Voulez-vous continuer le programme ?", 
			        "Choix utilisateur", 
			        JOptionPane.YES_NO_OPTION, 
			        JOptionPane.QUESTION_MESSAGE);
				
				if(option == 1) {
					programmeFini=true;
					System.out.println("Défausser votre main ?");//s'il arrete, il peut choisir de defausser sa main
					/* ancienne methode avec saisie au clavier
					do {
						System.out.println("oui ou non ?");
						reponse = scanner.nextLine();
					}while(!(reponse.equals("oui") || reponse.equals("non")));*/
						
				       option = jop.showConfirmDialog(null, 
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
						/*
						System.out.println("Voulez-vous défausser une ou toutes vos cartes ?");
						reponse = scanner.nextLine();
						
						do {
							
							System.out.println("une ou toutes ?");
							reponse = scanner.nextLine();
							
						}while(!(reponse.equals("une") || reponse.equals("toutes")));
						
						if(reponse.contentEquals("une")) {
							
							System.out.println("Quelle carte ?");
							
						}
						else {
							joueurs.get(tourJoueur).defausserMain(reponse);
							joueurs.get(tourJoueur).piocherCarte();
						}*/
					}
				}
				
			}		
		}
	}
	
	static void construireMur() {
		String mur;
		int x;
		int y;
		System.out.println("Entrez le nom du mur que vous avez choisis");
		//recuperation de la carte choisi par le joueur
		while(!(gui.getEvenementMur() )) {// tant quil ny a pas devenement// on a plus besoin de ca while(!(joueurs.get(tourJoueur).murInList(mur)));
			//System.out.println(gui.getEvenementMur());
			try {
			    Thread.sleep(200);
			} catch (InterruptedException e) {

			    e.printStackTrace();
			}
		}
		mur=gui.getMurSelectionne();
		System.out.println(mur);
		System.out.println("Entrez les coordonnées ou vous souhaiter placer ce mur");
		
		do {
			while(!(gui.getEvenementPlateau())) {//on attend un evenement de l'utilisateur
				//System.out.println(gui.getEvenementPlateau());
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
		}while((caseLibre(x,y)) || bloquePasJoyau(x,y));
		plateau[y][x]=joueurs.get(tourJoueur).retirerMur(mur).getNom();
		
		//TODO ajouter defausser sa main
	}
	
	static void executerProgramme() {
		String direction;
		int[] coordonneeSuivante= new int[2];
		int[] coordonneeTortue= new int[2];
		int[] positionDepart= new int[2];
		System.out.println(joueurs.get(tourJoueur).getInstructions());
		//TODO le mecanisme de recuperation d'instruction est faux
		/*
		 * il faut créer une file d'instrucion locale
		 * il faut ensuite l'initiliser avec la file du joueur
		 * une fois que la file du joueur est lu, il faut vider la file du joueur
		 * il n'y a pas besoin de vider la file dans la pioche de defausse pcq
		 * lorsquon retire une carte de la main du joueur pour lajouter au programme, on la rajoute aussi dans la pioche de defausse 
		 * je crois si ce n'est pas fait, il faut le faire la
		 * le while suivant se fera avec la file locale
		 */
		while(!joueurs.get(tourJoueur).getInstructions().isEmpty()) {
			direction = joueurs.get(tourJoueur).getDirection();
			
			coordonneeTortue=joueurs.get(tourJoueur).getPosition();
			coordonneeSuivante=coordonneeTortue;
			//appel de la fonction coordonneeSuivante qui vas renvoyer les coordonnee suivante en fonction de la direction
			coordonneeSuivante=coordonneeSuivante(coordonneeSuivante[0],coordonneeSuivante[1],direction);
			Carte instruction = joueurs.get(tourJoueur).getInstructions().pollFirst();
			//TODO APPLIQUER INSTRUCTION
			System.out.println(instruction);
			switch(instruction.getRole()) {
			case "Bleue":
				//TODO cest la partie la plus difficile, il y a beaucoup de regles : 
				/*
				 * — Les cartes bleues font avancer la tortue d’une case ;
				 * 
				 * — Si une tortue se heurte à un mur, elle va faire demi-tour. Le programme
				 * continue ensuite son exécution.
				 * — Si une tortue se heurte à une autre tortue, les deux tortues retournent
				 * à leurs positions de départ, et le programme continue son exécution.
				 * 
				 * aussi bah si la tortue rencontre un joyau, elle gagne directement et du coup faire un break
				 */
				
				break;
			case "Jaune":
				joueurs.get(tourJoueur).setDirection(changementDeDirection(-90, direction));
				break;
			case "Violette":
				joueurs.get(tourJoueur).setDirection(changementDeDirection(90, direction));
				break;
			case "Laser":
				
				//TODO finir cette condition entierement
				//la carte laser detruit le mur de glace quil y a a la case suivant
				
				//on verifie dabord quon depasse pas le plateau
				if(coordonneeSuivante[1]<0|| coordonneeSuivante[1]>7|| coordonneeSuivante[0]<0|| coordonneeSuivante[0]>7) {
					
				}
				else {
					switch(plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]) {
					case "Glace":
						System.out.println("ceeeeee bon!!!!");
						plateau[coordonneeSuivante[0]][coordonneeSuivante[1]]="rien";
						gui.updateTableau(plateau);
						//si la case suivante est un mur de glace, on le detruit
						break;
					case "rien":
						break;
					/* si la case suivante est un joyau alors : 
					 * le laser est réfléchi et se retourne contre la tortue. 
					 * -Celle-ci fait donc un demi-tour (s’il n’y a que deux joueurs) 
					 * -retourne à sa position de départ (s’il y a plus de deux joueurs).
					 */
					case "JoyauViolet":
						if(joueurs.size()>2) {
							//on recupere la position de depart de la tortue
							positionDepart=joueurs.get(tourJoueur).getPositionDepart();
							joueurs.get(tourJoueur).setPosition(positionDepart[0], positionDepart[1]);
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
							positionDepart=joueurs.get(tourJoueur).getPositionDepart();
							joueurs.get(tourJoueur).setPosition(positionDepart[0], positionDepart[1]);
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
							positionDepart=joueurs.get(tourJoueur).getPositionDepart();
							joueurs.get(tourJoueur).setPosition(positionDepart[0], positionDepart[1]);
							
						}
						else {
							//demi tour de la tortue
							//on appelle la fonction demitour qui prend en parametre une direction et qui renvoie la direction opposé
							joueurs.get(tourJoueur).setDirection(demiTour(joueurs.get(tourJoueur).getDirection()));
						}
						break;

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
			
			//on vient placer la carte exécuté dans la pioche de défausse
			joueurs.get(tourJoueur).piocheDefausse.add(instruction);
		}
		
		
	}
	
	//fonction qui vérifie si la case du plateau possede rien
	static boolean caseLibre(int x, int y) {
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
	
	//fonction qui verifie si le mur ne sera pas placé autour dun joyau
	static boolean bloquePasJoyau(int x, int y) {
		int xJoyau;
		int yJoyau;
		for(int i=0; i<joyaux.size();i++) {
			xJoyau=joyaux.get(i).getX();
			yJoyau=joyaux.get(i).getY();
			if((x == xJoyau) || (x == xJoyau+1) || (x == xJoyau-1) ){
				if((y == yJoyau) || (y == yJoyau+1) || (y == yJoyau-1)){
					return true;
				}
			}
			
			
		}
		return false;
	}
	
	
	
	//fonction permettant de trouver la nouvelle direction de la tortue
	static String changementDeDirection(int rotation, String direction) {
		String newDirection=direction;
		switch(direction) {
		case "s":
			if(rotation==90) {
				newDirection ="e";
			}
			else {
				newDirection ="o";
			}
			break;
		case "n":
			if(rotation==90) {
				newDirection ="o";
			}
			else {
				newDirection ="e";
			}
			break;
		case "e":
			if(rotation==90) {
				newDirection ="n";
			}
			else {
				newDirection ="s";
			}
			break;
		case "o":
			if(rotation==90) {
				newDirection ="s";
			}
			else {
				newDirection ="n";
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
	
	
	
	//fonction qui renvoie loppose de la direction recu
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
			
			if(j.nom.equals(nom)) {
				//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on récupère sa position de depart
				positionDep=j.getPositionDepart();
				
				//on vient maintenant replacer la tortue à sa position de départ
				j.setPosition(positionDep[0], positionDep[1]);
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
				
				if(j.nom.equals(nom)) {
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
				
				if(j.nom.equals(nom)) {
					//maintenant que l'on sait qu'on est bien sur la bonne tortue, alors on lui fait faire un demi-tour
					j.setDirection(demiTour(j.getDirection()));
				}
				else {}
			}
			
		}
		
		
	}
}
