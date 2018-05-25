package t5;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

public class Vertice extends Pane {
    private String label;
    private double x;
    private double y;

    public Vertice(String label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
        Circle c = new Circle(x, y, 25, Color.BLACK);
        Label lab =  new Label(label);
        getChildren().addAll(c, lab);
    }

    public String vertLabel() {
        return label;
    }

    public double vertX() {
        return x;
    }

    public double vertY() {
        return y;
    }
}
