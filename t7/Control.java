package t7;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.chart.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.io.File;

/*
 * Classe controladora.
 * Ela faz o intermédio entre o Model (Frota e Onibus) e o View (Interface).
 *
 */
public class Control {
    private Frota frota = new Frota("http://dadosabertos.rio.rj.gov.br/apiTransporte/apresentacao/rest/index.cfm/obterTodasPosicoes");
    private FilteredList<Onibus> dadosFiltrados;

    // Retorna os dados filtrados
    public FilteredList<Onibus> pegaDadosFiltrados() {
        return this.dadosFiltrados;
    }

    // Faz o controle dos botões (bastante coisa...)
    public void botoes(Button btnAtualizaDados, Button btnAtualizaGrafs, Button btnAbreJson, 
            Text txtUltimaLeitura, Text txtTamFrota, Text txtDataMenosRecente, Text txtDataMaisRecente, 
            HBox hboxGrafs, ArrayList<TextField> filtros, Stage stage) {

        btnAtualizaDados.setOnAction(e -> {
            frota.criaFrota();
            this.atualizaInfo(txtUltimaLeitura, txtTamFrota, txtDataMenosRecente, 
                    txtDataMaisRecente);
            this.apagaFiltros(filtros);
            criaGraficos(hboxGrafs);
        });

        btnAtualizaGrafs.setOnAction(e -> {
            criaGraficos(hboxGrafs);
        });

        btnAbreJson.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Abra um arquivo JSON");
            //fc.getExtensionFilters().add(
            //        new ExtensionFilter("Arquivos JSON", "*.json"));
            File arq = fc.showOpenDialog(stage);

            if (arq != null) {
                frota.criaFrotaArq(arq);
                this.atualizaInfo(txtUltimaLeitura, txtTamFrota, txtDataMenosRecente, 
                        txtDataMaisRecente);
                this.apagaFiltros(filtros);
                criaGraficos(hboxGrafs);
            }
        });
    }

    // Apaga o texto dos campos de filtro
    public void apagaFiltros(ArrayList<TextField> filtros) {
        for (TextField filtro : filtros)
            filtro.clear();
    }

    // Faz o controle da edição de comentários
    public void editaComentarios(TableColumn comentCol) {
        comentCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Onibus, String>>() {
                    @Override
                    public void handle(CellEditEvent<Onibus, String> t) {
                        ((Onibus) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                        ).setComentario(t.getNewValue());
                    }
                }
        ); 
    }

    // Atualiza as informações textuais abaixo da tabela
    public void atualizaInfo(Text txtUltimaLeitura, Text txtTamFrota, Text txtDataMenosRecente,
            Text txtDataMaisRecente) {
        txtUltimaLeitura.setText("Data da última leitura: " + frota.serverUltimaLeitura());
        txtTamFrota.setText("Tamanho total da frota: " + frota.tamFrota());
        txtDataMenosRecente.setText("Data menos recente: " + frota.dataMenosRecente());
        txtDataMaisRecente.setText("Data mais recente: " + frota.dataMaisRecente());
    }

    // Cria os gráficos
    public void criaGraficos(HBox hboxGrafs) {
        hboxGrafs.getChildren().clear();
        PieChart grafPizza = fazPizza(); 
        BarChart grafBarra = fazBarra();
        hboxGrafs.getChildren().addAll(grafPizza, grafBarra);
    }

    // Retorna o gráfico de pizza (% de veículos parados e em movimento)
    public PieChart fazPizza() {
        ObservableList<PieChart.Data> pizzaData = 
            FXCollections.observableArrayList(
                    new PieChart.Data("Veículos parados", frota.onibusParadosPercent(dadosFiltrados)), 
                    new PieChart.Data("Veículos em movimento", frota.onibusMovimentoPercent(dadosFiltrados)));

        PieChart grafPizza = new PieChart(pizzaData);
        return grafPizza;
    } 

    // Retorna o gráfico de barras (quant. veículos por linha)
    public BarChart fazBarra() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> grafBarra = new BarChart<String,Number>(xAxis,yAxis);

        xAxis.setLabel("Linha");       
        yAxis.setLabel("Quant. veículos em movimento");

        XYChart.Series series = new XYChart.Series();

        for (String linha : frota.linhasFrotaFiltrada(dadosFiltrados)) {
            int quantVeiculos = frota.qtdOnibusLinha(linha, dadosFiltrados);
            series.getData().add(new XYChart.Data(linha, quantVeiculos));
        }

        grafBarra.getData().addAll(series);
        return grafBarra;
    }

    // Filtra a tabela de acordo com o que foi inserido nos campos de filtragem;
    // Caso os campos estiverem vazios, mostra todos os dados
    public void filtraTabela(TableView<Onibus> table, TextField filtraDatah, TextField filtraLinha, 
            TextField filtraVelocidade, TextField filtraOrdem, TextField filtraComent) {

        ObjectProperty<Predicate<Onibus>> filtroDatah = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroLinha = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroVelocidade = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroOrdem = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Onibus>> filtroComent = new SimpleObjectProperty<>();

        filtroDatah.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getDatah().contains(filtraDatah.getText()), 
                    filtraDatah.textProperty()));

        filtroLinha.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getLinha().contains(filtraLinha.getText()), 
                    filtraLinha.textProperty()));

        filtroVelocidade.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getVelocidade().contains(filtraVelocidade.getText()), 
                    filtraVelocidade.textProperty()));

        filtroOrdem.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getOrdem().contains(filtraOrdem.getText()), 
                    filtraOrdem.textProperty()));

        filtroComent.bind(Bindings.createObjectBinding(() -> 
                    onb -> onb.getComentario().contains(filtraComent.getText()), 
                    filtraComent.textProperty()));

        dadosFiltrados = new FilteredList<>(frota.listaFrota(), p -> true);
        table.setItems(dadosFiltrados);
        dadosFiltrados.predicateProperty().bind(Bindings.createObjectBinding(
                    () -> filtroDatah.get().and(filtroLinha.get().and(filtroVelocidade.get()
                          .and(filtroOrdem.get().and(filtroComent.get())))), 
                          filtroDatah, filtroLinha, filtroVelocidade, filtroOrdem, filtroComent));
    }
}
