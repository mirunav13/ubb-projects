package UI;

import Domain.Pacient;
import Domain.Programare;
import Exceptions.AppException;
import Service.PacientService;
import Service.ProgramareService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final PacientService pacientService;
    private final ProgramareService programareService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Console(PacientService pacientService, ProgramareService programareService) {
        this.pacientService = pacientService;
        this.programareService = programareService;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("alege: ");
            String opt = sc.nextLine().trim();
            try {
                switch (opt) {
                    case "1":
                        listPacienti();
                        break;
                    case "2":
                        addPacient(sc);
                        break;
                    case "3":
                        updatePacient(sc);
                        break;
                    case "4":
                        deletePacient(sc);
                        break;
                    case "5":
                        listProgramari();
                        break;
                    case "6":
                        addProgramare(sc);
                        break;
                    case "7":
                        updateProgramare(sc);
                        break;
                    case "8":
                        deleteProgramare(sc);
                        break;
                    case "0":
                        System.out.println("la revedere!");
                        return;
                    default:
                        System.out.println("optiune invalida.");
                }
            } catch (RuntimeException e) {
                System.out.println("eroare: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("=== cabinet stomatologic ===");
        System.out.println("1. lista pacienti");
        System.out.println("2. adauga pacient");
        System.out.println("3. actualizeaza pacient");
        System.out.println("4. sterge pacient");
        System.out.println("5. lista programari");
        System.out.println("6. adauga programare");
        System.out.println("7. actualizeaza programare");
        System.out.println("8. sterge programare");
        System.out.println("0. iesire");
    }

    private void listPacienti() {
        List<Pacient> l = pacientService.all();
        if (l.isEmpty()) System.out.println("(fara pacienti)");
        for (Pacient p : l) System.out.println(p);
    }

    private void addPacient(Scanner sc) {
        int id = readId(sc);
        System.out.print("nume: ");
        String nume = sc.nextLine();
        System.out.print("prenume: ");
        String prenume = sc.nextLine();
        System.out.print("varsta: ");
        int varsta = Integer.parseInt(sc.nextLine());
        pacientService.add(new Pacient(id, nume, prenume, varsta));
        System.out.println("pacient adaugat.");
    }

    private void updatePacient(Scanner sc) {
        int id = readId(sc);
        System.out.print("nume nou: ");
        String nume = sc.nextLine();
        System.out.print("prenume nou: ");
        String prenume = sc.nextLine();
        System.out.print("varsta noua: ");
        int varsta = Integer.parseInt(sc.nextLine());
        pacientService.update(new Pacient(id, nume, prenume, varsta));
        System.out.println("pacient actualizat.");
    }

    private void deletePacient(Scanner sc) {
        int id = readId(sc);
        pacientService.delete(id);
        System.out.println("pacient sters.");
    }

    private void listProgramari() {
        List<Programare> l = programareService.all();
        if (l.isEmpty()) System.out.println("(fara programari)");
        for (Programare p : l) System.out.println(p);
    }

    private void addProgramare(Scanner sc) {
        int id = readId(sc);
        System.out.print("id pacient: ");
        int pacientId = Integer.parseInt(sc.nextLine());
        Date data = readDate(sc);
        System.out.print("scop: ");
        String scop = sc.nextLine();
        programareService.add(id, pacientId, data, scop);
        System.out.println("programare adaugata.");
    }

    private void updateProgramare(Scanner sc) {
        int id = readId(sc);
        System.out.print("id pacient nou: ");
        int pacientId = Integer.parseInt(sc.nextLine());
        Date data = readDate(sc);
        System.out.print("scop nou: ");
        String scop = sc.nextLine();
        programareService.update(id, pacientId, data, scop);
        System.out.println("programare actualizata.");
    }

    private void deleteProgramare(Scanner sc) {
        int id = readId(sc);
        programareService.delete(id);
        System.out.println("programare stearsa.");
    }

    private int readId(Scanner sc) {
        System.out.print("id: ");
        String s = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(s);
            if (id <= 0) throw new AppException("id-ul trebuie sa fie pozitiv (>= 1).");
            return id;
        } catch (NumberFormatException ex) {
            throw new AppException("id invalid (trebuie numar intreg).");
        }
    }

    private Date readDate(Scanner sc) {
        System.out.print("data (yyyy-MM-dd HH:mm): ");
        String s = sc.nextLine().trim();
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            throw new AppException("format de data invalid.");
        }
    }
}
