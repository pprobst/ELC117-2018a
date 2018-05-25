package t5;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import java.util.ArrayList;

public class Tela extends Application {
    private Grafo grafo;
    private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private Vertice origem;
    private Vertice destino;
    private boolean estado = false;
    private int cont = 1;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Editor de Grafos");
        Pane pane = new Pane();
        BorderPane borderPane = new BorderPane();
        Button troca = new Button("Vertice");

        fazVertice(pane);
        troca.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (estado) {
                    fazVertice(pane);
                    troca.setText("Aresta");
                    estado = false;
                    System.out.println("fazVertice");
                }
                else {
                    fazAresta(pane);
                    troca.setText("Vertice");
                    estado = true;
                    System.out.println("fazAresta");
                }
            }
        });

        borderPane.setCenter(pane);
        borderPane.setTop(troca);

        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Desenha aresta 
    // CACETE!
    public void fazAresta(Pane pane) {
        System.out.println("cont == 1");
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e0) { 
                for (Vertice v : vertices) {
                    v.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e1) {
                            if (cont == 1) {
                                origem = v;
                                cont++;
                                System.out.println("cont == 1");
                            }
                            else if (cont == 2) {
                                destino = v;
                                if (destino != origem) {
                                    Aresta aresta = new Aresta(origem, destino);
                                    arestas.add(aresta);
                                    pane.getChildren().add(aresta);
                                }
                                System.out.println("cont == 2");
                                cont = 1;
                            }
                        }
                    });
                }
            }
        });
    }

    // Desenha vértice
    public void fazVertice(Pane pane) {
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                Vertice vertice = new Vertice("teste", e.getX(), e.getY());
                vertices.add(vertice);
                /*vertice.setOnMouseClicked(e2 -> {
                    destino = origem;
                    origem = vertice;
                });*/
                pane.getChildren().add(vertice);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
