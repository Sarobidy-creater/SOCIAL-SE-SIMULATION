package parametreSimulation;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Personne;
import data.Relation;
import data.Ville;
import log.LoggerUtility;
import traitement.Launcher;
import traitement.PersonneBuilder;
import traitement.Launcher.FullHousingException;


public class Communaute {
	private int nombre_hab;

	private ArrayList<Personne> habitants;
	private Ville ville;
	private ArrayList<Relation> relations;

	public Communaute(int nombre_hab) throws IOException, FullHousingException {
		this.nombre_hab = nombre_hab;
		this.ville = new Ville();
		Launcher nouveau = new Launcher(ville,nombre_hab);
		this.habitants = nouveau.creer_habitants(nombre_hab);

	}

	public int getNombre_hab() {
		return nombre_hab;
	}

	public void setNombre_hab(int nombre_hab) {
		this.nombre_hab = nombre_hab;
	}

	public ArrayList<Personne> getHabitants() {
		return habitants;
	}

	public void setHabitants(ArrayList<Personne> habitants) {
		this.habitants = habitants;
	}

}
