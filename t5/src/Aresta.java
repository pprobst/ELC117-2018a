package t5;

import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

public class Aresta {
    private Vertice origem;
    private Vertice destino;
    private String formato;
    private Line linha;
    private String cor;

    public Aresta(Vertice origem, Vertice destino, String cor, String formato) {
        this.origem = origem;
        this.destino = destino;
        this.cor = cor;
        this.formato = formato;
    }

    public Line criaAresta() {
        Line l = new Line(this.origem.vertX(), this.origem.vertY(), 
                          this.destino.vertX(), this.destino.vertY());

        if (this.cor == "Azul") l.setStyle("-fx-stroke: blue;");
        else if (this.cor == "Vermelho") l.setStyle("-fx-stroke: red;");
        else l.setStyle("-fx-stroke: black;");
        if (this.formato == "Descontínua") 
              l.getStrokeDashArray().addAll(4.0, 3.0, 3.0, 4.0);
      
        this.linha = l;
        return l;
    }

    public String arestaCor() {
        if (this.cor == "Azul") return "blue";
        else if (this.cor == "Vermelho") return "red";
        return "black";
    }

    public boolean arestaDescontinua() {
        if (this.formato == "Descontínua") return true;
        return false;
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
}
