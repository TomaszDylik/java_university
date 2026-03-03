package lab;

public class Car extends Vehicle implements Drivable {

    @Override
    public String start() {
        return "Starting car " + id + "...";
    }

    @Override
    public String drive() {
        return "Car is driving.";
    }
}
