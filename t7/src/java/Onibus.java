package t7;

import javafx.beans.property.SimpleStringProperty;

/* 
 * Classe Model que armazena e manipula as informações sobre um ônibus da frota da 
 * cidade do Rio de Janeiro. 
 *
 */
public class Onibus {
    private final SimpleStringProperty datah;
    private final SimpleStringProperty ordem;
    private final SimpleStringProperty linha;
    private final SimpleStringProperty latitude;
    private final SimpleStringProperty longitude;
    private final SimpleStringProperty velocidade;
    private final SimpleStringProperty comentario;

    public Onibus(String datah, String ordem, String linha, String latitude, 
                  String longitude, String velocidade, String comentario) {
        this.datah = new SimpleStringProperty(datah);
        this.ordem = new SimpleStringProperty(ordem);
        if (linha.equals("")) this.linha = new SimpleStringProperty("Sem linha");
        else this.linha = new SimpleStringProperty(linha);
        this.latitude = new SimpleStringProperty(latitude);
        this.longitude = new SimpleStringProperty(longitude);
        this.velocidade = new SimpleStringProperty(velocidade);
        this.comentario = new SimpleStringProperty(comentario);
    }

    public SimpleStringProperty datahProperty() {
        return this.datah;
    }

    public String getDatah() {
        return this.datah.get();
    }

    public void setDatah(String datah) {
        this.datah.set(datah);
    }

    public SimpleStringProperty ordemProperty() {
        return this.ordem;
    }

    public String getOrdem() {
        return this.ordem.get();
    }

    public void setOrdem(String ordem) {
        this.ordem.set(ordem);
    }

    public SimpleStringProperty linhaProperty() {
        return this.linha;
    }

    public String getLinha() {
        return this.linha.get();
    }

    public void setLinha(String linha) {
        this.linha.set(linha);
    }

    public SimpleStringProperty latitudeProperty() {
        return this.latitude;
    }

    public String getLatitude() {
        return this.latitude.get();
    }

    public void setLatitude(String latitude) {
        this.latitude.set(latitude);
    }

    public SimpleStringProperty longitudeProperty() {
        return this.longitude;
    }

    public String getLongitude() {
        return this.longitude.get();
    }

    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
    }

    public SimpleStringProperty velocidadeProperty() {
        return velocidade;
    }

    public String getVelocidade() {
        return this.velocidade.get();
    }

    public void setVelocidade(String velocidade) {
        this.velocidade.set(velocidade);
    }

    public SimpleStringProperty comentarioProperty() {
        return comentario;
    }

    public String getComentario() {
        return this.comentario.get();
    }

    public void setComentario(String comentario) {
        this.comentario.set(comentario);
    }
}
