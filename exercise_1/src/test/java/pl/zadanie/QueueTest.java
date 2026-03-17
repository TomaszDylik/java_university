package pl.zadanie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy opisujące podstawowe zachowanie kolejki.
 */
public class QueueTest {

    /** Nowo utworzona kolejka powinna startować jako pusta. */
    @Test
    public void newQueueShouldBeEmpty() {
        Queue<String> queue = new Queue<>();
        assertTrue(queue.isEmpty());
    }

    /** Po dodaniu jednego elementu kolejka nie może być pusta. */
    @Test
    public void shouldNotBeEmptyAfterEnqueue() {
        Queue<String> queue = new Queue<>();

        queue.enqueue("Pierwszy wagon");

        assertFalse(queue.isEmpty(), "Kolejka nie powinna być pusta po dodaniu elementu");
    }

    /** Dodany element powinien zostać poprawnie pobrany z kolejki. */
    @Test
    public void shouldDequeueAddedElement() {
        
        Queue<String> queue = new Queue<>();
        String expectedValue = "Pierwszy wagon";
        queue.enqueue(expectedValue);

        String actualValue = queue.dequeue(); 

        assertEquals(expectedValue, actualValue, "Pobrany element powinien być taki sam jak dodany");
        assertTrue(queue.isEmpty(), "Kolejka powinna być pusta po pobraniu jedynego elementu");
    }

    /** Kolejka powinna zachować kolejność FIFO (First In, First Out). */
    @Test
    public void shouldMaintainFifoOrderForMultipleElements() {
        Queue<String> queue = new Queue<>();
        
        queue.enqueue("Pierwszy");
        queue.enqueue("Drugi");
        queue.enqueue("Trzeci");

        assertEquals("Pierwszy", queue.dequeue(), "Pierwszy dodany powinien wyjść pierwszy");
        assertEquals("Drugi", queue.dequeue(), "Drugi dodany powinien wyjść drugi");
        assertEquals("Trzeci", queue.dequeue(), "Trzeci dodany powinien wyjść trzeci");
        assertTrue(queue.isEmpty());
    }
}