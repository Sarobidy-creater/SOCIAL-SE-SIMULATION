package traitement;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.*;
import log.LoggerUtility;
import traitement.*;
import parametreSimulation.*;


public class Launcher {
	private static Logger logger = LoggerUtility.getLogger(Launcher.class, "html");
	private static final String PRENOM_FILE_PATH = "prenom.txt";
	private static final String METIER_FILE_PATH = "metiers.txt";

	private int population;
	private PersonneBuilder builder;
	private ArrayList<Personne> habitants;
	private Ville ville;

	public Launcher(Ville ville, int population) {
		this.builder = new PersonneBuilder(PRENOM_FILE_PATH, METIER_FILE_PATH);
		this.population = population;
		this.ville = ville;
		this.habitants = new ArrayList<>();
	}

	public ArrayList<Personne> creer_habitants(int population) throws IOException, FullHousingException {
		logger.info("Creation des habitants avec un nombre de: " + population);
		habitants = new ArrayList<>();
		ArrayList<Batiment> habitation = ville.buildBatiment();
		ArrayList<Batiment> workspace = ville.buildEntreprise();
		ArrayList<Batiment> appartements = ville.buildAppartement();

		for (int i = 0; i < population; i++) {
			Personne personne = builder.buildPersonne();
			boolean personneAjoutee = false;

			// Tente d'ajouter la personne à une habitation
			for (Batiment batiment : habitation) {
				if (batiment.ajouter(personne)) {
					personne.setMaison(batiment);
					personne.setPositions(batiment.getPositions());
					personneAjoutee = true;
					break;
				}
			}

			// Si aucune habitation n'est disponible, tente avec les appartements
			if (!personneAjoutee) {
				for (Batiment appartement : appartements) {
					if (appartement.ajouter(personne)) {
						personne.setMaison(appartement);
						personne.setPositions(appartement.getPositions());
						personneAjoutee = true;
						break;
					}
				}
			}

			// Ajoute la personne à la liste des habitants si une habitation a été trouvée
			if (personneAjoutee) {
				habitants.add(personne);
			} else {
				throw new FullHousingException("Toutes les habitations et tous les appartements sont pleins.");
			}

			// Attribue un lieu de travail à la personne, indépendamment de l'habitation
			boolean travailAttribue = false;
			for (Batiment lieuTravail : workspace) {
				if (lieuTravail.ajouter(personne)) {
					personne.setLieuTravail(lieuTravail);
					travailAttribue = true;
					break;
				}
			}

			// Optionnel : gérer le cas où aucun lieu de travail n'est disponible
			if (!travailAttribue) {
				System.err.println(
						"Toutes les entreprises sont pleines. " + personne.getName() + " n'a pas de lieu de travail.");
			}
		}

		return habitants;
	}

	public class FullHousingException extends Exception {
		public FullHousingException(String message) {
			super(message);
		}
	}

}
