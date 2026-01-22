package Service;

import Domain.Programare;
import Exceptions.AppException;
import Repository.Repository;

import java.util.Date;
import java.util.List;

public class ProgramareService {
    private final Repository<Programare> programariRepo;
    private final Repository<Domain.Pacient> pacientiRepo;

    public ProgramareService(Repository<Programare> programariRepo, Repository<Domain.Pacient> pacientiRepo) {
        this.programariRepo = programariRepo;
        this.pacientiRepo = pacientiRepo;
    }

    private void validate(Programare p) {
        if (p.getData() == null)
            throw new AppException("Data programării este obligatorie.");
        if (p.getScop() == null || p.getScop().trim().isEmpty())
            throw new AppException("Scopul programării nu poate fi gol.");
    }

    private void checkOverlap(Programare newProgramare, int excludeId) {
        List<Programare> toate = programariRepo.findAll();
        for (Programare p : toate) {
            if (p.getID() == excludeId) continue;

            long diff = Math.abs(newProgramare.getData().getTime() - p.getData().getTime());
            long minutes = diff / (60 * 1000);

            if (minutes < 60) {
                throw new AppException("Programarea se suprapune cu alta! Distanța minimă: 60 minute.");
            }
        }
    }

    public void add(int id, int pacientId, Date data, String scop) {
        pacientiRepo.findById(pacientId);

        Programare p = new Programare(id, pacientId, data, scop);
        validate(p);
        checkOverlap(p, -1);
        programariRepo.add(p);
    }

    public void update(int id, int pacientId, Date data, String scop) {
        pacientiRepo.findById(pacientId);

        Programare p = new Programare(id, pacientId, data, scop);
        validate(p);
        checkOverlap(p, id);
        programariRepo.update(p);
    }

    public void delete(int id) {
        programariRepo.delete(id);
    }

    public Programare get(int id) {
        return programariRepo.findById(id);
    }

    public List<Programare> all() {
        return programariRepo.findAll();
    }
}