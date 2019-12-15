import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
//test
public class Main {
	// TODO essayer de faire un plateau de object
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
			
			/* 
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
			
			//TODO Voir comment traiter ici le retrait de la carte etc depuis qu'on est passé à des listes de carte
			
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
			indexCarte=gui.getCarteSelectionne();
			joueurs.get(tourJoueur).ajouterInstruction( joueurs.get(tourJoueur).getMain().get(indexCarte) ); //on ajoute la carte dans la file dinstruction
			joueurs.get(tourJoueur).retirerCarte( joueurs.get(tourJoueur).getMain().get(indexCarte)); //on retire la carte de la main du joueur
			gui.setMain(joueurs.get(tourJoueur).getMain());//actualisation de la main sur la gui
			
			
			//TODO le joueur peut dans tout les cas choisir de retirer une carte
			if(joueurs.get(tourJoueur).getMain().isEmpty()) { //sil na plus de cartes en main, son tour est fini
				System.out.println("Votre tour est fini");
				joueurs.get(tourJoueur).piocherCarte();
				programmeFini=true;
				}
			// TODO refaire le mecanisme de defausse et de fin de tour	
			else {
				
				/* ancienne methode avec la saisie sur linvite de commande
				do {
					System.out.println("Continuer le programme ?");//le joueur peut choisir de continuer le programme ou non
					System.out.println("oui ou non ?");
					reponse = scanner.nextLine();
				}while(!(reponse.equals("oui") || reponse.equals("non")));*/
				
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
		System.out.println(joueurs.get(tourJoueur).getMurJoueur());
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
		System.out.println(joueurs.get(tourJoueur).getInstructions());
		while(!joueurs.get(tourJoueur).getInstructions().isEmpty()) {
			Carte instruction = joueurs.get(tourJoueur).getInstructions().pollFirst();
			//TODO APPLIQUER INSTRUCTION
			System.out.println(instruction);
			
			//on vient placer la carte exécuté dans la pioche de défausse
			joueurs.get(tourJoueur).piocheDefausse.add(instruction);
		}
		
		
	}
	
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


}
