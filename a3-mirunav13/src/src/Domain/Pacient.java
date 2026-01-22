package Domain;

public class Pacient extends Entity {
    private String nume;
    private String prenume;
    private int varsta;

    public Pacient(int id, String nume, String prenume, int varsta) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return getID() + ". " + nume + " " + prenume + ", varsta: " + varsta;
    }
}