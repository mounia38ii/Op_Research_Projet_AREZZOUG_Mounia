public class Affichage {
    public static void afficherResultat(Graphe graphe, ResultatFlot resultat) {
        System.out.println("Valeur totale du flot = " + resultat.valeurFlot);
        System.out.println("Coût total du flot = " + resultat.coutTotal);
        System.out.println("Flot sur chaque arc original :");
        for (Arc arc : graphe.getArcsOriginaux()) {
            System.out.printf("  %d -> %d : %d/%d, coût=%d%n", arc.depart, arc.arrivee, arc.flot, arc.capacite, arc.cout);
        }
        if (!resultat.arcsCoupeMin.isEmpty()) { // pour ford fulkerson 
            System.out.println("Coupe minimum :");
            for (Arc arc : resultat.arcsCoupeMin) {
                System.out.printf("  %d -> %d%n", arc.depart, arc.arrivee);
            }
        }
    }
}
