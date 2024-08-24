package traitement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import log.LoggerUtility;

public class ProfessionBuilder {
	private static Logger logger = LoggerUtility.getLogger(ProfessionBuilder.class, "html");

    private String filename;

    public ProfessionBuilder(String filename) {
        this.filename = filename;
    }

    public String getRandomOccupation() throws IOException {
    	logger.info("Attribution de leur metiers");
        List<String> occupations = readOccupationsFromFile(filename);
        Random random = new Random();
        int randomIndex = random.nextInt(occupations.size());
        return occupations.get(randomIndex);
    }

    public List<String> readOccupationsFromFile(String filename) throws IOException {
    	logger.info("Récupère les metiers depuis le fichier"+ filename);
        List<String> occupations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/" + filename)))) {
            String occupation;
            while ((occupation = reader.readLine()) != null) {
                occupations.add(occupation.trim());
            }
        }
        return occupations;
    }
}
