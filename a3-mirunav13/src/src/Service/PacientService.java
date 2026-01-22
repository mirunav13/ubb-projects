package Service;

import Domain.Pacient;
import Exceptions.AppException;
import Repository.Repository;

import java.util.List;

public class PacientService {
    private final Repository<Pacient> repo;

    public PacientService(Repository<Pacient> repo) {
        this.repo = repo;
    }

    private void validate(Pacient p) {
        if (p.getNume() == null || p.getNume().trim().isEmpty())
            throw new AppException("Numele nu poate fi gol.");
        if (p.getPrenume() == null || p.getPrenume().trim().isEmpty())
            throw new AppException("Prenumele nu poate fi gol.");
        if (p.getVarsta() <= 0)
            throw new AppException("Vârsta trebuie să fie pozitivă.");
    }

    public void add(Pacient p) {
        validate(p);
        repo.add(p);
    }

    public void update(Pacient p) {
        validate(p);
        repo.update(p);
    }

    public void delete(int id) {
        repo.delete(id);
    }

    public Pacient get(int id) {
        return repo.findById(id);
    }

    public List<Pacient> all() {
        return repo.findAll();
    }
}
