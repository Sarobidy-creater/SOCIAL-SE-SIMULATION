package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.*;
import gui.GraphiquesStats;
import gui.MapGUI;
import traitement.*;
import traitement.Launcher.FullHousingException;
import parametreSimulation.*;

public class Main {
    public static void main(String args[]) {
        try {
            Communaute communaute = new Communaute(200);
            new MapGUI("Social Simulation", communaute);
        } catch (IOException e) {
            System.err.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (FullHousingException e) {
            System.err.println("Problème de logement : " + e.getMessage());
        }
    }
}
