package t7;

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

public class Control {
    private Frota frota = new Frota();
    private FilteredList<Onibus> dadosFiltrados;

    public Control() {
        frota.setaURL("http://dadosabertos.rio.rj.gov.br/apiTransporte/apresentacao/rest/index.cfm/obterTodasPosicoes");
    }

    public FilteredList<Onibus> pegaDadosFiltrados() {
        return this.dadosFiltrados;
    }

    public void botoes(Button btnAtualizaDados, Button btnAtualizaGrafs, HBox hboxGrafs) {
        btnAtualizaDados.setOnAction(e -> {
            frota.criaFrota();
            System.out.println();
            criaGraficos(hboxGrafs);
        });

        btnAtualizaGrafs.setOnAction(e -> {
            criaGraficos(hboxGrafs);
        });
    }

    public void criaGraficos(HBox hboxGrafs) {
        hboxGrafs.getChildren().clear();
        PieChart grafPizza = fazPizza(); 
        BarChart grafBarra = fazBarra();
        hboxGrafs.getChildren().addAll(grafPizza, grafBarra);
    }

    public PieChart fazPizza() {
        ObservableList<PieChart.Data> pizzaData = 
            FXCollections.observableArrayList(
                    new PieChart.Data("Veículos parados", frota.onibusParadosPercent(dadosFiltrados)), 
                    new PieChart.Data("Veículos em movimento", frota.onibusMovimentoPercent(dadosFiltrados)));

        PieChart grafPizza = new PieChart(pizzaData);
        return grafPizza;
    } 

    public BarChart fazBarra() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> grafBarra = new BarChart<String,Number>(xAxis,yAxis);

        xAxis.setLabel("Linha");       
        yAxis.setLabel("Quant. veículos");

        XYChart.Series series = new XYChart.Series();

        for (String linha : frota.linhasFrotaFiltrada(dadosFiltrados)) {
            int quantVeiculos = frota.qtdOnibusLinha(linha, dadosFiltrados);
            series.getData().add(new XYChart.Data(linha, quantVeiculos));
        }

        grafBarra.getData().addAll(series);
        return grafBarra;
    }

    public void filtraTabela(TableView<Onibus> table, TextField filtraLinha, TextField filtraVelocidade, 
                             TextField filtraOrdem) {

        ObjectProperty<Predicate<Onibus>> filtroLinha = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroVelocidade = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroOrdem = new SimpleObjectProperty<>();

        filtroLinha.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getLinha().contains(filtraLinha.getText()), 
                    filtraLinha.textProperty()));

        filtroVelocidade.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getVelocidade().contains(filtraVelocidade.getText()), 
                    filtraVelocidade.textProperty()));

        filtroOrdem.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getOrdem().contains(filtraOrdem.getText()), 
                    filtraOrdem.textProperty()));

        dadosFiltrados = new FilteredList<>(frota.listaFrota(), p -> true);
        table.setItems(dadosFiltrados);
        dadosFiltrados.predicateProperty().bind(Bindings.createObjectBinding(
                    () -> filtroLinha.get().and(filtroVelocidade.get().and(filtroOrdem.get())), 
                    filtroLinha, filtroVelocidade, filtroOrdem));
    }
}
