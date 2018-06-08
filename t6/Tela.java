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
import java.util.*;
import javafx.scene.control.Tooltip;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Tela extends Application {
    private Grafo grafo;
    private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private Vertice vertAtual;
    private double orgSceneX = 0;
    private double orgSceneY = 0;
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
        Button btnVerifica = new Button("Verificar");
        btnVerifica.setTooltip(new Tooltip("Verifica se a solução está correta"));
        Button btnSair = new Button("Sair");
        btnSair.setTooltip(new Tooltip("Não aguento mais este programa ruim!"));

        // ChoiceBox (selectiona dificuldade)
        ChoiceBox<String> cbDificuldade = new ChoiceBox<String>();
        cbDificuldade.getItems().addAll("Fácil", "Médio", "Difícil");
        cbDificuldade.setTooltip(new Tooltip("Dificuldade do jogo"));
        cbDificuldade.setValue("Médio");

        // Funções dos botões/choicebox do menu ao clicá-los
        clicaMenu(stage, btnNovoJogo, btnVerifica, btnSair, cbDificuldade);

        // Adiciona botões/choicebox no menu
        menu.getChildren().addAll(btnNovoJogo, cbDificuldade, btnVerifica, btnSair);
        borderPane.setTop(menu);
        borderPane.setCenter(pane);

        // Controla o arraste dos vértices
        arrastaVertice();

        // Faz a cena
        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Define a função de cada botão/choicebox
    public void clicaMenu(Stage stage, Button btnNovoJogo, Button btnVerifica, 
                          Button btnSair, ChoiceBox<String> cbDificuldade) {

        btnNovoJogo.setOnMouseClicked(e -> {
            limpaTudo();
            String dificuldade = cbDificuldade.getValue().toString(); 
            double dificuldadeNum;
            if (dificuldade == "Fácil") dificuldadeNum = 1;
            else if (dificuldade == "Médio") dificuldadeNum = 2;
            else dificuldadeNum = 3;
            geraGrafo(dificuldadeNum);
            grafo = new Grafo(vertices, arestas);
        });

        btnVerifica.setOnMouseClicked(e -> {
            grafo = new Grafo(vertices, arestas);
            if (grafo.arestasSobrepostas() == 0) {
                // lógica de sucesso
                limpaTudo();
            }
        });

        btnSair.setOnMouseClicked(e -> {
            stage.close();
        });
    }

    // Gera o grafo
    public void geraGrafo(double dificuldade) {
        Random random = new Random();
        double chanceAresta = 0.8 * dificuldade;
        int maxPos = 500;
        int minPos = 50;
        int numVerticesAprox = 7 * (int) dificuldade;
        int distCircs = 25;

        // Cria os vértices
        for (int i = 0; i < numVerticesAprox; i++) {
            Vertice novoVert = 
                new Vertice(random.nextInt(maxPos - minPos + distCircs) + minPos,
                            random.nextInt(maxPos - minPos + distCircs) + minPos);

            // Se não ocorreu conflito de posição entre círculos, ok
            if (!conflitoVert(novoVert)) {
                vertices.add(novoVert);
                pane.getChildren().add(novoVert.criaVert());
            }
        }

        int linhaLen = (int) Math.sqrt(vertices.size());

        // Cria as arestas
        for (int i = 0; i < vertices.size(); i++) {
            int xn = i % linhaLen;
            int yn = i / linhaLen;
            for (int dy = -1; dy <= 0; dy++) {
                for (int dx = -1; dx <= 0; dx++) {
                    int x = xn + dx;
                    int y = yn + dy;
                    double rnd = random.nextFloat();
                    if (x >= 0 && y >= 0 && (dx != 0 || dy != 0) && rnd < chanceAresta) {
                        int j = x + y * linhaLen;
                        Vertice v1 = vertices.get(i);
                        Vertice v2 = vertices.get(j);
                        Aresta a = new Aresta(v1, v2);
                        v1.vertConecta(v2, a);
                        v2.vertConecta(v1, a);
                        v1.vertShape().toFront();
                        v2.vertShape().toFront();
                        pane.getChildren().add(a.criaAresta());
                    }
                }
            }
        }
        removeSolitarios();
    }

    // Verifica se há conflito no momento de inserir um novo vértice
    public boolean conflitoVert(Vertice vertInserido) {
        if (vertices.size() > 0) {
            for (Vertice v : vertices) {
                double difX = v.vertX() - vertInserido.vertX();
                double difY = v.vertY() - vertInserido.vertY();
                double distQuad = difX * difX + difY * difY;
                double somaRaios = v.vertRaio() + vertInserido.vertRaio();
                if (distQuad < (somaRaios * somaRaios)) 
                    return true;
            }
        }
        return false;
    }


    // Remove os vértices solitários (RIP)
    public void removeSolitarios() {
        if (vertices.size() > 0) {
            for (int i = 0; i < vertices.size(); i++) {
                Vertice v = vertices.get(i);
                if (v.vertQuantArestas() == 0) {
                    pane.getChildren().remove(v.vertShape());
                    vertices.remove(i);
                }
            }
        }
    }

    // Reseta o grafo e limpa a tela
    public void limpaTudo() {
        if (grafo != null) grafo.resetaGrafo();
        vertices.clear();
        arestas.clear();
        pane.getChildren().clear();
    }

    // Arrasta um vértice no plano
    public void arrastaVertice() {
        pane.setOnMousePressed(e0 -> {
            for (Vertice v : vertices) {
                v.vertShape().toFront();
                v.vertShape().setOnMousePressed(e1 -> {
                    vertAtual = v;
                    orgSceneX = e1.getSceneX();
                    orgSceneY = e1.getSceneY();
                });
            }
        //});

        //pane.setOnMouseDragged(e2 -> {
            if (vertAtual != null) {
                vertAtual.vertShape().setOnMouseDragged(e3 -> {
                    double offsetX = e3.getSceneX() - orgSceneX;
                    double offsetY = e3.getSceneY() - orgSceneY;
                    vertAtual.setVertX(vertAtual.vertX() + offsetX);
                    vertAtual.setVertY(vertAtual.vertY() + offsetY);
                    Circle c = (Circle) vertAtual.vertShape();
                    c.setCenterX(c.getCenterX() + offsetX);
                    c.setCenterY(c.getCenterY() + offsetY);
                    orgSceneX = e3.getSceneX();
                    orgSceneY = e3.getSceneY();
                    reconectaArestas();
                });
                vertAtual.vertShape().setOnMouseReleased(e4 -> {
                    arestasCorDefault();
                    vertAtual.setVertCorDefault();
                });
            }
        });
    }

    // Faz as arestas acompanharem o arraste dos vértices
    public void reconectaArestas() {
        for (Aresta a : vertAtual.vertArestasConectadas()) {
            a.atualizaAresta(vertAtual); 
            a.arestaCor(Color.RED);
            vertAtual.setVertCor(Color.BLUE);
        }
    }

    public void arestasCorDefault() {
        for (Aresta a : vertAtual.vertArestasConectadas()) {
            a.arestaCorDefault();
        }
    }

    // Lança o programa
    public static void main(String[] args) {
        launch(args);
    }
}
