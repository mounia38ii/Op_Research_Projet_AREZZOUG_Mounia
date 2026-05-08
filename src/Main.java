public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Usage : java Main <maxflow|mcf-bf|mcf-dijkstra> <graph_data.txt> <sortie.gv>");
            return;
        }

        String algorithme = args[0];
        String fichierEntree = args[1];
        String fichierSortie = args[2];
        
        // on lit le graphe 
        Graphe graphe = LecteurGraphe.lireDepuisFichier(fichierEntree);
        ResultatFlot resultat;

        // on choisit l'algo 
        switch (algorithme) {
            case "maxflow": // ford Fulkerson
                resultat = FordFulkerson.calculerFlotMax(graphe);
                break;
            case "mcf-bf": // balleman Ford 
                resultat = FlotCoutMinimum.calculerAvecBellmanFord(graphe);
                break;
            case "mcf-dijkstra": // djikstra 
                resultat = FlotCoutMinimum.calculerAvecDijkstra(graphe);
                break;
            default:
                throw new IllegalArgumentException("Algorithme inconnu : " + algorithme);
        }

        Affichage.afficherResultat(graphe, resultat);
        ExporteurGraphviz.exporterGrapheResiduel(graphe, fichierSortie);
        System.out.println("Commande Graphviz : dot " + fichierSortie + " -Tpdf > res.pdf");
    }
}
