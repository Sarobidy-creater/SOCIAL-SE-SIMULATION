package gui;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import parametreSimulation.Communaute;
import traitement.Launcher.FullHousingException;
import traitement.StatsCalculate;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphiquesStats extends JFrame {
	 private Communaute commu;

	    public GraphiquesStats(Communaute communaute) throws IOException {
	        this.commu = communaute;
	        setLayout(new GridLayout(2, 2));

	        StatsCalculate statsCalculator = new StatsCalculate(commu);

	        /*// Création des données pour le graphique de personnalité
	        HashMap<String, Integer> statsPersonnalite = statsCalculator.statsPersonnality();
	        DefaultCategoryDataset datasetPersonnalite = new DefaultCategoryDataset();
	        for (Map.Entry<String, Integer> entry : statsPersonnalite.entrySet()) {
	            datasetPersonnalite.addValue(entry.getValue(), "Personnalité", entry.getKey());
	        }
	        JFreeChart chartPersonnalite = ChartFactory.createBarChart("Répartition des personnalités", "Personnalité",
	                "Nombre", datasetPersonnalite);
	        add(new ChartPanel(chartPersonnalite));

	        // Création des données pour le graphique d'âge
	        HashMap<String, Integer> statsAge = statsCalculator.statsAge();
	        DefaultCategoryDataset datasetAge = new DefaultCategoryDataset();
	        for (Map.Entry<String, Integer> entry : statsAge.entrySet()) {
	            datasetAge.addValue(entry.getValue(), "Tranche d'âge", entry.getKey());
	        }
	        JFreeChart chartAge = ChartFactory.createBarChart("Répartition par tranche d'âge", "Tranche d'âge", "Nombre",
	                datasetAge);
	        add(new ChartPanel(chartAge));

	        // Création des données pour le graphique de profession
	        HashMap<String, Integer> statsProfession = statsCalculator.statsAge();
	        DefaultCategoryDataset datasetProfession = new DefaultCategoryDataset();
	        for (Map.Entry<String, Integer> entry : statsProfession.entrySet()) {
	            datasetProfession.addValue(entry.getValue(), "Profession", entry.getKey());
	        }
	        JFreeChart chartProfession = ChartFactory.createBarChart("Répartition des professions", "Profession", "Nombre",
	                datasetProfession);
	        add(new ChartPanel(chartProfession));
*/
	        // Création du graphique pour la répartition par tranche d'âge
	        double[] pourcentages = statsCalculator.tranche_age();
	        DefaultPieDataset datasetTrancheAge = new DefaultPieDataset();
	        datasetTrancheAge.setValue("Enfants", pourcentages[0]);
	        datasetTrancheAge.setValue("Jeunes", pourcentages[1]);
	        datasetTrancheAge.setValue("Adultes", pourcentages[2]);
	        datasetTrancheAge.setValue("Seniors", pourcentages[3]);
	        JFreeChart chartTrancheAge = ChartFactory.createPieChart("Répartition par tranche d'âge", datasetTrancheAge);
	        add(new ChartPanel(chartTrancheAge));
	    }

	    public static void createCommunityStatistics(Communaute commu) {
	        try {
	            JFrame frame = new JFrame("Statistiques de la communauté");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.getContentPane().add(new GraphiquesStats(commu));
	            frame.pack();
	            frame.setLocationRelativeTo(null);
	            frame.setVisible(true);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
