package Repository;

import Domain.Entity;
import java.util.List;

public interface Repository<T extends Entity> {
    void add(T elem) throws Exception;
    void update(T elem) throws Exception;
    void delete(int id) throws Exception;
    T findById(int id);
    List<T> getAll();
}
