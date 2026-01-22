package Service;

import Domain.Pacient;
import Domain.Programare;
import Repository.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CabinetService {
    private Repository<Pacient> repoPacienti;
    private Repository<Programare> repoProgramari;

    public CabinetService(Repository<Pacient> repoPacienti, Repository<Programare> repoProgramari) {
        this.repoPacienti = repoPacienti;
        this.repoProgramari = repoProgramari;
    }

    public void adaugaPacient(int id, String nume, String prenume, int varsta) throws Exception {
        repoPacienti.add(new Pacient(id, nume, prenume, varsta));
    }

    public List<Pacient> getTotiPacientii() {
        return repoPacienti.getAll();
    }

    public void actualizeazaPacient(int id, String numeNou, String prenumeNou, int varstaNoua) throws Exception {
        Pacient pacient = repoPacienti.findById(id);
        if (pacient == null) {
            throw new Exception("Nu există pacient cu ID-ul " + id);
        }
        Pacient pacientActualizat = new Pacient(id, numeNou, prenumeNou, varstaNoua);
        repoPacienti.update(pacientActualizat);
    }

    public void stergePacient(int id) throws Exception {
        repoPacienti.delete(id);
    }



    public void adaugaProgramare(int id, int idPacient, LocalDateTime data, String scop) throws Exception {
        Pacient pacient = repoPacienti.findById(idPacient);
        if (pacient == null)
            throw new Exception("Pacientul nu există!");

        for (Programare p : repoProgramari.getAll()) {
            long minute = Math.abs(ChronoUnit.MINUTES.between(p.getData(), data));
            if (minute < 60)
                throw new Exception("Programarea se suprapune cu alta!");
        }

        repoProgramari.add(new Programare(id, pacient, data, scop));
    }

    public List<Programare> getToateProgramarile() {
        return repoProgramari.getAll();
    }

    public void actualizeazaProgramare(int id, int idPacientNou, LocalDateTime dataNoua, String scopNou) throws Exception {
        Programare programare = repoProgramari.findById(id);
        if (programare == null) {
            throw new Exception("Nu există programare cu ID-ul " + id);
        }

        Pacient pacientNou = repoPacienti.findById(idPacientNou);
        if (pacientNou == null) {
            throw new Exception("Pacientul cu ID-ul dat nu există!");
        }

        for (Programare p : repoProgramari.getAll()) {
            if (p.getId() != id) {
                long minute = Math.abs(ChronoUnit.MINUTES.between(p.getData(), dataNoua));
                if (minute < 60) {
                    throw new Exception("Noua programare se suprapune cu alta existentă!");
                }
            }
        }

        Programare programareNoua = new Programare(id, pacientNou, dataNoua, scopNou);
        repoProgramari.update(programareNoua);
    }

    public void stergeProgramare(int id) throws Exception {
        repoProgramari.delete(id);
    }
}
