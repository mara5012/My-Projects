package Repo;

import Domain.I_TipObiect;
import Domain.Obiect;
import Exceptii.EntityAlreadyExistsException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileRepository<T extends Obiect> implements Repository<T> {

    private List<T> entities;
    private final String fileName;
    private final I_TipObiect<T> tipObiect;  // Instanță pentru crearea obiectelor

    public TextFileRepository(String fileName, I_TipObiect<T> tipObiect) {
        this.fileName = fileName;
        this.tipObiect = tipObiect;  // Setează instanța de tip obiect
        this.entities = loadFromFile();  // Încarcă datele din fișierul text la crearea repository-ului
    }

    @Override
    public void add(T entity) {
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

    // Salvează entitățile într-un fișier text
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : entities) {
                writer.write(formatEntity(entity));  // Formatează entitatea ca un text
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Încarcă entitățile din fișierul text
    private List<T> loadFromFile() {
        List<T> entities = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                T entity = tipObiect.createObiect(line);  // Creează entitatea folosind tipObiect
                entities.add(entity);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
        return entities;
    }

    // Formatează o entitate într-un șir de caractere pentru salvare
    private String formatEntity(T entity) {
        return entity.getId() + "," + entity.toString();  // Personalizează în funcție de entitate
    }

    private boolean doesIdExist(int id) {
        for (T entity : entities) {
            if (entity.getId() == id) {
                return true;  // ID-ul există deja
            }
        }
        return false;  // ID-ul nu există
    }
}
