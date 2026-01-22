package Service;

import Domain.Pacient;
import Domain.Programare;
import Repository.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class RapoarteService {
    private final Repository<Pacient> pacientiRepo;
    private final Repository<Programare> programariRepo;

    public RapoarteService(Repository<Pacient> pacientiRepo, Repository<Programare> programariRepo) {
        this.pacientiRepo = pacientiRepo;
        this.programariRepo = programariRepo;
    }

    public List<Map.Entry<Pacient, Long>> programariPerPacient() {
        List<Programare> programari = programariRepo.findAll();

        return programari.stream()
                .collect(Collectors.groupingBy(Programare::getPacientId, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    try {
                        Pacient p = pacientiRepo.findById(entry.getKey());
                        return Map.entry(p, entry.getValue());
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());
    }

    public List<Map.Entry<String, Long>> programariPerLuna() {
        List<Programare> programari = programariRepo.findAll();

        return programari.stream()
                .collect(Collectors.groupingBy(p -> {
                    LocalDate date = p.getData().toInstant() //transforma data intr un punct precis pe axa timpului
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                }, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());
    }

    public static class PacientUltimaProgramare {
        public Pacient pacient;
        public Date data;
        public long zile;

        public PacientUltimaProgramare(Pacient pacient, Date data, long zile) {
            this.pacient = pacient;
            this.data = data;
            this.zile = zile;
        }
    }

    public List<PacientUltimaProgramare> zileDelaUltimaProgramare() {
        List<Programare> programari = programariRepo.findAll();
        Date now = new Date();

        Map<Integer, Date> ultimaProgramare = programari.stream()
                .collect(Collectors.toMap(
                        Programare::getPacientId,
                        Programare::getData,
                        (d1, d2) -> d1.after(d2) ? d1 : d2
                ));

        return ultimaProgramare.entrySet().stream()
                .map(entry -> {
                    try {
                        Pacient p = pacientiRepo.findById(entry.getKey());
                        Date ultimaData = entry.getValue();
                        long days = (now.getTime() - ultimaData.getTime()) / (1000 * 60 * 60 * 24); //data este stocata in milisecunde
                        return new PacientUltimaProgramare(p, ultimaData, days);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .sorted((e1, e2) -> Long.compare(e2.zile, e1.zile))
                .collect(Collectors.toList());
    }

    public List<Map.Entry<String, Long>> celeMaiAglomerateLuni() {
        return programariPerLuna();
    }
}