package lab;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- TESTOWANIE SAMOCHODU ---");
        Car myCar = new Car();
        System.out.println(myCar.start());
        System.out.println(myCar.drive());

        System.out.println("\n--- TESTOWANIE MOTOCYKLA ---");
        Motorcycle myMoto = new Motorcycle();
        System.out.println(myMoto.start());
        System.out.println(myMoto.drive());

        System.out.println("\n--- TESTOWANIE SAMOLOTU ---");
        Plane myPlane = new Plane();
        System.out.println(myPlane.start());
        System.out.println(myPlane.fly());

        System.out.println("\n--- TESTOWANIE ŁODZI ---");
        Boat myBoat = new Boat();
        System.out.println(myBoat.start());
        System.out.println(myBoat.swim());

        System.out.println("\n--- TESTOWANIE AMFIBII ---");
        Amphibian myAmphibian = new Amphibian();
        System.out.println(myAmphibian.start());
        System.out.println(myAmphibian.drive());
        System.out.println(myAmphibian.swim());

        System.out.println("\n--- TESTOWANIE WODNOSAMOLOTU ---");
        Seaplane mySeaplane = new Seaplane();
        System.out.println(mySeaplane.start());
        System.out.println(mySeaplane.fly());
        System.out.println(mySeaplane.swim());
    }
}
