package data;

import java.util.ArrayList;

public class Batiment {
	private Position positions;
	private Routine routine;
	private String name;
	private ArrayList<Personne> occupants;
	private int occupantMax;

	public Batiment(String name, Position positions, int occupantMax) {
		this.name = name;
		this.positions = positions;
		this.occupantMax = occupantMax;
		this.occupants = new ArrayList<>();
	}

	public Position getPositions() {
		return positions;
	}

	public void setPositions(Position positions) {
		this.positions = positions;
	}

	public Routine getRoutine() {
		return routine;
	}

	public void setRoutine(Routine routine) {
		this.routine = routine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Personne> getOccupants() {
		return occupants;
	}

	public void setOccupants(ArrayList<Personne> occupants) {
		this.occupants = occupants;
	}

	public int getOccupantMax() {
		return occupantMax;
	}

	public void setOccupantMax(int occupantMax) {
		this.occupantMax = occupantMax;
	}

	public boolean ajouter(Personne personne) {
		if (occupants.size() < occupantMax) {
			occupants.add(personne);
			return true;
		} else {
			System.out.println("Le bâtiment " + name + " est plein. Impossible d'ajouter " + personne.getName());
			return false;
		}
	}

	@Override
	public String toString() {
		return "Batiment{" + "positions=" + positions + ", routine=" + routine + ", name='" + name + '\''
				+ ", occupants=" + occupants + ", occupantMax=" + occupantMax + '}';
	}
}
