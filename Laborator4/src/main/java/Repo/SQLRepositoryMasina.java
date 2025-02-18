package Repo;

import Domain.Masina;
import Exceptii.EntityAlreadyExistsException;
import Exceptii.EntityNotFoundException;
import com.github.javafaker.Faker;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.*;

public class SQLRepositoryMasina extends GenericRepository<Masina> {

    private static final String URL = "jdbc:sqlite:Resources/BazaDeDate.db";  // Calea către baza de date
    private Connection conn = null;
    private static final Faker faker = new Faker();
    private static final Random random = new Random();


    public SQLRepositoryMasina() throws SQLException {
        openConnection();
        createTable();
        loadData();
    }

    // Deschide conexiunea la baza de date
    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(URL);
            conn = ds.getConnection();
            if (conn != null) {
                System.out.println("Conexiune la baza de date realizată cu succes!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la conectarea la baza de date", e);
        }
    }

    // Creează tabela Masini dacă nu există
    private void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Masini (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "marca  VARCHAR(100) NOT NULL, " +
                    "model  VARCHAR(100) NOT NULL);");
        } catch (SQLException e) {
            System.err.println("[ERROR] createTable (Masini): " + e.getMessage());
        }
    }

    // Încarcă datele din baza de date în cache-ul local
    private void loadData() {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Masini");
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Masina masina = new Masina(rs.getInt("id"), rs.getString("marca"), rs.getString("model"));
                baza_de_date.put(masina.getId(), masina);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Metodă pentru popularea bazei de date
    public void populateDatabase() throws SQLException {
        conn.setAutoCommit(false);

        String sql = "INSERT INTO Masini (marca, model) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            for (int i = 0; i < 100; i++) {
                String marca = faker.company().name();
                String model = faker.commerce().productName();

                preparedStatement.setString(1, marca);
                preparedStatement.setString(2, model);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void add(Masina entity) throws EntityAlreadyExistsException {
        validateEntity(entity);

        validateIdUnique(entity.getId());
        super.add(entity);

        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Masini (id, marca, model) VALUES (?, ?, ?)")) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getMarca());
            statement.setString(3, entity.getModel());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la salvarea în baza de date", e);
        }

        baza_de_date.put(entity.getId(), entity); // Actualizare cache
    }

    @Override
    public void update(Masina entity) throws EntityNotFoundException {
        validateEntity(entity);

        if (!baza_de_date.containsKey(entity.getId())) {
            throw new EntityNotFoundException(entity.getId());
        }

        super.update(entity);

        try (PreparedStatement statement = conn.prepareStatement("UPDATE Masini SET marca = ?, model = ? WHERE id = ?")) {
            statement.setString(1, entity.getMarca());
            statement.setString(2, entity.getModel());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la actualizarea în baza de date", e);
        }

        baza_de_date.put(entity.getId(), entity); // Actualizare cache
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        if (!baza_de_date.containsKey(id)) {
            throw new EntityNotFoundException(id);
        }

        super.delete(id);

        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Masini WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la ștergerea din baza de date", e);
        }

        baza_de_date.remove(id); // Actualizare cache
    }

    @Override
    public Masina findID(int id) throws EntityNotFoundException {
        Masina masina = baza_de_date.get(id);
        if (masina == null) {
            throw new EntityNotFoundException(id);
        }
        return masina;
    }

    @Override
    public List<Masina> findAll() {
        return new ArrayList<>(baza_de_date.values());
    }

    private void validateEntity(Masina masina) {
        if (masina.getMarca() == null || masina.getMarca().isEmpty()) {
            throw new IllegalArgumentException("Marca nu poate fi null sau goală.");
        }
        if (masina.getModel() == null || masina.getModel().isEmpty()) {
            throw new IllegalArgumentException("Modelul nu poate fi null sau gol.");
        }
    }

    private void validateIdUnique(int id) throws EntityAlreadyExistsException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM Masini WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new EntityAlreadyExistsException(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la validarea ID-ului.", e);
        }
    }
}
