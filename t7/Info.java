package t7;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Info {
    private API api = new API();
    private String url;
    private ObservableList<Onibus> frota = FXCollections.observableArrayList();

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

    public int onibusParados() {
        int contPar = 0;
        for (Onibus onb : this.frota) {
            if (Double.parseDouble(onb.getVelocidade()) <= 0) contPar++;
        } 
        return contPar;
    }

    public int onibusMovimento() {
        int contMov = 0;
        for (Onibus onb : this.frota) {
            if (Double.parseDouble(onb.getVelocidade()) >= 0) contMov++; 
        }
        return contMov;
    }

    public double onibusParadosPercent() {
        return (this.onibusParados()*100)/this.tamFrota(); 
    }

    public double onibusMovimentoPercent() {
        return (this.onibusMovimento()*100)/this.tamFrota();
    }
}
