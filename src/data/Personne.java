package data;

import java.util.Random;

public class Personne {
	private String name;
	private int age;
	private Batiment maison;
	private Position positions;
	private Profession professions;
	private Etat etats;
	private Personality personality;
	private Relation relation;
	private int santeMentale;
	private int santePhysique;
	private int fatigue;
	private Batiment lieuTravail;

	public Batiment getLieuTravail() {
		return lieuTravail;
	}

	public void setLieuTravail(Batiment lieuTravail) {
		this.lieuTravail = lieuTravail;
	}

	public Personne(String name, int age, Profession professions, Personality personality) {
		this.name = name;
		this.age = age;
		this.personality = personality;
		this.professions = professions;
		initializeSanteMentale();
		initializeSantePhysique();
		initializeFatigue();
		this.etats = new Etat(santeMentale, santePhysique, fatigue);
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public Profession getProfessions() {
		return professions;
	}

	public void setProfessions(Profession professions) {
		this.professions = professions;
	}

	@Override
	public String toString() {
		return "Personne [name=" + name + ", age=" + age + ", maison=" + maison + ", positions=" + positions
				+ ", etats=" + etats + ", personalité=" + getPersonality() + "]";
	}

	public Etat getEtats() {
		return etats;
	}

	public void setEtats(Etat etats) {
		this.etats = etats;
	}

	public Position getPositions() {
		return positions;
	}

	public void setPositions(Position positions) {
		this.positions = positions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Batiment getMaison() {
		return maison;
	}

	public void setMaison(Batiment maison) {
		this.maison = maison;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	private void initializeSanteMentale() {
		santeMentale = new Random().nextInt(101);
		while (santeMentale < 20) {
			santeMentale = new Random().nextInt(101);
		}
	}

	public void updatesanteMentale(int delta) {
		santeMentale += delta;

		santeMentale = Math.max(0, Math.min(100, santeMentale));
	}

	public int getSanteMentale() {
		return santeMentale;
	}

	private void initializeFatigue() {
		fatigue = new Random().nextInt(101);
		while (fatigue < 20) {
			fatigue = new Random().nextInt(101);
		}
	}

	public void updateFatigue(int delta) {
		fatigue += delta;

		fatigue = Math.max(0, Math.min(100, fatigue));
	}

	public int getFatigue() {
		return fatigue;
	}

	private void initializeSantePhysique() {
		santePhysique = new Random().nextInt(101);
		while (santePhysique < 20) {
			santePhysique = new Random().nextInt(101);
		}
	}

	public void updateSantePhysique(int delta) {

		santePhysique += delta;

		santePhysique = Math.max(0, Math.min(100, santePhysique));
	}

	public int getSantePhysique() {
		return santePhysique;
	}

	public boolean isPositive() {
		return santeMentale > 50;
	}

}
