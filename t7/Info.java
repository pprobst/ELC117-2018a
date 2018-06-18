package t7;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Info {
    private API api = new API();
    private String url;

    public ArrayList<Onibus> criaFrota() {
        ArrayList<Onibus> frota = new ArrayList<Onibus>();
        Map json = this.api.muhJSON(this.url);
        List dados = (List)json.get("DATA");
        for (Object dado: dados) {
            List dado_atual = (List)dado;
            String datah = (String)dado_atual.get(0);
            String ordem = (String)dado_atual.get(1);
            String linha = (String)dado_atual.get(2);
            String latitude = (String)dado_atual.get(3);
            String longitude = (String)dado_atual.get(4);
            String velocidade = (String)dado_atual.get(5);

            Onibus onibus = new Onibus(datah, ordem, linha, latitude,
                                       longitude, velocidade);
            frota.add(onibus);    
        }

        return frota;
    }

    public void setaURL(String url) {
        this.url = url;
    }
}
