public class Joyau {
	private int[] position = new int[2];
	private String couleur;
	private int x;
	private int y;
	public Joyau(String couleurJoyau, int y , int x) {
		this.couleur=couleurJoyau;
		this.position[0]=y;
		this.position[1]=x;
		this.x=x;
		this.y=y;
	}
	public String getCouleur() {
		return this.couleur;
	}
	public int[] getPosition() {
		return this.position;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	

}
