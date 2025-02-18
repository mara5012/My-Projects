package Main;
import Domain.Inchiriere;
import Domain.Masina;
import Domain.TipInchiriere;
import Domain.TipMasina;
import Repo.*;
import Service.Service;
import UI.UI;

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

        // Citire tip repository din properties
        String repositoryType = properties.getProperty("repositoryType");
        Repository<Masina> MasinaRepository;
        Repository<Inchiriere> InchiriereRepository;

        if (repositoryType == null || !(repositoryType.equals("text") || repositoryType.equals("binary") || repositoryType.equals("memory") || repositoryType.equals("database"))) {
            throw new IllegalArgumentException("Invalid repository type specified in settings.");
        }

        // Configurare repository-uri
        switch (repositoryType) {
            case "text" -> {
                String MasiniFile = properties.getProperty("MasiniFile");
                String InchirieriFile = properties.getProperty("InchirieriFile");
                MasinaRepository = new TextFileRepository<>(MasiniFile, new TipMasina());
                InchiriereRepository = new TextFileRepository<>(InchirieriFile, new TipInchiriere());
            }
            case "binary" -> {
                String MasiniFile = properties.getProperty("MasiniFile");
                String InchirieriFile = properties.getProperty("InchirieriFile");
                MasinaRepository = new BinaryRepository<>(MasiniFile);
                InchiriereRepository = new BinaryRepository<>(InchirieriFile);
            }
            case "memory" -> {
                MasinaRepository = new GenericRepository<>();
                InchiriereRepository = new GenericRepository<>();
            }
            case "database" -> {
                MasinaRepository = new SQLRepositoryMasina(); // Implementarea ta pentru SQL
                InchiriereRepository = new SQLRepositoryInchiriere(); // Implementarea ta pentru SQL

            }
            default -> throw new IllegalArgumentException("Tipul de repository nu este suportat: " + repositoryType);
        }

        // Inițializare servicii și UI
        Service service = new Service(MasinaRepository, InchiriereRepository);
        UI ui = new UI(service);

        ui.start(); // Pornește UI-ul
    }
}
