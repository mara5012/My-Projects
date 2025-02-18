package Main;

import Domain.Produs;
import Repo.SQLRepositoryProdus;

import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        // Încarcă fișierul settings.properties
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("settings.properties")) {
            if (input == null) {
                System.out.println("Fișierul settings.properties nu a fost găsit.");
                return;
            }
            properties.load(input);
        }

        String binaryFilePath = "produse.bin"; // Calea către fișierul binar
        SQLRepositoryProdus produsRepository = new SQLRepositoryProdus();

        // Adăugare date noi (doar dacă nu există deja)
        Produs produs = new Produs(100, "Lenovo", "ThinkPad S100", 9500, 14);
        Produs produs2 = new Produs(101, "Asus", "Strix 45", 7700, 4);
        Produs produs3 = new Produs(102, "Ariston", "WSL-1002", 2240, 2);
        Produs produs4 = new Produs(103, "Bosch", "Series 4", 1900, 11);
        Produs produs5 = new Produs(104, "Whirlpool", "SuperFridge 100LE", 3200, 10);

        // Adaugă produsele doar dacă nu există deja în baza de date
        try {
            produsRepository.add(produs);
            produsRepository.add(produs2);
            produsRepository.add(produs3);
            produsRepository.add(produs4);
            produsRepository.add(produs5);
        } catch (Exception e) {
            System.out.println("Unele produse există deja: " + e.getMessage());
        }

        // Salvează toate produsele existente în fișierul binar
        produsRepository.saveToBinaryFile(binaryFilePath);

        // La următoarea pornire, produsele vor fi încărcate automat din fișierul binar
        System.out.println("Produsele din baza de date:");
        produsRepository.findAll().forEach(System.out::println);
    }
}
