import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// un joueur possede une couleur, un nom, une pioche de carte, une main, une liste d'intruction
public class Joueur {
	private String couleur;
	private List<Carte> main = new ArrayList<Carte>();
	private List<Carte> pioche;
	private List<Mur> murs = new ArrayList<Mur>();
	private List<Carte> piocheDefausse = new ArrayList<Carte>();
	private String nom;
	private String direction;
	private int[] position = new int[2];
	private ArrayDeque<Carte> file = new ArrayDeque<>();
	private int[] positionDepart = new int[2];
	
	public Joueur(String couleurTortue, String nomTortue, List<Carte> piocheDeBase) {
		this.couleur=couleurTortue;
		this.nom=nomTortue;
		this.direction="s";
		//initialisation des murs : 3 murs de pierre et 2 murs de glace
		this.murs.add(new Mur(false , false, "Pierre"));
		this.murs.add(new Mur(false , false, "Pierre"));
		this.murs.add(new Mur(false , false, "Pierre"));
		this.murs.add(new Mur(false , true, "Glace"));
		this.murs.add(new Mur(false , true, "Glace"));
		//fin d'initialisation
		this.pioche=new ArrayList<Carte>(piocheDeBase);//on recupere les cartes de la pioche
		Collections.shuffle(this.pioche);//on melange les cartes
		//on cree la main du joueur
		for(int i=0;i<5; i++) {
			this.main.add(this.pioche.get(0));
			this.pioche.remove(0);
		}
		
	}
	
	public String couleurJoueur() {
		return this.couleur;
	}
	public List<Carte> getMain(){
		return this.main;
	}
	
	//cette methode permet de retirer une carte de la main du joueur et de la mettre dans la liste de defausse
	public void retirerCarte(Carte carte) {
		this.piocheDefausse.add(carte);
		this.main.remove(carte);
	}
	
	//on pioche des cartes jusqu'a ce que le joueur en ait 5
	public void piocherCarte() {

		while(this.main.size()<5) {
			if(this.pioche.isEmpty()) {
				//au cas ou la pioche est vide
				this.pioche.addAll(this.piocheDefausse);
				this.piocheDefausse.clear();
				Collections.shuffle(this.pioche);
			}
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
	public void ajouterInstruction(Carte instruction) {
		this.file.addLast(instruction);
	}
	
	public ArrayDeque<Carte> getInstructions(){
		return this.file;
	}
	
	public List<Mur> getMurJoueur(){
		return this.murs;
	}
	public int[] getNombreMur() {
		int[] tableauNombreMur = {0,0,0};
		for(int i =0; i<this.murs.size(); i++) {
			switch(this.murs.get(i).getNom()) {
			case "Pierre":
				tableauNombreMur[0]++;
				break;
			case "murDeBois":
				tableauNombreMur[1]++;
				break;
			case "Glace":
				tableauNombreMur[2]++;
				break;
			}
		}
		return tableauNombreMur;
	}
	
	public Mur retirerMur(String nomMur) {
		for (Mur mr : murs) {
			if (mr.getNom().equals(nomMur)){
				this.murs.remove(mr);
				return mr;
			}
		}
		return new Mur(true, true, "bois");

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
