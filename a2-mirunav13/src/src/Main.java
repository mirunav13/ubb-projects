import Domain.Pacient;
import Domain.Programare;
import Repository.InMemoryRepository;
import Repository.Repository;
import Service.CabinetService;
import UI.ConsoleUI;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        Repository<Pacient> repoPacienti = new InMemoryRepository<Pacient>();
        Repository<Programare> repoProgramari = new InMemoryRepository<Programare>();
        CabinetService service = new CabinetService(repoPacienti, repoProgramari);

        service.adaugaPacient(1, "Popescu", "Ion", 30);
        service.adaugaPacient(2, "Ionescu", "Maria", 45);
        service.adaugaPacient(3, "Georgescu", "Andrei", 27);
        service.adaugaPacient(4, "Mihai", "Elena", 34);
        service.adaugaPacient(5, "Stan", "Cristina", 40);
        service.adaugaPacient(6, "Matei", "George", 28);


        service.adaugaProgramare(1, 1, LocalDateTime.of(2025, 11, 5, 9, 0), "Control general");
        service.adaugaProgramare(2, 2, LocalDateTime.of(2025, 11, 5, 10, 0), "Detartraj");
        service.adaugaProgramare(3, 3, LocalDateTime.of(2025, 11, 5, 11, 30), "Consult carie");
        service.adaugaProgramare(4, 4, LocalDateTime.of(2025, 11, 5, 13, 0), "Albire dentara");
        service.adaugaProgramare(5, 5, LocalDateTime.of(2025, 11, 5, 14, 30), "Plomba molar");

        new ConsoleUI(service).start();
    }
}
