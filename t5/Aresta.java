package t5;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;

public class Aresta extends Pane {
    private Vertice origem;
    private Vertice destino;

    public Aresta(Vertice origem, Vertice destino) {
        this.origem = origem;
        this.destino = destino;
        Line l = new Line();
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
