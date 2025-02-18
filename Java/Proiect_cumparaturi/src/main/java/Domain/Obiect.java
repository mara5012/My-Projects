package Domain;

import java.io.Serializable;

public abstract class Obiect implements Serializable {
    private int id;

    public Obiect(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String toString(){
        return  " "+id;
    }
}
