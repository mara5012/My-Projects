package Domain;

import Domain.Inchiriere;
import Domain.Masina;
import Domain.TipInchiriere;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TipInchiriereTest {

    private final TipInchiriere tipInchiriere = new TipInchiriere();

    @Test
    public void testCreateObiect_validInput() {
        // Date de intrare corecte
        String line = "1, 123 Toyota Corolla, 01/01/2023, 10/01/2023";

        // Crearea obiectului Inchiriere
        Inchiriere inchiriere = tipInchiriere.createObiect(line);

        // Verificări
        assertNotNull(inchiriere);  // Obiectul Inchiriere nu trebuie să fie null
        assertEquals(1, inchiriere.getId());  // ID-ul pentru inchiriere
        assertEquals(123, inchiriere.getMasina().getId());  // ID-ul pentru masina
        assertEquals("Toyota", inchiriere.getMasina().marca());  // Marca masinii
        assertEquals("Corolla", inchiriere.getMasina().model());  // Modelul masinii
        assertEquals(LocalDate.of(2023, 1, 1), inchiriere.getData_inceput());  // Data inceput
        assertEquals(LocalDate.of(2023, 1, 10), inchiriere.getData_sfarsit());  // Data sfarsit
    }

    @Test
    public void testCreateObiect_invalidDateFormat() {
        // Date de intrare cu format greșit pentru date
        String line = "1, 123 Toyota Corolla, 01-01-2023, 10/01/2023";

        // Test pentru a verifica excepția când data este incorectă
        assertThrows(IllegalArgumentException.class, () -> {
            tipInchiriere.createObiect(line);
        });
    }

    @Test
    public void testCreateObiect_invalidMasinaDetails() {
        // Date de intrare cu detalii incorecte pentru masina
        String line = "1, abc Toyota Corolla, 01/01/2023, 10/01/2023";

        // Test pentru a verifica excepția în cazul unui ID invalid pentru masina
        assertThrows(NumberFormatException.class, () -> {
            tipInchiriere.createObiect(line);
        });
    }

    @Test
    public void testCreateObiect_missingData() {
        // Date de intrare incomplete
        String line = "1, 123 Toyota Corolla, 01/01/2023";

        // Test pentru a verifica excepția când lipsesc date
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            tipInchiriere.createObiect(line);
        });
    }
}
