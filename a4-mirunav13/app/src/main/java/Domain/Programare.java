package Domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Programare extends Entity {
    private int pacientId;
    private Date data;
    private String scop;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Programare(int id, int pacientId, Date data, String scop) {
        super(id);
        this.pacientId = pacientId;
        this.data = data;
        this.scop = scop;
    }

    public int getPacientId() {
        return pacientId;
    }

    public void setPacientId(int pacientId) {
        this.pacientId = pacientId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getScop() {
        return scop;
    }

    public void setScop(String scop) {
        this.scop = scop;
    }

    @Override
    public String toString() {
        return getID() + ". pacient ID: " + pacientId + ", data: " + SDF.format(data) + ", scop: " + scop;
    }
}