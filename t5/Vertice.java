package t5;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.util.ArrayList;

public class Vertice {
    private double raio = 25;
    private double tamQuad = 25*2;
    private double x;
    private double y;
    private String cor;
    private String formato;
    private Shape shape;
    private Vertice destino;
    private ArrayList<Aresta> arestasConectadas;

    public Vertice(double x, double y, String cor, String formato) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.formato = formato;
        this.arestasConectadas = new ArrayList<Aresta>();
    }

    public Shape criaVert() {
        Shape f;

        if (this.formato == "Círculo") f = new Circle(this.x, this.y, raio);
        else f = new Rectangle(this.x-(tamQuad/2), this.y-(tamQuad/2), tamQuad, tamQuad);
        if (this.cor == "Vermelho") f.setFill(Color.RED);
        else if (this.cor == "Azul") f.setFill(Color.BLUE);
        else f.setFill(Color.BLACK);
        this.shape = f;

        return f;
    }

    public String vertFormato() {
        return this.formato;
    }

    public Shape vertShape() {
        return this.shape;
    }

    public String vertCor() {
        if (this.cor == "Vermelho") return "red";
        else if (this.cor == "Azul") return "blue";
        return "black";
    }

    public double vertX() {
        return this.x;
    }

    public double vertY() {
        return this.y;
    }

    public double vertTamCentroX() {
        if (this.formato == "Quadrado") 
            return this.x-(this.tamQuad/2);
        return this.vertX();
    }

    public double vertTamCentroY() {
        if (this.formato == "Quadrado") 
            return this.y-(this.tamQuad/2);
        return this.vertY();
    }

    public double vertTam() {
        if (this.formato == "Círculo") {
            return this.raio;
        } else {
            return this.tamQuad/2;
        }
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
