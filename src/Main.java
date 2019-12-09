import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//test
public class Main {
	// TODO essayer de faire un plateau de object
	public static String plateau[][] = new String[8][8];
	public static int nbJoueurs;
	public static int tourJoueur;//variable indiquant à quel joueur est-ce le tour de jouer
	public static String[] couleurs = {"Bleu","Rouge","Vert","Rose"}; //tableau des couleurs pour chaque joueurs
	public static String[] nomsTortue = {"Beep", "Pi", "Pangie", "Dot"};//tableau des noms de tortue
	
	static List<Joueur> joueurs = new ArrayList<>();
	//static Joueur[] joueurs; // tableau des objets joueurs// check index list si ca commence par zero ou un
	
	static List<Joyau> joyaux = new ArrayList<>();
	
	public static Scanner scanner = new Scanner(System.in);	
	public static void main(String[] args) {
		initialisation();
		while(!finDuJeu()) {
		updatePlateau();
		choixJoueur();
		joueurSuivant();
		}
	}
	
	public static void initialisation()
	{
		//initialisation de la pioche de base
		// TODO créer l'objet carte au lieu de string
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
		
		//choix du nombre de joueurs
		System.out.println("Combien de joueurs (2 à 4 joueurs autorisés)");
		do {
			nbJoueurs=scanner.nextInt();
		}while(nbJoueurs<2 || nbJoueurs>4);
		
		//joueurs = new Joueur[nbJoueurs];
		
		//on cree le nombre de joueurs avec une couleur et un nom
		for(int i = 0; i<nbJoueurs; i++) {
			
			//joueurs[i] = new Joueur(couleurs[i], nomsTortue[i], pioche);
			joueurs.add( new Joueur(couleurs[i], nomsTortue[i], pioche));
		}
		
		//placement des joueurs sur le plateau et des joyaux
		
		switch(nbJoueurs) {
		case 2:
			joueurs.get(0).setPosition(0,1);
			joueurs.get(0).setPositionDepart(0,1);
			joueurs.get(1).setPosition(0,5);
			joueurs.get(1).setPositionDepart(0,5);
			joyaux.add(new Joyau("Vert", 7, 3));
			plateau[7][3]="JoyauxVert";
			for(int i=0; i<8;i++) {
				plateau[i][7]="murDeBois";
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
				plateau[i][7]="murDeBois";
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
			plateau[7][0]="JoyauxViolet";
			plateau[7][6]="JoyauxBleu";
			break;
		}	
		tourJoueur= (int) (Math.random() * (nbJoueurs-1));
	}
	
	
	
	public static void updatePlateau() {
		for(int i=0; i < joueurs.size();i++) {
			plateau[joueurs.get(i).getPositionY()][joueurs.get(i).getPositionX()]=joueurs.get(i).getName();
		}
		//TODO ajouter affichage joyaux
		for(int i=0; i< (plateau.length); i++) {
			for(int j =0; j <(plateau[0].length); j++) {
				System.out.print(plateau[i][j]);
			}
			System.out.println();
		}
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
		int choix;
		System.out.println("C'est le tour de : " + joueurs.get(tourJoueur).getName());
		System.out.println("Choisissez entre ces trois option :");
		System.out.println("1 - Compléter le programme");
		System.out.println("2 - Construire un mur");
		System.out.println("3 - Exécuter le programme");
		do {
			choix = scanner.nextInt();
		}while(choix<1 || choix>3);
		
		switch(choix) {
		case 1:
			completerProgramme();
			break;
		case 2:
			construireMur();
			break;
		case 3:
			executerProgramme();
			break;
		}
	}
	
	static void completerProgramme() {
		boolean programmeFini=false;
		//recuperation de la main du joueur
		String carte;
		String reponse;
		while(!(programmeFini)) {
			System.out.println(joueurs.get(tourJoueur).getMain());
			System.out.println("Entrez le nom de la carte pour réaliser votre programme");
			//recuperation de la carte choisi par le joueur
			do {
				System.out.println("attention, vous devez posseder ces cartes");
				carte= scanner.nextLine();
			}while(!(joueurs.get(tourJoueur).getMain().contains(carte)));//on verifie que le joueur possede bien cette carte
			joueurs.get(tourJoueur).ajouterInstruction(carte);//on ajoute la carte a la file d'instruction
			joueurs.get(tourJoueur).retirerCarte(carte);// on retire la carte des mains du joueur
			
			//TODO le joueur peut dans tout les cas choisir de retirer une carte
			if(joueurs.get(tourJoueur).getMain().isEmpty()) { //sil na plus de cartes en main, son tour est fini
				System.out.println("Votre tour est fini");
				joueurs.get(tourJoueur).piocherCarte();
				programmeFini=true;
				}
			// TODO refaire le mecanisme de defausse et de fin de tour	
			else {
				do {
					System.out.println("Continuer le programme ?");//le joueur peut choisir de continuer le programme ou non
					System.out.println("oui ou non ?");
					reponse = scanner.nextLine();
				}while(!(reponse.equals("oui") || reponse.equals("non")));
				if(reponse.equals("non")) {
					programmeFini=true;
					System.out.println("Défausser votre main ?");//sil arrete, il peut choisir de defausser sa main
					do {
						
						System.out.println("oui ou non ?");
						reponse = scanner.nextLine();
					}while(!(reponse.equals("oui") || reponse.equals("non")));
					if(reponse.contentEquals("oui")) {
						
						joueurs.get(tourJoueur).defausserMain();
						joueurs.get(tourJoueur).piocherCarte();
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
		do {
			System.out.println("attention, vous devez posseder ce murs");
			mur=scanner.nextLine();
		}while(!(joueurs.get(tourJoueur).murInList(mur)));
		System.out.println("Entrez les coordonnées ou vous souhaiter placer ce mur");
		do {
			System.out.println("Entrez une valeur correcte");
			System.out.println("X = ?");
			x=scanner.nextInt();
			System.out.println("Y = ?");
			y=scanner.nextInt();
		}while(!(caseLibre(x,y)));
		plateau[y][x]=mur;
		//TODO ajouter defausser sa main
	}
	
	static void executerProgramme() {
		System.out.println(joueurs.get(tourJoueur).getInstructions());
		while(joueurs.get(tourJoueur).getInstructions().isEmpty()) {
			String instruction = joueurs.get(tourJoueur).getInstructions().pollFirst();
			//TODO APPLIQUER INSTRUCTION
			System.out.println(instruction);
		}
		// TODO ajouter defausser sa main
		
	}
	
	static boolean caseLibre(int x, int y) {
		if(x<0|| x>7|| y<0|| y>7) {
			return false;
		}
		if(plateau[x][y]==null) {
			return true;
		}
		else return false;
	}


}
