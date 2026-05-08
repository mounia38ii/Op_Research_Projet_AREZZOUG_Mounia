import java.util.*;

/* 
Le fichier ResultatFlot  il me sert à stoquer le résultat d'un algorithme 
la valeur du flot 
le cout total 
et les arcs de la coupe min

Remarque !!!
pour ford fulkeron j'aurais cout du flot obtenu ce n'est pas un tout minimum
et aussi pour le min cost flow la coupe est vide  

*/
public class ResultatFlot {
    public final int valeurFlot;
    public final int coutTotal;
    public final List<Arc> arcsCoupeMin;

    public ResultatFlot(int valeurFlot, int coutTotal, List<Arc> arcsCoupeMin) {
        this.valeurFlot = valeurFlot;
        this.coutTotal = coutTotal;
        this.arcsCoupeMin = arcsCoupeMin;
    }
}
