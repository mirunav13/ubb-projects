package Repository;

import Domain.Entity;
import Exceptions.RepositoryException;

import java.io.*;

public abstract class TextFileRepository<T extends Entity> extends InMemoryRepository<T> {
    private final String filename;

    public TextFileRepository(String filename) {
        this.filename = filename;
        loadFromFile();
    }

    @Override
    public void add(T element) {
        super.add(element);
        saveToFile();
    }

    @Override
    public void update(T element) {
        super.update(element);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        super.delete(id);
        saveToFile();
    }

    private void loadFromFile() {
        File file = new File(filename);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                T entity = readEntity(line);
                super.add(entity);
            }
        } catch (IOException e) {
            throw new RepositoryException("Eroare la citirea fișierului: " + filename);
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (T entity : findAll()) {
                bw.write(writeEntity(entity));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Eroare la scrierea fișierului: " + filename);
        }
    }

    protected abstract T readEntity(String line);
    protected abstract String writeEntity(T entity);
}
