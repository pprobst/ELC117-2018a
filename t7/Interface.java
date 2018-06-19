package t7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

public class Interface extends Application {
    private Control control = new Control();
    //private FilteredList<Onibus> dadosFiltrados;

    @Override
    public void start(Stage stage) {
        Label label = new Label("Frota de ônibus do Rio de Janeiro");
        TableView<Onibus> table = new TableView<Onibus>();

        TableColumn datahCol = new TableColumn("Data/Hora");
        datahCol.setMinWidth(150);
        TableColumn ordemCol = new TableColumn("Ordem");
        ordemCol.setMinWidth(100);
        TableColumn linhaCol = new TableColumn("Linha");
        linhaCol.setMinWidth(100);
        TableColumn latCol = new TableColumn("Latitude");
        latCol.setMinWidth(110);
        TableColumn longCol = new TableColumn("Longitude");
        longCol.setMinWidth(130);
        TableColumn velCol = new TableColumn("Velocidade");
        velCol.setMinWidth(120);

        // Ajeitar mais tarde...
        // Para evitar warnings
        // TableView<DataEntry> table = new TableView<DataEntry>();
        // TableColumn<DataEntry,String> datahCol = new TableColumn<DataEntry,String>("A");
        // TableColumn<DataEntry,String> ordemCol = new TableColumn<DataEntry,String>("B");
        //table.getColumns().add(datahCol);
        //table.getColumns().add(ordemCol);

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

        table.getColumns().addAll(datahCol, ordemCol, linhaCol, latCol, longCol, velCol);

        TextField filtraLinha = new TextField();
        filtraLinha.setPromptText("Linha");
        TextField filtraVelocidade = new TextField();
        filtraVelocidade.setPromptText("Velocidade");
        TextField filtraOrdem = new TextField();
        filtraOrdem.setPromptText("Ordem");

        control.filtraTabela(table, filtraLinha, filtraVelocidade, filtraOrdem);

        HBox hboxGrafs = new HBox();
        hboxGrafs.setSpacing(5);

        Button btnAtualizaDados = new Button("Atualizar dados");
        Button btnAtualizaGrafs = new Button("Atualizar gráficos");
        
        control.botoes(btnAtualizaDados, btnAtualizaGrafs, hboxGrafs);

        HBox hboxFiltros = new HBox();
        hboxFiltros.getChildren().addAll(filtraOrdem, filtraLinha, filtraVelocidade);
        hboxFiltros.setSpacing(5);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, btnAtualizaDados, btnAtualizaGrafs, hboxFiltros, table, hboxGrafs);

        stage.setScene(new Scene(vbox, 730, 800));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
