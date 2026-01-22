package Exceptions;

public class ObjectNotFoundException extends RepositoryException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}