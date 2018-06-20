package t7;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/*
 * Classe de Model que representa a frota de ônibus e os diversos métodos para manipulá-la.
 * Os métodos podem manipular listas com frotas total ou parcial (FilteredList), sendo que
 * esta última toma como base os dados que estão à vista na tabela.
 *
 */
public class Frota {
    private API api = new API();
    private String url;
    private ObservableList<Onibus> frota = FXCollections.observableArrayList();
    private ArrayList<String> linhas = new ArrayList<String>();
    private DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public Frota(String url) {
        this.url = url;
    }

    // Cria a frota a partir do uso da API web
    public void criaFrota() {
        this.frota.clear();
        Map json = this.api.muhJSON(this.url);
        List dados = (List)json.get("DATA");

        this.adicionaDadosNaFrota(dados);
    }

    // Cria a frota a partir de um arquivo
    public void criaFrotaArq(File arq) {
        this.frota.clear();
        String conteudo = "";

        try {
            conteudo = new String(Files.readAllBytes(Paths.get(arq.getAbsolutePath())));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        Map json = this.api.stringJSON(conteudo);
        List dados = (List)json.get("DATA");

        this.adicionaDadosNaFrota(dados);
    }

    // Pega os dados de uma lista (gerada por criaFrota ou criaFrotaArq) e os adiciona
    // na frota.
    public void adicionaDadosNaFrota(List dados) {
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

    // Retorna a lista da Frota total
    public ObservableList<Onibus> listaFrota() {
        return this.frota;
    }

    // Seta a URL dos dados JSON a serem utilizados
    public void setaURL(String url) {
        this.url = url;
    }

    // Retorna o tamanho da frota total
    public int tamFrota() {
        return this.frota.size();
    }

     // Adiciona as linhas existentes na lista de linhas
    public void criaLinhasFrota() {
        this.linhas.clear();

        for (Onibus onb : this.frota) {
            String linhaAtual = onb.getLinha();
            if (!this.linhas.contains(linhaAtual)) 
                this.linhas.add(linhaAtual);
        }
    }

    // Retorna a lista de linhas da frota total
    public ArrayList<String> linhasFrota() {
        return this.linhas;
    }

    // Retorna o tamanho da frota filtrada
    public int tamFrotaFiltrado(FilteredList<Onibus> onibusFiltrados) {
        return onibusFiltrados.size();
    }

    // Retorna o número de veículos parados da frota filtrada
    public int onibusParados(FilteredList<Onibus> onibusFiltrados) {
        int contPar = 0;

        for (Onibus onb : onibusFiltrados) {
            if (Double.parseDouble(onb.getVelocidade()) <= 0) contPar++;
        }

        return contPar;
    }

    // Retorna o número de veículos em movimento da frota filtrada
    public int onibusMovimento(FilteredList<Onibus> onibusFiltrados) {
        int contMov = 0;

        for (Onibus onb : onibusFiltrados) {
            if (Double.parseDouble(onb.getVelocidade()) >= 0) contMov++; 
        }

        return contMov;
    }

    // Retorna o percentual de veículos parados da frota filtrada
    public double onibusParadosPercent(FilteredList<Onibus> onibusFiltrados) {
        return (this.onibusParados(onibusFiltrados)*100)/this.tamFrotaFiltrado(onibusFiltrados); 
    }

    // Retorna o percentual de veículos em movimento da frota filtrada
    public double onibusMovimentoPercent(FilteredList<Onibus> onibusFiltrados) {
        return (this.onibusMovimento(onibusFiltrados)*100)/this.tamFrotaFiltrado(onibusFiltrados);
    }

    // Retorna a lista de linhas da a frota filtrada
    public ArrayList<String> linhasFrotaFiltrada(FilteredList<Onibus> onibusFiltrados) {
        ArrayList<String> linhasFiltradas = new ArrayList<String>();

        for (Onibus onb : onibusFiltrados) {
            String linhaAtual = onb.getLinha();
            if (!linhasFiltradas.contains(linhaAtual)) 
                linhasFiltradas.add(linhaAtual);
        }

        return linhasFiltradas;
    }

    // Retorna a quantidade de veículos existentes em uma linha da frota filtrada
    public int qtdOnibusLinha(String linha, FilteredList<Onibus> onibusFiltrados) {
        int contOnibusLinha = 0;

        for (Onibus onb : onibusFiltrados) {
           if (onb.getLinha().equals(linha) && Double.parseDouble(onb.getVelocidade()) > 0.0) 
               contOnibusLinha++;
        }

        return contOnibusLinha;
    }

    // Retorna a data/hora da última atualização que o usuário realizou
    public String serverUltimaLeitura() {
        Date dataAtual = new Date();
        String ultimaLeitura = this.dateFormat.format(dataAtual);
        return ultimaLeitura;
    }

    // Retorna a data/hora mais recente lida do servidor
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

    // Retorna a data/hora menos recente lida do servidor
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

    // Faz o parsing de duas datas e verifica se a primeira é anterior à segunda;
    // Retorna true se for anterior, false caso contrário
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
}
