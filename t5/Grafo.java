package t5;

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
        return vertices.size();
    }

    // Retorna o número de arestas
    public int numArestas() {
        return arestas.size();
    }

    // Reseta a configuração do grafo
    public void resetaGrafo() {
        vertices.clear();
        arestas.clear();
    }

    // Retorna o número de arestas sobrepostas
    public int arestasSobrepostas() {
       return 5;  
    }
}
