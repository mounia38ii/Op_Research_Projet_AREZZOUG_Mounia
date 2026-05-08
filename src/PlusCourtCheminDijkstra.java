import java.util.*;

/*
Dans ce fichier on implémente Djikstra pour calculer les plus courts chemins 
avec les couts réduits (normalisés ) 
*/


public class PlusCourtCheminDijkstra {
    public static Arc[] chercherChemin(Graphe graphe, long[] potentiel) {
        int n = graphe.getNombreNoeuds();
        int source = graphe.getSource();
        int puits = graphe.getPuits();
        long[] distance = new long[n];
        Arc[] parent = new Arc[n];
        // comme dans bellman ford, distance(s)= 0 et les autres noeuds infini
        Arrays.fill(distance, Long.MAX_VALUE / 4);
        distance[source] = 0;


        // on utilise une file de priorité pour prendre la plus petute distance actuelle 
        PriorityQueue<long[]> file = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        file.add(new long[]{source, 0});
        while (!file.isEmpty()) {
            long[] courant = file.poll();
            int u = (int) courant[0];
            if (courant[1] != distance[u]) continue;
            for (Arc arc : graphe.getVoisins(u)) {
                // j'ignore les arcs qui peuvent plus porter de flot
                if (arc.capaciteResiduelle() <= 0) continue; 

                // ici au lieu d'utiliser directement arc.cout on utilise le cout normalisé
                long coutReduit = arc.cout + potentiel[arc.depart] - potentiel[arc.arrivee];
                if (coutReduit < 0) { //si cout réduit est négatifs on peut pas utilisé djikstra 
                    throw new IllegalStateException("Dijkstra ne peut pas traiter un coût réduit négatif : " + arc.depart + " -> " + arc.arrivee);
                }
                // si on trouve un meilleur chemin on met a jour la distance, le parent et aussi la file a priorité 
                if (distance[arc.arrivee] > distance[u] + coutReduit) {
                    distance[arc.arrivee] = distance[u] + coutReduit;
                    parent[arc.arrivee] = arc;
                    file.add(new long[]{arc.arrivee, distance[arc.arrivee]});
                }
            }
        }

        if (parent[puits] == null) return null;
        // apres djikstra on met a jours les potentiels 
        for (int i = 0; i < n; i++) {
            if (distance[i] < Long.MAX_VALUE / 4) potentiel[i] += distance[i];
        }
        return parent;
    }
}
