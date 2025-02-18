package UI;

import Domain.Masina;
import Domain.Inchiriere;
import Repo.GenericRepository;
import Service.Service;
import Repo.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UITest {
    private UI ui;
    private Service service;

    @BeforeEach
    public void setUp() {
        // Folosim implementări în memorie pentru repository-uri
        Repository<Masina> masinaRepository = new GenericRepository<>();
        Repository<Inchiriere> inchiriereRepository = new GenericRepository<>();

        service = new Service(masinaRepository, inchiriereRepository);
        ui = new UI(service);
    }

    // Test pentru adăugarea unei mașini
//    @Test
//    public void testAdaugaMasina() {
////        String input = "1\n1\nDacia\nLogan\n0\n";  // Simulăm inputul pentru adăugarea unei mașini
////        simulateInput(input);
//
//        // Verificăm că mașina a fost adăugată corect
//        Masina masina = service.findIDMasina(1);
//        assertNotNull(masina, "Mașina nu a fost adăugată corect.");
//        assertEquals("Dacia", masina.marca(), "Marca mașinii nu este corectă.");
//        assertEquals("Logan", masina.model(), "Modelul mașinii nu este corect.");
//    }

    // Test pentru vizualizarea tuturor mașinilor
//    @Test
//    public void testAfiseazaMasini() {
//        // Adăugăm o mașină
//        service.addMasina(new Masina(1, "Dacia", "Logan"));
//
//        // Simulăm inputul pentru a vizualiza mașinile
////        String input = "4\n0\n";  // 4 este opțiunea pentru a vizualiza mașinile
////        simulateInput(input);
//
//        // Capturăm output-ul
//        String output = captureOutput(() -> ui.start());
//
//        // Verificăm dacă output-ul conține informațiile despre mașină
//        assertTrue(output.contains("Dacia Logan"), "Mașina nu apare în listă.");
//    }

    // Test pentru adăugarea unei închirieri
//    @Test
//    public void testAdaugaInchiriere() {
//        // Adăugăm o mașină pentru a o închiria
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        service.addMasina(masina);
//
//        // Simulăm inputul pentru adăugarea unei închirieri
////        String input = "1\n1\n2024-11-12\n2024-11-20\n0\n";  // 1 - adăugăm închiriere
////        simulateInput(input);
//
//        // Verificăm că închirierea a fost adăugată
//        Inchiriere inchiriere = service.findIDInchiriere(1);
//        assertNotNull(inchiriere, "Închirierea nu a fost adăugată corect.");
//        assertEquals(masina, inchiriere.getMasina(), "Mașina închirieri nu corespunde.");
//    }

    // Test pentru vizualizarea tuturor închirierilor
    @Test
    public void testAfiseazaInchirieri() {
        // Adăugăm o mașină și o închiriere
        Masina masina = new Masina(1, "Dacia", "Logan");
        service.addMasina(masina);
        service.addInchiriere(new Inchiriere(1, masina, LocalDate.now(), LocalDate.now().plusDays(7)));

        // Simulăm inputul pentru a vizualiza închirierile
//        String input = "4\n0\n";  // 4 este opțiunea pentru a vizualiza închirierile
//        simulateInput(input);

        // Capturăm output-ul
        //String output = captureOutput(() -> ui.start());

        // Verificăm dacă output-ul conține informațiile despre închiriere
        //assertTrue(output.contains("Inchirierea"), "Închirierea nu apare în listă.");
    }

    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));  // Simulăm inputul utilizatorului
    }

    private String captureOutput(Runnable runnable) {
        // Capturăm output-ul care ar fi fost trimis la consolă
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Executăm acțiunea
        runnable.run();

        // Returnăm output-ul ca un string
        return outputStream.toString();
    }
}
