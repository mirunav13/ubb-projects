package Repository;

import Domain.Entity;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository<T extends Entity> implements Repository<T> {
    private List<T> elements = new ArrayList<>();

    @Override
    public void add(T elem) throws Exception {
        for (T e : elements) {
            if (e.getId() == elem.getId())
                throw new Exception("Element cu acest ID există deja!");
        }
        elements.add(elem);
    }

    @Override
    public void update(T elem) throws Exception {
        boolean gasit = false;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getId() == elem.getId()) {
                elements.set(i, elem);
                gasit = true;
                break;
            }
        }
        if (!gasit)
            throw new Exception("Elementul nu a fost găsit pentru actualizare!");
    }

    @Override
    public void delete(int id) throws Exception {
        boolean removed = elements.removeIf(e -> e.getId() == id);
        if (!removed)
            throw new Exception("Elementul nu a fost găsit pentru ștergere!");
    }

    @Override
    public T findById(int id) {
        for (T e : elements)
            if (e.getId() == id)
                return e;
        return null;
    }

    @Override
    public List<T> getAll() {
        return elements;
    }
}
