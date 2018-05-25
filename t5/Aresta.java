package t5;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Aresta extends Line {
    private Vertice origem;
    private Vertice destino;

    public Aresta(Vertice origem, Vertice destino, MouseEvent e) {
        this.origem = origem;
        this.destino = destino;
        Line l = new Line();
        l.setStartX(origem.vertX());
        l.setStartY(origem.vertY());
        l.setEndX(destino.vertX());
        l.setEndY(destino.vertY());
    }

    public Vertice arestaOrigem() {
        return origem;
    }

    public Vertice arestaDestino() {
        return destino;
    }
}
