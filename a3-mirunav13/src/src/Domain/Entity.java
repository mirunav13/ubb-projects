package Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private int id;

    public Entity(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{id=" + id + "}";
    }
}