package test.unit;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import data.Personne;
import traitement.PersonneBuilder;

public class TestPersonneBuild {
	private PersonneBuilder builder;

    @Before
    public void setUp() throws Exception {
        // Assurez-vous que le fichier et les métiers existent et sont corrects.
        builder = new PersonneBuilder("prenom.txt", "metiers.txt");
    }

    @Test
    public void testBuildPersonne() throws IOException {
        Personne personne = builder.buildPersonne();
        assertNotNull("L'objet Personne ne doit pas être null", personne);
        assertNotNull("Le nom ne doit pas être null", personne.getName());
        assertTrue("L'âge doit être entre 5 et 100", personne.getAge() >= 5 && personne.getAge() <= 100);
        assertNotNull("La profession ne doit pas être null", personne.getProfessions());
        assertNotNull("La personnalité ne doit pas être null", personne.getPersonality());
    }

    @Test
    public void testGenerateRandomAge() {
        int age = builder.generateRandomAge();
        assertTrue("L'âge généré doit être entre 5 et 100", age >= 5 && age <= 100);
    }

    @Test
    public void testGetRandomUniqueNameFromFile() throws IOException {
        String name = builder.getRandomUniqueNameFromFile("prenom.txt");
        assertNotNull("Le nom ne doit pas être null", name);
    }

    @Test
    public void testGenerateJob() {
        String job = builder.generateJob(25);
        assertNotNull("La profession ne doit pas être null", job);
        assertFalse("La profession ne doit pas être 'Etudiant' pour un âge de 25 ans", job.equals("Etudiant"));
        assertFalse("La profession ne doit pas être 'Retraite' pour un âge de 25 ans", job.equals("Retraite"));
    }
}

