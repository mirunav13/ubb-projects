package Test;

import Domain.Pacient;
import Exceptions.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Repository.InMemoryRepository;
import Service.PacientService;

import static org.junit.jupiter.api.Assertions.*;

public class PacientServiceTest {
    private PacientService service;

    @BeforeEach
    void setUp() {
        service = new PacientService(new InMemoryRepository<Pacient>());
    }

    @Test
    void testAddValid() {
        service.add(new Pacient(1, "Popescu", "Ion", 30));
        assertEquals(1, service.all().size());
    }

    @Test
    void testInvalidNume() {
        assertThrows(AppException.class, () -> service.add(new Pacient(2, "", "Ion", 30)));
    }

    @Test
    void testInvalidVarsta() {
        assertThrows(AppException.class, () -> service.add(new Pacient(3, "Popescu", "Ion", -5)));
    }
}