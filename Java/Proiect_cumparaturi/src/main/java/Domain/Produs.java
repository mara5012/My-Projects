package Domain;

import java.io.Serializable;

public class Produs extends Obiect implements Serializable {
    String marca;
    String nume;
    int pret;
    int cantitate;

    public Produs(int id, String marca, String nume, int pret, int cantitate) {
        super(id);
        this.marca = marca;
        this.pret = pret;
        this.nume = nume;
        this.cantitate = cantitate;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return super.toString() + " " + marca + " " + nume + " " + pret + " " + cantitate;
    }
}
