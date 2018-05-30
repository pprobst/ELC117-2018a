package t5;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Vertice extends Pane {
    private double raio = 25;
    private double tamQuad = 25*2;
    private double x;
    private double y;
    private String cor;
    private String formatoS;
    private Shape formato;

    public Vertice(double x, double y, String cor, String formatoS) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.formatoS = formatoS;
    }

    public Shape criaVert() {
        Shape f;

        if (this.formatoS == "Círculo") f = new Circle(this.x, this.y, raio);
        else f = new Rectangle(this.x, this.y, tamQuad, tamQuad);
        if (this.cor == "Vermelho") f.setFill(Color.RED);
        else if (this.cor == "Azul") f.setFill(Color.BLUE);
        else f.setFill(Color.BLACK);
        this.formato = f;

        return f;
    }

    public String vertFormato() {
        return this.formatoS;
    }

    public Shape vertShape() {
        return this.formato;
    }

    public String vertCor() {
        return this.cor;
    }

    public double vertX() {
        return this.x;
    }

    public double vertY() {
        return this.y;
    }

    public double vertTam() {
        if (this.formatoS == "Círculo") {
            return this.raio;
        } else {
            return this.tamQuad;
        }
    }
}
