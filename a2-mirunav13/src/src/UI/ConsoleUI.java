package UI;

import Domain.Pacient;
import Domain.Programare;
import Service.CabinetService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ConsoleUI {
    private CabinetService service;
    private Scanner in = new Scanner(System.in);

    public ConsoleUI(CabinetService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            System.out.println("\n CABINET STOMATOLOGIC ");
            System.out.println("1. Adauga pacient");
            System.out.println("2. Listeaza pacienti");
            System.out.println("3. Modifica pacient");
            System.out.println("4. Sterge pacient");
            System.out.println("5. Adauga programare");
            System.out.println("6. Listeaza programari");
            System.out.println("7. Modifica programare");
            System.out.println("8. Sterge programare");
            System.out.println("0. Iesire");
            System.out.print("Alege optiunea: ");

            int opt = Integer.parseInt(in.nextLine());
            try {
                switch (opt) {
                    case 1 -> adaugaPacient();
                    case 2 -> listeazaPacienti();
                    case 3 -> actualizeazaPacient();
                    case 4 -> stergePacient();
                    case 5 -> adaugaProgramare();
                    case 6 -> listeazaProgramari();
                    case 7 -> actualizeazaProgramare();
                    case 8 -> stergeProgramare();
                    case 0 -> {
                        System.out.println("La revedere!");
                        return;
                    }
                    default -> System.out.println("Optiune invalida!");
                }
            } catch (Exception e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    private void adaugaPacient() throws Exception {
        System.out.print("ID: ");
        int id = Integer.parseInt(in.nextLine());
        System.out.print("Nume: ");
        String nume = in.nextLine();
        System.out.print("Prenume: ");
        String prenume = in.nextLine();
        System.out.print("Varsta: ");
        int varsta = Integer.parseInt(in.nextLine());
        service.adaugaPacient(id, nume, prenume, varsta);
    }

    private void listeazaPacienti() {
        for (Pacient p : service.getTotiPacientii())
            System.out.println(p);
    }

    private void actualizeazaPacient() {
        try {
            System.out.print("ID pacient de actualizat: ");
            int id = Integer.parseInt(in.nextLine());
            System.out.print("Nume nou: ");
            String nume = in.nextLine();
            System.out.print("Prenume nou: ");
            String prenume = in.nextLine();
            System.out.print("Varsta noua: ");
            int varsta = Integer.parseInt(in.nextLine());
            service.actualizeazaPacient(id, nume, prenume, varsta);
            System.out.println("Pacient actualizat cu succes!");
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void stergePacient() throws Exception {
        System.out.print("ID pacient de sters: ");
        int id = Integer.parseInt(in.nextLine());
        service.stergePacient(id);
    }

    private void adaugaProgramare() throws Exception {
        System.out.print("ID programare: ");
        int id = Integer.parseInt(in.nextLine());
        System.out.print("ID pacient: ");
        int idPacient = Integer.parseInt(in.nextLine());
        System.out.print("An: ");
        int an = Integer.parseInt(in.nextLine());
        System.out.print("Luna: ");
        int luna = Integer.parseInt(in.nextLine());
        System.out.print("Zi: ");
        int zi = Integer.parseInt(in.nextLine());
        System.out.print("Ora: ");
        int ora = Integer.parseInt(in.nextLine());
        System.out.print("Minut: ");
        int minut = Integer.parseInt(in.nextLine());
        System.out.print("Scopul programarii: ");
        String scop = in.nextLine();

        service.adaugaProgramare(id, idPacient, LocalDateTime.of(an, luna, zi, ora, minut), scop);
    }

    private void listeazaProgramari() {
        for (Programare p : service.getToateProgramarile())
            System.out.println(p);
    }

    private void actualizeazaProgramare() {
        try {
            System.out.print("ID programare de actualizat: ");
            int id = Integer.parseInt(in.nextLine());
            System.out.print("ID pacient nou: ");
            int idPacient = Integer.parseInt(in.nextLine());
            System.out.print("Data noua (yyyy-MM-dd HH:mm): ");
            LocalDateTime data = LocalDateTime.parse(in.nextLine().replace(" ", "T"));
            System.out.print("Scop nou: ");
            String scop = in.nextLine();
            service.actualizeazaProgramare(id, idPacient, data, scop);
            System.out.println("Programare actualizata cu succes!");
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void stergeProgramare() throws Exception {
        System.out.print("ID programare de sters: ");
        int id = Integer.parseInt(in.nextLine());
        service.stergeProgramare(id);
    }
}
