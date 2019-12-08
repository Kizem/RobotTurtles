
public class Joyau {
	private int[] position = new int[2];
	private String couleur;
	
	public Joyau(String couleurJoyau, int y , int x) {
		this.couleur=couleurJoyau;
		this.position[0]=y;
		this.position[1]=x;
	}
	public String getCouleur() {
		return this.couleur;
	}
	public int[] getPosition() {
		return this.position;
	}

}
