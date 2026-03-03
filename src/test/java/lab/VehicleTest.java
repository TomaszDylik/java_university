package lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    // --- Motorcycle ---

    @Test
    void motorcycleStartShouldContainMotorcycleWord() {
        Motorcycle m = new Motorcycle();
        assertTrue(m.start().contains("Starting motorcycle"));
    }

    @Test
    void motorcycleDriveShouldReturnCorrectMessage() {
        Motorcycle m = new Motorcycle();
        assertEquals("Motorcycle is driving.", m.drive());
    }

    @Test
    void motorcycleShouldImplementDrivable() {
        assertTrue(new Motorcycle() instanceof Drivable);
    }

    // --- Plane ---

    @Test
    void planeStartShouldContainPlaneWord() {
        Plane p = new Plane();
        assertTrue(p.start().contains("Starting plane"));
    }

    @Test
    void planeFlyShouldReturnCorrectMessage() {
        Plane p = new Plane();
        assertEquals("Plane is flying.", p.fly());
    }

    @Test
    void planeShouldImplementFlyable() {
        assertTrue(new Plane() instanceof Flyable);
    }

    // --- Boat ---

    @Test
    void boatStartShouldContainBoatWord() {
        Boat b = new Boat();
        assertTrue(b.start().contains("Starting boat"));
    }

    @Test
    void boatSwimShouldReturnCorrectMessage() {
        Boat b = new Boat();
        assertEquals("Boat is swimming.", b.swim());
    }

    @Test
    void boatShouldImplementSwimmable() {
        assertTrue(new Boat() instanceof Swimmable);
    }

    // --- Amphibian ---

    @Test
    void amphibianStartShouldContainAmphibianWord() {
        Amphibian a = new Amphibian();
        assertTrue(a.start().contains("Starting amphibian"));
    }

    @Test
    void amphibianDriveShouldReturnCorrectMessage() {
        Amphibian a = new Amphibian();
        assertEquals("Amphibian is driving.", a.drive());
    }

    @Test
    void amphibianSwimShouldReturnCorrectMessage() {
        Amphibian a = new Amphibian();
        assertEquals("Amphibian is swimming.", a.swim());
    }

    @Test
    void amphibianShouldImplementBothDrivableAndSwimmable() {
        Amphibian a = new Amphibian();
        assertTrue(a instanceof Drivable);
        assertTrue(a instanceof Swimmable);
    }

    // --- Seaplane ---

    @Test
    void seaplaneStartShouldContainSeaplaneWord() {
        Seaplane s = new Seaplane();
        assertTrue(s.start().contains("Starting seaplane"));
    }

    @Test
    void seaplaneFlyShouldReturnCorrectMessage() {
        Seaplane s = new Seaplane();
        assertEquals("Seaplane is flying.", s.fly());
    }

    @Test
    void seaplaneSwimShouldReturnCorrectMessage() {
        Seaplane s = new Seaplane();
        assertEquals("Seaplane is swimming.", s.swim());
    }

    @Test
    void seaplaneShouldImplementBothFlyableAndSwimmable() {
        Seaplane s = new Seaplane();
        assertTrue(s instanceof Flyable);
        assertTrue(s instanceof Swimmable);
    }

    // --- Vehicle general ---

    @Test
    void vehicleIdShouldNeverBeNull() {
        Vehicle v = new Car();
        assertNotNull(v.getId());
    }
}