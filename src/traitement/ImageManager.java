package traitement;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {
	private final static String HABITANT = "src/images/poke.png";
	public static Image habitant_img = readImage(HABITANT);
	
	private final static String HABITANT2 = "src/images/poke3.png";
	public static Image habitant2_img = readImage(HABITANT2);
	
	private final static String PERSO2 = "src/images/perso2.png";
	public static Image perso2_img = readImage(PERSO2);
	
	private final static String MAISON2 = "src/images/maison2.png";
	public static Image maison2_img = readImage(MAISON2);
	
	private final static String MAISON = "src/images/maisonG.png";
	public static Image maison_img = readImage(MAISON);
	
	private final static String APART = "src/images/apart.png";
	public static Image apart_img = readImage(APART);
	
	private final static String SUPERMARCHE = "src/images/supermarche.png";
	public static Image supermarche_img = readImage(SUPERMARCHE);
	
	private final static String CHEMIN = "src/images/chemin2.jpg";
	public static Image chemin_img = readImage(CHEMIN);
	
	private final static String CHEMINT = "src/images/chemin3.jpg";
	public static Image chemin3_img = readImage(CHEMINT);
	
	private final static String ARBRE = "src/images/arbre2.png";
	public static Image arbre_img = readImage(ARBRE);
	
	private final static String CERISIER = "src/images/cerisier.png";
	public static Image cerisier_img = readImage(CERISIER);
	
	private final static String POMMIER = "src/images/pommier.png";
	public static Image pommier_img = readImage(POMMIER);
	
	private final static String FRAISIER = "src/images/fraisier.png";
	public static Image fraisier_img = readImage(FRAISIER);
	
	private final static String ECOLE = "src/images/ecole.png";
	public static Image ecole_img = readImage(ECOLE);
	
	private final static String ENTREPRISE = "src/images/entreprise.png";
	public static Image entreprise_img = readImage(ENTREPRISE);
	
	private final static String OFFICE = "src/images/office.png";
	public static Image office_img = readImage(OFFICE);
	
	private final static String OFFICE2 = "src/images/office2.png";
	public static Image office2_img = readImage(OFFICE2);
	
	private final static String OFFICE3 = "src/images/office3.png";
	public static Image office3_img = readImage(OFFICE3);
	
	private final static String ENTREPRISE2 = "src/images/entreprise2.png";
	public static Image entreprisee_img = readImage(ENTREPRISE2);
	
	private final static String ENTREPRISE3 = "src/images/entreprise3.png";
	public static Image entreprises_img = readImage(ENTREPRISE3);
	
	private final static String FAC = "src/images/fac.png";
	public static Image fac_img = readImage(FAC);
	
	private final static String BOULANGERIE = "src/images/boulangerie.png";
	public static Image boulangerie_img = readImage(BOULANGERIE);
	
	private final static String COMMISSARIAT = "src/images/commisariat.png";
	public static Image commissariat_img = readImage(COMMISSARIAT);
	
	private final static String HOPITAL = "src/images/hopital.png";
	public static Image hopital_img = readImage(HOPITAL);
	
	private final static String BANK = "src/images/bank.png";
	public static Image bank_img = readImage(BANK);
	
	private final static String LAC = "src/images/lac.jpg";
	public static Image lac_img = readImage(LAC);
	
	private final static String CANARD = "src/images/canard.png";
	public static Image canard_img = readImage(CANARD);
	
	private final static String FLEUR = "src/images/fleur.jpg";
	public static Image fleur_img = readImage(FLEUR);
	
	private final static String HERBE = "src/images/herbe.png";
	public static Image herbe_img = readImage(HERBE);
	
	private final static String TOBOGGAN = "src/images/toboggan.png";
	public static Image toboggan_img = readImage(TOBOGGAN);
	
	private final static String BALANCOIRE = "src/images/balançoire.png";
	public static Image balançoire_img = readImage(BALANCOIRE);
	
	public static Image readImage(String filePath) {
		try {
			return ImageIO.read(new File(filePath));
		} catch(IOException e) {
			System.err.println("--Can not read the image file! --");
			return null;
		}
	}
}
