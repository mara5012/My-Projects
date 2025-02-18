package Repo;

import Domain.Produs;
import Exceptii.EntityAlreadyExistsException;
import Exceptii.EntityNotFoundException;
import org.sqlite.SQLiteDataSource;

import java.io.*;
import java.sql.*;
import java.util.*;

public class SQLRepositoryProdus extends GenericRepository<Produs> {
    private static final String URL = "jdbc:sqlite:Resources/BazaDeDate.db"; // Calea către baza de date
    private static final String BINARY_FILE = "Resources\\produse.bin"; // Calea către fișierul binar
    private Connection conn = null;

    public SQLRepositoryProdus() throws SQLException {
        openConnection();
        createTable();
        loadData(); // Încarcă datele din baza de date
        loadFromBinaryFile(BINARY_FILE); // Încarcă datele din fișierul binar
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

    // Creează tabela Produse dacă nu există
    private void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Produse (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "marca  VARCHAR(100) NOT NULL, " +
                    "nume  VARCHAR(100) NOT NULL," +
                    "pret INTEGER," +
                    "cantitate INTEGER);");
        } catch (SQLException e) {
            System.err.println("[ERROR] createTable (Produse): " + e.getMessage());
        }
    }

    // Încarcă datele din baza de date în cache-ul local
    private void loadData() {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Produse");
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Produs produs = new Produs(rs.getInt("id"), rs.getString("marca"), rs.getString("nume"), rs.getInt("pret"), rs.getInt("cantitate"));
                baza_de_date.put(produs.getId(), produs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Încarcă produsele dintr-un fișier binar
    private void loadFromBinaryFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Produs> produseDinFisier = (List<Produs>) ois.readObject();
            for (Produs produs : produseDinFisier) {
                baza_de_date.put(produs.getId(), produs); // Adăugare în cache
            }
            System.out.println("Produsele au fost încărcate din fișierul binar.");
        } catch (FileNotFoundException e) {
            System.out.println("Fișierul binar nu a fost găsit. Se va crea un fișier nou la salvare.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Eroare la încărcarea produselor din fișierul binar: " + e.getMessage());
        }
    }

    // Salvează produsele într-un fișier binar
    public void saveToBinaryFile(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(baza_de_date.values()));
            System.out.println("Produsele au fost salvate în fișierul binar.");
        } catch (IOException e) {
            System.err.println("Eroare la salvarea produselor în fișierul binar: " + e.getMessage());
        }
    }

    @Override
    public void add(Produs entity) throws EntityAlreadyExistsException {
        validateEntity(entity);
        validateIdUnique(entity.getId());
        super.add(entity);

        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Produse (id, marca, nume, pret, cantitate) VALUES (?, ?, ?, ?, ?)")) {
            statement.setInt(1, findNextId());
            statement.setString(2, entity.getMarca());
            statement.setString(3, entity.getNume());
            statement.setInt(4, entity.getPret());
            statement.setInt(5, entity.getCantitate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la salvarea în baza de date", e);
        }

        baza_de_date.put(entity.getId(), entity); // Actualizare cache
        saveToBinaryFile(BINARY_FILE); // Salvare după adăugare
    }

    @Override
    public void update(Produs entity) throws EntityNotFoundException {
        validateEntity(entity);
        if (!baza_de_date.containsKey(entity.getId())) {
            throw new EntityNotFoundException(entity.getId());
        }

        super.update(entity);

        try (PreparedStatement statement = conn.prepareStatement("UPDATE Produse SET marca = ?, nume = ?, pret = ?, cantitate = ? WHERE id = ?")) {
            statement.setString(1, entity.getMarca());
            statement.setString(2, entity.getNume());
            statement.setInt(3, entity.getPret());
            statement.setInt(4, entity.getCantitate());
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la actualizarea în baza de date", e);
        }

        baza_de_date.put(entity.getId(), entity); // Actualizare cache
        saveToBinaryFile(BINARY_FILE); // Salvare după actualizare
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        if (!baza_de_date.containsKey(id)) {
            throw new EntityNotFoundException(id);
        }

        super.delete(id);

        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Produse WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Eroare la ștergerea din baza de date", e);
        }

        baza_de_date.remove(id); // Actualizare cache
        saveToBinaryFile(BINARY_FILE); // Salvare după ștergere
    }

    @Override
    public Produs findID(int id) throws EntityNotFoundException {
        Produs produs = baza_de_date.get(id);
        if (produs == null) {
            throw new EntityNotFoundException(id);
        }
        return produs;
    }

    @Override
    public List<Produs> findAll() {
        return new ArrayList<>(baza_de_date.values());
    }

    private void validateEntity(Produs produs) {
        if (produs.getMarca() == null || produs.getMarca().isEmpty()) {
            throw new IllegalArgumentException("Marca nu poate fi null sau goală.");
        }
        if (produs.getNume() == null || produs.getNume().isEmpty()) {
            throw new IllegalArgumentException("Numele nu poate fi null sau gol.");
        }
        if (produs.getPret() < 0) {
            throw new IllegalArgumentException("Pretul nu poate fi negativ.");
        }
        if (produs.getCantitate() < 0) {
            throw new IllegalArgumentException("Cantitatea nu poate fi negativa.");
        }
    }

    private void validateIdUnique(int id) throws EntityAlreadyExistsException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM Produse WHERE id = ?")) {
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

    private int findNextId() {
        try (PreparedStatement statement = conn.prepareStatement("SELECT MAX(id) FROM Produse")) {
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1) + 1;
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la găsirea următorului ID disponibil.", e);
        }
        return 0;
    }

}
