package test;

import data.*;
import traitement.*;
import personnality.*;
import parametreSimulation.*;
import java.io.IOException;
import java.util.List;

public class TestConsole {
    public static void main(String[] args) {
        try {
            Ville ville = new Ville(); // Utilisez la classe Ville pour créer les bâtiments
            List<Batiment> batiments = ville.buildBatiment(); // Créez la liste des bâtiments

            PersonneBuilder personneBuilder = new PersonneBuilder("prenom.txt", "metiers.txt");
            // Initialisation des personnes
            for (int i = 0; i < 5; i++) { // Créer 5 personnes pour l'exemple
                Personne personne = personneBuilder.buildPersonne();
                attribuerMaisonEtLieuDeTravail(personne, batiments);
                afficherPersonne(personne); // Afficher l'état initial de chaque personne
            }

            // Simuler une discussion entre la première et la deuxième personne
            // Assurez-vous que les positions sont initialement différentes pour éviter une collision immédiate
            Personne p1 = batiments.get(0).getOccupants().get(0);
            Personne p2 = batiments.get(1).getOccupants().get(0);

            Interaction.detectCollisions(p1, p2);
            Interaction.simulateDiscussion(p1, p2);

            afficherPersonne(p1); // Afficher l'état après la discussion
            afficherPersonne(p2); // Afficher l'état après la discussion

        } catch (IOException e) {
            System.err.println("Erreur lors de la simulation : " + e.getMessage());
        }
    }

    public static void afficherPersonne(Personne personne) {
        System.out.println("Nom: " + personne.getName());
        System.out.println("Age: " + personne.getAge());
        System.out.println("Profession: " + personne.getProfessions().getName());
        System.out.println("Position: X=" + personne.getPositions().getX() + ", Y=" + personne.getPositions().getY());
        System.out.println("Personality: " + personne.getPersonality());
        System.out.println("Humeur: " + personne.getSanteMentale());
        System.out.println();
    }

    private static void attribuerMaisonEtLieuDeTravail(Personne personne, List<Batiment> batiments) {
        // Cette méthode est un exemple simplifié pour attribuer un bâtiment à une personne
        for (Batiment batiment : batiments) {
            if (!batiment.getOccupants().contains(personne)) { // Vérifier si la personne n'occupe pas déjà le bâtiment
                batiment.ajouter(personne); // Ajoute la personne au premier bâtiment disponible
                personne.setPositions(batiment.getPositions()); // Met à jour la position de la personne
                break; // Arrête la boucle une fois la personne ajoutée
            }
        }
    }
}
