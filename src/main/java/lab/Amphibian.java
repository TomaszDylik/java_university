package lab;

public class Amphibian extends Vehicle implements Drivable, Swimmable {

    @Override
    public String start() {
        return "Starting amphibian " + id + "...";
    }

    @Override
    public String drive() {
        return "Amphibian is driving.";
    }

    @Override
    public String swim() {
        return "Amphibian is swimming.";
    }
}
