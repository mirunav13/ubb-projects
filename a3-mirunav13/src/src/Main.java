import Domain.Pacient;
import Domain.Programare;
import Repository.*;
import Service.PacientService;
import Service.ProgramareService;
import UI.Console;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String pacientFile = props.getProperty("Patients");
        String programareFile = props.getProperty("Appointments");

        Repository<Pacient> pacientRepo;
        Repository<Programare> programareRepo;

        switch (repoType) {
            case "text":
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
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
                            throw new RuntimeException("Eroare parsare programare");
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
                pacientRepo = new BinaryFileRepository<Pacient>(pacientFile);
                programareRepo = new BinaryFileRepository<Programare>(programareFile);
                break;

            default:
                pacientRepo = new InMemoryRepository<Pacient>();
                programareRepo = new InMemoryRepository<Programare>();
                break;
        }

        PacientService pacientService = new PacientService(pacientRepo);
        ProgramareService programareService = new ProgramareService(programareRepo, pacientRepo);

        Console console = new Console(pacientService, programareService);
        console.run();
    }
}