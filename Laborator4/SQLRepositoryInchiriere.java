package Repo;

import Domain.Inchiriere;
import Domain.Masina;
import Exceptii.EntityAlreadyExistsException;
import Exceptii.EntityNotFoundException;
import com.github.javafaker.Faker;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SQLRepositoryInchiriere extends GenericRepository<Inchiriere> {

    private static final String URL = "jdbc:sqlite:Resources/BazaDeDate.db";  // Calea către baza de date
    private Connection conn = null;
    private static final Faker faker = new Faker(); // Instanță Java Faker pentru generarea datelor
    private static final Random random = new Random();

    public SQLRepositoryInchiriere() throws SQLException {
        openConnection();
        createTable();
        loadData();
        populateDatabase();
    }

    public void populateDatabase() throws SQLException {
        // Începem cu o tranzacție pentru a adăuga toate entitățile într-o singură comandă
        conn.setAutoCommit(false); // Dezactivăm auto-commit-ul pentru a începe o tranzacție

        String sql = "INSERT INTO inchirieri (marca, model) VALUES (?, ?)"; // Fără coloana id

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Generăm și inserăm 100 de mașini
            for (int i = 0; i < 100; i++) {
                String marca = faker.company().name(); // Generăm o marcă aleatorie
                String model = faker.commerce().productName(); // Generăm un model aleatoriu

                // Setăm parametrii pentru insert
                preparedStatement.setString(1, marca);
                preparedStatement.setString(2, model);

                // Adăugăm comanda la batch
                preparedStatement.addBatch();
            }

            // Executăm batch-ul de inserări
            preparedStatement.executeBatch();

            // Commit tranzacția
            conn.commit();
        } catch (SQLException e) {
            // Dacă apare o eroare, facem rollback
            try {
                conn.rollback();  // Anulează tranzacția în caz de eroare
            } catch (SQLException rollbackException) {
                System.err.println("Eroare la rollback: " + rollbackException.getMessage());
            }
            throw e;  // Re-aruncă excepția originală
        } finally {
            // Restaurăm auto-commit-ul la valoarea implicită
            conn.setAutoCommit(true);
        }
    }
    
    // Deschide conexiunea la baza de date
    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(URL); // Setează URL-ul bazei de date
            conn = ds.getConnection();
            if (conn != null) {
                System.out.println("Conexiune la baza de date realizată cu succes!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la conectarea la baza de date", e);
        }
    }

    // Creează tabela Inchirieri dacă nu există
    private void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Inchirieri (" +
                    "id INTEGER PRIMARY KEY, " +
                    "id_masina INTEGER, " +
                    "marca varchar(100), " +
                    "model  varchar(100), " +
                    "data_inceput DATE, " +
                    "data_sfarsit DATE, " +
                    "FOREIGN KEY (id_masina) REFERENCES Masini(id));");  // Cheie străină către Masini
        } catch (SQLException e) {
            System.err.println("[ERROR] createTable (Inchirieri): " + e.getMessage());
        }
    }

    // Încarcă datele din baza de date în mapă
    private void loadData() {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Inchirieri");
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int idInchiriere = rs.getInt("id");
                Date dataInchiriere = rs.getDate("data_inceput");
                Date dataReturnare = rs.getDate("data_sfarsit");
                int idMasina = rs.getInt("id_masina");

                Masina masina = findMasinaById(idMasina);  // Căutăm mașina asociată

                if (masina != null) {
                    Inchiriere inchiriere = new Inchiriere(idInchiriere, masina, dataInchiriere, dataReturnare);
                    baza_de_date.put(inchiriere.getId(), inchiriere);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Masina findMasinaById(int idMasina) {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Masini WHERE id = ?")) {
            statement.setInt(1, idMasina);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Masina(rs.getInt("id"), rs.getString("marca"), rs.getString("model"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Dacă nu există mașina
    }

    // Adaugă un obiect Inchiriere în baza de date
    @Override
    public void add(Inchiriere entity) throws EntityAlreadyExistsException {
        validateIdUnique(entity.getId());  // Verifică dacă ID-ul este unic

        super.add(entity);  // Adaugă în mapa de date

        // Inserare în baza de date
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Inchirieri (id, id_masina, marca, model, data_inceput, data_sfarsit) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getMasina().getId());
            statement.setString(3, entity.getMasina().getMarca());
            statement.setString(4, entity.getMasina().getModel());
            statement.setDate(5, entity.getData_inceput());
            statement.setDate(6, entity.getData_sfarsit());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la salvarea în baza de date", e);
        }
    }

    // Verifică dacă ID-ul este unic
    private void validateIdUnique(int id) throws EntityAlreadyExistsException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM Inchirieri WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new EntityAlreadyExistsException(id);  // Aruncă excepție dacă ID-ul există deja
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la validarea ID-ului.", e);
        }
    }

    // Căutare după ID
    @Override
    public Inchiriere findID(int id) throws EntityNotFoundException {
        Inchiriere inchiriere = baza_de_date.get(id);
        if (inchiriere == null) {
            throw new EntityNotFoundException(id);  // Aruncă excepție dacă nu găsește obiectul
        }
        return inchiriere;
    }

    // Returnează toate închirierile
    @Override
    public List<Inchiriere> findAll() {
        return new ArrayList<>(baza_de_date.values());
    }

    // Șterge un obiect Inchiriere din baza de date
    @Override
    public void delete(int id) throws EntityNotFoundException {
        if (!baza_de_date.containsKey(id)) {
            throw new EntityNotFoundException(id);  // Aruncă excepție dacă ID-ul nu există
        }

        super.delete(id);  // Șterge obiectul din mapa de date

        // Șterge din baza de date
        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Inchirieri WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la ștergerea din baza de date", e);
        }
    }
}
