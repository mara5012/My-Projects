package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Masina extends Obiect implements Serializable{
    private String marca;
    private String model;

    public Masina(int id, String marca, String model){
        super(id);
        this.marca = marca;
        this.model = model;
    }

    public String getMarca(){
        return this.marca;
    }

    public String getModel(){
        return this.model;
    }


    public void setMarca(String marca){
        this.marca = marca;
    }

    public void setModel(String model){
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Masina masina = (Masina) o;
        return this.getId() == masina.getId() && marca.equals(masina.marca) && model.equals(masina.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), marca, model);
    }


    public String toString(){
        return "" + super.toString() +  " " + marca + " " + model + "";
    }
}




