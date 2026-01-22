package Test;

import Domain.Pacient;
import Exceptions.AppException;
import Repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    private InMemoryRepository<Pacient> repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryRepository<Pacient>();
    }

    @Test
    void testAddAndFind() {
        repo.add(new Pacient(1, "Popescu", "Ion", 30));
        assertEquals("Popescu", repo.findById(1).getNume());
    }

    @Test
    void testDuplicateId() {
        repo.add(new Pacient(1, "Popescu", "Ion", 30));
        assertThrows(AppException.class, () -> repo.add(new Pacient(1, "Altul", "Alt", 25)));
    }

    @Test
    void testDelete() {
        repo.add(new Pacient(1, "Popescu", "Ion", 30));
        repo.delete(1);
        assertThrows(AppException.class, () -> repo.findById(1));
    }

    @Test
    void testUpdate() {
        repo.add(new Pacient(1, "Popescu", "Ion", 30));
        repo.update(new Pacient(1, "Ionescu", "Maria", 25));
        assertEquals("Ionescu", repo.findById(1).getNume());
    }
}