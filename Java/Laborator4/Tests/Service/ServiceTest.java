package Service;

import Domain.Inchiriere;
import Domain.Masina;
import Repo.GenericRepository;
import Repo.Repository;
import Repo.SQLRepositoryInchiriere;
import Repo.SQLRepositoryMasina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;
    private GenericRepository<Masina> masinaRepository;
    private GenericRepository<Inchiriere> inchiriereRepository;
    private SQLRepositoryMasina sqlRepositoryMasina;
    private SQLRepositoryInchiriere sqlRepositoryInchiriere;

    @BeforeEach
    public void setUp() {
        // Creăm instanțele repository-urilor în memorie
        masinaRepository = new GenericRepository<>();
        inchiriereRepository = new GenericRepository<>();

        // Creăm instanța de Service
        service = new Service(masinaRepository, inchiriereRepository);
    }

    // Test pentru adăugarea unei mașini
    @Test
    public void testAddMasina() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        service.addMasina(masina);

        List<Masina> masini = masinaRepository.findAll();
        assertEquals(1, masini.size(), "Numărul de mașini nu este corect.");
        assertEquals(masina, masini.get(0), "Mașina adăugată nu corespunde.");
    }

    // Test pentru adăugarea unei închirieri
    @Test
    public void testAddInchiriere() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Inchiriere inchiriere = new Inchiriere(1, masina,
                Date.valueOf("2024-01-01"),
                Date.valueOf("2024-01-08"));

        service.addInchiriere(inchiriere);

        List<Inchiriere> inchirieri = inchiriereRepository.findAll();
        assertEquals(1, inchirieri.size(), "Numărul de închirieri nu este corect.");
        assertEquals(inchiriere, inchirieri.get(0), "Închirierea adăugată nu corespunde.");
    }

    // Test pentru actualizarea unei mașini (actualizare simplă)
    @Test
    public void testUpdateMasina() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        service.addMasina(masina);

        masina.setMarca("Renault");  // Modificăm marca
        service.updateMasina(masina);

        Masina masinaActualizata = masinaRepository.findID(1);
        assertEquals("Renault", masinaActualizata.getMarca(), "Marca mașinii nu a fost actualizată corect.");
    }

    // Test pentru găsirea tuturor mașinilor
    @Test
    public void testFindAllMasina() {
        Masina masina1 = new Masina(1, "Dacia", "Logan");
        Masina masina2 = new Masina(2, "Renault", "Clio");
        service.addMasina(masina1);
        service.addMasina(masina2);

        List<Masina> masini = service.findAllMasina();
        assertEquals(2, masini.size(), "Numărul de mașini nu este corect.");
        assertTrue(masini.contains(masina1), "Mașina 1 nu a fost găsită.");
        assertTrue(masini.contains(masina2), "Mașina 2 nu a fost găsită.");
    }

    // Test pentru găsirea unei închirieri după ID
    @Test
    public void testFindIDInchiriere() {
        Masina masina = new Masina(1, "Dacia", "Logan");
        Inchiriere inchiriere = new Inchiriere(1, masina,
                Date.valueOf("2024-01-01"),
                Date.valueOf("2024-01-08"));

        service.addInchiriere(inchiriere);

        Inchiriere inchiriereGasita = service.findIDInchiriere(1);
        assertEquals(inchiriere, inchiriereGasita, "Închirierea găsită nu corespunde.");
    }
}
