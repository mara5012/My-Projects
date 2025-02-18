package Domain;

import java.io.Serializable;
import java.sql.Date;

public class Inchiriere extends Obiect implements Serializable{
    private Date data_inceput;
    private Date data_sfarsit;
    private Masina masina;  // elimină `static`

    public Inchiriere(int id, Masina masina, Date data_inceput, Date data_sfarsit) {
        super(id);
        this.masina = masina;
        this.data_inceput = data_inceput;
        this.data_sfarsit = data_sfarsit;
    }

    public Masina getMasina() {
        return masina;
    }

    public Date getData_inceput() {
        return data_inceput;
    }

    public Date getData_sfarsit() {
        return data_sfarsit;
    }

    public void setMasina(Masina masina) {
        this.masina = masina;
    }

    public void setData_inceput(Date data_inceput) {
        this.data_inceput = data_inceput;
    }

    public void setData_sfarsit(Date data_sfarsit) {
        this.data_sfarsit = data_sfarsit;
    }

    public String toString() {
        return "" + super.toString() + "," + masina + "," + data_inceput + "," + data_sfarsit + "";
    }
}
