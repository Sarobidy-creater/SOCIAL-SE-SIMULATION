package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;

import data.*;
import traitement.*;
import parametreSimulation.*;

public class MapGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final Dimension IDEAL_MAP_DIMENSIONS = new Dimension(100, 100);
	private static final Dimension IDEAL_MAIN_DIMENSIONS = new Dimension(1200, 700);

	private Communaute commu;

	private JPanel buttonPanel = new JPanel();
//    private JPanel mapPanel = new JPanel();
	private JPanel timePanel = new JPanel();
	private JButton profileButton = new JButton("Personnage");
	private JButton liveButton = new JButton("En direct");
	private JButton statButton = new JButton("Statistique");
	private JButton relationButton = new JButton("Relation");
	private JButton psychoButton = new JButton("Psychologie");
	private JButton interButton = new JButton("Interaction");
	private JButton V2Button = new JButton("2X");
	private JButton pauseButton = new JButton("Pause");
	private JButton resumeButton = new JButton("Reprendre");
	private RelationPanel relationPanel;
	private JMenu psychoMenu;
//	private JScrollPane scroll;
//	private JTextArea textArea;
//	private JFrame frame;
//	private RelationTree gt = new RelationTree(InputParameter.INPUT_PATH);

	public MapGUI(String title, Communaute commu) {
		super(title);
		this.commu = commu;
		init();
	}

	private void displayStatistics(Map map) {
		// Récupération des données à partir de l'instance de la classe Map
		int ageMoyen = map.moyenageinfo();
		double[] trancheAge = map.tranche_age();
		HashMap<Personality, Integer> perso = map.personnaliteDominante();

		// Création du graphique camembert pour la répartition par tranche d'âge
		DefaultPieDataset ageDataset = new DefaultPieDataset();
		ageDataset.setValue("Moins de 23 ans", trancheAge[0]);
		ageDataset.setValue("Entre 23 et 70 ans", trancheAge[1]);
		ageDataset.setValue("Plus de 70 ans", trancheAge[2]);

		JFreeChart ageChart = ChartFactory.createPieChart("Répartition par tranche d'âge", ageDataset, true, true,
				false);

		// Création du graphique histogramme pour la répartition par personnalité
		DefaultCategoryDataset personalityDataset = new DefaultCategoryDataset();

		// Créer une liste des entrées de la hashmap pour mélanger l'ordre
		List<Entry<Personality, Integer>> personalityEntries = new ArrayList<>(perso.entrySet());
		Collections.shuffle(personalityEntries);

		// Ajouter les valeurs à partir de la liste mélangée
		for (Entry<Personality, Integer> entry : personalityEntries) {
			Personality personality = entry.getKey();
			int count = entry.getValue();
			personalityDataset.addValue(count, "Personnalité", personality.toString());
		}

		JFreeChart personalityChart = ChartFactory.createBarChart("Répartition par personnalité", // Titre du graphique
				"Personnalité", // Axe X
				"Nombre", // Axe Y
				personalityDataset, // Données
				PlotOrientation.VERTICAL, // Orientation du graphique
				true, // Afficher légende
				true, // Afficher tooltips
				false // Afficher URLs
		);

		// Affichage des graphiques dans des fenêtres séparées
		ChartPanel ageChartPanel = new ChartPanel(ageChart);
		ageChartPanel.setPreferredSize(new Dimension(500, 300));
		JFrame ageChartFrame = new JFrame("Statistiques d'âge");
		ageChartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ageChartFrame.getContentPane().add(ageChartPanel);
		ageChartFrame.pack();
		ageChartFrame.setLocationRelativeTo(null);
		ageChartFrame.setVisible(true);

		ChartPanel personalityChartPanel = new ChartPanel(personalityChart);
		personalityChartPanel.setPreferredSize(new Dimension(500, 300));
		JFrame personalityChartFrame = new JFrame("Statistiques de personnalité");
		personalityChartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		personalityChartFrame.getContentPane().add(personalityChartPanel);
		personalityChartFrame.pack();
		personalityChartFrame.setLocationRelativeTo(null);
		personalityChartFrame.setVisible(true);
	}

	private void displayRelationGraph(Map map) {
		HashMap<Personne, HashMap<String, List<Personne>>> relationsMap = map.assignRelations(commu);
		DefaultXYDataset dataset = new DefaultXYDataset();

		// Ajouter les points pour chaque personne
		double[][] data = new double[2][relationsMap.size()];
		int index = 0;
		HashMap<Personne, Integer> personIndexMap = new HashMap<>();
		for (Personne personne : relationsMap.keySet()) {
			data[0][index] = Math.random(); // Position X aléatoire
			data[1][index] = Math.random(); // Position Y aléatoire
			personIndexMap.put(personne, index);
			index++;
		}

		dataset.addSeries("Personnes", data);

		// Créer le graphique
		JFreeChart scatterChart = ChartFactory.createScatterPlot("Relations entre les Personnes", // Titre du graphique
				"X", // Axe X
				"Y", // Axe Y
				dataset, // Données
				PlotOrientation.VERTICAL, true, // Inclure légende
				true, // Afficher tooltips
				false // Pas de URLs
		);

		// Ajouter les noms des personnes comme annotations
		XYPlot plot = scatterChart.getXYPlot();
		for (Personne personne : relationsMap.keySet()) {
			int personIndex = personIndexMap.get(personne);
			XYTextAnnotation annotation = new XYTextAnnotation(personne.getName(), data[0][personIndex],
					data[1][personIndex]);
			plot.addAnnotation(annotation);
		}

		// Créer un panel pour afficher le graphique
		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

		// Créer une fenêtre pour afficher le graphique
		JFrame frame = new JFrame("Relations entre les Personnes");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);

		// Personnaliser le graphique pour afficher les relations
		Color[] colors = {Color.GRAY, Color.GREEN, Color.BLUE}; // Tableau des couleurs
		Random rand = new Random(); // Générateur de nombres aléatoires

		for (Entry<Personne, HashMap<String, List<Personne>>> entry : relationsMap.entrySet()) {
		    Personne personne = entry.getKey();
		    int sourceIndex = personIndexMap.get(personne);

		    for (Entry<String, List<Personne>> relationEntry : entry.getValue().entrySet()) {
		        // Sélectionner une couleur aléatoire pour chaque lien
		        Color lineColor = colors[rand.nextInt(colors.length)];

		        List<Personne> personnesReliees = relationEntry.getValue();
		        for (Personne personneReliee : personnesReliees) {
		            int targetIndex = personIndexMap.get(personneReliee);
		            double[][] seriesData = { { data[0][sourceIndex], data[0][targetIndex] },
		                    { data[1][sourceIndex], data[1][targetIndex] } };
		            dataset.addSeries(relationEntry.getKey(), seriesData);

		            // Définir la couleur de la série
		            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		            renderer.setSeriesPaint(0, lineColor);
		            scatterChart.getXYPlot().setRenderer(renderer);
		        }
		    }
		}

		// Afficher la fenêtre
		frame.setVisible(true);
	}

	private void init() {
		psychoMenu = new JMenu("Psychologie"); // Initialisation de psychoMenu avec le texte "Psychologie"

		// Création d'un élément de menu pour afficher la recherche en psychologie
		JMenuItem psychologyMenuItem = new JMenuItem("Voir la recherche en psychologie");

		relationPanel = new RelationPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		buttonPanel.setLayout(new GridLayout(5, 1, 0, 10));
		buttonPanel.add(profileButton);
		buttonPanel.add(statButton);
		buttonPanel.add(relationButton);
		buttonPanel.add(interButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(resumeButton);
		buttonPanel.add(psychoButton);
		buttonPanel.add(liveButton);

		JPanel mapPanel = new JPanel(new BorderLayout());
		mapPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		Map map = new Map(commu);
		map.setPreferredSize(IDEAL_MAP_DIMENSIONS);
		mapPanel.add(map, BorderLayout.CENTER);

		JLabel timeLabel = new JLabel("Temps");
		timePanel.add(V2Button);
		timePanel.add(timeLabel);

//        JLabel bottomPanel = new JLabel("information");
		JPanel rightButtonPanel = new JPanel();
		rightButtonPanel.setLayout(new GridLayout(5, 1, 0, 10));

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.WEST);
		mainPanel.add(mapPanel, BorderLayout.CENTER);
//        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.add(rightButtonPanel, BorderLayout.EAST);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		contentPane.add(timePanel, BorderLayout.NORTH);
//        displaySimulationInfo();

		contentPane.setPreferredSize(IDEAL_MAIN_DIMENSIONS);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(true);
		displaySimulationInfo();

		psychoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lecture du contenu du fichier psychologie.txt
				String psychologyContent = readTextFile("src/psychologie.txt");

				// Création d'un JTextArea pour afficher le contenu
				JTextArea textArea = new JTextArea(psychologyContent);
				textArea.setEditable(false);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);

				// Création d'une JScrollPane pour permettre le défilement du contenu
				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setPreferredSize(new Dimension(400, 300)); // Définir les dimensions souhaitées

				// Affichage du contenu dans une boîte de dialogue
				JOptionPane.showMessageDialog(MapGUI.this, scrollPane, "Recherche en Psychologie",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		relationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRelationGraph(map);
			}
		});
		interButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayRelationInfo();
			}
		});

		V2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				map.accelerateMouvement();
			}
		});

		profileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayCharacterList();

			}
		});
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				map.pauseSimulation();
			}
		});
		statButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayStatistics(map); // Passer l'instance de Map à la méthode
			}
		});

		resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				map.resumeSimulation();
			}
		});
	}

	private void displayCharacterList() {
		JDialog characterListDialog = new JDialog(this, "Liste des personnages");
		characterListDialog.setSize(400, 600); // Définissez la taille selon vos besoins
		characterListDialog.setLayout(new BorderLayout());

		JTextArea characterTextArea = new JTextArea();
		characterTextArea.setEditable(false); // Rendre le JTextArea non éditable

		// Construire la chaîne de caractères pour la liste des personnages
		StringBuilder characterList = new StringBuilder();
		for (Personne personne : commu.getHabitants()) {
			characterList.append("Nom: ").append(personne.getName()).append("\n");
			characterList.append("Age: ").append(personne.getAge()).append("\n");
			characterList.append("Profession: ").append(personne.getProfessions().getName()).append("\n");
			characterList.append("Position: X=").append(personne.getPositions().getX()).append(", Y=")
					.append(personne.getPositions().getY()).append("\n");
			characterList.append("Personality: ").append(personne.getPersonality()).append("\n");
			characterList.append("Humeur: ").append(personne.getSanteMentale()).append("\n\n");
		}

		characterTextArea.setText(characterList.toString());

		// Ajouter le JTextArea à un JScrollPane pour le rendre défilable
		JScrollPane scrollPane = new JScrollPane(characterTextArea);
		characterListDialog.add(scrollPane, BorderLayout.CENTER);

		// Afficher la boîte de dialogue
		characterListDialog.setLocationRelativeTo(this); // Pour centrer la boîte de dialogue par rapport à la fenêtre
															// principale
		characterListDialog.setVisible(true);
	}

	private void displayPsychologyInfo() {
		String psychologyInfo = "La psychologie sociale explore l'impact de l'environnement sur le comportement individuel, notamment à travers la perception et l'influence sociales.\n En parallèle, la sociologie étudie les structures qui façonnent les interactions humaines.\n\n"
				+ "Dans notre simulation, nous adoptons la classification de Myers-Briggs, qui identifie 16 types de personnalités, réparties en 4 groupes principaux :\n\n"
				+ "1. Analystes (INTJ, INTP, ENTJ, ENTP) - Innovateurs, curieux, stratégiques, ils excellent dans la résolution de problèmes complexes.\n\n"
				+ "2. Diplomates (INFJ, INFP, ENFJ, ENFP) - Empathiques, inspirants, ils cherchent l'harmonie et les connections profondes entre les personnes.\n\n"
				+ "3. Sentinelles (ISTJ, ISFJ, ESTJ, ESFJ) - Fiables, dévoués, ils valorisent la stabilité, la sécurité et les traditions.\n\n"
				+ "4. Explorateurs (ISTP, ISFP, ESTP, ESFP) - Audacieux, artistiques, ils vivent pour l'expérience du moment présent et sont très adaptables.\n\n"
				+ "Ces traits de personnalité influencent la manière dont les individus interagissent au sein de notre communauté virtuelle, affectant leurs relations, carrières et décisions.";

		JOptionPane.showMessageDialog(this, psychologyInfo, "Introduction aux 16 personnalités",
				JOptionPane.INFORMATION_MESSAGE);

	}

	private void displayRelationInfo() {
		JDialog relationDialog = new JDialog(this, "Informations de Relation", true);
		relationDialog.setSize(600, 400);
		relationDialog.setLayout(new BorderLayout());

		JTextArea relationTextArea = new JTextArea();
		relationTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(relationTextArea);
		relationDialog.add(scrollPane, BorderLayout.CENTER);

		redirectSystemOutToTextArea(relationTextArea);

		relationDialog.setLocationRelativeTo(this);
		relationDialog.setVisible(true);
	}

	private void redirectSystemOutToTextArea(final JTextArea textArea) {
		PrintStream printStream = new PrintStream(new OutputStream() {
			@Override
			public void write(int b) {
				textArea.append(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) {
				textArea.append(new String(b, off, len));
			}
		});

		System.setOut(printStream);
	}

	private void displaySimulationInfo() {
		String simulationInfo = "Bonjours mesdames et monsieurs\n\n"
				+ "Bienvenue dans notre simulation représentant un réseau social se situant dans une ville fictive.\n\n"
				+ "Vous trouverez ci-dessous un manuel utilisateur qui vous permettra de mieux utiliser notre logiciel:\n\n"
				+ "Un menu avec 5 boutons pour pouvoir prendre connaissances:\n\n\t"
				+ "- Des notions de la psychologie pour mieux vous approprier notre logiciel\n\n"
				+ "- Des personnages habitants la ville avec leurs nom, leurs âge, leurs humeurs de bases et leurs métiers\n\n"
				+ "- Des relations familiales, amicales ou professionnels des personnages au début de la simulation\n\n"
				+ "- Des statistiques une fois la simulation terminer pour observer l'évolution des personnages\n\n"
				+ "On espère que vous passerez un agréable moment en visualisant notre simulation\n\n";
		JOptionPane.showMessageDialog(this, simulationInfo, "Salutations", JOptionPane.INFORMATION_MESSAGE);
	}

	private String readTextFile(String filename) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

}
