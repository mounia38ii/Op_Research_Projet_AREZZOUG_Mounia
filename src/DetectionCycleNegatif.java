import java.util.*;

/*  
Ce fichier détecte s’il existe un cycle de coût négatif dans le graphe résiduel
c'est un cycle dans la somme des cout est négative 
*/
public class DetectionCycleNegatif {
    public static boolean existeCycleNegatif(Graphe graphe) {
        int n = graphe.getNombreNoeuds();
        long[] distance = new long[n];
        Arrays.fill(distance, 0); 
        // on ilitialide toutes les distances à 0, parce que je veux détécter 
        // un cycle négatifs n'importe ou dans le graphe pas seulement depuis la source 

        // je parcour les arcs comme dans balleman ford 
        for (int iteration = 0; iteration < n - 1; iteration++) {
            boolean modifie = false;
            for (int u = 0; u < n; u++) {
                for (Arc arc : graphe.getVoisins(u)) {
                    if (arc.capaciteResiduelle() > 0 && distance[arc.arrivee] > distance[u] + arc.cout) {
                        distance[arc.arrivee] = distance[u] + arc.cout;
                        modifie = true;
                    }
                }
            }
            if (!modifie) return false;
        }
 
        // apres n-1 itération on refait un passage 
        for (int u = 0; u < n; u++) {
            for (Arc arc : graphe.getVoisins(u)) {
                if (arc.capaciteResiduelle() > 0 && distance[arc.arrivee] > distance[u] + arc.cout) {
                    // si on peut encore améliorer la distance donc c'est un cycle négatif
                    return true;
                }
            }
        }
        return false; // sinon y a pas de cycle négatifs 
    }
}
