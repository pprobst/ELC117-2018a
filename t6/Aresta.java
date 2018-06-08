package t6;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Aresta {
    private Vertice origem;
    private Vertice destino;
    private String formato;
    private Line linha;

    public Aresta(Vertice origem, Vertice destino) {
        this.origem = origem;
        this.destino = destino;
    }

    public Line criaAresta() {
        Line l = new Line(this.origem.vertX(), this.origem.vertY(), 
                          this.destino.vertX(), this.destino.vertY());

        this.linha = l;
        return l;
    }

    public void atualizaAresta(Vertice v) {
        if (v == this.origem) {
            this.linha.setStartX(v.vertX());
            this.linha.setStartY(v.vertY());
        } else {
            this.linha.setEndX(v.vertX());
            this.linha.setEndY(v.vertY());
        }
    }

    public Line arestaLinha() {
        return this.linha;
    }

    public Vertice arestaOrigem() {
        return this.origem;
    }

    public Vertice arestaDestino() {
        return this.destino;
    }

    public void arestaCor(Color cor) {
        this.linha.setStroke(cor);
    }

    public void arestaCorDefault() {
        this.linha.setStroke(Color.BLACK);
    }
}
