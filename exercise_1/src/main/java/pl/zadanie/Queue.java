package pl.zadanie;

/**
 * Prosta kolejka FIFO oparta na liście jednokierunkowej.
 */
public class Queue<A> {

    /** Pierwszy element kolejki (początek listy). */
    private Node<A> head;

    /** Węzeł listy używanej wewnętrznie przez kolejkę. */
    private static class Node<A> {
        A value;
        Node<A> next;

        Node(A value) {
            this.value = value;
            this.next = null;
        }
    }

    public Queue() {
        this.head = null;
    }

    /**
     * Sprawdza, czy kolejka nie zawiera elementów.
     */
    public boolean isEmpty() {
        return head == null; 
    }

    /**
     * Dodaje element do kolejki.
     */
    public void enqueue(A value) {
        Node<A> newNode = new Node<>(value);
        
        if (isEmpty()) {
            head = newNode;
            return;
        }
        Node<A> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
    
    /**
     * Usuwa i zwraca pierwszy element z kolejki.
     */
    public A dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Kolejka jest pusta");
        }
        A removedValue = head.value;
        head = head.next;
        return removedValue;
    }
}