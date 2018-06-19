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
    private TableView<Onibus> table = new TableView<Onibus>();
    private Info info = new Info();

    @Override
    public void start(Stage stage) {
        Label label = new Label("Frota de ônibus do Rio de Janeiro");

        info.setaURL("http://dadosabertos.rio.rj.gov.br/apiTransporte/apresentacao/rest/index.cfm/obterTodasPosicoes");
        info.criaFrota();

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

        table.setItems(info.listaFrota());
        table.getColumns().addAll(datahCol, ordemCol, linhaCol, latCol, longCol, velCol);

        TextField filterField = new TextField();
        filterField.setPromptText("Linha");
        TextField filtraVelocidade = new TextField();
        filtraVelocidade.setPromptText("Velocidade");
        TextField filtraOrdem = new TextField();
        filtraOrdem.setPromptText("Ordem");

        filtraTabela(filterField, filtraVelocidade, filtraOrdem);

        Button btnAtualiza = new Button("Atualizar");
        btnAtualiza.setOnAction(e -> {
            info.atualizaFrota();
        });


        PieChart grafPizza = fazPizza(); 
        BarChart grafBarra = fazBarra();

        HBox hboxFiltros = new HBox();
        hboxFiltros.getChildren().addAll(filtraOrdem, filterField, filtraVelocidade);
        hboxFiltros.setSpacing(5);

        HBox hboxGrafs = new HBox();
        hboxGrafs.getChildren().addAll(grafPizza, grafBarra);
        hboxGrafs.setSpacing(5);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, btnAtualiza, hboxFiltros, table, hboxGrafs);

        stage.setScene(new Scene(vbox, 800, 600));
        stage.show();
    }

    public void filtraTabela(TextField filterField, TextField filtraVelocidade, 
                             TextField filtraOrdem) {

        ObjectProperty<Predicate<Onibus>> filtroLinha = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroVelocidade = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroOrdem = new SimpleObjectProperty<>();

        filtroLinha.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getLinha().contains(filterField.getText()), 
                    filterField.textProperty()));

        filtroVelocidade.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getVelocidade().contains(filtraVelocidade.getText()), 
                    filtraVelocidade.textProperty()));

        filtroOrdem.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getOrdem().contains(filtraOrdem.getText()), 
                    filtraOrdem.textProperty()));

        FilteredList<Onibus> filteredData = new FilteredList<>(info.listaFrota(), p -> true);
        table.setItems(filteredData);
        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
                    () -> filtroLinha.get().and(filtroVelocidade.get().and(filtroOrdem.get())), 
                    filtroLinha, filtroVelocidade, filtroOrdem));
    }

        public PieChart fazPizza() {
            ObservableList<PieChart.Data> pizzaData = 
                FXCollections.observableArrayList(
                        new PieChart.Data("Veículos parados", info.onibusParadosPercent()), 
                        new PieChart.Data("Veículos em movimento", info.onibusMovimentoPercent()));

            PieChart grafPizza = new PieChart(pizzaData);
            return grafPizza;
        } 

        public BarChart fazBarra() {
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String,Number> grafBarra = new BarChart<String,Number>(xAxis,yAxis);

            xAxis.setLabel("Linha");       
            yAxis.setLabel("Quantidade de veículos");

            XYChart.Series series = new XYChart.Series();

            for (String linha : info.linhasFrota()) {
                int quantVeiculos = info.qtdOnibusLinha(linha);
                series.getData().add(new XYChart.Data(linha, quantVeiculos));
            }

            grafBarra.getData().addAll(series);
            return grafBarra;
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
