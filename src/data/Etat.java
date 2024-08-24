package data;

public class Etat {
	private int mentale;
	private int physique;
	private int fatigue;

	public Etat(int mentale, int physique, int fatigue) {
		this.mentale = mentale;
		this.physique = physique;
		this.fatigue = fatigue;
	}

	@Override
	public String toString() {
		return "Etat [mentale=" + mentale + ", physique=" + physique + ", fatigue=" + fatigue + "]";
	}

	public int getMentale() {
		return mentale;
	}

	public void setMentale(int mentale) {
		this.mentale = mentale;
	}

	public int getPhysique() {
		return physique;
	}

	public void setPhysique(int physique) {
		this.physique = physique;
	}

	public int getFatigue() {
		return fatigue;
	}

	public void setFatigue(int fatigue) {
		this.fatigue = fatigue;
	}
}