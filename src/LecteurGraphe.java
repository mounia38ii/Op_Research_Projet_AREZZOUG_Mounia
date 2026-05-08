import java.io.*;
import java.util.*;

/*
Ce fichier LecteurGraphe il sert a lire le graphe du fichier txt d'entrée 
et il le transforme en objet graphe
*/

public class LecteurGraphe {
    public static Graphe lireDepuisFichier(String chemin) throws IOException {
        try (Scanner scanner = new Scanner(new File(chemin))) {
            int nombreNoeuds = scanner.nextInt();
            int nombreArcs = scanner.nextInt();
            int source = scanner.nextInt();
            int puits = scanner.nextInt();
            Graphe graphe = new Graphe(nombreNoeuds, source, puits);
            for (int i = 0; i < nombreArcs; i++) {
                int depart = scanner.nextInt();
                int arrivee = scanner.nextInt();
                int capacite = scanner.nextInt();
                int cout = scanner.nextInt();
                graphe.ajouterArc(depart, arrivee, capacite, cout);
            }
            return graphe;
        }
    }
}
