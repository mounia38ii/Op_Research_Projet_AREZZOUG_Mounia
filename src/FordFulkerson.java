import java.util.*;


/*
dans ce fichier j'ai implémenté le flot maximum, on utilise un BfS pour 
trouver les chemins augmentants 
le principe :
tant qu’il existe un chemin de s vers t dans le graphe résiduel :
    trouver le chemin
    trouver la capacité minimale sur ce chemin
    ajouter ce flot sur le chemin
 */

public class FordFulkerson {
    public static ResultatFlot calculerFlotMax(Graphe graphe) {
        int n = graphe.getNombreNoeuds();
        int source = graphe.getSource();
        int puits = graphe.getPuits();
        int valeurFlot = 0;

        while (true) {
            Arc[] parent = new Arc[n]; // c'est pour reconstruire le chemin trouvé 
            Queue<Integer> file = new ArrayDeque<>();
            file.add(source);

            // j'ai utilisé un BFS avec une file 

            while (!file.isEmpty() && parent[puits] == null) {
        
                int u = file.poll();
                for (Arc arc : graphe.getVoisins(u)) {
                    // on suit un arc seulement si il vérifie les 
                    // conditions : 
                    // 1- le noeud d'arrivé n'a pas été deja visité 
                    // 2- ce n'est pas la source 
                    // 3- la capacité résiduelle est strictement positive (sinon il est saturé)
                    if (parent[arc.arrivee] == null && arc.arrivee != source && arc.capaciteResiduelle() > 0) {
                        parent[arc.arrivee] = arc;
                        file.add(arc.arrivee);
                    }
                }
            }

            if (parent[puits] == null) break; 
            // si on arrive pas au puit ça veut dire 
            // il n'ya plus de chemin augmentant donc le flot est max 
            // donc on sort de la boucle 

            int augmentation = Integer.MAX_VALUE; // calcul de l'augmentation 
            // on reegarde tous les arcs et on prend la plus petite capacité résiduelle 
            for (Arc arc = parent[puits]; arc != null; arc = parent[arc.depart]) {
                augmentation = Math.min(augmentation, arc.capaciteResiduelle());
            }
            // et puis on ajoute ce flot sur les arcs du chemin
            for (Arc arc = parent[puits]; arc != null; arc = parent[arc.depart]) {
                arc.ajouterFlot(augmentation);
            }
            valeurFlot += augmentation;
        }

        return new ResultatFlot(valeurFlot, graphe.calculerCoutFlot(), calculerCoupeMin(graphe));
    }

    /*
    La fonction calculerCoupeMin(Graphe graphe) calcule la coupe min 
    on récupere tous les arcs accessibles depuis la source dans le graphe résiduel
    et on applique : un arc appartient a la coupe min si :
    1- son départ est encore accessible depuis la source 
    2- son arrivé n'est plus accessible 
    */
    private static List<Arc> calculerCoupeMin(Graphe graphe) {
        boolean[] coteSource = graphe.noeudsAccessiblesDansResiduelDepuisSource();
        List<Arc> coupe = new ArrayList<>();
        for (Arc arc : graphe.getArcsOriginaux()) {
            if (coteSource[arc.depart] && !coteSource[arc.arrivee]) {
                coupe.add(arc);
            }
        }
        return coupe;
    }
}
