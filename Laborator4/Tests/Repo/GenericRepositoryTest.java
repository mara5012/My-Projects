package Repo;

import Domain.Inchiriere;
import Domain.Masina;
import Exceptii.EntityAlreadyExistsException;
import Exceptii.EntityNotFoundException;
import Exceptii.InvalidEntityException;
import Repo.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GenericRepositoryTest {

    private GenericRepository<Masina> masinaRepository;
    private GenericRepository<Inchiriere> inchiriereRepository;

    @BeforeEach
    public void setUp() {
        masinaRepository = new GenericRepository<>();
        inchiriereRepository = new GenericRepository<>();
    }

    // Teste pentru Masina
    @Test
    public void testAddMasinaValid() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        masinaRepository.add(masina);
        assertEquals(masina, masinaRepository.findID(1));
    }

    @Test
    public void testAddMasinaDuplicateID() {
        Masina masina1 = new Masina(1, "Dacia", "Logan");
        Masina masina2 = new Masina(1, "Renault", "Clio");

        masinaRepository.add(masina1);
        assertThrows(EntityAlreadyExistsException.class, () -> masinaRepository.add(masina2));
    }

    @Test
    public void testAddNullMasina() {
        assertThrows(InvalidEntityException.class, () -> masinaRepository.add(null));
    }

    @Test
    public void testFindAllMasini() {
        Masina masina1 = new Masina(1, "Dacia", "Logan");
        Masina masina2 = new Masina(2, "Renault", "Clio");

        masinaRepository.add(masina1);
        masinaRepository.add(masina2);

        assertEquals(2, masinaRepository.findAll().size());
        assertTrue(masinaRepository.findAll().contains(masina1));
        assertTrue(masinaRepository.findAll().contains(masina2));
    }

    @Test
    public void testUpdateMasinaValid() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        masinaRepository.add(masina);

        Masina updatedMasina = new Masina(1, "Dacia", "Duster");
        masinaRepository.update(updatedMasina);

        assertEquals("Duster", masinaRepository.findID(1).getModel());
    }

    @Test
    public void testUpdateNonExistentMasina() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        assertThrows(EntityNotFoundException.class, () -> masinaRepository.update(masina));
    }

    @Test
    public void testDeleteMasinaValid() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        masinaRepository.add(masina);
        masinaRepository.delete(1);
        assertThrows(EntityNotFoundException.class, () -> masinaRepository.findID(1));
    }

    @Test
    public void testDeleteNonExistentMasina() {
        assertThrows(EntityNotFoundException.class, () -> masinaRepository.delete(100));
    }

    // Teste pentru Inchiriere
    @Test
    public void testAddInchiriereValid() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Date startDate = Date.valueOf(LocalDate.of(2023, 1, 1));
        Date endDate = Date.valueOf(LocalDate.of(2023, 1, 10));
        Inchiriere inchiriere = new Inchiriere(1, masina, startDate, endDate);

        inchiriereRepository.add(inchiriere);
        assertEquals(inchiriere, inchiriereRepository.findID(1));
    }

    @Test
    public void testAddInchiriereDuplicateID() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Date startDate = Date.valueOf(LocalDate.of(2023, 1, 1));
        Date endDate = Date.valueOf(LocalDate.of(2023, 1, 10));
        Inchiriere inchiriere1 = new Inchiriere(1, masina, startDate, endDate);
        Inchiriere inchiriere2 = new Inchiriere(1, masina, startDate, endDate);

        inchiriereRepository.add(inchiriere1);
        assertThrows(EntityAlreadyExistsException.class, () -> inchiriereRepository.add(inchiriere2));
    }

    @Test
    public void testFindAllInchirieri() {
        Masina masina1 = new Masina(1, "Dacia", "Logan");
        Masina masina2 = new Masina(2, "Renault", "Clio");
        Inchiriere inchiriere1 = new Inchiriere(1, masina1, Date.valueOf("2023, 1, 1"), Date.valueOf("2023, 1, 10"));
        Inchiriere inchiriere2 = new Inchiriere(2, masina2, Date.valueOf("2023, 2, 1"), Date.valueOf("2023, 2, 10"));

        inchiriereRepository.add(inchiriere1);
        inchiriereRepository.add(inchiriere2);

        assertEquals(2, inchiriereRepository.findAll().size());
        assertTrue(inchiriereRepository.findAll().contains(inchiriere1));
        assertTrue(inchiriereRepository.findAll().contains(inchiriere2));
    }

    @Test
    public void testUpdateInchiriereValid() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Inchiriere inchiriere = new Inchiriere(1, masina, Date.valueOf("2023, 1, 1"), Date.valueOf("2023, 1, 10"));
        inchiriereRepository.add(inchiriere);

        Inchiriere updatedInchiriere = new Inchiriere(1, masina, Date.valueOf("2023, 1, 5"), Date.valueOf("2023, 1, 15"));
        inchiriereRepository.update(updatedInchiriere);

        assertEquals(LocalDate.of(2023, 1, 5), inchiriereRepository.findID(1).getData_inceput());
        assertEquals(LocalDate.of(2023, 1, 15), inchiriereRepository.findID(1).getData_sfarsit());
    }

    @Test
    public void testUpdateNonExistentInchiriere() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Inchiriere inchiriere = new Inchiriere(1, masina, Date.valueOf("2023, 1, 1"), Date.valueOf("2023, 1, 10"));
        assertThrows(EntityNotFoundException.class, () -> inchiriereRepository.update(inchiriere));
    }

    @Test
    public void testDeleteInchiriereValid() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Inchiriere inchiriere = new Inchiriere(1, masina,Date.valueOf("2023, 1, 1"), Date.valueOf("2023, 1, 10"));
        inchiriereRepository.add(inchiriere);
        inchiriereRepository.delete(1);
        assertThrows(EntityNotFoundException.class, () -> inchiriereRepository.findID(1));
    }

    @Test
    public void testDeleteNonExistentInchiriere() {
        assertThrows(EntityNotFoundException.class, () -> inchiriereRepository.delete(100));
    }
}
