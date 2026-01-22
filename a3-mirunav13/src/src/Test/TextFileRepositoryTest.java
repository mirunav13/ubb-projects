package Test;

import Domain.Pacient;
import Exceptions.AppException;
import Exceptions.RepositoryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Repository.TextFileRepository;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileRepositoryTest {
    private static final String TEST_FILE = "test_pacienti.txt";
    private TextFileRepository<Pacient> repo;

    /**
     * Clasa anonimă pentru Pacient care implementează metodele abstracte
     * readEntity și writeEntity necesare pentru TextFileRepository.
     */
    private TextFileRepository<Pacient> createPacientRepo() {
        return new TextFileRepository<Pacient>(TEST_FILE) {
            @Override
            protected Pacient readEntity(String line) {
                try {
                    String[] parts = line.split(",");
                    return new Pacient(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim())
                    );
                } catch (Exception e) {
                    throw new RepositoryException("Eroare la parsarea liniei: " + line);
                }
            }

            @Override
            protected String writeEntity(Pacient entity) {
                return entity.getID() + "," + entity.getNume() + "," +
                        entity.getPrenume() + "," + entity.getVarsta();
            }
        };
    }

    @BeforeEach
    void setUp() {
        // Asigură-te că fișierul de test nu există înainte de fiecare test
        new File(TEST_FILE).delete();
        repo = createPacientRepo();
    }

    @AfterEach
    void tearDown() {
        // Șterge fișierul de test după fiecare test
        new File(TEST_FILE).delete();
    }

    @Test
    void testLoadFromFile_EmptyFile() {
        // La setUp, fișierul nu există, deci repo ar trebui să fie gol.
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void testAddAndSaveToFile() {
        Pacient p1 = new Pacient(1, "Popescu", "Ion", 30);
        Pacient p2 = new Pacient(2, "Ionescu", "Maria", 25);

        repo.add(p1);
        repo.add(p2);

        // Verifică repo-ul în memorie
        assertEquals(2, repo.findAll().size());
        assertEquals("Popescu", repo.findById(1).getNume());

        // Simulează o nouă încărcare (creând un nou repository)
        TextFileRepository<Pacient> newRepo = createPacientRepo();
        List<Pacient> loadedList = newRepo.findAll();

        // Verifică repo-ul reîncărcat din fișier
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

        // Verifică repo-ul în memorie
        assertEquals("Popescu_Updated", repo.findById(1).getNume());

        // Simulează o nouă încărcare
        TextFileRepository<Pacient> newRepo = createPacientRepo();

        // Verifică repo-ul reîncărcat din fișier
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

        // Verifică repo-ul în memorie
        assertEquals(1, repo.findAll().size());
        assertThrows(AppException.class, () -> repo.findById(1));

        // Simulează o nouă încărcare
        TextFileRepository<Pacient> newRepo = createPacientRepo();

        // Verifică repo-ul reîncărcat din fișier
        assertEquals(1, newRepo.findAll().size());
        assertEquals("Ionescu", newRepo.findById(2).getNume());
        assertThrows(AppException.class, () -> newRepo.findById(1));
    }
}