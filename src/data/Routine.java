package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import parametreSimulation.SimulationTime;

public class Routine {
	private String nom;
	private List<String> taches;
	private List<SimulationTime> tempsAlloues;
	private boolean taskIsDone;

	public Routine(String nom, List<String> taches, List<SimulationTime> tempsAlloues) {
		this.nom = nom;
		this.taches = taches;
		this.tempsAlloues = tempsAlloues;
		this.taskIsDone = false;
	}

	public String getNom() {
		return nom;
	}

	public List<String> getTaches() {
		return taches;
	}

	public List<SimulationTime> getTempsAlloues() {
		return tempsAlloues;
	}

	public boolean isTaskDone() {
		return taskIsDone;
	}

	public void setTaskDone(boolean taskIsDone) {
		this.taskIsDone = taskIsDone;
	}

	public static List<Routine> lireRoutinesDepuisFichier(String fichier) throws IOException {
		List<Routine> routines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
			String line;
			String nom = null;
			List<String> taches = new ArrayList<>();
			List<SimulationTime> tempsAlloues = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					if (line.endsWith(":")) {
						if (nom != null) {
							routines.add(new Routine(nom, taches, tempsAlloues));
							taches = new ArrayList<>();
							tempsAlloues = new ArrayList<>();
						}
						nom = line.substring(0, line.length() - 1);
					} else {
						// Parsez les heures et les minutes à partir de la ligne
						String[] parts = line.split(" ");
						int heures = Integer.parseInt(parts[parts.length - 3]);
						int minutes = Integer.parseInt(parts[parts.length - 1]);
						// Ajoutez la tâche à la liste des tâches
						StringBuilder tacheBuilder = new StringBuilder();
						for (int i = 0; i < parts.length - 3; i++) {
							tacheBuilder.append(parts[i]);
							tacheBuilder.append(" ");
						}
						taches.add(tacheBuilder.toString().trim());
						// Créez une nouvelle instance de SimulationTime avec les heures et les minutes
						SimulationTime temps = new SimulationTime();
						temps.setHeures(heures);
						temps.setMinutes(minutes);
						tempsAlloues.add(temps);
					}
				}
			}
			if (nom != null) {
				routines.add(new Routine(nom, taches, tempsAlloues));
			}
		}
		return routines;
	}

	public static List<Routine> recupererRoutinesEtudiant() throws IOException {
		String cheminFichier = "src/basique.txt"; // Chemin vers le fichier basique.txt dans le répertoire src
		return lireRoutinesDepuisFichier(cheminFichier);
	}

	public static List<Routine> recupererRoutinesTravailleur() throws IOException {
		String cheminFichier = "src/basique.txt"; // Chemin vers le fichier basique.txt dans le répertoire src
		return lireRoutinesDepuisFichier(cheminFichier);
	}

	public static List<Routine> recupererRoutinesRetraite() throws IOException {
		String cheminFichier = "src/basique.txt"; // Chemin vers le fichier basique.txt dans le répertoire src
		return lireRoutinesDepuisFichier(cheminFichier);
	}
}
