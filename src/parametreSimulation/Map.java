package parametreSimulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import data.*;
import gui.GraphiquesStats;
import gui.RelationUpdateListener;
import traitement.*;
import parametreSimulation.*;

public class Map extends JPanel {
	 private JPanel mapPanel;
	private TimeSeries emotionData;
	private TimeSeries interactionData;
	private JFreeChart chart;
	private int countdownTimer = 20; // Compte à rebours initial de 10 secondes
	private boolean isReturningHome = false; // Indicateur pour savoir si les habitants retournent chez eux
	private boolean isAtHome = false; // Indicateur pour savoir si les habitants sont chez eux
	private boolean isAtWork = false; // Indicateur pour savoir si les habitants sont au travail
	private boolean isLeaving = false; // Indicateur pour savoir si les habitants quittent leur domicile ou leur lieu
										// de travail
	private boolean isDiscussion = false; // Indicateur pour savoir si une discussion est en cours
	private Personne currentPerson = null; // Référence à la personne impliquée dans la discussion
	private int discussionTimer = 0; // Timer pour la durée de la discussion
	private int sameLocationTimer = 0; // Timer pour suivre le temps passé aux mêmes coordonnées
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_DELAY = 1000;
	private Communaute commu;
	private int DELAY = DEFAULT_DELAY;
	private boolean isPaused = false; // Nouvelle variable d'état pour la pause
	private List<RelationUpdateListener> listeners = new ArrayList<>();

	public void addRelationUpdateListener(RelationUpdateListener listener) {
		listeners.add(listener);
	}

	private void notifyRelationUpdated(String relationInfo) {
		for (RelationUpdateListener listener : listeners) {
			listener.onRelationUpdated(relationInfo);
		}
	}

	// Méthode où vous mettez à jour les relations...
	public void updateRelation(String relationInfo) {
		// Mise à jour de la logique de relation...
		notifyRelationUpdated(relationInfo); // Notifiez les écouteurs
	}

	public Map(Communaute commu) {
		super();
		this.commu = commu;
		deplacement();
		
		 // Initialisation des séries temporelles
	    emotionData = new TimeSeries("Emotion");
	    interactionData = new TimeSeries("Interaction");

	    // Ajout des séries temporelles à un dataset
	    TimeSeriesCollection dataset = new TimeSeriesCollection();
	    dataset.addSeries(emotionData);
	    dataset.addSeries(interactionData);

	    // Création du graphique
	    chart = ChartFactory.createTimeSeriesChart("Evolution de l'Emotion au cours des Interactions", "Temps", "Valeur",
	            dataset, true, true, false);

	    // Personnalisation du graphique
	    chart.setBackgroundPaint(Color.WHITE);

	    // Création du panneau de visualisation du graphique
	    ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new Dimension(300,200));

	    // Définir les coordonnées du graphique sur la carte
	    chartPanel.setBounds(710, 420, 300, 100);

	    add(chartPanel, BorderLayout.NORTH);
	}
	 public HashMap<Personne, HashMap<String, List<Personne>>> assignRelations(Communaute commu) {
	        Random random = new Random();
	        HashMap<Personne, HashMap<String, List<Personne>>> relationsMap = new HashMap<>();

	        for (Personne habitant : commu.getHabitants()) {
	            // Créer les listes pour les relations
	            List<Personne> amis = new ArrayList<>();
	            List<Personne> famille = new ArrayList<>();
	            List<Personne> collegues = new ArrayList<>();

	            // Sélectionner 20 personnes aléatoires pour chaque relation
	            for (int i = 0; i < 20; i++) {
	                // Choisir une personne aléatoire parmi tous les habitants
	                int randomIndex = random.nextInt(commu.getHabitants().size());
	                Personne personneAleatoire = commu.getHabitants().get(randomIndex);

	                // Assurer que la personne aléatoire n'est pas la personne actuelle
	                if (!personneAleatoire.equals(habitant)) {
	                    // Ajouter la personne à la liste correspondante
	                    if (i < 10) {
	                        amis.add(personneAleatoire);
	                    } else if (i < 20) {
	                        famille.add(personneAleatoire);
	                    } else {
	                        collegues.add(personneAleatoire);
	                    }
	                }
	            }

	            // Créer la HashMap pour stocker les relations
	            HashMap<String, List<Personne>> relations = new HashMap<>();
	            relations.put("amis", amis);
	            relations.put("famille", famille);
	            relations.put("collegues", collegues);

	            // Stocker les relations dans la HashMap principale
	            relationsMap.put(habitant, relations);
	        }

	        return relationsMap;
	    }

	public void accelerateMouvement() {
		if (!isPaused) { // Accélérer seulement si ce n'est pas en pause
			DELAY = 100;
		}
	}

	public void pauseSimulation() {
		isPaused = true;
	}

	public void resumeSimulation() {
		isPaused = false;
		DELAY = DEFAULT_DELAY; // Réinitialiser la vitesse à x1
	}
	public void updateEmotion(int value) {
        emotionData.addOrUpdate(new Second(), value);
    }

    public void updateInteraction(int value) {
        interactionData.addOrUpdate(new Second(), value);
    }

	private void deplacement() {
		Thread thread = new Thread(() -> {
			while (true) {
				if (!isPaused) { // Vérifier l'état de pause
					movePeople();
					repaint();
				}
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	private boolean estHeureDeTravail = true; // Commencez par supposer qu'il est l'heure de travailler
	private int compteurHeure = 0; // Compteur pour simuler le passage du temps
	private final int DUREE_CYCLE = 10; // Durée du cycle pour changer entre travail et maison

	private void movePeople() {
		for (Personne habitant : commu.getHabitants()) {
			Position destination;

			// Vérifier le statut de la personne et définir la destination en conséquence
			if (isAtHome) {
				destination = habitant.getLieuTravail().getPositions(); // Aller au travail après avoir passé du temps à
																		// la maison
				isAtHome = false;
				isAtWork = true;
				countdownTimer = 15; // Réinitialiser le compte à rebours
			} else if (isAtWork) {
				destination = habitant.getMaison().getPositions(); // Rentrer chez eux après avoir passé du temps au
																	// travail
				isAtWork = false;
				isReturningHome = true;
				countdownTimer = 10; // Réinitialiser le compte à rebours
			} else if (isLeaving) {
				// Quand les habitants quittent leur domicile ou leur lieu de travail, ils
				// partent dans toutes les directions
				destination = new Position((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()));
				isLeaving = false;
				countdownTimer = 5; // Réinitialiser le compte à rebours
			} else {
				destination = isReturningHome ? habitant.getMaison().getPositions()
						: habitant.getLieuTravail().getPositions();
			}

			// Calculer le mouvement vers la destination
			int deltaX = destination.getX() - habitant.getPositions().getX();
			int deltaY = destination.getY() - habitant.getPositions().getY();
			double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

			if (length != 0) {
				deltaX = (int) (10 * (deltaX / length));
				deltaY = (int) (10 * (deltaY / length));
			}

			int newX = habitant.getPositions().getX() + deltaX;
			int newY = habitant.getPositions().getY() + deltaY;

			// Gérer les collisions et les discussions
			if (!isDiscussion) {
				for (Personne autreHabitant : commu.getHabitants()) {
					if (autreHabitant != habitant && newX == autreHabitant.getPositions().getX()
							&& newY == autreHabitant.getPositions().getY()) {
						// Collision détectée
						isDiscussion = true;
						currentPerson = habitant;
						Interaction.simulateDiscussion(habitant, autreHabitant); // Simulation de la discussion
						updateEmotion(habitant.getSanteMentale()); // Mettre à jour les données d'émotion
		                    break;
					}
				}
			} else {
				discussionTimer++;
				if (discussionTimer >= 2) {
					isDiscussion = false;
					discussionTimer = 0;
					continue; // Reprendre le déplacement initial après la discussion
				}
			}

			// Vérifier si les habitants restent aux mêmes coordonnées pendant plus de 3
			// secondes
			if (newX == habitant.getPositions().getX() && newY == habitant.getPositions().getY()) {
				sameLocationTimer++;
				if (sameLocationTimer >= 3) {
					newX += (int) (Math.random() * 3) - 1;
					newY += (int) (Math.random() * 3) - 1;
					sameLocationTimer = 0; // Réinitialiser le compteur
				}
			} else {
				sameLocationTimer = 0; // Réinitialiser le compteur si les coordonnées changent
			}

			// Mettre à jour la position
			newX = Math.max(0, Math.min(getWidth() - 10, newX));
			newY = Math.max(0, Math.min(getHeight() - 10, newY));
			habitant.getPositions().setX(newX);
			habitant.getPositions().setY(newY);
		}

		countdownTimer--;

		// Vérifier le compte à rebours
		if (countdownTimer <= 0) {
			// Changer les états pour le prochain cycle
			isReturningHome = !isReturningHome;
			isAtHome = !isAtHome;
			isLeaving = !isLeaving;
			countdownTimer = 10; // Réinitialiser le compte à rebours
		}

		SwingUtilities.invokeLater(() -> repaint());
	}
	public HashMap<Personality, Integer> personnaliteDominante() {
	    // Initialise la Map pour stocker les personnalités et leurs occurrences
	    HashMap<Personality, Integer> personalityMap = new HashMap<>();

	    // Parcourir les habitants de la communauté
	    for (Personne habitant : commu.getHabitants()) {
	        Personality personalite = habitant.getPersonality();
	        // Vérifier si la personnalité est déjà dans la Map
	        if (personalityMap.containsKey(personalite)) {
	            // Si oui, incrémenter le compteur d'occurrences
	            personalityMap.put(personalite, personalityMap.get(personalite) + 1);
	        } else {
	            // Sinon, ajouter la personnalité à la Map avec une occurrence de 1
	            personalityMap.put(personalite, 1);
	        }
	    }
	    // Retourne la Map contenant les personnalités et leurs occurrences
	    return personalityMap;
	}
	


	public int moyenageinfo() {
		int age_moyen = 0;
		int age_total = 0;
		for (int i = 0; i < commu.getHabitants().size(); i++) {
			Personne habitant = commu.getHabitants().get(i);
			age_total += habitant.getAge();
		}
		return age_moyen = (age_total / 200);
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

		double[] pourcentages = { pourcentage_etudiants, pourcentage_travailleurs, pourcentage_retraites };
		return pourcentages;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

//        for (int x = 30; x + 30 < 1200; x += 146) {
//            for (int y = 0; y + 100 < 800; y += 146) {
		setBackground(Color.gray);
		// Route du haut
		g.setColor(Color.GRAY);
		/*
		 * g.fillRect(550, 0, 5, 350); g.fillRect(600, 0, 5, 350); g.fillRect(575, 0, 5,
		 * 20); g.fillRect(575, 50, 5, 20); g.fillRect(575, 100, 5, 20); g.fillRect(575,
		 * 150, 5, 20); g.fillRect(575, 200, 5, 20); g.fillRect(575, 250, 5, 20);
		 * g.fillRect(575, 300, 5, 20); g.fillRect(575, 350, 5, 20);
		 */
		g.drawImage(ImageManager.pommier_img, 550, 0, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 20, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 40, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 60, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 80, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 100, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 120, 80, 80, null);
		g.drawImage(ImageManager.chemin3_img, 570, 190, 55, 20, null);
//      			g.fillRect(570,190,50,5);
//      			g.fillRect(570,210,50,5);
		g.drawImage(ImageManager.pommier_img, 550, 200, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 220, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 240, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 260, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 280, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 300, 80, 80, null);
		g.drawImage(ImageManager.pommier_img, 550, 320, 80, 80, null);

		// Route à gauche
		/*
		 * g.fillRect(0, 350, 555, 5); g.fillRect(0, 400, 555, 5); g.fillRect(0, 375,
		 * 20, 5); g.fillRect(50, 375, 20, 5); g.fillRect(100, 375, 20, 5);
		 * g.fillRect(150, 375, 20, 5); g.fillRect(200, 375, 20, 5); g.fillRect(250,
		 * 375, 20, 5); g.fillRect(300, 375, 20, 5); g.fillRect(350, 375, 20, 5);
		 * g.fillRect(400, 375, 20, 5); g.fillRect(450, 375, 20, 5); g.fillRect(500,
		 * 375, 20, 5); g.fillRect(550, 375, 20, 5);
		 */
		// Ligne à gauche
		g.drawImage(ImageManager.arbre_img, -50, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, -50, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, -30, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, -30, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, -10, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, -10, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 50, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 50, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 70, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 70, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 90, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 90, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 150, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 150, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 170, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 170, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 190, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 190, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 250, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 250, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 280, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 280, 330, 130, 130, null);
		g.drawImage(ImageManager.chemin3_img, 360, 360, 20, 55, null);
		g.drawImage(ImageManager.arbre_img, 330, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 330, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 350, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 350, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 370, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 370, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 390, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 390, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 410, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 410, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 430, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 430, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 450, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 450, 330, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 470, 310, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 470, 330, 130, 130, null);

		// Route à droite
		/*
		 * g.fillRect(600, 350, 400, 5); g.fillRect(600, 400, 400, 5); g.fillRect(600,
		 * 375, 20, 5); g.fillRect(650, 375, 20, 5); g.fillRect(700, 375, 20, 5);
		 * g.fillRect(750, 375, 20, 5); g.fillRect(800, 375, 20, 5); g.fillRect(850,
		 * 375, 20, 5); g.fillRect(900, 375, 20, 5); g.fillRect(950, 375, 20, 5);
		 */
		// Ligne à droite
		g.drawImage(ImageManager.cerisier_img, 600, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 600, 365, 50, 50, null);
		g.drawImage(ImageManager.chemin3_img, 645, 360, 40, 55, null);
		g.drawImage(ImageManager.cerisier_img, 680, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 680, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 700, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 700, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 720, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 720, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 740, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 740, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 760, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 760, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 780, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 780, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 800, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 800, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 820, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 820, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 840, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 840, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 860, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 860, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 880, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 880, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 900, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 900, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 920, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 920, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 940, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 940, 365, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 960, 345, 50, 50, null);
		g.drawImage(ImageManager.cerisier_img, 960, 365, 50, 50, null);

		// Route du bas
		/*
		 * g.fillRect(550, 400, 5, 350); g.fillRect(600, 400, 5, 350); g.fillRect(575,
		 * 400, 5, 20); g.fillRect(575, 450, 5, 20); g.fillRect(575, 500, 5, 20);
		 * g.fillRect(575, 550, 5, 20); g.fillRect(575, 600, 5, 20); g.fillRect(575,
		 * 650, 5, 20);
		 */
		g.drawImage(ImageManager.fraisier_img, 560, 400, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 410, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 420, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 430, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 440, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 450, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 460, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 470, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 480, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 490, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 500, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 510, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 520, 50, 50, null);
		g.drawImage(ImageManager.chemin3_img, 560, 560, 50, 35, null);
		g.drawImage(ImageManager.fraisier_img, 560, 580, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 590, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 600, 50, 50, null);
		g.drawImage(ImageManager.fraisier_img, 560, 610, 50, 50, null);

		// 1èreligne côté gauche
		g.drawImage(ImageManager.maison_img, -10, 0, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 77, 0, 20, 650, null);
		g.drawImage(ImageManager.maison_img, 100, 0, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 175, 0, 20, 650, null);
		g.drawImage(ImageManager.maison2_img, 185, 0, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 275, 0, 20, 570, null);
		g.drawImage(ImageManager.apart_img, 285, 0, 90, 90, null);
		g.drawImage(ImageManager.chemin_img, 370, 0, 20, 355, null);
		g.drawImage(ImageManager.maison2_img, 380, 0, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 0, 80, 460, 18, null);
		g.drawImage(ImageManager.commissariat_img, 450, 0, 130, 130, null);
		g.drawImage(ImageManager.chemin_img, 490, 110, 20, 245, null);
		// 2èmeligne côté gauche
		g.drawImage(ImageManager.office3_img, -5, 90, 80, 80, null);
		g.drawImage(ImageManager.chemin_img, 0, 180, 560, 18, null);
		g.drawImage(ImageManager.apart_img, 85, 90, 90, 90, null);
		g.drawImage(ImageManager.maison_img, 185, 100, 70, 70, null);
		g.drawImage(ImageManager.apart_img, 285, 90, 90, 90, null);
		g.drawImage(ImageManager.maison2_img, 390, 100, 70, 70, null);
		g.drawImage(ImageManager.maison_img, 500, 110, 70, 70, null);
		// 3èmeligne côté gauche
		g.drawImage(ImageManager.apart_img, -10, 180, 90, 90, null);
		g.drawImage(ImageManager.chemin_img, 0, 280, 520, 18, null);
		g.drawImage(ImageManager.maison_img, 95, 180, 70, 70, null);
		g.drawImage(ImageManager.office_img, 185, 195, 90, 90, null);
		g.drawImage(ImageManager.maison_img, 290, 190, 70, 70, null);
		g.drawImage(ImageManager.office_img, 390, 190, 90, 90, null);
		g.drawImage(ImageManager.maison2_img, 500, 200, 70, 70, null);
		// 4èmeligne côté gauche
		g.drawImage(ImageManager.maison_img, -10, 290, 70, 70, null);
		g.drawImage(ImageManager.apart_img, 85, 275, 90, 90, null);
		g.drawImage(ImageManager.maison2_img, 185, 290, 70, 70, null);
		g.drawImage(ImageManager.maison_img, 290, 290, 70, 70, null);
		g.drawImage(ImageManager.maison2_img, 390, 290, 70, 70, null);
		g.drawImage(ImageManager.maison_img, 500, 280, 70, 70, null);
		// 5èmeligne côté gauche
		g.drawImage(ImageManager.maison_img, -10, 410, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 0, 480, 400, 18, null);
		g.drawImage(ImageManager.maison2_img, 100, 410, 70, 70, null);
		g.drawImage(ImageManager.apart_img, 185, 395, 90, 90, null);
		g.drawImage(ImageManager.maison_img, 300, 410, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 370, 420, 20, 60, null);
		g.drawImage(ImageManager.ecole_img, 400, 370, 160, 160, null);
		g.drawImage(ImageManager.chemin_img, 480, 480, 18, 70, null);
		// 6èmeligne côté gauche
		g.drawImage(ImageManager.maison2_img, -10, 490, 70, 70, null);
		g.drawImage(ImageManager.maison_img, 100, 490, 70, 70, null);
		g.drawImage(ImageManager.apart_img, 185, 480, 90, 90, null);
		g.drawImage(ImageManager.apart_img, 300, 480, 90, 90, null);
		// 7èmeligne côté gauche
		g.drawImage(ImageManager.apart_img, -10, 580, 80, 80, null);
		g.drawImage(ImageManager.chemin_img, 0, 565, 400, 18, null);
		g.drawImage(ImageManager.maison2_img, 100, 580, 70, 70, null);
		g.drawImage(ImageManager.fac_img, 200, 530, 160, 160, null);
		g.drawImage(ImageManager.supermarche_img, 400, 520, 140, 140, null);

		// 1èreligne côté droit
		g.drawImage(ImageManager.office2_img, 600, 0, 90, 90, null);
		g.drawImage(ImageManager.chemin_img, 705, 0, 20, 350, null);
		g.drawImage(ImageManager.maison_img, 720, 0, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 800, 0, 20, 170, null);
		g.drawImage(ImageManager.office3_img, 815, 10, 80, 80, null);
		g.drawImage(ImageManager.chemin_img, 895, 0, 20, 350, null);
		g.drawImage(ImageManager.office2_img, 915, 0, 100, 100, null);
		g.drawImage(ImageManager.maison_img, 830, 120, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 850, 185, 20, 60, null);
		g.drawImage(ImageManager.maison2_img, 620, 120, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 610, 100, 390, 18, null);
		// 2èmeligne côté droit
		g.drawImage(ImageManager.boulangerie_img, 630, 190, 70, 70, null);
		g.drawImage(ImageManager.chemin_img, 690, 240, 240, 20, null);
		g.drawImage(ImageManager.hopital_img, 710, 130, 120, 120, null);
		g.drawImage(ImageManager.bank_img, 900, 130, 120, 120, null);
		// 3èmeligne côté droit
		g.drawImage(ImageManager.office_img, 605, 250, 100, 100, null);
		g.drawImage(ImageManager.office2_img, 715, 255, 90, 90, null);
		g.drawImage(ImageManager.office3_img, 800, 255, 90, 90, null);
		g.drawImage(ImageManager.maison2_img, 910, 250, 70, 70, null);
		// côté droit parc
		g.drawImage(ImageManager.lac_img, 710, 420, 300, 100, null);
		g.drawImage(ImageManager.canard_img, 710, 420, 30, 30, null);
		g.drawImage(ImageManager.canard_img, 720, 450, 30, 30, null);
		g.drawImage(ImageManager.canard_img, 730, 480, 30, 30, null);
		g.drawImage(ImageManager.canard_img, 840, 480, 30, 30, null);
		g.drawImage(ImageManager.canard_img, 850, 460, 30, 30, null);
		g.drawImage(ImageManager.arbre_img, 890, 495, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 505, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 515, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 525, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 535, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 545, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 555, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 565, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 890, 575, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 495, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 505, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 515, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 525, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 535, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 545, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 555, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 565, 130, 130, null);
		g.drawImage(ImageManager.arbre_img, 920, 575, 130, 130, null);
		g.drawImage(ImageManager.toboggan_img, 640, 575, 80, 80, null);
		g.drawImage(ImageManager.balançoire_img, 740, 575, 80, 80, null);
		g.drawImage(ImageManager.fleur_img, 870, 545, 70, 105, null);
		// côté droit parc herbe verticale
		g.setColor(Color.BLACK);
		g.fillRect(705, 425, 5, 95);
		// côté droit parc herbe horizontal bas
		g.drawImage(ImageManager.herbe_img, 700, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 710, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 720, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 730, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 740, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 750, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 760, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 770, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 780, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 790, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 800, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 810, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 820, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 830, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 840, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 850, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 860, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 870, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 880, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 890, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 900, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 910, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 920, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 930, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 940, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 950, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 960, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 970, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 980, 520, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 990, 520, 20, 20, null);
		// côté droit parc herbe horizontal haut
		g.drawImage(ImageManager.herbe_img, 700, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 710, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 720, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 730, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 740, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 750, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 760, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 770, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 780, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 790, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 800, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 810, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 820, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 820, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 830, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 840, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 850, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 860, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 870, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 880, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 890, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 900, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 910, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 920, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 930, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 940, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 950, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 960, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 970, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 980, 405, 20, 20, null);
		g.drawImage(ImageManager.herbe_img, 990, 405, 20, 20, null);
		/*
		 * } }
		 */
		for (Personne habitant : commu.getHabitants()) {
			int humeur = habitant.getEtats().getMentale();
			if (humeur < 10) {
				g.setColor(Color.RED); // Très faible
			} else if (humeur < 20) {
				g.setColor(new Color(255, 165, 0)); // Orange, faible
			} else if (humeur < 40) {
				g.setColor(Color.YELLOW); // Jaune, assez bas
			} else if (humeur < 60) {
				g.setColor(new Color(173, 216, 230)); // Bleu clair, moyen
			} else if (humeur < 80) {
				g.setColor(Color.BLUE); // Bleu, élevé
			} else {
				g.setColor(Color.GREEN); // Très élevé
			}
			g.drawImage(ImageManager.perso2_img, habitant.getPositions().getX(), habitant.getPositions().getY(), 30, 30,
					null);
			g.fillOval(habitant.getPositions().getX(), habitant.getPositions().getY(), 10, 10);
		}
	}
}
