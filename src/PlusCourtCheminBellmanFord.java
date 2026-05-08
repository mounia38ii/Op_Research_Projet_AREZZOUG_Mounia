import java.util.*;


/*
Ici je cherche un plus court chemin de la source vers le puits avec Bellman-Ford
Il est utilisé pour le min-cost flow avec coûts négatifs
du coup il accepte les arcs de coût négatif
*/
public class PlusCourtCheminBellmanFord {
    public static Arc[] chercherChemin(Graphe graphe) {
        int n = graphe.getNombreNoeuds();
        int source = graphe.getSource();
        int puits = graphe.getPuits();
        long[] distance = new long[n];
        Arc[] parent = new Arc[n];
        //je met toutes les distance à l'infini sauf la source 
        Arrays.fill(distance, Long.MAX_VALUE / 4);
        distance[source] = 0;

        // dans Bellman ford on fait au max n-1 itérations 
        for (int iteration = 0; iteration < n - 1; iteration++) {
            boolean modifie = false;
            // on parcourt tous les arcs 
            for (int u = 0; u < n; u++) {
                if (distance[u] == Long.MAX_VALUE / 4) continue;
                // on utilise que les arcs disponibles dans le graphe résuduel
                for (Arc arc : graphe.getVoisins(u)) {
                    if (arc.capaciteResiduelle() > 0 && distance[arc.arrivee] > distance[u] + arc.cout) {
                        // si passer par u améliore la distance de arc.arrivee je met à jour
                        distance[arc.arrivee] = distance[u] + arc.cout;
                        parent[arc.arrivee] = arc;
                        modifie = true;
                    }
                }
            }
            if (!modifie) break;
        }
        if (parent[puits] == null ){ 
            // ça veut dire qu'il n'existe pas de chemin de s à t 
            return null;
        }
        return  parent; // le tableau parent permet de reconstruire le chemin 
    }
}
