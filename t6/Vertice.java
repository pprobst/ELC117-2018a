package t6;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.util.ArrayList;

public class Vertice {
    private double raio = 10;
    private double x;
    private double y;
    private Shape shape;
    private Vertice destino;
    private ArrayList<Aresta> arestasConectadas;

    public Vertice(double x, double y) {
        this.x = x;
        this.y = y;
        this.arestasConectadas = new ArrayList<Aresta>();
    }

    public Shape criaVert() {
        Shape f;

        f = new Circle(this.x, this.y, raio);
        f.setFill(Color.BLACK);
        this.shape = f;

        return f;
    }

    public Shape vertShape() {
        return this.shape;
    }

    public double vertX() {
        return this.x;
    }

    public double vertY() {
        return this.y;
    }

    public void setVertX(double x) {
        this.x = x;
    }

    public void setVertY(double y) {
        this.y = y;
    }

    public double vertRaio() {
        return this.raio;
    }

    public void vertConecta(Vertice destino, Aresta aresta) {
        this.destino = destino;
        if (this != destino && aresta != null) {
            arestasConectadas.add(aresta);
        }
    }
    
    public Vertice vertConectado() {
        return this.destino;
    }

    public int vertQuantArestas() {
        return this.arestasConectadas.size();
    }

    public ArrayList<Aresta> vertArestasConectadas() {
        return this.arestasConectadas;
    }
}
