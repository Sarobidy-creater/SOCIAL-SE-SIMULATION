package traitement;


import java.io.IOException;
import java.util.*;
import data.*;
import parametreSimulation.Communaute;

public class StatsCalculate {
	 private Communaute commu;

	public StatsCalculate(Communaute commu) {
		this.commu = commu;
	}
	 
	
	/*public HashMap<String, Integer> statsPersonnality() {
		int total_habitants = commu.getHabitants().size();
		new PersonalityBuilder();
		List<Personality> personnalities = PersonalityBuilder.buildPersonalities();
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		for (Personality perso : personnalities ) {
			String name = perso.toString();
			stats.put(name, 0);
		}
		  for (int i = 0; i < total_habitants; i++) {
		    Personne habitant = commu.getHabitants().get(i);
			Integer oldvalue = stats.get(habitant.getPersonality().toString());
			Integer newvalue = oldvalue + 1;
			stats.put(habitant.getPersonality().toString(), newvalue);
		}
	
	return stats;
	}
	
	
	public HashMap<String, Integer> statsAge() {
		int total_habitants = commu.getHabitants().size();
		Integer min = 10;
		Integer max = 25;
		Integer sum =0;
		
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		while (max<71)  {
			 for (int i = 0; i < total_habitants; i++) {
				    Personne habitant = commu.getHabitants().get(i);
				if ((habitant.getAge()>=min) && (habitant.getAge()<=max)) {
					sum++;
				}
			}
			String tranche = min.toString() + "-" +max.toString();
			stats.put(tranche, sum);
			min+=15;
			if (max+15>70) {
				max=70;
			}
			else {
				max+=15;
			}
		}
		return stats;
		}
	
	
	/*public HashMap<String, Integer> statsJob() throws IOException {
		int total_habitants = commu.getHabitants().size();
		ProfessionBuilder jobs = new ProfessionBuilder("metiers.txt");
		List<String> joblist = jobs.readOccupationsFromFile("metiers.txt");
		joblist.add("Etudiant");
		joblist.add("Retraite");
		HashMap<String, Integer> stats = new HashMap<String, Integer>();

		for (String jobname : joblist) {
			stats.put(jobname, 0);
		}
		 for (int i = 0; i < total_habitants; i++) {
			    Personne habitant = commu.getHabitants().get(i);
			Integer oldvalue = stats.get(habitant.getProfessions().toString());
			Integer newvalue = oldvalue++;
			stats.put(habitant.getProfessions().toString(), newvalue);
		}
		return stats;
	}*/
	public int moyenageinfo() {
		int age_moyen = 0;
		int age_total = 0;
		for (int i = 0; i < commu.getHabitants().size(); i++) {
			Personne habitant = commu.getHabitants().get(i);
			age_total += habitant.getAge();
		}
		return age_moyen = (age_total/ 200);
	}

	public double[] tranche_age() {
	    int nb_etudiant = 0;
	    int nb_travailleur = 0;
	    int nb_retraite = 0;
	    int total_habitants = commu.getHabitants().size();
	    
	    for (int i = 0; i < total_habitants; i++) {
	        Personne habitant = commu.getHabitants().get(i);
	        int age_courrant = habitant.getAge();
	        
	        if (age_courrant < 23) {
	            nb_etudiant++;
	        } else if (age_courrant < 70) {
	            nb_travailleur++;
	        } else {
	            nb_retraite++;
	        }
	    }
	    
	    // Calcul des pourcentages
	    double pourcentage_etudiants = (double) nb_etudiant / total_habitants * 100;
	    double pourcentage_travailleurs = (double) nb_travailleur / total_habitants * 100;
	    double pourcentage_retraites = (double) nb_retraite / total_habitants * 100;
	    
	    double[] pourcentages = {pourcentage_etudiants, pourcentage_travailleurs, pourcentage_retraites};
	    return pourcentages;
	}
}


