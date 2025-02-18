package UI;

import Domain.Inchiriere;
import Domain.Masina;
import Service.Service;

import java.sql.Date;
import java.util.Scanner;

public class UI {
    private final Service service;
    private final Scanner scanner;

    public UI(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Meniu Principal ===");
            System.out.println("1. Gestionare Masini");
            System.out.println("2. Gestionare Inchirieri");
            System.out.println("3. Numarul de inchirieri pentru fiecare masina");
            System.out.println("4. Afiseaza numarul de inchirieri pe fiecare luna");
            System.out.println("5. Afiseaza numarul de zile inchiriate pentru fiecare masina");
            System.out.println("0. Iesire");

            int optiune = readInt("Alege o optiune: ");
            switch (optiune) {
                case 1 -> gestioneazaMasini();
                case 2 -> gestioneazaInchirieri();
                case 3 -> nrInchirieriMasini();
                case 4 -> nrInchirieriLuna();
                case 5 -> nrZileInchiriate();
                case 0 -> {
                    System.out.println("EXIT");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void gestioneazaMasini() {
        while (true) {
            System.out.println("\n=== Gestionare Masini ===");
            System.out.println("1. Adauga masina");
            System.out.println("2. Actualizeaza masina");
            System.out.println("3. Sterge masina");
            System.out.println("4. Afiseaza toate masinile");
            System.out.println("5. Cauta masina dupa ID");
            System.out.println("0. Inapoi");

            int optiune = readInt("Alege o optiune: ");
            switch (optiune) {
                case 1 -> adaugaMasina();
                case 2 -> actualizeazaMasina();
                case 3 -> stergeMasina();
                case 4 -> afiseazaMasini();
                case 5 -> cautaMasina();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void adaugaMasina() {
        int id = readInt("ID Masina: ");
        String marca = readString("Marca: ");
        String model = readString("Model: ");

        Masina masina = new Masina(id, marca, model);
        service.addMasina(masina);
        System.out.println("Masina a fost adaugata cu succes!");
    }

    private void actualizeazaMasina() {
        int id = readInt("ID masina de actualizat: ");
        String marca = readString("Noua marca: ");
        String model = readString("Noul model: ");

        Masina masina = new Masina(id, marca, model);
        service.updateMasina(masina);
        System.out.println("Masina a fost actualizata cu succes!");
    }

    private void stergeMasina() {
        int id = readInt("ID masina care urmeaza sa fie stearsa: ");
        service.deleteMasina(id);
        System.out.println("Masina a fost stearsa cu succes!");
    }

    private void afiseazaMasini() {
        System.out.println("Lista masini:");
        for (Masina masina : service.findAllMasina()) {
            System.out.println(masina);
        }
    }

    private void cautaMasina() {
        int id = readInt("ID masina de cautat: ");
        try {
            Masina masina = service.findIDMasina(id);
            System.out.println(masina);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void gestioneazaInchirieri() {
        while (true) {
            System.out.println("\n=== Gestionare Inchirieri ===");
            System.out.println("1. Adauga inchiriere");
            System.out.println("2. Actualizeaza inchiriere");
            System.out.println("3. Sterge inchiriere");
            System.out.println("4. Afiseaza toate inchirierile");
            System.out.println("5. Cauta inchiriere dupa ID");
            System.out.println("0. Inapoi");

            int optiune = readInt("Alege o optiune: ");
            switch (optiune) {
                case 1 -> adaugaInchiriere();
                case 2 -> actualizeazaInchiriere();
                case 3 -> stergeInchiriere();
                case 4 -> afiseazaInchirieri();
                case 5 -> cautaInchiriere();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void adaugaInchiriere() {
        int id = readInt("ID Inchiriere: ");
        int masinaId = readInt("ID Masina: ");
        Date dataInceput = Date.valueOf(readString("Data inceput (YYYY-MM-DD): "));
        Date dataSfarsit = Date.valueOf(readString("Data sfarsit (YYYY-MM-DD): "));

        Masina masina = service.findIDMasina(masinaId);
        if (masina == null) {
            System.out.println("Masina cu ID-ul specificat nu exista.");
            return;
        }

        // Verificare daca masina este deja inchiriata in intervalul respectiv
        for (Inchiriere in : service.findAllInchiriere()) {
            if (in.getMasina().getId() == masinaId) {
                // Verificare suprapunere
                if (!(dataSfarsit.before(in.getData_inceput()) || dataInceput.after(in.getData_sfarsit()))) {
                    System.out.println("Masina este deja inchiriata in perioada selectata.");
                    return;
                }
            }
        }

        // Adaugarea inchirierii
        Inchiriere inchiriere = new Inchiriere(id, masina, dataInceput, dataSfarsit);
        service.addInchiriere(inchiriere);
        System.out.println("Inchirierea a fost adaugata.");
    }

    private void actualizeazaInchiriere() {
        int id = readInt("ID Inchiriere de actualizat: ");
        int masinaId = readInt("ID Masina: ");
        Date dataInceput = Date.valueOf(readString("Noua data de inceput (YYYY-MM-DD): "));
        Date dataSfarsit = Date.valueOf(readString("Noua data de sfarsit (YYYY-MM-DD): "));

        Masina masina = service.findIDMasina(masinaId);
        Inchiriere inchiriere = new Inchiriere(id, masina, dataInceput, dataSfarsit);
        service.updateInchiriere(inchiriere);
        System.out.println("Inchirierea a fost actualizata.");
    }

    private void stergeInchiriere() {
        int id = readInt("ID Inchiriere de sters: ");
        service.deleteInchiriere(id);
        System.out.println("Inchirierea a fost stearsa.");
    }

    private void afiseazaInchirieri() {
        System.out.println("Lista Inchirieri:");
        for (Inchiriere inchiriere : service.findAllInchiriere()) {
            System.out.println(inchiriere);
        }
        if (service.findAllInchiriere().isEmpty()) {
            System.out.println("Nu exista inchirieri de afisat.");
        }
    }

    private void cautaInchiriere() {
        int id = readInt("ID Inchiriere de cautat: ");
        try {
            Inchiriere inchiriere = service.findIDInchiriere(id);
            System.out.println(inchiriere);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private int readInt(String mesaj) {
        System.out.print(mesaj);
        return Integer.parseInt(scanner.nextLine());
    }

    private String readString(String mesaj) {
        System.out.print(mesaj);
        return scanner.nextLine();
    }

    private void nrInchirieriMasini() {
        service.nrInchirieriMasini().forEach((masina, nrInchirieri) -> System.out.println(masina + ": " + nrInchirieri));
    }

    private void nrInchirieriLuna() {
        // Array cu numele lunilor
        String[] luni = {"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
                "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};

        // Iterăm prin rezultatele metodei nrInchirieriLuna() și afișăm numele lunii
        service.nrInchirieriLuna().forEach((luna, nrInchirieri) -> {
            int lunaIndex = Integer.parseInt(luna) - 1; // Convertim luna din String în index (0-based)
            String numeLuna = luni[lunaIndex]; // Obținem numele lunii
            System.out.println(numeLuna + ": " + nrInchirieri);
        });
    }

    private void nrZileInchiriate() {
        service.nrZileInchiriate().forEach((masina, nrZile) -> System.out.println(masina + ": " + nrZile));
    }
}
