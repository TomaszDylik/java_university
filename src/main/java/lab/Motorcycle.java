package lab;

public class Motorcycle extends Vehicle implements Drivable {

    @Override
    public String start() {
        return "Starting motorcycle " + id + "...";
    }

    @Override
    public String drive() {
        return "Motorcycle is driving.";
    }
}
