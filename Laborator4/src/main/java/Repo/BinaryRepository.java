package Repo;

import Domain.Obiect;
import Exceptii.EntityAlreadyExistsException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryRepository<T extends Obiect> implements Repository<T> {

    private List<T> entities;
    private final String fileName;

    public BinaryRepository(String fileName) {
        this.fileName = fileName;

        // Verifică dacă fișierul există deja; dacă nu, creează unul la prima salvare
        File file = new File(fileName);
        if (file.exists()) {
            this.entities = loadFromFile();  // Încarcă datele din fișierul binar existent
        } else {
            this.entities = new ArrayList<>();  // Începe cu o listă goală
        }
    }


    @Override
    public void add(T entity) {
        // Verifică dacă ID-ul entității există deja
        if (doesIdExist(entity.getId())) {
            throw new EntityAlreadyExistsException(entity.getId());
        }

        entities.add(entity);
        saveToFile();  // Salvează entitatea în fișier
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);  // Returnează o copie a entităților
    }

    @Override
    public T findID(int id) {
        for (T entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;  // Poți arunca o excepție dacă vrei, dacă nu găsește entitatea
    }

    @Override
    public void update(T entity) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId()) {
                entities.set(i, entity);  // Actualizează entitatea existentă
                saveToFile();  // Salvează modificările în fișier
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        entities.removeIf(entity -> entity.getId() == id);
        saveToFile();  // Salvează modificările în fișier
    }

    // Salvează entitățile într-un fișier binar
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(entities);  // Scrie lista de entități în fișier
        } catch (IOException e) {
            throw new RuntimeException("Error saving entities", e);
        }
    }

    // Încarcă entitățile din fișierul binar
    private List<T> loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();  // Citește obiectele din fișier
        } catch (FileNotFoundException e) {
            System.out.println("Fișierul nu există. Se va crea la prima salvare.");
            return new ArrayList<>();  // Dacă fișierul nu există, începe cu o listă goală
        } catch (IOException e) {
            System.out.println("Eroare la citirea din fișier. Verificați integritatea fișierului: " + fileName);
            e.printStackTrace();
            return new ArrayList<>();  // Dacă apare o eroare la încărcare, returnează o listă goală
        } catch (ClassNotFoundException e) {
            System.out.println("Tipul obiectelor din fișier nu este compatibil.");
            e.printStackTrace();
            return new ArrayList<>();  // Dacă apare o eroare la încărcare, returnează o listă goală
        } catch (ClassCastException e) {
            System.out.println("Eroare la conversia obiectelor. Fișierul poate fi corupt.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    // Verifică dacă un ID există deja în lista de entități
    private boolean doesIdExist(int id) {
        for (T entity : entities) {
            if (entity.getId() == id) {
                return true;  // ID-ul există deja
            }
        }
        return false;  // ID-ul nu există
    }

    // Poți crea o metodă care să genereze un ID unic
    public int generateUniqueId() {
        int maxId = 0;
        for (T entity : entities) {
            if (entity.getId() > maxId) {
                maxId = entity.getId();
            }
        }
        return maxId + 1;  // Crește ID-ul maxim existent
    }
}
