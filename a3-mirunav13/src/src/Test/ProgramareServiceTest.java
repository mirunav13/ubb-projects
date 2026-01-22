package Test;

import Domain.Pacient;
import Domain.Programare;
import Exceptions.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Repository.InMemoryRepository;
import Service.ProgramareService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramareServiceTest {
    private ProgramareService service;

    @BeforeEach
    void setUp() {
        var pacientRepo = new InMemoryRepository<Pacient>();
        pacientRepo.add(new Pacient(1, "Popescu", "Ion", 30));
        var programareRepo = new InMemoryRepository<Programare>();
        service = new ProgramareService(programareRepo, pacientRepo);
    }

    @Test
    void testAddValid() {
        service.add(1, 1, new Date(), "Control");
        assertEquals(1, service.all().size());
    }

    @Test
    void testInvalidPacient() {
        assertThrows(AppException.class, () -> service.add(2, 999, new Date(), "Control"));
    }

    @Test
    void testOverlap() {
        Date data1 = new Date();
        Date data2 = new Date(data1.getTime() + 30 * 60 * 1000);

        service.add(1, 1, data1, "Control");
        assertThrows(AppException.class, () -> service.add(2, 1, data2, "Detartraj"));
    }
}
