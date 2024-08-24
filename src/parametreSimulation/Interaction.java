package parametreSimulation;

import org.apache.log4j.Logger;

import data.Personne;
import log.LoggerUtility;

public class Interaction {

	public static void detectCollisions(Personne personne1, Personne personne2) {
		if (personne1.getPositions().getX() == personne2.getPositions().getX()
				&& personne1.getPositions().getY() == personne2.getPositions().getY()) {
			System.out.println(personne1.getName() + " et " + personne2.getName() + " sont en collision !");
			// Ici vous pouvez ajouter du code pour gérer la collision, si nécessaire
		}
	}

	public static void simulateDiscussion(Personne personne1, Personne personne2) {
		// Simulation de la discussion
		String topic = generateTopic();
		String response1 = generateResponse(personne1, topic);
		String response2 = generateResponse(personne2, topic);

		// Affichage des réponses dans l'IHM
		 displayResponse(personne1, response1, personne2, topic);
		 displayResponse(personne2, response2, personne1, topic);

		// Influence sur l'état de santé mentale des personnes en fonction de leurs
		// humeurs
		if (personne1.getSanteMentale() > 50 && personne2.getSanteMentale() > 50) {
			personne1.updatesanteMentale(12);
			personne2.updatesanteMentale(-15);
		} else if (personne1.getSanteMentale() <= 50 && personne2.getSanteMentale() <= 50) {
			personne1.updatesanteMentale(12);
			personne2.updatesanteMentale(7);
		} else {
			int influence = (int) (Math.random() * 2); // Génère soit 0 soit 1 de manière aléatoire
			if (influence == 0) {
				personne1.updatesanteMentale(-7);
				personne2.updatesanteMentale(6);
			} else {
				personne1.updatesanteMentale(13);
				personne2.updatesanteMentale(-14);
			}
		}
	}

	private static String generateTopic() {
		// Génération aléatoire d'un sujet de discussion
		String[] topics = { "maison", "travail", "ecole", "neutre" };
		int randomIndex = (int) (Math.random() * topics.length);
		return topics[randomIndex];
	}

	private static String generateResponse(Personne personne, String topic) {
		// Génération de la réponse en fonction de la personnalité de la personne et du
		// sujet
		String response = "";
		switch (topic) {
		case "maison":
			response = generateHomeResponse(personne);
			break;
		case "travail":
			response = generateWorkResponse(personne);
			break;
		case "ecole":
			response = generateSchoolResponse(personne);
			break;
		case "neutre":
			response = generateNeutralResponse(personne);
			break;
		}
		return response;
	}

	private static String generateHomeResponse(Personne personne) {
		// Génération de la réponse en fonction du contexte "maison"
		if (personne.isPositive()) {
			return personne.getPersonality().generateNegativeHomeResponse();
		} else if (!personne.isPositive()) {
			return personne.getPersonality().generateNegativeHomeResponse();
		} else {
			return personne.getPersonality().generateNeutralHomeResponse();
		}
	}

	private static String generateWorkResponse(Personne personne) {
		// Génération de la réponse en fonction du contexte "travail"
		if (personne.isPositive()) {
			return personne.getPersonality().generatePositiveWorkResponse();
		} else if (!personne.isPositive()) {
			return personne.getPersonality().generateNegativeWorkResponse();
		} else {
			return personne.getPersonality().generateNeutralWorkResponse();
		}
	}

	private static String generateSchoolResponse(Personne personne) {
		// Génération de la réponse en fonction du contexte "ecole"
		if (personne.isPositive()) {
			return personne.getPersonality().generatePositiveSchoolResponse();
		} else if (!personne.isPositive()) {
			return personne.getPersonality().generateNegativeSchoolResponse();
		} else {
			return personne.getPersonality().generateNeutralSchoolResponse();
		}
	}

	private static String generateNeutralResponse(Personne personne) {
		// Génération de la réponse en fonction du contexte neutre
		if (personne.isPositive()) {
			return personne.getPersonality().generatePositiveNeutralResponse();
		} else if (!personne.isPositive()) {
			return personne.getPersonality().generateNegativeNeutralResponse();
		} else {
			return personne.getPersonality().generateNeutralNeutralResponse();
		}
	}

	private static void displayResponse(Personne personne, String response, Personne autrePersonne, String topic) {
		// Vérifier l'humeur des deux personnes
		boolean isPersonnePositive = personne.isPositive();
		boolean isAutrePersonnePositive = autrePersonne.isPositive();

		// Déterminer le résultat en fonction de l'humeur des deux personnes
		String result;
		if (isPersonnePositive && isAutrePersonnePositive) {
			result = "positif";
		} else if (!isPersonnePositive && !isAutrePersonnePositive) {
			result = "négatif";
		} else {
			// Générer soit 0 soit 1 de manière aléatoire pour l'influence
			int influence = (int) (Math.random() * 2);
			if (influence == 0) {
				result = "négatif";
			} else {
				result = "positif";
			}
		}

		// Affichage dans le style demandé
		System.out.println(personne.getName() + " discute avec " + autrePersonne.getName() + " du thème \"" + topic
				+ "\", résultat : " + result);
	}
}
