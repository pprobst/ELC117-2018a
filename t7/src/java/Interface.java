package t7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.chart.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

/*
 * Classe Interface (View).
 * Cria as bases para a interface do programa e atua como lançador.
 *
 */
public class Interface extends Application {
    private Control control = new Control();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Frota de ônibus do Rio de Janeiro");
        TableView<Onibus> table = new TableView<Onibus>();

        table.setEditable(true);

        // Cria colunas
        TableColumn<Onibus,String> datahCol = new TableColumn<Onibus,String>("Data/Hora");
        datahCol.setMinWidth(150);
        TableColumn<Onibus,String> ordemCol = new TableColumn<Onibus,String>("Ordem");
        ordemCol.setMinWidth(100);
        TableColumn<Onibus,String> linhaCol = new TableColumn<Onibus,String>("Linha");
        linhaCol.setMinWidth(100);
        TableColumn<Onibus,String> latCol = new TableColumn<Onibus,String>("Latitude");
        latCol.setMinWidth(110);
        TableColumn<Onibus,String> longCol = new TableColumn<Onibus,String>("Longitude");
        longCol.setMinWidth(130);
        TableColumn<Onibus,String> velCol = new TableColumn<Onibus,String>("Velocidade");
        velCol.setMinWidth(120);
        TableColumn<Onibus,String> comentCol = new TableColumn<Onibus,String>("Comentário");
        comentCol.setMinWidth(155);

        datahCol.setEditable(false);
        ordemCol.setEditable(false);
        linhaCol.setEditable(false);
        latCol.setEditable(false);
        longCol.setEditable(false);
        velCol.setEditable(false);
        comentCol.setEditable(true);

        datahCol.setCellValueFactory(
                new PropertyValueFactory<Onibus, String>("datah"));
        datahCol.setCellFactory(TextFieldTableCell.forTableColumn());

        ordemCol.setCellValueFactory(
                new PropertyValueFactory<Onibus, String>("ordem"));
        ordemCol.setCellFactory(TextFieldTableCell.forTableColumn());

        linhaCol.setCellValueFactory(
                new PropertyValueFactory<Onibus, String>("linha"));
        linhaCol.setCellFactory(TextFieldTableCell.forTableColumn());

        latCol.setCellValueFactory(
                new PropertyValueFactory<Onibus, String>("latitude"));
        latCol.setCellFactory(TextFieldTableCell.forTableColumn());

        longCol.setCellValueFactory(
                new PropertyValueFactory<Onibus, String>("longitude"));
        longCol.setCellFactory(TextFieldTableCell.forTableColumn());

        velCol.setCellValueFactory(
                new PropertyValueFactory<Onibus, String>("velocidade"));
        velCol.setCellFactory(TextFieldTableCell.forTableColumn());

        comentCol.setCellValueFactory(
                new PropertyValueFactory<Onibus, String>("comentario"));
        comentCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // Adiciona as colunas na tabela
        // (para não gerar warning parece que se deve fazer assim...)
        table.getColumns().add(datahCol);
        table.getColumns().add(ordemCol);
        table.getColumns().add(linhaCol);
        table.getColumns().add(latCol);
        table.getColumns().add(longCol);
        table.getColumns().add(velCol);
        table.getColumns().add(comentCol);

        // Áreas de filtragem
        TextField filtraDatah = new TextField();
        filtraDatah.setPromptText("Data/Hora");
        TextField filtraLinha = new TextField();
        filtraLinha.setPromptText("Linha");
        TextField filtraVelocidade = new TextField();
        filtraVelocidade.setPromptText("Velocidade");
        TextField filtraOrdem = new TextField();
        filtraOrdem.setPromptText("Ordem");
        TextField filtraComent = new TextField();
        filtraComent.setPromptText("Comentário");

        ArrayList<TextField> filtros = new ArrayList<TextField>(
                                           Arrays.asList(filtraDatah, filtraLinha, filtraVelocidade, 
                                                         filtraOrdem, filtraComent));

        HBox hboxGrafs = new HBox();
        hboxGrafs.setSpacing(5);
        
        // Botões
        Button btnAtualizaDados = new Button("Atualizar dados");
        Button btnAtualizaGrafs = new Button("Atualizar gráficos");
        Button btnAbreJSON = new Button("Abrir JSON");

        // File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir JSON");
        
        // Textos informativos (abaixo da tabela)
        Text txtUltimaLeitura = new Text();
        txtUltimaLeitura.setWrappingWidth(170);
        txtUltimaLeitura.setText("Data da última leitura: N/A");
        Text txtTamFrota = new Text();
        txtTamFrota.setWrappingWidth(170);
        txtTamFrota.setText("Tamanho total da frota: N/A");
        Text txtDataMenosRecente = new Text();
        txtDataMenosRecente.setWrappingWidth(150);
        txtDataMenosRecente.setText("Data menos recente: N/A");
        Text txtDataMaisRecente = new Text();
        txtDataMaisRecente.setWrappingWidth(150);
        txtDataMaisRecente.setText("Data mais recente: N/A");

        // Controla a edição de comentários
        control.editaComentarios(comentCol);
        
        // Controla a filtragem de dados
        control.filtraTabela(table, filtraDatah, filtraLinha, filtraVelocidade, filtraOrdem, 
                             filtraComent);

        // Controla a função de cada botão
        control.botoes(btnAtualizaDados, btnAtualizaGrafs, btnAbreJSON, txtUltimaLeitura, 
                       txtTamFrota, txtDataMenosRecente, txtDataMaisRecente, hboxGrafs, filtros, stage);

        HBox hboxFiltros = new HBox();
        hboxFiltros.getChildren().addAll(filtraDatah, filtraOrdem, filtraLinha, filtraVelocidade,
                                         filtraComent);
        hboxFiltros.setSpacing(5);

        HBox hboxBotoes = new HBox();
        hboxBotoes.getChildren().addAll(btnAtualizaDados, btnAtualizaGrafs, btnAbreJSON);
        hboxBotoes.setSpacing(5);

        HBox hboxInfo = new HBox();
        hboxInfo.getChildren().addAll(txtUltimaLeitura, txtTamFrota, txtDataMenosRecente,
                                      txtDataMaisRecente);
        hboxInfo.setSpacing(5);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(hboxBotoes, hboxFiltros, table, hboxInfo, hboxGrafs);

        stage.setScene(new Scene(vbox, 900, 850));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
