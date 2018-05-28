package t5;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Vertice extends Pane {
    private double x;
    private double y;
    private String cor;
    private String formato;

    public Vertice(double x, double y, String cor, String formato) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.formato = formato;
        Shape f;

        if (formato == "Círculo") f = new Circle(x, y, 25);
        else f = new Rectangle(x, y, 50, 50);
        if (cor == "Vermelho") f.setFill(Color.RED);
        else if (cor == "Azul") f.setFill(Color.BLUE);
        else f.setFill(Color.BLACK);

        getChildren().add(f);
    }

    public String vertFormato() {
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
}
