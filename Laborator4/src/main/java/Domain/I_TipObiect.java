package Domain;

public interface I_TipObiect<T extends Obiect> {
    T createObiect(String line);  // Face metoda abstractă (nu statică)
}
