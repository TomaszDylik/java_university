package lab;

import java.util.UUID;

public abstract class Vehicle {

    protected final UUID id;

    public Vehicle() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String start() {
        return "Starting vehicle " + id + "...";
    }
}
