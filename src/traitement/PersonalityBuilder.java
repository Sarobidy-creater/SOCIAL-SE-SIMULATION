package traitement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import data.Personality;
import log.LoggerUtility;
import personnality.*;

public class PersonalityBuilder {
	private static Logger logger = LoggerUtility.getLogger(PersonalityBuilder.class, "html");
	public PersonalityBuilder() {
		
	}
    // Méthode pour créer une liste de personnalités
    public static List<Personality> buildPersonalities() {
        List<Personality> personalities = new ArrayList<>();
        
        // Amuseur: Spontané, enthousiaste, amical
        personalities.add(new Amuseur(5, 5, 7, 3, 5, 6, 6, 7));

        // Architecte: Logique, créatif, stratégique
        personalities.add(new Architecte(3, 7, 5, 8, 8, 3, 8, 3));

        // Aventurier: Aventureux, pragmatique, indépendant
        personalities.add(new Aventurier(6, 3, 8, 3, 7, 2, 7, 8));

        // Avocat: Idéaliste, créatif, empathique
        personalities.add(new Avocat(2, 8, 4, 7, 5, 7, 5, 6));

        // Commandant: Ambitieux, stratégique, organisé
        personalities.add(new Commandant(2, 8, 4, 8, 9, 2, 9, 1));

        // Consul: Aimable, chaleureux, attentionné
        personalities.add(new Consul(4, 5, 7, 3, 8, 4, 6, 5));

        // Défenseur: Fiable, organisé, pratique
        personalities.add(new Defenseur(8, 2, 9, 1, 8, 2, 8, 2));

        // Directeur: Ambitieux, stratégique, organisé
        personalities.add(new Directeur(2, 8, 4, 8, 9, 2, 9, 1));

        // Entrepreneur: Décisif, audacieux, pragmatique
        personalities.add(new Entrepreneur(7, 3, 9, 2, 6, 3, 8, 7));

        // Innovateur: Curieux, analytique, original
        personalities.add(new Innovateur(3, 7, 4, 8, 8, 3, 8, 3));

        // Inspirateur: Inspirant, idéaliste, créatif
        personalities.add(new Inspirateur(2, 9, 3, 8, 4, 8, 4, 7));

        // Logicien: Curieux, analytique, original
        personalities.add(new Logicien(3, 7, 4, 8, 8, 3, 8, 3));

        // Logistician: Fiable, organisé, pratique
        personalities.add(new Logistician(8, 2, 9, 1, 8, 2, 8, 2));

        // Médiateur: Idéaliste, créatif, empathique
        personalities.add(new Mediateur(2, 8, 4, 7, 5, 7, 5, 6));

        // Protagoniste: Charismatique, empathique, idéaliste
        personalities.add(new Protagoniste(3, 7, 5, 7, 6, 7, 7, 4));

        // Virtuose: Artistique, sensible, adaptable
        personalities.add(new Virtuose(4, 6, 6, 4, 5, 6, 6, 5));

        return personalities;
    }
    public static Personality getRandomPersonality(List<Personality> personalities) {
    	logger.info("Personnality creation with value : " + personalities);
        Random random = new Random();
        int index = random.nextInt(personalities.size());
        return personalities.get(index);
    }
}