package Domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MasinaTest {

    @org.junit.jupiter.api.Test
    void marca() {
        Masina m = new Masina(1, "Dacia", "Logan");
        assertEquals("Dacia", m.marca());
    }

    @org.junit.jupiter.api.Test
    void model() {
        Masina m = new Masina(1, "Dacia", "Logan");
        assertEquals("Logan", m.model());
    }

    @org.junit.jupiter.api.Test
    void setMarca() {
        Masina m = new Masina(1, "Dacia", "Logan");
        m.setMarca("Renault");
        assertEquals("Renault", m.marca());
    }

    @org.junit.jupiter.api.Test
    void setModel() {
        Masina m = new Masina(1, "Dacia", "Logan");
        m.setModel("Clio");
        assertEquals("Clio", m.model());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Masina m = new Masina(1, "Dacia", "Logan");
        assertEquals("{ID: 1, Marca: Dacia, Model: Logan}", m.toString());
    }

    @Test
    void testEquals() {
        Masina m = new Masina(1, "Dacia", "Logan");
        Masina m1 = new Masina(1, "Dacia", "Logan");
        assertEquals(m, m1);
    }

    @Test
    void testHashCode() {
        Masina m = new Masina(1, "Dacia", "Logan");
        Masina m1 = new Masina(1, "Dacia", "Logan");
        assertEquals(m.hashCode(), m1.hashCode());
    }
}