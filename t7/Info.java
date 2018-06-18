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

    public ObservableList<Onibus> criaFrota() {
        //this.frota.clear();
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
        return this.frota;
    }

    public void setaURL(String url) {
        this.url = url;
    }

    public int tamFrota() {
        return this.frota.size();
    }
}
