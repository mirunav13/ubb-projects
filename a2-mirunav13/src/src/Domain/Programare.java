package Domain;

import java.time.LocalDateTime;

public class Programare extends Entity {
    private Pacient pacient;
    private LocalDateTime data;
    private String scop;

    public Programare(int id, Pacient pacient, LocalDateTime data, String scop) {
        super(id);
        this.pacient = pacient;
        this.data = data;
        this.scop = scop;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getScop() {
        return scop;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "id=" + id +
                ", pacient=" + pacient.getNume() + " " + pacient.getPrenume() +
                ", data=" + data +
                ", scop='" + scop + '\'' +
                '}';
    }
}
