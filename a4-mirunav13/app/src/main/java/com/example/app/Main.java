package com.example.app;

import com.example.app.CabinetApplication;
import Domain.Pacient;
import Domain.Programare;
import Repository.*;
import Service.PacientService;
import Service.ProgramareService;
import Service.RapoarteService;
import UI.Console;
import Utils.DataGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("settings.properties"));
        } catch (IOException e) {
            System.out.println("Nu s-a găsit fișierul settings.properties");
            return;
        }

        String repoType = props.getProperty("Repository");
        String interfaceType = props.getProperty("Interface", "console");

        Repository<Pacient> pacientRepo;
        Repository<Programare> programareRepo;

        switch (repoType) {
            case "text":
                String pacientFile = props.getProperty("Patients");
                String programareFile = props.getProperty("Appointments");

                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                pacientRepo = new TextFileRepository<Pacient>(pacientFile) {
                    @Override
                    protected Pacient readEntity(String line) {
                        String[] parts = line.split(",");
                        return new Pacient(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2],
                                Integer.parseInt(parts[3])
                        );
                    }

                    @Override
                    protected String writeEntity(Pacient entity) {
                        return entity.getID() + "," + entity.getNume() + "," +
                                entity.getPrenume() + "," + entity.getVarsta();
                    }
                };

                programareRepo = new TextFileRepository<Programare>(programareFile) {
                    @Override
                    protected Programare readEntity(String line) {
                        String[] parts = line.split(",");
                        try {
                            return new Programare(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]),
                                    sdf.parse(parts[2]),
                                    parts[3]
                            );
                        } catch (Exception e) {
                            throw new RuntimeException("Eroare parsare programare: " + e.getMessage());
                        }
                    }

                    @Override
                    protected String writeEntity(Programare entity) {
                        return entity.getID() + "," + entity.getPacientId() + "," +
                                sdf.format(entity.getData()) + "," + entity.getScop();
                    }
                };
                break;

            case "binary":
                pacientRepo = new BinaryFileRepository<Pacient>(props.getProperty("Patients"));
                programareRepo = new BinaryFileRepository<Programare>(props.getProperty("Appointments"));
                break;

            case "database":
                String dbUrl = props.getProperty("DatabaseURL", "jdbc:sqlite:cabinet.db");
                pacientRepo = new PacientDatabaseRepository(dbUrl);
                programareRepo = new ProgramareDatabaseRepository(dbUrl);

                if (pacientRepo.findAll().isEmpty()) {
                    System.out.println("Baza de date este goala. Generez 100 pacienti si programari...");
                    DataGenerator.genereazaPacienti(pacientRepo, 100);
                    DataGenerator.genereazaProgramari(programareRepo, 100, 200);
                    System.out.println("Date generate cu succes!");
                }
                break;

            default:
                pacientRepo = new InMemoryRepository<Pacient>();
                programareRepo = new InMemoryRepository<Programare>();
                break;
        }

        PacientService pacientService = new PacientService(pacientRepo);
        ProgramareService programareService = new ProgramareService(programareRepo, pacientRepo);
        RapoarteService rapoarteService = new RapoarteService(pacientRepo, programareRepo);

        if (interfaceType.equalsIgnoreCase("gui")) {
            // Lansare JavaFX prin CabinetApplication
            CabinetApplication.setServices(pacientService, programareService, rapoarteService);
            CabinetApplication.launchApp(args);
        } else {
            // Interfata consola
            Console console = new Console(pacientService, programareService);
            console.run();
        }
    }
}
