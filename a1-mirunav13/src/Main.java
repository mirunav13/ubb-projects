
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static boolean esteNumarNatural(String s) {
        try {
            int n = Integer.parseInt(s);
            return n >= 0;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    private static int cmmdc(int a, int b) {
        while(b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    private static int cmmmc(int a, int b) {
        return a * b / cmmdc(a, b);
    }

    private static int calculeazaCmmmc(List<Integer> numere) {
        if (numere.isEmpty()) {
            return 0;
        } else {
            int rezultat = (Integer)numere.get(0);

            for(int i = 1; i < numere.size(); ++i) {
                rezultat = cmmmc(rezultat, (Integer)numere.get(i));
            }

            return rezultat;
        }
    }

    private static List<Integer> citesteNumereDeLaUtilizator() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> numere = new ArrayList();
        System.out.println("Introduceți numere naturale (0 sau mai mari), separate prin spațiu:");
        String linie = scanner.nextLine();
        String[] parti = linie.split("\\s+");

        for(String p : parti) {
            if (esteNumarNatural(p)) {
                numere.add(Integer.parseInt(p));
            }
        }

        return numere;
    }

    public static void main(String[] args) {
        List<Integer> numere = new ArrayList();

        for(String arg : args) {
            if (esteNumarNatural(arg)) {
                numere.add(Integer.parseInt(arg));
            }
        }

        if (numere.isEmpty()) {
            numere = citesteNumereDeLaUtilizator();
        }

        if (!numere.isEmpty()) {
            int rezultat = calculeazaCmmmc(numere);
            System.out.println("Cel mai mic multiplu comun al numerelor este: " + rezultat);
        } else {
            System.out.println("Nu au fost introduse numere naturale valide.");
        }

    }
}
