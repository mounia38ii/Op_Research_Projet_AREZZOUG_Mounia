import java.io.*;

/*
ce fichier crée le fichier .gv pour dessiner le graphe 
*/
public class ExporteurGraphviz {
    public static void exporterGrapheResiduel(Graphe graphe, String chemin) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(chemin))) {
            writer.println("digraph G {");
            writer.println("  graph [nodesep=\"0.3\", ranksep=\"0.3\", fontsize=12]");
            writer.println("  node [shape=circle, fixedsize=true, width=.3, height=.3, fontsize=12]");
            writer.println("  edge [arrowsize=0.6]");
            writer.println();

            for (int u = 0; u < graphe.getNombreNoeuds(); u++) {
                for (Arc arc : graphe.getVoisins(u)) {
                    if (arc.capaciteResiduelle() > 0) {
                        writer.printf("  %d -> %d [label = <<font color=\"green\">%d</font>,<font color=\"red\">%d</font>>]%n",
                                arc.depart, arc.arrivee, arc.capaciteResiduelle(), arc.cout);
                    }
                }
            }

            writer.println();
            for (int i = 0; i < graphe.getNombreNoeuds(); i++) {
                if (i == graphe.getSource()) writer.printf("  %d [label=\"s=%d\", color=green]%n", i, i);
                else if (i == graphe.getPuits()) writer.printf("  %d [label=\"t=%d\", color=blue]%n", i, i);
                else writer.printf("  %d [label=\"%d\"]%n", i, i);
            }
            writer.println("}");
        }
    }
}
