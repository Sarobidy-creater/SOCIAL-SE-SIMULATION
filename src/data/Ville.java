package data;

import batiment.Appartement;
import batiment.Entreprise;
import batiment.Maison;
import batiment.Public;
import java.util.ArrayList;
import java.util.List;

public class Ville {

	public Ville() {
	}

	public ArrayList<Batiment> buildBatiment() {
		ArrayList<Batiment> batiments = new ArrayList<>();
		int capaciteMaison = 10;

		batiments.add(new Maison("maison1", new Position(-10, 0), capaciteMaison));
		batiments.add(new Maison("maison2", new Position(100, 0), capaciteMaison));
		batiments.add(new Maison("maison3", new Position(720, 0), capaciteMaison));
		batiments.add(new Maison("maison4", new Position(185, 100), capaciteMaison));
		batiments.add(new Maison("maison5", new Position(500, 110), capaciteMaison));
		batiments.add(new Maison("maison6", new Position(830, 120), capaciteMaison));
		batiments.add(new Maison("maison7", new Position(95, 180), capaciteMaison));
		batiments.add(new Maison("maison8", new Position(290, 190), capaciteMaison));
		batiments.add(new Maison("maison9", new Position(-10, 290), capaciteMaison));
		batiments.add(new Maison("maison10", new Position(290, 290), capaciteMaison));
		batiments.add(new Maison("maison11", new Position(500, 280), capaciteMaison));
		batiments.add(new Maison("maison12", new Position(-10, 410), capaciteMaison));
		batiments.add(new Maison("maison13", new Position(300, 410), capaciteMaison));
		batiments.add(new Maison("maison14", new Position(100, 490), capaciteMaison));
		batiments.add(new Maison("maison-marron1", new Position(185, 0), capaciteMaison));
		batiments.add(new Maison("maison-marron2", new Position(380, 0), capaciteMaison));
		batiments.add(new Maison("maison-marron3", new Position(390, 100), capaciteMaison));
		batiments.add(new Maison("maison-marron4", new Position(620, 120), capaciteMaison));
		batiments.add(new Maison("maison-marron5", new Position(500, 200), capaciteMaison));
		batiments.add(new Maison("maison-marron6", new Position(185, 290), capaciteMaison));
		batiments.add(new Maison("maison-marron7", new Position(390, 290), capaciteMaison));
		batiments.add(new Maison("maison-marron8", new Position(910, 250), capaciteMaison));
		batiments.add(new Maison("maison-marron9", new Position(100, 410), capaciteMaison));
		batiments.add(new Maison("maison-marron10", new Position(-10, 490), capaciteMaison));
		batiments.add(new Maison("maison-marron11", new Position(100, 580), capaciteMaison));

		return batiments;
	}

	public ArrayList<Batiment> buildAppartement() {
		ArrayList<Batiment> appartements = new ArrayList<>();
		int capaciteAppartement = 10;

		appartements.add(new Appartement("appartement1", new Position(285, 0), capaciteAppartement));
		appartements.add(new Appartement("appartement2", new Position(85, 90), capaciteAppartement));
		appartements.add(new Appartement("appartement3", new Position(285, 90), capaciteAppartement));
		appartements.add(new Appartement("appartement4", new Position(-10, 180), capaciteAppartement));
		appartements.add(new Appartement("appartement5", new Position(85, 275), capaciteAppartement));
		appartements.add(new Appartement("appartement6", new Position(185, 395), capaciteAppartement));
		appartements.add(new Appartement("appartement7", new Position(185, 480), capaciteAppartement));
		appartements.add(new Appartement("appartement8", new Position(300, 480), capaciteAppartement));
		appartements.add(new Appartement("appartement9", new Position(-10, 580), capaciteAppartement));

		return appartements;
	}

	public ArrayList<Batiment> buildPublicSpace() {
		ArrayList<Batiment> espacesPublics = new ArrayList<>();
		espacesPublics.add(buildEcole());
		espacesPublics.add(buildFac());
		espacesPublics.add(buildSupermarche());
		espacesPublics.add(buildBoulangerie());
		espacesPublics.add(buildHopital());
		espacesPublics.add(buildComissariat());
		espacesPublics.add(buildBanque());

		return espacesPublics;
	}

	public Batiment buildEcole() {
		return new Public("ecole", new Position(400, 370), 100);
	}

	public Batiment buildFac() {
		return new Public("fac", new Position(200, 530), 100);
	}

	public Batiment buildSupermarche() {
		return new Public("supermarche", new Position(400, 520), 100);
	}

	public Batiment buildBoulangerie() {
		return new Public("boulangerie", new Position(630, 190), 100);
	}

	public Batiment buildHopital() {
		return new Public("hopital", new Position(710, 130), 100);
	}

	public Batiment buildComissariat() {
		return new Public("comissariat", new Position(450, 0), 100);
	}

	public Batiment buildBanque() {
		return new Public("banque", new Position(900, 130), 100);
	}

	public ArrayList<Batiment> buildEntreprise() {
		ArrayList<Batiment> entreprises = new ArrayList<>();
		int capaciteEntreprise = 23;

		entreprises.add(new Entreprise("entreprise1", new Position(600, 0), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise2", new Position(805, 0), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise3", new Position(915, 0), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise4", new Position(-10, 80), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise5", new Position(185, 180), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise6", new Position(390, 180), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise7", new Position(605, 250), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise8", new Position(710, 245), capaciteEntreprise));
		entreprises.add(new Entreprise("entreprise9", new Position(800, 245), capaciteEntreprise));

		return entreprises;
	}
}
