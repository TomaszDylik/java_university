package lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void startShouldContainCarWord() {
        Car car = new Car();
        assertTrue(car.start().contains("Starting car"));
    }

    @Test
    void startShouldContainId() {
        Car car = new Car();
        assertTrue(car.start().contains(car.getId().toString()));
    }

    @Test
    void driveShouldReturnCorrectMessage() {
        Car car = new Car();
        assertEquals("Car is driving.", car.drive());
    }

    @Test
    void carShouldImplementDrivable() {
        assertTrue(new Car() instanceof Drivable);
    }

    @Test
    void carShouldExtendVehicle() {
        assertTrue(new Car() instanceof Vehicle);
    }

    @Test
    void eachCarShouldHaveUniqueId() {
        Car car1 = new Car();
        Car car2 = new Car();
        assertNotEquals(car1.getId(), car2.getId());
    }
}
