package lab;

public class Boat extends Vehicle implements Swimmable {

    @Override
    public String start() {
        return "Starting boat " + id + "...";
    }

    @Override
    public String swim() {
        return "Boat is swimming.";
    }
}
