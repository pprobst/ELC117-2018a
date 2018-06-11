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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import java.util.*;
import javafx.scene.image.*;
import javafx.scene.control.Tooltip;
import java.util.Random;

public class Tela extends Application {
    private Grafo grafo;
    private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private Vertice vertAtual;
    private double raioVert = Vertice.vertRaio();
    private double orgSceneX = 0;
    private double orgSceneY = 0;
    private int dificuldadeNum;
    private Pane pane = new Pane();
    private BorderPane borderPane = new BorderPane();
    private Bounds borda;

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

        // ChoiceBox (seleciona dificuldade)
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
            if (dificuldade == "Fácil") dificuldadeNum = 1;
            else if (dificuldade == "Médio") dificuldadeNum = 2;
            else dificuldadeNum = 3;
            geraGrafoPlanar(dificuldadeNum);
        });

        btnVerifica.setOnMouseClicked(e -> {
            grafo = new Grafo(vertices, arestas);
            Alert aviso = new Alert(AlertType.INFORMATION);
            boolean vitoria = false;
            if (grafo.numVertices() == 0) aviso.setHeaderText("Crie um novo grafo antes!");
            else {
                Image kaosu;
                if (grafo.arestasSobrepostas() == 0) {
                    aviso.setHeaderText("Woah~, você conseguiu!");
                    kaosu = new Image(getClass().getResourceAsStream("kaosu_feliz.png"));
                    vitoria = true;
                } else {
                    aviso.setHeaderText("Ara, não foi desta vez.");
                    aviso.setContentText("Arestas sobrepostas: " + grafo.arestasSobrepostas());
                    kaosu = new Image(getClass().getResourceAsStream("kaosu_triste.png"));
                }
                ImageView kaosuView = new ImageView(kaosu);
                aviso.setGraphic(kaosuView);
            }
            aviso.showAndWait();
            if (vitoria) {
                limpaTudo();
                geraGrafoPlanar(dificuldadeNum);
            }
        });

        btnSair.setOnMouseClicked(e -> {
            stage.close();
        });
    }

    // Gera o grafo
    public void geraGrafoPlanar(int dificuldade) {
        Random random = new Random();
        double chanceAresta = 0.8;

        // Variáveis que decidem uma boa área para dispor o grafo
        // (mais ou menos centrado, com uma breve distância das bordas)
        double distCircs = raioVert*2;
        borda = pane.getBoundsInParent();
        double maxPosX = borda.getMaxX() - distCircs*4;
        double minPosX = borda.getMinX() + distCircs/2;
        double maxPosY = borda.getMaxY() - distCircs*4.5;
        double minPosY = borda.getMinY()/2;
        double numVerticesAprox = 7 * dificuldade;

        // Cria os vértices
        for (int i = 0; i < numVerticesAprox; i++) {
            Vertice novoVert = 
                new Vertice(random.nextInt((int) (maxPosX - minPosX + distCircs)) + minPosX,
                            random.nextInt((int) (maxPosY - minPosY + distCircs)) + minPosY);

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
                        arestas.add(a);
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
                if (v.vertShape() == e0.getTarget()) {
                    vertAtual = v;
                    orgSceneX = e0.getSceneX();
                    orgSceneY = e0.getSceneY();
                }
            }
        });

        pane.setOnMouseDragged(e1 -> {
            if (vertAtual != null) {
                vertAtual.vertShape().setOnMouseDragged(e2 -> {
                    double offsetX = e2.getSceneX() - orgSceneX;
                    double offsetY = e2.getSceneY() - orgSceneY;
                    Circle c = (Circle) vertAtual.vertShape();
                    double x = c.getCenterX() + offsetX;
                    double y = c.getCenterY() + offsetY;
                    if (dentroDoPaneX(x)) {
                        vertAtual.setVertX(vertAtual.vertX() + offsetX);
                        c.setCenterX(c.getCenterX() + offsetX);
                        orgSceneX = e2.getSceneX();
                    }
                    if (dentroDoPaneY(y)) {
                        vertAtual.setVertY(vertAtual.vertY() + offsetY);
                        c.setCenterY(c.getCenterY() + offsetY);
                        orgSceneY = e2.getSceneY();
                    }
                    reconectaArestas();
                });
                vertAtual.vertShape().setOnMouseReleased(e3 -> {
                    arestasCorDefault();
                    vertAtual.setVertCorDefault();
                });
            }
        });
    }

    // Verifica se a posição de arraste está entre os limites da largura (X)
    public boolean dentroDoPaneX(double x) {
        if ((x <= borda.getMaxX() - raioVert*3) && (x >= borda.getMinX() - raioVert))
            return true;
        return false;
    }

    // Verifica se a posição de arraste está entre os limites da altura (Y)
    public boolean dentroDoPaneY(double y) {
        if ((y <= borda.getMaxY() - raioVert*6) && (y >= borda.getMinY() - raioVert*4))
            return true;
        return false;
    }

    // Faz as arestas acompanharem o arraste dos vértices, mudando a cor
    public void reconectaArestas() {
        for (Aresta a : vertAtual.vertArestasConectadas()) {
            a.atualizaAresta(vertAtual); 
            a.arestaCor(Color.RED);
            vertAtual.setVertCor(Color.BLUE);
        }
    }

    // Volta para a cor default das arestas
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
