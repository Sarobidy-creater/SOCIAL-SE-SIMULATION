package traitement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import data.*;
import log.LoggerUtility;

public class PersonneBuilder {
	private static Logger logger = LoggerUtility.getLogger(PersonneBuilder.class, "html");
	private String filename;
	private String metiers;

	public PersonneBuilder(String filename, String metiers) {
		this.filename = filename;
		this.metiers = metiers;
	}

	public Personne buildPersonne() throws IOException {
		String nom = getRandomUniqueNameFromFile(filename);
		int age = generateRandomAge();
		String profession = generateJob(age);
		Profession job = new Profession(profession);
		Personality personality = getRandomPersonality();
		return new Personne(nom, age, job, personality);
	}

	public String getRandomUniqueNameFromFile(String filename) throws IOException {
		logger.info("Récupère et attribut un des prenoms issus de: " + filename);
		List<String> names = readNamesFromFile(filename);
		Random random = new Random();
		int randomIndex = random.nextInt(names.size());
		String randomName = names.remove(randomIndex);
		return randomName;
	}

	public List<String> readNamesFromFile(String filename) throws IOException {
		List<String> names = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("/" + filename)))) {
			String name;
			while ((name = reader.readLine()) != null) {
				names.add(name.trim());
			}
		}
		return names;
	}

	public int generateRandomAge() {
		Random random = new Random();
		int minAge = 5;
		int maxAge = 100;
		return random.nextInt(maxAge - minAge + 1) + minAge;
	}

	public String generateJob(int age) {
		logger.info("Sépare les etudiants des adultes selon age ex : " + age);
		if (age < 23) {
			return "Etudiant";
		} else if (age > 70) {
			return "Retraite";
		} else {
			// Utiliser l'instance de ProfessionBuilder pour générer une profession
			ProfessionBuilder professionBuilder = new ProfessionBuilder(metiers);
			try {
				return professionBuilder.getRandomOccupation();
			} catch (IOException e) {
				System.err.println("Erreur lors de la récupération de la profession : " + e.getMessage());
				return "Profession inconnue";
			}
		}
	}

	public Personality getRandomPersonality() {
		// Obtention d'une personnalité aléatoire en utilisant PersonalityBuilder
		List<Personality> personalities = PersonalityBuilder.buildPersonalities();
		return PersonalityBuilder.getRandomPersonality(personalities);
	}
}
