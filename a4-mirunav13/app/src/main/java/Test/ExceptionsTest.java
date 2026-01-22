package Test;

import Exceptions.AppException;
import Exceptions.DuplicateIDException;
import Exceptions.ObjectNotFoundException;
import Exceptions.RepositoryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExceptionsTest {

    @Test
    void testAppException() {
        String message = "Eroare generală a aplicației.";
        AppException appException = new AppException(message);

        // Testează moștenirea
        assertTrue(appException instanceof RuntimeException);

        // Testează mesajul
        assertEquals(message, appException.getMessage());

        // Testează constructorul cu Throwable cause
        Throwable cause = new Exception("Cauza internă");
        AppException appExceptionWithCause = new AppException(message, cause);
        assertEquals(message, appExceptionWithCause.getMessage());
        assertEquals(cause, appExceptionWithCause.getCause());
    }

    @Test
    void testRepositoryException() {
        String message = "Eroare de repository.";
        RepositoryException repoException = new RepositoryException(message);

        // Testează moștenirea
        assertTrue(repoException instanceof RuntimeException);

        // Testează mesajul
        assertEquals(message, repoException.getMessage());
    }

    @Test
    void testDuplicateIDException() {
        String message = "ID duplicat: 5.";
        DuplicateIDException duplicateIDException = new DuplicateIDException(message);

        // Testează moștenirea (ar trebui să moștenească din RepositoryException)
        assertTrue(duplicateIDException instanceof RepositoryException);

        // Testează mesajul
        assertEquals(message, duplicateIDException.getMessage());
    }

    @Test
    void testObjectNotFoundException() {
        String message = "Obiectul cu ID 10 nu a fost găsit.";
        ObjectNotFoundException notFoundException = new ObjectNotFoundException(message);

        // Testează moștenirea (ar trebui să moștenească din RepositoryException)
        assertTrue(notFoundException instanceof RepositoryException);

        // Testează mesajul
        assertEquals(message, notFoundException.getMessage());
    }
}