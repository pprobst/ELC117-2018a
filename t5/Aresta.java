package t5;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Aresta extends Pane {
    private Vertice origem;
    private Vertice destino;
    private String formato;
    private String cor;

    public Aresta(Vertice origem, Vertice destino, String cor, String formato) {
        this.origem = origem;
        this.destino = destino;
        this.cor = cor;
        this.formato = formato;
    }

    public Line criaAresta() {
        Line l = new Line();

        if (this.cor == "Azul") l.setStyle("-fx-stroke: blue;");
        else if (this.cor == "Vermelho") l.setStyle("-fx-stroke: red;");
        else l.setStyle("-fx-stroke: black;");
        l.setStartX(this.origem.vertX());
        l.setStartY(this.origem.vertY());
        l.setEndX(this.destino.vertX());
        l.setEndY(this.destino.vertY());

        return l;
    }

    public Vertice arestaOrigem() {
        return origem;
    }

    public Vertice arestaDestino() {
        return destino;
    }
}
