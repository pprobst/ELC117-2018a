package t7;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/*
 * Classe que representa a frota de ônibus e os diversos métodos para manipulá-la.
 * Os métodos podem manipular listas com frotas total ou parcial (FilteredList), 
 * sendo esta última utilizada para a elaboração dos gráficos no View/Controller.
 */

public class Frota {
    private API api = new API();
    private String url;
    private ObservableList<Onibus> frota = FXCollections.observableArrayList();
    private ArrayList<String> linhas = new ArrayList<String>();
    private DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public String serverUltimaLeitura() {
        Date dataAtual = new Date();
        String ultimaLeitura = this.dateFormat.format(dataAtual);
        return ultimaLeitura;
    }

    public String dataMaisRecente() {
        Onibus casoBase = this.listaFrota().get(0);
        String data1 = casoBase.getDatah();

        for (Onibus onb : this.listaFrota()) {
            String data2 = onb.getDatah();
            if (dataAntes(data1, data2)) 
                data1 = data2;
        }

        return data1;
    }

    public String dataMenosRecente() {
        Onibus casoBase = this.listaFrota().get(0);
        String data1 = casoBase.getDatah();

        for (Onibus onb : this.listaFrota()) {
            String data2 = onb.getDatah();
            if (!dataAntes(data1, data2)) 
                data1 = data2;
        }

        return data1;
    }

    public Boolean dataAntes(String data1, String data2) {
        Date data1F = null;
        Date data2F = null;

        try {
            data1F = dateFormat.parse(data1);
            data2F = dateFormat.parse(data2);
        } catch (Exception ex) {
            System.out.println(ex);
        } 

        if (data1F.before(data2F)) 
            return true;
        return false;
    } 

    public void criaFrota() {
        this.frota.clear();
        Map json = this.api.muhJSON(this.url);
        List dados = (List)json.get("DATA");

        for (Object dado: dados) {
            List dado_atual = (List)dado;
            String datah = (String)dado_atual.get(0);
            String ordem = (String)dado_atual.get(1);
            String linha = String.valueOf(dado_atual.get(2));
            String latitude = String.valueOf(dado_atual.get(3));
            String longitude = String.valueOf(dado_atual.get(4));
            String velocidade = String.valueOf(dado_atual.get(5));

            Onibus onibus = new Onibus(datah, ordem, linha, latitude,
                                       longitude, velocidade);
            this.frota.add(onibus);    
        }
        this.criaLinhasFrota();
    }

    public ObservableList<Onibus> listaFrota() {
        return this.frota;
    }

    public void atualizaFrota() {
        this.criaFrota();
    }

    public void setaURL(String url) {
        this.url = url;
    }

    public int tamFrota() {
        return this.frota.size();
    }

    public int tamFrotaFiltrado(FilteredList<Onibus> onibusFiltrados) {
        return onibusFiltrados.size();
    }

    public int onibusParados(FilteredList<Onibus> onibusFiltrados) {
        int contPar = 0;

        for (Onibus onb : onibusFiltrados) {
            if (Double.parseDouble(onb.getVelocidade()) <= 0) contPar++;
        }

        return contPar;
    }

    public int onibusMovimento(FilteredList<Onibus> onibusFiltrados) {
        int contMov = 0;

        for (Onibus onb : onibusFiltrados) {
            if (Double.parseDouble(onb.getVelocidade()) >= 0) contMov++; 
        }

        return contMov;
    }

    public double onibusParadosPercent(FilteredList<Onibus> onibusFiltrados) {
        return (this.onibusParados(onibusFiltrados)*100)/this.tamFrotaFiltrado(onibusFiltrados); 
    }

    public double onibusMovimentoPercent(FilteredList<Onibus> onibusFiltrados) {
        return (this.onibusMovimento(onibusFiltrados)*100)/this.tamFrotaFiltrado(onibusFiltrados);
    }

    public void criaLinhasFrota() {
        this.linhas.clear();

        for (Onibus onb : this.frota) {
            String linhaAtual = onb.getLinha();
            if (!this.linhas.contains(linhaAtual)) 
                this.linhas.add(linhaAtual);
        }
    }

    public ArrayList<String> linhasFrota() {
        return this.linhas;
    }

    public ArrayList<String> linhasFrotaFiltrada(FilteredList<Onibus> onibusFiltrados) {
        ArrayList<String> linhasFiltradas = new ArrayList<String>();

        for (Onibus onb : onibusFiltrados) {
            String linhaAtual = onb.getLinha();
            if (!linhasFiltradas.contains(linhaAtual)) 
                linhasFiltradas.add(linhaAtual);
        }

        return linhasFiltradas;
    }

    public int qtdOnibusLinha(String linha, FilteredList<Onibus> onibusFiltrados) {
        int contOnibusLinha = 0;

        for (Onibus onb : onibusFiltrados) {
           if (onb.getLinha().equals(linha) && Double.parseDouble(onb.getVelocidade()) > 0.0) 
               contOnibusLinha++;
        }

        return contOnibusLinha;
    }
}
