package test.unit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import traitement.ProfessionBuilder;
import java.io.IOException;
import java.util.List;

public class TestProfessionBuilder {
    private ProfessionBuilder builder;
    private String testFilename = "metiers.txt"; // Assurez-vous que ce fichier est correctement placé dans les ressources

    @Before
    public void setUp() {
        builder = new ProfessionBuilder(testFilename);
    }

    @Test
    public void testReadOccupationsFromFile() throws IOException {
        List<String> occupations = builder.readOccupationsFromFile(testFilename);
        assertNotNull("La liste des professions ne doit pas être null", occupations);
        assertFalse("La liste des professions ne doit pas être vide", occupations.isEmpty());
    }

    @Test
    public void testGetRandomOccupation() throws IOException {
        String occupation = builder.getRandomOccupation();
        assertNotNull("La profession obtenue ne doit pas être null", occupation);
        assertFalse("La profession obtenue ne doit pas être vide", occupation.isEmpty());
    }
}
