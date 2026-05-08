public class Arc {
    public final int depart;
    public final int arrivee;
    public final int capacite;
    public final int cout;
    public int flot; // quantité de flot actuellement envoyé 
    public Arc inverse; // arc inverse dans le graphe residuel 
    public final boolean original; // trur si c'est un arc du fichier graph_data.txt

    public Arc(int depart, int arrivee, int capacite, int cout, boolean original) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.capacite = capacite;
        this.cout = cout;
        this.flot = 0; /
        this.original = original; 
    }

    /* Fonction capaciteResiduelle()sert à donner la capacité encore disponible sur un arc */
    public int capaciteResiduelle() {
        return capacite - flot;
    }

    /* Fonction ajouterFlot() sert à mettre à jour l'arc inverse quand on ajoute du flot  */
    public void ajouterFlot(int quantite) {
        flot += quantite;
        inverse.flot -= quantite;
    }
}
