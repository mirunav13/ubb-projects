package Test;

import Domain.Pacient;
import Exceptions.AppException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Repository.BinaryFileRepository;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryFileRepositoryTest {
    private static final String TEST_FILE = "test_pacienti.bin";
    private BinaryFileRepository<Pacient> repo;

    @BeforeEach
    void setUp() {
        new File(TEST_FILE).delete();
        repo = new BinaryFileRepository<Pacient>(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        new File(TEST_FILE).delete();
    }

    @Test
    void testLoadFromFile_EmptyFile() {
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void testAddAndSaveToFile() {
        Pacient p1 = new Pacient(1, "Popescu", "Ion", 30);
        Pacient p2 = new Pacient(2, "Ionescu", "Maria", 25);

        repo.add(p1);
        repo.add(p2);

        assertEquals(2, repo.findAll().size());
        assertEquals("Popescu", repo.findById(1).getNume());

        BinaryFileRepository<Pacient> newRepo = new BinaryFileRepository<Pacient>(TEST_FILE);
        List<Pacient> loadedList = newRepo.findAll();

        assertEquals(2, loadedList.size());
        assertEquals("Popescu", newRepo.findById(1).getNume());
        assertEquals("Maria", newRepo.findById(2).getPrenume());
    }

    @Test
    void testUpdateAndSaveToFile() {
        Pacient p1 = new Pacient(1, "Popescu", "Ion", 30);
        repo.add(p1);

        Pacient pUpdated = new Pacient(1, "Popescu_Updated", "Ion_Updated", 31);
        repo.update(pUpdated);

        assertEquals("Popescu_Updated", repo.findById(1).getNume());

        BinaryFileRepository<Pacient> newRepo = new BinaryFileRepository<Pacient>(TEST_FILE);

        assertEquals(1, newRepo.findAll().size());
        assertEquals("Popescu_Updated", newRepo.findById(1).getNume());
    }

    @Test
    void testDeleteAndSaveToFile() {
        Pacient p1 = new Pacient(1, "Popescu", "Ion", 30);
        Pacient p2 = new Pacient(2, "Ionescu", "Maria", 25);
        repo.add(p1);
        repo.add(p2);

        repo.delete(1);

        assertEquals(1, repo.findAll().size());
        assertThrows(AppException.class, () -> repo.findById(1));

        BinaryFileRepository<Pacient> newRepo = new BinaryFileRepository<Pacient>(TEST_FILE);

        assertEquals(1, newRepo.findAll().size());
        assertEquals("Ionescu", newRepo.findById(2).getNume());
        assertThrows(AppException.class, () -> newRepo.findById(1));
    }
}