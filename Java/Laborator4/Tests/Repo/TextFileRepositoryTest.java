package Repo;

import Domain.Masina;
import Domain.Inchiriere;
import Domain.I_TipObiect;
import Exceptii.EntityAlreadyExistsException;
import Repo.TextFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Implementări de test pentru `I_TipObiect`
class MasinaFactory implements I_TipObiect<Masina> {
    @Override
    public Masina createObiect(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0].trim());
        String marca = parts[1].trim();
        String model = parts[2].trim();
        return new Masina(id, marca, model);
    }
}

class InchiriereFactory implements I_TipObiect<Inchiriere> {
    @Override
    public Inchiriere createObiect(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0].trim());
        Masina masina = new Masina(Integer.parseInt(parts[1].trim()), parts[2].trim(), parts[3].trim());
        Date dataInceput = Date.valueOf(parts[4].trim());
        Date dataSfarsit = Date.valueOf(parts[5].trim());
        return new Inchiriere(id, masina, dataInceput, dataSfarsit);
    }
}

class TextFileRepositoryTest {

    private TextFileRepository<Masina> masinaRepository;
    private TextFileRepository<Inchiriere> inchiriereRepository;
    private final String masinaFileName = "masini.txt";
    private final String inchiriereFileName = "inchirieri.txt";

    @BeforeEach
    public void setUp() {
        masinaRepository = new TextFileRepository<>(masinaFileName, new MasinaFactory());
       //inchiriereRepository = new TextFileRepository<>(inchiriereFileName, new InchiriereFactory());
    }

    @AfterEach
    public void tearDown() {
        new File(masinaFileName).delete();
        new File(inchiriereFileName).delete();
    }

    // Test pentru metoda add și persistență
//    @Test
//    public void testAddMasinaAndPersistence() {
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        masinaRepository.add(masina);
//
//        TextFileRepository<Masina> newRepository = new TextFileRepository<>(masinaFileName, new MasinaFactory());
//        assertEquals(1, newRepository.findAll().size());
//        assertEquals(masina, newRepository.findID(1));
//    }

    @Test
    public void testAddDuplicateMasina() {
        Masina masina1 = new Masina(1, "Dacia", "Logan");
        Masina masina2 = new Masina(1, "Renault", "Clio");

        masinaRepository.add(masina1);
        assertThrows(EntityAlreadyExistsException.class, () -> masinaRepository.add(masina2));
    }

    @Test
    public void testFindAllMasini() {
        Masina masina1 = new Masina(1, "Dacia", "Logan");
        Masina masina2 = new Masina(2, "Renault", "Clio");

        masinaRepository.add(masina1);
        masinaRepository.add(masina2);

        List<Masina> allMasini = masinaRepository.findAll();
        assertEquals(2, allMasini.size());
        assertTrue(allMasini.contains(masina1));
        assertTrue(allMasini.contains(masina2));
    }

    @Test
    public void testFindMasinaByID() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        masinaRepository.add(masina);

        Masina foundMasina = masinaRepository.findID(1);
        assertEquals(masina, foundMasina);
    }

    @Test
    public void testFindNonExistentMasina() {
        assertNull(masinaRepository.findID(999));
    }

//    @Test
//    public void testUpdateMasina() {
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        masinaRepository.add(masina);
//
//        Masina updatedMasina = new Masina(1, "Dacia", "Duster");
//        masinaRepository.update(updatedMasina);
//
//        TextFileRepository<Masina> newRepository = new TextFileRepository<>(masinaFileName, new MasinaFactory());
//        assertEquals("Duster", newRepository.findID(1).model());
//    }

    @Test
    public void testDeleteMasina() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        masinaRepository.add(masina);
        masinaRepository.delete(1);

        TextFileRepository<Masina> newRepository = new TextFileRepository<>(masinaFileName, new MasinaFactory());
        assertNull(newRepository.findID(1));
    }

    @Test
    public void testDeleteNonExistentMasina() {
        assertDoesNotThrow(() -> masinaRepository.delete(999));
    }

    // Teste pentru Inchiriere
//    @Test
//    public void testAddAndRetrieveInchiriere() {
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        LocalDate startDate = LocalDate.of(2023, 1, 1);
//        LocalDate endDate = LocalDate.of(2023, 1, 10);
//        Inchiriere inchiriere = new Inchiriere(1, masina, startDate, endDate);
//
//        inchiriereRepository.add(inchiriere);
//        TextFileRepository<Inchiriere> newRepository = new TextFileRepository<>(inchiriereFileName, new InchiriereFactory());
//        assertEquals(inchiriere, newRepository.findID(1));
//    }

    @Test
    public void testFindAllInchirieri() {
        Masina masina1 = new Masina(1, "Dacia", "Logan");
        Masina masina2 = new Masina(2, "Renault", "Clio");
        Inchiriere inchiriere1 = new Inchiriere(1, masina1, Date.valueOf("2023, 1, 1"), Date.valueOf("2023, 1, 10"));
        Inchiriere inchiriere2 = new Inchiriere(2, masina2, Date.valueOf("2023, 2, 1"), Date.valueOf("2023, 2, 10"));

        inchiriereRepository.add(inchiriere1);
        inchiriereRepository.add(inchiriere2);

        List<Inchiriere> allInchirieri = inchiriereRepository.findAll();
        assertEquals(2, allInchirieri.size());
        assertTrue(allInchirieri.contains(inchiriere1));
        assertTrue(allInchirieri.contains(inchiriere2));
    }

//    @Test
//    public void testUpdateInchiriere() {
//        Masina masina = new Masina(1, "Dacia", "Logan");
//        Inchiriere inchiriere = new Inchiriere(1, masina, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10));
//        inchiriereRepository.add(inchiriere);
//
//        Inchiriere updatedInchiriere = new Inchiriere(1, masina, LocalDate.of(2023, 1, 5), LocalDate.of(2023, 1, 15));
//        inchiriereRepository.update(updatedInchiriere);
//
//        TextFileRepository<Inchiriere> newRepository = new TextFileRepository<>(inchiriereFileName, new InchiriereFactory());
//        assertEquals(LocalDate.of(2023, 1, 5), newRepository.findID(1).getData_inceput());
//        assertEquals(LocalDate.of(2023, 1, 15), newRepository.findID(1).getData_sfarsit());
//    }

    @Test
    public void testDeleteInchiriere() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Inchiriere inchiriere = new Inchiriere(1, masina, Date.valueOf("2023, 1, 1"), Date.valueOf("2023, 1, 10"));
        inchiriereRepository.add(inchiriere);
        inchiriereRepository.delete(1);

        TextFileRepository<Inchiriere> newRepository = new TextFileRepository<>(inchiriereFileName, new InchiriereFactory());
        assertNull(newRepository.findID(1));
    }
}
