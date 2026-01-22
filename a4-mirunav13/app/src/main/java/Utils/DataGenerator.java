package Utils;

import Domain.Pacient;
import Domain.Programare;
import Repository.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class DataGenerator {

    private static final String[] NUME = {
            "Popescu", "Ionescu", "Popa", "Pop", "Radu", "Dumitru", "Stan", "Stoica",
            "Gheorghe", "Dima", "Matei", "Dumitrescu", "Tudor", "Marin", "Cristea",
            "Nicolae", "Vasile", "Luca", "Constantinescu", "Mocanu"
    };

    private static final String[] PRENUME = {
            "Ion", "Maria", "Gheorghe", "Elena", "Vasile", "Ana", "Constantin", "Ioana",
            "Alexandru", "Andreea", "Mihai", "Alexandra", "Nicolae", "Cristina", "Adrian",
            "Diana", "Florin", "Gabriela", "Marius", "Alina"
    };

    private static final String[] SCOPURI = {
            "Control general", "Detartraj", "Consult carie", "Albire dentara",
            "Plomba molar", "Extractie", "Proteza", "Ortodontie",
            "Tratament canal", "Implant", "Consult urgenta", "Radiografie"
    };

    private static final Random random = new Random();

    public static void genereazaPacienti(Repository<Pacient> repo, int numar) {
        for (int i = 1; i <= numar; i++) {
            String nume = NUME[random.nextInt(NUME.length)];
            String prenume = PRENUME[random.nextInt(PRENUME.length)];
            int varsta = 18 + random.nextInt(65);

            try {
                repo.add(new Pacient(i, nume, prenume, varsta));
            } catch (Exception e) {
                System.out.println("Eroare generare pacient " + i + ": " + e.getMessage());
            }
        }
    }

    public static void genereazaProgramari(Repository<Programare> repo, int numarPacienti, int numarProgramari) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -12);
        Date startDate = cal.getTime();

        long diff = new Date().getTime() - startDate.getTime();

        for (int i = 1; i <= numarProgramari; i++) {
            int pacientId = 1 + random.nextInt(numarPacienti);
            long randomTime = startDate.getTime() + (long)(random.nextDouble() * diff);
            Date data = new Date(randomTime);

            cal.setTime(data);
            cal.set(Calendar.HOUR_OF_DAY, 8 + random.nextInt(9));
            cal.set(Calendar.MINUTE, random.nextBoolean() ? 0 : 30);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            data = cal.getTime();

            String scop = SCOPURI[random.nextInt(SCOPURI.length)];

            try {
                repo.add(new Programare(i, pacientId, data, scop));
            } catch (Exception e) {
                System.out.println("Eroare generare programare " + i + ": " + e.getMessage());
            }
        }
    }
}
