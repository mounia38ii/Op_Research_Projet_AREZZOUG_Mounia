
/*
Dans ce fichier j'ai fait les 2 versions du min-cost flow
1- avec Bellman Ford
2- avec Djikstra 
et le principe est : 
tant qu’il existe un chemin de s vers t :
    trouver le chemin de coût minimum dans le graphe résiduel
    envoyer le maximum possible sur ce chemin
    mettre à jour le graphe résiduel
*/


public class FlotCoutMinimum {

    // version avec Bellman Ford  (du coup qui peut accepter les couts négatifs)
    public static ResultatFlot calculerAvecBellmanFord(Graphe graphe) {
        int valeurFlot = 0; // au debut j'ai envoyé aucun flot 
        while (true) {
            //avant chaque chemin augmentant je vérifie que y a pas de cycle négatif
            if (DetectionCycleNegatif.existeCycleNegatif(graphe)) {
                throw new IllegalStateException("Cycle négatif détecté dans le graphe résiduel");
            }
            // ici Bellman ford cherche le chemin de cout min de s à t 
            Arc[] parent = PlusCourtCheminBellmanFord.chercherChemin(graphe);
            if (parent == null) break;
            // on prend la plus petite capacité résiduelle du chemin
            int augmentation = calculerAugmentation(parent, graphe.getSource(), graphe.getPuits());
            // et on ajoute le flot sur chaque arc
            appliquerAugmentation(parent, graphe.getSource(), graphe.getPuits(), augmentation);
            valeurFlot += augmentation;
        }
        // et pour CMF je met la coupe min nulle (une liste vide)
        return new ResultatFlot(valeurFlot, graphe.calculerCoutFlot(), java.util.Collections.emptyList());
    }


    // la version avec djikstra (pas de couts négatifs)
    // mais vu que les arcs inverses ont un cout négatifs donc on fait 
    // une normalisation des couts 
    public static ResultatFlot calculerAvecDijkstra(Graphe graphe) {
        int valeurFlot = 0;
        // chaque noeud a un potentiel qui sert a transformer les couts
        //par exemple: pour un arc u->v le cout réduit est : 
        // cout réduit = cout original + potentiel[u]- potentiel[v]
        // c'est pour avoir des couts réduits positifs ou nuls
        long[] potentiel = new long[graphe.getNombreNoeuds()];


        while (true) {
            // on cherche le chemin avec Djikstra 
            Arc[] parent = PlusCourtCheminDijkstra.chercherChemin(graphe, potentiel);
            if (parent == null) break;
            int augmentation = calculerAugmentation(parent, graphe.getSource(), graphe.getPuits());
            appliquerAugmentation(parent, graphe.getSource(), graphe.getPuits(), augmentation);
            valeurFlot += augmentation;
        }
        return new ResultatFlot(valeurFlot, graphe.calculerCoutFlot(), java.util.Collections.emptyList());
    }

    private static int calculerAugmentation(Arc[] parent, int source, int puits) {
        int augmentation = Integer.MAX_VALUE;
        for (Arc arc = parent[puits]; arc != null; arc = parent[arc.depart]) {
            augmentation = Math.min(augmentation, arc.capaciteResiduelle());
            if (arc.depart == source) break;
        }
        return augmentation;
    }

    private static void appliquerAugmentation(Arc[] parent, int source, int puits, int augmentation) {
        for (Arc arc = parent[puits]; arc != null; arc = parent[arc.depart]) {
            arc.ajouterFlot(augmentation);
            if (arc.depart == source) break;
        }
    }
}
