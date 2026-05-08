# Op_Research_Projet_AREZZOUG_Mounia

Projet de recherche opérationnelle en Java


## Compilation

Depuis la racine du projet :

```bash
javac -d out src/*.java
```

## Exécution

### Ford-Fulkerson / flot maximum

```bash
java -cp out Main maxflow graph_data.txt residuel_maxflow.gv
```

### Min-cost flow avec chemins augmentants et Bellman-Ford

```bash
java -cp out Main mcf-bf graph_data.txt residuel_mcf_bf.gv
```

### Min-cost flow avec chemins augmentants et Dijkstra + potentiels

```bash
java -cp out Main mcf-dijkstra graph_data.txt residuel_mcf_dijkstra.gv
```

## Générer le PDF Graphviz

```bash
dot residuel_maxflow.gv -Tpdf > residuel_maxflow.pdf
```


