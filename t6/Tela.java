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
    private Vertice origem;
    private Vertice destino;
    private Vertice vertAtual;
    private boolean estado;
    private int cont = 1;
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

        // ChoiceBoxes
        ChoiceBox<String> cbDificuldade = new ChoiceBox<String>();
        cbDificuldade.getItems().addAll("Fácil", "Médio", "Difícil");
        cbDificuldade.setTooltip(new Tooltip("Dificuldade do jogo"));
        cbDificuldade.setValue("Fácil");

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
            int numArestas = 10;
            geraGrafo(numArestas);
            // lógica de novo grafo
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

    /*
    public void geraLinhas(int n) {
        ArrayList<Aresta> linhas = new ArrayList<Aresta>();
        int numVertices =  n(n-1)/2;
        int numArestas =  n(n-2);

        while (linhas.size() < n) {
            Vertice v1 = new Vertice(200);
            Vertice origem = new Vertice(200+(i*15+20), 150+(i*15+20));
            Vertice destino = new Vertice(200+(i*15+20), 300+(i*15)); 
            vertices.add(origem);
            vertices.add(destino);

        }
    }
    */

    // Gera o grafo
    public void geraGrafo(int numArestas) {
        try {
            int minV = (int) Math.ceil((1 + Math.sqrt(1 + 8 * numArestas)) / 2);
            int maxV = numArestas + 1;

            Random random = new Random();
            int v = Math.abs(random.nextInt(maxV - minV) + minV);
            System.out.println("O grafo aleatório tem " + v + " vértices.");

            int i = 0;
            while (i <= v/2) {
                Vertice origem = new Vertice(200+(i*35+20), 150+(i*15+20));
                Vertice destino = new Vertice(200+(i*35+20), 300+(i*15)); 
                Aresta novaAresta = new Aresta(origem, destino);
                origem.vertConecta(destino, novaAresta);
                destino.vertConecta(origem, novaAresta);
                vertices.add(origem);
                vertices.add(destino);
                arestas.add(novaAresta);
                pane.getChildren().addAll(origem.criaVert(), destino.criaVert(), 
                                          novaAresta.criaAresta());
                i++;
            }
        } 
        catch (Exception E) {
            System.out.println("Algo deu errado...");
        }
    }  

    // Reseta o grafo e limpa a tela
    public void limpaTudo() {
        if (grafo != null) grafo.resetaGrafo();
        vertices.clear();
        arestas.clear();
        pane.getChildren().clear();
    }

    // Desenha aresta 
    /*
    public void fazAresta(ChoiceBox<String> cbDificuldade) {
        for (Vertice v : vertices) {
            v.vertShape().setOnMouseClicked(e1 -> {
                DropShadow sombra = new DropShadow();
                sombra.setOffsetY(4.0);
                if (cont == 1) {
          i          origem = v;
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
    */

    // Arrasta um vértice no plano
    public void arrastaVertice() {
        pane.setOnMousePressed(e0 -> {
            for (Vertice v : vertices) {
                System.out.println(v);
                v.vertShape().setOnMousePressed(e1 -> {
                    vertAtual = v;
                    System.out.println("vertAtual: " + v);
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
            }
        });
    }

    public void reconectaArestas() {
        for (Aresta a : vertAtual.vertArestasConectadas()) {
            System.out.println("aresta: " + a);
            a.atualizaAresta(vertAtual); 
        }
    }

    // Desenha vértice
    /*
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
    */

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
