import java.util.*;

public class Graphe {
    private final int nombreNoeuds;
    private final int source; // s 
    private final int puits; // t
    private final List<List<Arc>> adjacence; //liste des arcs sortants de chaque noeud 
    private final List<Arc> arcsOriginaux;  // seulement les arcs lus depuis graph_data.txt

    public Graphe(int nombreNoeuds, int source, int puits) {
        this.nombreNoeuds = nombreNoeuds;
        this.source = source; 
        this.puits = puits; 
        this.adjacence = new ArrayList<>(); 
        this.arcsOriginaux = new ArrayList<>(); // une liste vide pour chaque noueud
        for (int i = 0; i < nombreNoeuds; i++) {
            adjacence.add(new ArrayList<>());
        }
    }

    /* 
    La fonction ajouterArc() fais 5 choses : 
    1- elle crée l'arc direct
    2-elle crée l'arc inverse 
    3-elle relie les deux arcs avec inverse 
    4-elle ajoute les arcs dans la liste d'adjacence 
    5-elle garde l'arc original dans arcsOriginaux 
     */

    public void ajouterArc(int depart, int arrivee, int capacite, int cout) {
        Arc arcDirect = new Arc(depart, arrivee, capacite, cout, true);
        Arc arcInverse = new Arc(arrivee, depart, 0, -cout, false);
        arcDirect.inverse = arcInverse;
        arcInverse.inverse = arcDirect;
        adjacence.get(depart).add(arcDirect);
        adjacence.get(arrivee).add(arcInverse);
        arcsOriginaux.add(arcDirect);
    }

    public int getNombreNoeuds() { return nombreNoeuds; }
    public int getSource() { return source; }
    public int getPuits() { return puits; }
    public List<Arc> getVoisins(int noeud) { return adjacence.get(noeud); }
    public List<Arc> getArcsOriginaux() { return arcsOriginaux; }


    /*
    fonction  calculerValeurFlot() pour calculer la valeur totale du flot
    Donc elle rehgarde tous les arcs qui sortent de la source */
 
    public int calculerValeurFlot() {
        int total = 0;
        for (Arc arc : adjacence.get(source)) {
            if (arc.original) total += arc.flot;
        }
        return total;
    }

    /*
    Fonction calculerCoutFlot() calcule le cout total, 
    elle regarde que les arcs originaux pas les arcs inverse 

    */

    public int calculerCoutFlot() {
        int total = 0;
        for (Arc arc : arcsOriginaux) {
            total += arc.flot * arc.cout;
        }
        return total;
    }

    /*
    Fonction noeudsAccessiblesDansResiduelDepuisSource() sert pour la coupe min
    apres Ford Fulkerson, on regarde le graphe résiduel quels noeud sont encore 
    disponible depuis la source , et pour cela on fait un BFS , on part de la source
    on suit seulement les arcs avec capacité résiduelle >0 , et on marque tous les
    noeuds accessibles et du coup apres ça on a coté source et l'autre coté non
    accessible et la coupe min contient les arcs originaux qui vont d'un noeud
    accessible vers un noeud non accessible
    */

    public boolean[] noeudsAccessiblesDansResiduelDepuisSource() {
        boolean[] visite = new boolean[nombreNoeuds];
        Queue<Integer> file = new ArrayDeque<>();
        visite[source] = true;
        file.add(source);
        while (!file.isEmpty()) {
            int u = file.poll();
            for (Arc arc : adjacence.get(u)) {
                if (arc.capaciteResiduelle() > 0 && !visite[arc.arrivee]) {
                    visite[arc.arrivee] = true;
                    file.add(arc.arrivee);
                }
            }
        }
        return visite;
    }
}
