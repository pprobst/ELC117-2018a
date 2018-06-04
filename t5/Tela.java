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
import javafx.scene.effect.DropShadow;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import javafx.geometry.Bounds;
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
    private String corAtual;
    private String formatoVertAtual;
    private String formatoArestAtual;
    private Pane pane = new Pane();
    private BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage stage) {
        // Título da janela
        stage.setTitle("Editor de Grafos");

        // Faz uma borda no pane
        pane.setStyle("-fx-border-color: black; -fx-border-width: 2.0");
        borderPane.setPadding(new Insets(10, 20, 10, 20));

        // Menu para os botões
        HBox menu = new HBox();
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(5);
        menu.setPadding(new Insets(5, 10, 10, 10));

        // Botões
        Button btnVertice = new Button("Vértice");
        btnVertice.setTooltip(new Tooltip("Desenha vértices"));
        Button btnAresta = new Button("Aresta");
        btnAresta.setTooltip(new Tooltip("Desenha arestas"));
        Button btnInfo = new Button("Info");
        btnInfo.setTooltip(new Tooltip("Informações do grafo"));
        Button btnNovoGrafo = new Button("Novo Grafo");
        btnNovoGrafo.setTooltip(new Tooltip("Apaga o grafo atual"));
        Button btnSVG = new Button("SVG");
        btnSVG.setTooltip(new Tooltip("Salva o grafo atual em SVG"));
        Button btnSair = new Button("Sair");
        btnSair.setTooltip(new Tooltip("Não aguento mais este programa ruim!"));

        // ChoiceBoxes
        ChoiceBox<String> cbCor = new ChoiceBox<String>();
        ChoiceBox<String> cbFormatoVert = new ChoiceBox<String>();
        ChoiceBox<String> cbFormatoArest = new ChoiceBox<String>();
        cbCor.getItems().addAll("Preto", "Azul", "Vermelho");
        cbCor.setTooltip(new Tooltip("Cor"));
        cbCor.setValue("Preto");
        cbFormatoVert.getItems().addAll("Círculo", "Quadrado");
        cbFormatoVert.setTooltip(new Tooltip("Formato do vértice"));
        cbFormatoVert.setValue("Círculo");
        cbFormatoArest.getItems().addAll("Contínua", "Descontínua");
        cbFormatoArest.setTooltip(new Tooltip("Formato da aresta"));
        cbFormatoArest.setValue("Contínua");

        // Funções dos botões/choiceboxes do menu ao clicá-los
        clicaMenu(stage, btnVertice, btnAresta, btnSair, btnSVG, btnNovoGrafo, 
                btnInfo, cbCor, cbFormatoVert, cbFormatoArest);

        // Adiciona botões/choiceboxes no menu
        menu.getChildren().addAll(btnVertice, btnAresta, cbCor, cbFormatoVert,  
                                  cbFormatoArest, btnInfo, btnNovoGrafo, btnSVG, 
                                  btnSair);
        borderPane.setTop(menu);
        borderPane.setCenter(pane);

        // Faz a cena
        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Define a função de cada botão/choicebox
    public void clicaMenu(Stage stage, Button btnVertice, Button btnAresta, 
                          Button btnSair, Button btnSVG, Button btnNovoGrafo, 
                          Button btnInfo, ChoiceBox<String> cbCor, ChoiceBox<String> cbFormatoVert, 
                          ChoiceBox<String> cbFormatoArest) {

        btnVertice.setOnMouseClicked(e -> {
            estado = true;
            cont = 1;
            fazVertice(cbCor, cbFormatoVert);
        });

        btnAresta.setOnMouseClicked(e -> {
            estado = false;
            fazAresta(cbCor, cbFormatoArest);
        });

        btnSair.setOnMouseClicked(e -> {
            stage.close();
        });

        btnNovoGrafo.setOnMouseClicked(e -> {
            pane.getChildren().clear();
            vertices.clear();
            arestas.clear();
            if (grafo != null) grafo.resetaGrafo();
        });

        btnInfo.setOnMouseClicked(e -> {
            if (grafo == null) grafo = new Grafo(vertices, arestas);
            Alert infoGrafo = new Alert(AlertType.INFORMATION);
            infoGrafo.setContentText("Vértices: " + grafo.numVertices() +
                                     "\nArestas: " + grafo.numArestas() + 
                                     "\nArestas sobrepostas: " + grafo.arestasSobrepostas());
            infoGrafo.setHeaderText("Informações do Grafo");
            infoGrafo.showAndWait();
        });

        btnSVG.setOnMouseClicked(e -> {
            if (grafo == null) grafo = new Grafo(vertices, arestas);
            criaSVG(grafo);
            Alert avisoSalvo = new Alert(AlertType.INFORMATION);
            avisoSalvo.setHeaderText("O grafo foi salvo!");
            avisoSalvo.showAndWait();
        });
    }

    // Desenha aresta 
    public void fazAresta(ChoiceBox<String> cbCor, ChoiceBox<String> cbFormatoArest) {
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
                    formatoArestAtual = cbFormatoArest.getValue().toString();
                    corAtual = cbCor.getValue().toString();
                    if (destino != origem && (origem.vertConectado() != destino.vertConectado())) {
                        Aresta aresta = new Aresta(origem, destino, corAtual, formatoArestAtual);
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
    public void fazVertice(ChoiceBox<String> cbCor, ChoiceBox<String> cbFormatoVert) {
        pane.setOnMouseClicked(e0 -> {
            formatoVertAtual= cbFormatoVert.getValue().toString();
            corAtual = cbCor.getValue().toString();
            if (estado) {
                Vertice vertice = new Vertice(e0.getX(), e0.getY(), corAtual, formatoVertAtual);
                Shape desenho = vertice.criaVert();
                if (!conflitoVert(desenho) && !(e0.getTarget() instanceof Shape) &&
                    dentroDoPane(vertice, e0)) {
                    vertices.add(vertice);
                    pane.getChildren().add(desenho);
                }
            }
        });
    }

    // Verifica se o vértice a ser inserido não ultrapassa o pane
    public boolean dentroDoPane(Vertice vertice, MouseEvent e) {
        Double raio = vertice.vertTam();
        Bounds borda = pane.getBoundsInParent();
        if ((e.getX() > borda.getMinX() + raio/2) && (e.getX() < borda.getMaxX() - raio*2) &&
             (e.getY() > borda.getMinY()/2) && (e.getY() < borda.getMaxY() - raio*3.5)) {
            return true;
        } return false;
    }

    // Verifica se há conflito no momento de inserir um novo vértice
    public boolean conflitoVert(Shape vertAtual) {
        for (Vertice v : vertices) {
            Shape intersect = Shape.intersect(v.vertShape(), vertAtual);
            if (intersect.getBoundsInLocal().getWidth() != -1) return true;
        }
        return false;
    }

    // Cria o arquivo SVG do grafo para ser visualizado em um browser
    public void criaSVG(Grafo grafo) {
        try {
            File arq = new File("grafo.svg");
            arq.createNewFile();
            PrintWriter escreve = new PrintWriter(arq);
            String header = ("<svg version='1.1' xmlns='http://www.w3.org/2000/svg'" +
                             " xmlns:xlink='http://www.w3.org/1999/xlink' viewBox='0 0 1024 768'" +
                             " preserveAspectRatio='xMidYMid meet'>");
            escreve.println(header);

            // Desenha as arestas no SVG
            for (Aresta a : arestas) {
                double x1 = a.arestaOrigem().vertX();
                double y1 = a.arestaOrigem().vertY();
                double x2 = a.arestaDestino().vertX();
                double y2 = a.arestaDestino().vertY();
                String cor = a.arestaCor();
                escreve.println("<line x1='" + x1 + "' y1='" + y1 + "' x2='" + x2 + 
                                "' y2='" + y2 + "' style='stroke:" + cor + ";stroke-width:2");
                if (a.arestaDescontinua())
                    escreve.print(";stroke-dasharray:4px,3px,3px,4px;");
                escreve.print("'/>");
            }

            // Desenha os vértices no SVG
            for (Vertice v : vertices) {
                double tam = v.vertTam();
                String cor = v.vertCor();
                if (v.vertFormato() == "Círculo") {
                    escreve.println("<circle cx='" + v.vertX() + "' cy='" + v.vertY() + "' r='" + tam + 
                                    "' stroke='black' stroke-width='2' fill='" + cor + "'/>");
                }
                else {
                    escreve.println("<rect x='" + v.vertTamCentroX() + "' y='" + v.vertTamCentroY() + 
                                    "' width='" + tam*2 + "' height='" + tam*2 + "' style='fill:" + cor + 
                                    ";stroke-width:2" + ";stroke:black" + "'/>");
                }
            }

            // Escreve informações sobre o grafo no SVG
            int numVert = grafo.numVertices();
            int numArest = grafo.numArestas();
            int arestSobrepost = grafo.arestasSobrepostas();
            escreve.println("<text x='150' y='650' fill='black'>" + 
                            "\nnumVert: " + numVert +" numArest: " + numArest + 
                            " arestSobrepost: " + arestSobrepost + "</text>");

            escreve.println("</svg>");
            escreve.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lança o programa
    public static void main(String[] args) {
        launch(args);
    }
}
