package Domain;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

class InchiriereTest {

    @Test
    void getMasina() {
        Masina m = new Masina(1, "Dacia", "Logan");
        Inchiriere i = new Inchiriere(10, m, Date.valueOf("2024-01-03"), Date.valueOf("2024-01-10"));
        Masina m1 = new Masina(1, "Dacia", "Logan");
        assertEquals(m1, i.getMasina());
    }

    @Test
    void getData_inceput() {
        Inchiriere i = new Inchiriere(10, new Masina(1, "Dacia", "Logan"), Date.valueOf("2024-01-03"), Date.valueOf("2024-01-10"));
        assertEquals(Date.valueOf("2024-01-03"), i.getData_inceput());
    }

    @Test
    void getData_sfarsit() {
        Inchiriere i = new Inchiriere(10, new Masina(1, "Dacia", "Logan"), Date.valueOf("2024-01-03"), Date.valueOf("2024-01-10"));
        assertEquals(Date.valueOf("2024-01-10"), i.getData_sfarsit());
    }

    @Test
    void setMasina() {
        Inchiriere i = new Inchiriere(10, new Masina(1, "Dacia", "Logan"), Date.valueOf("2024-01-03"), Date.valueOf("2024-01-10"));
        Masina m = new Masina(2, "Renault", "Clio");
        i.setMasina(m);
        assertEquals(m, i.getMasina());
    }

    @Test
    void setData_inceput() {
        Inchiriere i = new Inchiriere(10, new Masina(1, "Dacia", "Logan"), Date.valueOf("2024-01-03"), Date.valueOf("2024-01-10"));
        i.setData_inceput(Date.valueOf("2024-01-04"));
        assertEquals(Date.valueOf("2024-01-04"), i.getData_inceput());
    }

    @Test
    void setData_sfarsit() {
        Inchiriere i = new Inchiriere(10, new Masina(1, "Dacia", "Logan"), Date.valueOf("2024-01-03"), Date.valueOf("2024-01-10"));
        i.setData_sfarsit(Date.valueOf("2024-01-11"));
        assertEquals(Date.valueOf("2024-01-11"), i.getData_sfarsit());
    }

    @Test
    void testToString() {
        Inchiriere i = new Inchiriere(10, new Masina(1, "Dacia", "Logan"), Date.valueOf("2024-01-03"), Date.valueOf("2024-01-10"));
        assertEquals("{ID: 10, Masina: {ID: 1, Marca: Dacia, Model: Logan}, Data inceput: 2024-01-03, Data sfarsit: 2024-01-10}", i.toString());
    }
}
