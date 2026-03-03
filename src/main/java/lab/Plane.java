package lab;

public class Plane extends Vehicle implements Flyable {

    @Override
    public String start() {
        return "Starting plane " + id + "...";
    }

    @Override
    public String fly() {
        return "Plane is flying.";
    }
}
