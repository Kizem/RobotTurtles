
public class Mur {
	boolean amovible;
	boolean destructible;
	String nom;
	public Mur(boolean amovible, boolean destructible, String nom) {
		this.amovible=amovible;
		this.destructible=destructible;
		this.nom = nom;
	}
	public boolean isAmovible() {
		return amovible;
	}
	public boolean isDestructible() {
		return destructible;
	}
	public String getNom() {
		return nom;
	}

}
