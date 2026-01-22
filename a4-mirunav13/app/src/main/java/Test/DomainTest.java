package Test;

import Domain.Pacient;
import Domain.Programare;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DomainTest {

    @Test
    void testPacientToStringAndAccessors() {
        Pacient p = new Pacient(1, "Popescu", "Ion", 30);
        assertEquals(1, p.getID());
        assertEquals("Popescu", p.getNume());
        assertEquals("Ion", p.getPrenume());
        assertEquals(30, p.getVarsta());

        p.setNume("Ionescu");
        p.setPrenume("Maria");
        p.setVarsta(25);

        assertEquals("Ionescu", p.getNume());
        assertEquals("Maria", p.getPrenume());
        assertEquals(25, p.getVarsta());

        String s = p.toString();
        assertTrue(s.contains("Ionescu"));
        assertTrue(s.contains("Maria"));
    }

    @Test
    void testProgramareFieldsAndToString() {
        Date data = new Date();
        Programare pr = new Programare(1, 5, data, "Control general");

        assertEquals(1, pr.getID());
        assertEquals(5, pr.getPacientId());
        assertEquals(data, pr.getData());
        assertEquals("Control general", pr.getScop());

        Date newData = new Date(System.currentTimeMillis() + 86400000);
        pr.setData(newData);
        pr.setScop("Detartraj");
        pr.setPacientId(10);

        assertEquals(newData, pr.getData());
        assertEquals("Detartraj", pr.getScop());
        assertEquals(10, pr.getPacientId());

        String str = pr.toString();
        assertTrue(str.contains("10"));
        assertTrue(str.contains("Detartraj"));
    }
}
