package lab;

public class Seaplane extends Vehicle implements Flyable, Swimmable {

    @Override
    public String start() {
        return "Starting seaplane " + id + "...";
    }

    @Override
    public String fly() {
        return "Seaplane is flying.";
    }

    @Override
    public String swim() {
        return "Seaplane is swimming.";
    }
}
