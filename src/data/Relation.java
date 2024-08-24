package data;

import java.util.ArrayList;

import data.*;

public class Relation {
	private ArrayList<Personne> famille;
	private ArrayList<Personne> amical;
	private ArrayList<Personne> professionnelle;

	public Relation() {
		this.famille = new ArrayList<>();
		this.amical = new ArrayList<>();
		this.professionnelle = new ArrayList<>();
	}

	public ArrayList<Personne> getFamille() {
		return famille;
	}

	public void setFamille(ArrayList<Personne> famille) {
		this.famille = famille;
	}

	public ArrayList<Personne> getAmical() {
		return amical;
	}

	public void setAmical(ArrayList<Personne> amical) {
		this.amical = amical;
	}

	public ArrayList<Personne> getProfessionnelle() {
		return professionnelle;
	}

	public void setProfessionnelle(ArrayList<Personne> professionnelle) {
		this.professionnelle = professionnelle;
	}

}
