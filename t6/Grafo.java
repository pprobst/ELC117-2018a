package t6;

import javafx.scene.shape.*;
import java.util.ArrayList;

public class Grafo {
    public ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    public ArrayList<Aresta> arestas = new ArrayList<Aresta>();

    // Construtor do Grafo
    public Grafo(ArrayList<Vertice> vertices, ArrayList<Aresta> arestas) {
        this.vertices = vertices;
        this.arestas = arestas;
    }

    // Retorna o número de vértices
    public int numVertices() {
        return this.vertices.size();
    }

    // Retorna o número de arestas
    public int numArestas() {
        return this.arestas.size();
    }

    // Reseta a configuração do grafo
    public void resetaGrafo() {
        this.vertices.clear();
        this.arestas.clear();
    }

    // Retorna o TOTAL de arestas sobrepostas de uma lista de arestas
    public int sobreposArestas(ArrayList<Aresta> listaAresta) {
        int cont = 0;
        for (int i = 0; i < listaAresta.size(); i++) {
            for (int j = i+1; j < listaAresta.size();  j++) {
                Shape intersect = Shape.intersect(listaAresta.get(i).arestaLinha(), 
                                                  listaAresta.get(j).arestaLinha());
                if (intersect.getBoundsInLocal().getWidth() != -1) 
                    cont++;
            }
        }
        return cont;
    }

    // Returna o TOTAL de arestas sobrepostas dentro de um vértice
    public int sobreposArestVert() {
        int cont = 0;
        for (Vertice v : this.vertices) {
            ArrayList<Aresta> conexoes = v.vertArestasConectadas();
            cont += sobreposArestas(conexoes);
        }
        return cont;
    }

    // Retorna o número de arestas sobrepostas do grafo, exluindo as sobreposições
    // de dentro dos vértices
    public int arestasSobrepostas() {
        return sobreposArestas(this.arestas) - sobreposArestVert();
    }
}
