package test.unit;

import static org.junit.Assert.*;
import org.junit.Test;
import traitement.PersonalityBuilder;
import data.Personality;

import java.util.List;

public class TestPersonnalityBuilder {
    
    @Test
    public void testBuildPersonalities() {
        List<Personality> personalities = PersonalityBuilder.buildPersonalities();
        assertNotNull("La liste des personnalités ne doit pas être null", personalities);
        assertFalse("La liste des personnalités ne doit pas être vide", personalities.isEmpty());
        assertEquals("La liste des personnalités doit contenir le bon nombre de personnalités", 16, personalities.size());
    }

    @Test
    public void testGetRandomPersonality() {
        List<Personality> personalities = PersonalityBuilder.buildPersonalities();
        assertNotNull("La liste pour le test ne doit pas être null", personalities);
        assertFalse("La liste pour le test ne doit pas être vide", personalities.isEmpty());

        Personality personality = PersonalityBuilder.getRandomPersonality(personalities);
        assertNotNull("La personnalité obtenue ne doit pas être null", personality);
        assertTrue("La personnalité obtenue doit être une instance de Personality", personality instanceof Personality);
    }
}