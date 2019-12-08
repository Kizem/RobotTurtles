import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// un joueur possede une couleur, un nom, une pioche de carte, une main, une liste d'intruction
//TODO: Ajouter les murs !!!!
public class Joueur {
	private String couleur;
	private List<String> main = new ArrayList<String>();
	private List<String> pioche ;
	private List<Mur> murs = new ArrayList<Mur>();
	private List<String> piocheDefausse = new ArrayList<String>();
	private String nom;
	private String direction = "Sud";
	private int[] position = new int[2];
	private ArrayDeque<String> file = new ArrayDeque<>();
	private int[] positionDepart = new int[2];
	



	public Joueur(String couleurTortue, String nomTortue, List<String> piocheDeBase) {
		this.couleur=couleurTortue;
		this.nom=nomTortue;
		//initialisation des murs : 3murs de pierre et 2murs de glace
		this.murs.add(new Mur(false , false, "Pierre"));
		this.murs.add(new Mur(false , false, "Pierre"));
		this.murs.add(new Mur(false , false, "Pierre"));
		this.murs.add(new Mur(false , true, "Glace"));
		this.murs.add(new Mur(false , true, "Glace"));
		//fin d'initialisation
		this.pioche=new ArrayList<String>(piocheDeBase);//on recupere les cartes
		Collections.shuffle(this.pioche);//on melange les cartes
		//on cree la main du joueur
		for(int i=0;i<5; i++) {
			this.main.add(this.pioche.get(0));
			this.piocheDefausse.add(this.pioche.get(0));
			System.out.println(this.pioche.get(0));
			this.pioche.remove(0);
		}
		
	}
	
	
	public String couleurJoueur() {
		return this.couleur;
	}
	public List<String> getMain(){
		return this.main;
	}
	
	//cette methode permet de retirer une carte de la main du joueur et de la mettre dans la liste de defausse
	public void retirerCarte(String carte) {
		this.main.remove(carte);
		this.piocheDefausse.add(carte);
	}
	//on vide toutes les cartes de la main dans la liste de defausse
	
	
	public void defausserMain() {
		for(int i=0; i<this.main.size();i++) {
			this.piocheDefausse.add(this.main.get(i));
		}
		this.main.clear();
	}
	//on pioche des cartes jusqu'a ce que le joueur en ait 5
	public void piocherCarte() {
		while(this.main.size()<5) {
			this.main.add(this.pioche.get(0));
			this.piocheDefausse.add(this.pioche.get(0));
			this.pioche.remove(0);
		}
	}
	
	public String getName() {
		return this.nom;
	}
	//permet de modifier la position du joueur 
	public void setPosition(int y, int x) {
		this.position[0]=y;
		this.position[1]=x;
	}
	
	public int[] getPosition() {
		return this.position;
	}
	public int getPositionX() {
		return this.position[1];
	}
	public int getPositionY() {
		return this.position[0];
	}
	
	public String getDirection() {
		return this.direction;
	}
	//direction de la tortue
	public void setDirection(String dir) {
		this.direction=dir;
	}
	//ajoute une instruction a la file d'instruction du joueur
	public void ajouterInstruction(String instruction) {
		this.file.addLast(instruction);
	}
	
	public ArrayDeque<String> getInstructions(){
		return this.file;
	}
	
	public List<Mur> getMurJoueur(){
		return this.murs;
	}
	
	public void retirerMur(String nomMur) {
		for (Mur mr : murs) {
			if (mr.getNom().equals(nomMur)){
				this.murs.remove(mr);
			}
		}

	}
	public int[] getPositionDepart() {
		return positionDepart;
	}


	public void setPositionDepart(int y, int x) {
		this.positionDepart[0]=y;
		this.positionDepart[1]=x;
	}
	
	public boolean murInList(String nomMur) {
		for(Mur mr : murs) {
			if(mr.getNom().equals(nomMur)) {
				return true;
			}
		}
		return false;
	}
}
