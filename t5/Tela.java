package t5;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
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
    private boolean estado;
    private int cont = 1;
    private String corAtual;
    private String formatoVertAtual;
    private String formatoArestAtual;

    @Override
    public void start(Stage stage) {
        // Título da janela
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
        Button btnInfo = new Button("Info");
        Button btnNovoGrafo = new Button("Novo Grafo");
        Button btnSair = new Button("Sair");

        // ChoiceBoxes
        ChoiceBox btnCor = new ChoiceBox();
        ChoiceBox btnFormatoVert = new ChoiceBox();
        ChoiceBox btnFormatoArest = new ChoiceBox();
        btnCor.getItems().addAll("Preto", "Azul", "Vermelho");
        btnCor.setTooltip(new Tooltip("Cor"));
        btnCor.setValue("Preto");
        btnFormatoVert.getItems().addAll("Círculo", "Quadrado");
        btnFormatoVert.setTooltip(new Tooltip("Formato do vértice"));
        btnFormatoVert.setValue("Círculo");
        btnFormatoArest.getItems().addAll("Contínua", "Descontínua");
        btnFormatoArest.setTooltip(new Tooltip("Formato da aresta"));
        btnFormatoArest.setValue("Contínua");

        // Funções dos botões ao clicá-los
        btnVertice.setOnMouseClicked(e -> {
            estado = true;
            fazVertice(pane, btnCor, btnFormatoVert);
        });

        btnAresta.setOnMouseClicked(e -> {
            estado = false;
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
        for (Vertice v : vertices) {
            v.vertShape().setOnMouseClicked(e1 -> {
                if (cont == 1) {
                    System.out.println("Vértice origem: " + v);
                    origem = v;
                    cont = 2;
                }
                else if (cont == 2) { 
                    System.out.println("Vértice destino: " + v);
                    destino = v;
                    cont = 1;
                    formatoArestAtual = btnFormatoArest.getValue().toString();
                    corAtual = btnCor.getValue().toString();
                    Aresta aresta = new Aresta(origem, destino, corAtual, 
                            formatoArestAtual);
                    arestas.add(aresta);
                    pane.getChildren().add(aresta.criaAresta());
                }
            });
        }
    }

    // Desenha vértice
    public void fazVertice(Pane pane, ChoiceBox btnCor, ChoiceBox btnFormatoVert) {
        if (estado) {
            pane.setOnMouseClicked(e0 -> {
                formatoVertAtual= btnFormatoVert.getValue().toString();
                corAtual = btnCor.getValue().toString();
                if (estado) {
                    Vertice vertice = new Vertice(e0.getX(), e0.getY(), corAtual, 
                            formatoVertAtual);
                    vertices.add(vertice);
                    pane.getChildren().add(vertice.criaVert());
                }
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
