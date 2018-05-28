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
        Line l = new Line();
        if (cor == "Azul") l.setStyle("-fx-stroke: blue;");
        else if (cor == "Vermelho") l.setStyle("-fx-stroke: red;");
        else l.setStyle("-fx-stroke: black;");
        l.setStartX(origem.vertX());
        l.setStartY(origem.vertY());
        l.setEndX(destino.vertX());
        l.setEndY(destino.vertY());
        getChildren().add(l);
    }

    public Vertice arestaOrigem() {
        return origem;
    }

    public Vertice arestaDestino() {
        return destino;
    }
}
