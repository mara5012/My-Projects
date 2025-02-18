package Domain;

import Domain.Masina;
import Domain.TipMasina;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TipMasinaTest {

    @Test
    public void testCreateObiectValidInput() {
        // Test pentru input corect
        String line = "1, Toyota, Corolla";
        TipMasina tipMasina = new TipMasina();

        Masina masina = tipMasina.createObiect(line);

        // Verifică dacă valorile sunt corect setate
        assertEquals(1, masina.getId());
        assertEquals("Toyota", masina.marca());
        assertEquals("Corolla", masina.model());
    }

    @Test
    public void testCreateObiectInvalidId() {
        // Test pentru un id invalid (nu e un număr)
        String line = "abc, Toyota, Corolla";
        TipMasina tipMasina = new TipMasina();

        // Verifică dacă este aruncată o excepție NumberFormatException
        assertThrows(NumberFormatException.class, () -> tipMasina.createObiect(line));
    }

    @Test
    public void testCreateObiectMissingFields() {
        // Test pentru un input incomplet (lipsește modelul)
        String line = "1, Toyota";
        TipMasina tipMasina = new TipMasina();

        // Verifică dacă este aruncată o excepție ArrayIndexOutOfBoundsException
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> tipMasina.createObiect(line));
    }

    @Test
    public void testCreateObiectEmptyString() {
        // Test pentru un șir de caractere gol
        String line = "";
        TipMasina tipMasina = new TipMasina();

        // Verifică dacă este aruncată o excepție IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> tipMasina.createObiect(line));
    }

    @Test
    public void testCreateObiectExtraFields() {
        // Test pentru input cu câmpuri extra
        String line = "1, Toyota, Corolla, ExtraField";
        TipMasina tipMasina = new TipMasina();

        Masina masina = tipMasina.createObiect(line);

        // Verificăm doar câmpurile esențiale și ignorăm pe cele suplimentare
        assertEquals(1, masina.getId());
        assertEquals("Toyota", masina.marca());
        assertEquals("Corolla", masina.model());
    }
}
