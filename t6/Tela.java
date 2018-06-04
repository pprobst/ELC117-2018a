package t6;

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
import javafx.scene.effect.DropShadow;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import java.util.ArrayList;
import javafx.scene.control.Tooltip;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Tela extends Application {
    private Grafo grafo;
    private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private Vertice origem;
    private Vertice destino;
    private boolean estado;
    private int cont = 1;
    private Pane pane = new Pane();
    private BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage stage) {
        // Título da janela
        stage.setTitle("Planaridade");

        // Faz uma borda no pane
        pane.setStyle("-fx-border-color: black; -fx-border-width: 2.0");
        borderPane.setPadding(new Insets(10, 20, 10, 20));

        // Menu para os botões
        HBox menu = new HBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(5);
        menu.setPadding(new Insets(5, 10, 10, 10));

        // Botões
        Button btnNovoJogo = new Button("Novo Jogo");
        btnNovoJogo.setTooltip(new Tooltip("Faz um novo grafo"));
        Button btnSair = new Button("Sair");
        btnSair.setTooltip(new Tooltip("Não aguento mais este programa ruim!"));

        // ChoiceBoxes
        ChoiceBox<String> cbDificuldade = new ChoiceBox<String>();
        cbDificuldade.getItems().addAll("Fácil", "Médio", "Difícil");
        cbDificuldade.setTooltip(new Tooltip("Dificuldade do jogo"));
        cbDificuldade.setValue("Fácil");

        // Funções dos botões/choicebox do menu ao clicá-los
        clicaMenu(stage, btnNovoJogo, btnSair, cbDificuldade);

        // Adiciona botões/choicebox no menu
        menu.getChildren().addAll(btnNovoJogo, cbDificuldade, btnSair);
        borderPane.setTop(menu);
        borderPane.setCenter(pane);

        // Faz a cena
        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Define a função de cada botão/choicebox
    public void clicaMenu(Stage stage, Button btnNovoJogo, Button btnSair, 
                          ChoiceBox<String> cbDificuldade) {

        btnNovoJogo.setOnMouseClicked(e -> {
            pane.getChildren().clear();
            vertices.clear();
            arestas.clear();
            if (grafo != null) grafo.resetaGrafo();
        });

        btnSair.setOnMouseClicked(e -> {
            stage.close();
        });
    }

    // Desenha aresta 
    public void fazAresta(ChoiceBox<String> cbDificuldade) {
        for (Vertice v : vertices) {
            v.vertShape().setOnMouseClicked(e1 -> {
                DropShadow sombra = new DropShadow();
                sombra.setOffsetY(4.0);
                if (cont == 1) {
                    origem = v;
                    origem.vertShape().setEffect(sombra);
                    origem.vertConecta(origem, null);
                    cont = 2;
                }
                else if (cont == 2) { 
                    destino = v;
                    if (destino != origem && (origem.vertConectado() != destino.vertConectado())) {
                        Aresta aresta = new Aresta(origem, destino);
                        arestas.add(aresta);
                        pane.getChildren().add(aresta.criaAresta());
                        cont = 1;
                        origem.vertConecta(destino, aresta);
                        destino.vertConecta(origem, aresta);
                        origem.vertShape().setEffect(null);
                        origem.vertShape().toFront();
                        destino.vertShape().toFront();
                    } else cont = 2;
                }
            });
        }
    }

    // Desenha vértice
    public void fazVertice(ChoiceBox<String> cbDificuldade) {
        pane.setOnMouseClicked(e0 -> {
            if (estado) {
                Vertice vertice = new Vertice(e0.getX(), e0.getY());
                Shape desenho = vertice.criaVert();
                if (!conflitoVert(desenho) && !(e0.getTarget() instanceof Shape)) {
                    vertices.add(vertice);
                    pane.getChildren().add(desenho);
                }
            }
        });
    }

    // Verifica se há conflito no momento de inserir um novo vértice
    public boolean conflitoVert(Shape vertAtual) {
        for (Vertice v : vertices) {
            Shape intersect = Shape.intersect(v.vertShape(), vertAtual);
            if (intersect.getBoundsInLocal().getWidth() != -1) return true;
        }
        return false;
    }

    // Lança o programa
    public static void main(String[] args) {
        launch(args);
    }
}
