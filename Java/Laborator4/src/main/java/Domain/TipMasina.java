package Domain;

public class TipMasina implements I_TipObiect<Masina> {
    @Override
    public Masina createObiect(String line) {
        // Presupunând că linia este de forma "id,marca,model"
        String[] parts = line.split(" ");
        int id = Integer.parseInt(parts[0].trim());
        String marca = parts[1].trim();
        String model = parts[2].trim();
        return new Masina(id, marca, model);
    }
}
