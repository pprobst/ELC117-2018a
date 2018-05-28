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
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ChoiceBox;
import java.util.ArrayList;
import javafx.scene.control.Tooltip;
import javafx.collections.FXCollections;

public class Tela extends Application {
    private Grafo grafo;
    private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private Vertice origem;
    private Vertice destino;
    private boolean estado = true;
    private int cont = 1;
    private String corAtual;
    private String formatoVertAtual;
    private String formatoArestAtual;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Editor de Grafos");

        // Panes
        Pane pane = new Pane();
        BorderPane borderPane = new BorderPane();

        // Menu para os botões
        HBox menu = new HBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(5);

        // Botões
        Button btnVertice = new Button("Vértice");
        Button btnAresta = new Button("Aresta");
        ChoiceBox btnCor = new ChoiceBox();
        ChoiceBox btnFormatoVert = new ChoiceBox();
        ChoiceBox btnFormatoArest = new ChoiceBox();
        Button btnInfo = new Button("Info");
        Button btnNovoGrafo = new Button("Novo Grafo");
        Button btnSair = new Button("Sair");

        // ChoiceBoxes
        btnCor.getItems().addAll("Preto", "Azul", "Vermelho");
        btnCor.setTooltip(new Tooltip("Cor"));
        btnCor.setValue("Preto");
        btnFormatoVert.getItems().addAll("Círculo", "Quadrado");
        btnFormatoVert.setTooltip(new Tooltip("Formato do vértice"));
        btnFormatoVert.setValue("Círculo");
        btnFormatoArest.getItems().addAll("Contínua", "Descontínua");
        btnFormatoArest.setTooltip(new Tooltip("Formato da aresta"));
        btnFormatoArest.setValue("Contínua");

        System.out.println(btnCor.getValue().toString());

        // Funções dos botões
        btnVertice.setOnMouseClicked(e -> {
            estado = true;
            cont = 1;
            System.out.println("fazVertice");
            corAtual = btnCor.getValue().toString();
            formatoVertAtual = (btnFormatoVert.getValue().toString());
            fazVertice(pane, btnCor, btnFormatoVert);
        });

        btnAresta.setOnMouseClicked(e -> {
            estado = false;
            System.out.println("fazAresta");
            corAtual = btnCor.getValue().toString();
            formatoArestAtual = (btnFormatoArest.getValue().toString());
            fazAresta(pane, btnCor, btnFormatoArest);
        });

        btnSair.setOnMouseClicked(e -> {
            stage.close();
        });

        // Adiciona botões/choiceboxes no menu
        menu.getChildren().addAll(btnVertice, btnAresta, btnCor, btnFormatoVert,  
                                  btnFormatoArest, btnInfo, btnNovoGrafo, btnSair);
        borderPane.setTop(menu);
        borderPane.setCenter(pane);

        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();
}

// Desenha aresta 
public void fazAresta(Pane pane, ChoiceBox btnCor, ChoiceBox btnFormatoArest) {
    //pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
        //public void handle(MouseEvent e0) { 
            //System.out.println(e0.getTarget());
            //System.out.println(e0.getTarget().getSource());
            for (Vertice v : vertices) {
                v.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e1) {
                        System.out.println("Vértice clicada: " + v);
                        if (cont == 1) {
                            origem = v;
                            cont++;
                            System.out.println("cont == 1");
                        }
                        else if (cont == 2) {
                            destino = v;
                            if (destino != origem) {
                                //Aresta aresta = new Aresta(origem, destino);
                                //arestas.add(aresta);
                                //pane.getChildren().add(aresta);
                            }
                            System.out.println("cont == 2");
                            cont = 1;
                        }
                    }
                });
            }
        //}
    //});
}

// Desenha vértice
public void fazVertice(Pane pane, ChoiceBox btnCor, ChoiceBox btnFormatoVert) {
    if (estado) {
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                formatoVertAtual= btnFormatoVert.getValue().toString();
                corAtual = btnCor.getValue().toString();
                Vertice vertice = new Vertice(e.getX(), e.getY(), 
                                              corAtual, formatoVertAtual);
                vertices.add(vertice);
                System.out.println("Vértice adicionado: " + vertice);
                /*vertice.setOnMouseClicked(e2 -> {
                  destino = origem;
                  origem = vertice;
                  System.out.println("Origem: " + vertice);
                  }); */
                pane.getChildren().addAll(vertice);
            }
        });
    }
}

public static void main(String[] args) {
    launch(args);
}
}
