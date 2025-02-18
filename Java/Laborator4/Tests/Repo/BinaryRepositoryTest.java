//package Repo;
//
//import Domain.Masina;
//import Domain.Inchiriere;
//import Exceptii.EntityAlreadyExistsException;
//import Exceptii.EntityNotFoundException;
//import Repo.BinaryRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BinaryRepositoryTest {
//
//    private BinaryRepository<Masina> masinaRepository;
//    private BinaryRepository<Inchiriere> inchiriereRepository;
//
//    @BeforeEach
//    public void setUp() {
//        // Verifică că directorul 'Resources' există și creează-l dacă nu există
//        File directory = new File("Resources");
//        if (!directory.exists()) {
//            directory.mkdir();  // Creează directorul dacă nu există
//        }
//
//        // Șterge fișierele vechi, dacă există, pentru a evita conflictele
//        File masinaFile = new File("Resources/masini.bin");
//        if (masinaFile.exists()) {
//            masinaFile.delete();
//        }
//        File inchiriereFile = new File("Resources/inchirieri.bin");
//        if (inchiriereFile.exists()) {
//            inchiriereFile.delete();
//        }
//
//        // Inițializează repository-urile pentru masini și inchirieri
//        masinaRepository = new BinaryRepository<>("Resources/masini.bin");
//        inchiriereRepository = new BinaryRepository<>("Resources/inchirieri.bin");
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Șterge fișierele pentru a curăța după teste
//        new File("Resources/masini.bin").delete();
//        new File("Resources/inchirieri.bin").delete();
//    }
//
//    // Test pentru adăugarea unei mașini și persistarea acesteia
////    @Test
////    public void testAddMasinaAndPersistence() {
////        Masina masina = new Masina(1, "Dacia", "Logan");
////        masinaRepository.add(masina);  // Adaugă mașina
////
////        // Verifică că fișierul conține mașina adăugată
////        BinaryRepository<Masina> newRepository = new BinaryRepository<>("Resources/masini.bin");
////
////        // Așteaptă câteva momente pentru a te asigura că fișierul a fost salvat
////        try {
////            Thread.sleep(500);  // Pauză de 500ms pentru a permite fișierului să se actualizeze
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////
////        // Verifică dacă fișierul conține corect mașina adăugată
////        assertEquals(1, newRepository.findAll().size(), "Numărul de mașini din fișier nu este corect.");
////        assertEquals(masina, newRepository.findID(1), "Mașina salvată nu corespunde cu cea adăugată.");
////    }
//
//    // Test pentru adăugarea unei mașini cu ID duplicat
//    @Test
//    public void testAddDuplicateMasina() {
//        Masina masina1 = new Masina(1, "Dacia", "Logan");
//        Masina masina2 = new Masina(1, "Renault", "Clio");
//
//        masinaRepository.add(masina1);
//        assertThrows(EntityAlreadyExistsException.class, () -> masinaRepository.add(masina2));
//    }
//
//    // Test pentru găsirea tuturor mașinilor
//    @Test
//    public void testFindAllMasini() {
//        Masina masina1 = new Masina(1, "Dacia", "Logan");
//        Masina masina2 = new Masina(2, "Renault", "Clio");
//
//        masinaRepository.add(masina1);
//        masinaRepository.add(masina2);
//
//        List<Masina> allMasini = masinaRepository.findAll();
//        assertEquals(2, allMasini.size());
//        assertTrue(allMasini.contains(masina1));
//        assertTrue(allMasini.contains(masina2));
//    }
//
//    // Test pentru găsirea unei mașini după ID
//    @Test
//    public void testFindMasinaByID() {
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        masinaRepository.add(masina);
//
//        Masina foundMasina = masinaRepository.findID(1);
//        assertEquals(masina, foundMasina);
//    }
//
//    // Test pentru găsirea unei mașini inexistente
//    @Test
//    public void testFindNonExistentMasina() {
//        assertNull(masinaRepository.findID(999));
//    }
//
////    // Test pentru actualizarea unei mașini
////    @Test
////    public void testUpdateMasina() {
////        Masina masina = new Masina(1, "Dacia", "Logan");
////        masinaRepository.add(masina);
////
////        Masina updatedMasina = new Masina(1, "Dacia", "Duster");
////        masinaRepository.update(updatedMasina);
////
////        BinaryRepository<Masina> newRepository = new BinaryRepository<>("Resources/masini.bin");
////        assertEquals("Duster", newRepository.findID(1).model());
////    }
//
//    // Test pentru ștergerea unei mașini
//    @Test
//    public void testDeleteMasina() {
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        masinaRepository.add(masina);
//        masinaRepository.delete(1);
//
//        BinaryRepository<Masina> newRepository = new BinaryRepository<>("Resources/masini.bin");
//        assertNull(newRepository.findID(1));
//    }
//
//    // Test pentru ștergerea unei mașini inexistente
//    @Test
//    public void testDeleteNonExistentMasina() {
//        assertDoesNotThrow(() -> masinaRepository.delete(999));
//    }
//
//    // Test pentru adăugarea și recuperarea unei închirieri
////    @Test
////    public void testAddAndRetrieveInchiriere() {
////        Masina masina = new Masina(1, "Dacia", "Logan");
////        LocalDate startDate = LocalDate.of(2023, 1, 1);
////        LocalDate endDate = LocalDate.of(2023, 1, 10);
////        Inchiriere inchiriere = new Inchiriere(1, masina, startDate, endDate);
////
////        inchiriereRepository.add(inchiriere);
////        BinaryRepository<Inchiriere> newRepository = new BinaryRepository<>("Resources/inchirieri.bin");
////        assertEquals(inchiriere, newRepository.findID(1));
////    }
//
//    // Test pentru găsirea tuturor închirierilor
//    @Test
//    public void testFindAllInchirieri() {
//        Masina masina1 = new Masina(1, "Dacia", "Logan");
//        Masina masina2 = new Masina(2, "Renault", "Clio");
//        Inchiriere inchiriere1 = new Inchiriere(1, masina1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10));
//        Inchiriere inchiriere2 = new Inchiriere(2, masina2, LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 10));
//
//        inchiriereRepository.add(inchiriere1);
//        inchiriereRepository.add(inchiriere2);
//
//        List<Inchiriere> allInchirieri = inchiriereRepository.findAll();
//        assertEquals(2, allInchirieri.size());
//        assertTrue(allInchirieri.contains(inchiriere1));
//        assertTrue(allInchirieri.contains(inchiriere2));
//    }
//
//    // Test pentru actualizarea unei închirieri
////    @Test
////    public void testUpdateInchiriere() {
////        Masina masina = new Masina(1, "Dacia", "Logan");
////        Inchiriere inchiriere = new Inchiriere(1, masina, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10));
////        inchiriereRepository.add(inchiriere);
////
////        Inchiriere updatedInchiriere = new Inchiriere(1, masina, LocalDate.of(2023, 1, 5), LocalDate.of(2023, 1, 15));
////        inchiriereRepository.update(updatedInchiriere);
////
////        BinaryRepository<Inchiriere> newRepository = new BinaryRepository<>("Resources/inchirieri.bin");
////        assertEquals(LocalDate.of(2023, 1, 5), newRepository.findID(1).getData_inceput());
////        assertEquals(LocalDate.of(2023, 1, 15), newRepository.findID(1).getData_sfarsit());
////    }
//
//    // Test pentru ștergerea unei închirieri
//    @Test
//    public void testDeleteInchiriere() {
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        Inchiriere inchiriere = new Inchiriere(1, masina, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10));
//        inchiriereRepository.add(inchiriere);
//        inchiriereRepository.delete(1);
//
//        BinaryRepository<Inchiriere> newRepository = new BinaryRepository<>("Resources/inchirieri.bin");
//        assertNull(newRepository.findID(1));
//    }
//}
